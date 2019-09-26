package com.test.excel.web;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.excel.service.ExcelService;
import com.test.excel.vo.Fruit;
@Controller
public class ExcelController {
	
	@Autowired
	ExcelService service;
	
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
    public List<Map<String, Object>> uploadExcelFile(MultipartHttpServletRequest request, Model model) throws JsonProcessingException {
        MultipartFile file = null;
        Iterator<String> iterator = request.getFileNames();
        if(iterator.hasNext()) {
            file = request.getFile(iterator.next());
        }
        List<Map<String, Object>> list = service.uploadExcelFile(file);
        
        //model.addAttribute("list", new ObjectMapper().writeValueAsString(list));
        return list;
    }

}
