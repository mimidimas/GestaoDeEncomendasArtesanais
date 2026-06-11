package DAO;

import entity.Pedido;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PedidoImplDAO implements IPedidoDAO {


    @Override
    public void inserir(Pedido pedido) {

    }

    @Override
    public void atualizar(Pedido produto) {

    }

    @Override
    public void remover(int id) {

    }

    @Override
    public Pedido buscarPorId(int id) {
        return null;
    }

    @Override
    public Pedido buscarCliente(String nomeCliente) {
        return null;
    }
}