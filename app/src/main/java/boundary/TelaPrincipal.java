package boundary;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class TelaPrincipal extends Application{
        @Override
    public void start(Stage stage) {
        //Criação das panes
        BorderPane bp = new BorderPane();
        HBox paneBtn = new HBox();
        VBox paneTxt = new VBox();
        VBox caixaCentral = new VBox(50);

        bp.setCenter(caixaCentral);

        //Organizando as panes
        paneBtn.setAlignment(Pos.CENTER); //Centraliza na pane
        paneTxt.setAlignment(Pos.CENTER);

        paneBtn.setSpacing(25); //Distancia dos conteudos da table
        paneTxt.setSpacing(20);

        //Tamanho da telinha
        Scene scn = new Scene(bp, 1000, 700);

        //Criação de texto
        Label labelInicio = new Label("Bem vindo ao nosso sistema especial para artesãos");
        labelInicio.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        labelInicio.getStyleClass().add("field-label");

        Label labelDescricao = new Label("Esse sistema foi projetado para ajudar artesãos na organização\n " +
                                                "dos seus pedidos e produtos que podem ser encontrados em sua loja:\n " +
                                                "\nO que você gostaria de fazer?");
        labelDescricao.setStyle("-fx-font-size: 15px;");
        labelDescricao.getStyleClass().add("field-label");
        labelDescricao.setTextAlignment(TextAlignment.CENTER);

       //Criação de botões
        Button btnPedido = new Button("Cadastrar Pedido");
        Button btnProduto = new Button("Cadastrar Produto");

        btnPedido.getStyleClass().add("action-button");
        btnProduto.getStyleClass().add("action-button");


        btnProduto.setOnAction(e -> {
            ProdutoBoundary telaProduto = new ProdutoBoundary();

            // voltando pra tela inicial
            bp.setCenter(telaProduto.render(() -> {
                bp.setCenter(caixaCentral); // Ação de voltar: coloca a caixa do menu no centro
            }));
        });

            btnPedido.setOnAction(e -> {
                PedidoBoundary telaPedido = new PedidoBoundary();

                // voltando pra tela inicial
                bp.setCenter(telaPedido.render(() -> {
                    bp.setCenter(caixaCentral); // Ação de voltar: coloca a caixa do menu no centro
                }));
            });


        //Organizando botões
        btnProduto.setPrefSize(250, 50);
        btnPedido.setPrefSize(250, 50);

        //Adicionando os conteudos na telinha
        paneTxt.getChildren().addAll(labelInicio, labelDescricao);
        paneBtn.getChildren().addAll(btnPedido, btnProduto);


        //Criação da pane central

        bp.setCenter(caixaCentral);
        caixaCentral.getChildren().addAll(paneTxt, paneBtn);
        caixaCentral.setAlignment(Pos.CENTER);

        caixaCentral.getStyleClass().add("login-card");
        caixaCentral.setPrefWidth(700);
        caixaCentral.setMaxWidth(700);
        caixaCentral.setAlignment(Pos.CENTER);
        caixaCentral.setPadding(new Insets(50, 40, 50, 40)); // Margens internas do Card (Cima, Direita, Baixo, Esquerda)


        //Iniciando a telinha
        stage.setScene(scn);
        scn.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("Bem-vindo ao sistema de pedidos!");
        launch(args);
    }
}
