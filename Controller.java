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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Controller {
    @FXML private TextField userNameField;
    @FXML private PasswordField passwordField;
    @FXML private Button LogInButton;
    @FXML private Label status;
    @FXML private Label date;
    @FXML private Label time;
    public static final String userName = "Manager";
    public static final String password = "healthyfood";

    public void initialize(){
        LocalDate dateNOw = LocalDate.now();
        date.setText(String.valueOf(dateNOw));

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            time.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
                );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

    public void login(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        if (event.getSource().equals(LogInButton)) {
            if(userNameField.getText().equals(userName) && passwordField.getText().equals(password)){
                status.setText("Login Successful");
                stage = (Stage) LogInButton.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("Options.fxml"));
            }else{
                status.setText("Oops Wrong UserName or Password!!!");
            }
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
