package configuration;

import com.mongodb.client.MongoClient;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnection {

    public Connection conectarMysql() throws SQLException;

    public MongoClient conectarMongoDB();
}
