#include <iostream>
#include <string>

using namespace std;


// Base class
class Vehicle {

protected:
    string registrationNo;
    string ownerName;
    int yearOfManufacture;
    double kmDriven;

public:

    Vehicle(const string& reg, const string& owner, int year, double km) {
        registrationNo = reg;
        ownerName = owner;
        yearOfManufacture = year;
        kmDriven = km;

        cout << "[Vehicle Constructor] "
             << registrationNo << " : "
             << ownerName << endl;
    }

    virtual double fuelCost(double kmToTravel) const = 0;

    virtual void describe() const {
        cout << "Registration No: " << registrationNo << endl;
        cout << "Owner Name: " << ownerName << endl;
        cout << "Year: " << yearOfManufacture << endl;
        cout << "KM Driven: " << kmDriven << endl;
    }

    virtual string vehicleType() const = 0;

    virtual ~Vehicle() {
        cout << "[Vehicle Destructor] "
             << registrationNo << endl;
    }

    double getKmDriven() const {
        return kmDriven;
    }

    string getRegNo() const {
        return registrationNo;
    }
};


// Truck
class Truck : public Vehicle {

protected:
    double payloadCapacityTons;
    double fuelEfficiencyKmpl;

public:

    Truck(const string& reg, const string& owner, int year,
          double km, double payload, double efficiency)
        : Vehicle(reg, owner, year, km) {

        payloadCapacityTons = payload;
        fuelEfficiencyKmpl = efficiency;

        cout << "[Truck Constructor] "
             << registrationNo << endl;
    }

    double fuelCost(double kmToTravel) const override {

        double efficiency = fuelEfficiencyKmpl;

        efficiency = efficiency *
                     (1 - (0.05 * payloadCapacityTons));

        return (kmToTravel / efficiency) * 93.00;
    }

    string vehicleType() const override {
        return "Truck";
    }

    void describe() const override {
        Vehicle::describe();
    }

    ~Truck() {
        cout << "[Truck Destructor] "
             << registrationNo << endl;
    }
};


// ElectricTruck
class ElectricTruck : public Truck {

private:
    double batteryCapacityKWh;
    double rangePerChargeKm;

public:

    ElectricTruck(const string& reg, const string& owner,
                  int year, double km, double payload,
                  double efficiency, double battery,
                  double range)
        : Truck(reg, owner, year, km, payload, efficiency) {

        batteryCapacityKWh = battery;
        rangePerChargeKm = range;

        cout << "[ElectricTruck Constructor] "
             << registrationNo << endl;
    }

    double fuelCost(double kmToTravel) const override {

        return (kmToTravel / rangePerChargeKm)
               * batteryCapacityKWh * 9.50;
    }

    string vehicleType() const override {
        return "Electric Truck";
    }

    void describe() const override {
        Truck::describe();

        cout << "Battery Capacity: "
             << batteryCapacityKWh << " kWh" << endl;

        cout << "Range Per Charge: "
             << rangePerChargeKm << " km" << endl;
    }

    ~ElectricTruck() {
        cout << "[ElectricTruck Destructor] "
             << registrationNo << endl;
    }
};


int main() {

    ElectricTruck et(
        "V-ET001",
        "Green Logistics",
        2024,
        50000,
        10,
        5,
        100,
        400
    );

    cout << "...scope ends..." << endl;

    return 0;
}