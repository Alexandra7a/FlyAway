package com.example.zboruri.Controller;

import com.example.zboruri.AppExceptions.AppException;
import com.example.zboruri.AppExceptions.ServiceException;
import com.example.zboruri.AppExceptions.ValidationException;
import com.example.zboruri.Domain.Client;
import com.example.zboruri.Domain.Flight;
import com.example.zboruri.Service.ServiceFlight;
import com.example.zboruri.Service.ServiceTickets;
import com.example.zboruri.Domain.Ticket;
import com.example.zboruri.Service.ServiceClient;
import com.example.zboruri.Utils.Events.Event;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import com.example.zboruri.Utils.Observer.*;

public class UserAccountController implements Observer{

    @FXML
    TableView<Ticket> achizitionate;
    @FXML
    TableColumn<Ticket,String > usernameAchizitionate;
    @FXML
    TableColumn<Ticket,Long > flightIDAchizitionate;
    @FXML
    TableColumn<Ticket, LocalDateTime> dateAchizitionate;
    ObservableList<Ticket> model_achizitionate = FXCollections.observableArrayList();


    @FXML
    TableView<Ticket> bilete24ianuarie;
    @FXML
    TableColumn<Ticket,String > username24ian;
    @FXML
    TableColumn<Ticket,Long > zborID24ian;
    @FXML
    TableColumn<Ticket, LocalDateTime> data24ian;
    ObservableList<Ticket> model_24ian = FXCollections.observableArrayList();


    @FXML
    TableView<Flight> zboruri;
    @FXML
    TableColumn<Flight,String > fromField;
    @FXML
    TableColumn<Flight,String > toField;
    @FXML
    TableColumn<Flight,Integer > seatsField;
    @FXML
    TableColumn<Flight,Integer > disponibilField;
    @FXML
    TableColumn<Flight, LocalDateTime> departureField;
    @FXML
    TableColumn<Flight, LocalDateTime> landingField;
    ObservableList<Flight> model_flight = FXCollections.observableArrayList();
    @FXML
    ComboBox fromCombo;
    @FXML
    ComboBox toCombo;
    @FXML
    DatePicker datePicker;


    ServiceTickets serviceTickets;
    ServiceClient serviceClient;
    ServiceFlight serviceFlight;
    Client currentClient;
    ArrayList<Flight> filteredFlieghts=new ArrayList<>();

    public void setService(ServiceTickets serviceTickets, ServiceClient serviceClient, ServiceFlight serviceFlight, Client currentClient) {
        this.serviceTickets = serviceTickets;
        this.serviceClient = serviceClient;
        this.serviceFlight = serviceFlight;
        this.currentClient = currentClient;
        serviceFlight.addObserver(this); // adaugam acest aca observer al lui flight
        initModel();
    }

