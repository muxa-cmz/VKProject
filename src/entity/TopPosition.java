package entity;

/**
 * Created by valer on 03.12.2015.
 */
public class TopPosition {
    private String title;
    private int count;

    public TopPosition(String title, int count) {
        this.title = title;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}