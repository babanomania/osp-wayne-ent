package com.dchdemo.osp.wayneent.dbutil;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CustomersUtil {

	//http://localhost:8080/api/customer/Dent/Arthur
	public static Map<String, Customer> customers = new HashMap<String, Customer>();
	public static Map<String, Customer> customersCMS = new HashMap<String, Customer>();

	private static void loadData() throws Exception {
		
		ClassLoader classLoader = CustomersUtil.class.getClassLoader();
		File dataFile = Paths.get( classLoader.getResource("CustomerData.csv").toURI() ).toFile();
		
		Reader in = new FileReader(dataFile);
		
		Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
		for (CSVRecord record : records) {
			
			String firstName = record.get("First Name");
		    String lastName = record.get("Last Name");
		    String dob = record.get("DOB");
		    String email = record.get("email");
		    String statementEmail = record.get("Statement In Email");
		    String crmId = record.get("CRM ID");
		    String cmsId = record.get("CMS ID");
		    
		    Customer cust = new Customer();
		    cust.setFirstName(firstName);
		    cust.setLastName(lastName);
		    cust.setDateOfBirth(dob);
		    cust.setEmail(email);
		    cust.setStatementInEmail(statementEmail);
		    cust.setCrmid(crmId);
		    cust.setCmsid(cmsId);
		    
		    customers.put( firstName+lastName,  cust );
		    customersCMS.put( cmsId,  cust );
		}
		
	}
	
	static{
		try {
			loadData();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Customer getCustomerDetail( String firstName, String lastName ) {
		return customers.get(firstName+lastName);
	}
	
	public static Customer getCustomerDetail( String cmsId ) {
		return customersCMS.get(cmsId);
	}
	
	public static void saveCustomerDetail( Customer editerCustomer ) {
		customers.put( editerCustomer.getFirstName()+editerCustomer.getLastName(),  editerCustomer );
		customersCMS.put( editerCustomer.getCmsid(),  editerCustomer );
	}
	
}
