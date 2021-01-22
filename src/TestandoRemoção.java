
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

public class TestandoRemoção {
	public static void main(String[] args) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.RecuperarConexao();
		
		Statement stm = connection.createStatement();
		stm.execute("DELETE FROM PRODUTO WHERE ID > 2");
		
		Integer LinhasAlteradas = stm.getUpdateCount();
		System.out.println("A quantidade de linhas alteradas foram: " + LinhasAlteradas);
	}
}
