#ifndef BOOK_H
#define BOOK_H

#include <iostream>
#include <string>

namespace LibrarySystem {

enum class BookStatus {
    AVAILABLE,
    ISSUED
};

enum class BookGenre {
    FICTION,
    NON_FICTION,
    SCIENCE,
    TECHNOLOGY,
    HISTORY,
    OTHER
};

class Book {
private:
    int bookId;
    std::string title;
    std::string author;
    std::string isbn;

    BookStatus status;
    BookGenre genre;

    int quantity;
    int availableCopies;

public:
    Book();

    Book(
        int bookId,
        const std::string& title,
        const std::string& author,
        const std::string& isbn,
        BookGenre genre,
        int quantity
    );

    int getBookId() const;
    std::string getTitle() const;
    std::string getAuthor() const;
    std::string getISBN() const;

    BookStatus getStatus() const;
    BookGenre getGenre() const;

    int getQuantity() const;
    int getAvailableCopies() const;

    void setStatus(BookStatus status);

    bool issueCopy();
    bool returnCopy();

    void displayInfo() const;

    // Operators
    Book& operator++();       // pre-increment
    Book operator++(int);     // post-increment

    Book& operator--();

    bool operator==(const Book& other) const;
    bool operator<(const Book& other) const;

    std::string operator[](int index) const;

    friend std::ostream& operator<<(std::ostream& os, const Book& book);
    friend std::istream& operator>>(std::istream& is, Book& book);
};

}

#endif