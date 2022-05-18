package service;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import model.Produto;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.print.Doc;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ProdutoService {

    public void salvarProduto(
            Produto produto,
            Connection connection,
            MongoClient mongoClient)
            throws SQLException {
        if (connection != null) {
            try (connection) {
                Statement stmt = connection.createStatement();

                String query = "INSERT INTO PRODUTOS VALUES ('" +produto.getId() +"','"+ produto.getNome() +"','"+produto.getDescricao() +"','"+produto.getValor() +"','"+produto.getEstado() +"');";
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            MongoDatabase database = mongoClient.getDatabase("INF335");
            Document document = new Document();
            document.append("_id", produto.getId());
            document.append("nome", produto.getNome());
            document.append("descricao", produto.getDescricao());
            document.append("valor", produto.getValor());
            document.append("estado", produto.getEstado());

            try {
                database.getCollection("produtos").insertOne(document);
            } catch (MongoException me) {
                System.err.println("Não foi possível executar o processo: " + me);
            }
        }
    }

    public List<Produto> listarProdutos(Connection connection, MongoClient mongoClient) throws SQLException {
        ArrayList<Produto> produtos = new ArrayList<>();
        if (connection != null) {
            try (connection) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUTOS");

                while (rs.next()) {
                    Produto produto = new Produto(
                            rs.getString("id"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getString("valor"),
                            rs.getString("estado")
                    );
                    produtos.add(produto);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.close();
            }
        } else {
            MongoCursor<Document> cursor = mongoClient
                    .getDatabase("INF335")
                    .getCollection("produtos")
                    .find(new BasicDBObject())
                    .iterator();

            while (cursor.hasNext()) {
                Document document = cursor.next();
                Produto produto = new Produto(
                        document.getString("_id"),
                        document.getString("nome"),
                        document.getString("descricao"),
                        document.getString("valor"),
                        document.getString("estado")
                );

                produtos.add(produto);
            }
         }
        return produtos;
    }

    public void alterarValorProduto(
            String produtoId,
            String valor,
            Connection connection,
            MongoClient mongoClient) {

        if (connection != null) {
            try (connection) {
                Statement stmt = connection.createStatement();

                String query = "UPDATE PRODUTOS SET VALOR = '" + valor +"' WHERE id = '"+ produtoId +"';";
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            MongoDatabase database = mongoClient.getDatabase("INF335");
            BasicDBObject newDocument =
                    new BasicDBObject().append("$set",
                            new BasicDBObject().append("valor", valor));

            try {
                database.getCollection("produtos")
                        .updateOne(
                                new BasicDBObject().append("_id", produtoId),
                                newDocument
                        );
            } catch (MongoWriteException me) {
                System.err.println("Não foi possível executar o processo: " + me);
            }
        }
    }

    public void apagarProduto(
            String produtoId,
            Connection connection,
            MongoClient mongoClient) {

        if (connection != null) {
            try (connection) {
                Statement stmt = connection.createStatement();

                String query = "DELETE FROM PRODUTOS WHERE id = '"+ produtoId +"';";
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            MongoDatabase database = mongoClient.getDatabase("INF335");

            Bson query = eq("_id", produtoId);
            try {
                DeleteResult result = database.getCollection("produtos").deleteOne(query);
                System.out.println("Número de registros apagados: " + result.getDeletedCount());
            } catch (MongoException me) {
                System.err.println("Não foi possível executar o processo: " + me);
            }
        }
    }
}
