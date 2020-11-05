package com.capgemini.addressbooksystem;

import java.time.LocalDate;
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
	
	@Test
	public void givenNewCityForEmployee_WhenUpdatedUsingPreparedStatement_ShouldSyncWithDB() throws CustomJDBCException {
		addressBookDBService.readContactsFromDB();
		addressBookDBService.updateContactCity("Ram", "Ranchi");
		boolean result = addressBookDBService.checkAddressBookInSyncWithDB("Ram");
		Assert.assertTrue(result);
	}
	
	@Test
	public void givenDateRange_ReturnNoOfContactsAddedInTheRange() throws CustomJDBCException {
		addressBookDBService.readContactsFromDB();
		LocalDate startDate = LocalDate.parse("2018-01-01");
		LocalDate endDate = LocalDate.parse("2019-04-04");
		List<ContactDetails> addressBookList = addressBookDBService.getAddressBookDataInDateRange(startDate, endDate);
		Assert.assertEquals(2, addressBookList.size());
	}
	
	@Test
	public void givenCityOrState_ShouleRetrieveCorrespondingContactEntries() throws CustomJDBCException {
		addressBookDBService.readContactsFromDB();
		List<ContactDetails> addressBookListOnCity = addressBookDBService.getAddressBookDataOnCity("Ranchi");
		List<ContactDetails> addressBookListOnState = addressBookDBService.getAddressBookDataOnState("Karnataka");
		Assert.assertEquals(1, addressBookListOnCity.size());
		Assert.assertEquals(2, addressBookListOnState.size());
	}
	
	@Test
	public void givenNewContactEntry_WhenAdded_ShouldSyncWithDB() throws CustomJDBCException {
		addressBookDBService.readContactsFromDB();
		addressBookDBService.addContactEntryToDB("Pranav", "Kamra", "zipzap@gmail.com", "D-786", "Mysuru", "Tamil Nadu",
												57456, 73673352, "2019-06-14", "Home");
		boolean result = addressBookDBService.checkAddressBookInSyncWithDB("Pranav");
		Assert.assertTrue(result);
	}
}
