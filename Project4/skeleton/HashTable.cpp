
#include "HashTable.h"
#include "HashTableException.h"
#include <stdio.h>

using namespace std;

int HashTable::getHashCode(const char *str) {
  int code = 0;
  while(*str != 0) {
    code += *(str++);
  }
  return code;
}

int HashTable::getSize() const { return size; }
int HashTable::getCapacity() const {return capacity; }

bool HashTable::isEmpty(int pos) const { 
  if(table[pos].empty()) 
    return true;
  return false;
}

bool HashTable::isTomb(int pos) const { 
  if(table[pos].compare("##tomb##")==0)
    return true; 
  return false;
}

bool HashTable::isAvailable(int pos) const { 

}

HashTable::HashTable(int capacity) {

}

HashTable::HashTable(const HashTable &ht) {

}

bool HashTable::contains(const string &s) const {

}

bool HashTable::contains(const char *s) const {

}

string HashTable::print() const {
  string str;
  char buf[128];
  int j=0;
/* Remove the following comment if you want 
 * to try the iterator version.
 */
// #define __USE_ITERATOR__
#ifdef  __USE_ITERATOR__
  for(HashTable::Iterator it = begin(); it!=end(); it++) {
    sprintf(buf, "%2d. -%s-\n", j++, (*it).c_str());
    str.append(buf);
  }
#elif
  for(int i=0, j=0; i<capacity; i++)
    if(!isAvailable(i)) {
      sprintf(buf, "%2d. -%s-\n", j++, table[i].c_str());
      str.append(buf);
    }
#endif
  sprintf(buf, " --- CAPACITY: %d, SIZE: %d ---\n", capacity, size);
  str.append(buf);
  return str;
}

bool HashTable::add(const string &str) {
  
}

bool HashTable::add(const char *s) {
  
}

bool HashTable::remove(const string &str) {
  
}

bool HashTable::remove(const char *s) {
  
}

HashTable& HashTable::operator=(const HashTable &ht) {
  
}

bool HashTable::operator += (const string &str) { 
  
}
bool HashTable::operator += (const char* s) { 
  
}
bool HashTable::operator -= (const string &str) {
  
}
bool HashTable::operator -= (const char *s) { 
  
}

HashTable HashTable::operator + (const string &str) const {
  
}

HashTable HashTable::operator + (const char* s) const {

}
HashTable HashTable::operator - (const string &str) const {

}
HashTable HashTable::operator - (const char *s) const {

}

HashTable HashTable::operator+(const HashTable &t) const {

}

HashTable& HashTable::operator+=(const HashTable &t) {

}

std::ostream& operator<<(std::ostream &os, HashTable &t) {

}

HashTable::Iterator HashTable::begin() const {

}

HashTable::Iterator HashTable::end() const {

}