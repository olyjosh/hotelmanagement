package hotels.views.component;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Joshua Aroke (olyjosh)
 * olyjoshone@gmail.com
 * f12softwares.com
 */
public class WindowTop {
    
    private double mouseDragOffsetX = 0;
    private double mouseDragOffsetY = 0;

    public WindowTop(final Stage stage,final ToolBar toolBar,boolean hasSearch,boolean isDialog,Region ...spacer) {
        for (int i = 0; i < spacer.length; i++) {
            HBox.setHgrow(spacer[i], Priority.ALWAYS);
        }
        final WindowButtons windowButtons = new WindowButtons(stage, isDialog);
        HBox h = new HBox(100, new Region(), windowButtons);
        if (hasSearch) {
            SearchBox searchBox = new SearchBox();
            HBox.setMargin(searchBox, new Insets(0, 5, 0, 0));
            toolBar.getItems().add(new VBox(20, h, searchBox));
        }else{
            toolBar.getItems().add(h);
        }
   
        // add window header double clicking
        toolBar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //stage = (Stage) toolBar.getParent().getScene().getWindow();
                if (event.getClickCount() == 2) {
                    windowButtons.toogleMaximized();
                }
            }
        });
        // add window dragging
        toolBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseDragOffsetX = event.getSceneX();
                mouseDragOffsetY = event.getSceneY();
            }
        });
        toolBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!windowButtons.isMaximized()) {
                    stage.setX(event.getScreenX() - mouseDragOffsetX);
                    stage.setY(event.getScreenY() - mouseDragOffsetY);
                }
            }
        });
    }
    
    
}
