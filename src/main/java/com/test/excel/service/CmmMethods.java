package com.test.excel.service;

import org.apache.poi.ss.usermodel.Cell;

public class CmmMethods {

	public static String getCellValue(Cell cell) {
		// TODO Auto-generated method stub
		String cellValue = cell.getStringCellValue();
		return cellValue;
	}

}
