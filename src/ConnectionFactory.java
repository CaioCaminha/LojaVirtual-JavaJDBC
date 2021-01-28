import java.sql.Connection;  
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//F�brica de conexao
public class ConnectionFactory {
	//interface java que permite a comunica��o entre a aplica��o e os drivers de pool de conex�o
	public DataSource dataSource;
	public ConnectionFactory() {
		//classe do c3p0 que permite realizar as configura��es jdbc
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost/loja_virtual?useTimezone=true&serverTimezone=UTC");
		comboPooledDataSource.setUser("root");
		comboPooledDataSource.setPassword("caminha123");
		
		comboPooledDataSource.setMaxPoolSize(15);
		
		this.dataSource = comboPooledDataSource;
	}
	
	
	
	public Connection RecuperarConexao() throws SQLException {
		return this.dataSource.getConnection();
	}
}
