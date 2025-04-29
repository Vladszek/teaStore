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

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Processing proc = new Processing();
        String choice = "";
        boolean isEnd = false;

        proc.feelList();
        while (!isEnd) {
            System.out.println("\nВыберите пункт меню: ");
            System.out.println("[1] Определить самый урожайный год для каждого сорта");
            System.out.println("[2] Определить массу самого крупного пакета для каждого сорта");
            System.out.println("[3] Найти сорта собранные в 2018 году");
            System.out.println("[Любой иной символ] Выход");

            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    proc.getMostProductiveYear();
                    break;
                case "2":
                    proc.getHeaviestPackages();
                    break;
                case "3":
                    Set<String> sortsSet = proc.getSortsFrom2018Year();
                    System.out.println("Сорта чая, собранные в 2018г: ");
                    for (String sort : sortsSet) {
                        System.out.println(sort);
                    }
                    break;
                default:
                    isEnd = true;
                    break;
            }
        }
    }

}