import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import com.objects.Account;

public class TestAccount {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(out));
    }

    @Test
    public void case01() {
        String expected = "Your SAVINGS account 1 balance is: 1200.0".replaceAll("\\s", "");

        Account account = new Account(1);
        account.viewBalance();
        String actual = out.toString().replaceAll("\\e\\[\\d+m", "").replaceAll("\\033\\[\\d*[mHJ]", "").replaceAll("\\s", "");

        assertEquals(expected, actual);
    }

    @Test
    public void case02() {
        String expected = "Successfully deposited $1000.1".replaceAll("\\s", "");

        String input = "1000.1";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Account account = new Account(1);
    }
}
