package one.sprint.alexmalvaez.com.sprintone.models;

/**
 * Created by Android1 on 10/19/2015.
 */
public class SuperFling {

    public String id;
    public String imageId;
    public String title;
    public String userId;
    public String userName;

    public SuperFling(String id, String imageId, String title, String userId, String userName) {
        this.id = id;
        this.imageId = imageId;
        this.title = title;
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString(){
        return "[id:" + id +
                ", imageId:" + imageId +
                ", title:" + title +
                ", userId:" + userId +
                ", userName:" + userName + "]";
    }

}
