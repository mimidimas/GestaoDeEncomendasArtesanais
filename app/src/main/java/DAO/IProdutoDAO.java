package DAO;

import entity.Produto;

import java.util.List;

public interface IProdutoDAO {
    void inserir(Produto produto);
    void atualizar(Produto produto);
    void remover(int id);
    Produto buscarPorId(int id);
    List<Produto> buscarTodos();
}