package com.capgemini.addressbooksystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddressBookDBService {

	private List<ContactDetails> addressBookList = new ArrayList<>();
	private static final Logger log = LogManager.getLogger(AddressBookDBService.class);

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

	public List<ContactDetails> readContactsFromDB() throws CustomJDBCException {
		String sql = "select * from address_details;"; 
		try (Connection connection = this.getConnection()){
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = statement.executeQuery(sql);
			this.getAddressBookListFromResultSet(resultSet);
			return addressBookList;
		} catch (SQLException e) {
			throw new CustomJDBCException(ExceptionType.UNABLE_TO_READ_RECORDS_FROM_DB);
		}
	}

	private void getAddressBookListFromResultSet(ResultSet resultSet) throws CustomJDBCException {
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
		} catch(SQLException e) {
			throw new CustomJDBCException(ExceptionType.UNABLE_TO_UPDATE_ADDRESS_BOOK_LIST);
		}
	}
}
