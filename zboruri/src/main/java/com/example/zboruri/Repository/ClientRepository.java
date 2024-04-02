package com.example.zboruri.Repository;

import com.example.zboruri.Domain.Client;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class ClientRepository implements Repository<String, Client>{

    private DBConnection dbConnection;

    public ClientRepository(DBConnection connection) {
        this.dbConnection = connection;
    }


    @Override
    public Optional<Client> findOne(String s) {
        try(Connection connection = DriverManager.getConnection(dbConnection.DB_URL, dbConnection.DB_USER, dbConnection.DB_PASSWD);
            PreparedStatement statement = connection.prepareStatement("select * from clienti " +
                    "where username = ?");

        ) {
            statement.setString(1, s);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                String user = resultSet.getString("username");
                String nume = resultSet.getString("nume");
                Client u = new Client(user,nume);
                u.setId(user);
                return Optional.ofNullable(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Client> findAll() {
        return null;
    }

    @Override
    public Optional<Client> save(Client entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Client> delete(String s) {
        return Optional.empty();
    }

    @Override
    public Optional<Client> update(Client entity) {
        return Optional.empty();
    }

    @Override
    public int size() {
        return 0;
    }
}
