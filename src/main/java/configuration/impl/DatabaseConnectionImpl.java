package configuration.impl;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import configuration.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionImpl implements DatabaseConnection {

    @Override
    public Connection conectarMysql() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/INF335", "root", "UNICAMP2022");
    }

    @Override
    public MongoClient conectarMongoDB() {
        String uri = "mongodb://root:UNICAMP2022@localhost:27017/?maxPoolSize=20&w=majority";
        return MongoClients.create(uri);
    }
}
