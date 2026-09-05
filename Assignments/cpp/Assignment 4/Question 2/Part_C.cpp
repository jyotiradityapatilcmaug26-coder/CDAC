#include <iostream>
#include <string>
#include <vector>
#include <iomanip>

using namespace std;


// Base class
class Vehicle {

protected:
    string registrationNo;
    string ownerName;
    int yearOfManufacture;
    double kmDriven;

public:

    Vehicle(const string& reg, const string& owner,
            int year, double km) {

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

    string getOwnerName() const {
        return ownerName;
    }
};


// Car
class Car : public Vehicle {

private:
    string fuelType;
    double mileageKmpl;

public:

    Car(const string& reg, const string& owner,
        int year, double km, const string& fuel,
        double mileage)
        : Vehicle(reg, owner, year, km) {

        fuelType = fuel;
        mileageKmpl = mileage;

        cout << "[Car Constructor] "
             << registrationNo << endl;
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

    ~Car() {
        cout << "[Car Destructor] "
             << registrationNo << endl;
    }
};


// Truck
class Truck : public Vehicle {

protected:
    double payloadCapacityTons;
    double fuelEfficiencyKmpl;

public:

    Truck(const string& reg, const string& owner,
          int year, double km, double payload,
          double efficiency)
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

        cout << "Payload Capacity: "
             << payloadCapacityTons << " tons" << endl;

        cout << "Fuel Efficiency: "
             << fuelEfficiencyKmpl << " km/l" << endl;
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


// Van
class Van : public Vehicle {

private:
    int seatingCapacity;
    double mileageKmpl;

public:

    Van(const string& reg, const string& owner,
        int year, double km, int seating,
        double mileage)
        : Vehicle(reg, owner, year, km) {

        seatingCapacity = seating;
        mileageKmpl = mileage;

        cout << "[Van Constructor] "
             << registrationNo << endl;
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

    ~Van() {
        cout << "[Van Destructor] "
             << registrationNo << endl;
    }
};


// Fleet Report
void printFleetReport(const vector<Vehicle*>& fleet,
                      double tripKm) {

    cout << endl;
    cout << "===== FLEET REPORT - Trip Distance: "
         << tripKm << " km =====" << endl;

    cout << left
         << setw(12) << "Reg"
         << setw(20) << "Type"
         << setw(22) << "Owner"
         << "Km Driven" << endl;

    cout << "--------------------------------------------------------"
         << endl;

    for (Vehicle* v : fleet) {

        cout << left
             << setw(12) << v->getRegNo()
             << setw(20) << v->vehicleType()
             << setw(22) << v->getOwnerName()
             << v->getKmDriven() << endl;
    }


    cout << endl;
    cout << "===== FUEL / CHARGE COST ESTIMATE =====" << endl;

    double lowestCost = 999999999;
    Vehicle* efficientVehicle = nullptr;

    for (Vehicle* v : fleet) {

        double cost = v->fuelCost(tripKm);

        cout << v->getRegNo()
             << " (" << v->vehicleType() << ") : Rs. "
             << fixed << setprecision(2)
             << cost;

        // dynamic_cast
        ElectricTruck* electric =
            dynamic_cast<ElectricTruck*>(v);

        if (electric != nullptr) {
            cout << " [Electric Truck]";
        }

        cout << endl;

        if (cost < lowestCost) {
            lowestCost = cost;
            efficientVehicle = v;
        }
    }


    cout << endl;

    if (efficientVehicle != nullptr) {

        cout << "Most Efficient Vehicle: "
             << efficientVehicle->getRegNo()
             << " (" << efficientVehicle->vehicleType()
             << ") - Rs. "
             << fixed << setprecision(2)
             << lowestCost
             << " for " << tripKm << " km" << endl;
    }
}


int main() {

    vector<Vehicle*> fleet;


    // 1. Car
    fleet.push_back(
        new Car("KA01AA001",
                "Ramesh Kumar",
                2020,
                45200,
                "Petrol",
                18.75)
    );


    // 2. Truck
    fleet.push_back(
        new Truck("MH04BB002",
                  "Shyam Logistics",
                  2019,
                  123500,
                  8,
                  4.5)
    );


    // 3. Electric Truck
    fleet.push_back(
        new ElectricTruck("GJ07CC003",
                           "Green Fleet Co",
                           2023,
                           89000,
                           5,
                           4,
                           85,
                           400)
    );


    // 4. Van
    fleet.push_back(
        new Van("MH12DD004",
                "City Transport",
                2021,
                67800,
                12,
                15)
    );


    // 5. Another Car
    fleet.push_back(
        new Car("DL05EE005",
                "Amit Sharma",
                2022,
                34500,
                "Diesel",
                20)
    );


    // Print report
    printFleetReport(fleet, 200);


    // Cleanup
    cout << endl;
    cout << "===== CLEANUP =====" << endl;

    for (Vehicle* v : fleet) {
        delete v;
    }

    return 0;
}