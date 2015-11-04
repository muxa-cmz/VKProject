package entity;

/**
 * Created by Михаил on 24.10.2015.
 */
public class User extends VkEntity {
<<<<<<< Updated upstream
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
=======
    private String last_name;
    private String first_name;

    public User(String VkID, String last_name, String first_name) {
        this.last_name = last_name;
        this.first_name = first_name;
        this.setVkId(VkID);
    }
    public User(int ID, String VkID, String last_name, String first_name) {
        this.setID(ID);
        this.last_name = last_name;
        this.first_name = first_name;
        this.setVkId(VkID);
    }

    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
>>>>>>> Stashed changes
    }
}
