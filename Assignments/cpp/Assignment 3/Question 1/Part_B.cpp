#include <iostream>
#include <vector>
#include <string>
using namespace std;

using Header = pair<string, string>;
using HeaderList = vector<Header>;
using Port = unsigned int;
using IPAddress = string;

typedef unsigned long long RequestId;

void printHeaders(const HeaderList& headers) {

    cout << "Headers:" << endl;

    for (const auto& header : headers) {
        cout << " " << header.first << " : " << header.second << endl;
    }
}

int main() {

    HeaderList headers = {
        {"Content-Type", "application/json"},
        {"Authorization", "Bearer eyJhbGci..."},
        {"Accept-Language", "en-US"}
    };

    Port serverPort = 8080;
    RequestId requestId = 1748293847;

    cout << "Request ID : " << requestId << endl;
    cout << "Server Port : " << serverPort << endl;

    printHeaders(headers);

    return 0;
}