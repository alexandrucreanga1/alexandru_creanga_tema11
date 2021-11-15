import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class ColleaguesRepository {


    List<Colleagues> colleaguesList = new ArrayList<>();
    List<Colleagues> groupedByMonthList = new ArrayList<>();


    public void readFilterSortWriteFromCSV(String inputFileNameCSV,int monthNumber, String outputFileName) throws IOException {
        Path fileIn = new File("resources/"+inputFileNameCSV+".csv").toPath();
        System.out.println(fileIn.toAbsolutePath());
        try (BufferedReader reader = Files.newBufferedReader(fileIn)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                colleaguesList.add(getColleaguesFromCsvLine(line));
            }
        } catch (IOException x) {
            System.out.println("IOException: " + x);
        }
        System.out.println(colleaguesList.size() + " colleagues.");

        sortColleagues(monthNumber, outputFileName);

    }

    public Colleagues getColleaguesFromCsvLine(String line) {
        String[] tokens = line.split(",");
        if (tokens.length != 3) {
            throw new IllegalArgumentException();
        }

        if(parseInt(tokens[2].trim().substring(0,2)) > 31) {
            throw new IllegalArgumentException("Please take into consideration that month cannot have more than 31 days");
        }

        if(parseInt(tokens[2].trim().substring(0,2)) < 1) {
            throw new IllegalArgumentException("Please take into consideration that month cannot have 0 days or negative");
        }


        String firstName = tokens[0].trim();
        String lastName = tokens[1].trim();
        String dateOfBirth = tokens[2].trim();

        return new Colleagues(firstName, lastName, dateOfBirth);
    }


    private void sortColleagues(int monthNumber, String outputFileName) throws IOException {
        if((monthNumber < 1) || (monthNumber > 12)) {
            throw new IllegalArgumentException("Month Number have to be not greater than 12 or lower than 1!");
        }

        groupedByMonthList = colleaguesList.stream()
                .filter(colleagues -> parseInt(colleagues.dateOfBirth.substring(3,5)) == monthNumber)
              //  .filter(colleagues -> colleagues.dateOfBirth.substring(3,5).equals(monthNumber))
                .sorted(Comparator.comparing(Colleagues::getFirsName))
                .collect(Collectors.toList());

        writeColleaguesToExcelFile(outputFileName);
        System.out.println(groupedByMonthList);
    }


    private void writeColleaguesToExcelFile( String outputFileName) throws IOException {

        //Create Workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        // spreadsheet object
        XSSFSheet spreadsheet = workbook.createSheet("Colleagues");
        int rownum = 0;
        for (Colleagues colleagues : groupedByMonthList) {
            Row row = spreadsheet.createRow(rownum++);
            int columnCount = 0;
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(colleagues.getFirsName());
            cell = row.createCell(columnCount++);
            cell.setCellValue(colleagues.getLastName());


        }

        FileOutputStream out = new FileOutputStream("resources/"+outputFileName+".xlsx");
        workbook.write(out);
        out.close();

    }


}
