package DAO;

import entity.Produto;
import javafx.collections.ObservableList;

import java.util.List;

public interface IProdutoDAO {
    void inserir(Produto produto);
    void atualizar(long id, Produto p);
    void remover(String nome);
    Produto buscarPorId(int id);
    ObservableList<Produto> buscarNome(String nome);
}