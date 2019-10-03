package com.test.excel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExcelService {
    
    /**
     * 리스트를 간단한 엑셀 워크북 객체로 생성
     * @param list
     * @return 생성된 워크북
     */
    public XSSFWorkbook makeSimpleFruitExcelWorkbook(List<Map<String, Object>> list) {
    	XSSFWorkbook workbook = new XSSFWorkbook();
        
        // 시트 생성
        XSSFSheet sheet = workbook.createSheet("과일표");
        
        //시트 열 너비 설정
        sheet.setColumnWidth(0, 1500);
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(0, 1500);
        
        // 헤더 행 생
        Row headerRow = sheet.createRow(0);
        // 해당 행의 첫번째 열 셀 생성
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("번호");
        // 해당 행의 두번째 열 셀 생성
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("과일이름");
        // 해당 행의 세번째 열 셀 생성
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("가격");
        // 해당 행의 네번째 열 셀 생성
        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("수량");
        
        // 과일표 내용 행 및 셀 생성
        XSSFRow  bodyRow = null;
        XSSFCell bodyCell = null;
        for(int i=0; i<list.size(); i++) {
            // 행 생성
            bodyRow = sheet.createRow(i+1);
            // 데이터 번호 표시
            bodyCell = bodyRow.createCell(0);
            bodyCell.setCellValue((Integer) list.get(i).get("id"));
            // 데이터 이름 표시
            bodyCell = bodyRow.createCell(1);
            bodyCell.setCellValue((String) list.get(i).get("name"));
            // 데이터 가격 표시
            bodyCell = bodyRow.createCell(2);
            bodyCell.setCellValue((String) list.get(i).get("price"));
            // 데이터 수량 표시
            bodyCell = bodyRow.createCell(3);
            bodyCell.setCellValue((String) list.get(i).get("quantity"));
        }
        return workbook;
    }
    
    /**
     * 생성한 엑셀 워크북을 컨트롤레에서 받게해줄 메소드
     * @param list
     * @return
     */
    public XSSFWorkbook excelFileDownloadProcess(List<Map<String, Object>> list) {
        return this.makeSimpleFruitExcelWorkbook(list);
    }
    
    
    /**
     *업로드한 엑셀파일을 리스트로 만들기
     * @param excelFile
     * @return 리스트
     */
    public List<Map<String, String>> uploadExcelFile(MultipartFile excelFile){
        
        Map<String, String> map = null;
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        List<String> headerName = new ArrayList<String>(); 
        try {
        	// OPCPackage 2007이상 엑셀 파일들 읽어 올때
            OPCPackage opcPackage = OPCPackage.open(excelFile.getInputStream());
            XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
            
            // 첫번째 시트 불러오기
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            //데이터가 있는 로우 개수
            int rowNum = sheet.getLastRowNum();
            //데이터가 있는 컬럼 개수
            int colNum = sheet.getRow(sheet.getLastRowNum()).getPhysicalNumberOfCells();
            System.out.println("로우 수 : " + rowNum);
            System.out.println("컬럼 수 : " + colNum);
            XSSFRow row = sheet.getRow(0);

            for(int i = 0; i < colNum; i++) {
            	headerName.add(convertName(row.getCell(i).getStringCellValue()));
            }
            
//            for(int i = 0; i < colNum; i++) {
//            	headerName.add(row.getCell(i).getStringCellValue());
//            }

            for(int i=1; i<sheet.getLastRowNum() + 1; i++) {
            	map = new HashMap<String, String>();
                row = sheet.getRow(i);
                // 행이 존재하기 않으면 패스
                if(null == row) {
                    continue;
                }
                XSSFCell cell = null;
                for(int j = 0; j < colNum; j++) {
                	cell = row.getCell(j);
                	if(null != cell) {
                		map.put(headerName.get(j), getStringValue(cell));
                	}
                }
                result.add(map);
            }
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static String getStringValue(Cell cell) {
        String rtnValue = "";
        try {
            rtnValue = cell.getStringCellValue();
        } catch(IllegalStateException e) {
            rtnValue = Integer.toString((int)cell.getNumericCellValue());            
        }
        
        return rtnValue;
    }
    
    public static String convertName(String cellName) {
    		if(cellName.equals("과일이름")) {
    			return "name";
    		} else if(cellName.equals("가격")) {
    			return "price";
    		} else if(cellName.equals("수량")) {
    			return "quantity";
    		} else if(cellName.equals("번호")) {
    			return "id";
    		} else {
    			return null;
    		}
    }

}
