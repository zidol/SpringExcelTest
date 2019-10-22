package com.test.excel.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.test.excel.service.ArticleService;
import com.test.excel.service.ExcelDataProcessService;
import com.test.excel.service.ExcelService;

@Controller
public class ExcelController {

	private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

	@Autowired
	ExcelService service;

	@Autowired
	ExcelDataProcessService excelDataProcessService;

	@Autowired
	ArticleService articleService;

	@RequestMapping(value = "/excel/downloadExcelFile", method = RequestMethod.POST)
	public String downloadExcelFile(HttpServletRequest request, Model model) throws Exception {

		String tableName = request.getParameter("tableName");
//		System.out.println(tableName);
//        String[] names = {"자몽", "애플망고", "멜론", "오렌지"};
//        long[] prices = {5000, 10000, 7000, 6000};
//        int[] quantities = {50, 50, 40, 40};
//        List<Fruit> list = service.makeFruitList(names, prices, quantities);
		Map<String, String> map = new HashMap<String, String>();
		map.put("tableName", tableName);
		List<Map<String, String>> list = articleService.article(map);

		XSSFWorkbook workbook = service.excelFileDownloadProcess(list);

		model.addAttribute("locale", Locale.KOREA);
		model.addAttribute("workbook", workbook);
		model.addAttribute("workbookName", "Excel");

		return "excelDownloadView";
	}

	@RequestMapping(value = "/uploadExcelFile", method = RequestMethod.POST)
	@ResponseBody
//    public List<Map<String, String>> uploadExcelFile(MultipartHttpServletRequest request, Model model) throws Exception {
	public Map<String, Object> uploadExcelFile(MultipartHttpServletRequest request, Model model,
			@RequestParam("selected") String selected) throws Exception {
		request.setCharacterEncoding("UTF-8");
		System.out.println(selected);
		MultipartFile file = null;
		Iterator<String> iterator = request.getFileNames();
		if (iterator.hasNext()) {
			file = request.getFile(iterator.next());
		}
//        FileVO fvo = new FileVO();
//        fvo.setOrignlFileNm(file.getOriginalFilename());
//        fvo.set
		Map<String, Object> map = new HashMap<String, Object>();

//        List<Map<String, String>> list = service.uploadExcelFile(file);

//        return list;
		return excelDataProcessService.getPreviewFileData(file, selected);
	}

	@RequestMapping(value = "/extractData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  extractData(@RequestParam Map<String, Object> parameters) throws Exception {
		String json = parameters.get("list").toString();
		int number = Integer.parseInt(parameters.get("number").toString());
		int dataNumber = Integer.parseInt(parameters.get("dataNumber").toString());
		System.out.println("Number : " + number);
		System.out.println("dataNumber : " + dataNumber);
		ObjectMapper mapper = new ObjectMapper();
		List<List<Map<String, Object>>> list = mapper.readValue(json,
				new TypeReference<ArrayList<ArrayList<Map<String, Object>>>>() {
				});
		System.out.println("자르기 전 : " + list);
		System.out.println("배열 수  : " + list.size());
		if (number != dataNumber) {
			for (int i = dataNumber-2; i >= number; i--) {
				list.remove(i);
			}
		}

		for (int i = number-2; i >= 0; i--) { 
			list.remove(i); 
		}

		System.out.println("자른 후 : " + list);

		// System.out.println("data list : " + dataList);
//		System.out.println("number : " + header);
//		System.out.println("data number : " + dataStart);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", list);

		return map; 
	}
	@ResponseBody
	@RequestMapping(value = "/insertData", method = RequestMethod.POST)
	public void insertData(@RequestBody List<Map<String, String>> list) throws Exception {
		System.out.println(list);
		String tableName = System.currentTimeMillis() + "";
		logger.info("테이블 생성");

		tableName += "_Excel";
		articleService.createTable(list, tableName);

		logger.info("테이블 생성 완료");

		Map<String, String> addMap = new HashMap<String, String>();
		addMap.put("tableName", tableName);
//		list.add(addMap);
		logger.info("테이블 데이터 입력");
		articleService.insertArticle(list, addMap);
		logger.info("테이블 데이터 입력 완료");
	}

	@RequestMapping(value = "/articleList", method = RequestMethod.GET)
	public String viewData(Model model) throws Exception {
		List<String> map = articleService.articleList();
		model.addAttribute("tableList", new ObjectMapper().writeValueAsString(map));
		return "articleList";
	}

	// 게시글 상세 내용 호출
	@RequestMapping(value = "/excel/{tableName}", method = RequestMethod.GET)
	public String article(@PathVariable("tableName") String tableName, Model model) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tableName", tableName);
		List<Map<String, String>> list = articleService.article(map);
		System.out.println(list);
		model.addAttribute("article", new ObjectMapper().writeValueAsString(list));
		model.addAttribute("tableName", tableName);
		return "articleDetail";
	}
}
