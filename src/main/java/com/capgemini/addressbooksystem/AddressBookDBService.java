package com.capgemini.addressbooksystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AddressBookDBService {

	private List<ContactDetails> addressBookList = new ArrayList<>();
	private static final Logger log = LogManager.getLogger(AddressBookDBService.class);
	private PreparedStatement preparedStatement;
	private PreparedStatement preparedStatemetForRetrieval;

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
				throw new CustomJDBCException(ExceptionType.SQL_EXCEPTION);
			}
	}
}
