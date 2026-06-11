package boundary;

import control.ProdutoControl;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.beans.binding.Bindings;

class ProdutoBoundary {
    private ProdutoControl control = new ProdutoControl();
    private TextField txtNome = new TextField();
    private TextField txtDescricao = new TextField();
    private TextField txtTamanho = new TextField();
    private TextField txtValor = new TextField();
    private TextField txtQuantidade = new TextField();
    private TextField txtMaterial = new TextField();

    public BorderPane render(Runnable acaoVoltar) { //Precisa do render para navegar entre as telas e chamar na principal
        BorderPane bp = new BorderPane();
        GridPane paneCampos1 = new GridPane();
        GridPane paneCampos2 = new GridPane();
        HBox adicionandoCampos = new HBox();

        //Arrumando o campos 1
        paneCampos1.setPadding(new Insets(20, 20, 0, 20));
        paneCampos1.setHgap(10);
        paneCampos1.setVgap(10);

        //Arrumando o campos 2
        paneCampos2.setPadding(new Insets(20, 20, 0, 20));
        paneCampos2.setHgap(10);
        paneCampos2.setVgap(10);


        //Adicionando labels pane 1
        paneCampos1.add(criarLabelEstilizar("Nome"), 0, 0);
        paneCampos1.add(criarLabelEstilizar("Descrição"), 0, 1);
        paneCampos1.add(criarLabelEstilizar("Tamanho"), 0, 2);

        paneCampos1.add( txtNome, 1, 0);
        paneCampos1.add( txtDescricao, 1, 1);
        paneCampos1.add( txtTamanho, 1, 2);


        //Adicionando labels pane 2
        paneCampos2.add(criarLabelEstilizar("Material"), 0, 0);
        paneCampos2.add(criarLabelEstilizar("Valor"), 0, 1);
        paneCampos2.add(criarLabelEstilizar("Quantidade em estoque"), 0, 2);

        paneCampos2.add( txtMaterial, 1, 0);
        paneCampos2.add( txtValor, 1, 1);
        paneCampos2.add( txtQuantidade, 1, 2);

        Button btnCadastrar = new Button("Cadastrar");
        Button btnVoltar = new Button("Voltar");

        btnCadastrar.getStyleClass().add("action-button");
        btnVoltar.getStyleClass().add("action-button");

        paneCampos2.setHgap(30);
        paneCampos2.add( btnVoltar, 3, 3);
        paneCampos2.add( btnCadastrar, 4, 3);

        adicionandoCampos.getChildren().addAll(paneCampos1, paneCampos2);
        bp.setTop(adicionandoCampos);

        Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
        Bindings.bindBidirectional(txtDescricao.textProperty(), control.descricaoProperty());
        Bindings.bindBidirectional(txtTamanho.textProperty(), control.tamanhoProperty());
        Bindings.bindBidirectional(txtMaterial.textProperty(), control.materialProperty());
        Bindings.bindBidirectional(txtValor.textProperty(), control.valorProperty());
        Bindings.bindBidirectional(txtQuantidade.textProperty(), control.quantEstoqueProperty());

        btnCadastrar.setOnAction(e -> {
            control.salvar();
        });

        btnVoltar.setOnAction(e -> acaoVoltar.run());

        //Vai pra tela principal
        btnVoltar.setOnAction(e -> {
            acaoVoltar.run();
        });

        return bp;
    }

    private Label criarLabelEstilizar(String texto) { // cria no formato do css
        Label novoLabel = new Label(texto);
        novoLabel.setStyle("-fx-font-size: 15px;");
        novoLabel.getStyleClass().add("field-label");
        return novoLabel;
    }
}