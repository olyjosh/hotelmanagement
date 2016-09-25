/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hotels.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Olyjosh
 */
public class TestClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JSONException {
        // TODO code application logic here
        Navigator n = new Navigator();
        JSONArray jsonArray = n.fetchRoomType().getJSONArray("message");
        JSONObject jsonObject = jsonArray.getJSONObject(0);

    
    }

}
