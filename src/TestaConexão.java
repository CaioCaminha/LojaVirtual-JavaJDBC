import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestaConex�o {
	public static void main(String[] args) throws SQLException {
		Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost/loja_virtual?useTimezone=true&serverTimezone=UTC", "root", "caminha123");
		System.out.println("Conex�o Estabelecida");
		connection.close();
	}
}
