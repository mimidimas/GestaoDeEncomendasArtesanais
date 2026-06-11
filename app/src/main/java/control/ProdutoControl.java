package control;

import DAO.ProdutoImplDAO;
import entity.Produto;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProdutoControl {

    // 1. As "cordinhas" que vão se ligar aos campos da tela
    private ObservableList<Produto> lista = FXCollections.observableArrayList();

    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty descricao = new SimpleStringProperty("");
    private StringProperty material = new SimpleStringProperty("");
    private StringProperty tamanho = new SimpleStringProperty("");
    private StringProperty valor = new SimpleStringProperty("0.0");
    private StringProperty quantEstoque = new SimpleStringProperty("0");

    // 2. Chama o DAO que criamos
    private ProdutoImplDAO dao = new ProdutoImplDAO();

    // 3. Pega o que está nas propriedades e transforma na Entidade Produto
    public Produto toEntity() {
        Produto p = new Produto();
        p.setNome(nome.get());
        p.setDescricao(descricao.get());
        p.setMaterial(material.get());
        p.setTamanho(tamanho.get());

        // Converte o texto da tela para número
        try {
            p.setValor(Float.parseFloat(valor.get()));
            p.setQuantEstoque(Integer.parseInt(quantEstoque.get()));
        } catch (NumberFormatException e) {
            System.out.println("Atenção: Digite apenas números no valor e no estoque!");
        }
        return p;
    }

    // 4. A ação de Salvar (que o botão vai chamar)
    public void salvar() {
        Produto p = toEntity();
        dao.inserir(p);
        limparCampos();
        System.out.println("Produto salvo pelo Control!");
    }

    // 5. Limpa a tela depois de salvar
    public void limparCampos() {
        nome.set("");
        descricao.set("");
        material.set("");
        tamanho.set("");
        valor.set("0.0");
        quantEstoque.set("0");
    }

    public void carregarLista() {
        lista.clear();
        lista.addAll(dao.listar());
    }

    // --- Getters das Propriedades (O JavaFX precisa disso para conectar a tela) ---
    public StringProperty nomeProperty() { return nome; }
    public StringProperty descricaoProperty() { return descricao; }
    public StringProperty materialProperty() { return material; }
    public StringProperty tamanhoProperty() { return tamanho; }
    public StringProperty valorProperty() { return valor; }
    public StringProperty quantEstoqueProperty() { return quantEstoque; }
}