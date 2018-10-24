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

//function that returns true only if the specified position of the table is empty
bool HashTable::isEmpty(int pos) const {
	if(pos >= capacity){
		return false;
	}

	if(table[pos].empty()){
		return true;
	}
	return false;
}

//function that returns true only if the specified position of the table contains a tomb
bool HashTable::isTomb(int pos) const {
	if(pos >= capacity){
		return false;
	}

	if(table[pos].compare("##tomb##")==0){
		return true;
	}

	return false;
}

//function that returns true if the specified position of the table contains a tomb or is empty
bool HashTable::isAvailable(int pos) const {
	return this->isTomb(pos) || this->isEmpty(pos);
}

//Class constructor
HashTable::HashTable(int capacity) {
	if(capacity < 0){
		table = new string;
		std::bad_alloc ex;
		throw ex;
	}
	this->capacity = capacity;
	size = 0;

	table = new string[capacity];
}

//Class copy constructors
HashTable::HashTable(const HashTable &ht) {
	capacity = ht.getCapacity();
	size = ht.getSize();
	table = new string[capacity];
	for(int i=0; i < capacity; i++){
		this->table[i] = ht.table[i];
	}
}

//Class destructors
HashTable::~HashTable() {
	delete [] table;
}

//function that returns true only if the specified string exists in the table
bool HashTable::contains(const string &s) const {
	for(int i=0; i < capacity; i++){
		if(table[i].compare(s) == 0){
			return true;
		}
	}
	return false;
}

//function that returns true only if the specified c-string exists in the table
bool HashTable::contains(const char *s) const {
	for(int i=0; i < capacity; i++){
		if(table[i].compare(s) == 0){
			return true;
		}
	}
	return false;
}

//print the elements of the table
string HashTable::print() const {
	string str;
 	char buf[128];

/* Remove the following comment if you want
 * to try the iterator version.
 */
//#define __USE_ITERATOR__

#ifdef  __USE_ITERATOR__
	int j=0;

	for(HashTable::Iterator it = begin(); it!=end(); it++) {
		sprintf(buf, "%2d. -%s-\n", j++, (*it).c_str());
		str.append(buf);
	}
#else
	int i=0, j=0;
	 for(i=0, j=0; i<capacity; i++)
		if(!isAvailable(i)) {
			sprintf(buf, "%2d. -%s-\n", j++, table[i].c_str());
			str.append(buf);
		}
#endif
  sprintf(buf, " --- CAPACITY: %d, SIZE: %d ---\n", capacity, size);
  str.append(buf);
  return str;
}

//add the specified string to the table
bool HashTable::add(const string &str) {
	int x=0;
	int pos;
	//this string already exists
	if(this->contains(str)){
		return false;
	}
	else if( !this->contains("") && !this->contains("##tomb##") ){	//there is no available space
		cout << "THROW Exception!" << endl;
		HashTableException ex;
		throw ex;
		return false;
	}

	//add the ascii character numbers of the string
	for(unsigned int i=0; i< str.length(); i++){
		x += int(str[i]);
	}

	//insert the object in the correct position of the table
	for(int n=0; n < capacity; n++){
		pos = (x+n) % capacity;
		if(isAvailable(pos)){
			table[pos] = str;
			break;
		}
	}

	//increase the size variable
	this->size++;
	return true;
}

//add the specified string to the table
bool HashTable::add(const char *s) {
	int x=0;
	int pos;
	//this string already exists
	if(this->contains(s)){
		return false;
	}
	else if( !this->contains("") && !this->contains("##tomb##") ){	//there is no available space
		cout << "THROW Exception!" << endl;
		HashTableException ex;
		throw ex;
		return false;
	}

	//add the ascii character numbers of the string
	for(unsigned int i=0; s[i]!='\0'; i++){
		x += int(s[i]);
	}

	//insert the object in the correct position of the table
	for(int n=0; n < capacity; n++){
		pos = (x+n) % capacity;
		if(isAvailable(pos)){
			table[pos] = s;
			break;
		}
	}

	//increase the size variable
	this->size++;
	return true;
}

