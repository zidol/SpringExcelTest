package com.test.excel.service;

import java.util.List;
import java.util.Map;

public interface ArticleService {
	
	//테이블 데이터 넣기
	void insertArticle(List<Map<String, Object>> map) throws Exception;
	
	//테이블 생성하기
	void createArticle(List<Map<String, Object>> map, String tableName) throws Exception;
	
	//테이블 목록 불러오기
	List<String> articleList() throws Exception;
	
	//테이블 상세내용
	List<Map<String, String>> article(Map<String, String> id) throws Exception;
	
}
