import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        ColleaguesRepository colleaguesRepository = new ColleaguesRepository();

        colleaguesRepository.readColleaguesFromCSV();;
        colleaguesRepository.writeColleaguesToExcelFile();


        colleaguesRepository.sortColleagues();


    }

}
