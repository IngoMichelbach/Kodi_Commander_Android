package de.imichelb.kodicmd.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {
	
	public HttpUtil(){
		
	}
	
	//sends a http-get-Command
	public String get(String urlStr) throws IOException, MalformedURLException{
		
		HttpURLConnection connection = null;
		StringBuilder response = null;
		
		URL url = new URL(urlStr);
		
		//setup connection
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setUseCaches(false);
		connection.setDoOutput(false);
			    
		//response
		InputStream is = connection.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		response = new StringBuilder();
		
		String line;
		
		while((line = rd.readLine()) != null) {
			
			response.append(line);
			response.append('\r');
		}
		
		rd.close();
		
		return(response.toString());
	}
}
