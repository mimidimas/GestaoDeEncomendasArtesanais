package boundary;

import control.PedidoControl;
import entity.Pedido;
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
import java.time.format.DateTimeFormatter;

class PedidoBoundary {
    private PedidoControl control = new PedidoControl();

    private TextField txtNomeCliente = new TextField();
    private DatePicker dtDataEntrega = new DatePicker();
    private TextField txtStatusProduto = new TextField();
    private TextField txtStatusPagamento = new TextField();
    private TextField txtFormaPagamento = new TextField();
    private TextField txtTotal = new TextField();

    private TableView<Pedido> table = new TableView<>();

    public BorderPane render(Runnable acaoVoltar) {
        BorderPane bp = new BorderPane();
        GridPane paneCampos1 = new GridPane();
        GridPane paneCampos2 = new GridPane();
        HBox adicionandoCampos = new HBox();

        paneCampos1.setPadding(new Insets(20, 20, 0, 20));
        paneCampos1.setHgap(10);
        paneCampos1.setVgap(10);

        paneCampos2.setPadding(new Insets(20, 20, 0, 20));
        paneCampos2.setHgap(10);
        paneCampos2.setVgap(10);

        // Campos Pane 1
        paneCampos1.add(criarLabelEstilizar("Nome Cliente"), 0, 0);
        paneCampos1.add(criarLabelEstilizar("Data de entrega"), 0, 1);
        paneCampos1.add(criarLabelEstilizar("Status do Produto"), 0, 2);

        paneCampos1.add(txtNomeCliente, 1, 0);
        paneCampos1.add(dtDataEntrega, 1, 1);
        paneCampos1.add(txtStatusProduto, 1, 2);

        // Campos Pane 2
        paneCampos2.add(criarLabelEstilizar("Status Pagamento"), 0, 0);
        paneCampos2.add(criarLabelEstilizar("Forma de pagamento"), 0, 1);
        paneCampos2.add(criarLabelEstilizar("Valor total"), 0, 2);

        paneCampos2.add(txtStatusPagamento, 1, 0);
        paneCampos2.add(txtFormaPagamento, 1, 1);
        paneCampos2.add(txtTotal, 1, 2);

        Button btnCadastrar = new Button("Cadastrar");
        Button btnVoltar = new Button("Voltar");

        btnVoltar.getStyleClass().add("action-button");
        btnCadastrar.getStyleClass().add("action-button");

        paneCampos2.add(btnVoltar, 3, 3);
        paneCampos2.add(btnCadastrar, 4, 3);

        adicionandoCampos.getChildren().addAll(paneCampos1, paneCampos2);
        bp.setTop(adicionandoCampos);

        // Bindings (O control precisa ter esses métodos!)
        Bindings.bindBidirectional(txtNomeCliente.textProperty(), control.nomeClienteProperty());
        Bindings.bindBidirectional(dtDataEntrega.valueProperty(), control.dataDeEntregaProperty());
        Bindings.bindBidirectional(txtStatusProduto.textProperty(), control.statusProdutoProperty());
        Bindings.bindBidirectional(txtStatusPagamento.textProperty(), control.statusPagamentoProperty());
        Bindings.bindBidirectional(txtFormaPagamento.textProperty(), control.formaDePagamentoProperty());
        Bindings.bindBidirectional(txtTotal.textProperty(), control.totalProperty()); // Dica: pode precisar de um StringConverter

        btnCadastrar.setOnAction(e -> control.salvar());
        btnVoltar.setOnAction(e -> acaoVoltar.run());

        // Colunas da Tabela
        TableColumn<Pedido, String> colCliente = new TableColumn<>("Cliente");
        colCliente.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getNomeCliente()));

        TableColumn<Pedido, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(
                itemData.getValue().getDataDeEntrega().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        ));

        TableColumn<Pedido, String> colStatusProduto = new TableColumn<>("Status do produto");
        colStatusProduto.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getStatusProduto()));

        TableColumn<Pedido, String> colStatusPagamento = new TableColumn<>("Status do pagamento");
        colStatusPagamento.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getStatusPagamento()));


        TableColumn<Pedido, String> colFormaPagamento = new TableColumn<>("Forma de pagamento");
        colFormaPagamento.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getFormaDePagamento()));


        TableColumn<Pedido, String> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(String.valueOf(itemData.getValue().getTotal())));

        // Botões Ação
        TableColumn<Pedido, Void> colApagar = new TableColumn<>("Apagar");
        Callback<TableColumn<Pedido, Void>, TableCell<Pedido, Void>> callBack = new Callback<>() {
            public TableCell<Pedido, Void> call(TableColumn<Pedido, Void> param) {
                return new TableCell<Pedido, Void>(){
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

        TableColumn<Pedido, Void> colEditar = new TableColumn<>("Editar");
        Callback<TableColumn<Pedido, Void>, TableCell<Pedido, Void>> editCellFactory = new Callback<>() {
            public TableCell<Pedido, Void> call(TableColumn<Pedido, Void> param) {
                return new TableCell<Pedido, Void>() {
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
                            Pedido p = getTableView().getItems().get(getIndex());
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


        table.getColumns().add( colCliente );
        table.getColumns().add( colData );
        table.getColumns().add( colStatusProduto );
        table.getColumns().add( colFormaPagamento );
        table.getColumns().add( colStatusPagamento );
        table.getColumns().add( colTotal );
        table.getColumns().add( colApagar );
        table.getColumns().add( colEditar );
        table.setItems(control.getLista());

        bp.setBottom(table);
        return bp;
    }


    private TableColumn<Pedido, Void> criarColunaEditar() {
        TableColumn<Pedido, Void> col = new TableColumn<>("Editar");
        col.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("✎");
            { btn.setOnAction(e -> control.toBoundary(getTableView().getItems().get(getIndex()))); }
            @Override public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        return col;
    }

    private Label criarLabelEstilizar(String texto) { // cria no formato do css
        Label novoLabel = new Label(texto);
        novoLabel.setStyle("-fx-font-size: 15px;");
        novoLabel.getStyleClass().add("field-label");
        return novoLabel;
    }
}
