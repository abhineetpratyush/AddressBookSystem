package com.capgemini.addressbooksystem;

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
		AddressBookMain contact = new AddressBookMain("Abhineet", "Pratyush", "EPIP, Bengaluru", "Karnataka", 123456, 1234567891, "xyz@gmail.com");
		log.info(contact);
	}
}
