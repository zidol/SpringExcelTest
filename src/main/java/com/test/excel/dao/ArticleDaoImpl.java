package com.test.excel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleDaoImpl implements ArticleDao {
	@Autowired
	private SqlSession sqlsession;
	
	private static final String MP = "mapper.article";
	
	private static final String CREATE = MP + ".create";
	private static final String CREATE_TABLE = MP + ".create_table";
	
	@Override
	public void insert(List<Map<String, Object>> map) throws DataAccessException {
		sqlsession.insert(CREATE, map);		
	}

	@Override
	public void create(Map<String, String>map) throws DataAccessException {
		sqlsession.update(CREATE_TABLE, map);	
	}
}
