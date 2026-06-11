package DAO;

import entity.Pedido;

public interface IPedidoDAO {
    void inserir(Pedido pedido);
    void atualizar(Pedido produto);
    void remover(int id);
    Pedido buscarPorId(int id);
    Pedido buscarCliente(String nomeCliente);
}
