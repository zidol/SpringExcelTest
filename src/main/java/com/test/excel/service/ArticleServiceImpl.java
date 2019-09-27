package com.test.excel.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.excel.dao.ArticleDao;

@Service
public class ArticleServiceImpl implements ArticleService{
	
	@Autowired
	ArticleDao articleDao;
	
	@Override
	public void insertArticle(List<Map<String, Object>> map) throws Exception {
		// TODO Auto-generated method stub
		articleDao.insert(map);
		
	}

}
