package com.capgemini.addressbooksystem;

import java.util.ArrayList;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddressBookMain {
	private static final Logger log = LogManager.getLogger(AddressBookMain.class);

	private ArrayList<ContactDetails> contactArrayList;
	private AddressBookMain() {
		contactArrayList = new ArrayList<>(); 
	}

	private void addContactDetails(String firstName, String lastName, String address, String state, int zip, long phoneNo, String emailId) {
		ContactDetails contactDetail = new ContactDetails();
		contactDetail.setContactDetails(firstName, lastName, address, state, zip, phoneNo, emailId);
		contactArrayList.add(contactDetail);
	}

	private void editContactDetails(String firstName, String lastName, String address, String state, int zip, long phoneNo, String emailId) {
		for(int i = 0; i < contactArrayList.size(); i++) {
			ContactDetails contactDetail = contactArrayList.get(i);
			if(contactDetail.firstName.equals(firstName) && contactDetail.lastName.equals(lastName)) {
				contactDetail.setContactDetails(firstName, lastName, address, state, zip, phoneNo, emailId);
				contactArrayList.set(i, contactDetail);
				log.info("Address changed");
				log.info(contactArrayList.get(i));
				break;
			}
		}
	}

	private void deleteContactDetails(String firstName, String lastName) {
		for(int i = 0; i < contactArrayList.size(); i++) {
			ContactDetails contactDetail = contactArrayList.get(i);
			if(contactDetail.firstName.equals(firstName) && contactDetail.lastName.equals(lastName)) {
				contactArrayList.remove(i);
				log.info("Contact deleted");
				break;
			}
		} 
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		AddressBookMain addressBook = new AddressBookMain();
		log.info("No. of contact details to enter: ");
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
