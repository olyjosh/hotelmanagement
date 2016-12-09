
package hotels.views.component.fxml.admin.controller;

import hotels.Hotels;
import hotels.util.Navigator;
import hotels.util.Navigator2;
import hotels.util.State;
import hotels.util.Util;
import hotels.views.component.fxml.admin.model.RoomType;
import hotels.views.component.fxml.bar.controller.NewDrink;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author NOVA
 */
public class NewRoomTypeController implements Initializable {

    @FXML
    private TextField alias;
    @FXML
    private TextField name;
    @FXML
    private TextArea desc;
    @FXML
    private TextField rate;
    @FXML
    private TextField adultRate;
    @FXML
    private TextField childRate;
    @FXML
    private TextField obp;
    @FXML
    private Spinner<Integer> bapax;
    @FXML
    private Spinner<Integer> mapax;
    @FXML
    private Spinner<Integer> bcpax;
    @FXML
    private Spinner<Integer> mcpax;
    @FXML private ImageView imgV1, imgV2, imgV3, imgV4, imgV5;
    @FXML private StackPane imgStack1,imgStack2,imgStack3,imgStack4,imgStack5;
    @FXML private Text dragText1,dragText2,dragText3,dragText4,dragText5;
    private boolean imageEdit1,imageEdit2, imageEdit3, imageEdit4, imageEdit5;
    private List<String> localFiles, fileIds;
    private RoomType data;
    private boolean editMode;
    
