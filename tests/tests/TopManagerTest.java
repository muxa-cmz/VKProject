package tests;

import entity.TopPosition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.TopManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by valer on 03.12.2015.
 */
public class TopManagerTest {
    private List<String> items;
    List<TopPosition> expected;
    List<TopPosition> actual;

    @Before
    public void setUp() throws Exception {
        items = new ArrayList<String>();
        items.add("сплин");
        items.add("сплин");
        items.add("сплин");
        items.add("noize mc");
        items.add("noize mc");
        items.add("сплин");
        items.add("сплин");
        items.add("сплин");
        items.add("земфира");
        items.add("земфира");
        items.add("noize mc");
        items.add("noize mc");

        expected = new ArrayList<TopPosition>();
        expected.add(new TopPosition("сплин", 6));
        expected.add(new TopPosition("noize mc", 4));
        expected.add(new TopPosition("земфира", 2));

        actual = new ArrayList<TopPosition>();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetTop() throws Exception {
        actual.addAll(TopManager.getTop(items));
        for (int i = 0; i < 3; i++) {
            assertEquals(expected.get(i).getTitle(), actual.get(i).getTitle());
            assertEquals(expected.get(i).getCount(), actual.get(i).getCount());
        }
    }
}