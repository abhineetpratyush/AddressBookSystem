package com.capgemini.addressbooksystem;

public class ContactDetailsForMultithreading {
	public String firstName;
	public String lastName;
	public String address;
	public String city;
	public String state;
	public int zip;
	public long phoneNo;
	public String emailId;
	public String dateAdded;
	public String addressBookName;
	
	public ContactDetailsForMultithreading(String firstName, String lastName, String address, String city, String state,
			int zip, long phoneNo, String emailId, String addressBookName, String dateAdded) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNo = phoneNo;
		this.emailId = emailId;
		this.addressBookName = addressBookName;
		this.dateAdded = dateAdded;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((addressBookName == null) ? 0 : addressBookName.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((dateAdded == null) ? 0 : dateAdded.hashCode());
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + (int) (phoneNo ^ (phoneNo >>> 32));
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + zip;
		return result;
	}
}
