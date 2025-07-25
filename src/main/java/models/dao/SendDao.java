package models.dao;

import java.util.List;

import models.entities.Send;

public interface SendDao {
	
	public void create(Send obj);
	
	public List<Send> findAll ();
}
