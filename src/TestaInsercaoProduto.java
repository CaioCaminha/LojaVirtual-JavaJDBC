import java.sql.SQLException; 
import java.sql.Connection;

public class TestaInsercaoProduto {
	public static void main(String[] args) throws SQLException{
		produto armario = new produto("celular", "Iphone 12");
		
		try(Connection connection = new ConnectionFactory().RecuperarConexao()){
			ProdutoDAO produtoDao = new ProdutoDAO(connection);
			produtoDao.salvar(armario); 
		}
		System.out.println(armario);
	}
}
