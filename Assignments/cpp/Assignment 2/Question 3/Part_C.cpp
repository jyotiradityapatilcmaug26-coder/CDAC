#include <iostream>
using namespace std;

int main()
{
int R, C;

cout << "Enter rows: ";
cin >> R;

cout << "Enter columns: ";
cin >> C;

// Dynamic 2D array
int **map = new int*[R];

for(int i = 0; i < R; i++)
{
    map[i] = new int[C];
}

// Taking map values
cout << "Enter map values (0 to 4): " << endl;

for(int i = 0; i < R; i++)
{
    for(int j = 0; j < C; j++)
    {
        cin >> map[i][j];
    }
}

// Counters
int grass = 0;
int water = 0;
int mountain = 0;
int forest = 0;
int dungeon = 0;

// Count tile types
for(int i = 0; i < R; i++)
{
    for(int j = 0; j < C; j++)
    {
        if(map[i][j] == 0)
            grass++;
        else if(map[i][j] == 1)
            water++;
        else if(map[i][j] == 2)
            mountain++;
        else if(map[i][j] == 3)
            forest++;
        else if(map[i][j] == 4)
            dungeon++;
    }
}

// Display map
cout << endl;
cout << "===== GAME MAP (" << R << " x " << C << ") =====" << endl;

for(int i = 0; i < R; i++)
{
    for(int j = 0; j < C; j++)
    {
        cout << " " << map[i][j];
    }
    cout << endl;
}

// Legend
cout << "Legend: 0=Grass 1=Water 2=Mountain 3=Forest 4=Dungeon" << endl;

// Tile count
cout << "Tile Count:" << endl;
cout << " Grass : " << grass << endl;
cout << " Water : " << water << endl;
cout << " Mountain : " << mountain << endl;
cout << " Forest : " << forest << endl;
cout << " Dungeon : " << dungeon << endl;

// Deallocate memory
for(int i = 0; i < R; i++)
{
    delete[] map[i];
}

delete[] map;

return 0;

}
