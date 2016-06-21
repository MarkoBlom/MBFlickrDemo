package com.aalto.solutions.mbflickrdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.aalto.solutions.mbflickrdemo.model.ImageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marko
 */
public class Application extends android.app.Application
{
    private ArrayList<ImageModel> mData= new ArrayList<>();

    private Lifecycles mLifecyles;

    @Override
    public void onCreate()
    {
        super.onCreate();

        // Register for Activity lifecycle method callbacks, just for cleaning up
        mLifecyles = new Lifecycles();
        registerActivityLifecycleCallbacks(mLifecyles);
    }

    public void setData( ArrayList<ImageModel> aData )
    {
        mData = aData;
    }

    public ArrayList<ImageModel> getData() { return mData; }

    //=======================================================================

    /**
     * Activity lifecycle callback
     */
    private class Lifecycles implements ActivityLifecycleCallbacks
    {
        private int runningActivityCount = 0;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) { }

        @Override
        public void onActivityStarted(Activity activity){ }

        @Override
        public void onActivityResumed(Activity activity) { }

        @Override
        public void onActivityPaused(Activity activity){}

        @Override
        public void onActivityStopped(Activity activity){ }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState){}

        @Override
        public void onActivityDestroyed(Activity activity)
        {
            if( --runningActivityCount == 0)
            {
                mData.clear();
            }
        }
    }

    //==================================================================================


}
