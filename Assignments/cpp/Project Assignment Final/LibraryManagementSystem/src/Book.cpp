#include "../headers/Book.h"

namespace LibrarySystem {

Book::Book()
    : bookId(0),
      title(""),
      author(""),
      isbn(""),
      status(BookStatus::AVAILABLE),
      genre(BookGenre::OTHER),
      quantity(0),
      availableCopies(0) {
}

Book::Book(
    int bookId,
    const std::string& title,
    const std::string& author,
    const std::string& isbn,
    BookGenre genre,
    int quantity)
    : bookId(bookId),
      title(title),
      author(author),
      isbn(isbn),
      status(BookStatus::AVAILABLE),
      genre(genre),
      quantity(quantity),
      availableCopies(quantity) {
}

int Book::getBookId() const {
    return bookId;
}

std::string Book::getTitle() const {
    return title;
}

std::string Book::getAuthor() const {
    return author;
}

std::string Book::getISBN() const {
    return isbn;
}

BookStatus Book::getStatus() const {
    return status;
}

BookGenre Book::getGenre() const {
    return genre;
}

int Book::getQuantity() const {
    return quantity;
}

int Book::getAvailableCopies() const {
    return availableCopies;
}

void Book::setStatus(BookStatus status) {
    this->status = status;
}

bool Book::issueCopy() {

    if (availableCopies <= 0) {
        return false;
    }

    availableCopies--;

    if (availableCopies == 0) {
        status = BookStatus::ISSUED;
    }

    return true;
}

bool Book::returnCopy() {

    if (availableCopies >= quantity) {
        return false;
    }

    availableCopies++;

    if (availableCopies > 0) {
        status = BookStatus::AVAILABLE;
    }

    return true;
}

// Pre-increment
Book& Book::operator++() {

    quantity++;
    availableCopies++;

    status = BookStatus::AVAILABLE;

    return *this;
}

// Post-increment
Book Book::operator++(int) {

    Book temp = *this;

    ++(*this);

    return temp;
}

// Decrement
Book& Book::operator--() {

    if (quantity > 0) {

        quantity--;

        if (availableCopies > quantity) {
            availableCopies = quantity;
        }

        if (availableCopies == 0) {
            status = BookStatus::ISSUED;
        }
    }

    return *this;
}

bool Book::operator==(const Book& other) const {
    return bookId == other.bookId;
}

bool Book::operator<(const Book& other) const {
    return bookId < other.bookId;
}

// [] operator
std::string Book::operator[](int index) const {

    switch (index) {

        case 0:
            return std::to_string(bookId);

        case 1:
            return title;

        case 2:
            return author;

        case 3:
            return isbn;

        case 4:
            return std::to_string(quantity);

        case 5:
            return std::to_string(availableCopies);

        default:
            return "Invalid index";
    }
}

void Book::displayInfo() const {

    std::cout << "\nBook ID: " << bookId;
    std::cout << "\nTitle: " << title;
    std::cout << "\nAuthor: " << author;
    std::cout << "\nISBN: " << isbn;

    std::cout << "\nGenre: ";

    switch (genre) {

        case BookGenre::FICTION:
            std::cout << "Fiction";
            break;

        case BookGenre::NON_FICTION:
            std::cout << "Non-Fiction";
            break;

        case BookGenre::SCIENCE:
            std::cout << "Science";
            break;

        case BookGenre::TECHNOLOGY:
            std::cout << "Technology";
            break;

        case BookGenre::HISTORY:
            std::cout << "History";
            break;

        default:
            std::cout << "Other";
    }

    std::cout << "\nStatus: ";

    if (status == BookStatus::AVAILABLE) {
        std::cout << "AVAILABLE";
    }
    else {
        std::cout << "ISSUED";
    }

    std::cout << "\nQuantity: " << quantity;
    std::cout << "\nAvailable Copies: " << availableCopies;
    std::cout << "\n";
}

std::ostream& operator<<(std::ostream& os, const Book& book) {

    os << book.bookId << "|"
       << book.title << "|"
       << book.author << "|"
       << book.isbn << "|"
       << static_cast<int>(book.genre) << "|"
       << static_cast<int>(book.status) << "|"
       << book.quantity << "|"
       << book.availableCopies;

    return os;
}

std::istream& operator>>(std::istream& is, Book& book) {

    is >> book.bookId
       >> book.title
       >> book.author
       >> book.isbn;

    return is;
}

}