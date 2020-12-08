package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class RestaurantMapController {

    @FXML
    private Button back;
    @FXML
    private Button table1;
    @FXML
    private Button table2;
    @FXML
    private Button table3;
    @FXML
    private Button table4;
    @FXML
    private Button table5;
    @FXML
    private Button table6;

    public void Map(ActionEvent event) throws IOException {


        Stage stage = null;
        Parent root = null;

        if (event.getSource().equals(table1))
        {
            stage = (Stage) table1.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Tables.fxml"));
        }
        if (event.getSource().equals(table2))
        {
            stage = (Stage) table1.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Tables.fxml"));
        }
        if (event.getSource().equals(table3))
        {
            stage = (Stage) table1.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Tables.fxml"));
        }
        if (event.getSource().equals(table4))
        {
            stage = (Stage) table1.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Tables.fxml"));
        }
        if (event.getSource().equals(table5))
        {
            stage = (Stage) table1.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Tables.fxml"));
        }
        if (event.getSource().equals(table6))
        {
            stage = (Stage) table1.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Tables.fxml"));
        }
        if (event.getSource().equals(back))
        {
            stage = (Stage) table1.getScene().getWindow();
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
