import java.io.IOException;

//By using streams and lamba expressions, implement a small application which:
//
//        1.load from a file a structure like
//
//        first name, last name, date of birth
//
//        2. write back another file containing only
//
//        first name, last name
//
//        ordered alphabetically for all the of all matches which have the birthday on a month indicated.
//
//
//
//        Application takes 3 params:
//
//        1. input filename
//
//        2. target month (1-12)
//
//        3. output file name
//
//        Use unit tests to check the correctness.



public class Main {

    public static void main(String[] args) throws IOException {

        ColleaguesRepository colleaguesRepository = new ColleaguesRepository();


        // loading data from CSV >>>>>>>>>>>>
        colleaguesRepository.readFilterSortWriteFromCSV("colleaguesforeading",10,
                "outputColleaguesFileInExcelversion2");

    }

}
