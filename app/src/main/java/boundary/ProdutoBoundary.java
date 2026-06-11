package boundary;

import control.ProdutoControl;
import entity.Produto;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.beans.binding.Bindings;
import javafx.util.Callback;


class ProdutoBoundary {
    private ProdutoControl control = new ProdutoControl();
    private TextField txtNome = new TextField();
    private TextField txtDescricao = new TextField();
    private TextField txtTamanho = new TextField();
    private TextField txtValor = new TextField();
    private TextField txtQuantidade = new TextField();
    private TextField txtMaterial = new TextField();

    private TableView<Produto> table = new TableView<>();

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

        //table view
        TableColumn<Produto, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(
                itemData -> new ReadOnlyStringWrapper(itemData.getValue().getNome())
        );

        TableColumn<Produto, String> colDescricao = new TableColumn<>("Descrição");
        colDescricao.setCellValueFactory(
                itemData -> new ReadOnlyStringWrapper(itemData.getValue().getDescricao())
        );
        TableColumn<Produto, String> colMaterial = new TableColumn<>("Material");
        colMaterial.setCellValueFactory(
                itemData -> new ReadOnlyStringWrapper(itemData.getValue().getMaterial())
        );
        TableColumn<Produto, String> colTamanho = new TableColumn<>("Tamanho");
        colTamanho.setCellValueFactory(
                itemData -> new ReadOnlyStringWrapper(itemData.getValue().getTamanho())
        );
        TableColumn<Produto, String> colValor = new TableColumn<>("Valor");
        colValor.setCellValueFactory(
                itemData -> new ReadOnlyStringWrapper(String.valueOf(itemData.getValue().getValor()))
        );
        TableColumn<Produto, String> colQuantEstoque = new TableColumn<>("Quantidade em Estoque");
        colQuantEstoque.setCellValueFactory(
                itemData -> new ReadOnlyStringWrapper(String.valueOf(itemData.getValue().getQuantEstoque()))
        );

        TableColumn<Produto, Void> colApagar = new TableColumn<>("Apagar");
        Callback<TableColumn<Produto, Void>, TableCell<Produto, Void>> callBack = new Callback<>() {
            public TableCell<Produto, Void> call(TableColumn<Produto, Void> param) {
                return new TableCell<Produto, Void>(){
                    private Button btnDelete = new Button("");
                    {
                        Image iconDelete = new Image(getClass().getResourceAsStream("/images/delete.png"));
                        // 2. Wrap it in an ImageView
                        ImageView deleteImageView = new ImageView(iconDelete);
                        // 3. Optional: Resize the image to fit the button
                        deleteImageView.setFitHeight(20);
                        deleteImageView.setFitWidth(20);
                        btnDelete.setGraphic(deleteImageView);

                        btnDelete.setOnAction( e -> control.apagar( getIndex() ));
                    }

                    public void updateItem(Void item, boolean empty) {
                        if (!empty) {
                            setGraphic( btnDelete );
                        } else {
                            setGraphic( null );
                        }
                    }
                };
            }
        };
        colApagar.setCellFactory( callBack );

        TableColumn<Produto, Void> colEditar = new TableColumn<>("Editar");
        Callback<TableColumn<Produto, Void>, TableCell<Produto, Void>> editCellFactory = new Callback<>() {
            public TableCell<Produto, Void> call(TableColumn<Produto, Void> param) {
                return new TableCell<Produto, Void>() {
                    private Button btnEdit = new Button("");
                    {
                        Image iconEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
                        // 2. Wrap it in an ImageView
                        ImageView EditImageView = new ImageView(iconEdit);
                        // 3. Optional: Resize the image to fit the button
                        EditImageView.setFitHeight(20);
                        EditImageView.setFitWidth(20);
                        btnEdit.setGraphic(EditImageView);

                        btnEdit.setOnAction(e -> {
                            Produto p = getTableView().getItems().get(getIndex());
                            control.toBoundary(p); // Isso coloca os dados do produto clicado lá nos campos de texto
                        });
                    }

                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            setGraphic(btnEdit);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        };
        colEditar.setCellFactory(editCellFactory);

        table.setItems( control.getLista() );

        table.getColumns().add( colNome );
        table.getColumns().add( colDescricao );
        table.getColumns().add( colMaterial );
        table.getColumns().add( colTamanho );
        table.getColumns().add( colValor );
        table.getColumns().add( colQuantEstoque );
        table.getColumns().add( colApagar );
        table.getColumns().add( colEditar );

        table.getSelectionModel().selectedItemProperty().addListener(
                (obj, antigo, nova) -> control.toBoundary(nova)
        );

        control.limparCampos();

        bp.setBottom(table);

        return bp;
    }

    private Label criarLabelEstilizar(String texto) { // cria no formato do css
        Label novoLabel = new Label(texto);
        novoLabel.setStyle("-fx-font-size: 15px;");
        novoLabel.getStyleClass().add("field-label");
        return novoLabel;
    }
}