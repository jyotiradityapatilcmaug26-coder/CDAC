#include <iostream>
#include <string>
using namespace std;

class Product
{
    int productId;
    string name;
    double price;
    int quantity;

public:

    void acceptDetails()
    {
        cout << "Enter Product ID: ";
        cin >> productId;

        cout << "Enter Name: ";
        cin >> name;

        cout << "Enter Price: ";
        cin >> price;

        cout << "Enter Quantity: ";
        cin >> quantity;
    }

    double totalValue() const
    {
        return price * quantity;
    }

    bool isLowStock(int threshold) const
    {
        if(quantity < threshold)
            return true;
        else
            return false;
    }

    void displayDetails() const
    {
        cout << productId << "\t"
             << name << "\t"
             << price << "\t"
             << quantity << "\t"
             << totalValue() << endl;
    }

    string getName() const
    {
        return name;
    }
};

int main()
{
    Product products[5];

    for(int i = 0; i < 5; i++)
    {
        cout << "\nEnter details of Product " << i + 1 << endl;
        products[i].acceptDetails();
    }

    cout << "\n===== INVENTORY REPORT =====" << endl;
    cout << "ID\tName\tPrice\tQty\tTotal Value" << endl;

    for(int i = 0; i < 5; i++)
    {
        products[i].displayDetails();
    }

    double highest = products[0].totalValue();
    int index = 0;

    for(int i = 1; i < 5; i++)
    {
        if(products[i].totalValue() > highest)
        {
            highest = products[i].totalValue();
            index = i;
        }
    }

    cout << "\nHighest Value Product : "
         << products[index].getName()
         << " (Rs. " << highest << ")" << endl;

    int threshold;

    cout << "\nEnter Low Stock Threshold: ";
    cin >> threshold;

    cout << "\nLow Stock Products: ";

    for(int i = 0; i < 5; i++)
    {
        if(products[i].isLowStock(threshold))
        {
            cout << products[i].getName() << " ";
        }
    }

    cout << endl;

    return 0;
}

