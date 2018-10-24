#include <iostream>
#include <cstdlib>
#include <string.h>

#include "ExtHashTable.h"

int printFlag = 1;	// flag that is set to 1 for printing the table info when rehashing

//Class constructor
ExtHashTable::ExtHashTable( double upper_bound_ratio, double lower_bound_ratio, int size) : HashTable(size){
	this->upper_bound_ratio = upper_bound_ratio;
	this->lower_bound_ratio = lower_bound_ratio;
}
//Class copy constructor
ExtHashTable::ExtHashTable(const ExtHashTable &t) : HashTable(t) {
	this->upper_bound_ratio = t.upper_bound_ratio;
	this->lower_bound_ratio = t.lower_bound_ratio;
}

//function that performs the rehash
void ExtHashTable::rehash() {
	double ratio = size/(double)capacity;
	if(ratio > upper_bound_ratio){
		ExtHashTable ext(*this);
		delete [] table;
		this->capacity *= 2;	//double the table capacity
		this->table = new string[capacity];
		this->size = 0;

		for(int i=0; i < ext.getCapacity(); i++){
			this->HashTable::add(ext.table[i]);
		}
		if(printFlag == 1){
			cout << "--> Size: " << this->size << ", New capacity: " << this->capacity << endl;
		}
	}
	else if(ratio < lower_bound_ratio){
		if(capacity == 8){
			return;
		}
		ExtHashTable ext(*this);
		delete [] table;
		this->capacity /= 2;	//half the table capacity
		this->table = new string[capacity];
		this->size = -1;

		for(int i=0; i < ext.getCapacity(); i++){
			this->HashTable::add(ext.table[i]);
		}
		if(printFlag == 1){
			cout << "--> Size: " << this->size << ", New capacity: " << this->capacity << endl;
		}
	}
}

//add the specified string to the table
bool ExtHashTable::add(const string &str) {
	this->HashTable::add(str);
	rehash();
	return true;	//it never throws HashTableException because table
					//can never be full as we double its size every time
					//the number of elements inserted overcomes a number
					//called upper_bound_ratio
}

//add the specified c-string to the table
bool ExtHashTable::add(const char *s) {
	this->HashTable::add(s);
	rehash();
	return true;	//it never throws HashTableException because table
					//can never be full as we double its size every time
					//the number of elements inserted overcomes a number
					//called upper_bound_ratio
}

//remove the specified string from the table
bool ExtHashTable::remove(const string &str) {

	this->HashTable::remove(str);

	rehash();

	return true;
}

//remove the specified c-string from the table
bool ExtHashTable::remove(const char *s) {

	this->HashTable::remove(s);

	rehash();

	return true;
}

//same functionality to bool ExtHashTable::add(const string &s)
bool ExtHashTable::operator += (const string &str) {
	return add(str);
}

//same functionality to bool ExtHashTable::add(const char *s)
bool ExtHashTable::operator += (const char* s) {
	return add(s);
}

//same functionality to bool ExtHashTable::remove(const string &s)
bool ExtHashTable::operator -= (const string &str) {
	return remove(str);
}

//same functionality to bool ExtHashTable::remove(const char *s)
bool ExtHashTable::operator -= (const char *s) {
	return remove(s);
}

//Creates a new ExtHashTable that occurs the insertion of str to the existing ExtHashTable
ExtHashTable ExtHashTable::operator+(const string &str) const{
	ExtHashTable newExtHashTable(*this);

	newExtHashTable.add(str);

	return newExtHashTable;
}

//Creates a new ExtHashTable that occurs the insertion of s to the existing ExtHashTable
ExtHashTable ExtHashTable::operator+(const char* s) const{
	ExtHashTable newExtHashTable(*this);

	newExtHashTable.add(s);

	return newExtHashTable;
}

//Creates a new ExtHashTable that occurs the removal of str from the existing ExtHashTable
ExtHashTable ExtHashTable::operator-(const string &str) const{
	ExtHashTable newExtHashTable(*this);

	newExtHashTable.remove(str);

	return newExtHashTable;
}

//Creates a new ExtHashTable that occurs the removal of s from the existing ExtHashTable
ExtHashTable ExtHashTable::operator-(const char *s) const{
	ExtHashTable newExtHashTable(*this);

	newExtHashTable.remove(s);

	return newExtHashTable;
}

//Creates a new ExtHashTable that occurs from the union of t with the existing ExtHashTable
ExtHashTable ExtHashTable::operator+(const ExtHashTable &t) const {
	int i=0;
	ExtHashTable newExtHashTable(this->upper_bound_ratio, this->lower_bound_ratio);

	printFlag = 0;
	for(i=0; i < this->capacity; i++){
		if(this->table[i].compare("") == 0){
			continue;
		}
		newExtHashTable.add(this->table[i]);
	}
	for(i=0; i < t.getCapacity(); i++){
		if(t.table[i].compare("") == 0){
			continue;
		}
		newExtHashTable.add(t.table[i]);
	}
	printFlag = 1;
	cout << "--> Size: " << newExtHashTable.size - 1 << ", New capacity: " << newExtHashTable.capacity << endl;
	return newExtHashTable;
}

//Inserts elements of t in the existing ExtHashTable.
//Returns a reference to the existing ExtHashTable
ExtHashTable & ExtHashTable::operator+=(const ExtHashTable &t) {
	for(int i=0; i < t.getCapacity(); i++){
		if(t.table[i].compare("") == 0){
			continue;
		}
		this->add(t.table[i]);
	}
	return *this;
}

//equals operator overloading
ExtHashTable & ExtHashTable::operator=(const ExtHashTable &t) {
	if(table!=NULL){
		delete [] table;
	}
	this->size = t.getSize();
	this->capacity = t.capacity;
	this->table = new string[capacity];

	for(int i=0; i < capacity; i++){
		this->table[i] = t.table[i];
	}

	this->upper_bound_ratio = t.upper_bound_ratio;
	this->lower_bound_ratio = t.lower_bound_ratio;

	return *this;
}
