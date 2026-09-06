#include "../headers/Person.h"

namespace LibrarySystem {

Person::Person()
    : id(0), name(""), phone("") {
}

Person::Person(int id, const std::string& name, const std::string& phone)
    : id(id), name(name), phone(phone) {
}

int Person::getId() const {
    return id;
}

std::string Person::getName() const {
    return name;
}

std::string Person::getPhone() const {
    return phone;
}

void Person::setName(const std::string& name) {
    this->name = name;
}

void Person::setPhone(const std::string& phone) {
    this->phone = phone;
}

Person::~Person() {
}

}