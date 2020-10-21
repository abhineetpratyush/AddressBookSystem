package com.capgemini.addressbooksystem;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonFileToAddressBook {
	public static int createAddressBook(String contactDetailsJsonFilePath) {
		AddressBookMain addressBook = new AddressBookMain();
		try {
			Reader reader = Files.newBufferedReader(Paths.get(contactDetailsJsonFilePath));
			List<ContactDetails> contactList = new Gson().fromJson(reader, new TypeToken<List<ContactDetails>>() {}.getType());
			contactList.stream().forEach(individualContactEntry -> addressBook.addContactDirectlyToContactLinkedList(individualContactEntry));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return addressBook.getContactLinkedList().size();
	}
}
