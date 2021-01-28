import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaInsercao {
	public static void main(String[] args) throws  SQLException{
		ConnectionFactory factory = new ConnectionFactory();
		
	try(Connection connection = factory.RecuperarConexao()){
		connection.setAutoCommit(false);
		
		try(PreparedStatement stm = connection.prepareStatement("INSERT INTO PRODUTO (nome, descricao)VALUES(?, ?)"
			,Statement.RETURN_GENERATED_KEYS);){
			
			adicionarVariavel("Tablet", "9 polegadas", stm);
			adicionarVariavel("Monitor", "Monitor LG", stm);
			connection.commit();
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("ROLLBACK EXECUTADO");
			connection.rollback();
		}
	}
}

private static void adicionarVariavel(String nome, String descricao, PreparedStatement stm) throws SQLException{
	stm.setString(1, nome);
	stm.setString(2, descricao);
	
	if(nome.equals("mesa")) {
		throw new RuntimeException("Não foi possível adicionar o produto");
	}
	stm.execute();
	
	try(ResultSet rst = stm.getGeneratedKeys()){
			
		while(rst.next()) {
			Integer id = rst.getInt(1);
			System.out.println(id);
		}
		
	}
}

}
