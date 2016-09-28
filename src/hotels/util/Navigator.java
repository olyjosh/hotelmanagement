/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.util;

import eu.hansolo.enzo.notification.Notification;
import hotels.controllers.Main;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import org.apache.http.HttpException;
import static org.apache.http.HttpHeaders.USER_AGENT;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
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
    private HttpClient httpClient ;//= HttpClientBuilder.create().build(); 
    private JSONObject res;
    private final String BASE_URL = "http://192.168.0.197:9016/api/";   //development
    //private final String BASE_URL = "http://52.38.37.185:9016/api/";   //Production
    
    private final String OP_URL = BASE_URL+"op/";
    
     public Navigator(Main main) {
        this.httpClient = HttpClientBuilder.create()
                .addInterceptorFirst(new HttpRequestInterceptor() {
                    @Override
                    public void process(HttpRequest hr, HttpContext hc) throws HttpException, IOException {
                        main.responseProcessing(null);
                        hr.setHeader("User-Agent", USER_AGENT);
                        hr.setHeader("token", Storage.auth_token);
                    }
                }).addInterceptorFirst(new HttpResponseInterceptor() {
            @Override
            public void process(HttpResponse hr, HttpContext hc) throws HttpException, IOException {
                if (hr != null) {
                    try {
                        result = EntityUtils.toString(hr.getEntity());
                        int status = hr.getStatusLine().getStatusCode();
                        if (status == HttpStatus.SC_OK) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    main.responseInfo("DONE");
                                }
                            });
                            res = new JSONObject(result);
                        } else if (status == HttpStatus.SC_UNAUTHORIZED) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    main.responseWarning("Invalid authentication, Please login");
                                }
                            });

                        } else if (status == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    main.responseError("Internal Server Error");
                                }
                            });

                        }

                    } catch (JSONException | IOException ex) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                main.responseError("Invalid server response");
                            }
                        });

                        Logger.getLogger(Navigator2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            main.responseError("Network problem");
                        }
                    });

                }

//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        
//                    }
//                });
            }
        }).build();
    }

    
    
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
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
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
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
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
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject fetchRoomType(){
        String url = OP_URL+"fetch/roomtype";
        try{
            HttpGet request = new HttpGet(url);
            
           httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }   
    
    public JSONObject fetchGuest(){
        String url = OP_URL+"fetch/customers";
        try{
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public JSONObject fetchBooking(){
        String url = OP_URL+"fetch/book";
        try{
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
