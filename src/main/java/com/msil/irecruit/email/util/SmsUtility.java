package com.msil.irecruit.email.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URL;


import java.io.*;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.security.cert.Certificate;
import java.text.SimpleDateFormat;

public class SmsUtility {


/*	public static String sendSmsHandler( String mobileNumber, String msg,String senderId ) {	
	String url=	"https://sms4.tatacommunications.com:3821/sendsms?username=m_msil_lapp&password=w8P3CxnF&from="+senderId+"&to="+mobileNumber+"&text="+msg;
	String user="";  
	 try {
	    RestTemplate template = new RestTemplate();
		 user = template.getForObject(url, String.class);	
		}catch(Exception e) {
			System.out.println("error  sms......"+e);
		}*/

	/* public static String sendSmsHandler( String mobileNumber, String name ) { 
		System.out.println("Name : "+name+", Mobile : "+mobileNumber);
		String url ="https://api.myvfirst.com/psms/servlet/psms.Eservice2?action=send&data=<?xml "
				+ "	 version='1.0' encoding='ISO-8859-1'?><!DOCTYPE MESSAGE SYSTEM "
				+ " 'https://127.0.0.1/psms/dtd/messagev12.dtd' ><MESSAGE VER='1.2'><USER USERNAME='exon' "
				+ " PASSWORD='exon1245'/><SMS UDH='0' CODING='1' TEXT='Dear Candidate! The details of the DEMO TEST have been sent to your registered email id, kindly check once. Team Armezo  forwarded by Armezo' PROPERTY='0' ID='1'> <ADDRESS "
				+ " FROM='Armezo' TO='91"+mobileNumber+"' SEQ='1' TAG='' /></SMS></MESSAGE>";
		
		RestTemplate template = new RestTemplate();
		String user = template.getForObject(url, String.class);		
	}*/
	public static String sendSmsHandler( String mobileNumber, String msg,String senderId ) {
		if(mobileNumber.length()==10) {
			mobileNumber = "91"+mobileNumber;
		}
		msg = msg.replace(" ","%20");
		//System.out.println("/nMessage : "+msg+", ********* Mobile : "+mobileNumber+",******** Sender ID : "+senderId);
		String url=	"https://sms4.tatacommunications.com:3821/sendsms?username=m_msil_lapp&password=w8P3CxnF&from="+senderId+"&to="+mobileNumber+"&text="+msg;
		String user="";  
		System.out.println("URL......"+url);
		 try {
		    RestTemplate template = new RestTemplate();
			 user = template.getForObject(url, String.class);	
			 System.out.println("user......"+user);
			String smsResponse =  getHTML(url);
			 System.out.println("SMS respose ......"+smsResponse);
			}catch(Exception e) {
				System.out.println("error  sms......"+e);
			}

		return user;
	}
	
	
	public static String sendSmsHandlerRecuted( String mobileNumber, String msg,String senderId ) {
		if(mobileNumber.length()==10) {
			mobileNumber = "91"+mobileNumber;
		}
		msg = msg.replace(" ","%20");
		String url=	"https://sms4.tatacommunications.com:3821/sendsms?username=m_msil_lapp&password=w8P3CxnF&from="+senderId+"&to="+mobileNumber+"&text="+msg;
		String user="";  
		System.out.println("URL......"+url);
		 try {
		    RestTemplate template = new RestTemplate();
			 user = template.getForObject(url, String.class);	
			 System.out.println("user......"+user);
			String msmsResponse = getHTML(url);
			 System.out.println("res......"+msmsResponse);
			}catch(Exception e) {
				System.out.println("error  sms......"+e);
			}

		return user;
	}
	
