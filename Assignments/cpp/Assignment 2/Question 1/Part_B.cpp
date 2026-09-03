#include <iostream>
using namespace std;

double reorderCost(int qty, double unitPrice)   // integer quantity
{
    return qty * unitPrice;
}

double reorderCost(double qty, double unitPrice)   // fractional (by weight)
{
    return qty * unitPrice;
}

double reorderCost(int qty, double unitPrice, double taxRate)   // with tax
{
    return qty * unitPrice * (1 + taxRate / 100);
}

double applyDiscount(double price, double discountPercent = 10.0)

// If no discount is passed, apply 10% by default

{
    double discount = price * discountPercent / 100;
    return price - discount;
}

int main()
{
    cout << "Reorder Cost 1: "
         << reorderCost(10, 120.0) << endl;

    cout << "Reorder Cost 2: "
         << reorderCost(2.5, 120.0) << endl;

    cout << "Reorder Cost with Tax: "
         << reorderCost(45, 250.0, 10) << endl;

    cout << "Discount with default 10%: "
         << applyDiscount(2000) << endl;

    cout << "Discount with 20%: "
         << applyDiscount(2000, 20) << endl;

    return 0;
}
