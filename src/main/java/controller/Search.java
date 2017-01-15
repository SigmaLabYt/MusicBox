package controller;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import java.io.IOException;
import java.util.List;

public class Search {

    private long NUMBER_OF_VIDEOS_RETURNED;
    private String apiKey = "AIzaSyDL2ZqeB3uGYmqv8jJgITwS4N9eFyBz4IA";
    private YouTube youtube;
    private SearchListResponse searchResponse;
    private YouTube.Search.List search;
    private List<SearchResult> searchResultList;
    
    public Search(String keyword,long n) {
    	NUMBER_OF_VIDEOS_RETURNED = n;
        try {
        	youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest httpRequest) throws IOException {
                }
            }).setApplicationName("YoutubMP3").build();
            search = youtube.search().list("id,snippet");
            search.setKey(apiKey);
            search.setQ(keyword);
            search.setType("video");
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    public void executeYoutube(){
    	try {
			searchResponse = search.execute();
			searchResultList = searchResponse.getItems();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public List<SearchResult> getResult(){
    	return searchResultList;
    }

}
