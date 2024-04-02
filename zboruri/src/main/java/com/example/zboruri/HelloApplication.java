package com.example.zboruri;

import com.example.zboruri.Controller.FirstWindowController;
import com.example.zboruri.Repository.ClientRepository;
import com.example.zboruri.Repository.DBConnection;
import com.example.zboruri.Repository.FlightRepo;
import com.example.zboruri.Repository.TicketRepo;
import com.example.zboruri.Service.ServiceClient;
import com.example.zboruri.Service.ServiceFlight;
import com.example.zboruri.Service.ServiceTickets;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class HelloApplication extends Application {
    Stage primaryStage;

    ServiceTickets serviceTickets;
    ServiceClient serviceClient;
    ServiceFlight serviceFlight;

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {

        DBConnection dbConnection = new DBConnection();

        TicketRepo repoT=new TicketRepo(dbConnection);
        FlightRepo flightRepo=new FlightRepo(dbConnection);
        ClientRepository clientRepository=new ClientRepository(dbConnection);

         serviceTickets=new ServiceTickets(repoT);
         serviceClient=new ServiceClient(clientRepository);
         serviceFlight=new ServiceFlight(flightRepo);
        initView(primaryStage);
        primaryStage.show();
    }
    private void initView(Stage primaryStage) throws IOException{
        FXMLLoader userLoader=new FXMLLoader();
        userLoader.setLocation(HelloApplication.class.getResource("user-input-view.fxml"));
        AnchorPane userLayout=userLoader.load();

        FirstWindowController loginController=userLoader.getController();
        loginController.setService(serviceTickets,serviceClient,serviceFlight,primaryStage);

        Scene scene = new Scene(userLayout);
        scene.getStylesheets().add(getClass().getResource("styles/style.css").toExternalForm());

        primaryStage.setScene(scene);

    }
    public static void main(String[] args)
    {

        launch();
    }
}