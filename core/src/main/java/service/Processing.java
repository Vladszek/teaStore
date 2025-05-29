package service;

import models.TeaPackage;

import java.util.*;

public class Processing {
    private static final int PACKAGE_NUM = 10000;
    private static final int MIN_YEAR = 2010;
    private static final int MAX_YEAR = 2025;
    private static final double MIN_WEIGHT = 10;
    private static final double MAX_WEIGHT = 20;
    private static final String[] teaSorts = new String[]{"Keemun", "Huangshan Maofeng", "Lu'an Quapian", "Tai Ping Hou Kui"};
    private List<TeaPackage> teaPackages;

    private String getRandomSort() {
        Random rnd = new Random();
        return teaSorts[rnd.nextInt(0, teaSorts.length)];
    }

    private int getRandomYear() {
        Random rnd = new Random();
        return rnd.nextInt(MIN_YEAR, MAX_YEAR);
    }

    private double getRandomMas() {
        Random rnd = new Random();
        return rnd.nextDouble(MIN_WEIGHT, MAX_WEIGHT);
    }

    private double getMasOfCurrentSortByYear(String sortName, int year) {
        double sum = 0;
        for (TeaPackage tp : teaPackages) {
            if (sortName.equals(tp.getSort()) && year == tp.getHarvestYear()) {
                sum += tp.getMas();
            }
        }
        return sum;
    }

    private int getMostProductiveYearByCurrentSort(String sortName) {
        int mostProductiveYear = MIN_YEAR;
        double maxProduction = 0;
        for (int i = MIN_YEAR; i < MAX_YEAR; i++) {
            double currentYearProduction = getMasOfCurrentSortByYear(sortName, i);
            if (maxProduction < currentYearProduction) {
                maxProduction = currentYearProduction;
                mostProductiveYear = i;
            }
        }
        return mostProductiveYear;
    }

    public Map<String, Double> getSortsFromSetYear(int year) {
        Map<String, Double> sorts = new HashMap<>();
        for (TeaPackage tp : teaPackages) {
            if(tp.getHarvestYear() == year) {
                sorts.merge(tp.getSort(), tp.getMas(), Double::sum);
            }
        }
        return sorts;
    }

    private double getHeaviestPackageOfCurrentSort(String sortName) {
        double maxMas = 0;
        for (TeaPackage tp : teaPackages) {
            if (sortName.equals(tp.getSort()) && tp.getMas() > maxMas) {
                maxMas = tp.getMas();
            }
        }
        return maxMas;
    }

    public void feelList() {
        teaPackages = new ArrayList<>();
        for (int i = 0; i < PACKAGE_NUM; i++) {
            teaPackages.add(new TeaPackage(getRandomSort(), getRandomYear(), getRandomMas()));
        }
    }

    public Map<String, Double> getHeaviestPackages() {
        Map<String, Double> sortsWithMass = new HashMap<>();
        for (String name : teaSorts) {
            double maxMas = getHeaviestPackageOfCurrentSort(name);
            sortsWithMass.put(name, maxMas);
        }
        return sortsWithMass;
    }

    public Map<String, Integer> getMostProductiveYear() {
        Map<String, Integer> sortsWithYear = new HashMap<>();
        for (String name : teaSorts) {
            int mostProductiveYear = getMostProductiveYearByCurrentSort(name);
            sortsWithYear.put(name, mostProductiveYear);
        }
        return sortsWithYear;
    }

}
