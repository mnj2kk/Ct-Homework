#pragma once
#include "optional_base.h"
#include "optional_tumblers.h"
#include <utility>
struct nullopt_t {};

struct in_place_t {};
inline constexpr in_place_t in_place;
inline constexpr nullopt_t nullopt;

template <typename T>
class optional : details::copy_assign_base<T>,
                 details::move_assign_base<T>,
                 details::move_constructible_base<T>,
                 details::copy_constructible_base<T>,
                 public details::trivial_move_assign_base<T> {
  using details::trivial_move_assign_base<T>::trivial_move_assign_base;

public:
  constexpr optional() {}
  constexpr optional(nullopt_t) {}

  constexpr optional(const optional& other) = default;
  constexpr optional(optional&&) = default;

  constexpr optional& operator=(const optional&) = default;
  constexpr optional& operator=(optional&&) = default;

  constexpr optional(T& value)
      : details::trivial_move_assign_base<T>(std::move(value)) {}

  constexpr optional(T&& value) : details::trivial_move_assign_base<T>(value) {}

  template <typename... Args>
  explicit constexpr optional(in_place_t, Args&&... args)
      : details::trivial_move_assign_base<T>(std::forward<Args>(args)...) {}

  constexpr optional& operator=(nullopt_t) noexcept {
    this->reset();
    return *this;
  }

  constexpr explicit operator bool() const noexcept {
    return this->is_present;
  }

  constexpr T& operator*() noexcept {
    return this->data;
  }
  constexpr T const& operator*() const noexcept {
    return this->data;
  }

  constexpr T* operator->() noexcept {
    return &this->data;
  }
  constexpr T const* operator->() const noexcept {
    return &this->data;
  }

  template <typename... Args>
  constexpr void emplace(Args&&... args) {
    this->reset();
    new (&this->data) T(std::forward<Args>(args)...);
    this->is_present = true;
  }

  constexpr bool has_value() const {
    return this->is_present;
  }

  ~optional() = default;
};

template <typename T>
constexpr bool operator==(optional<T> const& a, optional<T> const& b) {
  if (a.has_value() && b.has_value()) {
    return *a == *b;
  } else if (a.has_value() == b.has_value()) {
    return true;
  }
  return false;
}

template <typename T>
constexpr bool operator!=(optional<T> const& a, optional<T> const& b) {
  if (a.has_value() && b.has_value()) {
    return *a != *b;
  } else if (a.has_value() == b.has_value()) {
    return false;
  }
  return true;
}

template <typename T>
constexpr bool operator<(optional<T> const& a, optional<T> const& b) {
  if (a.has_value() && b.has_value()) {
    return *a < *b;
  } else if (a.has_value() != b.has_value()) {
    return !a.has_value();
  }
  return false;
}

template <typename T>
constexpr bool operator<=(optional<T> const& a, optional<T> const& b) {
  if (a.has_value() && b.has_value()) {
    return *a <= *b;
  } else if (a.has_value() != b.has_value()) {
    return !a.has_value();
  }
  return true;
}

template <typename T>
constexpr bool operator>(optional<T> const& a, optional<T> const& b) {
  if (a.has_value() && b.has_value()) {
    return *a > *b;
  } else if (a.has_value() != b.has_value()) {
    return !b.has_value();
  }
  return false;
}

template <typename T>
constexpr bool operator>=(optional<T> const& a, optional<T> const& b) {
  if (a.has_value() && b.has_value()) {
    return *a >= *b;
  } else if (a.has_value() != b.has_value()) {
    return !b.has_value();
  }
  return true;
}