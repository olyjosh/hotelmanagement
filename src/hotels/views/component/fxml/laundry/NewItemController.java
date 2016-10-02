/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.laundry;

import hotels.Hotels;
import hotels.util.Navigator;
import hotels.util.State;
import hotels.util.Util;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author NOVA
 */
public class NewItemController implements Initializable {

    @FXML
    private TextField alias;
    @FXML
    private TextField name;
    @FXML
    private ComboBox cat;
    @FXML
    private RadioButton vhotel;
    @FXML
    private RadioButton vguest;
    @FXML
    private ImageView image;
    @FXML
    private TextArea desc;
    
    private Navigator nav;
    private JSONObject response;
    private Hotels app;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public NewItemController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList type = FXCollections.observableArrayList();
        type.add("Fabric");
        type.add("Linen");
        type.add("Native");
        type.add("Wool");
        cat.setItems(type);
        
        ToggleGroup tg = new ToggleGroup();
        vguest.setToggleGroup(tg);
        vhotel.setToggleGroup(tg);
    }    
    
    @FXML
    private void createItem(){
        String choice;
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("alias", alias.getText()));
        param.add(new BasicNameValuePair("name", name.getText()));
        param.add(new BasicNameValuePair("code", Util.random()));
        param.add(new BasicNameValuePair("category", cat.getSelectionModel().getSelectedItem().toString()));
        if(vguest.isSelected()){
            choice = "Guest";
        }else{
            choice = "Hotel";
        }
        param.add(new BasicNameValuePair("visibility", choice));
        param.add(new BasicNameValuePair("desc", desc.getText()));
        param.add(new BasicNameValuePair("performedBy", "57deca5d35fb9a487bdeb70f"));

        response = nav.createLaundryItem(param);
        System.out.println("Creating Laundry Item : " + response);
        
        nav.notify((Stage) alias.getScene().getWindow(), Pos.CENTER, State.NOTIFY_SUCCESS, "Laundry Item Created and Saved", 100,300);
    }
}
