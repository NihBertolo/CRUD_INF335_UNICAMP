import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import configuration.DatabaseConnection;
import configuration.impl.DatabaseConnectionImpl;
import model.Produto;
import service.ProdutoService;
import view.CommandInterfaceView;

import java.sql.SQLException;
import java.util.Scanner;

public class main {

    private static final ProdutoService produtoService = new ProdutoService();
    private static final DatabaseConnection databaseConnection = new DatabaseConnectionImpl();
    private static final CommandInterfaceView view = new CommandInterfaceView(produtoService, databaseConnection);

    public static void main(String[] args) throws SQLException {
        int option;
        Scanner keyboard = new Scanner(System.in);

        do {
            menuInterface();
            option = keyboard.nextInt();

            switch (option) {
                case 1:
                    view.listar(keyboard);
                    break;
                case 2:
                    view.cadastrar(keyboard);
                    break;
                case 3:
                    view.alterar(keyboard);
                    break;
                case 4:
                    view.apagar(keyboard);
                    break;
            }
        } while (option != 0);
    }

    private static void menuInterface() {
        System.out.println("===========================================");
        System.out.println("      |       O que deseja fazer? =)      |");
        System.out.println("      |  1 - Exibir lista de produtos     |");
        System.out.println("      |  2 - Cadastrar Novo Produto       |");
        System.out.println("      |  3 - Alterar valor do Produto     |");
        System.out.println("      |  4 - Apagar produto               |");
        System.out.println("      |  0 - Sair (ENCERRA PROGRAMA)      |");
        System.out.println("===========================================\n");
    }
}
