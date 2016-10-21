/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hotels.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Olyjosh
 */
public class Trys {

    
    public static void main(String[] args) {
        
        System.out.println(System.getProperty("user.country"));; 
        showPriceInUSD(40);
    }
    
    public static void showPriceInUSD(double price) {
        
        double priceInUSD = price;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        System.out.printf("Price in USD : %s %n", currencyFormat.format(priceInUSD));
    }

    
    public static void sendArray() throws JSONException {
//        57fcf4381c7114122e59e894
        
        List a = new ArrayList();
//        a.add("1");
//        a.add("2");
//        a.add("3");
        String [] aa = {"57fcf8281c7114122e59e895","57fd38181c7114122e59e896","57fcf4381c7114122e59e894"};
        for (int i = 0; i < 3; i++) {
            HashMap hashMap = new HashMap();
            hashMap.put("price", i);
            hashMap.put("qty", 3);
            hashMap.put("food", aa[i]);
            a.add(new JSONObject(hashMap));
        }
        JSONArray array = new JSONArray(a);
        JSONObject rawTest = Navigator2.createFoodOrderTest(array.toString(),"Joshua","Aroke","08061662025");
        System.out.println(rawTest);
    }
}



//
//db.guests.drop();db.floors.drop();db.roomtypes.drop();db.services.drop();db.identitycounters.drop();db.laundryitems.drop();db.lostfounds.drop();db.mealorders.drop();db.meals.drop();db.phones.drop();db.reminders.drop();db.returnins.drop();
//
//floors
//guests
//identitycounters
//laundryitems
//lostfounds
//mealorders
//meals
//phones
//reminders
//returnins
//roomtypes
//services
