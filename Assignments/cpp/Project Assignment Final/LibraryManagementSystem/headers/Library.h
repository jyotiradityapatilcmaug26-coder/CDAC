#ifndef LIBRARY_H
#define LIBRARY_H

#include "Book.h"
#include "Member.h"
#include "Librarian.h"
#include "Container.h"
#include "Transaction.h"
#include "Exceptions.h"

#include <vector>
#include <map>
#include <string>

namespace LibrarySystem {

class Library {

private:

    Container<Book> books;
    Container<Member> members;

    std::vector<Transaction> transactions;

    std::map<int, int> issuedBooks;

    int findBookIndex(int bookId) const;
    int findMemberIndex(int memberId) const;

public:

    Library();

    // Book management
    void addBook(const Book& book);
    void removeBook(int bookId);
    void searchBook(int bookId) const;
    void displayAllBooks() const;

    // Member management
    void registerMember(const Member& member);
    void removeMember(int memberId);
    void searchMember(int memberId) const;
    void displayAllMembers() const;

    // Transactions
    void issueBook(
        int memberId,
        int bookId,
        const std::string& issueDate,
        const std::string& dueDate);

    void returnBook(
        int memberId,
        int bookId,
        const std::string& returnDate,
        int daysOverdue);

    void viewOverdueBooks() const;

    // File handling
    void saveData() const;
    void loadData();

    // Polymorphism demonstration
    void demonstratePolymorphism() const;
};

}

#endif