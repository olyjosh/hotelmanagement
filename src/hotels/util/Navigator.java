/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.util;

import eu.hansolo.enzo.notification.Notification;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import static org.apache.http.HttpHeaders.USER_AGENT;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author nova
 */
public class Navigator {

    private String result;
    private HttpResponse response = null;
    private HttpClient httpClient = HttpClientBuilder.create().build(); 
    
    private final String BASE_URL = "http://192.168.0.197:9016/api/";   //development
    //private final String BASE_URL = "http://52.38.37.185:9016/api/";   //Production
    
    private final String OP_URL = BASE_URL+"op/";
    
    public JSONObject login(List data){
         
        try{
            HttpPost post = new HttpPost(BASE_URL+"login");
            post.setHeader("User-Agent", USER_AGENT);
            post.setEntity(new UrlEncodedFormEntity(data));
            HttpResponse response = httpClient.execute(post);
            if(response != null){
                result = EntityUtils.toString(response.getEntity());
            }
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.getInt("status") == 1){
                Storage.setAuth_token(jsonObject.getString("token"));
                JSONObject id = jsonObject.getJSONObject("user");
                Storage.setId(id.getString("_id"));
                System.out.println("Printing Logged In User ID : " + id.getString("_id"));
            }
            System.out.println(Storage.getAuth_token());
            return jsonObject;
        }
        catch(IOException |JSONException | ParseException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject registerUser(List data){
         
        try{
            HttpPost post = new HttpPost(BASE_URL+"register");
            post.setHeader("User-Agent", USER_AGENT);
            post.setEntity(new UrlEncodedFormEntity(data));
            HttpResponse response = httpClient.execute(post);
            
            if(response != null){
                result = EntityUtils.toString(response.getEntity());
            }
            return new JSONObject(result);
        }
        catch(IOException |JSONException | ParseException e){
            e.printStackTrace();
        }
        return null;
    }
     
    public JSONObject fetchRoom(){
        String url = OP_URL+"fetch/room";
        try{
            HttpGet request = new HttpGet(url);
            request.setHeader("User-Agent", USER_AGENT);
            request.setHeader("token",Storage.auth_token);
            HttpResponse response = httpClient.execute(request);
            if(response != null){
                result = EntityUtils.toString(response.getEntity());
            }
          return new JSONObject(result);
        }
        catch(IOException |JSONException | ParseException e){
            e.printStackTrace();
        }
        return null;
    }
        
    public JSONObject createRoom(List data){
        
        String url = OP_URL+"fetch/room";
        
        try{
            String param = URLEncodedUtils.format(data, "utf-8");
            url += param;
            HttpGet request = new HttpGet(url);
            request.setHeader("User-Agent", USER_AGENT);
            request.setHeader("token",Storage.auth_token);
            HttpResponse response = httpClient.execute(request);
            if(response != null){
                result = EntityUtils.toString(response.getEntity());
            }
          return new JSONObject(result);
        }
        catch(IOException |JSONException | ParseException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject createBooking(List data){

        String url = OP_URL+"create/book";
        
        try{
            String param = URLEncodedUtils.format(data, "utf-8");
            url +="?"+ param;
            HttpGet request = new HttpGet(url);
            request.setHeader("User-Agent", USER_AGENT);
            request.setHeader("token",Storage.auth_token);
            HttpResponse response = httpClient.execute(request);
            if(response != null){
                result = EntityUtils.toString(response.getEntity());
            }
          return new JSONObject(result);
        }
        catch(IOException |JSONException | ParseException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject fetchRoomType(){
        String url = OP_URL+"fetch/roomtype";
        try{
            HttpGet request = new HttpGet(url);
            request.setHeader("User-Agent", USER_AGENT);
            request.setHeader("token",Storage.auth_token);
            HttpResponse response = httpClient.execute(request);
            if(response != null){
                result = EntityUtils.toString(response.getEntity());
            }
          return new JSONObject(result);
        }
        catch(IOException |JSONException | ParseException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject fetchGuest(){
        String url = OP_URL+"fetch/customers";
        try{
            HttpGet request = new HttpGet(url);
            request.setHeader("User-Agent", USER_AGENT);
            request.setHeader("token",Storage.auth_token);
            HttpResponse response = httpClient.execute(request);
            if(response != null){
                result = EntityUtils.toString(response.getEntity());
            }
          return new JSONObject(result);
        }
        catch(IOException |JSONException | ParseException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject fetchBooking(){
        String url = OP_URL+"fetch/book";
        try{
            HttpGet request = new HttpGet(url);
            request.setHeader("User-Agent", USER_AGENT);
            request.setHeader("token",Storage.auth_token);
            HttpResponse response = httpClient.execute(request);
            if(response != null){
                result = EntityUtils.toString(response.getEntity());
            }
          return new JSONObject(result);
        }
        catch(IOException |JSONException | ParseException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public String postService(String url, JSONObject json){
        
        try {
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(json.toString());
            request.addHeader("content-type", "application/json");
            request.addHeader("Accept","application/json");
            request.setEntity(params);
            response = httpClient.execute(request);
            System.out.println("Passing through httpClient.execute()");
            
            // handle response here...lng
            if (response != null) {
                
                // CONVERT RESPONSE TO STRING
                result = EntityUtils.toString(response.getEntity());
                System.out.println("result " + result);
            }
        } catch (IOException | ParseException ex){
                ex.printStackTrace();
            }
        return result;
    }
    
    public String getService(String url){
        
        try {
            
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            
            // add request header
            request.addHeader("User-Agent", USER_AGENT);
            HttpResponse response = client.execute(request);
            
            result = EntityUtils.toString(response.getEntity());
                System.out.println("result " + result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    public String postEncode(String url, List data) {
        
        String base = "http://192.168.0.197:9016/api/";
        
        try{
            HttpPost post = new HttpPost(base+url);

            // add header
            post.setHeader("User-Agent", USER_AGENT);

            //List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            //urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));

            post.setEntity(new UrlEncodedFormEntity(data));
            HttpResponse response = httpClient.execute(post);
            if(response != null){
                result = EntityUtils.toString(response.getEntity());
            }
        }
        catch(IOException | ParseException e){
            e.printStackTrace();
        }
        return result;
    }
    
    public String getEncode(String url, List data){
        
        String base = "http://192.168.0.197:9016/api/register";
        
        try{
            String param = URLEncodedUtils.format(data, "utf-8");
            url += param;
            HttpGet request = new HttpGet(base+url);

            // add header
            request.setHeader("User-Agent", USER_AGENT);
            
            HttpResponse response = httpClient.execute(request);
            if(response != null){
                result = EntityUtils.toString(response.getEntity());
            }
        }
        catch(IOException | ParseException e){
            e.printStackTrace();
        }
        return result;
    }
    
    public void notify(Stage stage, Pos pos, String title, String message, int h, int w ){
        
        Notification.Notifier.setPopupLocation(stage, pos);
        Notification.Notifier.setWidth(w);
        Notification.Notifier.setHeight(h);
        Notification.Notifier.INSTANCE.notifySuccess(title, message);
    }
    
    public static Map<String,String> parse(JSONObject json , Map<String,String> out) throws JSONException{
        Iterator<String> keys = json.keys();
        while(keys.hasNext()){
            String key = keys.next();
            String val = null;
            try{
                 JSONObject value = json.getJSONObject(key);
                 parse(value,out);
            }catch(Exception e){
                val = json.getString(key);
            }

            if(val != null){
                out.put(key,val);
            }
        }
        return out;
    }   
    
    public String stripDate(String rawdate){
        String [] date = rawdate.split("T");
        return date[0];
    }
}
