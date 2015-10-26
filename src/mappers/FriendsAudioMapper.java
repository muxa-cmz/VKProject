package mappers;

import entity.FriendsAudio;

/**
 * Created by Михаил on 24.10.2015.
 */
public class FriendsAudioMapper implements IEntityMapperBase<FriendsAudio> {
    @Override
    public void Update(FriendsAudio friendsAudio) {

    }
    @Override
    public void Insert(FriendsAudio friendsAudio) {
        // SQL
        //INSERT INTO FriendsAudio (VkID_Friend, IDAudio) VALUES (friendsAudio.getVkIDFriend(), friendsAudio.getIDAudio());
    }
    @Override
    public void Delete(FriendsAudio friendsAudio) {

    }
    @Override
    public FriendsAudio FindById(int id) {
        return null;
    }
}
