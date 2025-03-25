import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class UserDataFileTest {
    private static final String TEST_FILE = "TestUserData.txt";
    private static final String TEST_USER = "testuser";
    private static final String TEST_PASS = "testpass";

    @BeforeEach
    void setUp() throws IOException {
        // Ensure clean test file
        Files.deleteIfExists(Paths.get(TEST_FILE));
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clean up after tests
        Files.deleteIfExists(Paths.get(TEST_FILE));
    }

    @Test
    void testLoadUserData() {
        // Create test data
        String testData = TEST_USER + ":" + TEST_PASS + "\nuser2:pass2";
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(TEST_FILE))) {
            writer.write(testData);
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        // Test loading
        Map<String, String> users = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    users.put(parts[0].toLowerCase(), parts[1]);
                }
            }
        } catch (IOException e) {
            fail("Failed to read test file");
        }

        assertEquals(2, users.size());
        assertEquals(TEST_PASS, users.get(TEST_USER));
    }

    @Test
    void testSaveUserData() {
        // Test saving
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE, true))) {
            writer.write(TEST_USER + ":" + TEST_PASS);
            writer.newLine();
        } catch (IOException e) {
            fail("Failed to write to test file");
        }

        // Verify content
        try {
            List<String> lines = Files.readAllLines(Paths.get(TEST_FILE));
            assertTrue(lines.contains(TEST_USER + ":" + TEST_PASS));
        } catch (IOException e) {
            fail("Failed to verify test file");
        }
    }

    @Test
    void testRegisterUser() {
        // Test registration
        assertTrue(ChatServer.registerUser("test123", "testpass"));
        
        // Verify duplicate registration fails
        assertFalse(ChatServer.registerUser(TEST_USER, "anotherpass"));
    }

    @Test
    void testLoginUser() {
        // Setup test user
        ChatServer.registerUser(TEST_USER, TEST_PASS);
        
        // Test valid login
        assertTrue(ChatServer.loginUser(TEST_USER, TEST_PASS));
        
        // Test invalid login
        assertFalse(ChatServer.loginUser(TEST_USER, "wrongpass"));
        assertFalse(ChatServer.loginUser("nonexistent", TEST_PASS));
    }
}