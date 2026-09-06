#include "../headers/Library.h"

#include <fstream>
#include <sstream>
#include <iostream>

namespace LibrarySystem {

Library::Library()
    : books(), members(), transactions(), issuedBooks() {
}

int Library::findBookIndex(int bookId) const {

    for (int i = 0; i < books.getSize(); i++) {

        if (books[i].getBookId() == bookId) {
            return i;
        }
    }

    return -1;
}

int Library::findMemberIndex(int memberId) const {

    return members.find(memberId);
}

void Library::addBook(const Book& book) {

    if (findBookIndex(book.getBookId()) != -1) {
        std::cout << "Book already exists.\n";
        return;
    }

    books.add(book);

    std::cout << "Book added successfully.\n";
}

void Library::removeBook(int bookId) {

    int index = findBookIndex(bookId);

    if (index == -1) {
        throw BookNotFoundException(__FUNCTION__, __LINE__);
    }

    books.remove(index);

    std::cout << "Book removed successfully.\n";
}

void Library::searchBook(int bookId) const {

    int index = findBookIndex(bookId);

    if (index == -1) {
        throw BookNotFoundException(__FUNCTION__, __LINE__);
    }

    books[index].displayInfo();
}

void Library::displayAllBooks() const {

    if (books.getSize() == 0) {
        std::cout << "No books available.\n";
        return;
    }

    std::cout << "\n========== ALL BOOKS ==========\n";

    for (int i = 0; i < books.getSize(); i++) {
        books[i].displayInfo();
        std::cout << "-----------------------------\n";
    }
}

void Library::registerMember(const Member& member) {

    if (findMemberIndex(member.getId()) != -1) {
        std::cout << "Member already exists.\n";
        return;
    }

    members.add(member);

    std::cout << "Member registered successfully.\n";
}

void Library::removeMember(int memberId) {

    int index = findMemberIndex(memberId);

    if (index == -1) {
        throw MemberNotFoundException(__FUNCTION__, __LINE__);
    }

    if (members[index].getIssuedCount() > 0) {
        std::cout << "Member has issued books. Cannot remove.\n";
        return;
    }

    members.remove(index);

    std::cout << "Member removed successfully.\n";
}

void Library::searchMember(int memberId) const {

    int index = findMemberIndex(memberId);

    if (index == -1) {
        throw MemberNotFoundException(__FUNCTION__, __LINE__);
    }

    members[index].displayInfo();
}

void Library::displayAllMembers() const {

    if (members.getSize() == 0) {
        std::cout << "No members registered.\n";
        return;
    }

    std::cout << "\n========== ALL MEMBERS ==========\n";

    for (int i = 0; i < members.getSize(); i++) {
        members[i].displayInfo();
        std::cout << "-----------------------------\n";
    }
}

void Library::issueBook(
    int memberId,
    int bookId,
    const std::string& issueDate,
    const std::string& dueDate) {

    int memberIndex = findMemberIndex(memberId);

    if (memberIndex == -1) {
        throw MemberNotFoundException(__FUNCTION__, __LINE__);
    }

    int bookIndex = findBookIndex(bookId);

    if (bookIndex == -1) {
        throw BookNotFoundException(__FUNCTION__, __LINE__);
    }

    if (members[memberIndex].getIssuedCount() >= 5) {
        throw MaxBooksExceededException(__FUNCTION__, __LINE__);
    }

    if (!books[bookIndex].issueCopy()) {
        throw BookNotAvailableException(__FUNCTION__, __LINE__);
    }

    members[memberIndex].addIssuedBook(bookId);

    issuedBooks[bookId] = memberId;

    Transaction transaction(
        memberId,
        bookId,
        issueDate,
        dueDate
    );

    transactions.push_back(transaction);

    std::cout << "Book issued successfully.\n";
}

void Library::returnBook(
    int memberId,
    int bookId,
    const std::string& returnDate,
    int daysOverdue) {

    int memberIndex = findMemberIndex(memberId);

    if (memberIndex == -1) {
        throw MemberNotFoundException(__FUNCTION__, __LINE__);
    }

    int bookIndex = findBookIndex(bookId);

    if (bookIndex == -1) {
        throw BookNotFoundException(__FUNCTION__, __LINE__);
    }

    if (!members[memberIndex].hasBook(bookId)) {
        std::cout << "This member does not have this book.\n";
        return;
    }

    books[bookIndex].returnCopy();

    members[memberIndex].removeIssuedBook(bookId);

    issuedBooks.erase(bookId);

    for (auto& transaction : transactions) {

        if (transaction.getMemberId() == memberId &&
            transaction.getBookId() == bookId &&
            transaction.getReturnDate().empty()) {

            transaction.returnBook(
                returnDate,
                daysOverdue
            );

            std::cout << "Book returned successfully.\n";
            std::cout << "Fine: Rs. "
                      << transaction.getFineAmount()
                      << "\n";

            return;
        }
    }
}

void Library::viewOverdueBooks() const {

    bool found = false;

    std::cout << "\n========== TRANSACTIONS ==========\n";

    for (const auto& transaction : transactions) {

        if (transaction.getReturnDate().empty()) {

            transaction.displayInfo();

            found = true;
        }
    }

    if (!found) {
        std::cout << "No overdue/unreturned books.\n";
    }
}

void Library::saveData() const {

    std::ofstream bookFile("data/books.txt");

    if (!bookFile) {
        std::cout << "Unable to open books file.\n";
        return;
    }

    for (int i = 0; i < books.getSize(); i++) {
        bookFile << books[i] << "\n";
    }

    bookFile.close();

    std::ofstream memberFile("data/members.txt");

    if (!memberFile) {
        std::cout << "Unable to open members file.\n";
        return;
    }

    for (int i = 0; i < members.getSize(); i++) {
        memberFile << members[i] << "\n";
    }

    memberFile.close();

    std::ofstream transactionFile("data/transactions.txt");

    if (!transactionFile) {
        std::cout << "Unable to open transactions file.\n";
        return;
    }

    for (const auto& transaction : transactions) {
        transactionFile << transaction << "\n";
    }

    transactionFile.close();

    std::cout << "Data saved successfully.\n";
}

void Library::loadData() {

    std::ifstream bookFile("data/books.txt");

    if (bookFile) {

        books.clear();

        std::string line;

        while (std::getline(bookFile, line)) {

            if (line.empty()) {
                continue;
            }

            std::stringstream ss(line);

            std::string value;

            int id;
            std::string title;
            std::string author;
            std::string isbn;

            int genre;
            int status;
            int quantity;
            int available;

            std::getline(ss, value, '|');
            id = std::stoi(value);

            std::getline(ss, title, '|');
            std::getline(ss, author, '|');
            std::getline(ss, isbn, '|');

            std::getline(ss, value, '|');
            genre = std::stoi(value);

            std::getline(ss, value, '|');
            status = std::stoi(value);

            std::getline(ss, value, '|');
            quantity = std::stoi(value);

            std::getline(ss, value, '|');
            available = std::stoi(value);

            Book book(
                id,
                title,
                author,
                isbn,
                static_cast<BookGenre>(genre),
                quantity
            );

            for (int i = 0;
                 i < quantity - available;
                 i++) {

                book.issueCopy();
            }

            book.setStatus(
                static_cast<BookStatus>(status)
            );

            books.add(book);
        }

        bookFile.close();
    }

    std::ifstream memberFile("data/members.txt");

    if (memberFile) {

        members.clear();

        std::string line;

        while (std::getline(memberFile, line)) {

            if (line.empty()) {
                continue;
            }

            std::stringstream ss(line);

            std::string value;

            int id;
            std::string name;
            std::string phone;
            int issuedCount;

            std::getline(ss, value, '|');
            id = std::stoi(value);

            std::getline(ss, name, '|');
            std::getline(ss, phone, '|');

            std::getline(ss, value, '|');
            issuedCount = std::stoi(value);

            Member member(id, name, phone);

            for (int i = 0; i < issuedCount; i++) {

                std::getline(ss, value, '|');

                member.addIssuedBook(
                    std::stoi(value)
                );
            }

            members.add(member);
        }

        memberFile.close();
    }

    std::cout << "Data loaded successfully.\n";
}

void Library::demonstratePolymorphism() const {

    Member member(
        999,
        "Polymorphism Member",
        "9999999999"
    );

    Librarian librarian(
        1000,
        "Polymorphism Librarian",
        "8888888888",
        500,
        50000
    );

    Person* p1 = &member;
    Person* p2 = &librarian;

    std::cout << "\n========== RUNTIME POLYMORPHISM ==========\n";

    p1->displayInfo();
    p2->displayInfo();
}

}