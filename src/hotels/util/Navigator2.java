package hotels.util;

import hotels.controllers.Main;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author nova
 */
public class Navigator2 {
    
    private Main main;
    private String result;
    private HttpResponse response = null;
    private JSONObject res;
    private HttpClient httpClient;

    private final static String BASE_URL = "http://127.0.0.1:9016/api/"; 
//    private final static String BASE_URL = "http://192.168.0.197:9016/api/";   //development
    //private final static String BASE_URL = "http://52.38.37.185:9016/api/";   //Production

    private final static String OP_URL = BASE_URL + "op/";
    public final static String IMG_URL = BASE_URL+"static/image?id=";
    
    
    public Navigator2(Main main) {
        this.main = main;
        this.httpClient = HttpClientBuilder.create()
                .addInterceptorFirst(new HttpRequestInterceptor() {
                    @Override
                    public void process(HttpRequest hr, HttpContext hc){
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
            public void process(HttpResponse hr, HttpContext hc) {
                if (hr != null) {
                    try {
                        result = EntityUtils.toString(hr.getEntity());
                        System.out.println("RESULT: "+result);
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
                            res=null;
                        } else if (status == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    main.responseError("Internal Server Error");
                                }
                            });
                            res=null;
                        }

                    } catch (JSONException | IOException ex) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                main.responseError("Invalid server response");
                            }
                        });
                        res=null;
                        Logger.getLogger(Navigator2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            main.responseError("Network problem");
                        }
                    });
                    res=null;
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

    
//    
//    public JSONObject login(List data){
//        
//        try{
//            HttpPost post = new HttpPost(BASE_URL+"login");
//            post.setHeader("User-Agent", USER_AGENT);
//            post.setEntity(new UrlEncodedFormEntity(data));
//            HttpResponse response = httpClient.execute(post);
//            if(response != null){
//                result = EntityUtils.toString(response.getEntity());
//                
//            }
//            JSONObject jsonObject = new JSONObject(result);
//            if(jsonObject.getInt("status") == 1){
//                Storage.setAuth_token(jsonObject.getString("token"));
//            }
//            System.out.println(Storage.getAuth_token());
//            return jsonObject;
//        }
//        catch(IOException |JSONException | ParseException e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//    
    
    
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
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
    
    public JSONObject registerUser(List data){
         
        try{
            HttpPost post = new HttpPost(BASE_URL+"register");
            post.setEntity(new UrlEncodedFormEntity(data));
            httpClient.execute(post);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
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
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
    
        
    public JSONObject createRoom(List data){
        
        String url = OP_URL+"create/room";
        
        try{
            String param = URLEncodedUtils.format(data, "utf-8");
            url += param;
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
    
    public JSONObject fetchFloors(){
        String url = OP_URL+"fetch/floor";
        try{
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
    
        
    
    public JSONObject fetchCustomers(){
        String url = OP_URL+"fetch/customers";
        try{
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
    
     public JSONObject fetchMessage(String phone){
        String url = OP_URL+"fetch/message";
        try{
            List <NameValuePair> data = new ArrayList<>();
            data.add(new BasicNameValuePair("phone", phone));
            String param = URLEncodedUtils.format(data, "utf-8");
            url +="?"+ param;
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
     
    public JSONObject sendMessage(String to, String from, String message) {
        String url = OP_URL + "create/message";

        try {
            List<NameValuePair> data = new ArrayList<>();
            data.add(new BasicNameValuePair("to", to));
            data.add(new BasicNameValuePair("from", from));
            data.add(new BasicNameValuePair("message", message));
            String param = URLEncodedUtils.format(data, "utf-8");
            url += "?" + param;
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
     
      
      public JSONObject fetchRoomStay(String d1, String d2){
        
        //2016-09-22
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("d1", d1));
        data.add(new BasicNameValuePair("d2", d2));
        String url = OP_URL+"fetch/roomstay2";
        String param = URLEncodedUtils.format(data, "utf-8");
        url +="?"+ param;
        try{
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
    
    public JSONObject createHouseKeepTasks(String ...x){
        
        //2016-09-22
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("date", x[0]));
        data.add(new BasicNameValuePair("endDate", x[1]));
        data.add(new BasicNameValuePair("desc", x[2]));
        data.add(new BasicNameValuePair("room", x[3]));
        data.add(new BasicNameValuePair("interval", x[4]));
        data.add(new BasicNameValuePair("reminder", x[5]));
        data.add(new BasicNameValuePair("maids", x[6]));
        data.add(new BasicNameValuePair("performedBy", x[7]));
        String url = OP_URL+"create/housekeeptask";
        String param = URLEncodedUtils.format(data, "utf-8");
        url +="?"+ param;
        try{
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
    
    
    
    public JSONObject fetchHouseKeepTasks(){
        
        //2016-09-22
        
        
        String url = OP_URL+"fetch/housekeeptask";
        //String param = URLEncodedUtils.format(data, "utf-8");
        //url +="?"+ param;
        try{
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
    
    public JSONObject editHouseKeepTasks(String ...x){
        //2016-09-22
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("date", x[0]));
        data.add(new BasicNameValuePair("endDate", x[1]));
        data.add(new BasicNameValuePair("desc", x[2]));
        data.add(new BasicNameValuePair("room", x[3]));
        data.add(new BasicNameValuePair("interval", x[4]));
        data.add(new BasicNameValuePair("reminder", x[5]));
        data.add(new BasicNameValuePair("maids", x[6]));
        data.add(new BasicNameValuePair("performedBy", x[7]));
        data.add(new BasicNameValuePair("id", x[8]));
        
        String url = OP_URL+"edit/housekeeptask";
        String param = URLEncodedUtils.format(data, "utf-8");
        url +="?"+ param;
        try{
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
      
    public JSONObject deleteHouseKeepTasks(String id){
        //2016-09-22
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("id", id));
        
        String url = OP_URL+"delete/housekeeptask";
        String param = URLEncodedUtils.format(data, "utf-8");
        url +="?"+ param;
        try{
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
   
    public JSONObject maidTasks(String id) {
        
        String url = OP_URL + "fetch/housekeeptask";
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("id", id));
        String param = URLEncodedUtils.format(data, "utf-8");
        url +="?"+ param;
        try {
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
     
       public JSONObject fetchStaffAll() {
        String url = OP_URL + "fetch/staff";

        try {
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }

    
    public JSONObject fetchStaff(String id){
        String url = OP_URL+"fetch/staff";
        if (id != null) {
            List<NameValuePair> data = new ArrayList<>();
            data.add(new BasicNameValuePair("id", id));
            String param = URLEncodedUtils.format(data, "utf-8");
            url += "?" + param;
        }
        try{
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
    
         
    public JSONObject fetchStaff(int privi){
        String url = OP_URL+"fetch/staff";
            List<NameValuePair> data = new ArrayList<>();
            data.add(new BasicNameValuePair("privilege", ""+privi));
            String param = URLEncodedUtils.format(data, "utf-8");
            url += "?" + param;
        
        try{
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
    
    public JSONObject fetchStaffCommnet(String id){
        String url = OP_URL+"fetch/staffcomments";
            List<NameValuePair> data = new ArrayList<>();
            data.add(new BasicNameValuePair("id", id));
            String param = URLEncodedUtils.format(data, "utf-8");
            url += "?" + param;
        
        try{
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
    
    public JSONObject postStaffCommnet(String staffId, String com){
        String url = OP_URL+"create/staffcomments";
            List<NameValuePair> data = new ArrayList<>();
            data.add(new BasicNameValuePair("staff", staffId));
            data.add(new BasicNameValuePair("comment", com));
            data.add(new BasicNameValuePair("performedBy", Storage.getId()));
            String param = URLEncodedUtils.format(data, "utf-8");
            url += "?" + param;
        
        try{
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
    
     public JSONObject createFood(String name, String desc, String price, String img) {
        String url = OP_URL + "create/food";
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("name", name));
        data.add(new BasicNameValuePair("desc", desc));
        data.add(new BasicNameValuePair("img", img));
        data.add(new BasicNameValuePair("price", price));
        data.add(new BasicNameValuePair("performedBy", Storage.getId()));
        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;

        try {
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
    
    public JSONObject editFood(String id,String name, String desc, String price, String img) {
        String url = OP_URL + "edit/food";
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("name", name));
        data.add(new BasicNameValuePair("desc", desc));
        data.add(new BasicNameValuePair("img", img));
        data.add(new BasicNameValuePair("price", price));
        data.add(new BasicNameValuePair("id", id));
        data.add(new BasicNameValuePair("performedBy", Storage.getId()));
        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;

        try {
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
     
     public JSONObject fetchFoods() {
        String url = OP_URL + "fetch/food";

        try {
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }

    public JSONObject deleteFood(String id) {
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("id", id));

        String url = OP_URL + "delete/food";
        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;
        try {
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
     
  
        
    public JSONObject createFoodOrder(String ...x) throws JSONException {
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("channel", State.channel_FRONT));
        data.add(new BasicNameValuePair("orders", x[0]));
        data.add(new BasicNameValuePair("guest_firstName", x[1]));
        data.add(new BasicNameValuePair("guest_lastName", x[2]));
        data.add(new BasicNameValuePair("guest_phone", x[3]));
        data.add(new BasicNameValuePair("performedBy", Storage.getId()));

        String url = OP_URL + "create/foodorders";
        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;
        try {
            HttpGet request = new HttpGet(url);
            request.setHeader("User-Agent", USER_AGENT);
            request.setHeader("token", Storage.auth_token);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse execute = httpClient.execute(request);
            String toString = EntityUtils.toString(execute.getEntity());
            System.out.println(toString);
            return new JSONObject(toString);
            
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }     
     
    
    
            
    public static JSONObject createFoodOrderTest(String ...x) throws JSONException {
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("channel", State.channel_FRONT));
        data.add(new BasicNameValuePair("orders", x[0]));
        data.add(new BasicNameValuePair("guest_firstName", x[1]));
        data.add(new BasicNameValuePair("guest_lastName", x[2]));
        data.add(new BasicNameValuePair("guest_phone", x[3]));
        data.add(new BasicNameValuePair("performedBy", Storage.getId()));

        String url = OP_URL + "create/foodorders";
        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;
        try {
            HttpGet request = new HttpGet(url);
            request.setHeader("User-Agent", USER_AGENT);
            request.setHeader("token", Storage.auth_token);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse execute = httpClient.execute(request);
            String toString = EntityUtils.toString(execute.getEntity());
            System.out.println(toString);
            return new JSONObject(toString);
            
        } catch (IOException e) {
            e.printStackTrace(); 
//            main.responseError("Network problem");
        }
        return null;
    }     
     
         
     public JSONObject fetchFoodsOrders(String channel, String d1,String d2) {
        String url = OP_URL + "fetch/foodorders";
        List<NameValuePair> data = new ArrayList<>();

        if(d1!=null){
            data.add(new BasicNameValuePair("d1", d1));
            data.add(new BasicNameValuePair("d2", d2));
        }
        if(channel!=null){
            data.add(new BasicNameValuePair("channel", channel));
        }

        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;

        try {
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
     
             
     public JSONObject approveFoodsOrders(String id) {
        String url = OP_URL + "approve/foodorders";
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("id", id));
        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;
        try {
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
    
    public JSONObject cancelFoodsOrders(String id) {
        String url = OP_URL + "cancel/foodorders";
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("id", id));
        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;
        try {
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
     
        
    public JSONObject doneFoodsOrders(String id) {
        String url = OP_URL + "done/foodorders";
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("id", id));
        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;
        try {
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
     
    
    
    
    
    // Bar and Drinks
    
         public JSONObject createDrink(String name, String desc, String price, String img) {
        String url = OP_URL + "create/drink";
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("name", name));
        data.add(new BasicNameValuePair("desc", desc));
        data.add(new BasicNameValuePair("img", img));
        data.add(new BasicNameValuePair("price", price));
        data.add(new BasicNameValuePair("performedBy", Storage.getId()));
        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;

        try {
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
    
    public JSONObject editDrink(String id,String name, String desc, String price, String img) {
        String url = OP_URL + "edit/drink";
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("name", name));
        data.add(new BasicNameValuePair("desc", desc));
        data.add(new BasicNameValuePair("img", img));
        data.add(new BasicNameValuePair("price", price));
        data.add(new BasicNameValuePair("id", id));
        data.add(new BasicNameValuePair("performedBy", Storage.getId()));
        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;

        try {
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
     
     public JSONObject fetchDrinks() {
        String url = OP_URL + "fetch/drink";

        try {
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }

    public JSONObject deleteDrink(String id) {
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("id", id));

        String url = OP_URL + "delete/drink";
        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;
        try {
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
     
  
        
    public JSONObject createDrinkOrder(String ...x) throws JSONException {
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("channel", State.channel_FRONT));
        data.add(new BasicNameValuePair("orders", x[0]));
        data.add(new BasicNameValuePair("guest_firstName", x[1]));
        data.add(new BasicNameValuePair("guest_lastName", x[2]));
        data.add(new BasicNameValuePair("guest_phone", x[3]));
        data.add(new BasicNameValuePair("performedBy", Storage.getId()));

        String url = OP_URL + "create/drinkorders";
        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;
        try {
            HttpGet request = new HttpGet(url);
            request.setHeader("User-Agent", USER_AGENT);
            request.setHeader("token", Storage.auth_token);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse execute = httpClient.execute(request);
            String toString = EntityUtils.toString(execute.getEntity());
            System.out.println(toString);
            return new JSONObject(toString);
            
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }     
     
    
    
            
    public static JSONObject createDrinkOrderTest(String ...x) throws JSONException {
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("channel", State.channel_FRONT));
        data.add(new BasicNameValuePair("orders", x[0]));
        data.add(new BasicNameValuePair("guest_firstName", x[1]));
        data.add(new BasicNameValuePair("guest_lastName", x[2]));
        data.add(new BasicNameValuePair("guest_phone", x[3]));
        data.add(new BasicNameValuePair("performedBy", Storage.getId()));

        String url = OP_URL + "create/drinkorders";
        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;
        try {
            HttpGet request = new HttpGet(url);
            request.setHeader("User-Agent", USER_AGENT);
            request.setHeader("token", Storage.auth_token);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse execute = httpClient.execute(request);
            String toString = EntityUtils.toString(execute.getEntity());
            System.out.println(toString);
            return new JSONObject(toString);
            
        } catch (IOException e) {
            e.printStackTrace(); 
//            main.responseError("Network problem");
        }
        return null;
    }     
     
         
     public JSONObject fetchDrinksOrders(String channel, String d1,String d2) {
        String url = OP_URL + "fetch/drinkorders";
        List<NameValuePair> data = new ArrayList<>();

        if(d1!=null){
            data.add(new BasicNameValuePair("d1", d1));
            data.add(new BasicNameValuePair("d2", d2));
        }
        if(channel!=null){
            data.add(new BasicNameValuePair("channel", channel));
        }

        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;

        try {
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
     
             
     public JSONObject approveDrinksOrders(String id) {
        String url = OP_URL + "approve/drinkorders";
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("id", id));
        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;
        try {
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
    
    public JSONObject cancelDrinksOrders(String id) {
        String url = OP_URL + "cancel/drinkorders";
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("id", id));
        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;
        try {
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
     
        
    public JSONObject doneDrinksOrders(String id) {
        String url = OP_URL + "done/drinkorders";
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("id", id));
        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;
        try {
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
     
    

    
    
    public boolean verifyAdmin(String... x) {
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("username", x[0]));
        data.add(new BasicNameValuePair("password", x[1]));

        String param = URLEncodedUtils.format(data, "utf-8");
        try {
            HttpPost post = new HttpPost(BASE_URL + "login");
            post.setEntity(new UrlEncodedFormEntity(data));
            httpClient.execute(post);
//            if (res.getInt("status") == 1) {
//                Storage.setAuth_token(res.getString("token"));
//            }
            if(res==null)return false;
                
            return res.getInt("status")==1;
        } catch (IOException | JSONException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return false;
    }
     
    public static JSONObject rawTest(String array) throws JSONException {
        //2016-09-22
        List<NameValuePair> data = new ArrayList<>();
//        data.add(new BasicNameValuePair("id", id));
        data.add(new BasicNameValuePair("orders", array));
        data.add(new BasicNameValuePair("guest_firstName", array));
        data.add(new BasicNameValuePair("guest_lastName", array));
        data.add(new BasicNameValuePair("guest_phone", array));

        String url = OP_URL + "create/orders";
        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;
        try {
            HttpGet request = new HttpGet(url);
            request.setHeader("User-Agent", USER_AGENT);
            request.setHeader("token", Storage.auth_token);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse execute = httpClient.execute(request);
            String toString = EntityUtils.toString(execute.getEntity());
            System.out.println(toString);
            return new JSONObject(toString);
            
        } catch (IOException e) {
            e.printStackTrace(); 
//            main.responseError("Network problem");
        }
        return null;
    }
     
          
      public JSONObject fetchCutomersDetails(){
        
        //2016-09-22
        List<NameValuePair> data = new ArrayList<>();
//        data.add(new BasicNameValuePair("d1", d1));
//        data.add(new BasicNameValuePair("d2", d2));
        String url = OP_URL+"fetch/customerdetail";
        String param = URLEncodedUtils.format(data, "utf-8");
        url +="?"+ param;
        try{
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); 
            main.responseError("Network problem");
        }
        return null;
    }
         
             
     public JSONObject fetchFolio( String d1,String d2) {
        String url = OP_URL + "fetch/folio";
        List<NameValuePair> data = new ArrayList<>();

        if(d1!=null){
            data.add(new BasicNameValuePair("d1", d1));
            data.add(new BasicNameValuePair("d2", d2));
        }


        String param = URLEncodedUtils.format(data, "utf-8");
        url += "?" + param;

        try {
            HttpGet request = new HttpGet(url);
            httpClient.execute(request);
            return res;
        } catch (IOException e) {
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }
     
      
         
    public JSONObject upload(File file){
        try{
            HttpPost post = new HttpPost(OP_URL+"static/upload");
            MultipartEntityBuilder mpEntity = MultipartEntityBuilder.create();
            mpEntity.addBinaryBody("file", file, ContentType.create("image/jpeg"), file.getName());
            post.setEntity(mpEntity.build());
            httpClient.execute(post);
            return res;
        } catch (IOException e) {
            res=null;
            e.printStackTrace(); main.responseError("Network problem");
        }
        return null;
    }

    public static String getIMG_URL() {
        return IMG_URL;
    }
    
    public static Map<String, String> parse(JSONObject json, Map<String, String> out) throws JSONException {
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            String val = null;
            try {
                JSONObject value = json.getJSONObject(key);
                parse(value, out);
            } catch (Exception e) {
                val = json.getString(key);
            }

            if (val != null) {
                out.put(key, val);
            }
        }
        return out;
    }
    
}
