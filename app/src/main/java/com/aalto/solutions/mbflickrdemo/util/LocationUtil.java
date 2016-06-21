package com.aalto.solutions.mbflickrdemo.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.util.Log;

import com.aalto.solutions.mbflickrdemo.R;

import java.util.List;

/**
 * Created by marko
 */
public class LocationUtil
{
    // key for preferences
    public static final String LOCATION_PERMISSION_REQUEST_FIRST_TIME = "location_request_first_time";

    /**
     * Utility method for location capability based on GPS or Network
     *
     * This check is mainly for Tablet devices
     *
     * @param context
     * @return true if device can get location
     */
    public static boolean canGetLocation(Context context)
    {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        List<String> list = lm.getAllProviders();

        for (String provider : list)
        {
            if (provider.equals(LocationManager.GPS_PROVIDER) || provider.equals(LocationManager.NETWORK_PROVIDER))
            {
                return true;
            }
        }
        return false;
    }

    // ===============================================================

    public static android.location.Location getLocation(Context context)
    {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        android.location.Location location = null;

        try
        {
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        catch (Exception ex)
        {
            Log.d("=MB=", "Couldn't get GPS location", ex);
        }


        if (location == null)
        {
            location = getCoarseLocation(context);
        }

        return location;
    }

    // ===============================================================

    public static android.location.Location getCoarseLocation(Context context)
    {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        android.location.Location location = null;

        try
        {
            location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        catch (Exception ex)
        {
            Log.d("=MB=", "Couldn't get location from network provider", ex);
        }

        return location;
    }

    // ===============================================================

    /**
     * Runtime permission check dialog to urge client to grant permission
     * @param aTitleTxt Title of the Dialog
     * @param aInfoTxt Message to display, typically why this permission id needed
     */
    public static void showPermissionInfoDialog( Context aCtx,
                                             final String aTitleTxt,
                                             final String aInfoTxt,
                                             final DialogInterface.OnClickListener aBackListener )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder( aCtx );
        builder.setTitle( aTitleTxt );
        builder.setMessage( aInfoTxt );
        builder.setPositiveButton("Back", aBackListener);
        builder.setCancelable(false);
        builder.show();
    }

    // ===============================================================
}
