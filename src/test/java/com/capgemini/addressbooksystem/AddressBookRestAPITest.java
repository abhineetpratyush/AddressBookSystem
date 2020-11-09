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
import io.restassured.specification.RequestSpecification;

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
		ContactDetailsForRestAPI[] arraysOfContacts = getContactDetailsList();
		addressBookDBService = new AddressBookDBService(Arrays.asList(arraysOfContacts));
		int entries = addressBookDBService.countEntriesForRest();
		Assert.assertEquals(3, entries);
	}

	private ContactDetailsForRestAPI[] getContactDetailsList() {
		Response response = RestAssured.get("/addressbook");
		log.info("CONTACT ENTRIES IN JSON SERVER:\n" + response.asString());
		ContactDetailsForRestAPI[] arrayOfContacts = new Gson().fromJson(response.asString(), ContactDetailsForRestAPI[].class);
		return arrayOfContacts;
	}
	
	@Test
	public void givenMuiltipleNewContactDetails_WhenAdded_ShouldMatch201ResponseAndCount() {
		ContactDetailsForRestAPI[] arraysOfContacts = getContactDetailsList();
		addressBookDBService = new AddressBookDBService(Arrays.asList(arraysOfContacts));
		ContactDetailsForRestAPI[] arrayOfContactDetails = {
				new ContactDetailsForRestAPI(4, "Ankush", "Sharan", "D-75", "Vadodara", "Gujarat", 353543, 3535252, "ankush@gmail.com"),
				new ContactDetailsForRestAPI(5, "Karan", "Singh", "E-75", "London", "UK", 32343, 35323252, "karan@gmail.com"),
				new ContactDetailsForRestAPI(6, "Rajendra", "Kushwaha", "F-75", "Brisbon", "Chile", 23232, 232424, "rajuking@gmail.com")
		};
		for(ContactDetailsForRestAPI contactEntry : arrayOfContactDetails) {
		Response response = addContactEntryToJsonServer(contactEntry);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(201, statusCode);
		
		contactEntry = new Gson().fromJson(response.asString(), ContactDetailsForRestAPI.class);
		addressBookDBService.addContactToAddressBook(contactEntry);
		}
		int entries = addressBookDBService.countEntriesForRest();
		Assert.assertEquals(6, entries);
	}

	private Response addContactEntryToJsonServer(ContactDetailsForRestAPI contactEntry) {
		String addressJson = new Gson().toJson(contactEntry);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(addressJson);
		return request.post("/addressbook");
	}
	
	@Test
	public void givenContactEntry_WhenCityUpdated_ShouldMatch200Response() {
		ContactDetailsForRestAPI[] arraysOfContacts = getContactDetailsList();
		addressBookDBService = new AddressBookDBService(Arrays.asList(arraysOfContacts));
		addressBookDBService.updateContactCityRestAPI("Karan", "Madrid");
		ContactDetailsForRestAPI contactData = addressBookDBService.getContactForRest("Karan");
		String addressJson = new Gson().toJson(contactData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(addressJson);
		Response response = request.put("/addressbook/" + contactData.id);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);
	}
	
	@Test
	public void givenContactEntry_WhenDeleted_ShouldMatch200Count() {
		ContactDetailsForRestAPI[] arraysOfContacts = getContactDetailsList();
		addressBookDBService = new AddressBookDBService(Arrays.asList(arraysOfContacts));
		ContactDetailsForRestAPI contactData = addressBookDBService.getContactForRest("Ankush");
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response response = request.delete("/addressbook/" + contactData.id);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);
		addressBookDBService.deleteContactFromAddressBook(contactData.firstName);
		int entries = addressBookDBService.countEntriesForRest();
		Assert.assertEquals(5, entries);
	}
}
