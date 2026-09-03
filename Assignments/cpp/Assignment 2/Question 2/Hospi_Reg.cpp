#include <iostream>
#include <string>
using namespace std;

class Patient
{
    int patientId;
    string name;
    int age;
    string ward;
    const string bloodGroup;

public:

    // Default constructor
    Patient()
        : patientId(0), name("Unknown"), age(0),
          ward("General"), bloodGroup("O+")
    {
        cout << "[Constructor] Default patient registered." << endl;
    }

    // Emergency admission
    Patient(int id, const string& n)
        : patientId(id), name(n), age(0),
          ward("Emergency"), bloodGroup("O+")
    {
        cout << "[Constructor] Emergency: " << name << endl;
    }

    // Full admission
    Patient(int id, const string& n, int a,
            const string& w, const string& bg)
        : patientId(id), name(n), age(a),
          ward(w), bloodGroup(bg)
    {
        cout << "[Constructor] Full admission: "
             << name << endl;
    }

    // Destructor
    ~Patient()
    {
        cout << "[Destructor] Patient "
             << name << " discharged." << endl;
    }

    void displayRecord() const
    {
        cout << "\nPatient Record:" << endl;
        cout << "ID : " << patientId << endl;
        cout << "Name : " << name << endl;
        cout << "Age : " << age << endl;
        cout << "Ward : " << ward << endl;
        cout << "Blood Grp : " << bloodGroup << endl;
    }

    void transferWard(const string& newWard)
    {
        cout << "Ward Transfer: " << name
             << " -> " << newWard << endl;

        ward = newWard;
    }
};

int main()
{
    // 3 stack objects using different constructors

    Patient p1(1001, "Meera", 34, "Cardiology", "B+");

    Patient p2(1002, "Raj");

    Patient p3;

    // Dynamic array of 4 patients
    Patient *patients = new Patient[4];

    // Display all 4 patients
    for(int i = 0; i < 4; i++)
    {
        patients[i].displayRecord();
    }

    // Transfer ward
    p2.transferWard("ICU");

    // Delete dynamic array
    delete[] patients;

    cout << "\nEnd of main()" << endl;

    return 0;
}

