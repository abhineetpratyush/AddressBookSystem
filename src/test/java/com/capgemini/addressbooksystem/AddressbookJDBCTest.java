package com.capgemini.addressbooksystem;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddressbookJDBCTest {
	private static final Logger log = LogManager.getLogger(AddressbookJDBCTest.class);
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
	
	@Test
	public void given5NewContactEntries_WhenAddedUsingMulltithreading_ShouldSyncWithDB() throws CustomJDBCException {
		addressBookDBService.readContactsFromDB();
		ContactDetailsForMultithreading[] arrayOfContacts = {
				new ContactDetailsForMultithreading("Saurav", "Raj", "X-908", "Dhanbad", "Jharkhand", 545454, 767346743, "saurav@xya.com", "Home", "2017-01-01"),
				new ContactDetailsForMultithreading("Prince", "Jha", "X-910", "Ranchi", "Karnataka", 54654, 8746723, "prince@xya.com", "Relatives", "2018-01-01"),
				new ContactDetailsForMultithreading("Kolhan", "Kotla", "Y-908", "Mumbai", "Maharashtra", 263253, 672547, "abc@xya.com", "College Friends", "2019-01-01"),
				new ContactDetailsForMultithreading("Suklekha", "Mazumdar", "Q-908", "Kullu", "Himachal Pradesh", 56423, 62746784, "sulekha@xya.com", "School Friends", "2020-01-01"),
				new ContactDetailsForMultithreading("Raunak", "Kumar", "W-908", "Lahaul", "Himachal Pradesh", 232323, 434535335, "kumar@xya.com", "Relatives", "2018-04-11")};
		Instant threadStart = Instant.now();
		addressBookDBService.addMultipleContactsToDB(Arrays.asList(arrayOfContacts));
		Instant threadEnd = Instant.now();
		log.info("Duration with thread: "+ Duration.between(threadStart, threadEnd));
		Assert.assertEquals(10, addressBookDBService.countEntries());
	}
}
