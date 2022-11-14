#pragma once

#include <algorithm>
#include <cstddef>
#include <memory>

template <typename T>
class shared_ptr;

template <typename T>
class weak_ptr;

class control_block {
  template <typename U>
  friend class weak_ptr;

  template <typename U>
  friend class shared_ptr;

  virtual void unlink() = 0;

protected:
  size_t weak_ref = 0;
  size_t strong_ref = 0;

  void dec_weak() noexcept {
    weak_ref--;
    if (weak_ref == 0 && strong_ref == 0) {
      delete this;
    }
  }

  void dec_strong() noexcept {
    strong_ref--;
    if (strong_ref == 0) {
      unlink();
      if (weak_ref == 0) {
        delete this;
      }
    }
  }

  void inc_weak() noexcept {
    weak_ref++;
  }

  void inc_strong() noexcept {
    strong_ref++;
  }

public:
  virtual ~control_block() = default;
};
template <typename T>
class object_control_block : public control_block {
public:
  std::aligned_storage_t<sizeof(T), alignof(T)> obj;

  template <typename... Args>
  object_control_block(Args&&... args) {
    new (&obj) T(std::forward<T>(args)...);
  }

  void unlink() override {
    get_ptr()->~T();
  }

  T* get_ptr() {
    return reinterpret_cast<T*>(&obj);
  }
};

template <typename T, typename D = std::default_delete<T>>
class ptr_control_block : public control_block, D {
private:
  template <typename U>
  friend class shared_ptr;

  T* ptr;

  ptr_control_block(T* ptr_, D&& d_ = D{}) : ptr(ptr_), D(std::move(d_)) {}

  void unlink() override {
    D::operator()(ptr);
  }
};

template <typename T>
class shared_ptr {
private:
  friend class weak_ptr<T>;

  template <typename V>
  friend class shared_ptr;

  template <typename O, typename... Args>
  friend shared_ptr<O> make_shared(Args&&... args);

  control_block* cb{nullptr};
  T* ptr{nullptr};

  shared_ptr(object_control_block<T>* obj) : cb(obj), ptr(obj->get_ptr()) {
    cb->inc_strong();
  }

  shared_ptr(control_block* obj, T* ptr_) : cb(obj), ptr(ptr_) {
    cb->inc_strong();
  }

  void update_refs() noexcept {
    if (cb != nullptr) {
      cb->inc_strong();
    }
  }

public:
  shared_ptr() noexcept =default;

  shared_ptr(std::nullptr_t){}

  template <typename V, typename D = std::default_delete<V>>
  shared_ptr(V* ptr_, D d = D{}) try
      : cb(new ptr_control_block<V, D>(ptr_, std::move(d))), ptr(ptr_) {
    cb->inc_strong();
  } catch (...) {
    delete this->ptr;
    throw std::bad_alloc();
  }

  template <typename V>
  shared_ptr(const shared_ptr<V>& sh, T* ptr_) : cb(sh.cb), ptr(ptr_) {
    update_refs();
  }

  shared_ptr(const shared_ptr<T>& other) : cb(other.cb), ptr(other.ptr) {
    update_refs();
  }

  template <typename V>
  shared_ptr(const shared_ptr<V>& other) : ptr(other.ptr), cb(other.cb) {
    update_refs();
  }

  shared_ptr(shared_ptr<T>&& other) noexcept {
    swap(other);
  }

  template <typename V>
  shared_ptr(shared_ptr<V>&& other) noexcept {
    swap(other);
  }

  shared_ptr& operator=(const shared_ptr& other) noexcept {
    shared_ptr tmp = shared_ptr(other);
    reset();
    swap(tmp);
    return *this;
  }

  shared_ptr& operator=(shared_ptr&& other) noexcept {
    if (this != &other) {
      reset();
      swap(other);
    }
    return *this;
  }

  T* get() const noexcept {
    if (cb == nullptr) {
      return nullptr;
    }
    return ptr;
  }

  operator bool() const noexcept {
    return get() != nullptr;
  }

  T& operator*() const noexcept {
    return *get();
  }

  T* operator->() const noexcept {
    return get();
  }

  friend bool operator==(const shared_ptr<T>& p, std::nullptr_t) {
    return p.cb == nullptr;
  }
  friend bool operator==(std::nullptr_t, const shared_ptr<T>& p) {
    return p.cb == nullptr;
  }
  friend bool operator!=(const shared_ptr<T>& p, std::nullptr_t) {
    return p.cb != nullptr;
  }
  friend bool operator!=(std::nullptr_t, const shared_ptr<T>& p) {
    return p.cb != nullptr;
  }

  std::size_t use_count() const noexcept {
    if (cb == nullptr) {
      return 0;
    }
    return cb->strong_ref;
  }

  void reset() noexcept {
    if (cb != nullptr && cb->strong_ref > 0) {
      cb->dec_strong();
    }
    cb = nullptr;
    ptr = nullptr;
  };

  template <typename V, typename D = std::default_delete<V>>
  void reset(V* new_ptr, D d = D{}) {
    reset();
    *this = shared_ptr(new_ptr, std::move(d));
  }

  void swap(shared_ptr<T>& other) noexcept {
    std::swap(this->cb, other.cb);
    std::swap(this->ptr, other.ptr);
  }

  template <typename V>
  void swap(shared_ptr<V>& other) {
    std::swap(this->cb, other.cb);
    std::swap(this->ptr, other.ptr);
  }

  ~shared_ptr() noexcept {
    reset();
  }
};

template <typename T>
class weak_ptr {
  friend class shared_ptr<T>;

public:
  weak_ptr() noexcept = default;

  weak_ptr(const shared_ptr<T>& other) noexcept : cb(other.cb), ptr(other.ptr) {
    if (cb != nullptr) {
      cb->inc_weak();
    }
  }

  weak_ptr& operator=(const shared_ptr<T>& other) noexcept {
    weak_ptr tmp = weak_ptr(other);
    reset();
    swap(tmp);
    return *this;
  }

  weak_ptr& operator=(const weak_ptr<T>& other) noexcept {
    weak_ptr tmp = weak_ptr(other);
    reset();
    swap(tmp);
    return *this;
  }

  weak_ptr(const weak_ptr<T>& other) : cb(other.cb), ptr(other.ptr) {
    update_refs();
  }

  weak_ptr& operator=(weak_ptr<T>&& other) noexcept {
    if (this != &other) {
      reset();
      swap(other);
    }
    return *this;
  }

  weak_ptr(weak_ptr<T>&& other) noexcept {
    swap(other);
  }

  ~weak_ptr() noexcept {
    reset();
  }

  void reset() noexcept {
    if (cb != nullptr && cb->weak_ref > 0) {
      cb->dec_weak();
    }
    cb = nullptr;
    ptr = nullptr;
  }

  shared_ptr<T> lock() const noexcept {
    if (cb != nullptr && cb->strong_ref > 0) {
      shared_ptr p = shared_ptr<T>(cb, ptr);
      return p;
    }
    return shared_ptr<T>(nullptr);
  };

  void swap(weak_ptr<T>& other) {
    std::swap(cb, other.cb);
    std::swap(ptr, other.ptr);
  }

private:
  control_block* cb{nullptr};
  T* ptr{nullptr};

  void update_refs() noexcept {
    if (cb != nullptr) {
      cb->inc_weak();
    }
  }
};

template <typename T, typename... Args>
shared_ptr<T> make_shared(Args&&... args) {
  return shared_ptr<T>(
      new object_control_block<T>(std::forward<Args...>(args)...));
}
