/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.front.controller;

import hotels.views.component.chatBubble.BubbleSpec;
import hotels.views.component.chatBubble.BubbledLabel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author NOVA
 */
public class GuestMessage implements Initializable {

    @FXML
    private VBox msg_pane;
    @FXML
    private TextArea txt_field;
    @FXML
    private Button send_btn;
    private HBox msgBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Insets in = new Insets(20,30,10,20);
        msg_pane.setSpacing(10);
        msg_pane.setPadding(in);
        
    }    
    
    @FXML 
    private void postMessage(){// sender 1 = staff, sender 0 = guest
        
        String msg = txt_field.getText();
        
        BubbledLabel bl2 = new BubbledLabel(BubbleSpec.FACE_LEFT_CENTER);
        bl2.relocate(310, 100);
        bl2.setText(msg);
        bl2.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK,
                        null, null)));
        msgBox = new HBox();
        msgBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        msgBox.getChildren().add(bl2);
        msg_pane.getChildren().add(msgBox);
        
        //===================================OJORO DOWN HERE================
        recMessage();
    }
    
    private void recMessage(){
        
        String msg = txt_field.getText();
        
        BubbledLabel bl2 = new BubbledLabel(BubbleSpec.FACE_RIGHT_CENTER);
        bl2.relocate(310, 100);
        bl2.setStyle("-fx-text-fill: white");
        bl2.setText(msg);
        bl2.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,
                        null, null)));
        msgBox = new HBox();
        msgBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        msgBox.getChildren().add(bl2);
        msg_pane.getChildren().add(msgBox);
    }
}
