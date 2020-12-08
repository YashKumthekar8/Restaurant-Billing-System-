package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class OrderHistoryController {

    @FXML
    private DatePicker Datepickerfrom;
    @FXML
    private DatePicker Datepickerto;
    @FXML
    private Button Ordersearchbutton;
    @FXML
    private Button Totalsalesbutton;
    @FXML
    private Button Allordersbutton;
    @FXML
    private TextField Orderidtext;
    @FXML
    private TableView<Orders> TableViewtable;
    @FXML
    private TableColumn<Orders,Integer> OrderIDCol;
    @FXML
    private TableColumn<Orders,Double> AmountCol;
    @FXML
    private TableColumn<Orders,Time> TimeCol;
    @FXML
    private TableColumn<Orders,Date> DateCol;
    @FXML
    private Button Backbutton;
    @FXML
    private Label totalsaleslabel;

    public static ObservableList<Orders> OrderhistoryList;

    DataSourceOrderDetails dataSourceOrderDetails = new DataSourceOrderDetails();


    public void initialize()
    {
        dataSourceOrderDetails.open();

        try
        {
            OrderhistoryList = FXCollections.observableArrayList(dataSourceOrderDetails.selectOrdersDetails());

            OrderIDCol.setCellValueFactory(new PropertyValueFactory<>("orderID"));
            AmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
            TimeCol.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
            DateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
            TableViewtable.setItems(OrderhistoryList);
        }catch(NullPointerException ignored)
        {

        }


        dataSourceOrderDetails.close();
    }

    public void displayallorders(ActionEvent event)
    {
        dataSourceOrderDetails.open();

        try
        {
            OrderhistoryList = FXCollections.observableArrayList(dataSourceOrderDetails.selectOrdersDetails());

            OrderIDCol.setCellValueFactory(new PropertyValueFactory<>("orderID"));
            AmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
            TimeCol.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
            DateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
            TableViewtable.setItems(OrderhistoryList);
        }catch(NullPointerException ignored)
        {

        }


        dataSourceOrderDetails.close();
    }

    public void ordersearch(ActionEvent event)
    {
        Date datefrom = Date.valueOf(Datepickerfrom.getValue());
        Date dateto = Date.valueOf(Datepickerto.getValue());

        dataSourceOrderDetails.open();

        try
        {
            OrderhistoryList = FXCollections.observableArrayList(dataSourceOrderDetails.selectOrderDetailsBasedOnDate(datefrom,dateto));

            OrderIDCol.setCellValueFactory(new PropertyValueFactory<>("orderID"));
            AmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
            TimeCol.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
            DateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
            TableViewtable.setItems(OrderhistoryList);
        }catch(NullPointerException ignored)
        {

        }


         dataSourceOrderDetails.close();

    }

    public void orderSearchByOrderID(ActionEvent event){

        dataSourceOrderDetails.open();
        try
        {
            OrderhistoryList = FXCollections.observableArrayList(dataSourceOrderDetails.selectOrderDetailsBasedOnOrderID(Integer.parseInt(Orderidtext.getText())));

            OrderIDCol.setCellValueFactory(new PropertyValueFactory<>("orderID"));
            AmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
            TimeCol.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
            DateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
            TableViewtable.setItems(OrderhistoryList);
        }catch(NullPointerException ignored)
        {

        }
        dataSourceOrderDetails.close();

    }


    public void totalsales(ActionEvent event)
    {
        Date datefrom = Date.valueOf(Datepickerfrom.getValue());
        Date dateto = Date.valueOf(Datepickerto.getValue());

        dataSourceOrderDetails.open();

        double amount = dataSourceOrderDetails.queryTotalSalesOrders(datefrom,dateto);
        String disptotal = "Total Sale is Rs."+amount;

        totalsaleslabel.setText(disptotal);

        dataSourceOrderDetails.close();
    }

    public void controlpage(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;



        if (event.getSource().equals(Backbutton))
        {
            stage = (Stage) Backbutton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Options.fxml"));
        }

        if (root != null) {
            Scene scene = new Scene(root);
            if (stage != null) {
                stage.setScene(scene);
                stage.show();
            }
        } else {
            System.out.println("Just Add Some Details And Click the Button Don't Press the Enter");
        }



    }






}