	public static String sendSmsPromotional( String mobileNumber, String msg,String senderId,String emplateid ) {
		   System.out.println("per..............");
		if(mobileNumber.length()==10) {
			mobileNumber = "91"+mobileNumber;
		}
		/*"&TEMPLATE_ID="1007606977458974398"
*/		msg = msg.replace(" ","%20");
		
		String url=	"https://sms3.tatacommunications.com:3821/sendsms?username=m_msil_lapp1&password=q4GCPGzG"
				+ "&from=522225&to="+mobileNumber
				+ "&text="+msg
				+ "&dlr-mask=31&PE_ID=1601100000000001379"
				+ "&TEMPLATE_ID="+emplateid
				+ "&dlr-url=";
		String user="";  
		System.out.println("SMS....."+url);
		
		  try {
			  RestTemplate template = new RestTemplate(); //user =
		    user =  template.getForObject(url, String.class); // ResponseEntity<String>
		 //  responseEntity = template.getForEntity(url, String.class); //
		    
		    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date date = new Date();  
		    
		    System.out.println("getresponse........"+user+"       "+formatter.format(date));
		 System.out.println("Call SM..........S"+ getHTML(url));
		//  Base64.getEncoder().encodeToString(userAndPass.getBytes());
		  
		 // URL url1 = new URL(url); HttpsURLConnection conn =
		 // (HttpsURLConnection)url1.openConnection(); //
		//  conn.setRequestProperty("Authorization", "Basic " + encoded);
		//  conn.setRequestMethod("GET");
		  
		  }catch(Exception e) { System.out.println("error  sms......"+e); }
		 
		 
		 StringBuilder sb = new StringBuilder();
	        String testName="";
	        try { 
	        	
	        	// url="http://localhost:8081/ExideIndustries/pa/deleteAccesskey?"+accesskey;
	        	// URL url1 = new URL("https://sms4.tatacommunications.com:3821/sendsms");
	        	// URL url = new URL("http://localhost:4001/sales/simulation/insurance/getTestType");
	   			// HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
	   			// conn.setDoOutput(true);
	   			// conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
	   			// conn.setRequestMethod("GET");
	   			// String urlParameters  = "username=m_msil_lapp1&password=q4GCPGzG&from=522225&to="+mobileNumber+"&text="+msg+"&dlr-mask=31&PE_ID=1601100000000001379&TEMPLATE_ID="+emplateid+"&dlr-url=";
			/*
			 * OutputStream os = conn.getOutputStream(); os.write(urlParameters.getBytes());
			 * os.flush(); if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			 * 
			 * BufferedReader reader = new BufferedReader(new
			 * InputStreamReader(conn.getInputStream()));
			 * 
			 * String line; while ((line = reader.readLine()) != null) { sb.append(line); }
			 * reader.close(); System.out.println(sb.toString());
			 * 
			 * } else{ System.out.println(
			 * "Connection Refused ************"+conn.getResponseCode()); }
			 */
	        	
	        //	URL	url2 = new URL(url);
	   	    // HttpsURLConnection con = (HttpsURLConnection)url2.openConnection();
	   			
	   	     //dumpl all cert info
	   	    // print_https_cert(con);
	   			
	   	     //dump all the content
	   	   //  print_content(con);
	        	    
	        	  //  System.out.println("methid.............."+con.getResponseCode());
	        	    //return null;
	        	 // }
	          
	        } catch (Exception e) {
	            throw new RuntimeException("Error..."+e);
	        } 

		return user;
	}
	
	
	public static String getHTML(String urlToRead) throws Exception {
	      StringBuilder result = new StringBuilder();
	      URL url = new URL(urlToRead);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      try (BufferedReader reader = new BufferedReader(
	                  new InputStreamReader(conn.getInputStream()))) {
	          for (String line; (line = reader.readLine()) != null; ) {
	              result.append(line);
	          }
	      }
	      return result.toString();
	   }
	
	
	public static final String ACCOUNT_SID = "my_ACCOUNT_SID";
	   public static final String AUTH_TOKEN = "my_AUTH_TOKEN";


	   public static void main(String[] args) throws Exception {
	      String userAndPass = ACCOUNT_SID + ':' + AUTH_TOKEN;
	      String encoded = Base64.getEncoder().encodeToString(userAndPass.getBytes());

	      URL url = new URL("https://comms.tatacommunications.com/api/2012-04-24/Accounts/" + ACCOUNT_SID + ".json");
	      HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
	      conn.setRequestProperty("Authorization", "Basic " + encoded);
	      conn.setRequestMethod("GET");

	      // Add your business logic below; response code can be obtained from 'conn.getResponseCode()' and input stream from 'conn.getInputStream()'
	     
	  }
	   
	   
			
		   private static void print_content(HttpsURLConnection con){
			if(con!=null){
					
			try {
				
			   System.out.println("****** Content of the URL ********");			
			   BufferedReader br = 
				new BufferedReader(
					new InputStreamReader(con.getInputStream()));
						
			   String input;
						
			   while ((input = br.readLine()) != null){
			      System.out.println(input);
			   }
			   br.close();
						
			} catch (IOException e) {
			   e.printStackTrace();
			}
					
		       }
				
		   }
}
