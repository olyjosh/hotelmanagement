package hotels.views.component.fxml.restaurant.controller;

import hotels.Hotels;
import hotels.util.Navigator2;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class NewFood implements Initializable {

    @FXML private VBox progressBox;
    @FXML private StackPane imgStack;
    @FXML private Text dragText,error;
    @FXML private ImageView imgV;
    @FXML private TextField price,name;
    @FXML private TextArea desc;
    @FXML private Button save;
    
 
    
    private Hotels app;
    private Navigator2 nav;
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public NewFood(Hotels app) {
        this.app =app;
        nav = new Navigator2(getApp().getMain());
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        defaults();
    }    
    
    
    private void defaults(){
        imgStack.setOnMouseClicked((MouseEvent)->pickFile());
//        imgV.setOnMouseClicked((MouseEvent)->pickFile());
//        dragText.setOnMouseClicked((MouseEvent)->pickFile());
        dragText.setText("Drag an image here or\n click to select image");
    }

    @FXML private void dragDetected(DragEvent e){
        System.out.println("Drag Detected");
    }
        

    @FXML private void dragDropped(DragEvent e){
        System.out.println("Drag DROPED");
        
        Dragboard db = e.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            filePath = null;
            File file = db.getFiles().get(0);
            filePath = file.getAbsolutePath();
            try {
                imgV.setImage(new Image(new FileInputStream(file)));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(NewFood.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        e.setDropCompleted(success);
        e.consume();
    }
    
    @FXML private void dragDone(DragEvent e){
        imgStack.setStyle("-fx-background-color: white;");
        System.out.println("DONE");
    }
 
        
    @FXML private void dragEnter(DragEvent e){
        imgStack.setStyle("-fx-background-color: #a6eaaa;");
        System.out.println("DRAG ENTER");
    }
    
     @FXML private void dragExited(DragEvent e){
        imgStack.setStyle("-fx-background-color: white;");
        System.out.println("DONE");
    }
 

  @FXML private void mouseDragDropped(final DragEvent e) {
        final Dragboard db = e.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            // Only get the first file from the list
            final File file = db.getFiles().get(0);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    System.out.println(file.getAbsolutePath());
                    try {

                        Image img = new Image(new FileInputStream(file.getAbsolutePath()));  
                        imgV.setImage(img);
                        
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(NewFood.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
        e.setDropCompleted(success);
        e.consume();
    }
 
    @FXML private  void mouseDragOver(final DragEvent e) {
        final Dragboard db = e.getDragboard();
 
        final boolean isAccepted = db.getFiles().get(0).getName().toLowerCase().endsWith(".png")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpeg")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpg");
 
        if (db.hasFiles()) {
            if (isAccepted) {
                imgStack.setStyle("-fx-border-color: red;"
              + "-fx-border-width: 5;"
              + "-fx-background-color: #C6C6C6;"
              + "-fx-border-style: solid;");
                e.acceptTransferModes(TransferMode.COPY);
            }
        } else {
            e.consume();
        }
    }
     
     
     
     
     
     private String filePath;
     private void pickFile(){
        File f = filechoose(new FileChooser.ExtensionFilter("Images - Only jpeg and png are supported", "*.jpeg", "*.jpg","*.spng"));
        if(f!=null){
            try {
                imgV.setImage(new Image(new FileInputStream(f)));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(NewFood.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }     
    
    private File filechoose(FileChooser.ExtensionFilter... ext) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Food Image");
        if (ext != null) {
            fc.getExtensionFilters().addAll(ext);
        }
        return fc.showOpenDialog(app.getStage());
    }
    
    
    private void uploadTask(){
        Runnable task = new Runnable() {
            public void run() {
                create();
            }
        };
        // Run the task in a background thread
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
    }

    @FXML private void createFood(){
        if(name.getText().isEmpty() || price.getText().isEmpty() || desc.getText().isEmpty()){
            error.setVisible(true);
            error.setText("Please fill all fields");
            return;
        }
        if(imgV.getImage()==null){
            error.setVisible(true);
            error.setText("image of this food is required");
            return;
        }
        save.setDisable(true);
        uploadTask();
        
    }
    
    private void create() {
        String nameS = name.getText();
        String priceS = price.getText();
        String descS = desc.getText();
        String image = null;
        JSONObject upload = nav.upload(new File(filePath));
        try {
            int a = upload.getInt("status");
            if(a==1){
                String s = upload.getString("message");
                image = s;
                nav.createFood(nameS, descS, priceS, image);
            }
        } catch (JSONException ex) {
            Logger.getLogger(NewFood.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
