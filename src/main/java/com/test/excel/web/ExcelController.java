package com.test.excel.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.excel.HomeController;
import com.test.excel.dao.ArticleDao;
import com.test.excel.service.ArticleService;
import com.test.excel.service.ExcelService;
import com.test.excel.vo.Fruit;
@Controller
public class ExcelController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);
	
	@Autowired
	ExcelService service;
	
	@Autowired
	ArticleService articleService;
	
	@RequestMapping(value = "/downloadExcelFile", method = RequestMethod.POST)
    public String downloadExcelFile(Model model) {
        String[] names = {"자몽", "애플망고", "멜론", "오렌지"};
        long[] prices = {5000, 10000, 7000, 6000};
        int[] quantities = {50, 50, 40, 40};
        List<Fruit> list = service.makeFruitList(names, prices, quantities);
        
        SXSSFWorkbook workbook = service.excelFileDownloadProcess(list);
        
        model.addAttribute("locale", Locale.KOREA);
        model.addAttribute("workbook", workbook);
        model.addAttribute("workbookName", "과일표");
        
        return "excelDownloadView";
    }
	

	@RequestMapping(value = "/uploadExcelFile", method = RequestMethod.POST)
	@ResponseBody
    public List<Map<String, String>> uploadExcelFile(MultipartHttpServletRequest request, Model model) throws JsonProcessingException {
        MultipartFile file = null;
        Iterator<String> iterator = request.getFileNames();
        if(iterator.hasNext()) {
            file = request.getFile(iterator.next());
        }
        List<Map<String, String>> list = service.uploadExcelFile(file);
        
        //model.addAttribute("list", new ObjectMapper().writeValueAsString(list));
        return list;
    }
	
	
	@ResponseBody
	@RequestMapping(value = "/insertData", method = RequestMethod.POST)
    public void insertData(@RequestBody List<Map<String,Object>> map) throws Exception {
		String now = System.currentTimeMillis() +"";
		logger.info("테이블 생성");
		articleService.createArticle(map, now);
		logger.info("테이블 생성 완료");
		now += "_Excel";
		Map<String, Object> addMap = new HashMap<String, Object>();
		addMap.put("now", now);
		map.add(addMap);
		System.out.println(map);
		articleService.insertArticle(map);
    }
	
	@RequestMapping(value = "/articleList", method = RequestMethod.GET)
	public String viewData() throws Exception {
		return "articleList";
	}

}
