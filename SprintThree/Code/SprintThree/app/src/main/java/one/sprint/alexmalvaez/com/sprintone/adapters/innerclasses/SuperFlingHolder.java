package one.sprint.alexmalvaez.com.sprintone.adapters.innerclasses;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import one.sprint.alexmalvaez.com.sprintone.R;

/**
 * Created by Android1 on 10/6/2015.
 */
public class SuperFlingHolder extends RecyclerView.ViewHolder{

    public ImageView imFling;
    public TextView tvTitle;

    public SuperFlingHolder(View view){
        super(view);

        this.imFling = (ImageView) view.findViewById(R.id.cv_img_fling);
        this.tvTitle = (TextView) view.findViewById(R.id.cv_tv_title);

        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(SuperFlingHolder.class.getSimpleName(),"Title: " + tvTitle.getText().toString());
            }
        });
    }
}
