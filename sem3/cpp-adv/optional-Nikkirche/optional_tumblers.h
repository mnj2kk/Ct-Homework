#pragma once
#include <type_traits>
namespace details {
template <typename T, bool enabled = std::is_copy_assignable_v<T>&&
                          std::is_copy_constructible_v<T>>
struct copy_assign_base {
  constexpr copy_assign_base() = default;
  constexpr copy_assign_base(const copy_assign_base&) = default;
  constexpr copy_assign_base(copy_assign_base&&) = default;
  constexpr copy_assign_base& operator=(copy_assign_base&&) = default;
  constexpr copy_assign_base& operator=(const copy_assign_base&) = delete;
};

template <typename T>
struct copy_assign_base<T, true> {};

template <typename T, bool enabled = std::is_move_assignable_v<T>&&
                          std::is_move_assignable_v<T>>
struct move_assign_base {
  constexpr move_assign_base() = default;
  constexpr move_assign_base(const move_assign_base&) = default;
  constexpr move_assign_base(move_assign_base&&) = default;
  constexpr move_assign_base& operator=(const move_assign_base&) = default;
  constexpr move_assign_base& operator=(move_assign_base&&) = delete;
};

template <typename T>
struct move_assign_base<T, true> {};

template <typename T, bool enabled = std::is_copy_constructible_v<T>>
struct copy_constructible_base {
  constexpr copy_constructible_base() = default;
  constexpr copy_constructible_base&
  operator=(copy_constructible_base&&) = default;
  constexpr copy_constructible_base&
  operator=(const copy_constructible_base&) = default;
  constexpr copy_constructible_base(copy_constructible_base&&) = default;
  constexpr copy_constructible_base(const copy_constructible_base&) = delete;
};

template <typename T>
struct copy_constructible_base<T, true> {};

template <typename T, bool enabled = std::is_move_constructible_v<T>>
struct move_constructible_base {
  constexpr move_constructible_base() = default;
  constexpr move_constructible_base&
  operator=(move_constructible_base&&) = default;
  constexpr move_constructible_base&
  operator=(const move_constructible_base&) = delete;
  constexpr move_constructible_base(const move_constructible_base&) = delete;
  constexpr move_constructible_base(move_constructible_base&&) = delete;
};

template <typename T>
struct move_constructible_base<T, true> {};

} // namespace details