package com.aalto.solutions.mbflickrdemo.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.aalto.solutions.mbflickrdemo.Application;
import com.aalto.solutions.mbflickrdemo.R;
import com.aalto.solutions.mbflickrdemo.model.ImageModel;
import com.aalto.solutions.mbflickrdemo.model.PhotoModel;
import com.aalto.solutions.mbflickrdemo.util.CountBitmapDrawable;
import com.aalto.solutions.mbflickrdemo.util.Flickr;
import com.aalto.solutions.mbflickrdemo.util.FlickrConstant;
import com.aalto.solutions.mbflickrdemo.model.Size;
import com.aalto.solutions.mbflickrdemo.util.LocationUtil;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class SplashActivity extends AppCompatActivity
{
    /**
     * Full phone image for the 1st transition
     */
    @Bind(R.id.activity_splash_phone_image) ImageView mPhoneImage;
    @Bind(R.id.activity_splash_phone_image_bottom)  ImageView mBottomPhoneImage;
    @Bind(R.id.activity_splash_phone_image_top)  ImageView mTopPhoneImage;
    @Bind(R.id.activity_splash_counter_image)  ImageView mCntImage;

    Application mApp;

    SharedPreferences mSettings;

    /**
     * Phone image transition up within screen bounds
     */
    FastOutSlowInInterpolator mFastOutSlowInInterpolator;

    private CountBitmapDrawable mCountDrawable = null;

    Location mLoc = null;

    // For permission flow
    int mRequestCode;

    //============================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        // Basically if tablet device:
        if( !LocationUtil.canGetLocation(this) )
        {
            Toast toast = Toast.makeText( this, "No Location available, app closed", Toast.LENGTH_SHORT );
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            finish();
        }
        else
        {
            // Conduct runtime permission check for M & N devices:
            if( Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 )
            {
                mSettings = PreferenceManager.getDefaultSharedPreferences(this);

                // check if this is the first time we request for this permission
                if( mSettings.getBoolean(LocationUtil.LOCATION_PERMISSION_REQUEST_FIRST_TIME, true) )
                {
                    // yep, this is the first time, always denied by default
                    mRequestCode=1;
                    LocationUtil.showPermissionInfoDialog(this, "Permission required", "Location info to download photos",
                                                            mBackKeyListener );
                }
                else
                {
                    if( hasPermission() )
                        getLocation();
                    else
                        doSubsequentFlow(); // do other flow
                }
            }
            else
            {
                // Just the old way... no need for a permission check.
                getLocation();
            }
        }
    }

    //=============================================================

    private void getLocation()
    {
        mLoc = LocationUtil.getLocation(this);
        if( mLoc==null )
        {
            Toast toast = Toast.makeText( this, "No Location available, app closed", Toast.LENGTH_SHORT );
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            finish();

            // Two reasons:
            // 1. Location service not enabled, take user to settings
            // 2. service is ON but no location available yet, takes a while
        }
        else
        {
            init();
        }
    }

    //==============================================================

    private void init()
    {
        new PullImagesFromFlickrATask().execute();

        mApp = (Application)this.getApplication();

        // For image transitions
        mFastOutSlowInInterpolator = new FastOutSlowInInterpolator();

        beginPhoneImageTransitionUp();

        Bitmap inboxIcon = ((BitmapDrawable) getResources().getDrawable(R.drawable.inbox_tab)).getBitmap();
        mCountDrawable = new CountBitmapDrawable( getResources(), inboxIcon );

        mCntImage.setImageDrawable(mCountDrawable);
    }

    //==============================================================

    /**
     *
     */
    class PullImagesFromFlickrATask extends AsyncTask<String, Integer, List<ImageModel>>
    {
        private Integer totalCount, currentIndex;

        @Override
        protected void onPreExecute() {super.onPreExecute();}

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            mCountDrawable.setValues( values[0], values[1] );
        }

        @Override
        protected List<ImageModel> doInBackground(String... params)
        {
            Flickr flickr = new Flickr(FlickrConstant.FLICKR_API_KEY, FlickrConstant.FLICKR_FORMAT);

            Double lat = mLoc.getLatitude();
            Double lon = mLoc.getLongitude();

            List<PhotoModel> photos = flickr.getPhotoSets().getPhotos( lat.toString(), lon.toString() );
            // For PhotoSet : List<PhotoModel> photos = flickr.getPhotoSets().getPhotos( FlickrConstant.PHOTOSET_ID );
            List<ImageModel> result = new ArrayList<ImageModel>();
            totalCount = photos.size();
            currentIndex = 0;

            for (PhotoModel photo : photos)
            {
                currentIndex++;
                List<Size> sizes = flickr.getPhotos().getSizes(photo.getId());
                String thumbnailUrl = sizes.get(0).getSource();
                String mediumUrl = sizes.get(4).getSource();
                InputStream inputStreamThumbnail = null, inputStreamMedium = null;
                try {
                    inputStreamThumbnail = new URL(thumbnailUrl).openStream();
                    inputStreamMedium = new URL(mediumUrl).openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap bitmapThumbnail = BitmapFactory.decodeStream(inputStreamThumbnail);
                Bitmap bitmapMedium = BitmapFactory.decodeStream(inputStreamMedium);
                result.add(new ImageModel(photo.getTitle(), bitmapThumbnail, bitmapMedium));
                publishProgress(currentIndex, totalCount);
            }

            return result;
        }

        @Override
        protected void onPostExecute( List<ImageModel> s )
        {
            mApp.setData((ArrayList)s);
            startActivity( new Intent( SplashActivity.this, MainActivity.class) );
            super.onPostExecute(s);
            finish();
        }
    }

    //=================================================================================

    /**
     * The first transition: Phone image slides up to become visible
     */
    private void beginPhoneImageTransitionUp()
    {
        String propertyName = "translationY";

        // begin from where the image is:
        float propertyStart = 0.0f;

        // Phone image transition up:
        float propertyEnd = (float)mPhoneImage.getDrawable().getIntrinsicHeight();

        ObjectAnimator objectAnimatorP = ObjectAnimator.ofFloat( mPhoneImage,
                propertyName,
                propertyStart,
                -propertyEnd);

        objectAnimatorP.setDuration(2000);
        objectAnimatorP.setRepeatCount(0);
        objectAnimatorP.setInterpolator(mFastOutSlowInInterpolator);

        objectAnimatorP.addListener( new Animator.AnimatorListener()
                                     {
                                         @Override
                                         public void onAnimationStart(Animator animation) {Log.d("=MB=", "Started");}

                                         @Override
                                         public void onAnimationEnd(Animator animation)
                                         {
                                             beginCounterTransitionDown();
                                         }

                                         @Override
                                         public void onAnimationCancel(Animator animation) {}

                                         @Override
                                         public void onAnimationRepeat(Animator animation) {}
                                     }
        );

        objectAnimatorP.start();
    }

    //================================================================

    private void beginCounterTransitionDown()
    {
        // hide initial phone image
        mPhoneImage.setVisibility(View.INVISIBLE);

        // show mock
        mBottomPhoneImage.setVisibility(View.VISIBLE);
        mCntImage.setVisibility(View.VISIBLE);
        mTopPhoneImage.setVisibility(View.VISIBLE);

        String propertyName = "translationY";

        // begin from where the image is:
        float propertyStart = 0.0f;

        // Phone image transition up:
        float propertyEnd = (float)mCntImage.getDrawable().getIntrinsicHeight()*1.5f;

        ObjectAnimator objectAnimatorP = ObjectAnimator.ofFloat( mCntImage,
                propertyName,
                propertyStart,
                propertyEnd);

        objectAnimatorP.setDuration(2000);
        objectAnimatorP.setRepeatCount(0);
        objectAnimatorP.setInterpolator(mFastOutSlowInInterpolator);

        objectAnimatorP.start();
    }

    //================================================================

    @TargetApi(23)
    protected boolean hasPermission()
    {
        return ( checkSelfPermission(ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED  );

    }

    //=================================================================

    @TargetApi(23)
    private void doFirstTimeFlow()
    {
        // Both coarse and fine permissions are bundled as "Location", just one system dialog is displayed asking
        // for "Location" permission

        // Request for permission, displays a system dialog:
        requestPermissions(new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION}, 1);
    }

    //=================================================================

    @Override
    public void onRequestPermissionsResult(final int aRequestCode, String[] permissions, int[] grantResults)
    {
        int result = grantResults[0];
        Log.d("=MB=","onRequestPermissionsResult() LOCATION : " + result );

        mSettings
                .edit()
                .putBoolean(LocationUtil.LOCATION_PERMISSION_REQUEST_FIRST_TIME, false)
                .commit();

        if( result==PackageManager.PERMISSION_GRANTED )
        {
            getLocation();
        }
        else
        {
            // Note: we could display a dialog here, which explains why we need this permission and ask again
            // if ask never again was not checked (shouldShowRationale return value now)
            // DENIED
            Toast toast = Toast.makeText( this, "Location permission denied, app closed", Toast.LENGTH_SHORT );
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            finish();
        }
    }

    //=========================================================================

    @TargetApi(23)
    private void doSubsequentFlow()
    {
        // false if 'never ask again' is checked
        if( shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION) )
        {
             LocationUtil.showPermissionInfoDialog( this,
                                                    "Permission required",
                                                    "Location info to download photos",
                                                    mBackKeyListener );
        }
        else
        {
            // never ask again is checked:
            // DENIED
            Toast toast = Toast.makeText( this, "Location permission denied, app closed", Toast.LENGTH_SHORT );
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            finish();
        }
    }

    //=========================================================================

    DialogInterface.OnClickListener mBackKeyListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            doFirstTimeFlow();
        }
    };

    //===============================================================================
}