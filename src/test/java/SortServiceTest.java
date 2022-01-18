import com.services.SortService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SortServiceTest {
    private final SortService cut = new SortService();

    @Test
    void quickSortingTest() {
        int[] actual = cut.quickSort(new int[]{-1, 3, 24, -22}, 0, 3);
        int[] expected = new int[]{-22, -1, 3, 24};
        assertArrayEquals(expected, actual);
    }

    static Arguments[] quickSortingWithNullTestArgs() {
        return new Arguments[] {
                Arguments.arguments(new int[]{5, 6, 1, 0}, -2, 9),
                Arguments.arguments(new int[]{24, 24, 55}, 0, 5),
                Arguments.arguments(new int[] {}, 0, 0),
                Arguments.arguments(null, 1, 10)
        };
    }

    @ParameterizedTest
    @MethodSource("quickSortingWithNullTestArgs")
    void quickSortingNullTest(int[] arr, int low, int high) {
        int[] actual = cut.quickSort(arr, low, high);
        assertNull(actual);
    }
}
