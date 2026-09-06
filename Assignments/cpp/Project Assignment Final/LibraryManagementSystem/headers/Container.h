#ifndef CONTAINER_H
#define CONTAINER_H

#include <iostream>
#include <string>

namespace LibrarySystem {

template <typename T>
class Container {

private:
    T* data;
    int size;
    int capacity;

    void resize() {

        capacity *= 2;

        T* temp = new T[capacity];

        for (int i = 0; i < size; i++) {
            temp[i] = data[i];
        }

        delete[] data;

        data = temp;
    }

public:

    Container(int initialCapacity = 5)
        : size(0), capacity(initialCapacity) {

        data = new T[capacity];
    }

    // Copy constructor
    Container(const Container& other)
        : size(other.size),
          capacity(other.capacity) {

        data = new T[capacity];

        for (int i = 0; i < size; i++) {
            data[i] = other.data[i];
        }
    }

    // Assignment operator
    Container& operator=(const Container& other) {

        if (this != &other) {

            delete[] data;

            size = other.size;
            capacity = other.capacity;

            data = new T[capacity];

            for (int i = 0; i < size; i++) {
                data[i] = other.data[i];
            }
        }

        return *this;
    }

    ~Container() {
        delete[] data;
    }

    void add(const T& item) {

        if (size >= capacity) {
            resize();
        }

        data[size++] = item;
    }

    bool remove(int index) {

        if (index < 0 || index >= size) {
            return false;
        }

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        size--;

        return true;
    }

    T& operator[](int index) {
        return data[index];
    }

    const T& operator[](int index) const {
        return data[index];
    }

    int getSize() const {
        return size;
    }

    void clear() {
        size = 0;
    }

    // Find by ID
    int find(int id) const {

        for (int i = 0; i < size; i++) {

            if (data[i].getId() == id) {
                return i;
            }
        }

        return -1;
    }

    // Find by name
    int find(const std::string& name) const {

        for (int i = 0; i < size; i++) {

            if (data[i].getName() == name) {
                return i;
            }
        }

        return -1;
    }
};

}

#endif