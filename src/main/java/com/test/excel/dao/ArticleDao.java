package com.test.excel.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface ArticleDao {
	
	void insert(List<Map<String, Object>> map) throws DataAccessException;
	
	void create(Map<String, String> map) throws DataAccessException;
}
