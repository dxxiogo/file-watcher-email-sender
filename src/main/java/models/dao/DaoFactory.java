package models.dao;

import db.DB;
import models.dao.impl.SendJDBC;

public class DaoFactory {
	
	public static SendJDBC createDaoJDBC () {
		return new SendJDBC(DB.getConnection());
	}

}
