package com.test.excel.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import com.test.excel.vo.FileVO;

@Service
public class ExcelDataProcessService {
	private static final String UPLOAD_PATH = "C:\\Study\\fileupload\\";

	// 시각화 요청 데이터 미리보기
	public Map<String, Object> getPreviewFileData(MultipartFile file) throws Exception {
//	      System.out.println("");
//	      System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//	      System.out.println("getOrignlFileNm : " + fvo.getOrignlFileNm());
//	      System.out.println("getStreFileNm : " + fvo.getStreFileNm());
//	      System.out.println("getFileExtsn : " + fvo.getFileExtsn());
//	      System.out.println("getFileStreCours : " + fvo.getFileStreCours());
//	      System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//	      System.out.println("");
		System.out.println(file.getOriginalFilename());
		FileVO fvo = new FileVO();
		fvo.setOrignlFileNm(file.getOriginalFilename());
		fvo.setStreFileNm(file.getOriginalFilename());
		int pos = fvo.getOrignlFileNm().lastIndexOf(".");
		fvo.setFileExtsn(fvo.getOrignlFileNm().substring(pos + 1));
		long startTime = System.currentTimeMillis();

		Map<String, Object> data = new LinkedHashMap<String, Object>();

		try {
			if (fvo.getFileExtsn().toLowerCase().equals("csv")) {
				// csv 타입 데이터 읽어오기
				System.out.println("readCSVFILE" + fvo.toString());
				data = readCsvFile(fvo, file);
			} else if (fvo.getFileExtsn().toLowerCase().equals("xlsx")) {
				// xlsx 타입 데이터 읽어오기 (대용량까지 가능)
//	            data = readLargeExcelFile(fvo);
				data = excelFileRead(fvo, file);
			} else if (fvo.getFileExtsn().toLowerCase().equals("xls")) {
				// xls 타입 데이터 읽어오기
				data = excelFileRead(fvo, file);
			}
		} catch (Throwable e) {
			// e.printStackTrace();
		} finally {
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
		}
		System.out.println(data);
		return data;
	}

	// CellProcessor[] 객체 생성 후, 배열 내부의 null값들을 Optional로 대체
	private CellProcessor[] getProcessors(int size) throws Exception {
		CellProcessor[] processors = new CellProcessor[size];
		for (int i = 0; i < size; i++) {
			Optional sub_processors = new Optional();
			processors[i] = sub_processors;
		}
		return processors;
	}

	/**
	 * .csv 파일 읽고 데이터 반환
	 * 
	 * @param fvo
	 * @return
	 * @throws Exception
	 */
	private final static String NULL = "-";
	public final static String UNKNOWN = "";
	public final static int MAX_READ_ROW = 100;

	public Map<String, Object> readCsvFile(FileVO fvo, MultipartFile file) throws Exception {
		// 반환 데이터 (시트별 데이터 - csv는 시트가 없음)
		Map<String, Object> info = new LinkedHashMap<String, Object>();

		// csv 파일을 읽어드림
		BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), "euc-kr"));
		ICsvListReader listReader = null;

//	      try {
		// 읽은 csv 파일 객체를 super csv라는 라이브러리의 ICsvListReader 객체로 받아옴 (STANDARD_PREFERENCE
		// => csv 표준 데이터 구분값 - '"', ',', "\r\n")
		listReader = new CsvListReader(br, CsvPreference.STANDARD_PREFERENCE);

		/* @@@ 해더 정보 담기 @@@ */
		// 첫번째 줄(컬럼) 데이터 가져오기
		final String[] header = listReader.getHeader(true);

		// csv파일의 시트 및 행,열 데이터를 담을 변수 (시트는 1개밖에 없음)
		List<List<Map<String, String>>> rowDataList = new ArrayList<List<Map<String, String>>>();

		// 한개의 행에 대한 열 데이터 List
		List<Map<String, String>> headerCellDataList = new ArrayList<Map<String, String>>();

		// 컬럼 중 null값을 '-' 대체
		for (int i = 0; i < header.length; i++) {
			// Cell의 색상과, data를 담기 위한 Map 변수
			Map<String, String> map = new LinkedHashMap<String, String>();

			String value = header[i];
			if (value == null) {
				value = NULL;
			}
			map.put("color", "#ddd");
			map.put("value", value);
			headerCellDataList.add(map);
		}
		rowDataList.add(headerCellDataList);

		/* @@@ 바디 정보 담기 @@@ */
		// getProcessors => CellProcessor[] 객체 생성 후, 배열 내부의 null값들을 Optional로 대체
		final CellProcessor[] processors = getProcessors(header.length);

		int index = 1;
		// 위에서부터 한 행씩 읽어감 다음 줄의 데이터가 없을 시(null값), while문 종료
		while ((listReader.read()) != null && index <= MAX_READ_ROW) {
			// 한개의 행에 대한 열 데이터 List
			List<Map<String, String>> bodyCellDataList = new ArrayList<Map<String, String>>();

			// 행 값을 받아옴
			List<Object> csvRowDataList = listReader.executeProcessors(processors);

			for (int i = 0; i < header.length; i++) {
				// Cell의 색상과, data를 담기 위한 Map 변수
				Map<String, String> map = new HashMap<String, String>();

				if (csvRowDataList.get(i) == null) {
					csvRowDataList.set(i, NULL); // null 빈 값 처리
				}
				map.put("color", "");
				map.put("value", (String) csvRowDataList.get(i));
				bodyCellDataList.add(map);
			}

			rowDataList.add(bodyCellDataList); // 행별로 된 데이터 리스트들
			index++;
		}

		// Key(시트명 -> 파일명) : Value(시트의 행,열 데이터)
		info.put(fvo.getOrignlFileNm().substring(0, fvo.getOrignlFileNm().lastIndexOf(".")), rowDataList);

