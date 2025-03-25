import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class UserStatusFileTest {
    private static final String TEST_FILE = "TestUserStatus.csv";
    private static final String USER1 = "user1";
    private static final String USER2 = "user2";
    private static final String USER3 = "user3";

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILE));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILE));
    }

    @Test
    void testSaveUserStatus() {
        // Test blocking
        ChatServer.saveUserStatus(USER1, USER2, true);
        assertTrue(ChatServer.isUserBlocked(USER1, USER2));
        
        // Test unblocking
        ChatServer.saveUserStatus(USER1, USER2, false);
        assertFalse(ChatServer.isUserBlocked(USER1, USER2));
    }

    @Test
    void testLoadUserStatus() {
        // Create test data
        String testData = USER1 + "," + USER2 + "\n" + USER2 + "," + USER3;
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(TEST_FILE))) {
            writer.write(testData);
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        // Test loading
        Map<String, Set<String>> status = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String username = parts[0];
                    status.put(username, new HashSet<>(Arrays.asList(parts).subList(1, parts.length)));
                }
            }
        } catch (IOException e) {
            fail("Failed to read test file");
        }

        assertEquals(2, status.size());
        assertTrue(status.get(USER1).contains(USER2));
        assertTrue(status.get(USER2).contains(USER3));
    }

    @Test
    void testIsUserBlocked() {
        // Setup test data
        ChatServer.saveUserStatus(USER1, USER2, true);
        
        // Test blocking status
        assertTrue(ChatServer.isUserBlocked(USER1, USER2));
        assertFalse(ChatServer.isUserBlocked(USER1, USER3));
    }
}