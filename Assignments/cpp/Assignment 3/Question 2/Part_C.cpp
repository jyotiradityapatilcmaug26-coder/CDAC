#include <iostream>
#include <memory>
#include <string>
using namespace std;

class AudioClip {
    string name;
    double duration;

public:
    AudioClip(string n, double d) {
        name = n;
        duration = d;
        cout << "[AudioClip Loaded]" << endl;
    }

    ~AudioClip() {
        cout << "[AudioClip Released]" << endl;
    }

    string getName() const {
        return name;
    }
};

int main() {

    auto audio = make_shared<AudioClip>("explosion", 3.5);

    weak_ptr<AudioClip> observer = audio;

    if (auto clip = observer.lock()) {
        cout << "Clip alive: " << clip->getName() << endl;
    }

    audio.reset();

    if (observer.expired()) {
        cout << "Clip already unloaded." << endl;
    }

    return 0;
}