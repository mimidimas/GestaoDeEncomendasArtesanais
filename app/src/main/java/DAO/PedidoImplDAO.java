package DAO;

import entity.Pedido;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDate;

    public class PedidoImplDAO {

        private static final String DB_JDBC_URI = "jdbc:mariadb://localhost:3306/gestao_artesanato";
        private static final String DB_USER = "root";
        private static final String DB_PASS = "123456";
        private Connection con;

        public PedidoImplDAO() {
            try {
                con = DriverManager.getConnection(DB_JDBC_URI, DB_USER, DB_PASS);
                criarTabela();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void criarTabela() {
            try {
                String sql = "CREATE TABLE IF NOT EXISTS pedido (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "nome_cliente VARCHAR(100), " +
                        "data_entrega DATE, " +
                        "status_produto VARCHAR(50), " +
                        "status_pagamento VARCHAR(50), " +
                        "forma_pagamento VARCHAR(50), " +
                        "total FLOAT)";
                con.createStatement().execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void inserir(Pedido pedido) {
            String sql = "INSERT INTO pedido (nome_cliente, data_entrega, status_produto, status_pagamento, forma_pagamento, total) VALUES (?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, pedido.getNomeCliente());
                stm.setDate(2, Date.valueOf(pedido.getDataDeEntrega())); // Convertendo LocalDate para SQL Date
                stm.setString(3, pedido.getStatusProduto());
                stm.setString(4, pedido.getStatusPagamento());
                stm.setString(5, pedido.getFormaDePagamento());
                stm.setFloat(6, pedido.getTotal());

                stm.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void atualizar(long id, Pedido p) {
            String sql = "UPDATE pedido SET nome_cliente = ?, data_entrega = ?, status_produto = ?, status_pagamento = ?, forma_pagamento = ?, total = ? WHERE id = ?";
            try {
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, p.getNomeCliente());
                stm.setDate(2, Date.valueOf(p.getDataDeEntrega()));
                stm.setString(3, p.getStatusProduto());
                stm.setString(4, p.getStatusPagamento());
                stm.setString(5, p.getFormaDePagamento());
                stm.setFloat(6, p.getTotal());
                stm.setLong(7, id);

                stm.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void remover(String nomeCliente) {
            String sql = "DELETE FROM pedido WHERE nome_cliente = ?";
            try {
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, nomeCliente);
                stm.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public ObservableList<Pedido> buscarPorCliente(String nome) {
            ObservableList<Pedido> lista = FXCollections.observableArrayList();
            String sql = "SELECT * FROM pedido WHERE nome_cliente LIKE ?";
            try {
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, "%" + nome + "%");
                ResultSet rs = stm.executeQuery();

                while (rs.next()) {
                    Pedido p = new Pedido();
                    // Assumindo que você adicione um setId na sua entidade Pedido
                    p.setId(rs.getLong("id"));
                    p.setNomeCliente(rs.getString("nome_cliente"));
                    p.setDataDeEntrega(rs.getDate("data_entrega").toLocalDate());
                    p.setStatusProduto(rs.getString("status_produto"));
                    p.setStatusPagamento(rs.getString("status_pagamento"));
                    p.setFormaDePagamento(rs.getString("forma_pagamento"));
                    p.setTotal(rs.getFloat("total"));
                    lista.add(p);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return lista;
        }
    }