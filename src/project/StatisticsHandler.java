package project;

import java.util.*;

/**
 * Created by Михаил on 20.09.2015.
 */
public class StatisticsHandler {

    public List Top(List<String> ListAudioAllUser, int quantity){
        HashMap<String, Integer> top = new HashMap<String, Integer>();
        Integer am;
        for (String i : ListAudioAllUser) {
            am = top.get(i);
            top.put(i, am == null ? 1 : am + 1);
        }
        top = DeleteAudioCountOne(top);
        List finalList = TopQuantity(SortedTop(top), quantity);
        return  finalList;
    }

    public HashMap<String, Integer> DeleteAudioCountOne(HashMap<String, Integer> top){
        Iterator it = top.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            if (1 == pair.getValue().hashCode()) {
                it.remove();
            }
        }
        return top;
    }

    public List SortedTop(HashMap<String, Integer> top){
        List entryList = new ArrayList(top.entrySet());
        Collections.sort(entryList, new Comparator() {
            public int compare(Object o1, Object o2) {
                Map.Entry e1 = (Map.Entry) o1;
                Map.Entry e2 = (Map.Entry) o2;
                Comparable c1 = (Comparable) e1.getValue();
                Comparable c2 = (Comparable) e2.getValue();
                return c2.compareTo(c1);
            }
        });
        return entryList;
    }


    public List TopQuantity(List sortedList, int quantity){
        List finalList = new ArrayList();
        for (int i = 0; i < quantity; i++){
            finalList.add(sortedList.get(i).toString());
            //System.out.println(i + 1 + " место: " + sortedList.get(i).toString());
        }
        return finalList;
    }

}
