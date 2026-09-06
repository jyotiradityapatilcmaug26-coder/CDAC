#ifndef LIBRARIAN_H
#define LIBRARIAN_H

#include "Person.h"

namespace LibrarySystem {

class Librarian : public Person {
private:
    int employeeId;
    double salary;

public:
    Librarian();
    Librarian(int id,
              const std::string& name,
              const std::string& phone,
              int employeeId,
              double salary);

    void displayInfo() const override;

    int getEmployeeId() const;
    double getSalary() const;

    void setSalary(double salary);
};

}

#endif