#include <stdbool.h>

struct testStruct
{
    long a;
    bool b;
    double c;
};

struct testStructRec
{
    long a;
    struct testStructRec *b;
};

struct testStruct* getTestStruct(long a, bool b, double c);
struct testStructRec* getTestStructRec(long a, struct testStructRec* b);

long getStructFieldA(struct testStruct *);
bool getStructFieldB(struct testStruct *);
double getStructFieldC(struct testStruct *);

struct testStruct *setStructFieldA(struct testStruct *t, long a);
struct testStruct *setStructFieldB(struct testStruct *t, bool b);
struct testStruct *setStructFieldC(struct testStruct *t, double c);

long getStructFieldRecA(struct testStructRec *t);
struct testStructRec *getStructFieldRecB(struct testStructRec *t);

struct testStructRec *setStructFieldRecA(struct testStructRec *t, long a);
struct testStructRec *setStructFieldRecB(struct testStructRec *t, struct testStructRec *b);