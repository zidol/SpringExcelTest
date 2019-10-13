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
	
	private static final String INSERT = MP + ".insert";
	private static final String CREATE_TABLE = MP + ".create_table";
	private static final String SELECT_ALL = MP + ".listAll";
	private static final String SELECT_DETAIL = MP + ".article";
	
	@Override
	public void insert(List<Map<String, String>> map) throws DataAccessException {
		sqlsession.insert(INSERT, map);		
	}

	@Override
	public void create(Map<String, String>map) throws DataAccessException {
		sqlsession.update(CREATE_TABLE, map);	
	}

	@Override
	public List<String> articleList() throws DataAccessException {
		return sqlsession.selectList(SELECT_ALL);
	}

	@Override
	public List<Map<String, String>> article(Map<String, String> id) throws DataAccessException {
		return sqlsession.selectList(SELECT_DETAIL, id);
	}
}
