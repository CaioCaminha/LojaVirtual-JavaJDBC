import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class TestandoRemoção {
	
	public static void main(String[] args) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		try(Connection connection = factory.RecuperarConexao()){
			try(PreparedStatement stm = connection.prepareStatement("DELETE FROM PRODUTO WHERE ID > ?")){
				stm.setInt(1, 5);
				stm.execute();
		
				Integer LinhasAlteradas = stm.getUpdateCount();
				System.out.println("A quantidade de linhas alteradas foram: " + LinhasAlteradas);
			}
		}
	}
}
