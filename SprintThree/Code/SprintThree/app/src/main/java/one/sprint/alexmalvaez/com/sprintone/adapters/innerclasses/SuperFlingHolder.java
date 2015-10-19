package one.sprint.alexmalvaez.com.sprintone.adapters.innerclasses;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import one.sprint.alexmalvaez.com.sprintone.R;

/**
 * Created by Android1 on 10/6/2015.
 */
public class SuperFlingHolder extends RecyclerView.ViewHolder{

    public TextView tvCategory;
    public TextView tvJoke;

    public SuperFlingHolder(View view){
        super(view);

        this.tvCategory = (TextView) view.findViewById(R.id.cv_tv_category);
        this.tvJoke = (TextView) view.findViewById(R.id.cv_tv_joke);

        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(SuperFlingHolder.class.getSimpleName(),"Category: " + tvCategory.getText().toString() + " Joke: " + tvJoke.getText().toString());
            }
        });
    }
}
