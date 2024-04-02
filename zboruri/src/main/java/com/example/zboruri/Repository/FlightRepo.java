package com.example.zboruri.Repository;

import com.example.zboruri.Domain.Flight;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class FlightRepo implements Repository<Long, Flight>{

    private DBConnection dbConnection;

    public FlightRepo(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Optional<Flight> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Flight> findAll() {
        Set<Flight> flights = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(dbConnection.DB_URL,dbConnection.DB_USER, dbConnection.DB_PASSWD);
             PreparedStatement statement = connection.prepareStatement("select * from flights" );//+
                     //" where \"from\"='Lisabona' and \"to\" ='Paris';");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long idf = resultSet.getLong("id_flight");
                String from = resultSet.getString("from");
                String to = resultSet.getString("to");
                LocalDateTime departureTime = resultSet.getTimestamp("departuretime").toLocalDateTime();
                LocalDateTime landingTime = resultSet.getTimestamp("landingtime").toLocalDateTime();
                Integer seats = resultSet.getInt("seats");
                Integer taken = resultSet.getInt("disponibil");

                Flight flight = new Flight(from,to,departureTime,landingTime,seats,taken);
                flight.setId(idf);

                flights.add(flight);
            }
            return flights;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    @Override
    public Optional<Flight> save(Flight entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Flight> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Flight> update(Flight entity) {
        try(Connection connection = DriverManager.getConnection(dbConnection.DB_URL, dbConnection.DB_USER, dbConnection.DB_PASSWD);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE flights SET disponibil = ? WHERE id_flight = ?")
        ){
            preparedStatement.setInt(1, entity.getDisponibil());
            preparedStatement.setLong(2,entity.getId());
            var response = preparedStatement.executeUpdate();
            return response!=0 ? Optional.of(entity) : Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
