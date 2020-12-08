package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalTime;

public class Options {
    @FXML private Label timeOptions;
    @FXML private Button addOrderButton;
    @FXML private Button orderHistoryButton;
    @FXML private Button menuManagerButton;
    @FXML private Button logOutButton;
    public void initialize(){
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            timeOptions.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void options(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;
        if(event.getSource().equals(addOrderButton)){
            stage = (Stage) addOrderButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("RestaurantMapController.fxml"));

        }else if(event.getSource().equals(orderHistoryButton)){
            stage = (Stage) orderHistoryButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("OrderHistoryController.fxml"));

        }else if(event.getSource().equals(menuManagerButton)){
            stage = (Stage) orderHistoryButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("MenuAdministrationController.fxml"));

        }else if(event.getSource().equals(logOutButton)){
            stage = (Stage) orderHistoryButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        }

        if(root != null){
            Scene scene = new Scene(root);
            if(stage != null){
                stage.setScene(scene);
                stage.show();
            }
        }else{
            System.out.println("Just Add Some Details And Click the Button Don't Press the Enter");
        }
    }

}







