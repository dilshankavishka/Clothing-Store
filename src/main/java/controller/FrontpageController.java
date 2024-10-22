package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class FrontpageController implements Initializable {

    @FXML
    private JFXButton btnAdmin;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private Label txtDate;

    @FXML
    private Label txtTime;

    @FXML
    void btnAdminOnAction(ActionEvent event) {
        // Load the new FXML
        Parent newWindowRoot = null;
        try {
            newWindowRoot = FXMLLoader.load(getClass().getResource("/view/admin_login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a new stage (window)
        Stage newStage = new Stage();
        newStage.setTitle("Admin Login");
        newStage.setScene(new Scene(newWindowRoot));
        newStage.show();

        // Close the current stage (window)
        // Get the current stage by getting the button's scene and window
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {

        // Load the new FXML
        Parent newWindowRoot = null;
        try {
            newWindowRoot = FXMLLoader.load(getClass().getResource("/view/employee_login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a new stage (window)
        Stage newStage = new Stage();
        newStage.setTitle("Employee Login");
        newStage.setScene(new Scene(newWindowRoot));
        newStage.show();

        // Close the current stage (window)
        // Get the current stage by getting the button's scene and window
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
    }
    private void loadDateAndTime(){
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = f.format(date);

        txtDate.setText(dateNow);

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime localTime = LocalTime.now();
            txtTime.setText(localTime.getHour() + " : " + localTime.getMinute() + " : " + localTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
