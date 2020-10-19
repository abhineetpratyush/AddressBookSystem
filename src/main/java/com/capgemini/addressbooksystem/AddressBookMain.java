package com.capgemini.addressbooksystem;

import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddressBookMain {
	private static final Logger log = LogManager.getLogger(AddressBookMain.class);

	private int numOfContact = 0;
	private ContactDetails[] contactArray;
	private AddressBookMain() {
		contactArray = new ContactDetails[5];
	}

	private void addContactDetails(String firstName, String lastName, String address, String state, int zip, long phoneNo, String emailId) {
		contactArray[numOfContact] = new ContactDetails();
		contactArray[numOfContact].setContactDetails(firstName, lastName, address, state, zip, phoneNo, emailId);
		numOfContact++;
	}

	private void editContactDetails(String firstName, String lastName, String address, String state, int zip, long phoneNo, String emailId) {
		for(int i = 0; i < numOfContact; i++) {
			if(contactArray[i].firstName.equals(firstName) && contactArray[i].lastName.equals(lastName)) {
				contactArray[i].setContactDetails(firstName, lastName, address, state, zip, phoneNo, emailId);
				log.info("Address changed");
				log.info(contactArray[i]);
				break;
			}
		}
	}

	private void deleteContactDetails(String firstName, String lastName) {
		for(int i = 0; i < numOfContact; i++) {
			if(contactArray[i].firstName.equals(firstName) && contactArray[i].lastName.equals(lastName)) {
				contactArray[i] = null;
				log.info("Contact deleted");
				break;
			}
		} 
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		AddressBookMain addressBook = new AddressBookMain();
		log.info("No. of contact details to enter (upto 5): ");
		int numOfContact = sc.nextInt();
		sc.nextLine();
		//adding
		for(int i = 0; i < numOfContact; i++) {
			log.info("First Name: ");
			String firstName = sc.nextLine();
			log.info("Last Name: ");
			String lastName = sc.nextLine();
			log.info("Address: ");
			String address = sc.nextLine();
			log.info("State: ");
			String state = sc.nextLine();
			log.info("ZIP: " );
			int zip = sc.nextInt();
			log.info("Phone No: ");
			long phoneNo = sc.nextLong();
			sc.nextLine();
			log.info("Email ID: ");
			String emailId = sc.nextLine();
			addressBook.addContactDetails(firstName, lastName, address, state, zip, phoneNo, emailId);
		}

		//editing
		log.info("Enter details for contact record you want to edit");
		log.info("First Name: ");
		String firstName = sc.nextLine();
		log.info("Last Name: ");
		String lastName = sc.nextLine();
		log.info("Address: ");
		String address = sc.nextLine();
		log.info("State: ");
		String state = sc.nextLine();
		log.info("ZIP: " );
		int zip = sc.nextInt();
		log.info("Phone No: ");
		long phoneNo = sc.nextLong();
		sc.nextLine();
		log.info("Email ID: ");
		String emailId = sc.nextLine();
		addressBook.editContactDetails(firstName, lastName, address, state, zip, phoneNo, emailId);

		//deleting
		log.info("Enter first name of contact to be deleted: ");
		firstName = sc.nextLine();
		log.info("Enter last name of contact to be deleted: ");
		lastName = sc.nextLine();
		addressBook.deleteContactDetails(firstName, lastName);		
	}
}
