package one.sprint.alexmalvaez.com.sprintone.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import one.sprint.alexmalvaez.com.sprintone.R;
import one.sprint.alexmalvaez.com.sprintone.database.SuperFlingDBManager;
import one.sprint.alexmalvaez.com.sprintone.models.SuperFling;

/**
 * Created by Android1 on 10/21/2015.
 */
public class SuperFlingSSPFragment extends Fragment {

    private SuperFling superFling;

    //private TextView tvId;
    //private TextView tvImageId;
    private TextView tvTitle;
    //private TextView tvUserId;
    //private TextView tvUserName;
    private ImageView imgvBling;
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";
    public static final String ARG_ID = "id";
    public static final String ARG_IMAGE_ID = "imageId";
    public static final String ARG_TITLE = "title";
    public static final String ARG_USER_ID = "userId";
    public static final String ARG_USER_NAME = "userName";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    public SuperFlingSSPFragment(){

    }

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static SuperFlingSSPFragment create(int pageNumber, SuperFling superFling) {
        Log.d(SuperFlingSSPFragment.class.getSimpleName(), "SuperFlingSSPFragment.create pageNumber: " + pageNumber);

        Log.d(SuperFlingSSPFragment.class.getSimpleName(), "SuperFlingSSPFragment.create SuperFling: " + superFling);
        SuperFlingSSPFragment fragment = new SuperFlingSSPFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_PAGE, pageNumber);
        args.putString(ARG_ID, superFling.id);
        args.putString(ARG_IMAGE_ID, superFling.imageId);
        args.putString(ARG_TITLE, superFling.title);
        args.putString(ARG_USER_ID, superFling.userId);
        args.putString(ARG_USER_NAME, superFling.userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(SuperFlingSSPFragment.class.getSimpleName(), "SuperFlingSSPFragment.onCreate mPageNumber: " + mPageNumber);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        superFling = new SuperFling();
        superFling.id = getArguments().getString(ARG_ID);
        superFling.imageId = getArguments().getString(ARG_IMAGE_ID);
        superFling.title = getArguments().getString(ARG_TITLE);
        superFling.userId = getArguments().getString(ARG_USER_ID);
        superFling.userName = getArguments().getString(ARG_USER_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(SuperFlingSSPFragment.class.getSimpleName(), "SuperFlingSSPFragment.onCreateView");

        // Inflate the layout containing a title and body text.
        //ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        View view = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);

        Log.d(SuperFlingSSPFragment.class.getSimpleName(), "SuperFlingSSPFragment.onCreateView view: " + view);

        // Set the title view to show the page number.
        //tvId = (TextView) view.findViewById(R.id.tv_id);
        //tvImageId = (TextView) view.findViewById(R.id.tv_imageId);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        //tvUserId = (TextView) view.findViewById(R.id.tv_userId);
        //tvUserName = (TextView) view.findViewById(R.id.tv_userName);

        Log.d(SuperFlingSSPFragment.class.getSimpleName(), "SuperFlingSSPFragment.onCreateView before getting image");

        //if(mPageNumber > 1) {
            imgvBling = (ImageView) view.findViewById(R.id.imv_bling);
            Bitmap btm = SuperFlingDBManager.getSuperFlingDBManager(getContext()).getImageById(superFling.imageId);
            if(btm != null) {
                imgvBling.setImageBitmap(btm);
            }else{
                imgvBling.setImageResource(R.drawable.android);
            }
        //}

        Log.d(SuperFlingSSPFragment.class.getSimpleName(), "SuperFlingSSPFragment.onCreateView after getting image");

        //tvId.setText(superFling.id);
        //tvImageId.setText(superFling.imageId);
        tvTitle.setText(superFling.title);
        //tvUserId.setText(superFling.userId);
        //tvUserName.setText(superFling.userName);

        return view;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        Log.d(SuperFlingSSPFragment.class.getSimpleName(), "SuperFlingSSPFragment.getPageNumber");
        return mPageNumber;
    }
}
