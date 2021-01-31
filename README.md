# LojaVirtual-JavaJDBCAULA 1:





	public class TestaConexão {
	public static void main(String[] args) throws SQLException {
		Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost/loja_virtual?useTimezone=true&serverTimezone=UTC", "root", "caminha123");
		System.out.println("Conexão Estabelecida");
		connection.close();
	}
	}
	Statement stm = con.createStatement();

		stm.execute("SELECT ID, NOME, DESCRICAO FROM PRODUTO");
		
		ResultSet rst = stm.getResultSet();
		
		while(rst.next()) {
			Integer id = rst.getInt("ID");
			System.out.println(id);
			String nome = rst.getString("NOME");
			System.out.println(nome);
			String descricao = rst.getString("DESCRICAO");
			System.out.println(descricao);
 		}

	public class TestaInsercao {
	public static void main(String[] args) throws  SQLException{
		ConnectionFactory factory = new ConnectionFactory();
		//connection extende o AutoClosable, entao ao fechar o try, o connection tambem será fechado
	try(Connection connection = factory.RecuperarConexao()){
		//Me da o controle do commit da transação(de quando será enviado os dados para o BD)
		connection.setAutoCommit(false);
		
		//evita SQL Injections
		
		// preparedStatement extende o AutoClosable, então ao ser fechado o Try, tambem sera fechado o stm
		try(PreparedStatement stm = connection.prepareStatement("INSERT INTO PRODUTO (nome, descricao)VALUES(?, ?)"
			,Statement.RETURN_GENERATED_KEYS);){
			
			adicionarVariavel("Tablet", "9 polegadas", stm);
			adicionarVariavel("Monitor", "Monitor LG", stm);
			
			connection.commit();
			
			
		}catch(Exception e){
			//MOSTRA QUAL A EXCEPTION
			e.printStackTrace();
			System.out.println("ROLLBACK EXECUTADO");
			//método da interface connection, que desfaz todo o código anteriormente executado
			connection.rollback();
		}
	}
	}

	private static void adicionarVariavel(String nome, String descricao, PreparedStatement stm) throws SQLException{
	stm.setString(1, nome);
	stm.setString(2, descricao);
	
	if(nome.equals("mesa")) {
		throw new RuntimeException("Não foi possível adicionar o produto");
	}
	
	stm.execute();
	//ResultSet extende o AutoClosable, entao ao ser fechado o try o rst também será fechado
	try(ResultSet rst = stm.getGeneratedKeys()){
	while(rst.next()) {
		Integer id = rst.getInt(1);
		System.out.println(id);
	}
	}
	}

	}
	//A DAO do modelo produto
	//DAO => Data Access Object
	package br.com.alura.jdbc.dao;

	import java.sql.Connection; 
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.util.ArrayList;
	import java.util.List;

	import br.com.alura.jdbc.modelo.Categoria;
	import br.com.alura.jdbc.modelo.Produto;

	public class ProdutoDAO {

	private Connection connection;

	public ProdutoDAO(Connection connection) {
		this.connection = connection;
	}

	public void salvar(Produto produto){
		try {
			String sql = "INSERT INTO PRODUTO (NOME, DESCRICAO) VALUES (?, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				pstm.setString(1, produto.getNome());
				pstm.setString(2, produto.getDescricao());

				pstm.execute();

				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						produto.setId(rst.getInt(1));
					}
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void salvarComCategoria(Produto produto){
		try {
			String sql = "INSERT INTO PRODUTO (NOME, DESCRICAO, CATEGORIA_ID) VALUES (?, ?, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				pstm.setString(1, produto.getNome());
				pstm.setString(2, produto.getDescricao());
				pstm.setInt(3, produto.getCategoriaId());

				pstm.execute();

				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						produto.setId(rst.getInt(1));
					}
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Produto> listar(){
		try {
			List<Produto> produtos = new ArrayList<Produto>();
			String sql = "SELECT ID, NOME, DESCRICAO FROM PRODUTO";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				trasformarResultSetEmProduto(produtos, pstm);
			}
			return produtos;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Produto> buscar(Categoria ct) throws SQLException {
		List<Produto> produtos = new ArrayList<Produto>();
		String sql = "SELECT ID, NOME, DESCRICAO FROM PRODUTO WHERE CATEGORIA_ID = ?";

		try (PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.setInt(1, ct.getId());
			pstm.execute();

			trasformarResultSetEmProduto(produtos, pstm);
		}
		return produtos;
	}

	public void deletar(Integer id){
		try {
			try (PreparedStatement stm = connection.prepareStatement("DELETE FROM PRODUTO WHERE ID = ?")) {
				stm.setInt(1, id);
				stm.execute();
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void alterar(String nome, String descricao, Integer id) throws SQLException {
		try (PreparedStatement stm = connection
				.prepareStatement("UPDATE PRODUTO P SET P.NOME = ?, P.DESCRICAO = ? WHERE ID = ?")) {
			stm.setString(1, nome);
			stm.setString(2, descricao);
			stm.setInt(3, id);
			stm.execute();
		}
	}

	private void trasformarResultSetEmProduto(List<Produto> produtos, PreparedStatement pstm) throws SQLException {
		try (ResultSet rst = pstm.getResultSet()) {
			while (rst.next()) {
				Produto produto = new Produto(rst.getInt(1), rst.getString(2), rst.getString(3));

				produtos.add(produto);
			}
		}
	}
	}
	//A controller da DAO Produto
	//Controller é responsavel por fazer a conexão entre o front e a DAO, instanciando as connections e deixando o front apenas com as requisiçãos
	package br.com.alura.jdbc.controller;

	import java.util.ArrayList;
	import java.util.List;
	import java.sql.Connection;

	import br.com.alura.jdbc.dao.ProdutoDAO;
	import br.com.alura.jdbc.modelo.Produto;
	import br.com.alura.jdbc.factory.ConnectionFactory;

	public class ProdutoController {
	
	private ProdutoDAO produtoDAO;
	public ProdutoController() {
		Connection connection =
				new ConnectionFactory().recuperarConexao();
		this.produtoDAO = new ProdutoDAO(connection);
	}

	public void deletar(Integer id) {
		this.produtoDAO.deletar(id);
	}

	public void salvar(Produto produto) {
		this.produtoDAO.salvar(produto);
	}

	public List<Produto> listar() {
		return this.produtoDAO.listar();
	}

	public void alterar(String nome, String descricao, Integer id) {
		this.alterar(nome, descricao, id);
	}
	}
Ao se trabalhar com uma connection, é recomendado que se use uma ConnectionFactory, uma classe responsável apenas pela criação da conexão com o Banco de Dados, no caso pode ser usado um driver que conectará a aplicação ao Banco de dados, nesta aplicação foi usado o c3p0, um driver que permite padronizar as configurações do jdbc, como jdbcurl, user, password, dentre outras, e também que permite configurar o pool de conexões, ou seja, o número de conexões estabelecidas com o BD, evitando que muitas conexões sejam abertas ou que um numero muito pequeno seja criado, o driver usa a interface DataSource, que permite fazer a interação entre a aplicação e os drivers de pool, que tem como um de seus metodos o mesmo getConnection(); usado para estabelecer conexão com o BD.
				

	import java.util.ArrayList;
	import java.util.List;
	import java.sql.Connection;

	import br.com.alura.jdbc.dao.ProdutoDAO;
	import br.com.alura.jdbc.modelo.Produto;
	import br.com.alura.jdbc.factory.ConnectionFactory;

	public class ProdutoController {
	
	private ProdutoDAO produtoDAO;
	public ProdutoController() {
		Connection connection =
				new ConnectionFactory().recuperarConexao();
		this.produtoDAO = new ProdutoDAO(connection);
	}

	public void deletar(Integer id) {
		this.produtoDAO.deletar(id);
	}

	public void salvar(Produto produto) {
		this.produtoDAO.salvar(produto);
	}

	public List<Produto> listar() {
		return this.produtoDAO.listar();
	}

	public void alterar(String nome, String descricao, Integer id) {
		this.alterar(nome, descricao, id);
	}
	}
