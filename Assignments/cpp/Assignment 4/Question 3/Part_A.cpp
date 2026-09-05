#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
#include <iomanip>
using namespace std;

class DataProcessor {
public:

    virtual void loadData(const string& source) = 0;

    virtual void processData() = 0;

    virtual void exportResult(const string& destination) = 0;

    virtual string processorType() const = 0;

    virtual int recordCount() const = 0;

    virtual void printSummary() const {
        cout << "Processor Type: " << processorType() << endl;
        cout << "Record Count: " << recordCount() << endl;
    }

    virtual ~DataProcessor() = default;
};


class CSVProcessor : public DataProcessor {
private:
    vector<string> records;

public:

    void loadData(const string& source) override {
        records.push_back("name,age,city");
        records.push_back("ramesh,25,pune");
        records.push_back("amit,28,mumbai");
        records.push_back("suresh,24,nashik");
        records.push_back("rahul,30,nagpur");
    }

    void processData() override {
        for (int i = 0; i < records.size(); i++) {
            for (int j = 0; j < records[i].length(); j++) {
                records[i][j] = toupper(records[i][j]);
            }
        }
    }

    void exportResult(const string& destination) override {
        cout << "[CSV EXPORT -> " << destination << "]" << endl;

        for (int i = 0; i < records.size(); i++) {
            cout << records[i] << endl;
        }
    }

    string processorType() const override {
        return "CSV Processor";
    }

    int recordCount() const override {
        return records.size();
    }
};


class SensorStreamProcessor : public DataProcessor {
private:
    vector<double> readings;
    double mean;
    double minimum;
    double maximum;

public:

    SensorStreamProcessor() {
        mean = 0;
        minimum = 0;
        maximum = 0;
    }

    void loadData(const string& source) override {
        readings.push_back(23.4);
        readings.push_back(21.8);
        readings.push_back(25.1);
        readings.push_back(24.6);
        readings.push_back(22.9);
        readings.push_back(26.3);
        readings.push_back(23.7);
        readings.push_back(24.2);
    }

    void processData() override {
        double sum = 0;

        minimum = readings[0];
        maximum = readings[0];

        for (int i = 0; i < readings.size(); i++) {
            sum = sum + readings[i];

            if (readings[i] < minimum)
                minimum = readings[i];

            if (readings[i] > maximum)
                maximum = readings[i];
        }

        mean = sum / readings.size();
    }

    void exportResult(const string& destination) override {
        cout << "[SENSOR EXPORT -> " << destination << "]" << endl;
        cout << "Mean: " << mean << endl;
        cout << "Min: " << minimum << endl;
        cout << "Max: " << maximum << endl;
    }

    string processorType() const override {
        return "Sensor Stream Processor";
    }

    int recordCount() const override {
        return readings.size();
    }
};


int main() {

    vector<DataProcessor*> pipeline;

    pipeline.push_back(new CSVProcessor());
    pipeline.push_back(new SensorStreamProcessor());

    for (auto* p : pipeline) {
        p->loadData("source_data");
        p->processData();
        p->printSummary();
        p->exportResult("output_dir");
        cout << "---" << endl;
    }

    // DataProcessor dp;
    // Error because DataProcessor is an abstract class.

    for (auto* p : pipeline) {
        delete p;
    }

    return 0;
}