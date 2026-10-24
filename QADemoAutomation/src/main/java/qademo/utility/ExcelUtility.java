
package qademo.utility;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

    private static final String FILE_PATH =
            "src/test/resources/TestDatafile.xlsx";

    private static final String SHEET_NAME = "TestData";

    // Read data from TestData sheet
    public static String getDataByHeader(
            String testCaseId, String columnName) throws IOException {

        return readData(SHEET_NAME, testCaseId, columnName);
    }

    // Read data from Signup sheet
    public static String getSignupDataByHeader(
            String testCaseId, String columnName) throws IOException {

        return readData("Signup", testCaseId, columnName);
    }

    // Read invalid login data from Invalidlogin sheet
    public static String getInvalidLoginDataByHeader(
            String testCaseId, String columnName) throws IOException {

        return readData("Invalidlogin", testCaseId, columnName);
    }

    // Read checkout data from Validcheckout sheet
    public static String getCheckoutDataByHeader(
            String testCaseId, String columnName) throws IOException {

        return readData("Validcheckout", testCaseId, columnName);
    }

    // Common method to read Excel data
    private static String readData(
            String sheetName,
            String testCaseId,
            String columnName) throws IOException {

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IOException(
                        "Sheet not found: " + sheetName);
            }

            DataFormatter formatter = new DataFormatter();

            Row headerRow = sheet.getRow(0);

            if (headerRow == null) {
                throw new IOException(
                        "Excel header row is missing in sheet: "
                                + sheetName);
            }

            int idColumn = -1;
            int targetColumn = -1;

            // Find column positions using header names
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

            if (idColumn == -1) {
                throw new IOException(
                        "TC_ID column not found in sheet: "
                                + sheetName);
            }

            if (targetColumn == -1) {
                throw new IOException(
                        "Column not found: " + columnName
                                + " in sheet: " + sheetName);
            }

            // Find the required test case ID
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
                                        + " in test case: "
                                        + testCaseId);
                    }

                    return value;
                }
            }

            throw new IOException(
                    "Test case not found: " + testCaseId
                            + " in sheet: " + sheetName);
        }
    }
}
