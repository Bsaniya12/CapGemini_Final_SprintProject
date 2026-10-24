package qademo.utility;

import java.io.InputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

    private static final String SHEET_NAME = "TestData";

    public static String getDataByHeader(
            String testCaseId, String columnName) throws IOException {

        return readData(SHEET_NAME, testCaseId, columnName);
    }

    public static String getSignupDataByHeader(
            String testCaseId, String columnName) throws IOException {

        return readData("Signup", testCaseId, columnName);
    }

    // Read invalid login data from the Invalidlogin sheet
    public static String getInvalidLoginDataByHeader(
            String testCaseId, String columnName) throws IOException {

        return readData("Invalidlogin", testCaseId, columnName);
    }

    // Read checkout data from the Validcheckout sheet
    public static String getCheckoutDataByHeader(
            String testCaseId, String columnName) throws IOException {

        return readData("Validcheckout", testCaseId, columnName);
    }

    private static String readData(
            String sheetName,
            String testCaseId,
            String columnName) throws IOException {

        InputStream inputStream = ExcelUtility.class
                .getClassLoader()
                .getResourceAsStream("TestDatafile.xlsx");

        if (inputStream == null) {
            throw new IOException(
                    "TestDatafile.xlsx not found. "
                    + "Please check src/test/resources/");
        }

        try (InputStream fis = inputStream;
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IOException("Sheet not found: " + sheetName);
            }

            DataFormatter formatter = new DataFormatter();
            Row headerRow = sheet.getRow(0);

            if (headerRow == null) {
                throw new IOException("Excel header row is missing.");
            }

            int idColumn = -1;
            int targetColumn = -1;

            for (int col = 0;
                    col < headerRow.getLastCellNum(); col++) {

                String header = formatter.formatCellValue(
                        headerRow.getCell(col)).trim();

                if (header.equalsIgnoreCase("TC_ID")) {
                    idColumn = col;
                }

                if (header.equalsIgnoreCase(columnName)) {
                    targetColumn = col;
                }
            }

            if (idColumn == -1 || targetColumn == -1) {
                throw new IOException(
                        "Required column not found: " + columnName
                        + " in sheet " + sheetName);
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                String currentId = formatter.formatCellValue(
                        row.getCell(idColumn)).trim();

                if (currentId.equalsIgnoreCase(testCaseId)) {

                    String value = formatter.formatCellValue(
                            row.getCell(targetColumn)).trim();

                    if (value.isEmpty()) {
                        throw new IOException(
                                "No value for " + columnName
                                + " in " + testCaseId);
                    }

                    return value;
                }
            }

            throw new IOException(
                    "Test case not found: " + testCaseId
                    + " in sheet " + sheetName);
        }
    }
}