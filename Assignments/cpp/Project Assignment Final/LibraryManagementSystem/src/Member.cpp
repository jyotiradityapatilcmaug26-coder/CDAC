#include "../headers/Member.h"

namespace LibrarySystem {

Member::Member()
    : Person(), issuedCount(0), issuedBookIds(nullptr), capacity(2) {

    issuedBookIds = new int[capacity];
}

Member::Member(int id, const std::string& name, const std::string& phone)
    : Person(id, name, phone), issuedCount(0),
      issuedBookIds(nullptr), capacity(2) {

    issuedBookIds = new int[capacity];
}

// Deep copy constructor
Member::Member(const Member& other)
    : Person(other.id, other.name, other.phone),
      issuedCount(other.issuedCount),
      capacity(other.capacity) {

    issuedBookIds = new int[capacity];

    for (int i = 0; i < issuedCount; i++) {
        issuedBookIds[i] = other.issuedBookIds[i];
    }
}

// Assignment operator
Member& Member::operator=(const Member& other) {

    if (this != &other) {

        id = other.id;
        name = other.name;
        phone = other.phone;

        issuedCount = other.issuedCount;
        capacity = other.capacity;

        delete[] issuedBookIds;

        issuedBookIds = new int[capacity];

        for (int i = 0; i < issuedCount; i++) {
            issuedBookIds[i] = other.issuedBookIds[i];
        }
    }

    return *this;
}

// Destructor
Member::~Member() {
    delete[] issuedBookIds;
}

void Member::resize() {

    capacity *= 2;

    int* temp = new int[capacity];

    for (int i = 0; i < issuedCount; i++) {
        temp[i] = issuedBookIds[i];
    }

    delete[] issuedBookIds;

    issuedBookIds = temp;
}

void Member::addIssuedBook(int bookId) {

    if (issuedCount >= capacity) {
        resize();
    }

    issuedBookIds[issuedCount] = bookId;
    issuedCount++;
}

bool Member::removeIssuedBook(int bookId) {

    for (int i = 0; i < issuedCount; i++) {

        if (issuedBookIds[i] == bookId) {

            for (int j = i; j < issuedCount - 1; j++) {
                issuedBookIds[j] = issuedBookIds[j + 1];
            }

            issuedCount--;
            return true;
        }
    }

    return false;
}

bool Member::hasBook(int bookId) const {

    for (int i = 0; i < issuedCount; i++) {

        if (issuedBookIds[i] == bookId) {
            return true;
        }
    }

    return false;
}

int Member::getIssuedCount() const {
    return issuedCount;
}

int Member::getIssuedBookId(int index) const {

    if (index < 0 || index >= issuedCount) {
        return -1;
    }

    return issuedBookIds[index];
}

bool Member::operator==(const Member& other) const {
    return id == other.id;
}

void Member::displayInfo() const {

    std::cout << "\nMember ID: " << id;
    std::cout << "\nName: " << name;
    std::cout << "\nPhone: " << phone;
    std::cout << "\nIssued Books: " << issuedCount;

    if (issuedCount > 0) {

        std::cout << "\nBook IDs: ";

        for (int i = 0; i < issuedCount; i++) {
            std::cout << issuedBookIds[i] << " ";
        }
    }

    std::cout << "\n";
}

std::ostream& operator<<(std::ostream& os, const Member& member) {

    os << member.id << "|"
       << member.name << "|"
       << member.phone << "|"
       << member.issuedCount;

    for (int i = 0; i < member.issuedCount; i++) {
        os << "|" << member.issuedBookIds[i];
    }

    return os;
}

std::istream& operator>>(std::istream& is, Member& member) {

    is >> member.id
       >> member.name
       >> member.phone;

    return is;
}

}