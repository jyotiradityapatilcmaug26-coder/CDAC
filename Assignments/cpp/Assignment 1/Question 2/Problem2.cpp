#include <iostream>
#include <cmath>
#include <iomanip>
using namespace std;


// Calculate RMS
double computeRMS(double* signal, int n)
{
    double sum = 0;

    for (int i = 0; i < n; i++)
    {
        sum = sum + (*(signal + i) * *(signal + i));
    }

    return sqrt(sum / n);
}


// Normalise array
void normalise(double* signal, int n)
{
    double maxValue = fabs(*signal);

    for (int i = 1; i < n; i++)
    {
        if (fabs(*(signal + i)) > maxValue)
        {
            maxValue = fabs(*(signal + i));
        }
    }

    for (int i = 0; i < n; i++)
    {
        *(signal + i) = *(signal + i) / maxValue;
    }
}


// Count zero crossings
int countZeroCrossings(double* signal, int n)
{
    int count = 0;

    for (int i = 0; i < n - 1; i++)
    {
        if ((*(signal + i) < 0 && *(signal + i + 1) > 0) ||
            (*(signal + i) > 0 && *(signal + i + 1) < 0))
        {
            count++;
        }
    }

    return count;
}


// Apply gain
void applyGain(double* signal, int n, double gainFactor)
{
    for (int i = 0; i < n; i++)
    {
        *(signal + i) = *(signal + i) * gainFactor;
    }
}


int main()
{
    double signal[] = {
        0.5, -1.2, 0.8, -0.3,
        1.0, -0.9, 0.1
    };

    int n = 7;

    cout << fixed << setprecision(2);

    // Before processing
    cout << "Before : ";

    for (int i = 0; i < n; i++)
    {
        cout << *(signal + i) << " ";
    }

    cout << endl;


    // RMS
    double rms = computeRMS(signal, n);

    cout << "RMS : " << rms << endl;


    // Zero crossings
    int crossings = countZeroCrossings(signal, n);

    cout << "Zero Crossings : " << crossings << endl;


    // Normalise
    normalise(signal, n);

    cout << "After Normalise : ";

    for (int i = 0; i < n; i++)
    {
        cout << *(signal + i) << " ";
    }

    cout << endl;


    // Apply gain
    applyGain(signal, n, 2.0);

    cout << "After Gain : ";

    for (int i = 0; i < n; i++)
    {
        cout << *(signal + i) << " ";
    }

    cout << endl;


    return 0;
}