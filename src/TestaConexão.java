import java.sql.Connection;
import java.sql.SQLException;

public class TestaConex�o {
	public static void main(String[] args) throws SQLException {
		ConnectionFactory criaConexao = new ConnectionFactory();
		Connection connection = criaConexao.RecuperarConexao();
		System.out.println("Conex�o Estabelecida");
		connection.close();
	}
}
