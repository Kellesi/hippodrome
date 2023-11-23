import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    @Timeout(22)
    @Disabled
    void mainShouldFailAfter22Seconds() throws Exception {
        Main.main(new String[]{});
    }
}