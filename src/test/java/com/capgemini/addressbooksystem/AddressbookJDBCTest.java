package com.capgemini.addressbooksystem;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class AddressbookJDBCTest {
	
	public AddressBookDBService addressBookDBService;

	@Before
	public void initialise() {
		this.addressBookDBService = new AddressBookDBService();
	}
	

	@Test
	public void givenAddressbookEntries_WhenReadFromDB_ShouldReturnCount() throws CustomJDBCException{
		List<ContactDetails> addressBookList = addressBookDBService.readContactsFromDB();
		Assert.assertEquals(3, addressBookList.size());
	}
}
