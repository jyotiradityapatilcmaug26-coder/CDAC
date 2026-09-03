#include <iostream>
using namespace std;

// V1 - Call by Value
void resetSensorPairV1(int reading1, int reading2)
{
    int temp = reading1;
    reading1 = reading2;
    reading2 = temp;
}

// V2 - Call by Reference
void resetSensorPairV2(int& reading1, int& reading2)
{
    int temp = reading1;
    reading1 = reading2;
    reading2 = temp;
}

// V3 - Call by Pointer
void resetSensorPairV3(int* reading1, int* reading2)
{
    int temp = *reading1;
    *reading1 = *reading2;
    *reading2 = temp;
}

int main()
{
    int A = 55;
    int B = 12;

    // V1
    cout << "--- V1: Call by Value ---" << endl;
    cout << "Before : A=" << A << " B=" << B << endl;

    resetSensorPairV1(A, B);

    cout << "After  : A=" << A << " B=" << B << endl;


    // V2
    cout << "--- V2: Call by Reference ---" << endl;
    cout << "Before : A=" << A << " B=" << B << endl;

    resetSensorPairV2(A, B);

    cout << "After  : A=" << A << " B=" << B << endl;


    // V3
    cout << "--- V3: Call by Pointer ---" << endl;
    cout << "Before : A=" << A << " B=" << B << endl;

    resetSensorPairV3(&A, &B);

    cout << "After  : A=" << A << " B=" << B << endl;


    /*
       V1 uses Call by Value.
       It receives copies of A and B.
       The copies are swapped inside the function.
       The original A and B remain unchanged.
       Therefore, V1 does not swap the original values.
    */

    return 0;
}