//remove the specified string from the table
bool HashTable::remove(const string &str) {
	//this string does not exist
	if(!this->contains(str)){
		return false;
	}
	//remove the correct table element
	for(int i=0; i < capacity; i++){
		if(table[i].compare(str) == 0){
			table[i] = "##tomb##";
			break;
		}
	}

	//decrease the size variable
	this->size--;
	return true;
}

//remove the specified c-string from the table
bool HashTable::remove(const char *s) {
	//this string does not exist
	if(!this->contains(s)){
		return false;
	}
	//remove the correct table element
	for(int i=0; i < capacity; i++){
		if(table[i].compare(s) == 0){
			table[i] = "##tomb##";
			break;
		}
	}

	//decrease the size variable
	this->size--;
	return true;
}

//equals operator overloading
HashTable& HashTable::operator=(const HashTable &ht) {
	if(table!=NULL){
		delete [] table;
	}
	this->size = ht.getSize();
	this->capacity = ht.capacity;
	this->table = new string[capacity];

	for(int i=0; i < capacity; i++){
		this->table[i] = ht.table[i];
	}

	return *this;

}

//same functionality to bool add(const string &s)
bool HashTable::operator += (const string &str) {
	return add(str);
}

//same functionality to bool add(const char *s)
bool HashTable::operator += (const char* s) {
	return add(s);
}

//same functionality to bool remove(const string &s)
bool HashTable::operator -= (const string &str) {
	return remove(str);
}

//same functionality to bool remove(const char *s)
bool HashTable::operator -= (const char *s) {
	return remove(s);
}

//Creates a new HashTable that occurs the insertion of str to the existing HashTable
HashTable HashTable::operator + (const string &str) const {
	HashTable newHashTable(*this);

	newHashTable.add(str);

	return newHashTable;
}

//Creates a new HashTable that occurs the insertion of s to the existing HashTable
HashTable HashTable::operator + (const char* s) const {
	HashTable newHashTable(*this);

	newHashTable.add(s);

	return newHashTable;
}

//Creates a new HashTable that occurs the removal of str from the existing HashTable
HashTable HashTable::operator - (const string &str) const {
	HashTable newHashTable(*this);

	newHashTable.remove(str);

	return newHashTable;
}

//Creates a new HashTable that occurs the removal of s from the existing HashTable
HashTable HashTable::operator - (const char *s) const {
	HashTable newHashTable(*this);

	newHashTable.remove(s);

	return newHashTable;
}

//Creates a new HashTable that occurs from the union of t with the existing HashTable
HashTable HashTable::operator+(const HashTable &t) const {
	int i=0;
	HashTable newHashTable(this->capacity + t.getCapacity());

	//add the current tables' items to the new HashTable object
	for(i=0; i < this->capacity; i++){
		newHashTable.add(this->table[i]);
	}

	//add the table items of the HashTable t to the new HashTable object
	for(i=0; i < t.getCapacity(); i++){
		newHashTable.add(t.table[i]);
	}

	return newHashTable;
}

//Inserts elements of t in the existing HashTable.
//Returns a reference to the existing HashTable
HashTable& HashTable::operator+=(const HashTable &t) {
	int i;
	HashTable newHashTable(*this);

	delete [] table;
	this->size = 0;
	this->capacity = newHashTable.getCapacity() + t.getCapacity();
	table = new string[this->capacity];

	//add the current tables' items to the new HashTable object
	for(i=0; i < newHashTable.getCapacity(); i++){
		this->add(newHashTable.table[i]);
	}

	//add the table items of the HashTable t to the new HashTable object
	for(i=0; i < t.getCapacity(); i++){
		this->add(t.table[i]);
	}

	return *this;
}

//Prints to ostream
std::ostream& operator<<(std::ostream &os, HashTable &t) {

	os << t.print();

	return os;
}

//Returns an Iterator at the first position of the table
HashTable::Iterator HashTable::begin() const {
	Iterator it(this);
	return it;
}

//Returns an Iterator after the end of the table
HashTable::Iterator HashTable::end() const {
	Iterator it(this, false);
	return it;
}
