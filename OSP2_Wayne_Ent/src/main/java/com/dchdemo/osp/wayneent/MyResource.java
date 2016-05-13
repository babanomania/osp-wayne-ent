package com.dchdemo.osp.wayneent;

import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.dchdemo.osp.wayneent.dbutil.Customer;
import com.dchdemo.osp.wayneent.dbutil.CustomersUtil;
import com.dchdemo.osp.wayneent.dbutil.Policy;
import com.dchdemo.osp.wayneent.dbutil.PolicyUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("api")
public class MyResource {

	private static String OSP_CONFIG = "osp_config.properties";
	private static Properties ospConfig = new Properties();
	
	private static String url_isb;
	
	static{
		try
		{
			if(! StringUtils.isEmpty(System.getenv( "url_isb" )) ){
				url_isb = System.getenv( "url_isb" );
			}else{
				ospConfig.load( MyResource.class.getClassLoader().getResourceAsStream( OSP_CONFIG ) );
				url_isb = ospConfig.getProperty("url_isb");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
    @GET
    @Path("customer/{lastname}/{firstname}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Customer getCustomerDetails(	@PathParam("lastname") String lastname, @PathParam("firstname") String firstname	){
    	return CustomersUtil.getCustomerDetail(firstname, lastname);
    }
    
    @POST
    @Path("customer")
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public void editCustomerDetails( Customer editedCustomer ) throws Exception{
    	CustomersUtil.saveCustomerDetail(editedCustomer);
    	saveToCrm( editedCustomer );
    }
    
    public void saveToCrm( Customer editedCustomer ) throws Exception {
    	
    	ObjectMapper mapper = new ObjectMapper();
    	String jsonCustomer = mapper.writeValueAsString(editedCustomer);
    	String uri = "/crm/customer";
    	
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	HttpPut httpput = new HttpPut( url_isb + uri );
    	httpput.addHeader("Accept", "application/json");
    	
    	HttpEntity entity = new StringEntity( jsonCustomer );
    	httpput.setEntity(entity);
    	
    	// Create a custom response handler
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

            @Override
            public String handleResponse( final HttpResponse response	) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                	
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                    
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }

        };

        String responseBody = httpclient.execute(httpput, responseHandler);
        System.out.println( "response after saving to crm : " + responseBody );
    	
    }
    
    @GET
    @Path("policy/{polNum}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Policy getPolicyDetails(	@PathParam("polNum") String polNum	){
    	return PolicyUtil.getPolicyDetail(polNum);
    }
    
    @POST
    @Path("policy")
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public void editPolicyDetails( Policy editedPolicy	){
    	PolicyUtil.savePolicyDetail(editedPolicy);
    }
}
