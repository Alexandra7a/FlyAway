package com.example.zboruri.Controller;

import com.example.zboruri.AppExceptions.AppException;
import com.example.zboruri.Domain.Client;
import com.example.zboruri.Service.ServiceFlight;
import com.example.zboruri.Service.ServiceTickets;
import com.example.zboruri.AppExceptions.ValidationException;
import com.example.zboruri.HelloApplication;
import com.example.zboruri.Service.ServiceClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstWindowController {

    Stage primaryStage;
    @FXML
    TextField userNameField;
    ServiceTickets serviceTickets;
    ServiceClient serviceClient;
    ServiceFlight serviceFlight;

    public void setService(ServiceTickets serviceTickets, ServiceClient serviceClient, ServiceFlight serviceFlight,Stage primaryStage) {
        this.serviceTickets = serviceTickets;
        this.serviceClient = serviceClient;
        this.serviceFlight = serviceFlight;
        this.primaryStage=primaryStage;


    }


    public void LogInButtonClicked(ActionEvent actionEvent) {
        try {
            if(userNameField.getText().isEmpty())
                throw new ValidationException("Username gol");
            var client=serviceClient.findOne(userNameField.getText());

            if(client.isEmpty())
                throw new ValidationException("Nu exista user");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(HelloApplication.class.getResource("user-account.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            Stage userStage = new Stage();
            userStage.setTitle("LOG AS " + userNameField.getText());

            scene.getStylesheets().add(HelloApplication.class.getResource("styles/style.css").toExternalForm());
            userStage.setScene(scene);
            UserAccountController logInController = fxmlLoader.getController();

            logInController.setService(serviceTickets,serviceClient,serviceFlight,client.get());


            userStage.show();
            userNameField.clear();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("io IO");
        }
        catch (AppException e){
            MessageAlert.showMessage(null, Alert.AlertType.WARNING,"warning",e.getMessage());
        }
    }
}
