import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static final String FILE_URL = "C:\\Users\\ekste\\Downloads\\clocking.csv";
    public static final String OUTPUT_URL = "C:\\Users\\ekste\\Downloads\\clocking12.xlsx";

    public static void main(String[] args) throws IOException {
        Workbook workbook = createWorkBook();

        // TODO: #1 create logic to iterate through the data and to create sheets for each month with headings

        Sheet sheet = createSheet(workbook, "August");
        createHeadings(sheet);

        // TODO: #2 create logic to add data to month sheet in cols

        FileOutputStream outputStream = new FileOutputStream(OUTPUT_URL);

        List<DataRow> dataRows = mapCsvToDataRow();
        List<DataRow> combinedDatesTimesList = combineDatesTimes(dataRows).stream().distinct().collect(Collectors.toList());
        List<DataRow> removeDuplicates = removeDuplicates(combinedDatesTimesList);

        // TODO: use below list
        List<DataRow> collect = removeDuplicates.stream().filter(e -> e.getDate().getMonthValue() == 8).collect(Collectors.toList());

        int rowCounter = 1;
        for (DataRow r : removeDuplicates) {
            if(r.getDate().getMonthValue()==8){
                Row row = sheet.createRow(rowCounter);
                Cell date = row.createCell(0);
                date.setCellValue(r.getDate().toString());
                Cell surname = row.createCell(1);
                surname.setCellValue(r.getSurname());
                Cell name = row.createCell(2);
                name.setCellValue(r.getName());
                Cell timeIn = row.createCell(3);
                timeIn.setCellValue(r.getInTime().toString());
                Cell timeOut = row.createCell(4);
                timeOut.setCellValue(r.getOutTime().toString());

                rowCounter++;
            }
        }
        workbook.write(outputStream);


        workbook.close();
        System.out.println(removeDuplicates.size());
    }

    private static List<DataRow> removeDuplicates(List<DataRow> combinedDatesTimesList) throws IOException {
        List<DataRow> result = new ArrayList<>();

        for (int i = 0; i < combinedDatesTimesList.size() - 1; i++) {
            DataRow thisRow = combinedDatesTimesList.get(i);
            DataRow nextRow = combinedDatesTimesList.get(i + 1);

            if (!thisRow.getDate().isEqual(nextRow.getDate()))
                result.add(thisRow);
        }

        result.sort(Comparator.comparing(DataRow::getDate));



        return result;
    }

    private static List<DataRow> combineDatesTimes(List<DataRow> dataRows) {
        List<DataRow> result = new ArrayList<>();

        for (int i = 0; i < dataRows.size() - 1; i++) {
            for (int j = 0; j < dataRows.size() - 1; j++) {
                if (dataRows.get(i).getDate().isEqual(dataRows.get(j).getDate())
                        && dataRows.get(i).getSurname().equals(dataRows.get(j).getSurname())
                        && dataRows.get(i).getName().equals(dataRows.get(j).getName())) {
                    if (dataRows.get(j).getInTime().isBefore(dataRows.get(i).getInTime())) {
                        dataRows.get(i).setInTime(dataRows.get(j).getInTime());
                    }
                    if (dataRows.get(j).getOutTime().isAfter(dataRows.get(i).getOutTime())) {
                        dataRows.get(i).setOutTime(dataRows.get(j).getOutTime());
                    }

                    result.add(dataRows.get(i));
                }
            }
        }

        return result;
    }

    private static List<DataRow> mapCsvToDataRow() throws IOException {
        List<DataRow> dataRows = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(FILE_URL));
        String line = reader.readLine();

        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            String[] fields = line.split(",");

            DataRow row = new DataRow();
            row.setDate(convertToLocalDate(fields[1]));
            row.setName(fields[4]);
            row.setSurname(fields[5]);
            row.setInTime(convertToLocalTime(fields[2]));
            row.setOutTime(convertToLocalTime(fields[2]));
            dataRows.add(row);
        }

        dataRows.sort(Comparator.comparing(DataRow::getName));
        return dataRows;
    }

    private static LocalDate convertToLocalDate (String date) {
        return LocalDate.of(Integer.parseInt(date.substring(6, 10)), Integer.parseInt(date.substring(3, 5)), Integer.parseInt(date.substring(0, 2)));
    }

    private static LocalTime convertToLocalTime (String time) {
        return LocalTime.of(Integer.parseInt(time.substring(0, 2)), Integer.parseInt(time.substring(3, 5)));
    }

    private static Workbook createWorkBook () throws IOException {
        return new HSSFWorkbook();
    }

    private static Sheet createSheet(Workbook workbook, String name) {
        return workbook.createSheet(name);
    }

    private static void createHeadings(Sheet sheet) {
        Row row = sheet.createRow(0);
        Cell cellDate = row.createCell(0);
        cellDate.setCellValue("Date");
        Cell cellSurname = row.createCell(1);
        cellSurname.setCellValue("Surname");
        Cell cellName = row.createCell(2);
        cellName.setCellValue("Name");
        Cell cellTimeIn = row.createCell(3);
        cellTimeIn.setCellValue("Time In");
        Cell cellTimeOut = row.createCell(4);
        cellTimeOut.setCellValue("Time Out");
    }
}
