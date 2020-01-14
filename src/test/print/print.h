void incSuccess();
void incFailure();

void succ(char* name, int expected, int result);
void err(char* name, int expected, int result);

void succPrefixOne(char* name, int x,  int expected, int result);
void errPrefixOne(char* name, int x,  int expected, int result);
void succPrefixTwo(char* name, int x,  int y, int expected, int result);
void errPrefixTwo(char* name, int x,  int y, int expected, int result);

void succInfixTwo(char* name, int x, int y, int expected, int result);
void errInfixTwo(char* name, int x, int y, int expected, int result);