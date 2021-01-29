import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;

public class TestaInsercaoEListagemProduto {
	public static void main(String[] args) throws SQLException{
		Produto armario = new Produto("televisao", "Iphone 12");
		
		try(Connection connection = new ConnectionFactory().RecuperarConexao()){
			ProdutoDAO produtoDao = new ProdutoDAO(connection);
			produtoDao.salvar(armario); 
			
		List<Produto> ListaProdutos = produtoDao.listar();
		//cria uma listagem dos produtos atraves de um forEach
		ListaProdutos.stream().forEach(lp -> System.out.println(lp));
		}
	}
}
