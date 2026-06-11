package control;

import DAO.ProdutoImplDAO;
import entity.Produto;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class ProdutoControl {

    // 1. As "cordinhas" que vão se ligar aos campos da tela
    private ObservableList<Produto> lista = FXCollections.observableArrayList();

    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty descricao = new SimpleStringProperty("");
    private StringProperty tamanho = new SimpleStringProperty("");
    private StringProperty material = new SimpleStringProperty("");
    private StringProperty valor = new SimpleStringProperty("0.0");
    private StringProperty quantEstoque = new SimpleStringProperty("0");

    private long idEdicao = 0;

    // 2. Chama o DAO que criamos
    private ProdutoImplDAO dao = new ProdutoImplDAO();

    // 3. Pega o que está nas propriedades e transforma na Entidade Produto
    public Produto toEntity(){
        Produto p = new Produto();

        p.setId(this.idEdicao);
        p.setNome(nome.get());
        p.setDescricao(descricao.get());
        p.setMaterial(material.get());
        p.setTamanho(tamanho.get());

        // Converte o texto da tela para número
        try {
            p.setValor(Float.parseFloat(valor.get()));
            p.setQuantEstoque(Integer.parseInt(quantEstoque.get()));
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Atenção: Digite apenas números no valor e no estoque!").show();
        }
        return p;
    }

    public void toBoundary(Produto p) {
        if (p != null) {
            this.idEdicao = p.getId();
            nome.set(p.getNome());
            descricao.set(p.getDescricao());
            material.set(p.getMaterial());
            tamanho.set(p.getTamanho());
            valor.set(String.valueOf(p.getValor()));
            quantEstoque.set(String.valueOf(p.getQuantEstoque()));
        }
    }

    public void carregar() {
        this.idEdicao = 0;
        lista.clear();
        lista.addAll(
                dao.buscarNome("")
        );
    }

    // 4. A ação de Salvar (que o botão vai chamar)
    public void salvar() {
        Produto p = toEntity();
        this.idEdicao = p.getId();
        if (p.getId() > 0) {
            dao.atualizar( p.getId(), p );
        } else {
            dao.inserir(p);
        }
        limparCampos();
        carregar();
    }

    // 5. Limpa a tela depois de salvar
    public void limparCampos() {
        this.idEdicao = 0;
        nome.set("");
        descricao.set("");
        material.set("");
        tamanho.set("");
        valor.set("0.0");
        quantEstoque.set("0");
    }

    public void pesquisar() {
        lista.clear();
        lista.addAll(
                dao.buscarNome( nome.get() )
        );
    }

    public void apagar( int index ){
        Produto p = lista.get( index );
        dao.remover( p.getNome() );
        carregar();
    }


    public StringProperty nomeProperty() { return nome; }
    public StringProperty descricaoProperty() { return descricao; }
    public StringProperty tamanhoProperty() { return tamanho; }
    public StringProperty materialProperty() { return material; }
    public StringProperty valorProperty() { return valor; }
    public StringProperty quantEstoqueProperty() { return quantEstoque; }

    public ObservableList<Produto> getLista() {
        return lista;
    }
}