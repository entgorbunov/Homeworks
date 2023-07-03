import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CollectionsHomework1 {
    public static int[] findSumIndices(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] + array[j] == target) {
                    return new int[]{i, j};
                }
            }
        }

        return new int[0];
    }

    public static int[] findSumIndices2(int[] array, int target) {
        Arrays.sort(array); // Сортировка массива

        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            int sum = array[left] + array[right];

            if (sum < target) {
                left++; // Увеличение указателя начала массива
            } else if (sum > target) {
                right--; // Уменьшение указателя конца массива
            } else {
                return new int[]{left, right};
            }
        }

        return new int[0];
    }

    public static int[] findSumIndices3(int[] array, int target) {
        Map<Integer, Integer> complementMap = new HashMap<>(); // Карта для хранения чисел и их индексов

        for (int i = 0; i < array.length; i++) {
            int complement = target - array[i]; // Вычисляем комплемент для текущего числа

            // Если комплемент уже есть в карте, возвращаем его индекс и текущий индекс
            if (complementMap.containsKey(complement)) {
                return new int[]{complementMap.get(complement), i};
            }

            // Добавляем текущее число и его индекс в карту
            complementMap.put(array[i], i);
        }

        // Если нет пары чисел, возвращаем пустой массив
        return new int[0];
    }

    public static void main(String[] args) {
        int[] array = {3, 8, 12, 7};
        int target = 11;

        int[] result = findSumIndices3(array, target);

        System.out.println("Result: [" + result[0] + ", " + result[1] + "]");
    }




}
