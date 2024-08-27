package day4_1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PrimeStream {
    public static void main(String[] args) {
        List<Integer> integerList = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11);

        List<Integer> primeNums = integerList.stream()
                .filter(n -> {
                    if (n <= 1) return false;
                    for (int i = 2; i < n; i++) {
                        if (n % i == 0) return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());

        System.out.println("Primes: " + primeNums);
    }
}