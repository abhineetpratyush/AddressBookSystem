package com.capgemini.addressbooksystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class AddressBookFileIOService {
	public static String ADDRESSBOOK_FILE_NAME = "address-book.txt";

	public void writeContactDetails(List<ContactDetails> contactList) {
		StringBuffer contactsBuffer = new StringBuffer();
		contactList.forEach(contact -> contactsBuffer.append(contact.toString().concat("\n")));
		try {
			Files.write(Paths.get(ADDRESSBOOK_FILE_NAME), contactsBuffer.toString().getBytes());
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	public List<ContactDetails> readContactDetails(){
		List<ContactDetails> contactList = null;
		try {
			contactList = Files.lines(new File(ADDRESSBOOK_FILE_NAME).toPath())
					.map(singleContactEntry -> singleContactEntry.trim())
					.map(singleContactEntry -> {
						String[] contactDetailsArray = singleContactEntry.split(", ");
						ContactDetails contactEntry = new ContactDetails();
						contactEntry.setContactDetails(contactDetailsArray[0].split(" ")[0],
								contactDetailsArray[0].split(" ")[1],
								contactDetailsArray[1],
								contactDetailsArray[2],
								contactDetailsArray[3],
								Integer.parseInt(contactDetailsArray[4]),
								Long.parseLong(contactDetailsArray[5]),
								contactDetailsArray[6]
								);
						return contactEntry;
					}).collect(Collectors.toList());
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return contactList;
	}
}
