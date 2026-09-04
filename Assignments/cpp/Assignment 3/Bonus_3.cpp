#include <iostream>
#include <cstring>
using namespace std;

class LogBuffer {
    char* buffer;
    int capacity;
    int size;

    static int instanceCount;

    mutable int accessCount = 0;

public:

    // Constructor
    LogBuffer(int capacity) {
        this->capacity = capacity;
        size = 0;

        buffer = new char[capacity];
        buffer[0] = '\0';

        instanceCount++;

        cout << "[LogBuffer Created] capacity=" << capacity << endl;
    }

    // Copy Constructor
    LogBuffer(const LogBuffer& other) {
        capacity = other.capacity;
        size = other.size;

        buffer = new char[capacity];
        strcpy(buffer, other.buffer);

        instanceCount++;

        cout << "[LogBuffer Deep Copied] capacity=" << capacity << endl;
    }

    // Copy Assignment Operator
    LogBuffer& operator=(const LogBuffer& other) {

        if (this == &other) {
            cout << "[Self-assignment detected — no operation]" << endl;
            return *this;
        }

        delete[] buffer;

        capacity = other.capacity;
        size = other.size;

        buffer = new char[capacity];
        strcpy(buffer, other.buffer);

        cout << "[LogBuffer Assigned]" << endl;

        return *this;
    }

    // Destructor
    ~LogBuffer() {
        delete[] buffer;
        instanceCount--;

        cout << "[LogBuffer Destroyed]" << endl;
    }

    // Append
    void append(const char* msg) {
        int len = strlen(msg);

        if (size + len < capacity) {
            strcpy(buffer + size, msg);
            size += len;
        }
    }

    // Const member function
    void print() const {
        accessCount++;
        cout << buffer << endl;
    }

    int getAccessCount() const {
        return accessCount;
    }

    // Clear
    void clear() {
        size = 0;
        buffer[0] = '\0';
    }

    // Static member function
    static int getInstanceCount() {
        return instanceCount;
    }
};

// Initialize static member
int LogBuffer::instanceCount = 0;


int main() {

    // Objective 1
    LogBuffer log1(256);

    log1.append("Server started on port 8080");
    log1.append(" | Request received from 192.168.1.10");

    log1.print();


    // Objective 2
    LogBuffer log2 = log1;

    log2.append(" | Cached response sent");

    cout << "log1 : ";
    log1.print();

    cout << "log2 : ";
    log2.print();


    // Objective 3
    LogBuffer log3(128);

    log3 = log1;

    cout << "log3 : ";
    log3.print();


    // Objective 4
    log1 = log1;

    log1.print();


    // Objective 5
    cout << "Live LogBuffer objects : "
         << LogBuffer::getInstanceCount() << endl;

    const LogBuffer readOnly(log1);

    readOnly.print();
    readOnly.print();
    readOnly.print();

    cout << "Times printed: "
         << readOnly.getAccessCount() << endl;


    return 0;
}