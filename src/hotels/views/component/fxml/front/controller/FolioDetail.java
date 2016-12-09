
package hotels.views.component.fxml.front.controller;

import hotels.Hotels;
import hotels.reports.Reporter;
import hotels.util.Navigator2;
import hotels.util.Util;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.MaskerPane;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class FolioDetail implements Initializable {

    @FXML
    private ListView<?> list;
    @FXML
    private StackPane node;
    @FXML
    private ChoiceBox<?> viewFor;
    @FXML
    private HBox datePickerBox;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private ChoiceBox<?> dept;
    private SwingNode swingNode;
    private MaskerPane mp;
    
    private String guestId;
    private String guestName;
    private String guestPhone;

    public void setGuestData(String guestId,String guestName,String guestPhone) {
        this.guestId = guestId;
        this.guestPhone = guestPhone;
        this.guestName = guestName;
    }
    
    private Hotels app;
    private Navigator2 nav;
    
    
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public FolioDetail(Hotels app) {
        this.app=app;
        nav=new Navigator2(app.getMain());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        nightTask();
    }    
    
    private void defaults(){
        swingNode = new SwingNode();
        node.getChildren().add(swingNode);
        
        
        Util.formatDatePicker2(startDate);
        Util.formatDatePicker2(endDate);
        
    }
    
    private void nightTask(){
        if(mp == null ){
            mp= new MaskerPane();
            node.getChildren().add(mp);
        }
        mp.setVisible(true);
        
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                doProcess();
            }
        });
        th.setDaemon(true);
        th.setPriority(Thread.NORM_PRIORITY);
        th.start();
    }
    
    private void doProcess(){
        JSONObject fetchNight = nav.fetchFolioDetail(guestId, guestPhone, startDate.getValue().toString(),endDate.getValue().toString());
        
        if (fetchNight!=null){
            JasperViewer buildNight = new Reporter().buildNight(fetchNight);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    //((JFrame)buildNight).getRootPane()
                    JFrame f = buildNight;
                    
                    swingNode.setContent((JComponent) f.getContentPane());
                    mp.setVisible(false);
                }
            });
        };
    }
    
}