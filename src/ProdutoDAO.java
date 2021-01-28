import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class ProdutoDAO {
	private Connection connection;
	
	public ProdutoDAO(Connection connection) {
		this.connection = connection;
	}
	
	public void salvar(produto produto) throws SQLException {
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
}
