#ifndef MEMBER_H
#define MEMBER_H

#include "Person.h"

namespace LibrarySystem {

class Member : public Person {
private:
    int issuedCount;
    int* issuedBookIds;
    int capacity;

    void resize();

public:
    Member();
    Member(int id, const std::string& name, const std::string& phone);

    Member(const Member& other);
    Member& operator=(const Member& other);

    ~Member();

    void displayInfo() const override;

    bool operator==(const Member& other) const;

    void addIssuedBook(int bookId);
    bool removeIssuedBook(int bookId);
    bool hasBook(int bookId) const;

    int getIssuedCount() const;
    int getIssuedBookId(int index) const;

    friend std::ostream& operator<<(std::ostream& os, const Member& member);
    friend std::istream& operator>>(std::istream& is, Member& member);
};

}

#endif