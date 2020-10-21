package com.capgemini.addressbooksystem;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CSVToAddressBook {
	public static int createAddressBook(String contactDetailsCSVFilePath) {	
		AddressBookMain addressBook = new AddressBookMain();
		try {
			Reader reader = Files.newBufferedReader(Paths.get(contactDetailsCSVFilePath)); 
			CsvToBean<ContactDetails> csvToBean = new CsvToBeanBuilder<ContactDetails>(reader)
					.withType(ContactDetails.class)
					.withIgnoreLeadingWhiteSpace(true)
					.build();
			Iterator<ContactDetails> contactDetailsIterator = csvToBean.iterator();
			while (contactDetailsIterator.hasNext()) {
				ContactDetails csvContactEntry = contactDetailsIterator.next();
				ContactDetails contactDetails = new ContactDetails();
				contactDetails.setContactDetails(csvContactEntry.getFirstName(), 
						csvContactEntry.getLastName(), 
						csvContactEntry.getAddress(),
						csvContactEntry.getCity(),
						csvContactEntry.getState(),
						csvContactEntry.getZip(), 
						csvContactEntry.getPhoneNo(),
						csvContactEntry.getEmailId());
				addressBook.addContactDirectlyToContactLinkedList(contactDetails);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		return addressBook.getContactLinkedList().size();
	}
}
