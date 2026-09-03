#include <iostream>
#include <memory>
#include <string>
using namespace std;

class Shader {
    string name;
    string type;

public:
    Shader(string n, string t) {
        name = n;
        type = t;
        cout << "[Shader Compiled]" << endl;
    }

    ~Shader() {
        cout << "[Shader Destroyed]" << endl;
    }

    int getReferenceCount(shared_ptr<Shader> shader) {
        return shader.use_count();
    }
};

int main() {

    auto shader = make_shared<Shader>("main_vert", "vertex");

    cout << "Ref count: " << shader.use_count() << endl;

    {
        auto rendererRef = shader;
        cout << "Ref count: " << shader.use_count() << endl;

        auto editorRef = shader;
        cout << "Ref count: " << shader.use_count() << endl;
    }

    cout << "Ref count: " << shader.use_count() << endl;

    return 0;
}