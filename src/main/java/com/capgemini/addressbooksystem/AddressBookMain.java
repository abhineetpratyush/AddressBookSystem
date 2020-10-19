package com.capgemini.addressbooksystem;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddressBookMain {
	private static final Logger log = LogManager.getLogger(AddressBookMain.class);

	private String firstName;
	private String lastName;
	private String address; 
	private String state;
	private int zip;
	private long phoneNo;
	private String emailId;

	public AddressBookMain(String firstName, String lastName, String address, String state, int zip, long phoneNo, String emailId) { 
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.state = state;
		this.zip = zip;
		this.phoneNo = phoneNo;
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "Name: " + firstName + " " + lastName + " Address: " + address;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
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
		AddressBookMain contact = new AddressBookMain(firstName, lastName, address, state, zip, phoneNo, emailId);
		log.info(contact);
	}
}
