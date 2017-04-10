package viewPackage.insert;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.ConnectionProvider;

public class InsertArticleService {
	public int getNumberOfList() throws SQLException{
		try(Connection conn = ConnectionProvider.getConnection()){
			
		}
		return 0;
	}
}
