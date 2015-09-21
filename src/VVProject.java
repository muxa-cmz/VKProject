import org.apache.http.HttpException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Михаил on 15.09.2015.
 */


public class VVProject {

    public static void main(String args[]) throws URISyntaxException, IOException, HttpException, ParseException {

        MyHTTPClient http = new MyHTTPClient();
        //http.Parse();
        //http.auth();
//        try {
//            //System.out.println(http.Top(http.getListAudioUsers(http.getListFriends(http.getID(args[0])))));
//            http.Top(http.getListAudioUsers(http.getListFriends(http.getID(args[0]))));
//        }
//        catch (ParseException ex){
//            ex.printStackTrace();
//        }
        http.TopArtist(args[0], Integer.parseInt(args[1]));

    }

}
