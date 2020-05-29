package com.cadastro.produto.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.cadastro.produto.model.Produto;

public class ProdutoDAO {

    private final Connection connection;

    public ProdutoDAO(Connection connection) {
        this.connection = connection;
    }

    public Integer salvar(Produto produto) {
        String sql = "insert into produto (id, nome, data) values (null, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, produto.getNome());
            preparedStatement.setDate(2, new Date(produto.getData().getTime()));

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();

                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void atualizar(Produto produto) {
        String sql = "update produto set nome = ?, data = ? where id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, produto.getNome());
            preparedStatement.setDate(2, new Date(produto.getData().getTime()));
            preparedStatement.setInt(3, produto.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Produto buscar(Integer id) {
        String sql = "select * from produto where id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(!resultSet.next()) {
                    return null;
                }

                return new Produto(id, resultSet.getString("nome"),
                        resultSet.getDate("data"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Produto> listar() {
        String sql = "select * from produto";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Produto> produtos = new ArrayList<Produto>();

                while(resultSet.next()) {
                    produtos.add(new Produto(resultSet.getInt("id"),
                            resultSet.getString("nome"), resultSet.getDate("data")));
                }

                return produtos;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(Integer id) {
        String sql = "delete from produto where id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
