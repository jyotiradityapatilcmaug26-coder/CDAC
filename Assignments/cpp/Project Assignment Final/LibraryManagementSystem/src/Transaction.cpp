#include "../headers/Transaction.h"

namespace LibrarySystem {

int Transaction::nextTransactionId = 1;

const double Transaction::FINE_RATE_PER_DAY = 5.0;

Transaction::Transaction()
    : transactionId(nextTransactionId++),
      memberId(0),
      bookId(0),
      issueDate(""),
      dueDate(""),
      returnDate(""),
      fineAmount(0.0) {
}

Transaction::Transaction(
    int memberId,
    int bookId,
    const std::string& issueDate,
    const std::string& dueDate)
    : transactionId(nextTransactionId++),
      memberId(memberId),
      bookId(bookId),
      issueDate(issueDate),
      dueDate(dueDate),
      returnDate(""),
      fineAmount(0.0) {
}

int Transaction::getTransactionId() const {
    return transactionId;
}

int Transaction::getMemberId() const {
    return memberId;
}

int Transaction::getBookId() const {
    return bookId;
}

std::string Transaction::getIssueDate() const {
    return issueDate;
}

std::string Transaction::getDueDate() const {
    return dueDate;
}

std::string Transaction::getReturnDate() const {
    return returnDate;
}

double Transaction::getFineAmount() const {
    return fineAmount;
}

void Transaction::returnBook(
    const std::string& returnDate,
    int daysOverdue) {

    this->returnDate = returnDate;

    if (daysOverdue > 0) {
        fineAmount = daysOverdue * FINE_RATE_PER_DAY;
    }
    else {
        fineAmount = 0.0;
    }
}

void Transaction::displayInfo() const {

    std::cout << "\nTransaction ID: " << transactionId;
    std::cout << "\nMember ID: " << memberId;
    std::cout << "\nBook ID: " << bookId;
    std::cout << "\nIssue Date: " << issueDate;
    std::cout << "\nDue Date: " << dueDate;
    std::cout << "\nReturn Date: " << returnDate;
    std::cout << "\nFine: Rs. " << fineAmount;
    std::cout << "\n";
}

std::ostream& operator<<(
    std::ostream& os,
    const Transaction& transaction) {

    os << transaction.transactionId << "|"
       << transaction.memberId << "|"
       << transaction.bookId << "|"
       << transaction.issueDate << "|"
       << transaction.dueDate << "|"
       << transaction.returnDate << "|"
       << transaction.fineAmount;

    return os;
}

}