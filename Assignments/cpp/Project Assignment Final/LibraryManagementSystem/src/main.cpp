#include "../headers/Library.h"
#include "../headers/Librarian.h"

#include <iostream>
#include <limits>

using namespace LibrarySystem;

void clearInput() {

    std::cin.clear();

    std::cin.ignore(
        std::numeric_limits<std::streamsize>::max(),
        '\n'
    );
}

void showMenu() {

    std::cout << "\n";
    std::cout << "========================================\n";
    std::cout << "       LIBRARY MANAGEMENT SYSTEM\n";
    std::cout << "========================================\n";

    std::cout << "1.  Add New Book\n";
    std::cout << "2.  Remove Book\n";
    std::cout << "3.  Search Book\n";
    std::cout << "4.  Display All Books\n";
    std::cout << "5.  Register Member\n";
    std::cout << "6.  Remove Member\n";
    std::cout << "7.  Search Member\n";
    std::cout << "8.  Display All Members\n";
    std::cout << "9.  Issue Book\n";
    std::cout << "10. Return Book\n";
    std::cout << "11. View Overdue Books\n";
    std::cout << "12. Save Data\n";
    std::cout << "13. Load Data\n";
    std::cout << "14. Test Runtime Polymorphism\n";
    std::cout << "0.  Exit\n";

    std::cout << "========================================\n";
    std::cout << "Enter choice: ";
}

BookGenre selectGenre() {

    int choice;

    std::cout << "\nSelect Genre:\n";
    std::cout << "1. Fiction\n";
    std::cout << "2. Non-Fiction\n";
    std::cout << "3. Science\n";
    std::cout << "4. Technology\n";
    std::cout << "5. History\n";
    std::cout << "6. Other\n";

    std::cout << "Choice: ";
    std::cin >> choice;

    switch (choice) {

        case 1:
            return BookGenre::FICTION;

        case 2:
            return BookGenre::NON_FICTION;

        case 3:
            return BookGenre::SCIENCE;

        case 4:
            return BookGenre::TECHNOLOGY;

        case 5:
            return BookGenre::HISTORY;

        default:
            return BookGenre::OTHER;
    }
}

int main() {

    Library library;

    int choice;

    do {

        showMenu();

        std::cin >> choice;

        if (std::cin.fail()) {
            clearInput();
            continue;
        }

        try {

            switch (choice) {

                // --------------------------------
                // ADD BOOK
                // --------------------------------
                case 1: {

                    int id;
                    std::string title;
                    std::string author;
                    std::string isbn;
                    int quantity;

                    std::cout << "\nEnter Book ID: ";
                    std::cin >> id;

                    clearInput();

                    std::cout << "Enter Title: ";
                    std::getline(std::cin, title);

                    std::cout << "Enter Author: ";
                    std::getline(std::cin, author);

                    std::cout << "Enter ISBN: ";
                    std::getline(std::cin, isbn);

                    BookGenre genre = selectGenre();

                    std::cout << "Enter Quantity: ";
                    std::cin >> quantity;

                    Book book(
                        id,
                        title,
                        author,
                        isbn,
                        genre,
                        quantity
                    );

                    library.addBook(book);

                    break;
                }

                // --------------------------------
                // REMOVE BOOK
                // --------------------------------
                case 2: {

                    int id;

                    std::cout << "Enter Book ID: ";
                    std::cin >> id;

                    library.removeBook(id);

                    break;
                }

                // --------------------------------
                // SEARCH BOOK
                // --------------------------------
                case 3: {

                    int id;

                    std::cout << "Enter Book ID: ";
                    std::cin >> id;

                    library.searchBook(id);

                    break;
                }

                // --------------------------------
                // DISPLAY BOOKS
                // --------------------------------
                case 4:

                    library.displayAllBooks();

                    break;

                // --------------------------------
                // REGISTER MEMBER
                // --------------------------------
                case 5: {

                    int id;
                    std::string name;
                    std::string phone;

                    std::cout << "Enter Member ID: ";
                    std::cin >> id;

                    clearInput();

                    std::cout << "Enter Name: ";
                    std::getline(std::cin, name);

                    std::cout << "Enter Phone: ";
                    std::getline(std::cin, phone);

                    Member member(
                        id,
                        name,
                        phone
                    );

                    library.registerMember(member);

                    break;
                }

                // --------------------------------
                // REMOVE MEMBER
                // --------------------------------
                case 6: {

                    int id;

                    std::cout << "Enter Member ID: ";
                    std::cin >> id;

                    library.removeMember(id);

                    break;
                }

                // --------------------------------
                // SEARCH MEMBER
                // --------------------------------
                case 7: {

                    int id;

                    std::cout << "Enter Member ID: ";
                    std::cin >> id;

                    library.searchMember(id);

                    break;
                }

                // --------------------------------
                // DISPLAY MEMBERS
                // --------------------------------
                case 8:

                    library.displayAllMembers();

                    break;

                // --------------------------------
                // ISSUE BOOK
                // --------------------------------
                case 9: {

                    int memberId;
                    int bookId;

                    std::string issueDate;
                    std::string dueDate;

                    std::cout << "Enter Member ID: ";
                    std::cin >> memberId;

                    std::cout << "Enter Book ID: ";
                    std::cin >> bookId;

                    clearInput();

                    std::cout << "Enter Issue Date (DD-MM-YYYY): ";
                    std::getline(std::cin, issueDate);

                    std::cout << "Enter Due Date (DD-MM-YYYY): ";
                    std::getline(std::cin, dueDate);

                    library.issueBook(
                        memberId,
                        bookId,
                        issueDate,
                        dueDate
                    );

                    break;
                }

                // --------------------------------
                // RETURN BOOK
                // --------------------------------
                case 10: {

                    int memberId;
                    int bookId;
                    int daysOverdue;

                    std::string returnDate;

                    std::cout << "Enter Member ID: ";
                    std::cin >> memberId;

                    std::cout << "Enter Book ID: ";
                    std::cin >> bookId;

                    clearInput();

                    std::cout << "Enter Return Date (DD-MM-YYYY): ";
                    std::getline(std::cin, returnDate);

                    std::cout << "Enter Days Overdue: ";
                    std::cin >> daysOverdue;

                    library.returnBook(
                        memberId,
                        bookId,
                        returnDate,
                        daysOverdue
                    );

                    break;
                }

                // --------------------------------
                // OVERDUE
                // --------------------------------
                case 11:

                    library.viewOverdueBooks();

                    break;

                // --------------------------------
                // SAVE
                // --------------------------------
                case 12:

                    library.saveData();

                    break;

                // --------------------------------
                // LOAD
                // --------------------------------
                case 13:

                    library.loadData();

                    break;

                // --------------------------------
                // POLYMORPHISM
                // --------------------------------
                case 14:

                    library.demonstratePolymorphism();

                    break;

                // --------------------------------
                // EXIT
                // --------------------------------
                case 0:

                    std::cout << "\nExiting program...\n";

                    break;

                default:

                    std::cout << "Invalid choice.\n";
            }

        }
        catch (const LibraryException& e) {

            std::cout << "\nERROR: "
                      << e.what()
                      << "\nFunction: "
                      << e.getFunctionName()
                      << "\nLine: "
                      << e.getLineNumber()
                      << "\n";
        }
        catch (const std::exception& e) {

            std::cout << "\nStandard Exception: "
                      << e.what()
                      << "\n";
        }

    } while (choice != 0);

    return 0;
}