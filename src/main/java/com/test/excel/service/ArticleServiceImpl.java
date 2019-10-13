package com.test.excel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
	public void insertArticle(List<Map<String, String>> map, Map<String, String> tableName) throws Exception {
		// TODO Auto-generated method stub\
		List<Map<String, String>> newList = new ArrayList<Map<String,String>>();
		Map<String, String> newMap = null;
		//엑셀 헤더이름 추출
		List<String> keys = new ArrayList<String>();
		Map<String, String> keyMap = new LinkedHashMap<String, String>();
		keyMap = map.get(0);
		Set set = keyMap.keySet();
		Iterator iterator = set.iterator();
		
		//헤더이름 테이블 컬럼 이름으로 저장 하기위한 작업
		while(iterator.hasNext()) {
			String key = (String)iterator.next();
			keys.add(key);
		}
		
		System.out.println(keys);
		for(int i = 0; i < map.size(); i++) {
			newMap = new LinkedHashMap<String, String>();
			for(int j = 0; j < keys.size(); j++) {
				String convertedValue = map.get(i).get(keys.get(j)).replace("\\", "\\\\");
				newMap.put(keys.get(j), convertedValue);
			}
			newList.add(newMap);
		}
		newList.add(tableName);
		map.clear();
		articleDao.insert(newList);
	}

	@Override
	public void createTable(List<Map<String, String>> map, String tableName) throws Exception {
		// TODO Auto-generated method stub
		List<String> tableColumn = new ArrayList<String>();
		
		//엑셀 헤더이름 추출
		Map<String, String> keyMap = new LinkedHashMap<String, String>();
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
			create_table += tableColumn.get(i).equals("id") ? tableColumn.get(i).replace(" ", "") + " int auto_increment primary key, " : tableColumn.get(i).replace(" ", "_") + " varchar(500) not null, ";
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
	public List<Map<String, String>> article(Map<String, String> id) throws Exception {
		return articleDao.article(id);
	}

}
