package com.dchdemo.osp.wayneent.dbutil;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class PolicyUtil {

	public static Map<String, Policy> policies = new HashMap<String, Policy>();

	private static void loadData() throws Exception {
		
		ClassLoader classLoader = CustomersUtil.class.getClassLoader();
		File dataFile = Paths.get( classLoader.getResource("PolicyData.csv").toURI() ).toFile();
		
		Reader in = new FileReader(dataFile);
		
		Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
		for (CSVRecord record : records) {
			
			String polNumber = record.get("Policy No");
		    String role = record.get("Role");
		    String lastPremPaid = record.get("Last Prem Paid");
		    String status = record.get("Status");
		    
		    Policy pol = new Policy();
		    pol.setPolicyNumber(polNumber);
		    pol.setRole(role);
		    pol.setLastPremiumPaid(lastPremPaid);
		    pol.setStatus(status);
		    policies.put( polNumber,  pol );
		}
		
	}
	
	static{
		try {
			loadData();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Policy getPolicyDetail( String policyNumber ) {
		return policies.get(policyNumber);
	}
	
	public static void savePolicyDetail( Policy policyData ) {
		policies.put( policyData.getPolicyNumber(), policyData );
	}
}
