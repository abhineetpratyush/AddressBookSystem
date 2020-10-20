package com.capgemini.addressbooksystem;

import java.util.Comparator;

class SortByName implements Comparator<ContactDetails> {
	public int compare(ContactDetails personOne, ContactDetails personTwo) {		
		return personOne.fullName.compareTo(personTwo.fullName);
	}
}

