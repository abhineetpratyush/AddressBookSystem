package com.capgemini.addressbooksystem;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class AddressBookFileTest {

	@Test
	public void given2ContactsDetails_WhenWrittenToFile_ShouldPassTheTest() {
		ContactDetails personOne = new ContactDetails();
		personOne.setContactDetails("Abhineet", "Pratyush", "XYZ", "Ranchi", "Jharkhand", 834003, 1234567891, "abc@gmail.com");
		ContactDetails personTwo = new ContactDetails();
		personTwo.setContactDetails("Ram", "Sharma", "LMN", "Pune", "Maharashtra", 123456, 1112345678, "ram@gmail.com");
		List<ContactDetails> contactList = Arrays.asList(new ContactDetails[] {personOne, personTwo});
		AddressBookFileIOService addressBookFileIOServiceObj =new AddressBookFileIOService();
		addressBookFileIOServiceObj.writeContactDetails(contactList);
		List<ContactDetails> contactListRead=addressBookFileIOServiceObj.readContactDetails();
		Assert.assertEquals(personOne.toString(), contactListRead.get(0).toString());
		Assert.assertEquals(personTwo.toString(), contactListRead.get(1).toString());
	}
	
	@Test
	public void givenCSVFileWithContactDetails_WhenInitializedIntoAddressBook_ShouldPassTheTest() {
		int numOfContactEntriesMade = CSVToAddressBook.createAddressBook("./src/main/resources/list-of-contacts.csv");
		Assert.assertEquals(3, numOfContactEntriesMade);
	}
	
	@Test
	public void given2ContactsDetails_WhenWrittenToCSVFile_ShouldPassTheTest() {
		ContactDetails personOne = new ContactDetails();
		personOne.setContactDetails("Abhineet", "Pratyush", "XYZ", "Ranchi", "Jharkhand", 834003, 1234567891, "abc@gmail.com");
		ContactDetails personTwo = new ContactDetails();
		personTwo.setContactDetails("Ram", "Sharma", "LMN", "Pune", "Maharashtra", 123456, 1112345678, "ram@gmail.com");
		List<ContactDetails> contactList = Arrays.asList(new ContactDetails[] {personOne, personTwo});
		AddressBookToCSV.createCSVFile(contactList, "./src/main/resources/csv-file-from-addressbook.csv");
		int numOfContactEntriesMade = CSVToAddressBook.createAddressBook("./src/main/resources/csv-file-from-addressbook.csv");
		Assert.assertEquals(2, numOfContactEntriesMade);
	}
	
	@Test
	public void givenJsonFile_WhenAddedToAddressBook_ShouldReturnCountOf2() {
		int numOfContactEntriesMade = JsonFileToAddressBook.createAddressBook("./src/main/resources/contacts-json-file.json");
		Assert.assertEquals(2, numOfContactEntriesMade);
	}
	
	@Test
	public void given2ContactsDetails_WhenWrittenToJsonFile_ShouldPassTheTest() throws IOException {
		ContactDetails personOne = new ContactDetails();
		personOne.setContactDetails("Abhineet", "Pratyush", "XYZ", "Ranchi", "Jharkhand", 834003, 1234567891, "abc@gmail.com");
		ContactDetails personTwo = new ContactDetails();
		personTwo.setContactDetails("Ram", "Sharma", "LMN", "Pune", "Maharashtra", 123456, 1112345678, "ram@gmail.com");
		List<ContactDetails> contactList = Arrays.asList(new ContactDetails[] {personOne, personTwo});
		AddressBookToJsonFile.createJsonFile(contactList, "./src/main/resources/json-file-from-addressbook.json");
		int numOfContactEntriesMade = JsonFileToAddressBook.createAddressBook("./src/main/resources/json-file-from-addressbook.json");
		Assert.assertEquals(2, numOfContactEntriesMade);
	}
}
