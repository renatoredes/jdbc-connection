package com.cadastro.produto.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cadastro.produto.model.Produto;



public class ProdutoDaoTeste {


	private static Connection connection;
	
	@BeforeClass
	public static void iniciarClasse() throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cadastroproduto" +
				"?useTimezone=true&serverTimezone=UTC", "root", "root");
	}

	@AfterClass
	public static void encerrarClasse() throws SQLException {
		connection.close();
	}
	
	@Test
	public void crud() {
		Produto produto = new Produto(null, "Teclado", new Date());

		// Criando a instância do DAO.
		ProdutoDAO dao = new ProdutoDAO(connection);

		// Fazendo a inserção e recuperando o identificador.
		Integer id = dao.salvar(produto);
		Assert.assertNotNull("Identificador foi retornado como NULL.", id);

		// Atribuindo o identificador retornado ao atributo "id".
		produto.setId(id);

		// Verificando se o registro realmente foi para o banco de dados.
		produto = dao.buscar(produto.getId());
		Assert.assertNotNull("Produto nulo.", produto);

		// Atualizando o registro no banco de dados.
		String nomeAlterado = produto.getNome() + " alterado";
		produto.setNome(nomeAlterado);
		dao.atualizar(produto);

		// Verificando se atualização ocorreu com sucesso.
		produto = dao.buscar(produto.getId());
		Assert.assertEquals("O nome não foi atualizado corretamente.", nomeAlterado, produto.getNome());

		// Removendo o registro.
		dao.deletar(produto.getId());

		// O registro não existe mais. O método "buscar" deve retornar nulo.
		produto = dao.buscar(produto.getId());
		Assert.assertNull("Evento ainda existe e não deveria.", produto);
	
	}
}
