package com.example.zboruri.Repository;

import com.example.zboruri.Domain.Ticket;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TicketRepo implements Repository<Long, Ticket>{

    private DBConnection dbConnection;

    public TicketRepo(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Optional<Ticket> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Ticket> findAll() {
        Set<Ticket> tickets = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(dbConnection.DB_URL,dbConnection.DB_USER, dbConnection.DB_PASSWD))
        {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tickets");
                ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long tiketID=resultSet.getLong("id_ticket");
                String  username = resultSet.getString("username");
                Long flightid = resultSet.getLong("id_flight");
                LocalDateTime purchaseTime = resultSet.getTimestamp("purchaseTime").toLocalDateTime();

                Ticket ticket=new Ticket(username,purchaseTime,flightid);
                ticket.setId(tiketID);
                tickets.add(ticket);
            }
            return tickets;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;

    }

    @Override
    public Optional<Ticket> save(Ticket entity) {
        try(Connection connection = DriverManager.getConnection(dbConnection.DB_URL, dbConnection.DB_USER, dbConnection.DB_PASSWD);
            PreparedStatement statement = connection.prepareStatement("insert into tickets(username,id_flight,purchasetime) values (?,?,?)")
        )
            {
                statement.setString(1,entity.getUsername());
            statement.setLong(2, entity.getFlightID());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getPurchaseTime()));
            var responseSQL = statement.executeUpdate();
                return responseSQL==0 ? Optional.of(entity) : Optional.empty();
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Ticket> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Ticket> update(Ticket entity) {
        return Optional.empty();
    }

    @Override
    public int size() {
        return 0;
    }
}
