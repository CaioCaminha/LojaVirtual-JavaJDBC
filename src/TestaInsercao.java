import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaInsercao {
	public static void main(String[] args) throws  SQLException{
		String Nome = "";
		String Descricao = "";
		
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.RecuperarConexao();
		
		PreparedStatement stm = connection.prepareStatement("INSERT INTO PRODUTO (nome, descricao)VALUES(?, ?)"
				,Statement.RETURN_GENERATED_KEYS);
		stm.setString(1, Nome);
		stm.setString(2, Descricao);
		
		stm.execute();
		
		ResultSet rst = stm.getGeneratedKeys();
		while(rst.next()) {
			Integer id = rst.getInt(1);
			System.out.println(id);
		}
	}
}
