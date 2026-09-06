#include "../headers/Librarian.h"

namespace LibrarySystem {

Librarian::Librarian()
    : Person(), employeeId(0), salary(0.0) {
}

Librarian::Librarian(
    int id,
    const std::string& name,
    const std::string& phone,
    int employeeId,
    double salary)
    : Person(id, name, phone),
      employeeId(employeeId),
      salary(salary) {
}

void Librarian::displayInfo() const {

    std::cout << "\nLibrarian ID: " << id;
    std::cout << "\nName: " << name;
    std::cout << "\nPhone: " << phone;
    std::cout << "\nEmployee ID: " << employeeId;
    std::cout << "\nSalary: " << salary << "\n";
}

int Librarian::getEmployeeId() const {
    return employeeId;
}

double Librarian::getSalary() const {
    return salary;
}

void Librarian::setSalary(double salary) {
    this->salary = salary;
}

}