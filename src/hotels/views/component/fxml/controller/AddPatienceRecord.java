package hotels.views.component.fxml.controller;

import hotels.Hotels;
import hotels.controllers.FXMLDocumentController;
import hotels.model.MongoDBInc;
import hotels.model.user.ClientM;
import hotels.util.Util;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 *
 * @author olyjosh
 */
public class AddPatienceRecord implements Initializable{

    @FXML private VBox symVBox,tesVBox,diaVBox,preVBox,comVBox;
    @FXML private FlowPane symPane,tesPane,diaPane,prePane,comPane;
    @FXML private AnchorPane addRecPane;
    @FXML private TextField symField,tesField,diaField,preField,comField;
    private Hotels app;
    private MongoDBInc m;
    private ClientM patience;
    private FXMLDocumentController home;
    
    /**
     * These variables collect new entries for suggestion usages on the fly 
     */
    private final Set newSym,newTes,newDia,newPre,newCom;
    private List[] data;
    
    
    public AddPatienceRecord() {
        this.newPre = new HashSet<>();
        this.newDia = new HashSet<>();
        this.newTes = new HashSet<>();
        this.newSym = new HashSet<>();
        this.newCom = new HashSet<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        m=app.getDbInstance();
        powerFields();
    }
    
    
    private void powerFields(){
//        String a[] = {"Fever","Head-ache","Nerval reversal","muscle ache","sneezing","Cough"};
//        for (int i = 0; i < a.length; i++) {
//            m.addSuggestionToDb(MongoDBInc.SYMPTOM_LIST, a[i]);
//        }
                
        
        if(m.isEmptySuggestion())m.addSuggestionToDb(MongoDBInc.SYMPTOM_LIST, Util.initSuggestion);

        TextFields.bindAutoCompletion(
                symField,m.suggestion(MongoDBInc.SYMPTOM_LIST))
                .setOnAutoCompleted((AutoCompletionBinding.AutoCompletionEvent<String> event) -> {
            updateThosePane(symField, symPane);
        });

        TextFields.bindAutoCompletion(
                tesField,m.suggestion(MongoDBInc.TEST_LIST))
                .setOnAutoCompleted((AutoCompletionBinding.AutoCompletionEvent<String> event) -> {
            updateThosePane(tesField, tesPane);
        });
        
        TextFields.bindAutoCompletion(
                diaField,m.suggestion(MongoDBInc.DIAGNOSIS_LIST))
                .setOnAutoCompleted((AutoCompletionBinding.AutoCompletionEvent<String> event) -> {
            updateThosePane(diaField, diaPane);
        });
        
        TextFields.bindAutoCompletion(
                preField,m.suggestion(MongoDBInc.PRESCRIPTION_LIST))
                .setOnAutoCompleted((AutoCompletionBinding.AutoCompletionEvent<String> event) -> {
            updateThosePane(preField, prePane);
        });
        
        TextFields.bindAutoCompletion(
                comField,m.suggestion(MongoDBInc.COMMENT_LIST))
                .setOnAutoCompleted((AutoCompletionBinding.AutoCompletionEvent<String> event) -> {
            updateThosePane(comField, comPane);
        });
        
        
        symField.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if(event.getCode()==KeyCode.ENTER){
                newSym.add(symField.getText());
                updateThosePane(symField, symPane);
            }
            deleteComponent(event, symField);
        });
        
        tesField.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                newTes.add(tesField.getText());
                updateThosePane(tesField, tesPane);
            }
            deleteComponent(event, tesField);
        });
        
        diaField.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                newDia.add(diaField.getText());
                updateThosePane(diaField, diaPane);
            }
            deleteComponent(event, diaField);
        });
        
        preField.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                newPre.add(preField.getText());
                updateThosePane(preField, prePane);
            }
            deleteComponent(event, preField);
        });
        
        comField.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                newCom.add(comField.getText());
                updateThosePane(comField, comPane);
            }
            deleteComponent(event, comField);
        });
        
        symVBox.setOnMouseClicked((MouseEvent event) -> {
            symField.requestFocus();
        });
        
        tesVBox.setOnMouseClicked((MouseEvent event) -> {
            tesField.requestFocus();
        });
        
        diaVBox.setOnMouseClicked((MouseEvent event) -> {
            diaField.requestFocus();
        });
        
        preVBox.setOnMouseClicked((MouseEvent event) -> {
            preField.requestFocus();
        });
        
        comVBox.setOnMouseClicked((MouseEvent event) -> {
            comField.requestFocus();
        });
        
    }
    
    private void updateThosePane(TextField t, FlowPane f){
        String te=t.getText();
        if(!te.isEmpty()){
            f.getChildren().add(f.getChildren().size()-1, new Text(te+","));
            t.clear();
        }
    }
    
    private void deleteComponent(KeyEvent event, TextField t){
        if(event.getCode()==KeyCode.BACK_SPACE && t.getText().isEmpty()){
            FlowPane parent =(FlowPane) t.getParent();
                int si = parent.getChildren().size();
                if(si>1){
                    Text tt=((Text)parent.getChildren().get(si-2));
                    parent.getChildren().remove(tt);
                }
        }
    }
    
    private List[] collectDataForSaving(){
        data =new List[5];
        ObservableList<Node> children = symPane.getChildren(),
                children2 = tesPane.getChildren(),
                children3 = diaPane.getChildren(),
                children4 = prePane.getChildren(),
                children5 = comPane.getChildren();
        List sym = new ArrayList(),
                tes = new ArrayList(),
                dia = new ArrayList(),
                pre = new ArrayList(),
                com = new ArrayList();
        for (int i = 0; i < children.size() - 1; i++) {
            sym.add(((Text) children.get(i)).getText());
        }
        for (int i = 0; i < children2.size() - 1; i++) {
            tes.add(((Text) children2.get(i)).getText());
        }
        for (int i = 0; i < children3.size() - 1; i++) {
            dia.add(((Text) children3.get(i)).getText());
        }
        for (int i = 0; i < children4.size() - 1; i++) {
            pre.add(((Text) children4.get(i)).getText());
        }
        for (int i = 0; i < children5.size() - 1; i++) {
            com.add(((Text) children5.get(i)).getText());
        }
        data[0]=sym;
        data[1]=tes;
        data[2]=dia;
        data[3]=pre;
        data[4]=com;
        return data;
    }
    
    /**
     * This clear component and the resources used
     */
    @FXML private void clear(){
        newSym.clear();
        newTes.clear();
        newDia.clear();
        newPre.clear();
        newCom.clear();
        ObservableList<Node> children = symPane.getChildren(),
                children2 = tesPane.getChildren(),
                children3 = diaPane.getChildren(),
                children4 = prePane.getChildren(),
                children5 = comPane.getChildren();
        children.remove(0, children.size()-2);
        children2.remove(0, children2.size()-2);
        children3.remove(0, children3.size()-2);
        children4.remove(0, children4.size()-2);
        children5.remove(0, children5.size()-2);
    }
    
    @FXML 
    private void save(){
        getHome().showProgress();
        m.addPatienceRec(
                patience.getPatienceID(), 
                collectDataForSaving());
        saveNewSuggestion();
        clear();
        getHome().hideProgress();
        //Show NOTIFICATION HERE TO SHow the operationhas beeing perform
    }
    
    
    
    private void queForLater(){
        
    }
    
    private void saveNewSuggestion(){
        
        m.addSuggestionToDb(MongoDBInc.SYMPTOM_LIST,  newSym.toArray());
        m.addSuggestionToDb(MongoDBInc.PRESCRIPTION_LIST, newPre.toArray());
        m.addSuggestionToDb(MongoDBInc.DIAGNOSIS_LIST, newDia.toArray());
        m.addSuggestionToDb(MongoDBInc.TEST_LIST, newTes.toArray());
        m.addSuggestionToDb(MongoDBInc.COMMENT_LIST, newCom.toArray());
    }
    
    public void setApp(Hotels app) {
        this.app = app;
    }

    public FXMLDocumentController getHome() {
        return home;
    }

    public void setHome(FXMLDocumentController home) {
        this.home = home;
    }
    
    public void setPatience(ClientM patience) {
        this.patience = patience;
    }
     
    
    /**
     * The editable mode give room for this component to be added in a mode
     * whereby textfield gain focus by default
     */
    public void setEditable(boolean b){
    
    }
    
    
}