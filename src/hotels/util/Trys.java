/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hotels.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Olyjosh
 */
public class Trys {

    
    public static void main(String[] args) throws JSONException {
//        57fcf4381c7114122e59e894
        
        List a = new ArrayList();
//        a.add("1");
//        a.add("2");
//        a.add("3");
  
        for (int i = 0; i < 3; i++) {
            HashMap hashMap = new HashMap();
            hashMap.put("price", "The text");
            hashMap.put("qty", "The text");
            hashMap.put("food", "57fcf4381c7114122e59e894");
            a.add(new JSONObject(hashMap));
        }
        JSONArray array = new JSONArray(a);
//        System.out.println(array.toString());
        JSONObject rawTest = Navigator2.rawTest(array.toString());
        System.out.println(rawTest);
    }
}
