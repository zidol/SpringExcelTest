package com.test.excel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	@Override
	public void createArticle(List<Map<String, Object>> map, String tableName) throws Exception {
		// TODO Auto-generated method stub
		List<String> tableColumn = new ArrayList<String>();
		
		//엑셀 헤더이름 추출
		Map<String, Object> keyMap = new HashMap<String, Object>();
		keyMap = map.get(0);
		Set set = keyMap.keySet();
		Iterator iterator = set.iterator();
		
		//헤더이름 테이블 컬럼 이름으로 저장 하기위한 작업
		while(iterator.hasNext()) {
			String key = (String)iterator.next();
			tableColumn.add(key);
		}
		
		String create_table = "create table " + tableName + "(";
		for(int i = 0; i < tableColumn.size(); i++) {
			create_table += tableColumn.get(i).equals("id") ? tableColumn.get(i).replace(" ", "") + " int auto_increment primary key, " : tableColumn.get(i).replace(" ", "") + " varchar(500) not null, ";
		}
		create_table = create_table.substring(0, create_table.length()-2);
		create_table += ")DEFAULT CHARSET=utf8";
		
		Map<String, String> tableMap = new HashMap<String, String>();
		tableMap.put("create_table", create_table);
		System.out.println(create_table);
		articleDao.create(tableMap);
	}

	@Override
	public List<String> articleList() throws Exception {
		return articleDao.articleList();
	}

	@Override
	public List<Map<String, Object>> article(Map<String, String> id) throws Exception {
		return articleDao.article(id);
	}

}
