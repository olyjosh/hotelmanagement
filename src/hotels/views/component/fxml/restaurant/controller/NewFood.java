package hotels.views.component.fxml.restaurant.controller;

import hotels.views.component.fxml.restaurant.model.FoodModel;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import hotels.Hotels;
import hotels.util.Codes;
import hotels.util.Navigator2;
import hotels.util.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import org.json.JSONArray;
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
    @FXML private ImageView imgV,imgV2;
    @FXML private TextField price,name;
    @FXML private TextArea desc,deatil;
    @FXML private Button save;
    @FXML private TableView<FoodModel> table;
    @FXML private TableColumn<FoodModel, String> nameCol, priceCol;
    @FXML private TableColumn statusCol;
    @FXML private TitledPane createTitledPane;
    @FXML private Hyperlink exitEdit;
    @FXML private StackPane paneStack;
    @FXML private HBox priceBox;
    
    
    private ObservableList<FoodModel> list;
    final private Text AVAIL = GlyphsDude.createIcon(FontAwesomeIcons.CIRCLE);
    final private Text NOT_AVAIL = GlyphsDude.createIcon(FontAwesomeIcons.CIRCLE);
    
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
        loadFoodTask();
    }    
    
    
    private void defaults(){
        AVAIL.setFill(Paint.valueOf(Codes.COLOR_VACANT));
        NOT_AVAIL.setFill(Paint.valueOf(Codes.COLOR_OUT_ORDER));
        imgStack.setOnMouseClicked((MouseEvent)->pickFile());
        dragText.setText("Drag an image here or\n click to select image");
        
        nameCol.setCellValueFactory(new PropertyValueFactory<FoodModel, String>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<FoodModel, String>("price"));

        
//        statusCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FoodModel, Text>, ObservableValue<Text>>() {
//            @Override
//            public ObservableValue<Text> call(TableColumn.CellDataFeatures<FoodModel, Text> param) {
//                return (ObservableValue<Text>) AVAIL;
//            }
//        });
        list= FXCollections.observableArrayList();
        table.setItems(list);
        
        table.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends FoodModel> observable, FoodModel oldValue, FoodModel newValue) -> {
            if (newValue != null) {
                deatil.setText(newValue.getDesc());
                Image image = Util.getImage(newValue.getImage());
                imgV2.setImage(image);
            }
//            if(){}
            
        });
        
        table.setRowFactory(new Callback<TableView<FoodModel>, TableRow<FoodModel>>() {
            @Override
            public TableRow<FoodModel> call(TableView<FoodModel> param) {
                final TableRow<FoodModel> row = new TableRow<>();
                MenuItem delete = new MenuItem("Delete", GlyphsDude.createIcon(FontAwesomeIcons.REMOVE)),
                        edit = new MenuItem("Edit", GlyphsDude.createIcon(FontAwesomeIcons.PENCIL)),
                        avail = new MenuItem("Set As NOT Available", GlyphsDude.createIcon(FontAwesomeIcons.PENCIL));//        
                ContextMenu con = new ContextMenu(avail,edit, delete);
                delete.setOnAction((ActionEvent event) -> {
                    delete(row.getItem());
                });
                
                edit.setOnAction((ActionEvent event) -> {
                    setEditMode();
                });
                
                avail.setOnAction((ActionEvent event) -> {
//                    newOrEdit.set(false);
                });
                
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                        .then((ContextMenu) null)
                        .otherwise(con)
                );
//                
                row.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        FoodModel item = row.getItem();
                        if(item !=null){
                            if(Double.parseDouble(""+item.getPrice())==0)
                            row.setTooltip(new Tooltip("Needs Admin's price approval"));
                        }
                    }
                });
                return row;
                
            }
        });
        
    }
    
 
    private void delete(FoodModel x) {
        int i = table.getItems().indexOf(x);
        table.getItems().remove(x);
        JSONObject del = nav.deleteFood(x.getId());
        if (del != null) {
            try {
                if (del.getInt("status") == 1) {

                } else {
                    //allert problem while deleting
                }
            } catch (JSONException ex) {
                Logger.getLogger(NewFood.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //alert that it could not be deleted
            table.getItems().add(i, x);
        }
    }

    
    private boolean imageEdit = false; 
    private String id;
    private String fileId;
    private void setEditMode() {
        save.setText("Save");
        
        createTitledPane.setText("Edit Food");
        fadeAndStroke(paneStack);
        FoodModel s = table.getSelectionModel().getSelectedItem();
        name.setText(s.getName());
        desc.setText(s.getDesc());
        imgV.setImage(imgV2.getImage());
        id=s.getId();
        fileId = s.getImage();
        exitEdit.setVisible(true);
        
    }
    
    @FXML private void exitEditMode() {
        save.setText("Create New Schedule Task");
        exitEdit.setVisible(false);
        createTitledPane.setText("Create New Food Menu");
        clear();
        stopFadeAndStroke();
    }

    FadeTransition fadeTransition;
    private void fadeAndStroke(StackPane p) {
        Rectangle rect = new Rectangle(0, 0, createTitledPane.getPrefWidth() + 2, createTitledPane.getPrefHeight() + 2);
        rect.setArcHeight(5);
        rect.setArcWidth(5);
        rect.setFill(null);
        rect.setStroke(Color.DODGERBLUE);
        rect.setStrokeWidth(10);
        p.getChildren().add(rect);
        fadeTransition = new FadeTransition(Duration.seconds(3), rect);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0.3);
        fadeTransition.setCycleCount(Timeline.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
    }

    private void stopFadeAndStroke(){
        fadeTransition.stop();
        paneStack.getChildren().remove(1);
        fadeTransition=null;
    }
    
    
    
//    @FXML private void dragDetected(DragEvent e){
//        System.out.println("Drag Detected");
//    }
//        

    @FXML private void dragDropped(DragEvent e){
        System.out.println("Drag DROPED");
        
        Dragboard db = e.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;

            upFile = db.getFiles().get(0);
            setImageFromFile();

        }
        e.setDropCompleted(success);
        e.consume();
    }
    
    private void setImageFromFile(){
        try {
            imgV.setImage(new Image(new FileInputStream(upFile)));
            imageEdit=true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewFood.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML private void dragDone(DragEvent e){
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
            imgV.setImage(img);
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
                imgStack.setStyle("-fx-border-color: a6eaaa;"
              + "-fx-border-width: 5;"
              + "-fx-background-color: #C6C6C6;"
              + "-fx-border-style: solid;");
                e.acceptTransferModes(TransferMode.COPY);
            }
        } else {
            e.consume();
        }
    }
        
     File upFile;
     private void pickFile(){
        File f = filechoose(new FileChooser.ExtensionFilter("Images - only jpeg and png are supported", "*.jpeg", "*.jpg","*.spng"));
        if(f!=null){
            upFile=f;
            setImageFromFile();
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
    
    
    /**
     * @param edit 
     * true is expected for edit while false if creating new food
     */
    private void uploadTask(final boolean edit){
        Runnable task = new Runnable() {
            public void run() {
                create(edit);
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
        save.setText("Please wait...");
        if(exitEdit.isVisible())uploadTask(true);
        else uploadTask(false);
    }
    /**
     * @param edit 
     * true is expected for edit while false if creating new food
     */
    private void create(final boolean edit) {
        
        String nameS = name.getText();
        String priceS = price.getText();
        String descS = desc.getText();
        String image = null;
        System.out.println("UPLOAD  :"+upFile);
                
            try {
                String s;
                JSONObject createFood;
                if (edit) {
                    // Editing food here
                    
                    if (imageEdit) {
                        JSONObject upload = nav.upload(upFile);
                        if (upload != null) {
                            int a = upload.getInt("status");
                            if(a==1)
                            s = upload.getString("fileId");
                            else return;
                        }
                    }else image = fileId;
                    
                    createFood = nav.editFood(id,nameS, descS, priceS, image);
//                        add1ToTable(createFood.getJSONObject("message"));
                        if (createFood != null) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    clear();
                                    
                                    Util.notify("Edited", "Food Menu Edited Successfully", Pos.CENTER);
                                }
                            });
                        }
                    loadFoodTask();
                }else {
                    // Creating new Food here
                    
                    JSONObject upload = nav.upload(upFile);
                    int a = upload.getInt("status");
                    if (a == 1) {
                        
                        s = upload.getString("fileId");
                        image = s;
                        System.out.println(image);
                        createFood = nav.createFood(nameS, descS, priceS, image);
                        add1ToTable(createFood.getJSONObject("message"));
                        if (createFood != null) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    clear();
                                    Util.notify( "Food Created", "Food Menu Created Successfully", Pos.CENTER);
                                }
                            });
                        }
                    }
                }
            } catch (JSONException ex) {
                save.setText("Save");
                save.setDisable(false);
                Logger.getLogger(NewFood.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    
    private void clear() {
        imageEdit=false;
        imgV.setImage(null);
        name.clear();
        desc.clear();
        price.setText("0.00");
        save.setText("Save");
        save.setDisable(false);
    }
    
    @FXML private void loadFoodTask(){
        
        Runnable task = new Runnable() {
            public void run() {
                food();
            }
        };
        // Run the task in a background thread
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
    }
    
    private void food(){
        JSONObject foods = nav.fetchFoods();
        if(foods!=null){
            try {
                JSONArray a = foods.getJSONArray("message");
                list.clear();
                for (int i = 0; i < a.length(); i++) {
                    JSONObject o = a.getJSONObject(i);
                    
                    add1ToTable(o);
                    
                }
            } catch (JSONException ex) {
                Logger.getLogger(NewFood.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void add1ToTable(JSONObject o){
        try {
            FoodModel m = new FoodModel();
            m.setDesc(o.getString("desc"));
            m.setId(o.getString("_id"));
            m.setName(o.getString("name"));
            m.setPrice(""+o.getDouble("price"));
            m.setImage(o.getString("img"));
            list.add(m);
        } catch (JSONException ex) {
            Logger.getLogger(NewFood.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
  
//    private class ButtonCell extends TableCell<FoodModel, FoodModel> {
//
//
//    private Button cellButton;
//
//    ButtonCell(){
//          cellButton = new Button();
//        cellButton.setOnAction(new EventHandler<ActionEvent>(){
//
//            @Override
//            public void handle(ActionEvent t) {
//                // do something when button clicked
//                FoodModel record = getItem();
//                // do something with record....
//            }
//        });
//    }
//
//    //Display button if the row is not empty
//    @Override
//    protected void updateItem(FoodModel record, boolean empty) {
//        super.updateItem(record, empty);
//        if(!empty){
//            cellButton.setText("Something with "+record.getId());
//            setGraphic(cellButton);
//        } else {
//            setGraphic(null);
//        }
//    }
//}


}
