package com.msil.irecruit.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class Util {

	
	public static String getTestName(String jobId,String baseServerUrl,String appName) {
		
		
        StringBuilder sb = new StringBuilder();
        String testName="";
        try { 
        	
        	// url="http://localhost:8081/ExideIndustries/pa/deleteAccesskey?"+accesskey;
        	 URL url = new URL("http://localhost:8081/ExideIndustries/pa/deleteAccesskey");
        	// URL url = new URL("http://localhost:4001/sales/simulation/insurance/getTestType");
   			 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
   			 conn.setDoOutput(true);
   			conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
   			 conn.setRequestMethod("GET");
   			 String urlParameters  = "jobId="+jobId;
        	
   			 OutputStream os = conn.getOutputStream();
   			 os.write(urlParameters.getBytes());
   			 os.flush();
   			 if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
   				 
   			  BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
              
              String line;
              while ((line = reader.readLine()) != null) {
                  sb.append(line);
              }
              reader.close();
              
   			 }
   			 else{
   				 System.out.println( "Connection Refused ************");
   			 }
          
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
       return testName;
		
	}
	
}
