package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuAdministrationController {
    @FXML
    private Button backButton;
    @FXML
    private TextField foodName;
    @FXML
    private TextField rate;

    @FXML
    private TableView<FoodItem> menuTableView;
    @FXML
    private TableColumn<FoodItem, String> ItemNameCol;
    @FXML
    private TableColumn<FoodItem, Double> PriceCol;

    public static ObservableList<FoodItem> addMenuItemsList = FXCollections.observableArrayList();
    DataSourceMenuManager dataSourceMenuManager = new DataSourceMenuManager();

    public void initialize() {
        menuTableView.setItems(addMenuItemsList);
    }

    public void getBackToOptions(ActionEvent event) {
        dataSourceMenuManager.open();

        Stage stage = null;
        Parent root = null;
        if (event.getSource().equals(backButton)) {

            stage = (Stage) backButton.getScene().getWindow();
            try {
                root = FXMLLoader.load(getClass().getResource("Options.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (root != null) {
                Scene scene = new Scene(root);
                if (stage != null) {
                    stage.setScene(scene);
                    stage.show();
                }

            }

            dataSourceMenuManager.close();
        }


    }

    public void addItem (ActionEvent event){
        dataSourceMenuManager.open();

        try {
            FoodItem foodItem = dataSourceMenuManager.addFoodItemToMenu(foodName.getText(), Double.parseDouble(rate.getText()));

            addMenuItemsList.add(foodItem);

        }catch (NullPointerException ignored){
        }
        ItemNameCol.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("foodPrice"));

        menuTableView.setItems(addMenuItemsList);

        dataSourceMenuManager.close();
    }
    public void removeItem(ActionEvent event){
        dataSourceMenuManager.open();

        try{
            addMenuItemsList = (ObservableList<FoodItem>) dataSourceMenuManager.removeFoodItemFromMenu(addMenuItemsList,foodName.getText());

            ItemNameCol.setCellValueFactory(new PropertyValueFactory<>("foodName"));
            PriceCol.setCellValueFactory(new PropertyValueFactory<>("foodPrice"));

            menuTableView.setItems(addMenuItemsList);

        }catch (NullPointerException ignored){

        }
        dataSourceMenuManager.close();
    }




}
