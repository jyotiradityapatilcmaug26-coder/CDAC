#ifndef PERSON_H
#define PERSON_H

#include <iostream>
#include <string>

namespace LibrarySystem {

class Person {
protected:
    int id;
    std::string name;
    std::string phone;

public:
    Person();
    Person(int id, const std::string& name, const std::string& phone);

    virtual void displayInfo() const = 0;

    int getId() const;
    std::string getName() const;
    std::string getPhone() const;

    void setName(const std::string& name);
    void setPhone(const std::string& phone);

    virtual ~Person();
};

}

#endif