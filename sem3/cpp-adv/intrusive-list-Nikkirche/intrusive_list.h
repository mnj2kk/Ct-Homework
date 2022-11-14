#pragma once
#include <cstddef>
#include <iterator>
#include <type_traits>
namespace intrusive {
struct default_tag;
template <typename T, typename Tag>
class list;

class list_base {
private:
  list_base* prev{nullptr};
  list_base* next{nullptr};

  template <typename T, class Tag>
  friend class list;

  void unlink();


  bool operator==(const list_base& rhs) const;

  bool operator!=(const list_base& rhs) const;


public:
  list_base() = default;

  ~list_base();
  list_base& operator=(list_base&& rhs) noexcept;

  list_base(list_base&& rhs) noexcept;

};

template <typename Tag = default_tag>
struct list_element : list_base {};

template <typename T, typename Tag = default_tag>
class list {
  using l_element = list_element<Tag>;

  l_element base;

  template <bool IsConst>
  class list_iterator {
  private:
    l_element* i;
    friend struct list;

    using self_type = list_iterator;

  public:
    using value_type = typename std::conditional<IsConst, const T, T>::type;
    using reference = value_type&;
    using pointer = value_type*;
    using iterator_category = std::bidirectional_iterator_tag;
    using difference_type = std::ptrdiff_t;

    explicit list_iterator(list_base* s) : i(static_cast<l_element*>(s)) {}

    list_iterator() = default;

    reference operator*() const {
      return static_cast<T&>(*i);
    }

    pointer operator->() const {
      return static_cast<T*>(i);
    }

    self_type& operator++() {
      i = static_cast<l_element*>(this->i->next);
      return *this;
    }

    self_type operator++(int) {
      self_type old = *this;
      i = static_cast<l_element*>(this->i->next);
      return old;
    }

    self_type operator--(int) {
      self_type old = *this;
      this->i = static_cast<l_element*>(this->i->prev);
      return old;
    }

    self_type& operator--() {
      i = static_cast<l_element*>(this->i->prev);
      return *this;
    }

    template <bool rhsConst>
    bool operator==(const list_iterator<rhsConst>& rhs) const {

      return rhs.i == i;
    }

    template <bool rhsConst>
    bool operator!=(const list_iterator<rhsConst>& rhs) const {
      return i != rhs.i;
    }

    template <bool WasConst, class = std::enable_if_t<IsConst || !WasConst>>
    list_iterator(list_iterator<WasConst> rhs) : i(rhs.i) {}
  };
public:
  using iterator = list_iterator<false>;
  using const_iterator = list_iterator<true>;

  list() {
    base.prev = &base;
    base.next = &base;
  }
  //No idea why, but with = default it passes local tests, but not at git
  list& operator=(list&& other) noexcept {
    if (this->base != other.base) {
      if (!other.empty()) {
        this->base.next = other.base.next;
        this->base.prev = other.base.prev;
        this->base.prev->next = &base;
        this->base.next->prev = &base;
      } else {
        this->base.next = nullptr;
        this->base.prev = nullptr;
      }
      other.base.prev = nullptr;
      other.base.next = nullptr;
    }
    return *this;
  }
  list(list&& other) noexcept {
    *this = std::move(other);
  }


private:
  T& base_to_type(list_base& elem) const noexcept {
    return static_cast<T&>(static_cast<l_element&>(elem));
  }

  void connect(l_element* a, l_element* b) noexcept {
    a->next = b;
    b->prev = a;
  }
  void connect_prev(list_base* a, list_base* b) noexcept {
    a->prev->next = b;
  }
  void connect_next(list_base* a, list_base* b) noexcept {
    a->next->prev = b;
  }
  l_element* remove(l_element* a, list_base* tmp) noexcept {
    a->next->prev = a->prev;
    a->prev->next = tmp;
    return a;
  }

  void put_node_before(l_element* a, l_element* p) noexcept {
    a->prev = p->prev;
    a->next = p;
    p->prev->next = a;
    p->prev = a;
  }

public:
  bool empty() noexcept {
    return (base.prev == nullptr || base.next == nullptr) ||
           (&base == base.prev && &base == base.next);
  }

  iterator insert(iterator iter, T& element) noexcept {
    auto* e = static_cast<l_element*>(&element);
    l_element* place = iter.i;
    if (place == e) {
      return iter;
    }
    e->unlink();
    put_node_before(e, place);
    iter--;
    return iter;
  }

  void push_back(T& element) noexcept {
    insert(end(), element);
  }

  void push_front(T& element) noexcept {
    insert(begin(), element);
  }

  void splice(const_iterator iter, list& other, const_iterator start,
              const_iterator end) {
    if (start != end) {
      l_element* o_end = (--end).i;
      l_element* o_start = start.i;
      auto* tmp = static_cast<l_element*>(o_end->next);
      connect_next(o_end,o_start->prev);
      connect_prev(o_start,tmp);
      l_element* t_end = iter.i;
      l_element* t_start = (--iter).i;
      connect(o_end, t_end);
      connect(t_start, o_start);
    }
  }

  const T& back() const noexcept {
    return base_to_type(*base.prev);
  }

  const T& front() const {
    return base_to_type(*base.next);
  }

  T& back() noexcept {
    return base_to_type(*base.prev);
  }

  T& front() {
    return base_to_type(*base.next);
  }

  iterator begin() noexcept {
    return iterator(base.next);
  }

  iterator end() noexcept {
    return iterator(&base);
  }

  const_iterator begin() const noexcept {
    return const_iterator(base.next);
  }

  const_iterator end() const noexcept {
    return const_iterator(base.next->prev);
  }

  iterator erase(iterator it) noexcept {
    ++it;
    it.i->prev->unlink();
    return it;
  }

  void pop(iterator iter) noexcept {
    l_element* elem = iter.i;
    remove(elem, static_cast<l_element*>(elem->next))->unlink();
  }

  void pop_front() noexcept {
    pop(begin());
  }

  void pop_back() noexcept {
    pop(--end());
  }
};
} // namespace intrusive