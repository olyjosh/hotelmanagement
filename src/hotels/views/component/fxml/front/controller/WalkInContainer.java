/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.front.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class WalkInContainer implements Initializable {

    @FXML
    private StackPane changePane;
    @FXML private Pagination pagination;


//    private HospitalFx app;
    private AnchorPane  reg1, reg2,reg3;
    
//    public HospitalFx getApp() {
//        return app;
//    }
//
//    public void setApp(HospitalFx app){
//        this.app = app;
//    }
//        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        powerPagination();
    }

    private void powerPagination() {
        
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex);
            }
        });
                
        pagination.setCurrentPageIndex(0);

    }

    private Node createPage(int p) {
        try {
            if (p == 0) {
                if(reg1!=null)return reg1;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/walkIn_1.fxml"));
                //Parent root = (Parent)fxmlLoader.load();
                //fxmlLoader.setController(logCont);
                return reg1=(AnchorPane) fxmlLoader.load();
            }else if (p == 1){
                if(reg2!=null)return reg2;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/walkIn_2.fxml"));
                return reg2=(AnchorPane) fxmlLoader.load();

            }else if (p==2){
                if(reg3!=null)return reg3;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/walkIn_3.fxml"));
                return reg3=(AnchorPane) fxmlLoader.load();
            }
        } catch (IOException ex) {
            Logger.getLogger(WalkInContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reg1;
    }
    
}
