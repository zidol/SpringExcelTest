package com.test.excel.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface ArticleDao {
	
	void insert(List<Map<String, String>> map) throws DataAccessException;
	
	void create(Map<String, String> map) throws DataAccessException;
	
	public List<String> articleList() throws DataAccessException;

	public List<Map<String, String>> article(Map<String, String> id) throws DataAccessException;
}
