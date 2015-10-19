package one.sprint.alexmalvaez.com.sprintone.models;

import java.util.ArrayList;

/**
 * Created by Android1 on 10/16/2015.
 */
public class Joke {

    public String id;
    public String des;
    public ArrayList<String> categories;

    public Joke(ArrayList<String> categories, String id, String des) {
        this.categories = categories;
        this.id = id;
        this.des = des;
    }

    public String getCategoriesString(){
        String categoriesStr = "";

        if(categories != null) {
            for (String category : categories)
                categoriesStr += category + " ";
        }

        return categoriesStr;
    }

    @Override
    public String toString(){
        return "[id:" + id +
                ",des:" + des +
                ",categories:" + getCategoriesString() + "]";
    }

}