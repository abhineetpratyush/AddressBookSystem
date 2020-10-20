package com.capgemini.addressbooksystem;

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
}
