//Булгаков В. Г. 1к. 14гр.
//Приложение для анализа производства и поставке чая.
//Чай поствляется в пакетах, где указаны: сорт чая, год сбора урожая и масса.
//Данные о пакетах в количестве 10000 генерируются автоматически
//Необходимо вывести следующую информацию:
// Самый урожайный год для каждого вида чая
// Массу самого крупного пакета для каждого сорта чая
// Сорта чая, собранные в 2018 году

import service.Processing;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Processing proc = new Processing();
        String choice;
        boolean isEnd = false;

        proc.feelList();
        while (!isEnd) {
            System.out.println("\nВыберите пункт меню: ");
            System.out.println("[1] Определить самый урожайный год для каждого сорта");
            System.out.println("[2] Определить массу самого крупного пакета для каждого сорта");
            System.out.println("[3] Посмотреть статистику по выбранному году");
            System.out.println("[Любой иной символ] Выход");

            choice = sc.nextLine();
            logger.info("Выбран пункт меню: {}", choice);
            switch (choice) {
                case "1" -> {
                    Map<String, Integer> sortsSet = proc.getMostProductiveYear();
                    for (Map.Entry<String, Integer> sort : sortsSet.entrySet()) {
                        System.out.printf("%s, самый урожайный год: %d\n", sort.getKey(), sort.getValue());
                    }
                }
                case "2" -> {
                    Map<String, Double> sortsSet = proc.getHeaviestPackages();
                    for (Map.Entry<String, Double> sort : sortsSet.entrySet()) {
                        System.out.printf("%s, максимальная масса: %f\n", sort.getKey(), sort.getValue());
                    }
                }
                case "3" -> {
                    System.out.println("Выберите год");
                    try {
                        int year = sc.nextInt();
                        sc.nextLine();
                        logger.info("Выбран год урожая: {}", year);
                        Map<String, Double> sortsSet = proc.getSortsFromSetYear(year);
                        System.out.printf("Сорта чая, собранные в %d году: \n", year);
                        for (Map.Entry<String, Double> sort : sortsSet.entrySet()) {
                            System.out.printf("%s, общая масса: %.2f\n", sort.getKey(), sort.getValue());
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Ошибка ввода");
                        logger.warn("Неверно указан год");
                    }

                }
                default -> isEnd = true;
            }
        }
    }

}