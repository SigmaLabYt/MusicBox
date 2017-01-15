package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class GetTopTracks {
	
	private String last_fm_api_key = "5abec855f4b739ba513f68612659f8d2";
	private String last_fm_api_version = "2.0";
	private String result;
	
	public GetTopTracks(String country,String limit){
		
		try {
			URL url = createUrl(country,limit);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			String output,o = "";
			while ((output = br.readLine()) != null) {
				o+=output;
			}
			result = o;
			
			conn.disconnect();
			
		}catch (IOException e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public String getResult(){
		return result;
	}
	
	private URL createUrl(String c,String l){
		String urlPattern = "https://ws.audioscrobbler.com/"+last_fm_api_version+"/"
				+ "?method=geo.gettoptracks"
				+ "&country="+c
				+ "&limit="+l
				+ "&api_key="+last_fm_api_key
				+"&format=json";
		URL url = null;
		try {
			url = new URL(urlPattern);
		} catch (MalformedURLException e){
			e.printStackTrace();
		}
		return url;
	}
	
}