package com.capgemini.addressbooksystem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddressBookMain {
	private static final Logger log = LogManager.getLogger(AddressBookMain.class);

	private LinkedList<ContactDetails> contactLinkedList;
	private AddressBookMain() {
		contactLinkedList = new LinkedList<>();
	}

	private void addContactToAddressBook(int addressBookNo) {
		Scanner takeInput = new Scanner(System.in);
		log.info("How many entries you want to make in Address Book " + addressBookNo);
		int numOfEntries = takeInput.nextInt();
		takeInput.nextLine();
		for(int contactIndex = 0; contactIndex < numOfEntries; contactIndex++) {
			log.info("First Name: ");
			String firstName = takeInput.nextLine();
			log.info("Last Name: ");
			String lastName = takeInput.nextLine();
			log.info("Address: ");
			String address = takeInput.nextLine();
			log.info("State: ");
			String state = takeInput.nextLine();
			log.info("ZIP: " );
			int zip = takeInput.nextInt();
			log.info("Phone No: ");
			long phoneNo = takeInput.nextLong();
			takeInput.nextLine();
			log.info("Email ID: ");
			String emailId = takeInput.nextLine();
			ContactDetails contactDetail = new ContactDetails();
			contactDetail.setContactDetails(firstName, lastName, address, state, zip, phoneNo, emailId);
			contactLinkedList.add(contactDetail);
		}
	}

	private static void editContactFromAddressBook(Map<String, AddressBookMain> addressBookMap) {
		Scanner takeInput = new Scanner(System.in);
		log.info("Original First Name of the person whose record is to be edited: ");
		String firstName = takeInput.nextLine();
		log.info("Original Last Name of the person whose record is to be edited: ");
		String lastName = takeInput.nextLine();
		log.info("Edited Address: ");
		String address = takeInput.nextLine();
		log.info("Edited State: ");
		String state = takeInput.nextLine();
		log.info("Edited ZIP: " );
		int zip = takeInput.nextInt();
		log.info("Edited Phone No: ");
		long phoneNo = takeInput.nextLong();
		takeInput.nextLine();
		log.info("Edited Email ID: ");
		String emailId = takeInput.nextLine();
		for (Map.Entry<String, AddressBookMain> entry : addressBookMap.entrySet()) {
			AddressBookMain value = entry.getValue();
			for(int i = 0; i < value.contactLinkedList.size(); i++) 
				if(value.contactLinkedList.get(i).firstName.contains(firstName) && value.contactLinkedList.get(i).lastName.contains(lastName))
				{
					ContactDetails contactDetail = new ContactDetails();
					contactDetail.setContactDetails(firstName, lastName, address, state, zip, phoneNo, emailId);
					value.contactLinkedList.set(i, contactDetail);
				}
		}
	}

	private static void displayContactDetailsSearchedByName(Map<String, AddressBookMain> addressBookMap) {
		Scanner takeInput = new Scanner(System.in);
		log.info("Enter First Name of person whose record is to be displayed: ");
		String firstName = takeInput.nextLine();
		log.info("Enter Last Name of person whose record is to be displayed: ");
		String lastName = takeInput.nextLine();
		for (Map.Entry<String, AddressBookMain> entry : addressBookMap.entrySet()) {
			AddressBookMain value = entry.getValue();
			for(int i = 0; i < value.contactLinkedList.size(); i++) 
				if(value.contactLinkedList.get(i).firstName.contains(firstName) && value.contactLinkedList.get(i).lastName.contains(lastName))
					log.info(value.contactLinkedList.get(i));
		}
	}

	public static void deleteContactDetailsSearchedByName(Map<String, AddressBookMain> addressBookMap) {
		Scanner takeInput = new Scanner(System.in);
		log.info("Enter First Name of person whose record is to be deleted: ");
		String firstName = takeInput.nextLine();
		log.info("Enter Last Name of person whose record is to be deleted: ");
		String lastName = takeInput.nextLine();
		for (Map.Entry<String, AddressBookMain> entry : addressBookMap.entrySet()) {
			AddressBookMain value = entry.getValue();
			for(int i = 0; i < value.contactLinkedList.size(); i++) 
				if(value.contactLinkedList.get(i).firstName.contains(firstName) && value.contactLinkedList.get(i).lastName.contains(lastName))
					value.contactLinkedList.remove(i);
		}
	}

	public static void main(String[] args) {
		Scanner takeInput = new Scanner(System.in);
		Map<String, AddressBookMain> addressBookMap = new HashMap<>();
		log.info("How many address books need to be created? ");
		int noOfAddressBooks = takeInput.nextInt();
		takeInput.nextLine();
		AddressBookMain[] addressBookArray = new AddressBookMain[noOfAddressBooks];
		for(int addressBookIndex = 0; addressBookIndex < noOfAddressBooks; addressBookIndex++) {
			log.info("Enter name for Address Book " + (addressBookIndex + 1) + ": ");
			String addressBookName = takeInput.nextLine();
			addressBookArray[addressBookIndex] = new AddressBookMain();
			addressBookArray[addressBookIndex].addContactToAddressBook(addressBookIndex + 1);
			addressBookMap.put(addressBookName, addressBookArray[addressBookIndex]);
		}
		int exitFlag = 0;
		do {
			log.info("Choose an option\n1.EDIT\n2.DELETE\n3.DISPLAY\n4.EXIT");
			int menuChoice = takeInput.nextInt();
			switch(menuChoice) {
			case 1: editContactFromAddressBook(addressBookMap);
			break;
			case 2: deleteContactDetailsSearchedByName(addressBookMap);
			break;
			case 3: displayContactDetailsSearchedByName(addressBookMap);
			break;
			default: exitFlag = 1;
			}
		} while(exitFlag == 0);

	}
}
