package com.capgemini.addressbooksystem;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class AddressBookToCSV {
	public static void createCSVFile(List<ContactDetails> contactList, String filePath) {
		try (Writer fileWriter = Files.newBufferedWriter(Paths.get(filePath));){
			StatefulBeanToCsv<ContactDetails> beanToCSV = new StatefulBeanToCsvBuilder<ContactDetails>(fileWriter)
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
					.build();
			beanToCSV.write(contactList);
		} catch(IOException e) {
			e.printStackTrace();
		}
		catch(CsvDataTypeMismatchException e) {
			e.printStackTrace();
		}
		catch(CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
		}
	}
}