
package hotels.views.component.fxml.front.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class RoomsPane implements Initializable {

    @FXML private Label all,vacant,occupied,dirty,outOfOrder,reserved,dueOut;
    @FXML private TabPane floorTab;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addFloors("Ground Floor" ,"THE DATABASE ID");
        addFloors("First Floor", "THE DATABASE ID");
        
        floorTab.selectionModelProperty().addListener(new ChangeListener<SingleSelectionModel<Tab>>() {
            @Override
            public void changed(ObservableValue<? extends SingleSelectionModel<Tab>> observable, SingleSelectionModel<Tab> oldValue, SingleSelectionModel<Tab> newValue) {
                
            }
        });
    }    
    
    private void retrieveFloors(){
        
    }
    
    private void addFloors(String floor, String floorId){
        Tab tab = new Tab(floor);
        tab.getProperties().put("id", floorId);
        floorTab.getTabs().add(tab);
        
    }
    
    private void addFloors(String floor, Node content){
        floorTab.getTabs().add(new Tab(floor, content));
    }
    
//    @FXML
//    private void showbookingCardLayoutView() throws IOException{
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/roomsPane.fxml"));
//        //loader.setController(controller);
//        AnchorPane content = (AnchorPane)loader.load();
//        ObservableList<Node> children = frontContentStack.getChildren();
//        if(children.size()>0)frontContentStack.getChildren().remove(0, children.size());
//        children.add(content);
//        
//    }
//    
}
