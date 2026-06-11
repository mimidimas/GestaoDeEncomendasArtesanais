package DAO;

import entity.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProdutoImplDAO implements IProdutoDAO {

    // 1. DADOS DE CONEXÃO DIRETO NO DAO (Padrão do Professor)
    private static final String DB_JDBC_URI = "jdbc:mariadb://localhost:3306/gestao_artesanato";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "123456";
    private Connection con;

    // 2. CONSTRUTOR COM TRY-CATCH (Idêntico ao PetDAOImpl)
    public ProdutoImplDAO() {
        System.out.println("Produto DAO criado - com database");
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("Classe carregada...");
            con = DriverManager.getConnection(DB_JDBC_URI, DB_USER, DB_PASS);
            System.out.println("Conexao foi feita com sucesso");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao carregar a classe");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar");
            e.printStackTrace();
        }
    }

    @Override
    public void inserir(Produto produto) {
        try {
            // 1. Tenta criar a tabela automaticamente se ela não existir
            String sqlCriarTabela = "CREATE TABLE IF NOT EXISTS produto (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nome VARCHAR(100), " +
                    "descricao VARCHAR(255), " +
                    "material VARCHAR(100), " +
                    "tamanho VARCHAR(50), " +
                    "valor FLOAT, " +
                    "quant_estoque INT)";

            java.sql.Statement stm = con.createStatement();
            stm.execute(sqlCriarTabela);
            System.out.println(">>> Tabela 'produto' verificada/criada com sucesso!");

        } catch (java.sql.SQLException e) {
            System.out.println("Erro ao criar tabela automaticamente: " + e.getMessage());
        }
        try {
            java.sql.Statement stm = con.createStatement();
            java.sql.ResultSet rs = stm.executeQuery("SHOW TABLES");
            System.out.println(">>> Tabelas que o Java está vendo neste banco:");
            while (rs.next()) {
                System.out.println("Tabela encontrada: " + rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String sql = "INSERT INTO produto (nome, descricao, material, tamanho, valor, quant_estoque) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(sql);

            stm.setString(1, produto.getNome());
            stm.setString(2, produto.getDescricao());
            stm.setString(3, produto.getMaterial());
            stm.setString(4, produto.getTamanho());
            stm.setFloat(5, produto.getValor());
            stm.setInt(6, produto.getQuantEstoque());

            stm.executeUpdate();
            System.out.println("Comando executado com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar/inserir");
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(long id, Produto p) {
        try {
            String sql = "UPDATE produto SET nome = ?, descricao = ?, material = ?, tamanho = ?, valor = ?, quant_estoque = ? WHERE id = ?";
            PreparedStatement stm = con.prepareStatement(sql);

            stm.setString(1, p.getNome());
            stm.setString(2, p.getDescricao());
            stm.setString(3, p.getMaterial());
            stm.setString(4, p.getTamanho());
            stm.setFloat(5, p.getValor());
            stm.setInt(6, p.getQuantEstoque());
            stm.setLong(7, id); // O ID vai por último

            stm.executeUpdate();
            System.out.println("Produto atualizado com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar no banco");
            e.printStackTrace();
        }
    }

    @Override
    public void remover(String nome) {
        try {
            String sql = "DELETE FROM produto WHERE nome = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, nome);
            stm.executeUpdate();
            System.out.println("Produto apagado com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar/apagar");
            e.printStackTrace();
        }
    }

    @Override
    public Produto buscarPorId(int id) {
        Produto p = null;
        try {
            String sql = "SELECT * FROM produto WHERE id = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                p = new Produto();
                p.setId(rs.getLong("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setMaterial(rs.getString("material"));
                p.setTamanho(rs.getString("tamanho"));
                p.setValor(rs.getFloat("valor"));
                p.setQuantEstoque(rs.getInt("quant_estoque"));
            }
            System.out.println("Comando executado com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar/buscar");
            e.printStackTrace();
        }
        return p;
    }

    @Override
    public ObservableList<Produto> buscarNome(String nome) {
        // A forma correta de inicializar:
        ObservableList<Produto> lista = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM produto WHERE nome LIKE ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + nome + "%");

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getLong("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setMaterial(rs.getString("material"));
                p.setTamanho(rs.getString("tamanho"));
                p.setValor(rs.getFloat("valor"));
                p.setQuantEstoque(rs.getInt("quant_estoque"));
                lista.add(p);
            }
            System.out.println("Comando executado com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar/buscar");
            e.printStackTrace();
        }
        return lista;
    }
}