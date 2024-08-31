package com.corcentric.common;

import com.corcentric.runner.CucumberTestRunner;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

public class Datatable extends CucumberTestRunner {
	/********************************************************
     * Method Name      : setCellData()
     * Purpose          : to set/update the data into the excel file based on row & column numbers
     * Author           : Gudi
     * Parameters       : filePath, sheetName, columnName, logicalName, strValue
     * ReturnType       : NA
     ********************************************************/
	public void setCellData(String filePath, String sheetName, String columnName, String logicalName, String strValue) {
		FileInputStream fin = null;
		FileOutputStream fout = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		int colNum = 0;
		int rowNum = 0;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);

			//find the columnNumber based on the columnName
			row = sh.getRow(0);
			int cols = row.getPhysicalNumberOfCells();
			for(int c=0; c<cols; c++) {
				cell = row.getCell(c);
				if(cell.getStringCellValue().trim().equalsIgnoreCase(columnName)) {
					colNum = c;
					break;
				}
			}


			//Find the row number based on the logicalName
			int rows = sh.getPhysicalNumberOfRows();
			for(int r=0; r<rows; r++) {
				row = sh.getRow(r);
				cell = row.getCell(0);
				if(cell.getStringCellValue().trim().equalsIgnoreCase(logicalName)) {
					rowNum = r;
					break;
				}
			}

			row = sh.getRow(rowNum);
			cell = row.getCell(colNum);

			if(row.getCell(colNum) == null) {
				cell = row.createCell(colNum);
			}

			cell.setCellValue(strValue);

			fout = new FileOutputStream(filePath);
			wb.write(fout);
		}catch(Exception e) {
			reports.writeResult(null, "Exception", "Exception while executing 'setCellData()' method. " + e);
		}
		finally
		{
			try {
				fout.flush();
				fout.close();
				fout = null;
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e) {
				reports.writeResult(null, "Exception", "Exception while executing 'setCellData()' method. " + e);
			}
		}
	}





	/********************************************************
	 * Method Name      : getCellData()
	 * Purpose          : to get/Read the data from the excel file based on row & column numbers
	 * Author           : Gudi
	 * Parameters       : filePath, sheetName, columnName, logicalName, strValue
	 * ReturnType       : NA
	 ********************************************************/
	public String getCellData(String filePath, String sheetName, String columnName, int rowNum)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		int colNum = 0;
		String strData = null;
		String sDay = null;
		String sMonth = null;
		String sYear = null;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);

			if(sh==null) {
				reports.writeResult(null, "Fail", "The sheet '"+sheetName+"' doesnot exist");
				return null;
			}

			//Find out the column number based on the column name
			row = sh.getRow(0);
			for(int c=0; c<row.getPhysicalNumberOfCells(); c++)
			{
				cell = row.getCell(c);
				if(cell.getStringCellValue().equalsIgnoreCase(columnName))
				{
					colNum = c;
					break;
				}
			}

			row = sh.getRow(rowNum);
			cell = row.getCell(colNum);

			if(cell==null || cell.getCellType()==CellType.BLANK)
			{
				strData = "";
			}else if(cell.getCellType()==CellType.BOOLEAN) {
				strData = String.valueOf(cell.getBooleanCellValue());
			}else if(cell.getCellType()==CellType.STRING)
			{
				strData = cell.getStringCellValue();
			}else if(cell.getCellType()==CellType.NUMERIC)
			{
				//Validate the cell comtain date
				if(DateUtil.isCellDateFormatted(cell))
				{
					double dt = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(dt));

					//If day is less than 10 then prefix zero
					if(cal.get(Calendar.DAY_OF_MONTH)<10) {
						sDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
					}else {
						sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
					}


					//If month is less than 10 then prefix zero
					if(cal.get(Calendar.DAY_OF_MONTH)<10) {
						sMonth = "0" + (cal.get(Calendar.MONTH)+1);
					}else {
						sMonth = String.valueOf((cal.get(Calendar.MONTH)+1));
					}

					sYear = String.valueOf(cal.get(Calendar.YEAR));
					strData = sDay +"-"+ sMonth +"-"+ sYear;
				}else {
					strData = String.valueOf(cell.getNumericCellValue());
				}
			}
			return strData;
		}catch(Exception e)
		{
			reports.writeResult(null, "Exception", "Exception in getCellData() method. "+e.getMessage());
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				reports.writeResult(null, "Exception", "Exception in getCellData() method. "+e.getMessage());
				return null;
			}
		}
	}
}
