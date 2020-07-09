import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class WWAMassTest {

    @ParameterizedTest
    @MethodSource("dataForCut")
    public void checkCutMass(int[] result, int[] array) {
        Assertions.assertArrayEquals(result, WorkWithArr.cutArray(array));
    }

    //Можно через поток аргументов данные передать
    public static Stream<Arguments> dataForCut() throws RuntimeException {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[]{2, 3, 5}, new int[]{1, 2, 3, 4, 2, 3, 5}));
        out.add(Arguments.arguments(new int[]{6, 7, 8, 5}, new int[]{1, 2, 3, 4, 2, 3, 4, 6, 7, 8, 5}));
        out.add(Arguments.arguments(new int[]{2, 3, 6, 2, 3, 5}, new int[]{4, 2, 3, 6, 2, 3, 5}));
        return out.stream();
    }

    //Можно через коллекцию данные передать
//    public static Collection<Object> dataForCut() {
//        return Arrays.asList(new Object[][]{
//                {new int[]{2, 3, 5}, new int[]{1, 2, 3, 4, 2, 3, 5}},
//                {new int[]{6, 7, 8, 5}, new int[]{1, 2, 3, 4, 2, 3, 4, 6, 7, 8, 5}},
//                {new int[]{2, 3, 6, 2, 3, 5}, new int[]{4, 2, 3, 6, 2, 3, 5}}
//        });
//    }

    @ParameterizedTest
    @MethodSource("dataForEx")
    public void checkCutMassEx(int[] array) {
        Assertions.assertThrows(RuntimeException.class, () -> WorkWithArr.cutArray(array));
    }

    //Можно через поток объектов данные передать
    public static Stream<Object> dataForEx() throws RuntimeException {
        List<Object> out = new ArrayList<>(Arrays.asList(
                new int[]{3,5,6,8},
                new int[]{1,2,5,3,6,7},
                new int[]{12,45,65,33,7},
                new int[]{6,5,7,3,2}
                ));
        return out.stream();
    }

    @ParameterizedTest
    @MethodSource("dataForOneFourT")
    public void checkOneFourMassT(int[] array) {
        Assertions.assertTrue(WorkWithArr.checkOneFourArr(array));
    }

    //Можно через поток аргументов данные передать
//    public static Stream<Arguments> dataForOneFourT() throws RuntimeException {
//        List<Arguments> out = new ArrayList<>();
//        out.add(Arguments.arguments(new int[]{1, 4, 4, 1, 1, 4, 1, 4, 1}));
//        out.add(Arguments.arguments(new int[]{1, 4, 4}));
//        out.add(Arguments.arguments(new int[]{1, 1, 4, 1, 4, 1}));
//        return out.stream();
//    }

    //Можно через коллекцию данные передать
    public static Collection<Object> dataForOneFourT() {
        return Arrays.asList(
        new int[]{1, 4, 4, 1, 1, 4, 1, 4, 1},
        new int[]{1, 4, 4},
        new int[]{1, 1, 4, 1, 4, 1}
        );
    }

    @ParameterizedTest
    @MethodSource("dataForOneFourF")
    public void checkOneFourMassF(int[] array) {
        Assertions.assertFalse(WorkWithArr.checkOneFourArr(array));
    }

    public static Collection<Object> dataForOneFourF() {
        return Arrays.asList(
                new int[]{1, 1, 1, 1, 1},
                new int[]{4, 4, 4, 4, 4},
                new int[]{1, 1, 4, 1, 4, 1, 3}
        );
    }
}