    @FXML
    public void initialize() {

        usernameAchizitionate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        flightIDAchizitionate.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getFlightID()).asObject());
        dateAchizitionate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPurchaseTime()));

        username24ian.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        zborID24ian.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getFlightID()).asObject());
        data24ian.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPurchaseTime()));

        fromField.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFrom()));
        toField.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTo()));
        seatsField.setCellValueFactory(new PropertyValueFactory<>("seats"));
        disponibilField.setCellValueFactory(param -> new SimpleObjectProperty<>
                (param.getValue().getSeats()-serviceTickets.takenSeats(param.getValue().getId()))
        );
        departureField.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDepartureTime()));
        landingField.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getLandingTime()));

        // customise rows
        zboruri.setRowFactory(tv -> new TableRow<Flight>() {
            @Override
            protected void updateItem(Flight flight, boolean empty) {
                super.updateItem(flight, empty);
                if (flight == null || empty) {
                    setStyle("");
                } else {
                    // Check if the disponibilField value is 0
                    if (flight.getSeats() - serviceTickets.takenSeats(flight.getId()) == 0) {
                        setStyle("-fx-background-color: #d09292;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

    }

    public void initModel()
    {
        // initializare combo box

        List<String> fromList=StreamSupport.stream(serviceFlight.findAll().spliterator(),false)
                .map(flight -> flight.getFrom())
                .distinct()
                .collect(Collectors.toList());
        ObservableList<String> fromObs=FXCollections.observableArrayList(fromList);
        fromCombo.setItems(fromObs);


        List<String> toList=StreamSupport.stream(serviceFlight.findAll().spliterator(),false)
                .map(flight -> flight.getTo())
                .distinct()
                .collect(Collectors.toList());
        ObservableList<String> toObs=FXCollections.observableArrayList(toList);
        toCombo.setItems(toObs);


        //
        List<Ticket> currentUserTickets = StreamSupport.stream(serviceTickets.findAll().spliterator(), false)
                .filter(ticket -> ticket.getUsername().equals(currentClient.getUsernaem()))
                .collect(Collectors.toList());

        ObservableList<Ticket> currentUserTicketsObservableList = FXCollections.observableArrayList(currentUserTickets);
        achizitionate.setItems(currentUserTicketsObservableList);


        List<Ticket> all24ian=StreamSupport.stream(serviceTickets.findAll().spliterator(),false)
                .filter(ticket -> ticket.getPurchaseTime().getMonthValue()==1 && ticket.getPurchaseTime().getYear()==2024 )
                .collect(Collectors.toList());
        ObservableList<Ticket> all24obs = FXCollections.observableArrayList(all24ian);
        bilete24ianuarie.setItems(all24obs);

        List<Flight> flights=StreamSupport.stream(serviceFlight.findAll().spliterator(),false)
                .toList();

        model_flight.clear();
        model_flight.addAll(flights);
        zboruri.setItems(model_flight);



    }
    private String from,to;
    private LocalDate date;

    public void onSearchButtonClicked(ActionEvent actionEvent) {

        try {
            if (toCombo.getValue() == null || toCombo.getValue() == null)
                throw new ValidationException("slectie goala");
            from = fromCombo.getValue().toString();
            to = toCombo.getValue().toString();
            date = datePicker.getValue();
            List<Flight> flights=StreamSupport.stream(serviceFlight.findAll().spliterator(),false)
                    .filter(flight -> flight.getFrom().equals(from) && flight.getTo().equals(to) &&
                            flight.getDepartureTime().getYear()==date.getYear()
                            && flight.getDepartureTime().getMonthValue()==date.getMonthValue()
                            && flight.getDepartureTime().getDayOfYear()==date.getDayOfYear())
                    .toList();
            initFlights(flights);
        }
        catch (AppException e)
        {
            MessageAlert.showMessage(null, Alert.AlertType.WARNING,"warning",e.getMessage());
        }
    }
    public void clearFilterButtonClicked(ActionEvent actionEvent) {
        toCombo.getItems().clear();
        fromCombo.getItems().clear();
        datePicker.setValue(null);
        List<Flight> flights=StreamSupport.stream(serviceFlight.findAll().spliterator(),false)
                .toList();
        initFlights(flights);
    }
    public void initFlights(List<Flight> flights)
    {
            try
            {
                if(flights.isEmpty()) throw new ValidationException("Nu exista zboruri");
                filteredFlieghts.addAll(flights); // punem in lista asta
                model_flight.clear();
                model_flight.addAll(flights);
                zboruri.setItems(model_flight);
            }
        catch (AppException e)
        {
            filteredFlieghts.clear();
            MessageAlert.showMessage(null, Alert.AlertType.WARNING,"warning",e.getMessage());
        }

    }

    public void onBuyButtonClicked(ActionEvent actionEvent) {

        try
        {
           Flight selection= zboruri.getSelectionModel().getSelectedItem();
           if(selection == null)
               throw new ValidationException("nimic selectat");

           int taken=serviceTickets.takenSeats(selection.getId());
            if(taken==selection.getSeats()) throw new ValidationException("Nu sunt locuri");

            serviceTickets.save(currentClient.getUsernaem(),selection.getId(),LocalDateTime.now());
            selection.setDisponibil(selection.getSeats()-taken);
            serviceFlight.update(selection);
        }
        catch (AppException e)
        {
            MessageAlert.showMessage(null, Alert.AlertType.WARNING,"warning",e.getMessage());
        }
    }
    @Override
    public void update(Event eventUpdate) {
        //update personal
       // serviceFlight.update(serviceFlight.findOne());
        initModel();

        //initFlights(fl);
    }


}
