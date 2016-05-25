package com.dchdemo.osp.wayneent;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("api/customer")
public class CustomerEndpoint extends BaseEndpoint {
    
    @GET
    @Path("{lastname}/{firstname}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Customer getCustomerDetails(	@PathParam("lastname") String lastname, 
    									@PathParam("firstname") String firstname	){
    	
    	Customer data = CustomersUtil.getCustomerDetail(firstname, lastname);
    	if( data == null ){
    		throw new NotFoundException("no such customer");
    	}
    	
    	return data;
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public void editCustomerDetails( Customer editedCustomer ) throws Exception{
    	
    	CustomersUtil.saveCustomerDetail(editedCustomer);
    	syncCustomerUpdate( editedCustomer );
    }
    
    private void syncCustomerUpdate( Customer editedCustomer ) throws Exception {
    	
    	ObjectMapper mapper = new ObjectMapper();
    	String jsonCustomer = mapper.writeValueAsString(editedCustomer);
    	String uri = "/api/customer";
    	
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
    
}
