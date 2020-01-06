package loadDatabasParts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParseDatabasToTävlingTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    void transformDateTest() {
        assertEquals("1810-11-01", ParseDatabasToTävling.transformDate("1810-11-01 00:00:00.000000"));
        assertEquals( "1800-01-01", ParseDatabasToTävling.transformDate(null));
    }
}