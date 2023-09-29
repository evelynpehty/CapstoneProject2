import org.junit.Before;
import org.junit.Test;

import com.objects.ScannerManager;
import com.pages.CreateCustomer;
import com.validations.MenuChoices;
import com.validations.chkDate;
import com.validations.chkDigits;
import com.validations.chkEmail;
import com.validations.chkText;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class TestValidation {

    @Test
    public void testCheckDateFormatValid() {
        // Test with a valid date format (YYYY-MM-DD)
        String validDateFormat = "2023-09-29";
        boolean result = chkDate.checkDateFormat(validDateFormat);
        assertTrue(result);
    }

    @Test
    public void testCheckDateFormatInvalid() {
        // Test with an invalid date format
        String invalidDateFormat = "29/09/2023";
        boolean result = chkDate.checkDateFormat(invalidDateFormat);
        assertFalse(result);
    }

    @Test
    public void testCheckDateValidityValid() {
        // Test with a valid date (2023-09-29)
        String validDate = "2023-09-29";
        boolean result = chkDate.checkDateValidity(validDate);
        assertTrue(result);
    }

    @Test
    public void testCheckDateValidityInvalid() {
        // Test with an invalid date (e.g., 2023-09-31, which doesn't exist)
        String invalidDate = "2023-12-32";
        boolean result = chkDate.checkDateValidity(invalidDate);
        assertFalse(result);
    }

    @Test
    public void testCheckDateValidityInvalidFormat() {
        // Test with an invalid date format (e.g., "2023/09/29")
        String invalidDateFormat = "2023/09/29";
        boolean result = chkDate.checkDateValidity(invalidDateFormat);
        assertFalse(result);
    }

    @Test
    public void testCheckTextValidInput() {
        // Test with a valid input containing only alphabetic characters
        String validInput = "John";
        boolean result = chkText.checkText(validInput);
        assertTrue(result);
    }

    @Test
    public void testCheckTextInValidInput() {
        // Test with a valid input containing only alphabetic characters
        String invalidInput = "John123123";
        boolean result = chkText.checkText(invalidInput);
        assertFalse(result);
    }

    @Test
    public void testCheckTextEmptyInput() {
        // Test with an empty input
        String emptyInput = "";
        boolean result = chkText.checkText(emptyInput);
        assertFalse(result);
    }

    @Test
    public void testCheckValidsEmailInput() {
        // Test with an empty input
        String validEmail = "eve@gmail.com";
        boolean result = chkEmail.checkEmailFormat(validEmail);
        assertTrue(result);
    }
    
    @Test
    public void testCheckInvalidsEmailInput() {
        // Test with an empty input
        String invalidEmail = "eve";
        boolean result = chkEmail.checkEmailFormat(invalidEmail);
        assertFalse(result);
    }

    @Test
    public void testCheckValidDigitsInput() {
        // Test with an empty input
        String validInput = "123";
        boolean result = chkDigits.checkDigits(validInput);
        assertTrue(result);
    }

    @Test
    public void testCheckInValidDigitsInput() {
        // Test with an empty input
        String invalidInput = "211ss";
        boolean result = chkDigits.checkDigits(invalidInput);
        assertFalse(result);
    }

    @Test
    public void testYesNoConfirmationYes() {
        // Simulate user input of "Y"
        String input = "Y\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Capture the output for testing
        ScannerManager.setScanner(new Scanner(System.in));

        // Call the method and assert the result
        boolean result = MenuChoices.yesnoConfirmation("Confirm? (Y/N): ");
        assertTrue(result);
    }

    @Test
    public void testYesNoConfirmationNo() {
        // Simulate user input of "N"
        String input = "N\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Capture the output for testing
        ScannerManager.setScanner(new Scanner(System.in));

        // Call the method and assert the result
        boolean result = MenuChoices.yesnoConfirmation("Confirm? (Y/N): ");
        assertFalse(result);
    }

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        // Redirect the standard output to capture printed messages
        System.setOut(new PrintStream(outContent));
    }
    @Test
    public void testYesNoConfirmationInvalidInput() {
        // Simulate invalid user input ("Invalid") followed by valid input "N" to trigger the "Invalid choice. Please enter either Y or N" message.
        String input = "Invalid\nN\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Capture the output for testing
        ScannerManager.setScanner(new Scanner(System.in));

        // Call the method and assert the result
        boolean result = MenuChoices.yesnoConfirmation("Confirm? (Y/N): ");
        assertFalse(result);
        
        // // Check if the "Invalid choice. Please enter either Y or N" message is printed along with the prompt
        // String expectedOutput = "Confirm? (Y/N): Invalid choice. Please enter either Y or N\nConfirm? (Y/N): ";
        // assertEquals(expectedOutput, outContent.toString());    
    }
}
