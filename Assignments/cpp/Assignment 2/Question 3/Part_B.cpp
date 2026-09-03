#include <iostream>
using namespace std;

namespace Physics
{
int clamp(int value, int min, int max)
{
if(value < min)
return min;
else if(value > max)
return max;
else
return value;
}

double lerp(double a, double b, double t)
{
    return a + (b - a) * t;
}

}

namespace GameMath
{
int clamp(int value, int min, int max)
{
if(value < min)
return min;
else if(value > max)
return max;
else
return value;
}

double lerp(double a, double b, double t)
{
    return a + (b - a) * t;
}

}

int main()
{
cout << "Physics clamp : "
<< Physics::clamp(120, 0, 100) << endl;

cout << "GameMath clamp : "
     << GameMath::clamp(-10, 0, 100) << endl;

cout << "Physics lerp : "
     << Physics::lerp(10, 20, 0.5) << endl;

cout << "GameMath lerp : "
     << GameMath::lerp(100, 200, 0.25) << endl;

{
    using namespace Physics;

    cout << "Using Physics namespace : "
         << clamp(150, 0, 100) << endl;
}

return 0;

}
