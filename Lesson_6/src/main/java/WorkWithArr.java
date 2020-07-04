public class WorkWithArr {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 4, 5, 3, 5, 7, 3, 5, 7, 4, 3, 2, 6, 10, 8, 6};

        try {
            int[] arr2 = cutArray(arr);
            for (int e : arr2) {
                System.out.print(e + "\t");
            }
            System.out.println();
        } catch (RuntimeException e) {
            System.out.println("В исходном массиве отсутствуют 4-ки");
        }

        int[] arr3 = new int[]{1,1,1,1,1,1,1,4,2};
        System.out.println(checkOneFourArr(arr3));
    }

    public static int[] cutArray(int[] arr) throws RuntimeException {
        int size = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] == 4) {
                break;
            }
            size++;
            if (size == arr.length) {
                throw new RuntimeException();
            }
        }
        int[] arr2 = new int[size];
        for (int i = arr.length - 1, j = arr2.length - 1; j >= 0; i--, j--) {
            arr2[j] = arr[i];
        }
        return arr2;
    }

    public static boolean checkOneFourArr(int[] arr) {
        boolean isOne = false;
        boolean isFour = false;
        for (int value : arr) {
            switch (value) {
                case 1:
                    isOne = true;
                    break;
                case 4:
                    isFour = true;
                    break;
                default:
                    return false;
            }
        }
        return isOne && isFour;
    }
}
