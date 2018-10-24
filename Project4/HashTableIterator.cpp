#include "HashTable.h"
#include "HashTableException.h"
#include <stdio.h>

using namespace std;

//Class constructor
HashTable::Iterator::Iterator(const HashTable *t) {
	curr = &(t->table[0]);
	//skip the table entries that are either empty or correspond to a tombstone
	while((*curr).compare("") == 0 || (*curr).compare("##tomb##") == 0 ){
		curr++;
		if(curr == t->end().curr){
			break;
		}
	}
	ht = t;
}

//Class constructor
HashTable::Iterator::Iterator(const HashTable *t, bool start){

	if(start == false){	//set the iterator to the first position
						//of the table that is not empty or tomb
		//set the curr address to the address right after the end of the table
		curr = &(t->table[(t->capacity)-1]);
		curr++;
	}
	else{//set the iterator to the first position
		//of the table that is not empty or tomb
		curr = &(t->table[0]);
		//skip the table entries that are either empty or correspond to a tombstone
		while((*curr).compare("") == 0 || (*curr).compare("##tomb##") == 0 ){
			curr++;
			if(curr == t->end().curr){
				break;
			}
		}
	}
	ht = t;
}

//Class copy constructor
HashTable::Iterator::Iterator(const HashTable::Iterator &it) {
	this->curr = it.curr;
	this->ht = it.ht;
}

//equals operator overloading
HashTable::Iterator& HashTable::Iterator::operator=(const HashTable::Iterator &it) {
	this->curr = it.curr;
	this->ht = it.ht;

	return *this;
}

//this operator increases the Iterator by one. Returns an Iterator object that contains the new position of curr
HashTable::Iterator HashTable::Iterator::operator++() {
	Iterator it = *this;

	//skip the table entries that are either empty or correspond to a tombstone
	do{
		if(++(this->curr) == it.ht->end().curr){
			break;
		}
	}while((*curr).compare("") == 0 || (*curr).compare("##tomb##") == 0 );

	it = *this;

	return it;
}

//this operator increases the Iterator by one. Returns an Iterator object that contains the old position of curr
HashTable::Iterator HashTable::Iterator::operator++(int a) {
	Iterator it = *this;

	//skip the table entries that are either empty or correspond to a tombstone
	do{
		if(++(this->curr) == it.ht->end().curr){
			break;
		}
	}while((*curr).compare("") == 0 || (*curr).compare("##tomb##") == 0 );

	return it;
}

//Checks if the current object is the same with object t
bool HashTable::Iterator::operator==(const HashTable::Iterator &it) const {
	if((this->curr == it.curr) && (this->ht == it.ht)){
		return true;
	}

	return false;
}

//Checks if the current object is different from object t
bool HashTable::Iterator::operator!=(const HashTable::Iterator &it) const {
	if((this->curr == it.curr) && (this->ht == it.ht)){
		return false;
	}

	return true;
}

//Returns the data of the address that the the pointer curr indicates
const string& HashTable::Iterator::operator*() {
	return *curr;
}

//Returns the address that the the pointer curr indicates
const string* HashTable::Iterator::operator->() {
	return curr;
}
