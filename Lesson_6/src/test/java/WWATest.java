import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//Класс тестов без параметров
public class WWATest {
    @Test
    public void checkCut() {
        Assertions.assertArrayEquals(new int[]{2, 3, 5}, WorkWithArr.cutArray(new int[]{1, 2, 3, 4, 2, 3, 5}));
        Assertions.assertArrayEquals(new int[]{6, 7, 8, 5}, WorkWithArr.cutArray(new int[]{1, 2, 3, 4, 2, 3, 4, 6, 7, 8, 5}));
        Assertions.assertArrayEquals(new int[]{2, 3, 6, 2, 3, 5}, WorkWithArr.cutArray(new int[]{4, 2, 3, 6, 2, 3, 5}));
    }

    @Test
    public void checkThrowEx() {
        Assertions.assertThrows(RuntimeException.class, () -> WorkWithArr.cutArray(new int[]{1, 2, 3, 5, 6, 78, 5, 7}));
    }

    @Test
    public void checkOneFour() {
        Assertions.assertTrue(WorkWithArr.checkOneFourArr(new int[]{1, 4, 4, 1, 1, 4, 1, 4, 1}));
        Assertions.assertTrue(WorkWithArr.checkOneFourArr(new int[]{1, 4, 4}));
        Assertions.assertTrue(WorkWithArr.checkOneFourArr(new int[]{1, 1, 4, 1, 4, 1}));
        Assertions.assertFalse(WorkWithArr.checkOneFourArr(new int[]{1, 1, 1, 1, 1}));
        Assertions.assertFalse(WorkWithArr.checkOneFourArr(new int[]{4, 4, 4, 4, 4}));
        Assertions.assertFalse(WorkWithArr.checkOneFourArr(new int[]{1, 1, 4, 1, 4, 1, 3}));
    }
}
