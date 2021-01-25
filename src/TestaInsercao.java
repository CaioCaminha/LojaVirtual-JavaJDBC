import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaInsercao {
	public static void main(String[] args) throws  SQLException{
		ConnectionFactory factory = new ConnectionFactory();
		//connection extende o AutoClosable, entao ao fechar o try, o connection tambem ser� fechado
	try(Connection connection = factory.RecuperarConexao()){
		//Me da o controle do commit da transa��o(de quando ser� enviado os dados para o BD)
		connection.setAutoCommit(false);
		
		//evita SQL Injections
		
		// preparedStatement extende o AutoClosable, ent�o ao ser fechado o Try, tambem sera fechado o stm
		try(PreparedStatement stm = connection.prepareStatement("INSERT INTO PRODUTO (nome, descricao)VALUES(?, ?)"
			,Statement.RETURN_GENERATED_KEYS);){
			
			adicionarVariavel("Tablet", "9 polegadas", stm);
			adicionarVariavel("Monitor", "Monitor LG", stm);
			
			//fazer o commit quando todo o c�digo acima estiver preparado para o envio (quando n�o houver nenhuma exception)
			connection.commit();
			
			
		}catch(Exception e){
			//MOSTRA QUAL A EXCEPTION
			e.printStackTrace();
			System.out.println("ROLLBACK EXECUTADO");
			//m�todo da interface connection, que desfaz todo o c�digo anteriormente executado
			connection.rollback();
		}
	}
}

private static void adicionarVariavel(String nome, String descricao, PreparedStatement stm) throws SQLException{
	stm.setString(1, nome);
	stm.setString(2, descricao);
	
	if(nome.equals("mesa")) {
		throw new RuntimeException("N�o foi poss�vel adicionar o produto");
	}
	
	stm.execute();
	//ResultSet extende o AutoClosable, entao ao ser fechado o try o rst tamb�m ser� fechado
	try(ResultSet rst = stm.getGeneratedKeys()){
	while(rst.next()) {
		Integer id = rst.getInt(1);
		System.out.println(id);
	}
	}
}

}
