import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class ColleaguesRepositoryTest extends TestCase {

    //object under Test>>>>
    ColleaguesRepository colleaguesRepository = new ColleaguesRepository();

    @Test
    public void testCSVInput () {
        //given (ce se da ca conditie de input)
        String inputLine = "Habib, Jonhson,11-10-2001";


        //when (aici e metoda propriu zisa)
        Colleagues colleagues = colleaguesRepository.getColleaguesFromCsvLine(inputLine);

        //then
        assert colleagues != null;
        Assert.assertEquals("Habib",colleagues.getFirsName());
        Assert.assertEquals("Jonhson",colleagues.getLastName());
        Assert.assertEquals("11-10-2001",colleagues.getDateOfBirth());

    }

    @Test
    public void testCSVInputWithSpaces () {
        //given (ce se da ca conditie de input)
        String inputLine = "Habib , Jonhson , 11-10-2001";


        //when (aici e metoda propriu zisa)
        Colleagues colleagues = colleaguesRepository.getColleaguesFromCsvLine(inputLine);


        //then
        assert colleagues != null;
        Assert.assertEquals("Habib",colleagues.getFirsName());
        Assert.assertEquals("Jonhson",colleagues.getLastName());
        Assert.assertEquals("11-10-2001",colleagues.getDateOfBirth());
    }


    @Test (expected = IllegalArgumentException.class)
    public void testMalformatedCSVInput () {
        //given (ce se da ca conditie de input)
        String inputLine = "Habib , , 11-10-2001";


        //when (aici e metoda propriu zisa)
        Colleagues result = colleaguesRepository.getColleaguesFromCsvLine(inputLine);


    }


    @Test (expected = IllegalArgumentException.class)
    public void testAnormalDayNumberCSVInput () {
        //given (ce se da ca conditie de input)
        String inputLine = "Habib , Jonhson , 56-10-2001";


        //when (aici e metoda propriu zisa)
        Colleagues result = colleaguesRepository.getColleaguesFromCsvLine(inputLine);


    }


}