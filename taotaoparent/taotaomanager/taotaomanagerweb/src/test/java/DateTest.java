import org.joda.time.DateTime;
import org.junit.Test;

public class DateTest {
    @Test
    public void test(){
        String imagePath = new DateTime().toString("/yyyy/MM/dd");
        System.out.println(imagePath);
    }
}
