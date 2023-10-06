import com.objects.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMock {

    private final InputStream originalIn = System.in;
    private ByteArrayInputStream testIn;
    private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        // Redirect the standard output to capture the printed output
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void tearDown() {
        // Restore the original input and output streams
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    public void testMethodWithInputGreaterThan100() {
        // Set up the input stream to simulate user input
        String input = "150\n"; // Input value greater than 100
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Create a mock Scanner
        Scanner mockScanner = new Scanner(System.in);

        // Call the method with the mock scanner
        Mock mock = new Mock();
        mock.test(mockScanner);

        // Capture the printed output (excluding the prompt)
        String output = testOut.toString().trim();
        String expectedOutput = "Please key in your value: Yes";

        // Assert the expected output
        assertEquals(expectedOutput, output);
    }
    

    
}
