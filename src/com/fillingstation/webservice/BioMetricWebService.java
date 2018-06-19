package com.fillingstation.webservice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.fillingstation.json.FingerPrintJSON;
@Path("/Biometric")
public class BioMetricWebService {

	 /*
     * API call for Login verification
     */
    
    @POST
    @Path("/CheckInBio")
    @Consumes(value= {"application/json"})
    @Produces(value={"application/json"})
    public Response loginVerify ()throws ParseException, IOException, JSONException
    {  
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }};

                   try {
                    SSLContext sc = SSLContext.getInstance("SSL");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                    } catch (Exception e) {
                    }

        
        
         System.out.println("hi");
         final URL url = new URL("https://localhost:8003/mfs100/capture");
         final HttpURLConnection   urlConnection =(HttpURLConnection) url.openConnection();
         
         urlConnection.setRequestMethod( "POST" );

         urlConnection.setDoOutput(true);
         urlConnection.setRequestProperty("Cross-Domain", "true");
            
         urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
         urlConnection.connect();
         System.out.println("hi"+urlConnection);
         JSONObject json=new JSONObject();
         json.put("Quality",10);
         json.put("TimeOut",60);
         
        // JSON json=new JSON();
         
         final OutputStreamWriter outputStream = new OutputStreamWriter(urlConnection.getOutputStream());
         outputStream.write(json.toString());
         System.out.println("hi"+json);
         outputStream.flush();
         final InputStream inputStream = urlConnection.getInputStream();
        return Response.status(200).entity(inputStream).build();
    }
    
    /*
     * API call for Login verification
     */
    
    @POST
    @Path("/BioVerification")
    @Consumes(value= {"application/json"})
    
    @Produces(value={"application/json"})
    public Response BioVerification (FingerPrintJSON json)throws ParseException, IOException, JSONException
    {
    	
    	  TrustManager[] trustAllCerts = new TrustManager[]{
                  new X509TrustManager() {
                      public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                          return null;
                      }
                      public void checkClientTrusted(
                          java.security.cert.X509Certificate[] certs, String authType) {
                      }
                      public void checkServerTrusted(
                          java.security.cert.X509Certificate[] certs, String authType) {
                      }
                  }};

                     try {
                      SSLContext sc = SSLContext.getInstance("SSL");
                      sc.init(null, trustAllCerts, new java.security.SecureRandom());
                      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                      } catch (Exception e) {
                      }

    	 System.out.println("verify");
         final URL url = new URL("https://localhost:8003/mfs100/verify");
         final HttpURLConnection   urlConnection =(HttpURLConnection) url.openConnection();
         
         urlConnection.setRequestMethod( "POST" );

         urlConnection.setDoOutput(true);
         urlConnection.setRequestProperty("Cross-Domain", "true");
            
         urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
         urlConnection.connect();
         System.out.println("hi"+urlConnection);
         JSONObject input=new JSONObject();
         input.put("ProbTemplate",json.getCurrentFingerValue());
         input.put("GalleryTemplate",json.getDbFingerValue());
         input.put("BioType","ANSI");
     
         
        // JSON json=new JSON();
         
         final OutputStreamWriter outputStream = new OutputStreamWriter(urlConnection.getOutputStream());
         outputStream.write(input.toString());
         System.out.println("verify "+input);
         outputStream.flush();
         final InputStream inputStream = urlConnection.getInputStream();
        return Response.status(200).entity(inputStream).build();
	
    	
    }
    }
    
	

