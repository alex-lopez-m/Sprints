package one.sprint.alexmalvaez.com.sprintone.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import one.sprint.alexmalvaez.com.sprintone.R;
import one.sprint.alexmalvaez.com.sprintone.adapters.innerclasses.SuperFlingHolder;
import one.sprint.alexmalvaez.com.sprintone.models.Joke;

/**
 * Created by Android1 on 10/6/2015.
 */
public class SuperFlingAdapter extends RecyclerView.Adapter<SuperFlingHolder>{

    private ArrayList<Joke> jokeList;
    private Context context;

    public SuperFlingAdapter(Context context, ArrayList<Joke> listItem){
        this.context = context;
        this.jokeList = listItem;
    }

    public void setJokeList(ArrayList<Joke> jokeList) {
        this.jokeList = jokeList;
    }

    @Override
    public SuperFlingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_super_fling, parent, false);
        SuperFlingHolder jokeHolder = new SuperFlingHolder(view);

        return jokeHolder;
    }

    @Override
    public void onBindViewHolder(SuperFlingHolder jokeHolder, int position) {
        Joke joke = jokeList.get(position);

        jokeHolder.tvCategory.setText(joke.getCategoriesString());
        jokeHolder.tvJoke.setText(joke.des);
    }

    @Override
    public int getItemCount() {
        if(jokeList != null)
            return jokeList.size();
        return 0;
    }
}
