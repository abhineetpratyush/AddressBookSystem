package com.capgemini.addressbooksystem;

public class ContactDetailsForRestAPI {
	public int id;
	public String firstName;
	public String lastName;
	public String address;
	public String city;
	public String state;
	public int zip;
	public long phoneNo;
	public String emailId;
	
	public ContactDetailsForRestAPI(int id, String firstName, String lastName, String address, String city,
			String state, int zip, long phoneNo, String emailId) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNo = phoneNo;
		this.emailId = emailId;
	}
}
