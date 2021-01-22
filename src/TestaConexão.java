import java.sql.Connection;
import java.sql.SQLException;

public class TestaConexão {
	public static void main(String[] args) throws SQLException {
		ConnectionFactory criaConexao = new ConnectionFactory();
		Connection connection = criaConexao.RecuperarConexao();
		System.out.println("Conexão Estabelecida");
		connection.close();
	}
}
