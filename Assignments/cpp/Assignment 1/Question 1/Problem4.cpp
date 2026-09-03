#include <iostream>
#include <cstdlib>
using namespace std;

int main(int argc, char *argv[])
{
    // Check for missing arguments
    if (argc < 4)
    {
        cout << "Usage   : ./sensor_monitor <warn_threshold> <critical_threshold> <num_readings>" << endl;
        cout << "Error   : Missing arguments." << endl;

        return 1;
    }

    // Convert command-line arguments
    int warn = stoi(argv[1]);
    int critical = stoi(argv[2]);
    int numReadings = stoi(argv[3]);

    // Validate arguments
    if (warn >= critical || numReadings < 1 || numReadings > 500)
    {
        cout << "Error   : Invalid arguments." << endl;
        return 1;
    }

    // Display configuration
    cout << "Config  : Warn=" << warn
         << "°C  Critical=" << critical
         << "°C  Readings=" << numReadings << endl;

    int normal = 0;
    int warning = 0;
    int criticalCount = 0;
    int shutdown = 0;

    // Generate and classify readings
    for (int i = 0; i < numReadings; i++)
    {
        int temperature = rand() % 70;

        if (temperature < warn)
        {
            normal++;
        }
        else if (temperature < critical)
        {
            warning++;
        }
        else if (temperature < 60)
        {
            criticalCount++;
        }
        else
        {
            shutdown++;
        }
    }

    // Display result
    cout << "Results : Normal:" << normal
         << "  Warning:" << warning
         << "  Critical:" << criticalCount
         << "  Shutdown:" << shutdown << endl;

    return 0;
}