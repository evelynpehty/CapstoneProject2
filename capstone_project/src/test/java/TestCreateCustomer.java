import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.objects.ScannerManager;
import com.pages.CreateCustomer;

public class TestCreateCustomer {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        // Redirect the standard output to capture printed messages
        System.setOut(new PrintStream(outContent));
    }
    
    @Test
    public void TestCreateCustomer_HappyPath() {
        // Simulate invalid user input ("Invalid") followed by valid input "N" to trigger the "Invalid choice. Please enter either Y or N" message.
        String input = "2000-12-19\nT0000005B\nEvelyn\nPeh\nF\n91234567\npehtingyu@gmail.com\nSG\nY";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Capture the output for testing
        ScannerManager.setScanner(new Scanner(System.in));

        // Call the method and assert the result
        CreateCustomer.show();
       
        String expectedOutput = "CreateNewCustomer\n" +
        "EnterDOB(YYYY-MM-DD):\n" +
        "EnterNRIC:\n" +
        "Enterfirstname:\n" +
        "Enterlastname:\n" +
        "EnterGender(M/F):\n" +
        "Enterphonenumber:\n" +
        "Enteremail:\n" +
        "Enternationality:\n" +
        "CustomerDetails\n" +
        "+---+---------------------+---------------------+\n" +
        "|1|DateofBirth|2000-12-19|\n" +
        "|2|NRIC|T0000005B|\n" +
        "|3|FirstName|Evelyn|\n" +
        "|4|LastName|Peh|\n" +
        "|5|Gender|Female|\n" +
        "|6|Phone|91234567|\n" +
        "|7|Email|pehtingyu@gmail.com|\n" +
        "|8|Nationality|SG|\n" +
        "+---+---------------------+---------------------+\n" +
        "KeyYtoconfirmcreation,Ntocancelcreationor(1-8)toeditfields:CustomerCreatedSuccessfully!";
    
        // Remove ANSI escape codes and whitespaces from actual and expected output
        String actualOutput = outContent.toString().replaceAll("\u001B\\[\\d+m", "").replaceAll("\\s", "");
        expectedOutput = expectedOutput.replaceAll("\\s", "");

        // Use assertEquals to compare the modified strings
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void TestCreateCustomer_InvalidAge() {
        String input = "2020-12-12";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Capture the output for testing
        ScannerManager.setScanner(new Scanner(System.in));

        // Call the method and assert the result
        CreateCustomer.show();
       
        String expectedOutput = "Create New Customer Enter DOB (YYYY-MM-DD): Unable to Create Customer. He/she must be at least 18 years old.";
        String actualOutput = outContent.toString();

        expectedOutput = expectedOutput.replaceAll("\u001B\\[\\d+m|\\s", "");
        actualOutput = actualOutput.replaceAll("\u001B\\[\\d+m|\\s", "");

        // Use assertEquals to compare the modified strings
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void TestCreateCustomer_EXistingCustomer() {
        String input = "2003-12-12\nA1234567z\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Capture the output for testing
        ScannerManager.setScanner(new Scanner(System.in));

        // Call the method and assert the result
        CreateCustomer.show();
       
        String expectedOutput = "Create New Customer Enter DOB (YYYY-MM-DD): Enter NRIC: Customer Exists!";
        String actualOutput = outContent.toString();

        // Remove ANSI color codes from both strings before comparison
        expectedOutput = expectedOutput.replaceAll("\u001B\\[\\d+m|\\s", "");
        actualOutput = actualOutput.replaceAll("\u001B\\[\\d+m|\\s", "");

        // Use assertEquals to compare the modified strings
        assertEquals(expectedOutput, actualOutput);
    }
}
