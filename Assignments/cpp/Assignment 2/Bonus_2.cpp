#include <iostream>
using namespace std;

int level = 1;

namespace Engine
{
    namespace Audio
    {
        void playSound(string Sound)
        {
            cout <<"Playing Sound: "<< Sound <<endl;
        }
    }
}

int main()
{
    int level = 10;

    cout << "Local level: " <<level << endl;
    cout << "Global level: " <<::level <<endl;

    Engine::Audio::playSound("Sword_clash");

    return 0;
}