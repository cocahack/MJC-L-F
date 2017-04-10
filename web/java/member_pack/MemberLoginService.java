package member_pack;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.ConnectionProvider;

public class MemberLoginService {
	private MemberDao memberDao = new MemberDao();
	public Member login(PrivateInfoSet pis) throws SQLException{
		try(Connection conn = ConnectionProvider.getConnection()){
			Member member = memberDao.selectById(conn, pis.getId());
			if(member==null){
				return null;
			}else{
				if(member.getPwd().equals(pis.getPwd())){
					return member;
				}else{
					return null;
				}
			}
		}
	}
}
