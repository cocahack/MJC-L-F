package jdbc;

import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class DBCPInit extends HttpServlet {

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		loadJDBCDriver();
		initConnectionPool();
	}
	private void loadJDBCDriver(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException e){
			throw new RuntimeException("fail to load JDBC Driver");
		}
	}
	private void initConnectionPool(){
		try{
			String jdbcUrl = "jdbc:mysql://localhost:3306/webdb"
					+"?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false";
			String username = "root";
			String password = "medusa11";
			
			ConnectionFactory connFactory = 
					new DriverManagerConnectionFactory(jdbcUrl, username, password);
			PoolableConnectionFactory poolableConnectionFactory = 
					new PoolableConnectionFactory(connFactory, null);
			poolableConnectionFactory.setValidationQuery("select 1");
			
			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
			poolConfig.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L);
			poolConfig.setTestWhileIdle(true);
			poolConfig.setMinIdle(4);
			poolConfig.setMaxTotal(50);
			
			GenericObjectPool<PoolableConnection> connectionPool = 
					new GenericObjectPool<>(poolableConnectionFactory, poolConfig);
			poolableConnectionFactory.setPool(connectionPool);
			
			Class.forName("org.apache.commons.dbcp2.PoolingDriver");
			PoolingDriver driver = (PoolingDriver)DriverManager.getDriver("jdbc:apache:commons:dbcp:");
			driver.registerPool("webdb", connectionPool);
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
