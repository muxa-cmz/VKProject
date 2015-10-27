package entity;

import com.google.gson.annotations.SerializedName;

public class VkEntity extends Entity {
    @SerializedName("id")
    protected String VkId;
    public String getVkId() {
        return VkId;
    }
    public void setVkId(String vkId) {
        VkId = vkId;
    }
}
