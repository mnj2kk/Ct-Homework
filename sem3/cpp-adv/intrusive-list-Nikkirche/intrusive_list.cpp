#include "intrusive_list.h"
namespace intrusive {
void list_base::unlink() {
  if (this->prev != nullptr) {
    this->prev->next = this->next;
  }
  if (this->next != nullptr) {
    this->next->prev = this->prev;
  }
  this->prev = nullptr;
  this->next = nullptr;
}

list_base::~list_base() {
  unlink();
}

list_base& list_base::operator=(list_base&& rhs) noexcept {
  if (this != &rhs) {
    this->next = rhs.next;
    this->prev = rhs.prev;
    rhs.next = nullptr;
    rhs.prev = nullptr;
  }
  return *this;
}

list_base::list_base(list_base&& rhs) noexcept {
  *this = std::move(rhs);
}

bool list_base::operator==(const list_base& rhs) const {
  return prev == rhs.prev && next == rhs.next;
}

bool list_base::operator!=(const list_base& rhs) const {
  return prev != rhs.prev || next != rhs.next;
}

} // namespace intrusive
