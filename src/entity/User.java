package entity;

/**
 * Created by Михаил on 24.10.2015.
 */
public class User extends VkEntity {

    private String lastName;
    private String firstName;

    public User(String VkID, String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.setVkId(VkID);
    }
    public User(int ID, String VkID, String lastName, String firstName) {
        this.setID(ID);
        this.lastName = lastName;
        this.firstName = firstName;
        this.setVkId(VkID);
    }
    public User() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
