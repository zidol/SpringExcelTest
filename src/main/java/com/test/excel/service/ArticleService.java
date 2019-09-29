package com.test.excel.service;

import java.util.List;
import java.util.Map;

public interface ArticleService {
	
	//게시글 작성
	void insertArticle(List<Map<String, Object>> map) throws Exception;
	void createArticle(List<Map<String, Object>> map, String tableName) throws Exception;
	
}
