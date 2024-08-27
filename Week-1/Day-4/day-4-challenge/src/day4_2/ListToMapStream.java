package day4_2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListToMapStream {
        public static void main(String[] args) {
            List<City> cityList = new ArrayList<>();
            cityList.add(new City("Tokyo", 70000));
            cityList.add(new City("Amsterdam", 50000));

            Map<String, Integer> cityMap = cityList.stream().collect(Collectors.toMap(City::getCityName, City::getPopulation));

            for (Map.Entry<String, Integer> entry : cityMap.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }

        }
}

