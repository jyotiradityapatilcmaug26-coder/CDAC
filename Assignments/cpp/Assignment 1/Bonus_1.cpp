#include <iostream>
using namespace std;

struct layout1
{
    char c1;
    int i;
    char c2;
};

struct Layout2 
{ 
    int i; 
    char c1; 
    char c2; 
};

int main()

{
    cout << "Size of layout 1 : " <<sizeof(layout1) << endl;
    cout << "Size of layout 2 : " <<sizeof(Layout2) << endl;

    /* Both have members, order is bit different.

    Compiler adds extra empty spaces called padding,
    so that data is neatly aligned in memory.

    In Layout1 char comes first and then int, so padding is added before int.

    Member order can xhange the total size of a structure.
    this matters in very imp in network packets and 
    hardware registers because the memory layout has to be correct,     
    */
   
    return 0;
}