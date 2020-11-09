package com.capgemini.addressbooksystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddressBookDBService {

	private List<ContactDetails> addressBookList;
	private List<ContactDetailsForRestAPI> addressBookListForRest;
	private static final Logger log = LogManager.getLogger(AddressBookDBService.class);
	private PreparedStatement preparedStatement;
	private PreparedStatement preparedStatemetForRetrieval;
	
	public AddressBookDBService(List<ContactDetailsForRestAPI> contactDetailsList) {
		addressBookListForRest = new ArrayList<>(contactDetailsList);
	}
	
	public AddressBookDBService() {}
	private Connection getConnection() throws CustomJDBCException {
		String jdbcURL = "jdbc:mysql://localhost:3306/addressbook_service?useSSL=false";
		String userName = "root";
		String password = "abcd4321";
		Connection connection;
		log.info("Connecting to database: " + jdbcURL);
		try {
			connection = DriverManager.getConnection(jdbcURL, userName, password);
			log.info("Connection is successful!! " + connection);
			return connection;
		} catch (SQLException e) {
			throw new CustomJDBCException(ExceptionType.UNABLE_TO_ESTABLISH_CONNECTION);
		}
	}

	private List<ContactDetails> getAddressBookListFromResultSet(ResultSet resultSet) throws CustomJDBCException {
		List<ContactDetails> addressBookList = new ArrayList<>();
		try{
			while(resultSet.next()) {
				String emailId = resultSet.getString("email_id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String address = resultSet.getString("address");
				String city = resultSet.getString("city");
				String state = resultSet.getString("state");
				int zip = resultSet.getInt("zip");
				long phoneNumber = resultSet.getLong("phone_number");
				addressBookList.add(new ContactDetails(firstName, lastName, address, city, state, zip, phoneNumber, emailId));
			}
			return addressBookList;
		} catch(SQLException e) {
			throw new CustomJDBCException(ExceptionType.UNABLE_TO_UPDATE_ADDRESS_BOOK_LIST);
		}
	}
	
	private void prepareStatementForAddressBook() throws CustomJDBCException {
		try {
			Connection connection = this.getConnection();
			String sql = "update address_details set city = ? where first_name = ?";
			this.preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}catch (SQLException e) {
			throw new CustomJDBCException(ExceptionType.UNABLE_TO_USE_PREPARED_STATEMENT);
		}
	}
	
	private ContactDetails getContact(String firstName) {
		return this.addressBookList.stream()
				.filter(addressBookDataItem -> addressBookDataItem.firstName.equals(firstName))
				.findFirst()
				.orElse(null);
	}
	
	public ContactDetailsForRestAPI getContactForRest(String firstName) {
		return this.addressBookListForRest.stream()
				.filter(addressBookDataItem -> addressBookDataItem.firstName.equals(firstName))
				.findFirst()
				.orElse(null);
	}
	
	private void prepareStatementForAddressBookDataRetrieval() throws CustomJDBCException {
		try {
			Connection connection = this.getConnection();
			String sql = "select * from address_details where first_name = ?";
			this.preparedStatemetForRetrieval = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			throw new CustomJDBCException(ExceptionType.SQL_EXCEPTION);
		}

	}
	
	public List<ContactDetails> getAddressBookEntriesFromDB(String firstName) throws CustomJDBCException  {
		if (this.preparedStatemetForRetrieval == null) {
			this.prepareStatementForAddressBookDataRetrieval();
		}
		try (Connection connection = this.getConnection()) {
			this.preparedStatemetForRetrieval.setString(1, firstName);
			ResultSet resultSet = preparedStatemetForRetrieval.executeQuery();
			return getAddressBookListFromResultSet(resultSet);
		} catch (SQLException e) {
			throw new CustomJDBCException(ExceptionType.SQL_EXCEPTION);
		}
	}
	
	public List<ContactDetails> readContactsFromDB() throws CustomJDBCException {
		String sql = "select * from address_details;"; 
		try (Connection connection = this.getConnection()){
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = statement.executeQuery(sql);
			this.addressBookList = this.getAddressBookListFromResultSet(resultSet);
			return addressBookList;
		} catch (SQLException e) {
			throw new CustomJDBCException(ExceptionType.UNABLE_TO_READ_RECORDS_FROM_DB);
		}
	}

	public void updateContactCity(String firstName, String city) throws CustomJDBCException {
		if(this.preparedStatement == null) {
			this.prepareStatementForAddressBook();
		}
		try {
			preparedStatement.setString(1, city);
			preparedStatement.setString(2, firstName);
			int rowsAffected = preparedStatement.executeUpdate();
			if(rowsAffected == 0)
				throw new CustomJDBCException(ExceptionType.RECORD_UPDATE_FAILURE);
			ContactDetails contactEntry = this.getContact(firstName);
			if(contactEntry != null) 
				contactEntry.city = city;
		} catch(SQLException e) {
			throw new CustomJDBCException(ExceptionType.UNABLE_TO_USE_PREPARED_STATEMENT);
		}
	}		

	public boolean checkAddressBookInSyncWithDB(String firstName) throws CustomJDBCException {
		List<ContactDetails> addressBookList = getAddressBookEntriesFromDB(firstName);
		return addressBookList.get(0).equals(getContact(firstName));
	}

	public List<ContactDetails> getAddressBookDataInDateRange(LocalDate startDate, LocalDate endDate) throws CustomJDBCException {
			String sql = String.format("select * from address_details where date_added between "
					+ "cast('%s' as date) and cast('%s' as date);",
					startDate.toString(), endDate.toString());
			try (Connection connection = this.getConnection()) {
				Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet resultSet = statement.executeQuery(sql);
				return this.getAddressBookListFromResultSet(resultSet);
			} catch (SQLException e) {
				throw new CustomJDBCException(ExceptionType.UNABLE_TO_GET_CONTACTS_IN_DATE_RANGE);
			}
	}

	public List<ContactDetails> getAddressBookDataOnCity(String city) throws CustomJDBCException {
		String sql = String.format("select * from address_details where city = '%s'", city);
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = statement.executeQuery(sql);
			return this.getAddressBookListFromResultSet(resultSet);
		} catch (SQLException e) {
			throw new CustomJDBCException(ExceptionType.UNABLE_TO_GET_CONTACTS_IN_CITY);
		}
	}

	public List<ContactDetails> getAddressBookDataOnState(String state) throws CustomJDBCException {
		String sql = String.format("select * from address_details where state = '%s'", state);
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = statement.executeQuery(sql);
			return this.getAddressBookListFromResultSet(resultSet);
		} catch (SQLException e) {
			throw new CustomJDBCException(ExceptionType.UNABLE_TO_GET_CONTACTS_IN_STATE);
		}
	}

	public void addContactEntryToDB(String firstName, String lastName, String emailId, String address, String city,
			String state, int zip, long phoneNumber, String dateAdded, String addressBookName) throws CustomJDBCException {
		Connection connection = null;
		connection = this.getConnection();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e1) {
			throw new CustomJDBCException(ExceptionType.UNABLE_TO_SET_AUTO_COMMIT);
		}
		try(Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)){
			String sql = String.format("insert into address_details" +
					" values ('%s', '%s', '%s', '%s', '%s', '%s', %s, %s, '%s')", emailId, firstName, lastName, address, city, state, zip,  phoneNumber, dateAdded);
			statement.executeUpdate(sql);
			} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new CustomJDBCException(ExceptionType.UNABLE_TO_ROLLBACK);
			}
			throw new CustomJDBCException(ExceptionType.UNABLE_TO_ADD_RECORD_TO_DB);
		}
		try(Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)){
			String sql = String.format("insert into address_details_addressbook_name " + 
					"values ('%s', '%s')", emailId, addressBookName);
			statement.executeUpdate(sql);
		} catch(SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new CustomJDBCException(ExceptionType.UNABLE_TO_ROLLBACK);
			}
			throw new CustomJDBCException(ExceptionType.UNABLE_TO_ADD_RECORD_TO_DB);
		}
		try {
			connection.commit();
		} catch (SQLException e) {
			throw new CustomJDBCException(ExceptionType.UNABLE_TO_COMMIT);
		} finally {
			if(connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					throw new CustomJDBCException(ExceptionType.UNABLE_TO_CLOSE_CONNECTION);
				}
		}
		addressBookList.add(new ContactDetails(firstName, lastName, address, city, state, zip, phoneNumber, emailId));
	}

	public void addMultipleContactsToDB(List<ContactDetailsForMultithreading> contactDetailsDataList) {
		Map<Integer, Boolean> contactAdditionStatus = new HashMap<>();
		contactDetailsDataList.forEach(contactEntry ->
		{
			Runnable task = () -> {
				contactAdditionStatus.put(contactEntry.hashCode(), false);
				log.info("Contact Entry Being Added: "+Thread.currentThread().getName());
				try {
					this.addContactEntryToDB(contactEntry.firstName, contactEntry.lastName, contactEntry.emailId, contactEntry.address, 
							contactEntry.city, contactEntry.state, contactEntry.zip, 
							contactEntry.phoneNo, contactEntry.dateAdded, contactEntry.addressBookName);
				} catch (CustomJDBCException e) {
					log.info("Unable to add contact entry to DB");
				}
				contactAdditionStatus.put(contactEntry.hashCode(), true);
				log.info("Contact Entry Added: " + Thread.currentThread().getName());
			};
			Thread thread = new Thread(task, contactEntry.firstName);
			thread.start();
		}
				);
		while(contactAdditionStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch(InterruptedException e) {
				log.info("Unable to sleep");
			}
		}
	}

	public int countEntries() {
		return addressBookList.size();
	}
	
	public int countEntriesForRest() {
		return addressBookListForRest.size();
	}

	public void addContactToAddressBook(ContactDetailsForRestAPI contactEntry) {
		this.addressBookListForRest.add(new ContactDetailsForRestAPI(contactEntry.id, contactEntry.firstName, 
				contactEntry.lastName, contactEntry.address, contactEntry.city, 
				contactEntry.state, contactEntry.zip, contactEntry.phoneNo, contactEntry.emailId));
	}

	public void updateContactCityRestAPI(String firstName, String newCity) {
		ContactDetailsForRestAPI contactData = this.getContactForRest(firstName);
		if(contactData != null) 
			contactData.city = newCity;
	}

	public void deleteContactFromAddressBook(String firstName) {
		ContactDetailsForRestAPI contactData = this.getContactForRest(firstName);
		addressBookListForRest.remove(contactData);
	}
}
