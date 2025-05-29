package service;

import models.TeaPackage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProcessingTest {

    private Processing processing;

    @BeforeEach
    public void setUp() {
        processing = new Processing();
        List<TeaPackage> mockList = Arrays.asList(
                new TeaPackage("Keemun", 2022, 10.0),
                new TeaPackage("Huangshan Maofeng", 2020, 12.5),
                new TeaPackage("Huangshan Maofeng", 2019, 15.5),
                new TeaPackage("Lu'an Quapian", 2022, 10.0),
                new TeaPackage("Keemun", 2022, 16.0)
        );
        injectTeaPackages(processing, mockList);
    }

    private void injectTeaPackages(Processing processing, List<TeaPackage> list) {
        try {
            var field = Processing.class.getDeclaredField("teaPackages");
            field.setAccessible(true);
            field.set(processing, list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetSortsFromSetYear() {
        Map<String,Double> res = processing.getSortsFromSetYear(2022);
        assertEquals(2,res.size());
        assertEquals(26.0,res.get("Keemun"));
        assertEquals(10.0,res.get("Lu'an Quapian"));
    }

    @Test
    public void testGetSortsFromEmptyYear() {
        Map<String,Double> res = processing.getSortsFromSetYear(100);
        assertTrue(res.isEmpty());
    }

    @Test
    public void testNotEmptyFeelList() throws Exception {
        processing.feelList();
        List<TeaPackage> res = getList();
        assertNotNull(res);
        assertEquals(10000, res.size());
    }

    @Test
    public void testCorrectSortsFeelList() throws Exception {
        processing.feelList();
        List<TeaPackage> res = getList();
        Set<String> correctSorts = Set.of("Keemun", "Huangshan Maofeng", "Lu'an Quapian", "Tai Ping Hou Kui");
        for (TeaPackage tp : res) {
            assertTrue(correctSorts.contains(tp.getSort()), "Неверный сорт: " + tp.getSort());
            assertTrue(tp.getHarvestYear() >= 2010 && tp.getHarvestYear() < 2025, "Неверный год: " + tp.getHarvestYear());
            assertTrue(tp.getMas() >= 10 && tp.getMas() < 20, "Неверная масса: " + tp.getMas());
        }
    }

    private List<TeaPackage> getList() throws Exception {
        Field field = Processing.class.getDeclaredField("teaPackages");
        field.setAccessible(true);
        return (List<TeaPackage>) field.get(processing);
    }

    @Test
    public void testGetCorrectHeaviestPackages() {
        Map<String, Double> res = processing.getHeaviestPackages();
        assertEquals(16.0,res.get("Keemun"));
        assertEquals(10.0,res.get("Lu'an Quapian"));
        assertEquals(15.5,res.get("Huangshan Maofeng"));
    }

    @Test
    public void testGetCorrectMostProductiveYear() {
        Map<String, Integer> res = processing.getMostProductiveYear();
        assertEquals(2022,res.get("Keemun"));
        assertEquals(2022,res.get("Lu'an Quapian"));
        assertEquals(2019,res.get("Huangshan Maofeng"));
    }
}
