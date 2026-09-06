#ifndef EXCEPTIONS_H
#define EXCEPTIONS_H

#include <exception>
#include <string>

namespace LibrarySystem {

class LibraryException : public std::exception {

protected:
    std::string message;
    std::string functionName;
    int lineNumber;

public:

    LibraryException(
        const std::string& message,
        const std::string& functionName,
        int lineNumber)
        : message(message),
          functionName(functionName),
          lineNumber(lineNumber) {
    }

    const char* what() const noexcept override {
        return message.c_str();
    }

    std::string getFunctionName() const {
        return functionName;
    }

    int getLineNumber() const {
        return lineNumber;
    }
};

class BookNotFoundException : public LibraryException {

public:

    BookNotFoundException(
        const std::string& functionName,
        int lineNumber)
        : LibraryException(
            "Book not found.",
            functionName,
            lineNumber) {
    }
};

class MemberNotFoundException : public LibraryException {

public:

    MemberNotFoundException(
        const std::string& functionName,
        int lineNumber)
        : LibraryException(
            "Member not found.",
            functionName,
            lineNumber) {
    }
};

class BookNotAvailableException : public LibraryException {

public:

    BookNotAvailableException(
        const std::string& functionName,
        int lineNumber)
        : LibraryException(
            "Book is not available.",
            functionName,
            lineNumber) {
    }
};

class MaxBooksExceededException : public LibraryException {

public:

    MaxBooksExceededException(
        const std::string& functionName,
        int lineNumber)
        : LibraryException(
            "Maximum number of books exceeded.",
            functionName,
            lineNumber) {
    }
};

}

#endif