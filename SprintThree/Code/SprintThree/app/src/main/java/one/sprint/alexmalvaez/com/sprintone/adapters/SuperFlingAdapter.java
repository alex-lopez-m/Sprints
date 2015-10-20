package one.sprint.alexmalvaez.com.sprintone.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import one.sprint.alexmalvaez.com.sprintone.R;
import one.sprint.alexmalvaez.com.sprintone.adapters.innerclasses.SuperFlingHolder;
import one.sprint.alexmalvaez.com.sprintone.models.SuperFling;

/**
 * Created by Android1 on 10/6/2015.
 */
public class SuperFlingAdapter extends RecyclerView.Adapter<SuperFlingHolder>{

    private ArrayList<SuperFling> superFlings;
    private Context context;

    public SuperFlingAdapter(Context context, ArrayList<SuperFling> listItem){
        this.context = context;
        this.superFlings = listItem;
    }

    public void setSuperFlings(ArrayList<SuperFling> superFlings) {
        this.superFlings = superFlings;
    }

    @Override
    public SuperFlingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_super_fling, parent, false);
        SuperFlingHolder jokeHolder = new SuperFlingHolder(view);

        return jokeHolder;
    }

    @Override
    public void onBindViewHolder(SuperFlingHolder superFlingHolder, int position) {
        SuperFling superFling = superFlings.get(position);

        //superFlingHolder.imFling = (superFling.getCategoriesString());
        //Use Picasso
        superFlingHolder.tvTitle.setText(superFling.title);
    }

    @Override
    public int getItemCount() {
        if(superFlings != null)
            return superFlings.size();
        return 0;
    }
}
