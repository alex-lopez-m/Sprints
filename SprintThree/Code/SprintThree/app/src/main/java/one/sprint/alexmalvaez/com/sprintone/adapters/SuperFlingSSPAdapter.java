package one.sprint.alexmalvaez.com.sprintone.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

import one.sprint.alexmalvaez.com.sprintone.fragments.SuperFlingSSPFragment;
import one.sprint.alexmalvaez.com.sprintone.models.SuperFling;

/**
 * Created by Android1 on 10/21/2015.
 */
public class SuperFlingSSPAdapter extends FragmentStatePagerAdapter {

    private ArrayList<SuperFling> superFlingList;

    public SuperFlingSSPAdapter(FragmentManager fm) {
        super(fm);
        Log.d(SuperFlingSSPAdapter.class.getSimpleName(), "Contructor SuperFlingSSPAdapter()");
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(SuperFlingSSPAdapter.class.getSimpleName(), "getItem: " + position);
        return SuperFlingSSPFragment.create(position, superFlingList.get(position));
    }


    @Override
    public int getCount() {
        Log.d(SuperFlingSSPAdapter.class.getSimpleName(), "getCount: " + superFlingList.size());
        return superFlingList.size();
    }

    public void setSuperFlingList(ArrayList<SuperFling> superFlingList) {
        Log.d(SuperFlingSSPAdapter.class.getSimpleName(), "setSuperFlingList: " + superFlingList.size());
        this.superFlingList = superFlingList;
    }
}
