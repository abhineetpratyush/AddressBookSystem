package com.capgemini.addressbooksystem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class AddressBookMain {
	private static final Logger log = LogManager.getLogger(AddressBookMain.class);

	private LinkedList<ContactDetails> contactLinkedList;
	public static Multimap<String, ContactDetails> cityToContactEntryMap = ArrayListMultimap.create();
	public static Multimap<String, ContactDetails> stateToContactEntryMap = ArrayListMultimap.create();

	private AddressBookMain() {
		contactLinkedList = new LinkedList<>();
	}

	private void addContactToAddressBook(int addressBookNo) {
		Scanner takeInput = new Scanner(System.in);
		log.info("How many entries you want to make in Address Book " + addressBookNo);
		int numOfEntries = takeInput.nextInt();
		takeInput.nextLine();
		for(int contactIndex = 0; contactIndex < numOfEntries; contactIndex++) {
			String firstName, lastName;
			int exitFlag;
			do{
				int counter = 0;
				log.info("First Name: ");
				firstName = takeInput.nextLine();
				log.info("Last Name: ");
				lastName = takeInput.nextLine();
				for(int i = 0; i < contactIndex; i++) 
					if(contactLinkedList.get(i).firstName.equals(firstName) && contactLinkedList.get(i).lastName.equals(lastName)) {
						counter++;
					}
				if(counter != 0) {
					log.info("This name already exists! Please enter again");
					exitFlag = 0;
				}
				else
					exitFlag = 1;
			}while(exitFlag == 0);
			log.info("Address: ");
			String address = takeInput.nextLine();
			log.info("City: ");
			String city = takeInput.nextLine();
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
			contactDetail.setContactDetails(firstName, lastName, address, city, state, zip, phoneNo, emailId);
			contactLinkedList.add(contactDetail);
			cityToContactEntryMap.put(city, contactDetail);
			stateToContactEntryMap.put(state, contactDetail);
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
		log.info("Edited City: ");
		String city = takeInput.nextLine();
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
				if(value.contactLinkedList.get(i).firstName.equals(firstName) && value.contactLinkedList.get(i).lastName.equals(lastName))
				{
					ContactDetails contactDetail = new ContactDetails();
					contactDetail.setContactDetails(firstName, lastName, address, city, state, zip, phoneNo, emailId);
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
				if(value.contactLinkedList.get(i).firstName.equals(firstName) && value.contactLinkedList.get(i).lastName.equals(lastName))
					log.info(value.contactLinkedList.get(i));
		}
	}

	private static void deleteContactDetailsSearchedByName(Map<String, AddressBookMain> addressBookMap) {
		Scanner takeInput = new Scanner(System.in);
		log.info("Enter First Name of person whose record is to be deleted: ");
		String firstName = takeInput.nextLine();
		log.info("Enter Last Name of person whose record is to be deleted: ");
		String lastName = takeInput.nextLine();
		for (Map.Entry<String, AddressBookMain> entry : addressBookMap.entrySet()) {
			AddressBookMain value = entry.getValue();
			for(int i = 0; i < value.contactLinkedList.size(); i++) 
				if(value.contactLinkedList.get(i).firstName.equals(firstName) && value.contactLinkedList.get(i).lastName.equals(lastName))
					value.contactLinkedList.remove(i);
		}
	}

	private static void searchContactByCity(String city) {
		Collection<ContactDetails> contactValues = cityToContactEntryMap.get(city);
		log.info(contactValues);
	}

	private static void searchContactByState(String state) {
		Collection<ContactDetails> contactValues = stateToContactEntryMap.get(state);
		log.info(contactValues);
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
			log.info("Choose an option\n1.EDIT\n2.DELETE\n3.DISPLAY\n4.SEARCH BY CITY\n5.SEARCH BY STATE\n6.EXIT");
			int menuChoice = takeInput.nextInt();
			takeInput.nextLine();
			switch(menuChoice) {
			case 1: editContactFromAddressBook(addressBookMap);
			break;
			case 2: deleteContactDetailsSearchedByName(addressBookMap);
			break;
			case 3: displayContactDetailsSearchedByName(addressBookMap);
			break;
			case 4: log.info("Enter city: ");
			String city = takeInput.nextLine();
			searchContactByCity(city);
			break;
			case 5: log.info("Enter state: ");
			String state = takeInput.nextLine();
			searchContactByState(state);
			break;
			default: exitFlag = 1;
			}
		} while(exitFlag == 0);

	}
}
