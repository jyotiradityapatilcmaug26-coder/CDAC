#include <iostream>
#include <string>
using namespace std;

class Entity
{
string name;
int health;
int level;
string type;

public:

Entity& setName(string n)
{
    name = n;
    return *this;
}

Entity& setHealth(int h)
{
    health = h;
    return *this;
}

Entity& setLevel(int l)
{
    level = l;
    return *this;
}

Entity& setType(string t)
{
    type = t;
    return *this;
}

string getName() const
{
    return name;
}

int getHealth() const
{
    return health;
}

int getLevel() const
{
    return level;
}

string getType() const
{
    return type;
}

void displayInfo() const
{
    cout << "Name   : " << name << endl;
    cout << "Health : " << health << endl;
    cout << "Level  : " << level << endl;
    cout << "Type   : " << type << endl;
}

};

int main()
{
Entity e;

e.setName("Knight")
 .setHealth(100)
 .setLevel(5)
 .setType("Warrior");

e.displayInfo();

return 0;

}
