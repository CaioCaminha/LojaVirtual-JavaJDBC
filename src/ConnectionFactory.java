import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Fábrica de conexao
public class ConnectionFactory {
	public Connection RecuperarConexao() throws SQLException {
		return DriverManager
				.getConnection("jdbc:mysql://localhost/loja_virtual?useTimezone=true&serverTimezone=UTC", "root", "caminha123");
	}
}
