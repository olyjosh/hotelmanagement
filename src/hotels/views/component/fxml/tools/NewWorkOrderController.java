/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.tools;

import hotels.Hotels;
import hotels.util.Navigator;
import hotels.util.State;
import hotels.util.Util;
import hotels.views.component.fxml.tools.model.WorkOrder;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author NOVA
 */
public class NewWorkOrderController implements Initializable {

    @FXML
    private TextField orderNo;
    @FXML
    private TextArea description;
    @FXML
    private DatePicker dateIssued;
    @FXML
    private ComboBox room;
    @FXML
    private ComboBox assignedTo;
    @FXML
    private ComboBox status;
    @FXML
    private DatePicker dueDate;
    @FXML
    private TextArea remark;
    @FXML
    private TextField workType;

    
    private Hotels app;
    private Navigator nav;
    private JSONObject response;
    private boolean editMode;
    private WorkOrder data;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public WorkOrder getData() {
        return data;
    }

    public void setData(WorkOrder data) {
        this.data = data;
    }
    

    public NewWorkOrderController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Util.formatDatePicker(dateIssued);
        Util.formatDatePicker(dueDate);
        onLoad();
    }    
    
    private void onLoad(){
        if(isEditMode()){
            popEdit();
        }
    }
    
    private void popEdit(){
        orderNo.setText(data.getOrderNo());
        description.setText(data.getDes());
        dateIssued.getEditor().setText(data.getDateIssued());
        room.getSelectionModel().select(data.getRoom());
        assignedTo.getSelectionModel().select(data.getAssignedTo());
        status.getSelectionModel().select(data.getStatus());
        dueDate.getEditor().setText(data.getDueDate());
        remark.setText(data.getRemark());
        workType.setText(data.getWorkType());
    }
    
    @FXML private void newWorkOrder(){

        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("date", dateIssued.getEditor().getText()));
        param.add(new BasicNameValuePair("type", workType.getText()));
        param.add(new BasicNameValuePair("workOrderNo", orderNo.getText()));
        param.add(new BasicNameValuePair("desc", description.getText()));
        param.add(new BasicNameValuePair("assignedTo", assignedTo.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("room", room.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("status", status.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("dueDate", dueDate.getEditor().getText()));
        param.add(new BasicNameValuePair("remarks", remark.getText()));
        param.add(new BasicNameValuePair("performedBy", "57deca5d35fb9a487bdeb70f"));
      
        if(!isEditMode()){
            
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    try {
                        response = nav.createWorkOrder(param);
                        if(response != null && response.getInt("status") == 1){
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_SUCCESS, "A New Work Order has been Issued and Saved", Pos.CENTER);
                                }
                            });
                        }else{

                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_ERROR, "New Work Order Failed to Save", Pos.CENTER);
                                }
                            });
                        }
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            };
            Thread back = new Thread(task);
            back.setPriority(Thread.MAX_PRIORITY);
            back.setDaemon(true);
            back.start();
        }else{

            Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    param.add(new BasicNameValuePair("id", data.getId()));
                    response = nav.editWorkOrder(param);
                        if(response != null && response.getInt("status") == 1){
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_SUCCESS, "Work Order has been Updated", Pos.CENTER);
                                }
                            });
                        }else{

                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_ERROR, "Work Order Failed to Update", Pos.CENTER);
                                }
                            });
                        }
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            };
            Thread back = new Thread(task);
            back.setPriority(Thread.MAX_PRIORITY);
            back.setDaemon(true);
            back.start();
        }
    }
    
}
