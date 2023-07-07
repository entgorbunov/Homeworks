package com.sasha.hometasks.collections;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CollectionsHomeWork2 {
    public static boolean isThereDuplicates(int[] array) {
        Set<Integer> set = new HashSet<>();
        for (int element : array) {
            if (set.contains(element)) {
                return true;
            }
            set.add(element);
        }
        return false;
    }

    public static boolean isThereDuplicates2(int[] array) {
        Arrays.sort(array);
        for (int i = 0; i < array.length; i++) {
            if (array[i] == array[i + 1]) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] array = {4,5,6,6,8};


        boolean result = isThereDuplicates(array);

        if (result) {
            System.out.printf("Дубликаты есть");
        } else {
            System.out.printf("Дубликатов нет");
        }
    }




}
