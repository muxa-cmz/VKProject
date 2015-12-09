package tests;

import api.Api;
import api.IApi;
import entity.Friend;
import entity.Song;
import entity.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockTest{
    @Test
    public void getUserInfoTest(){
        IApi apiMock = mock(Api.class);
        when(apiMock.getUserInfo()).thenReturn(new User("142182681", "Майорова", "Вера" ));
        User user1 = apiMock.getUserInfo();
        Assert.assertEquals(user1.getFirstName(), "Вера");
    }


    @Test
    public void getListFriendTest(){
        IApi apiMock = mock(Api.class);
        List<Friend> friends = new ArrayList<Friend>();
        friends.add(new Friend("25293291", "3.6.1994", "1"));
        friends.add(new Friend("52506062", "22.8.1994", "2"));
        friends.add(new Friend("78543368", "27.3.1992", "2"));
        friends.add(new Friend("260871398", "13.5.1962", "1"));

        when(apiMock.getListFriend("142182681")).thenReturn(friends);
        List<Friend> friendsTest = apiMock.getListFriend("142182681");
        Assert.assertEquals(friendsTest, friends);
    }


    @Test
    public void getListSongTest(){
        IApi apiMock = mock(Api.class);
        List<Song> songs = new ArrayList<Song>();
        songs.add(new Song("The PanHeads Band", "Не будите меня (Skillet Cover)"));
        songs.add(new Song("Неизвестный исполнитель", "Дорожка 5"));


        when(apiMock.getListSong("78543368")).thenReturn(songs);
        List<Song> songsTest = apiMock.getListSong("78543368");
        Assert.assertEquals(songsTest, songs);
    }

}