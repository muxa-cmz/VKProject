package entity;

import com.google.gson.annotations.SerializedName;

public class Friend extends VkEntity {
    @SerializedName("bdate")
    private String birthday;
    private String sex;

    public Friend(String VkID, String birthday, String sex) {
        this.birthday = birthday;
        this.sex = sex;
        this.setVkId(VkID);
    }
    public Friend(int ID, String VkID, String birthday, String sex) {
        this.setID(ID);
        this.birthday = birthday;
        this.sex = sex;
        this.setVkId(VkID);
    }

    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
