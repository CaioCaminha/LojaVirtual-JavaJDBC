
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
	private Connection connection;
	
	public CategoriaDAO(Connection connection) {
		this.connection = connection;
	}
	
	public List<Categoria> listar() throws SQLException{
		List<Categoria> categorias = new ArrayList<>();
		
		System.out.println("Buscando Categorias");
		
		String sql = "SELECT ID, NOME FROM CATEGORIA";
		
		try(PreparedStatement psmd = connection.prepareStatement(sql)){
			psmd.execute();
			
			try(ResultSet rst = psmd.getResultSet()){
				while(rst.next()) {
					Categoria categoria = 
							new Categoria(rst.getInt(1), rst.getString(2));
					categorias.add(categoria);
				}
			}
		}
		return categorias;
	}
	public List<Categoria> listarComProdutos() throws SQLException{
		Categoria ultima = null;
		//instancia o array categorias
		List<Categoria> categorias = new ArrayList<>();
		
		System.out.println("Buscando Categorias");
		//selecionar o id, nome da categoria e id, nome, descricao de produto onde id da categoria é igual ao categoria_id do produto
		String sql = "SELECT C.ID, C.NOME, P.ID, P.NOME, P.DESCRICAO FROM CATEGORIA C INNER JOIN PRODUTO P ON C.ID = P.CATEGORIA_ID";
		
		try(PreparedStatement psmd = connection.prepareStatement(sql)){
			psmd.execute();
			
			try(ResultSet rst = psmd.getResultSet()){
				while(rst.next()) {
					//se ultima for igual a null (nao foi usada) ou o nome da ultima for diferente da String da coluna 2 execute
					if(ultima == null || !ultima.getNome().equals(rst.getString(2))) {
						Categoria categoria = 
								new Categoria(rst.getInt(1), rst.getString(2));
						//atribui a categoria criada a categoria ultima
						ultima = categoria;
						
						categorias.add(categoria);
					}
					//instancia um objeto produto
					Produto produto = 
							new Produto(rst.getInt(3), rst.getString(4), rst.getString(5));
					
					//adiciona o produto a lista de produtos da categoria ultima
					ultima.adicionar(produto);
				}
			}
		}
		return categorias;
	}
}
