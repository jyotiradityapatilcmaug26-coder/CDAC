#include <iostream>
using namespace std;

int main()
{
    int statusReg = 0b10110001;
    int controlReg = 0b00000000;
    int dataReg = 0b11001010;

    // 1. const int* 
    // Pointer can change, but value cannot be changed through pointer
    const int* regPtr1 = &statusReg;

    cout << "Status Register : " << *regPtr1 << endl;

    // *regPtr1 = 10;      // ERROR: cannot modify value
    regPtr1 = &dataReg;   // OK: pointer can be changed


    // 2. int* const
    // Pointer cannot change, but value can be changed
    int* const regPtr2 = &controlReg;

    *regPtr2 = 10;        // OK: value can be changed

    cout << "Control Register : " << *regPtr2 << endl;

    // regPtr2 = &dataReg; // ERROR: cannot change pointer


    // 3. const int* const
    // Neither pointer nor value can be changed
    const int* const regPtr3 = &statusReg;

    cout << "ROM/Status Register : " << *regPtr3 << endl;

    // *regPtr3 = 10;      // ERROR: cannot modify value
    // regPtr3 = &dataReg; // ERROR: cannot change pointer

    return 0;
}