package com.capgemini.addressbooksystem;

public class ContactDetails {
	public String firstName;
	public String lastName;
	private String address; 
	private String state;
	private int zip;
	private long phoneNo;
	private String emailId;

	public void setContactDetails(String firstName, String lastName, String address, String state, int zip, long phoneNo, String emailId) { 
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
		return "Name: " + firstName + " " + lastName + " Address: " + address + " State: " + state + " Zip: " + zip + " Phone No: " + phoneNo + " Email: " + emailId;
	}
}
