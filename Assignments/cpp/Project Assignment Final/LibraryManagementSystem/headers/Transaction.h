#ifndef TRANSACTION_H
#define TRANSACTION_H

#include <iostream>
#include <string>

namespace LibrarySystem {

class Transaction {

private:
    static int nextTransactionId;

    int transactionId;
    int memberId;
    int bookId;

    std::string issueDate;
    std::string dueDate;
    std::string returnDate;

    double fineAmount;

public:

    static const double FINE_RATE_PER_DAY;

    Transaction();

    Transaction(
        int memberId,
        int bookId,
        const std::string& issueDate,
        const std::string& dueDate);

    int getTransactionId() const;
    int getMemberId() const;
    int getBookId() const;

    std::string getIssueDate() const;
    std::string getDueDate() const;
    std::string getReturnDate() const;

    double getFineAmount() const;

    void returnBook(
        const std::string& returnDate,
        int daysOverdue);

    void displayInfo() const;

    friend std::ostream& operator<<(
        std::ostream& os,
        const Transaction& transaction);
};

}

#endif