import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class TestandoRemoção {
	
	public static void main(String[] args) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.RecuperarConexao();
		
		PreparedStatement stm = connection.prepareStatement("DELETE FROM PRODUTO WHERE ID > ?");
		stm.setInt(1, 2);
		stm.execute();
		
		Integer LinhasAlteradas = stm.getUpdateCount();
		System.out.println("A quantidade de linhas alteradas foram: " + LinhasAlteradas);
	}
}
