package hotels.views.component.fxml.admin.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.controlsfx.control.spreadsheet.*;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class Summary implements Initializable {

    @FXML
    private Label expArrival_Booking;
    @FXML
    private Label noOfCancel_Booking;
    @FXML
    private Label noOfVoid_Booking;
    @FXML
    private Label bookednReserverd_Booking;
    @FXML
    private Label expArrival_Reserve;
    @FXML
    private Label noOfCancel_Reserve;
    @FXML
    private Label noOfVoid_Total;
    @FXML
    private Label booknReserved_Total;
    @FXML
    private Label stat_projected;
    @FXML
    private Label stat_roomRevenue;
    @FXML
    private Label stat_extra;
    @FXML
    private Label stat_tax;
    @FXML
    private Label stat_current;
    @FXML
    private Label stat_complimentary;
    @FXML
    private Label stat_expectedCheckOut;
    @FXML
    private Label stat_dayUseRoom;
    @FXML
    private Label stat_pax;
    @FXML
    private Label stat_soldRoom;
    @FXML
    private Label settle_online;
    @FXML
    private Label settle_pos;
    @FXML
    private Label settle_bank;
    @FXML
    private Label settle_paymentOnHold;
    @FXML
    private Label settle_cash;
    @FXML
    private Label settle_void;
    @FXML
    private BarChart<?, ?> occup_barChart;
    @FXML
    private PieChart avail_pieChart;
    @FXML
    private ChoiceBox<?> viewFor;
    @FXML
    private HBox datePickerBox;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