    private Hotels app;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public NewRoomTypeController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
    }

    private Navigator nav;
    private JSONObject response;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        bapax.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10));
        mapax.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10));
        bcpax.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10));
        mcpax.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10));
        
        defaults();
    }   
    
    private void defaults(){
        localFiles = new ArrayList<>(5);
        imgStack1.setOnMouseClicked((MouseEvent)->pickFile(imgV1,0));
        imgStack2.setOnMouseClicked((MouseEvent)->pickFile(imgV2,1));
        imgStack3.setOnMouseClicked((MouseEvent)->pickFile(imgV3,2));
        imgStack4.setOnMouseClicked((MouseEvent)->pickFile(imgV4,3));
        imgStack5.setOnMouseClicked((MouseEvent)->pickFile(imgV5,4));
        
        dragText1.setText("Drag an image here or\n click to select image");
        dragText2.setText("Drag an image here or\n click to select image");
        dragText3.setText("Drag an image here or\n click to select image");
        dragText4.setText("Drag an image here or\n click to select image");
        dragText5.setText("Drag an image here or\n click to select image");
        
        if (editMode) {
            alias.setText(data.getAlias());
            name.setText(data.getName());
            desc.setText(data.getDesc());
            rate.setText(""+data.getRate());
            adultRate.setText(""+data.getAdultRate());
            childRate.setText(""+data.getChildRate());
            obp.setText(""+data.getOverBooking());
            bapax.getEditor().setText(""+data.getBa());
            bcpax.getEditor().setText(""+data.getBc());
            mapax.getEditor().setText(""+data.getMa());
            mcpax.getEditor().setText(""+data.getMc());
            
            List<String> images = data.getImages();
            for (int i = 0; i < images.size(); i++) {
                String get = images.get(i);
                if(get==null)break;
                ImageView v =null;
                switch(i){
                    case 0 : v=imgV1; break;
                    case 1 : v=imgV2; break;
                    case 2 : v=imgV3; break;
                    case 3 : v=imgV4; break;
                    case 4 : v=imgV5; break;
                }
                v.setImage(Util.getImage(get));
            }
            fileIds =images;
        }else{
            
        }
    }

    public void setData(RoomType data) {
        this.data = data;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
    
            
     File upFile;
     private void pickFile(ImageView imgV,int i){
        File f = filechoose(new FileChooser.ExtensionFilter("Images - only jpeg and png are supported", "*.jpeg", "*.jpg","*.spng"));
        if(f!=null){
            upFile=f;
            localFiles.add(i, f.getAbsolutePath());
            setImageFromFile(imgV, i);
        }
    }     

    private File filechoose(FileChooser.ExtensionFilter... ext) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Food Image");
        if (ext != null) {
            fc.getExtensionFilters().addAll(ext);
        }
        return fc.showOpenDialog(this.imgV1.getScene().getWindow());
    }
    
        
    private void setImageFromFile(ImageView imgV, int imageEdit){
        try {
            imgV.setImage(new Image(new FileInputStream(upFile)));
            switch (imageEdit){
                case 1 : imageEdit1=true;break;
                case 2 : imageEdit2=true;break;
                case 3 : imageEdit3=true;break;
                case 4 : imageEdit4=true;break;
                case 5 : imageEdit5=true;break;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewDrink.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String filesIds(List<String> x){
        JSONArray j = new JSONArray();
        List xy = new ArrayList();
        for (int i = 0; i < x.size(); i++) {
            String xx=x.get(i);
            if(xx!=null){
                xy.add(xx);
            }
        }
        return new JSONArray(xy).toString();
    }
    
    private boolean validat(){
        return true;
    }
    
        
    
    private void clearFieds(){
        alias.clear();
        name.clear();
        desc.clear();
        rate.clear();
        adultRate.clear();
        childRate.clear();
        obp.clear();
        bapax.getEditor().setText(""+1);
        bcpax.getEditor().setText(""+1);
        mapax.getEditor().setText(""+1);
        mcpax.getEditor().setText(""+1);
        imageEdit1=false;
        imageEdit2=false;
        imageEdit3=false;
        imageEdit4=false;
        imageEdit5=false;
        imgV1.setImage(null);
        imgV2.setImage(null);
        imgV3.setImage(null);
        imgV4.setImage(null);
        imgV5.setImage(null);
        localFiles=null;
        fileIds=null;
        
    }
        
    
    @FXML
    private void newRoomType(ActionEvent event) throws JSONException {
        if(!validat())return;
        String uploadFiles = uploadFiles();
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("alias", alias.getText()));
        param.add(new BasicNameValuePair("name", name.getText()));
        param.add(new BasicNameValuePair("desc", desc.getText()));
        param.add(new BasicNameValuePair("rate_rate", rate.getText()));
        param.add(new BasicNameValuePair("rate_adult", adultRate.getText()));
        param.add(new BasicNameValuePair("rate_child", childRate.getText()));
        param.add(new BasicNameValuePair("rate_overBookingPercentage", obp.getText()));
        param.add(new BasicNameValuePair("pax_baseAdult", bapax.getEditor().getText()));
        param.add(new BasicNameValuePair("pax_baseChild", bcpax.getEditor().getText()));
        param.add(new BasicNameValuePair("pax_maxAdult", mapax.getEditor().getText()));
        param.add(new BasicNameValuePair("pax_maxChild", mcpax.getEditor().getText()));
        param.add(new BasicNameValuePair("color", "0000FF"));
        param.add(new BasicNameValuePair("images",uploadFiles ));
        
        response =editMode? nav.editRoomType(param) : nav.createRoomType(param);
        if(response!=null){
            int a = response.getInt("status");
            if(a==1){
                String s = editMode?" Room Type Updated":" Room Type Created";
                Util.notify_SUCCESS(State.NOTIFY_SUCCESS, name.getText() +s, Pos.CENTER);
                clearFieds();
                
            }else{
                Util.notify_ERROR(State.NOTIFY_SUCCESS, name.getText() +" Error while performing operation", Pos.CENTER);
            }
        }
        
    }
    

      @FXML
    private void close(ActionEvent event){
        ((Stage)this.alias.getScene().getWindow()).close();
        
    }

    private String uploadFiles() throws JSONException {
        if (fileIds == null) {
            fileIds = new ArrayList();
        }
        
        
        Navigator2 nav2 = new Navigator2(this.getApp().getMain());
        for (int i = 0; i < localFiles.size(); i++) {
            String get = localFiles.get(i);
            if (get != null) {
                JSONObject upload = nav2.upload(new File(get));
                if (upload != null) {
                    int a = upload.getInt("status");
                    if (a == 1) {
                        String s = upload.getString("fileId");
                        fileIds.add(i, s);
                    }
                }
            }
        }
        
        return filesIds(fileIds);
    }

    
    @FXML private void dragDropped(DragEvent e){
        System.out.println("Drag DROPED");
        
        Dragboard db = e.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;

            upFile = db.getFiles().get(0);
//just            setImageFromFile();

        }
        e.setDropCompleted(success);
        e.consume();
    }

    
    @FXML private void dragDone(DragEvent e){
        StackPane imgStack = (StackPane)e.getSource();
        imgStack.setStyle("-fx-background-color: white;");
    }
 
//        
//    @FXML private void dragEnter(DragEvent e){
//        imgStack.setStyle("-fx-background-color: #a6eaaa;");
//        System.out.println("DRAG ENTER");
//    }
//    
//     @FXML private void dragExited(DragEvent e){
//        imgStack.setStyle("-fx-background-color: white;");
//        System.out.println("DONE");
//    }
// 

  @FXML private void mouseDragDropped(final DragEvent e) throws FileNotFoundException {
        final Dragboard db = e.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            upFile = db.getFiles().get(0);
            System.out.println("DRAGGED FILE :"+upFile);
            Image img = new Image(new FileInputStream(upFile));
// just           imgV.setImage(img);
        }
        e.setDropCompleted(success);
        e.consume();
    }
 
    @FXML private  void mouseDragOver(final DragEvent e) {
        final Dragboard db = e.getDragboard();
        String toLowerCase = db.getFiles().get(0).getName().toLowerCase();
        final boolean isAccepted = toLowerCase.endsWith(".png")
                || toLowerCase.endsWith(".jpeg")
                || toLowerCase.endsWith(".jpg");
 
        if (db.hasFiles()) {
            if (isAccepted) {
//                imgStack.setStyle("-fx-background-color: #a6eaaa;");


//                imgStack.setStyle("-fx-border-color: a6eaaa;"
//              + "-fx-border-width: 5;"
//              + "-fx-background-color: #C6C6C6;"
//              + "-fx-border-style: solid;");
                e.acceptTransferModes(TransferMode.COPY);
            }
        } else {
            e.consume();
        }
    }    
    
}
