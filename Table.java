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
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ConcurrentModificationException;

public class Table {

    @FXML private ComboBox<String> foodItemsComboBox;
    @FXML private TextField qyt;
    @FXML private TextField req;
    @FXML private TextField foodName2;
    @FXML private TextField qyt2;
    @FXML private Label showTotalLable;

    @FXML private Button closeOrderButton;

    @FXML private TableView<AddOrderTable> addOrderTableTableView;
    @FXML private TableColumn<AddOrderTable, String> ItemNameCol;
    @FXML private TableColumn<AddOrderTable,Integer> QuantityCol;
    @FXML private TableColumn<AddOrderTable, Double> PriceCol;
    @FXML private TableColumn<AddOrderTable, String> RequestsCol;
    @FXML private Label orderIDLabel;

    public static ObservableList<AddOrderTable> addOrderItemsList = FXCollections.observableArrayList();
    DataSourceAddOrder dataSourceAddOrder = new DataSourceAddOrder();
    public ObservableList<String> menuItems;

    public void initialize(){
        dataSourceAddOrder.open();
        menuItems = FXCollections.observableArrayList(dataSourceAddOrder.menuAddOrder());
        foodItemsComboBox.setItems(menuItems);
        addOrderTableTableView.setItems(addOrderItemsList);
        int orderID = dataSourceAddOrder.generateOrderID();
        orderIDLabel.setText(String.valueOf(orderID));
        dataSourceAddOrder.close();
    }

    public void showOnTable(ActionEvent actionEvent){
        int orderId = Integer.parseInt(orderIDLabel.getText());
        dataSourceAddOrder.open();

        String requestsField;
        if(req.getText().equals("")){
            requestsField = null;
        }else{
            requestsField = req.getText();
        }
        try{
            AddOrderTable addOrderTable = dataSourceAddOrder.addItem(orderId,
                    foodItemsComboBox.getValue(), Integer.parseInt(qyt.getText()),requestsField);
            addOrderItemsList.add(addOrderTable);
        }catch (NullPointerException ignored){
        }

        ItemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        QuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        RequestsCol.setCellValueFactory(new PropertyValueFactory<>("requests"));
        addOrderTableTableView.setItems(addOrderItemsList);
        dataSourceAddOrder.close();
    }

    public void deleteOnTable(ActionEvent actionEvent){
        int orderId = Integer.parseInt(orderIDLabel.getText());

        dataSourceAddOrder.open();

        try{
            AddOrderTable deleteOrderTable = dataSourceAddOrder.removeItem(orderId,
                    foodName2.getText(),Integer.parseInt(qyt2.getText()));

            for(AddOrderTable item : addOrderItemsList){
                if(item.getItemName().equals(foodName2.getText())){
                    addOrderItemsList.remove(item);
                    if(deleteOrderTable.getQuantity() <= 0){

                    }else{
                        addOrderItemsList.add(deleteOrderTable);
                    }
                }
            }



        } catch (NullPointerException | ConcurrentModificationException ignored) {
        }
        ItemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        QuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        RequestsCol.setCellValueFactory(new PropertyValueFactory<>("requests"));
        addOrderTableTableView.setItems(addOrderItemsList);
        dataSourceAddOrder.close();
    }

    public void closeOrder(ActionEvent event) throws IOException {
        dataSourceAddOrder.open();
        Stage stage = null;
        Parent root = null;


        if(event.getSource().equals(closeOrderButton)){
            stage = (Stage) closeOrderButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("RestaurantMapController.fxml"));
        }

        if(root != null){
            Scene scene = new Scene(root);
            if(stage != null){
                stage.setScene(scene);
                stage.show();
            }


        }
        dataSourceAddOrder.close();
        addOrderTableTableView.setItems(null);
        addOrderItemsList = FXCollections.observableArrayList();
    }

    public void addOrder(ActionEvent event){
        dataSourceAddOrder.open();
        dataSourceAddOrder.addOrder(Integer.parseInt(orderIDLabel.getText()),
                Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()));
        dataSourceAddOrder.close();



    }
    public void cancelOrder(ActionEvent event){
        dataSourceAddOrder.open();
        dataSourceAddOrder.cancelOrder(Integer.parseInt(orderIDLabel.getText()));
        dataSourceAddOrder.close();
    }
    public void calculateTotal(ActionEvent event){

        double total = dataSourceAddOrder.amount;
        showTotalLable.setText(String.valueOf(total));
    }
}