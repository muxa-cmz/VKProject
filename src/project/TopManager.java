package project;

/**
 * Created by valer on 03.12.2015.
 */
import entity.TopPosition;

import java.util.*;

public class TopManager {

    public static List<TopPosition> getTop(List<String> items) {
        Map<String, Integer> topMap = new HashMap<String, Integer>();

        for (String item: items) {
            Integer number = topMap.get(item);
            topMap.put(item, number == null ? 1 : number + 1);
        }

        List<TopPosition> topList = new ArrayList<TopPosition>();

        for(Map.Entry<String, Integer> entry : topMap.entrySet()) {
            String title = entry.getKey();
            Integer count = entry.getValue();
            topList.add(new TopPosition(title, count));
        }

        Comparator<TopPosition> comparator = new Comparator<TopPosition>() {
            public int compare(TopPosition topPosition1, TopPosition topPosition2) {
                return topPosition2.getCount() - topPosition1.getCount();
            }
        };

        Collections.sort(topList, comparator);
        return topList;
    }
}
