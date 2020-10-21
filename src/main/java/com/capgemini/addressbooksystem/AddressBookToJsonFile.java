package com.capgemini.addressbooksystem;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;

public class AddressBookToJsonFile {
	public static void createJsonFile(List<ContactDetails> contactList, String jsonFilePath) throws IOException {
		FileWriter fileWriter = new FileWriter(jsonFilePath);
		String jsonRep = new Gson().toJson(contactList);
		fileWriter.write(jsonRep);
		fileWriter.close();
	}
}
