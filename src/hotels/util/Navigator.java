/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.util;


import hotels.controllers.Main;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import org.apache.http.HttpException;
import static org.apache.http.HttpHeaders.USER_AGENT;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
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
//    private final String BASE_URL = "http://127.0.0.1:9016/api/"; //development
    //private final String BASE_URL = "http://192.168.0.197:9016/api/";   //development
    private final String BASE_URL = "http://35.164.173.241:9016/api/";   //Production
    
    public final String OP_URL = BASE_URL+"op/";
    
    public Navigator(Main main) {

        this.httpClient = HttpClientBuilder.create()
                .addInterceptorFirst(new HttpRequestInterceptor() {
                    @Override
                    public void process(HttpRequest hr, HttpContext hc) throws HttpException, IOException {
                        res=null;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                main.responseProcessing(null);
                            }
                        });
                        hr.setHeader("User-Agent", USER_AGENT);
                        hr.setHeader("token", Storage.auth_token);
                    }
                }).addInterceptorFirst(new HttpResponseInterceptor() {
            @Override
            public void process(HttpResponse hr, HttpContext hc) throws HttpException, IOException {
                if (hr != null) {
                    try {
                        result = EntityUtils.toString(hr.getEntity());
                        System.out.println(result);
                        int status = hr.getStatusLine().getStatusCode();
                        if (status == HttpStatus.SC_OK) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    main.responseInfo("DONE");
                                }
                            });
                            res = new JSONObject(result);
                            System.out.println("Printing res from Navigator : " + res);
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
                        main.responseError("Network problem...");
                        Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
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
            post.setEntity(new UrlEncodedFormEntity(data));
            httpClient.execute(post);
            if(res.getInt("status") == 1){
                Storage.setAuth_token(res.getString("token"));
            }
            return res;
        } catch (IOException |JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //=============USER ACCOUNT=================
    
    public JSONObject registerUser(List data){
         
        try{
            HttpPost post = new HttpPost(BASE_URL+"register");
            post.setEntity(new UrlEncodedFormEntity(data));
            httpClient.execute(post);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject fetchUsers(){
        
        String url = OP_URL+"fetch/staff";
        try{
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject editUser(List data){
         
        try{
            HttpPost post = new HttpPost(BASE_URL+"edit/register");
            post.setEntity(new UrlEncodedFormEntity(data));
            httpClient.execute(post);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject deleteUser(List data){
        
        String url = OP_URL+"delete/user";
        
        try{
            String param = URLEncodedUtils.format(data, "utf-8");
            url +="?"+ param;
            System.out.println("Printing URL : " + url);
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
     
   //============ROOMS=========================
    
    public JSONObject createRoom(List data){
        
        String url = OP_URL+"create/room";
        
        try{
            String param = URLEncodedUtils.format(data, "utf-8");
            url +="?"+ param;
            System.out.println("Printing URL : " + url);
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
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
    
    public JSONObject editRoom(List data){
        
        String url = OP_URL+"edit/room";
        
        try{
            String param = URLEncodedUtils.format(data, "utf-8");
            url +="?"+ param;
            System.out.println("Printing URL : " + url);
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject deleteRoom(List data){
        
        String url = OP_URL+"delete/room";
        
        try{
            String param = URLEncodedUtils.format(data, "utf-8");
            url +="?"+ param;
            System.out.println("Printing URL : " + url);
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //==========BOOKING THINGS================//
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
    
    public JSONObject editBooking(List data){

        String url = OP_URL+"edit/book";
        
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
    
    public JSONObject deleteBooking(List data){
        
        String url = OP_URL+"delete/book";
        
        try{
            String param = URLEncodedUtils.format(data, "utf-8");
            url +="?"+ param;
            System.out.println("Printing URL : " + url);
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //=============ROOM TYPE====================
    
    public JSONObject createRoomType(List data){
        
        String url = OP_URL+"create/roomtype";
        
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
    
    public JSONObject editRoomType(List data){

        String url = OP_URL+"edit/roomtype";
        
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
    
    public JSONObject deleteRoomType(List data){
        
        String url = OP_URL+"delete/roomtype";
        
        try{
            String param = URLEncodedUtils.format(data, "utf-8");
            url +="?"+ param;
            System.out.println("Printing URL : " + url);
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    //==========FLOORS======================
    public JSONObject createFloor(List data){
        
        String url = OP_URL+"create/floor";
        
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
    
    public JSONObject fetchFloor(){
        String url = OP_URL+"fetch/floor";
        try{
            HttpGet request = new HttpGet(url);
           httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject editFloor(List data){
        
        String url = OP_URL+"edit/floor";
        
        try{
            String param = URLEncodedUtils.format(data, "utf-8");
            url +="?"+ param;
            System.out.println("Printing URL : " + url);
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject deleteFloor(List data){
        
        String url = OP_URL+"delete/floor";
        
        try{
            String param = URLEncodedUtils.format(data, "utf-8");
            url +="?"+ param;
            System.out.println("Printing URL : " + url);
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //=============GUEST========================
    
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

    
    //==============LAUNDRY SERVICE=================
    
    public JSONObject createLaundryService(List data){
        String url = OP_URL+"create/service";
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
    
    public JSONObject editLaundryService(List data){
        String url = OP_URL+"edit/service";
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
    
    public JSONObject fetchLaundryService(List data){
        String url = OP_URL+"fetch/service";
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
    
    public JSONObject deleteLaundryService(List data){
        String url = OP_URL+"delete/service";
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
    
    
    //==============Daily Laundry===============//
    
    public JSONObject createDailyLaundry(List data){
        System.out.println("Daily Laundry Event Processing.....");
        String url = OP_URL+"create/dailyLaundry";
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
    
    public JSONObject fetchDailyLaundry(){
        String url = OP_URL+"fetch/dailyLaundry";
        try{
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject editDailyLaundry(List data){
        String url = OP_URL+"edit/dailyLaundry";
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
    
    public JSONObject deleteDailyLaundry(List data){
        String url = OP_URL+"delet/dailyLaundry";
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
    
    //==============Laundry Item===============//
    
    public JSONObject createLaundryItem(List data){
        String url = OP_URL+"create/laundryitem";
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
    
    public JSONObject editLaundryItem(List data){
        String url = OP_URL+"edit/laundryitem";
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
    
    public JSONObject deleteLaundryItem(List data){
        String url = OP_URL+"delete/laundryitem";
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
    
    public JSONObject fetchLaundryItems(){
        String url = OP_URL+"fetch/laundryitem";
        try{
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
  
    //==============Return In===============//
    
    public JSONObject createReturn(List data){
        String url = OP_URL+"create/returnIn";
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
    
    public JSONObject fetchReturnIn(){
        String url = OP_URL+"fetch/returnIn";
        try{
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //==============HOtel Services===============//
    
    public JSONObject createHotelService(List data){
        String url = OP_URL+"create/service";
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
    
    public JSONObject fetchHotelService(List data){
        String url = OP_URL+"fetch/service";
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
    
    public JSONObject editHotelService(List data){
        String url = OP_URL+"edit/service";
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
    
    public JSONObject deleteHotelService(List data){
        String url = OP_URL+"delete/service";
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
    
    
    //==============Lost and Found===============//
    
    public JSONObject createLostFound(List data){
        String url = OP_URL+"create/lostfound";
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
        
    public JSONObject fetchLostFound(){
        String url = OP_URL+"fetch/lostfound";
        try{
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject editLostFound(List data){
        String url = OP_URL+"edit/lostfound";
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
      
    public JSONObject deleteLostFound(List data){
        String url = OP_URL+"delete/lostfound";
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
    
    
    //==============REMINDERS===============//
    
    public JSONObject createNewReminder(List data){
        String url = OP_URL+"create/reminder";
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
        
    public JSONObject fetchReminder(){
        String url = OP_URL+"fetch/reminder";
        try{
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject editReminder(List data){
        String url = OP_URL+"edit/reminder";
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
      
    public JSONObject deleteRemider(List data){
        String url = OP_URL+"delete/reminder";
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
    
    //==============Phone Directory===============//
    
    public JSONObject createNewPhone(List data){
        String url = OP_URL+"create/phone";
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
        
    public JSONObject fetchPhone(){
        String url = OP_URL+"fetch/phone";
        try{
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject editPhone(List data){
        String url = OP_URL+"edit/phone";
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
      
    public JSONObject deletePhone(List data){
        String url = OP_URL+"delete/phone";
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
    
    
     //==============Miscellaneouse Sales===============//
    
    public JSONObject createNewSale(List data){
        String url = OP_URL+"create/phone";
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
        
    public JSONObject fetchSale(){
        String url = OP_URL+"fetch/phone";
        try{
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject editSale(List data){
        String url = OP_URL+"edit/phone";
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
      
    public JSONObject deleteSale(List data){
        String url = OP_URL+"delete/phone";
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
    
    
    //==============Work Order===============//
    
    public JSONObject createWorkOrder(List data){
        String url = OP_URL+"create/workorder";
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
        
    public JSONObject fetchWorkOrder(){
        String url = OP_URL+"fetch/workorder";
        try{
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject editWorkOrder(List data){
        String url = OP_URL+"edit/workorder";
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
      
    public JSONObject deleteWorkOrder(List data){
        String url = OP_URL+"delete/workorder";
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
    
    //==============Credit Accounts===============//
    
    public JSONObject createAccount(List data){
        String url = OP_URL+"create/account";
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
        
    public JSONObject fetchAccount(){
        String url = OP_URL+"fetch/account";
        try{
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject editAccount(List data){
        String url = OP_URL+"edit/account";
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
      
    public JSONObject deleteAccount(List data){
        String url = OP_URL+"delete/account";
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
    
    //==============Business Sources===============//
    
    public JSONObject createBiz(List data){
        String url = OP_URL+"create/bizsource";
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
        
    public JSONObject fetchBiz(){
        String url = OP_URL+"fetch/bizsource";
        try{
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject editBiz(List data){
        String url = OP_URL+"edit/bizsource";
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
      
    public JSONObject deleteBiz(List data){
        String url = OP_URL+"delete/bizsource";
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
    
    //==============Payout===============//
    
    public JSONObject createPayout(List data){
        String url = OP_URL+"create/payout";
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
        
    public JSONObject fetchPayout(){
        String url = OP_URL+"fetch/payout";
        try{
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject editPayout(List data){
        String url = OP_URL+"edit/payout";
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
      
    public JSONObject deletePayout(List data){
        String url = OP_URL+"delete/payout";
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
    
    //==============Miscellaneous Sales===============//
    
    public JSONObject createMisc(List data){
        String url = OP_URL+"create/pending";
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
        
    public JSONObject fetchMisc(){
        String url = OP_URL+"fetch/pending";
        try{
            HttpGet request = new HttpGet(url);
            
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONObject editMisc(List data){
        String url = OP_URL+"edit/pending";
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
      
    public JSONObject deleteMisc(List data){
        String url = OP_URL+"delete/pending";
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
}
