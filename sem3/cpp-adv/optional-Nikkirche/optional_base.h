#pragma once
#include <type_traits>
#include <utility>
namespace details {
template <typename T, bool trivial = std::is_trivially_destructible_v<T>>
struct base {
  constexpr base() : dummy{0} {}

  template <typename... Args>
  constexpr base(Args... args)
      : data(std::forward<Args>(args)...), is_present{true} {}

  ~base() {
    reset();
  }
  constexpr void reset() {
    if (is_present) {
      data.~T();
      is_present = false;
    }
  }

protected:
  bool is_present{false};
  union {
    char dummy;
    T data;
  };
};
template <typename T>
struct base<T, true> {
  constexpr base() : dummy{0} {}

  template <typename... Args>
  constexpr base(Args... args)
      : data(std::forward<Args>(args)...), is_present{true} {}
  ~base() = default;
  constexpr void reset() {
    is_present = false;
  }

protected:
  bool is_present{false};
  union {
    char dummy;
    T data;
  };
};
template <typename T, bool trivial = std::is_trivially_copy_constructible_v<T>>
struct trivial_copy_constructor_base : base<T> {
  using base<T>::base;
  constexpr trivial_copy_constructor_base(T value)
      : base<T>(std::move(value)) {}
  constexpr trivial_copy_constructor_base() = default;
  constexpr trivial_copy_constructor_base(trivial_copy_constructor_base&&) =
      default;
  constexpr trivial_copy_constructor_base(
      const trivial_copy_constructor_base& other) {
    if (other.is_present) {
      new (&this->data) T(other.data);
      this->is_present = true;
    }
  }
};

template <typename T>
struct trivial_copy_constructor_base<T, true> : base<T> {
  using base<T>::base;
  constexpr trivial_copy_constructor_base(
      const trivial_copy_constructor_base&) = default;
};

template <typename T, bool trivial = std::is_trivially_copy_assignable_v<T>&&
                          std::is_trivially_copy_constructible_v<T>>
struct trivial_copy_assign_base : trivial_copy_constructor_base<T> {
  using trivial_copy_constructor_base<T>::trivial_copy_constructor_base;
  constexpr trivial_copy_assign_base() = default;
  constexpr trivial_copy_assign_base&
  operator=(const trivial_copy_assign_base& other) {
    if (this->is_present && !other.is_present) {
      this->reset();
    } else if (this->is_present && other.is_present) {
      this->data = other.data;
    } else if (other.is_present) {
      new (&this->data) T(other.data);
      this->is_present = true;
    }
    return *this;
  }
};

template <typename T>
struct trivial_copy_assign_base<T, true> : trivial_copy_constructor_base<T> {
  using trivial_copy_constructor_base<T>::trivial_copy_constructor_base;
};
template <typename T, bool trivial = std::is_trivially_move_constructible_v<T>&&
                          std::is_trivially_move_constructible_v<T>>
struct trivial_move_constructor_base : trivial_copy_assign_base<T> {
  using trivial_copy_assign_base<T>::trivial_copy_assign_base;
  constexpr trivial_move_constructor_base&
  operator=(const trivial_move_constructor_base& other) = default;
  constexpr trivial_move_constructor_base(
      const trivial_move_constructor_base& other) = default;
  constexpr trivial_move_constructor_base(
      trivial_move_constructor_base&& other) {
    if (this->is_present && other.is_present) {
      this->data = std::move(other.data);
    } else if (!this->is_present && other.is_present) {
      new (&this->data) T(std::move(other.data));
      this->is_present = true;
    } else {
      this->reset();
    }
  }
};

template <typename T>
struct trivial_move_constructor_base<T, true> : trivial_copy_assign_base<T> {
  using trivial_copy_assign_base<T>::trivial_copy_assign_base;
};

template <typename T, bool trivial = std::is_trivially_move_assignable_v<T>>
struct trivial_move_assign_base : trivial_move_constructor_base<T> {
  using trivial_move_constructor_base<T>::trivial_move_constructor_base;

  constexpr trivial_move_assign_base(const trivial_move_assign_base& other) =
      default;
  constexpr trivial_move_assign_base&
  operator=(trivial_move_assign_base&& other) {
    if (this != &other) {
      this->reset();
      if (other.is_present) {
        new (&this->data) T(std::move(other.data));
      }
      this->is_present = other.is_present;
    }
    return *this;
  }
  constexpr trivial_move_assign_base&
  operator=(const trivial_move_assign_base& other) = default;
  constexpr trivial_move_assign_base(trivial_move_assign_base&& other) =
      default;
};

template <typename T>
struct trivial_move_assign_base<T, true> : trivial_move_constructor_base<T> {
  using trivial_move_constructor_base<T>::trivial_move_constructor_base;
};

} // namespace details