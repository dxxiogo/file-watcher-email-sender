package models.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import models.dao.SendDao;
import models.entities.Send;

public class SendJDBC implements SendDao {
	
	
	private Connection conn;
	

	public SendJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void create(Send obj) {
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO SEND (RECIPIENT, FILE_NAME, RESPONSE, SEND_DATE)"
					+ "VALUES (?, ?, ?, ?)");
			
			st.setString(1, obj.getRecipient());
			st.setString(2, obj.getFileName());
			st.setString(3, obj.getResponse());

			LocalDateTime dateTime = obj.getDate();

			java.sql.Date sqlDate = java.sql.Date.valueOf(dateTime.toLocalDate());

			st.setDate(4, sqlDate);

			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected <= 0) {
				throw new DbException("Unexpected error");
			}
			
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}

	
	@Override
	public List<Send> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Send> sends = new ArrayList<>();
		
		try {
			
			st = conn.prepareStatement("SELECT * FROM SEND");
			rs = st.executeQuery();
			while(rs.next()) {
				Send send = instantiateSend(rs);
				sends.add(send);
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		
		return null;
	}
	
	
	private Send instantiateSend(ResultSet rs) throws SQLException {
		Send send = new Send();
		send.setId(rs.getInt("ID"));
		send.setFileName(rs.getString("FILE_NAME"));
		send.setRecipient(rs.getString("RECIPIENT"));
		send.setResponse(rs.getString("RESPONSE"));
		send.setDate(LocalDateTime.parse((CharSequence) rs.getDate("SEND_DATE")));
		return send;
	}
	
}
