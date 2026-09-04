#include <iostream>
#include <string>
#include <stdexcept>
#include <iomanip>
#include <utility>

using namespace std;

class LedgerEntry {

private:
    string description;
    double* amounts;
    int days;

    static int totalEntries;

public:

    // Constructor
    LedgerEntry(const string& desc, int d) {
        description = desc;
        days = d;
        amounts = new double[days];

        for (int i = 0; i < days; i++) {
            amounts[i] = 0;
        }

        totalEntries++;

        cout << "[LedgerEntry Created] "
             << description << " (" << days << " days)" << endl;
    }

    // Copy Constructor
    LedgerEntry(const LedgerEntry& other) {
        description = other.description;
        days = other.days;

        if (days > 0) {
            amounts = new double[days];

            for (int i = 0; i < days; i++) {
                amounts[i] = other.amounts[i];
            }
        }
        else {
            amounts = nullptr;
        }

        totalEntries++;
    }

    // Move Constructor
    LedgerEntry(LedgerEntry&& other) noexcept {
        description = other.description;
        amounts = other.amounts;
        days = other.days;

        other.amounts = nullptr;
        other.days = 0;
        other.description = "";

        totalEntries++;

        cout << "[Move Constructor] Ownership transferred from: "
             << description << endl;
    }

    // Copy Assignment Operator
    LedgerEntry& operator=(const LedgerEntry& other) {

        if (this != &other) {

            delete[] amounts;

            description = other.description;
            days = other.days;

            if (days > 0) {
                amounts = new double[days];

                for (int i = 0; i < days; i++) {
                    amounts[i] = other.amounts[i];
                }
            }
            else {
                amounts = nullptr;
            }
        }

        return *this;
    }

    // Move Assignment Operator
    LedgerEntry& operator=(LedgerEntry&& other) noexcept {

        if (this != &other) {

            delete[] amounts;

            description = other.description;
            amounts = other.amounts;
            days = other.days;

            other.amounts = nullptr;
            other.days = 0;
            other.description = "";

            cout << "[Move Assignment] Ownership transferred from: "
                 << description << endl;
        }

        return *this;
    }

    // Destructor
    ~LedgerEntry() {

        delete[] amounts;
        totalEntries--;

        if (!description.empty()) {
            cout << "[Destructor] " << description
                 << " destroyed" << endl;
        }
    }

    // Addition operator
    LedgerEntry operator+(const LedgerEntry& other) const {

        if (days != other.days) {
            throw invalid_argument("Number of days must be same");
        }

        LedgerEntry result("Combined", days);

        for (int i = 0; i < days; i++) {
            result.amounts[i] = amounts[i] + other.amounts[i];
        }

        return result;
    }

    // Equality operator
    bool operator==(const LedgerEntry& other) const {

        if (days != other.days) {
            return false;
        }

        for (int i = 0; i < days; i++) {
            if (amounts[i] != other.amounts[i]) {
                return false;
            }
        }

        return true;
    }

    // Greater than operator
    bool operator>(const LedgerEntry& other) const {

        double total1 = 0;
        double total2 = 0;

        for (int i = 0; i < days; i++) {
            total1 += amounts[i];
        }

        for (int i = 0; i < other.days; i++) {
            total2 += other.amounts[i];
        }

        return total1 > total2;
    }

    // Subscript operator
    double& operator[](int index) {

        if (index < 0 || index >= days) {
            throw out_of_range("Invalid day index");
        }

        return amounts[index];
    }

    // Const subscript operator
    const double& operator[](int index) const {

        if (index < 0 || index >= days) {
            throw out_of_range("Invalid day index");
        }

        return amounts[index];
    }

    // Friend stream output operator
    friend ostream& operator<<(ostream& out, const LedgerEntry& entry);

    // Friend stream input operator
    friend istream& operator>>(istream& in, LedgerEntry& entry);

    // Static function
    static int getTotalEntries() {
        return totalEntries;
    }
};


// Static member definition
int LedgerEntry::totalEntries = 0;


// Output operator
ostream& operator<<(ostream& out, const LedgerEntry& entry) {

    out << entry.description << " : [";

    double total = 0;

    for (int i = 0; i < entry.days; i++) {

        out << fixed << setprecision(2)
            << entry.amounts[i];

        total += entry.amounts[i];

        if (i < entry.days - 1) {
            out << ", ";
        }
    }

    out << "] Total: "
        << fixed << setprecision(2)
        << total;

    return out;
}


// Input operator
istream& operator>>(istream& in, LedgerEntry& entry) {

    for (int i = 0; i < entry.days; i++) {
        in >> entry.amounts[i];
    }

    return in;
}


int main() {

    // Objective 1 - Constructor & deep copy

    LedgerEntry jan("January Sales", 5);

    jan[0] = 1200.50;
    jan[1] = 3400.00;
    jan[2] = 800.75;
    jan[3] = 2100.00;
    jan[4] = 650.25;


    LedgerEntry feb("February Sales", 5);

    feb[0] = 900.00;
    feb[1] = 2200.50;
    feb[2] = 1750.00;
    feb[3] = 3000.00;
    feb[4] = 475.50;


    cout << jan << endl;
    cout << feb << endl;


    // Objective 2 - Operator +

    LedgerEntry combined = jan + feb;

    cout << "Combined : " << combined << endl;


    // Objective 3 - Relational operators

    cout << "Jan == Feb : "
         << (jan == feb ? "Yes" : "No") << endl;

    cout << "Jan > Feb : "
         << (jan > feb ? "Yes" : "No") << endl;


    // Objective 4 - Move constructor

    LedgerEntry moved = move(jan);

    cout << "After move, jan.amounts is null: "
         << ((jan[0], false) ? "NO" : "YES") << endl;

    cout << "Moved entry : " << moved << endl;


    // Objective 5 - Move assignment

    LedgerEntry q1("Q1 Total", 5);

    q1 = move(feb);

    cout << "Q1 (moved from feb) : "
         << q1 << endl;


    // Objective 6 - Static member

    cout << "Live LedgerEntry objects: "
         << LedgerEntry::getTotalEntries() << endl;


    return 0;
}