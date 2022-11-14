#pragma once

#include <bit>
#include <memory>

struct bad_function_call : std::exception {
  const char* what() const noexcept override {
    return "use of deleted or empty function";
  }
};

namespace details {

using storage_t = std::aligned_storage_t<sizeof(void*), alignof(void*)>;

template <typename T>
T* get_big(storage_t storage) {
  return std::bit_cast<T*>(storage);
}

template <typename T>
const T* get_small(const storage_t* storage) {
  return reinterpret_cast<const T*>(storage);
}

template <typename T>
T* get_small(storage_t* storage) {
  return reinterpret_cast<T*>(storage);
}

template <typename T>
storage_t get_storage_from_small(T&& p) {
  return reinterpret_cast<storage_t&>(p);
}

template <typename T>
storage_t get_storage_from_big(T* ptr) {
  return std::bit_cast<storage_t>(ptr);
}

template <typename T>
constexpr inline bool fits_small =
    sizeof(T) < sizeof(storage_t) && std::is_nothrow_move_constructible_v<T> &&
    alignof(storage_t) % alignof(T) == 0 &&
    std::is_nothrow_move_assignable_v<T>;

template <typename R, typename... Args>
struct type_descriptor {

  void (*copy)(storage_t const& src, storage_t& dst);
  void (*move)(storage_t& src, storage_t& dst);
  void (*destroy)(storage_t& src);
  R (*invoke)(storage_t const& src, Args... args);

  static type_descriptor<R, Args...> const* get_empty_func_descriptor() {
    constexpr static type_descriptor<R, Args...> result = {
        [](storage_t const& src, storage_t& dst) { dst = src; },
        [](storage_t& src, storage_t& dst) { dst = src; },
        [](storage_t& src) {},
        [](storage_t const& src, Args... args) -> R {
          throw bad_function_call{};
        }};

    return &result;
  }

  template <typename T>
  static type_descriptor<R, Args...> const* get_descriptor() noexcept {

    constexpr static type_descriptor<R, Args...> result = {
        [](storage_t const& src, storage_t& dst) {
          if constexpr (fits_small<T>) {
            dst = get_storage_from_small(*const_cast<T*>(get_small<T>(&src)));
          } else {
            dst = get_storage_from_big(new T(*get_big<T>(src)));
          }
        },
        [](storage_t& src, storage_t& dst) {
          if constexpr (fits_small<T>) {
            dst = get_storage_from_small(std::move(*get_small<T>(&src)));
            get_small<T>(&src)->~T();
          } else {
            dst = src;
            src = get_storage_from_big<nullptr_t>(nullptr);
          }
        },
        [](storage_t& src) {
          if constexpr (fits_small<T>) {
            get_small<T>(&src)->~T();
          } else {
            delete get_big<T>(src);
          }
        },
        [](storage_t const& src, Args... args) -> R {
          if constexpr (fits_small<T>) {
            return (*get_small<T>(&src))(std::forward<Args>(args)...);
          } else {
            return (*get_big<T>(src))(std::forward<Args>(args)...);
          }
        }};

    return &result;
  }
};
} // namespace details

template <typename F>
struct function;

template <typename R, typename... Args>
struct function<R(Args...)> {
  function() noexcept
      : desc(
            details::type_descriptor<R, Args...>::get_empty_func_descriptor()) {
  }

  function(function const& other) : desc(other.desc) {
    other.desc->copy(other.storage, this->storage);
  }

  function(function&& other) noexcept : desc(other.desc) {
    other.desc->move(other.storage, this->storage);
  }

  template <typename T>
  function(T val)
      : desc(details::type_descriptor<R,
                                      Args...>::template get_descriptor<T>()) {
    if constexpr (details::fits_small<T>) {
      new (&storage) T(std::move(val));
    } else {
      auto ptr = new T(std::move(val));
      new (&storage) T*(ptr);
    }
  }

  function& operator=(function const& rhs) {
    if (this != &rhs) {
      function(rhs).swap(*this);
    }
    return *this;
  }
  void swap(function& other) noexcept {
    details::storage_t s;
    desc->move(other.storage, s);
    desc->move(storage, other.storage);
    other.desc->move(s, storage);
    auto t = std::move(other.desc);
    other.desc = desc;
    desc = t;
  }

  function& operator=(function&& rhs) noexcept {
    swap(rhs);
    return *this;
  }

  ~function() {
    desc->destroy(storage);
    desc = nullptr;
  }

  explicit operator bool() const noexcept {
    return details::type_descriptor<R, Args...>::get_empty_func_descriptor() !=
           desc;
  }

  R operator()(Args... args) const {
    return desc->invoke(storage, std::forward<Args>(args)...);
  }

  template <typename T>
  T* target() noexcept {
    if (!*this ||
        details::type_descriptor<R, Args...>::template get_descriptor<T>() !=
            desc) {
      return nullptr;
    }

    if constexpr (details::fits_small<T>) {
      return details::get_small<T>(&storage);
    } else {
      return details::get_big<T>(storage);
    }
  }

  template <typename T>
  T const* target() const noexcept {
    if (!*this ||
        details::type_descriptor<R, Args...>::template get_descriptor<T>() !=
            desc) {
      return nullptr;
    }

    if constexpr (details::fits_small<T>) {
      return details::get_small<T>(&storage);
    } else {
      return details::get_big<const T>(storage);
    }
  }

private:
  details::storage_t storage;
  const details::type_descriptor<R, Args...>* desc;
};
