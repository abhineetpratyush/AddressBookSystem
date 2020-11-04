package com.capgemini.addressbooksystem;

import com.opencsv.bean.CsvBindByName;

public class ContactDetails {
	@CsvBindByName
	public String firstName;
	@CsvBindByName
	public String lastName;
	@CsvBindByName
	public String address;
	@CsvBindByName
	public String city;
	@CsvBindByName
	public String state;
	@CsvBindByName
	public int zip;
	@CsvBindByName
	public long phoneNo;
	@CsvBindByName
	public String emailId;
	
	public ContactDetails() {}
	
	public ContactDetails(String firstName, String lastName, String address, String city, String state, int zip,
			long phoneNo, String emailId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNo = phoneNo;
		this.emailId = emailId;
	}

	public void setContactDetails(String firstName, String lastName, String address, String city, String state, int zip, long phoneNo, String emailId) { 
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNo = phoneNo;
		this.emailId = emailId;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName + ", " + address + ", " + city + ", " + state + ", " + zip + ", " + phoneNo + ", " + emailId;
	}
}
