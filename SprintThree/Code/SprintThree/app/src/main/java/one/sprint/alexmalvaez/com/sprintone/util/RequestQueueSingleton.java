package one.sprint.alexmalvaez.com.sprintone.util;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Android1 on 9/1/2015.
 */
public class RequestQueueSingleton {

    private static RequestQueueSingleton rqcInstance;
    private RequestQueue requestQueue;

    public static final String CLASS_NAME = RequestQueueSingleton.class.getSimpleName();

    private RequestQueueSingleton(){
    }

    public static synchronized RequestQueueSingleton getInstance(){
        if(rqcInstance == null){
            rqcInstance = new RequestQueueSingleton();
        }

        return rqcInstance;
    }

    public RequestQueue getRequestQueue(Context context){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context);
        }

        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag, Context context){
        request.setTag(TextUtils.isEmpty(tag) ? CLASS_NAME : tag);
        getRequestQueue(context).add(request);
    }

    public void cancelPendingRequest(Object tag){
        if(requestQueue != null){
            requestQueue.cancelAll(tag);
        }
    }

}
