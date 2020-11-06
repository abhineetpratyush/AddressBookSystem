package com.capgemini.addressbooksystem;

import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AddressBookRestAPITest {
	private static final Logger log = LogManager.getLogger(AddressBookRestAPITest.class);
	public AddressBookDBService addressBookDBService;
	
	@Before
	public void initialise() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	@Test
	public void givenContactDetailsInJsonServer_WhenRetrieved_ShouldMatchCount() {
		ContactDetails[] arraysOfContacts = getContactDetailsList();
		addressBookDBService = new AddressBookDBService(Arrays.asList(arraysOfContacts));
		int entries = addressBookDBService.countEntries();
		Assert.assertEquals(3, entries);
	}

	private ContactDetails[] getContactDetailsList() {
		Response response = RestAssured.get("/addressbook");
		log.info("CONTACT ENTRIES IN JSON SERVER:\n" + response.asString());
		ContactDetails[] arrayOfContacts = new Gson().fromJson(response.asString(), ContactDetails[].class);
		return arrayOfContacts;
	}
}
