package hotels.views.component.roomStayView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import org.controlsfx.control.spreadsheet.Grid;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

/**
 *
 * @author Olyjosh
 */
public class RoomStayView  extends SpreadsheetView{

       
    private int row;
    private int column;
    private Calendar c;
    private Grid grid;
    private ObservableList<ObservableList<SpreadsheetCell>> rows;

    public RoomStayView(String []rowHeaders ,String []columnHeaders, Grid grid) {
        super(grid);
        rows = FXCollections.observableArrayList();
        this.grid=grid;
        c = Calendar.getInstance();
        this.row = grid.getRowCount();
        this.column = grid.getColumnCount();
        grid.getRowHeaders().addAll(rowHeaders);
        grid.getColumnHeaders().addAll(rowHeaders);
        
    }
    
    private String getdays(Date d) {
        c.setTime(d);
        return new SimpleDateFormat("EE").format(d)+" "+c.get(Calendar.DAY_OF_WEEK);
    }

    
//    private ObservableList<ObservableList<SpreadsheetCell>> defaults(Button stayItem,int columnSpan){
//        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
//        for (int row = 0; row < grid.getRowCount(); ++row) {
//            final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
//            for (int column = 0; column < grid.getColumnCount(); ++column) {
//                    SpreadsheetCellBase cell = new SpreadsheetCellBase(row, column, 1, columnSpan);
//                        cell.setGraphic(stayItem);
//                        list.add(cell);
//            }
//            rows.add(list);
//        }
//        return rows;
//    }

    public void addStayItem(int row, int startColum, int numOfDays, final Button stayItem) {
        final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
        SpreadsheetCellBase cell = new SpreadsheetCellBase(row, column, 1, numOfDays);
        cell.setGraphic(stayItem);
        list.add(cell);
        rows.add(list);
    }


}