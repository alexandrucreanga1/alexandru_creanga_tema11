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

public class ColleaguesRepository {


    List<Colleagues> colleaguesList = new ArrayList<>();
    List<Colleagues> groupedByMonthList = new ArrayList<>();


    public void readColleaguesFromCSV() {
        Path fileIn = new File("resources/colleaguesIN.csv").toPath();
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
    }

    public Colleagues getColleaguesFromCsvLine(String line) {
        String[] tokens = line.split(",");
        if (tokens.length != 3) {
            throw new IllegalArgumentException();
        }
        String firstName = tokens[0].trim();
        String lastName = tokens[1].trim();
        String dateOfBirth = tokens[2].trim();

        return new Colleagues(firstName, lastName, dateOfBirth);
    }


    public void sortColleagues() {
        List<Colleagues> collectColleagues = colleaguesList.stream()
                //   .filter(colleagues -> colleagues.getFirsName().startsWith("U"))
              //  .filter(colleagues -> parseInt(colleagues.dateOfBirth.substring(3,5)) == 2)
                .filter(colleagues -> colleagues.dateOfBirth.substring(3,5).equals("10"))
                .sorted(Comparator.comparing(Colleagues::getFirsName))
                .collect(Collectors.toList());
        System.out.println(collectColleagues);
    }


    public void writeColleaguesToExcelFile() throws IOException {

        //Create Workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        // spreadsheet object
        XSSFSheet spreadsheet = workbook.createSheet("Colleagues");
        int rownum = 0;
        for (Colleagues colleagues : colleaguesList) {
            Row row = spreadsheet.createRow(rownum++);
            int columnCount = 0;
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(colleagues.getFirsName());
            cell = row.createCell(columnCount++);
            cell.setCellValue(colleagues.getLastName());
            cell = row.createCell(columnCount++);
            cell.setCellValue(colleagues.getDateOfBirth());


        }

        FileOutputStream out = new FileOutputStream("resources/colleaguesOUT.xlsx");
        workbook.write(out);
        out.close();

    }


}
