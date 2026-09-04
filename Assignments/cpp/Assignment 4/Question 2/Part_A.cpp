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
    }

    double getKmDriven() const {
        return kmDriven;
    }

    string getRegNo() const {
        return registrationNo;
    }
};


// Car
class Car : public Vehicle {

private:
    string fuelType;
    double mileageKmpl;

public:

    Car(const string& reg, const string& owner, int year,
        double km, const string& fuel, double mileage)
        : Vehicle(reg, owner, year, km) {

        fuelType = fuel;
        mileageKmpl = mileage;
    }

    double fuelCost(double kmToTravel) const override {

        double fuelPrice;

        if (fuelType == "Petrol") {
            fuelPrice = 106.00;
        }
        else {
            fuelPrice = 93.00;
        }

        return (kmToTravel / mileageKmpl) * fuelPrice;
    }

    string vehicleType() const override {
        return "Car";
    }

    void describe() const override {

        Vehicle::describe();

        cout << "Fuel Type: " << fuelType << endl;
        cout << "Mileage: " << mileageKmpl << " km/l" << endl;
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
    }

    double fuelCost(double kmToTravel) const override {

        double efficiency = fuelEfficiencyKmpl;

        // Efficiency drops 5% per ton carried
        efficiency = efficiency * (1 - (0.05 * payloadCapacityTons));

        return (kmToTravel / efficiency) * 93.00;
    }

    string vehicleType() const override {
        return "Truck";
    }

    void describe() const override {

        Vehicle::describe();

        cout << "Payload Capacity: "
             << payloadCapacityTons << " tons" << endl;

        cout << "Fuel Efficiency: "
             << fuelEfficiencyKmpl << " km/l" << endl;
    }
};


// ElectricTruck
class ElectricTruck : public Truck {

private:
    double batteryCapacityKWh;
    double rangePerChargeKm;

public:

    ElectricTruck(const string& reg, const string& owner, int year,
                  double km, double payload, double efficiency,
                  double battery, double range)
        : Truck(reg, owner, year, km, payload, efficiency) {

        batteryCapacityKWh = battery;
        rangePerChargeKm = range;
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
};


// Van
class Van : public Vehicle {

private:
    int seatingCapacity;
    double mileageKmpl;

public:

    Van(const string& reg, const string& owner, int year,
        double km, int seating, double mileage)
        : Vehicle(reg, owner, year, km) {

        seatingCapacity = seating;
        mileageKmpl = mileage;
    }

    double fuelCost(double kmToTravel) const override {

        return (kmToTravel / mileageKmpl) * 106.00;
    }

    string vehicleType() const override {
        return "Van";
    }

    void describe() const override {

        Vehicle::describe();

        cout << "Seating Capacity: "
             << seatingCapacity << endl;

        cout << "Mileage: "
             << mileageKmpl << " km/l" << endl;
    }
};


int main() {

    return 0;
}