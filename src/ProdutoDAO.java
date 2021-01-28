import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class ProdutoDAO {
	private Connection connection;
	
	public ProdutoDAO(Connection connection) {
		this.connection = connection;
	}
	
	public void salvar(Produto produto) throws SQLException {
		String sql = "INSERT INTO PRODUTO(NOME, DESCRICAO)VALUES(?, ?)";
		try(PreparedStatement psmd = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			psmd.setString(1, produto.getNome());
			psmd.setString(2, produto.getDescricao());
			
			psmd.execute();
			
			try (ResultSet rst = psmd.getGeneratedKeys()){
				while(rst.next()) {
					produto.setId(rst.getInt(1));
				}
			}
		}
	}
	
	public List<Produto> listar() throws SQLException{
		List<Produto> produtos = new ArrayList<Produto>();
		
		String sql = "SELECT ID, NOME, DESCRICAO FROM PRODUTO";
		
		try(PreparedStatement psmd = connection.prepareStatement(sql)){
			psmd.execute();
			try(ResultSet rst = psmd.getResultSet()){
				while(rst.next()) {
					//cria o objeto produto que vai receber os o index das colunas onde estão o id, nome e descricao
					Produto produto = 
							new Produto(rst.getInt(1), rst.getString(2), rst.getString(3));
					//adiciona o objeto produto no array produtos fazendo a incrementação6
					produtos.add(produto);
				}
			}
		}
		return produtos;
	}
	
}
