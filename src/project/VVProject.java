package project;

import entity.Change;
import entity.FriendsAudio;
import mappers.FriendsAudioMapper;
import org.apache.http.HttpException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;


/**
 * Created by Михаил on 15.09.2015.
 */

public class VVProject {

    public static void main(String args[]) throws URISyntaxException, IOException, HttpException, ParseException, SQLException, java.text.ParseException {

//        Friend friend = new Friend("Привет", "12.12.1992", "ж");
        Object mapper = null;
        Change change = new Change("19.12.1992", 30, 1, true);
        if (change instanceof Change){
            mapper = new mappers.ChangeMapper();
        }

//        IEntityMapperBase<Change> ivk = (IEntityMapperBase<Change>) mapper;
//        ivk.Insert(change);


        //UsersFriends au = new UsersFriends(7, 30);
        FriendsAudio fa = new FriendsAudio(30, 2);
        FriendsAudioMapper fam = new FriendsAudioMapper();
        //fam.Insert(fa);
        fam.FindByIdFriend(30);

        //VKClient vkClient = new VKClient();
//        vkClient.TopArtist(args[0], Integer.parseInt(args[1]));
    }
}
