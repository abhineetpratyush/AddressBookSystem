package com.capgemini.addressbooksystem;

public class ContactDetails {
	public String firstName;
	public String lastName;
	public String fullName;
	public String address; 
	public String city;
	public String state;
	public int zip;
	public long phoneNo;
	public String emailId;

	public void setContactDetails(String firstName, String lastName, String address, String city, String state, int zip, long phoneNo, String emailId) { 
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = firstName.concat(lastName);
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNo = phoneNo;
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName + ", " + address + ", " + city + ", " + state + ", " + zip + ", " + phoneNo + ", " + emailId;
	}
}
