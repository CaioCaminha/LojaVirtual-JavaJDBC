
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestaListagemDeCategorias {
	public static void main(String[] args) throws SQLException {
		
		 try(Connection connection = new ConnectionFactory().RecuperarConexao()){
			 
			 CategoriaDAO categoriaDao = new CategoriaDAO(connection);
			
			 List<Categoria> ListaCategorias = categoriaDao.listarComProdutos();
			 
			 ListaCategorias.stream().forEach(ct -> {
				 System.out.println(ct.getNome());
				//cria o atributo produto do tipo Produto que recebe a lista de produtos da categoria
				 for(Produto produto : ct.getProdutos()) {
					 //printa o nome da categoria e o nome dos produtos contidos na lista produto
						 System.out.println(ct.getNome() + " - " + produto.getNome());
				 }
				 
			 });
		 }
	}
}