//	      } catch (Exception e) {
		// e.printStackTrace();
//	      } finally {
//	         
//	      }

		if (listReader != null) {
			listReader.close();
		}

		return info;
	}

	/**
	 * 엑셀 - Excel (xls, xlsx) 파일 읽고 데이터 반환
	 * 
	 * @param fvo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> excelFileRead(FileVO fvo, MultipartFile file) throws Exception {
		// 반환 데이터 (시트별 데이터)
		Map<String, Object> info = new LinkedHashMap<String, Object>();

		Workbook wb = null;
//	      try {
		// 파일 객체 생성
//		File dataFile = new File(UPLOAD_PATH, fvo.getStreFileNm());
		System.out.println("File is Empty ? : " +file.isEmpty());
		// 엑셀 객체 생성
		wb = WorkbookFactory.create(file.getInputStream());
		// 시트 for문
		for (int sheetIdx = 0; sheetIdx < wb.getNumberOfSheets(); sheetIdx++) {

			// 시트 객체
			Sheet sheet = wb.getSheetAt(sheetIdx);

			// 데이터가 들어있는 시트의 첫 번째 행 index
			int firstRowNum = sheet.getFirstRowNum();
			// 데이터가 들어있는 시트의 최종 행 index
			int lastRowNum = sheet.getLastRowNum();

			// 데이터가 들어있는 시트의 첫 번째 행의 마지막 열 index
			int firstCellNum = sheet.getRow(firstRowNum).getFirstCellNum();
			int lastCellNum = sheet.getRow(firstRowNum).getLastCellNum();

			// 시트의 최종 행이 100보다 클 때, 최종 행을 100개로 한정
			if (lastRowNum > MAX_READ_ROW) {
				lastRowNum = MAX_READ_ROW;
			}

			// 한개의 시트에 대한 행,열 데이터 List
			List<List<Map<String, String>>> rowDataList = new ArrayList<List<Map<String, String>>>();

			// 시트의 Row for문
			for (int rowIdx = firstRowNum; rowIdx < lastRowNum + firstRowNum; rowIdx++) {
				// Row 객체
				Row row = sheet.getRow(rowIdx);

				if (row != null) {
					// 한개의 행에 대한 열 데이터 List
					List<Map<String, String>> cellDataList = new ArrayList<Map<String, String>>();
					// Row의 Cell 데이터 담기
					for (int cellIdx = firstCellNum; cellIdx < lastCellNum; cellIdx++) {
						// Cell의 색상과, data를 담기 위한 Map 변수
						Map<String, String> map = new HashMap<String, String>();
						Cell cell = row.getCell(cellIdx);
						String value = cell.getStringCellValue();
						if (value == null || value.trim().length() <= 0) {
							value = NULL;
						}
						map.put("color", null);
						map.put("value", value);
						map.put("colspan", "1");
						map.put("rowspan", "1");
						cellDataList.add(map);
					}
					rowDataList.add(cellDataList);
				}

			} // !시트의 Row for문 끝

			rowDataList = mergeCheck(sheet, rowDataList, firstRowNum, lastRowNum, firstCellNum, lastCellNum);
			// Key(시트명) : Value(시트의 행,열 데이터)
			info.put(sheet.getSheetName(), rowDataList);

		} // !시트 for문 끝

		return info;
	}

	protected List<List<Map<String, String>>> mergeCheck(Sheet sheet, List<List<Map<String, String>>> rowDataList,
			int firstRowNum, int lastRowNum, int firstCellNum, int lastCellNum) {
		for (int i = 0; i < sheet.getNumMergedRegions(); ++i) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			if (range.getFirstRow() < firstRowNum)
				continue;
			if (range.getLastRow() > lastRowNum)
				continue;
			if (range.getFirstColumn() < firstCellNum)
				continue;
			if (range.getLastColumn() > lastCellNum)
				continue;

			int startRowIndexByData = range.getFirstRow() - firstRowNum;
			int rowMergedSize = range.getLastRow() - range.getFirstRow();
			int endRowIndexByData = startRowIndexByData + rowMergedSize;

			int startCellIndexByData = range.getFirstColumn() - firstCellNum;
			int cellMergedSize = range.getLastColumn() - range.getFirstColumn();
			int endCellIndexByData = startCellIndexByData + cellMergedSize;

			for (int j = startRowIndexByData; j <= endRowIndexByData; j++) {
				for (int z = startCellIndexByData; z <= endCellIndexByData; z++) {
					if (j == startRowIndexByData && z == startCellIndexByData) {
						rowDataList.get(j).get(z).put("colspan", (cellMergedSize + 1) + "");
						rowDataList.get(j).get(z).put("rowspan", (rowMergedSize + 1) + "");
					} else {
						rowDataList.get(j).get(z).put("colspan", "0");
						rowDataList.get(j).get(z).put("rowspan", "0");
					}
				}
			}

		}
		return rowDataList;
	}
}
