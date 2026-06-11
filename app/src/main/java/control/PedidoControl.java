package control;

import DAO.PedidoImplDAO; // Lembre-se de criar essa classe DAO seguindo o modelo do Produto
import entity.Pedido;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.time.LocalDate;

public class PedidoControl {

    private ObservableList<Pedido> lista = FXCollections.observableArrayList();

    // Propriedades (Cordinhas da tela)
    private StringProperty nomeCliente = new SimpleStringProperty("");
    private ObjectProperty<LocalDate> dataDeEntrega = new SimpleObjectProperty<>(LocalDate.now());
    private StringProperty statusProduto = new SimpleStringProperty("");
    private StringProperty statusPagamento = new SimpleStringProperty("");
    private StringProperty formaDePagamento = new SimpleStringProperty("");
    private StringProperty total = new SimpleStringProperty("0.0");

    private long idEdicao = 0;

    private PedidoImplDAO dao = new PedidoImplDAO();

    public Pedido toEntity() {
        Pedido p = new Pedido();
         p.setId(this.idEdicao); // Descomente esta linha se adicionar o ID na entidade Pedido

        p.setNomeCliente(nomeCliente.get());
        p.setDataDeEntrega(dataDeEntrega.get());
        p.setStatusProduto(statusProduto.get());
        p.setStatusPagamento(statusPagamento.get());
        p.setFormaDePagamento(formaDePagamento.get());

        try {
            p.setTotal(Float.parseFloat(total.get()));
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Atenção: Digite um valor numérico para o total!").show();
        }
        return p;
    }

    public void toBoundary(Pedido p) {
        if (p != null) {
            this.idEdicao = p.getId(); // Guarde o ID se tiver
            nomeCliente.set(p.getNomeCliente());
            dataDeEntrega.set(p.getDataDeEntrega());
            statusProduto.set(p.getStatusProduto());
            statusPagamento.set(p.getStatusPagamento());
            formaDePagamento.set(p.getFormaDePagamento());
            total.set(String.valueOf(p.getTotal()));
        }
    }

    public void carregar() {
        this.idEdicao = 0;
        lista.clear();
        // Lembre-se de adaptar o método no DAO para buscar pedidos
        lista.addAll(dao.buscarPorCliente(""));
    }

    public void salvar() {
        Pedido p = toEntity();
        // Lógica de ID > 0 para decidir entre inserir ou atualizar
        if (p.getId() > 0) {
            dao.atualizar(p.getId(), p);
            System.out.println("Pedido atualizado!");
        } else {
            // Se o ID é 0, é um pedido novo (Cadastro)
            dao.inserir(p);
            System.out.println("Novo pedido inserido!");
        }
        limparCampos();
        carregar();
    }

    public void limparCampos() {
        this.idEdicao = 0;
        nomeCliente.set("");
        dataDeEntrega.set(LocalDate.now());
        statusProduto.set("");
        statusPagamento.set("");
        formaDePagamento.set("");
        total.set("0.0");
    }

    public void apagar(int index) {
        Pedido p = lista.get(index);
        dao.remover(p.getNomeCliente());
        carregar();
    }

    // Getters para Bindings
    public StringProperty nomeClienteProperty() { return nomeCliente; }
    public ObjectProperty<LocalDate> dataDeEntregaProperty() { return dataDeEntrega; }
    public StringProperty statusProdutoProperty() { return statusProduto; }
    public StringProperty statusPagamentoProperty() { return statusPagamento; }
    public StringProperty formaDePagamentoProperty() { return formaDePagamento; }
    public StringProperty totalProperty() { return total; }

    public ObservableList<Pedido> getLista() { return lista; }
}