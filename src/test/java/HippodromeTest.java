import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    @Test
    void constructorShouldThrowExceptionWhenArgumentIsNull() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));

        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void constructorShouldThrowExceptionWhenListIsEmpty() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Hippodrome(Collections.EMPTY_LIST));

        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorsesShouldReturnEqualListAsInConstructor() {
        List<Horse> expected = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            expected.add(new Horse("Horse" + i, i));
        }
        Hippodrome hippodrome = new Hippodrome(expected);
        List<Horse> actual = hippodrome.getHorses();

        assertEquals(expected, actual);
    }

    @Test
    void moveCallsForAllListElements() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse horse : horses) {
            Mockito.verify(horse, Mockito.times(1)).move();
        }
    }

    @Test
    void getWinnerShouldReturnHorseWithMaxDistance() {
        List<Horse> horses = List.of(
                new Horse("minHorse", 1, 1),
                new Horse("midHorse", 1, 2),
                new Horse("maxHorse", 1, 3)
        );
        Hippodrome hippodrome = new Hippodrome(horses);
        Horse actual = hippodrome.getWinner();

        assertEquals("maxHorse", actual.getName());
    }
}