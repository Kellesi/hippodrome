import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HorseTest {

    private static final String NAME = "Horse";
    private static final int POSITIVE_ARGUMENT = 1;
    private static final int NEGATIVE_ARGUMENT = -1;

    @Test
    void constructorShouldThrowExceptionWhenFirstArgumentIsNull() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Horse(null, POSITIVE_ARGUMENT, POSITIVE_ARGUMENT));

        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\r"})
    void constructorShouldThrowExceptionWhenFirstArgumentIsBlank(String name) {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Horse(name, POSITIVE_ARGUMENT, POSITIVE_ARGUMENT));

        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void constructorShouldThrowExceptionWhenSecondArgumentIsNegative() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Horse(NAME, NEGATIVE_ARGUMENT, POSITIVE_ARGUMENT));

        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void constructorShouldThrowExceptionWhenThirdArgumentIsNegative() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Horse(NAME, POSITIVE_ARGUMENT, NEGATIVE_ARGUMENT));

        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getNameShouldReturnFirstArgumentFromConstructor() {
        String expected = NAME;
        Horse horse = new Horse(expected, POSITIVE_ARGUMENT);
        String actual = horse.getName();

        assertEquals(expected, actual);
    }

    @Test
    void getSpeedShouldReturnSecondArgumentFromConstructor() {
        double expected = POSITIVE_ARGUMENT;
        Horse horse = new Horse(NAME, expected);
        double actual = horse.getSpeed();

        assertEquals(expected, actual);
    }

    @Test
    void getDistanceShouldReturnThirdArgumentFromConstructor() {
        double expected = POSITIVE_ARGUMENT;
        Horse horse = new Horse("Name", POSITIVE_ARGUMENT, expected);
        double actual = horse.getDistance();

        assertEquals(expected, actual);
    }

    @Test
    void getDistanceShouldReturnZeroIfUsedConstructorWithTwoArguments() {
        double expected = 0;
        Horse horse = new Horse(NAME, POSITIVE_ARGUMENT);
        double actual = horse.getDistance();

        assertEquals(expected, actual);
    }

    @Test
    void moveCallsGetRandomDoubleWithDefinedParams() {
        Horse horse = new Horse(NAME, POSITIVE_ARGUMENT, POSITIVE_ARGUMENT);
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horse.move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.5, 0.9})
    void moveUsesCorrectFormula(double pseudoRandom) {
        double distance = 2;
        double speed = 2;
        Horse horse = new Horse(NAME, distance, speed);
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            Mockito.when(Horse.getRandomDouble(0.2, 0.9)).thenReturn(pseudoRandom);
            horse.move();
            double actual = horse.getDistance();

            assertEquals(distance + speed * pseudoRandom, actual);
        }
    }
}