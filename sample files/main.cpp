//
//  main.cpp
//  HW8
//
//  Created by Shenwei Chen on 11/5/16.
//  Copyright © 2016 Shenwei Chen. All rights reserved.
//

#include <iostream>
#include <fstream>
#include <math.h>
#include <vector>
#include <iomanip>

using namespace std;

// **********IMPORTANT**********
// Below is the file path on my computer. Please change the path to test the code.
// dict.txt
#define FILE_PATH "/Users/Steveisno1/Documents/16-17Fall/CPE593/HW8/HW8/dict.txt"
// hw8.dat
#define TEST_PATH "/Users/Steveisno1/Documents/16-17Fall/CPE593/HW8/HW8/hw8.dat"

// store the key and the string value
struct Node {
    int key;
    string val;
    Node* next;
    Node(int k, string v) {
        key = k;
        val = v;
        this->next = nullptr;
    }
};

// store the count of the number of checks
struct counting {
    int number;
    int count;
    counting(int num) {
        number = num;
        count = 1;
    }
    counting(int num, int ct) {
        number = num;
        count = ct;
    }
};

class HashMap {
private:
    Node** table;
    int size;
    int init;
    vector<counting> histogram;  //to count the number that adding the word checks
public:
    HashMap(int init_size) {
        int i = 1;
        while(i < init_size) {
            i *= 2;
        }
        table = new Node*[i];
        for(int j = 0; j < i; j++) {
            table[j] = nullptr;
        }
        init = i;
        size = 0;
        counting c = *new counting(1, 0);   // make histogram not empty, make it contains at least one value
        histogram.push_back(c);
    }
    
    ~HashMap() {
        for (int i = 0; i < init; ++i)
        {
            Node* linked = table[i];
            while (linked != nullptr)
            {
                Node* temp = linked;
                linked = linked->next;
                delete temp;
            }
        }
        delete[] table;
    }

    //hash function, which is similar with the function String.hashCode() in java
    int hashCode(string s) {
        unsigned long long h = 0;
        if(s.length() == 0)
            return 0;
        for(int i = 0; i < s.length(); i++) {
            h = h * 31 + int(s[i]);
        }
        return h % init;
    }
    
    // insert value
    void add(string s) {
        size++;
        int count = 1;
        int k = hashCode(s);
        Node* temp = nullptr;
        Node* linked = table[k];
        // find a place(nullptr) to store the value
        while(linked != nullptr) {
            count++;
            temp = linked;
            linked = linked->next;
        }
        linked = new Node(k, s);
        if(temp == nullptr) {
            table[k] = linked;   // if null, replace with the new node
        }
        else {
            temp->next = linked; // not null, link the new node
        }
        vector<counting>::iterator itr;
        bool isbreak = false;
        for(itr = histogram.begin(); itr != histogram.end(); itr++) {
            if(itr->number == count) {
                itr->count++;
                isbreak = true;
                break;
            }
        }
        if(isbreak == false) {  // not found the count number
            for(itr = histogram.begin(); itr != histogram.end(); itr++) {
                if(itr->number > count) {
                    counting c = *new counting(count);
                    histogram.insert(itr, c);
                    isbreak = true;
                    break;
                }
            }
            if(isbreak == false) {
                counting c = *new counting(count);
                histogram.push_back(c);
            }
            
        }
    }
    
    // check if the word is in dictionary
    bool contains(string s) {
        int k = hashCode(s);
        Node* linked = table[k];
        while(linked != nullptr) {
            if(linked->val == s) {
                return true;
            }
            linked = linked->next;
        }
        return false;
    }

    // transfer the boolean value into string
    string TrueFalse(bool b) {
        if(b == true)
            return "TRUE";
        else
            return "FALSE";
    }
    
    // read the dictionary from the filepath
    void readDict(string filePath) {
        ifstream infile(filePath);
        if (!infile) {
            infile.close();
            cout<<"CAN NOT OPEN "<<filePath<<endl;
            cout<<"FILE DOES NOT EXIST!"<<endl;
            exit(0);
        }
        else {
            char ch[100];
            while(infile.getline(ch, 100)) {
                char* c = ch;
                add(c);
            }
        }
        infile.close();
    }
    
    // print the histogram
    void print() {
        vector<counting>::iterator itr;
        cout<<"insert\tcount"<<endl;
        for(itr = histogram.begin(); itr != histogram.end(); itr++) {
            cout<<itr->number<<"\t\t"<<itr->count<<endl;
        }
        cout<<endl;
    }
    
    // read the test file and print the result on the console
    void readTest(string filePath) {
        ifstream infile(filePath);
        cout<<"Reading test file...\n"<<endl;
        if (!infile) {
            infile.close();
            cout<<"CAN NOT OPEN "<<filePath<<endl;
            cout<<"FILE DOES NOT EXIST!"<<endl;
            exit(0);
        }
        else {
            char ch[100];
            cout<<left<<setw(18)<<"word"<<left<<setw(8)<<"contains"<<endl;
            while(infile.getline(ch, 100)) {
                if(ch[0] < 'a' || ch[0] > 'z')  // no word in this line
                    continue;
                for(int i = 0; i < 100; i++) {  // delete all escape character
                    if(ch[i] < 'a' || ch[i] > 'z')
                        ch[i] = '\0';
                }
                char* c = ch;
                cout<<left<<setw(18)<<c<<right<<setw(8)<<TrueFalse(contains(c))<<endl;
            }
        }
    }
};

int main(int argc, const char * argv[]) {
    HashMap hm = *new HashMap(500000);
    hm.readDict(FILE_PATH);
    hm.print();
    hm.readTest(TEST_PATH);
    return 0;
}
