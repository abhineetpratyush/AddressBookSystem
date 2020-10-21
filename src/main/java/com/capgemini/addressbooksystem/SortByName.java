package com.capgemini.addressbooksystem;

import java.util.Comparator;

class SortByName implements Comparator<ContactDetails> {
	public int compare(ContactDetails personOne, ContactDetails personTwo) {		
		return personOne.firstName.concat(personOne.lastName).compareTo(personTwo.firstName.concat(personTwo.lastName));
	}
}

class SortByCity implements Comparator<ContactDetails> {
	public int compare(ContactDetails personOne, ContactDetails personTwo) {		
		return personOne.city.compareTo(personTwo.city);
	}
}

class SortByState implements Comparator<ContactDetails> {
	public int compare(ContactDetails personOne, ContactDetails personTwo) {		
		return personOne.state.compareTo(personTwo.state);
	}
}

class SortByZip implements Comparator<ContactDetails> {
	public int compare(ContactDetails personOne, ContactDetails personTwo) {		
		return personOne.zip - personTwo.zip;
	}
}
