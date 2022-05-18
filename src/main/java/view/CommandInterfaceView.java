package view;

import com.mongodb.client.MongoClient;
import configuration.DatabaseConnection;
import configuration.enums.ConnectionEnum;
import configuration.impl.DatabaseConnectionImpl;
import model.Produto;
import service.ProdutoService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class CommandInterfaceView {

    private ProdutoService service;

    private DatabaseConnection databaseConnection;

    public CommandInterfaceView(ProdutoService service, DatabaseConnection databaseConnection) {
        this.service = service;
        this.databaseConnection = databaseConnection;
    }

    public void cadastrar(Scanner scanner) throws SQLException {
        int option;
        showMenuSGBD();
        option = scanner.nextInt();
        if (option == 1) {
            Produto produto = montarProduto(scanner);
            Object conexao = connectHandler(ConnectionEnum.MYSQL);
            service.salvarProduto(produto, (Connection) conexao, null);
            System.out.println("Lista com Novo Produto -- MySQL");
            listagem(ConnectionEnum.MYSQL);
        } else if (option == 2) {
            Produto produto = montarProduto(scanner);
            Object conexao = connectHandler(ConnectionEnum.MONGODB);
            service.salvarProduto(produto,  null, (MongoClient) conexao);
            System.out.println("Lista com Novo Produto -- MONGODB");
            listagem(ConnectionEnum.MONGODB);
        } else {
            System.out.println("Opção inválida.");
        }
    }

    public void listar(Scanner scanner) throws SQLException {
        int option;
        showMenuSGBD();
        option = scanner.nextInt();
        if (option == 1) {
            System.out.println("Lista de Produtos -- MySQL");
            listagem(ConnectionEnum.MYSQL);
        } else if (option == 2) {
            System.out.println("Lista de Produtos -- MONGODB");
            listagem(ConnectionEnum.MONGODB);
        } else {
            System.out.println("Opção inválida.");
        }
    }

    public void alterar(Scanner scanner) throws SQLException {
        int option;
        showMenuSGBD();
        option = scanner.nextInt();
        if (option == 1) {
            listagem(ConnectionEnum.MYSQL);
            System.out.println("Digite o id do produto a ser alterado:");
            String produtoId = scanner.next();
            System.out.println("Digite o novo valor:");
            String valor = scanner.next();

            Object conexao = connectHandler(ConnectionEnum.MYSQL);
            service.alterarValorProduto(produtoId, valor, (Connection) conexao, null);
            System.out.println("Lista com Produto Alterado -- MySQL");
            listagem(ConnectionEnum.MYSQL);
        } else if (option == 2) {
            listagem(ConnectionEnum.MONGODB);
            System.out.println("Digite o id do produto a ser alterado:");
            String produtoId = scanner.next();
            System.out.println("Digite o novo valor:");
            String valor = scanner.next();

            Object conexao = connectHandler(ConnectionEnum.MONGODB);
            service.alterarValorProduto(produtoId, valor,  null, (MongoClient) conexao);
            System.out.println("Lista com Produto Alterado -- MONGODB");
            listagem(ConnectionEnum.MONGODB);
        } else {
            System.out.println("Opção inválida.");
        }
    }

    public void apagar(Scanner scanner) throws SQLException {
        int option;
        showMenuSGBD();
        option = scanner.nextInt();
        if (option == 1) {
            listagem(ConnectionEnum.MYSQL);
            System.out.println("Digite o id do produto a ser apagado:");
            String produtoId = scanner.next();

            Object conexao = connectHandler(ConnectionEnum.MYSQL);
            service.apagarProduto(produtoId, (Connection) conexao, null);
            System.out.println("Lista com Produto Alterado -- MySQL");
            listagem(ConnectionEnum.MYSQL);
        } else if (option == 2) {
            listagem(ConnectionEnum.MONGODB);
            System.out.println("Digite o id do produto a ser apagado:");
            String produtoId = scanner.next();

            Object conexao = connectHandler(ConnectionEnum.MONGODB);
            service.apagarProduto(produtoId,  null, (MongoClient) conexao);
            System.out.println("Lista com Produto Alterado -- MONGODB");
            listagem(ConnectionEnum.MONGODB);
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private void showMenuSGBD() {
        System.out.println("===========================================");
        System.out.println("      | Escolha qual Database quer operar:|");
        System.out.println("      |  1 - MySQL                        |");
        System.out.println("      |  2 - MongoDB                      |");
        System.out.println("===========================================\n");
    }

    private Produto montarProduto(Scanner scanner) {
        System.out.println("Insira o código do produto:");
        scanner.nextLine();
        String codigo = scanner.nextLine();
        System.out.println("Insira o nome do produto:");
        String nome = scanner.nextLine();
        System.out.println("Insira a descrição do produto:");
        String descricao = scanner.nextLine();
        System.out.println("Insira o valor do produto:");
        String valor = scanner.nextLine();
        System.out.println("Insira o estado do produto:");
        String estado = scanner.nextLine();

        return new Produto(codigo, nome, descricao, valor, estado);
    }

    private Object connectHandler(ConnectionEnum database) throws SQLException {
        DatabaseConnectionImpl config = new DatabaseConnectionImpl();
        if (database.equals(ConnectionEnum.MYSQL)) {
            return config.conectarMysql();
        } else {
            return config.conectarMongoDB();
        }
    }

    private void listagem(ConnectionEnum database) throws SQLException {
        if (database.equals(ConnectionEnum.MYSQL)) {
            Object conexao = connectHandler(ConnectionEnum.MYSQL);
            for (Produto produto : service.listarProdutos((Connection) conexao, null)) {
                System.out.println(produto.toString());
            }
        } else {
            Object conexao = connectHandler(ConnectionEnum.MONGODB);
            for (Produto produto : service.listarProdutos(null, (MongoClient) conexao)) {
                System.out.println(produto.toString());
            }
        }
    }

}
