package com.aalto.solutions.mbflickrdemo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.aalto.solutions.mbflickrdemo.Application;
import com.aalto.solutions.mbflickrdemo.R;
import com.aalto.solutions.mbflickrdemo.adapter.CoverFlowAdapter;
import com.aalto.solutions.mbflickrdemo.model.ImageModel;

import java.util.ArrayList;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

/**
 * Created by marko
 */
public class MainActivity extends AppCompatActivity
{
    private FeatureCoverFlow mCoverFlow;
    private CoverFlowAdapter mAdapter;
    private TextSwitcher mTitle;
    private Application mApp;

    private ArrayList<ImageModel> mData2;

    //=======================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mApp = (Application)this.getApplication();
        mData2 = mApp.getData();

        mTitle = (TextSwitcher) findViewById(R.id.title);
        mTitle.setFactory(new ViewSwitcher.ViewFactory()
        {
            @Override
            public View makeView()
            {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                TextView textView = (TextView) inflater.inflate(R.layout.item_title, null);
                return textView;
            }
        });

        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
        mTitle.setInAnimation(in);
        mTitle.setOutAnimation(out);

        mAdapter = new CoverFlowAdapter(this);
        mAdapter.setData(mData2);
        mCoverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
        mCoverFlow.setAdapter(mAdapter);

        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText( MainActivity.this,
                                mData2.get(position).getName(),
                                Toast.LENGTH_SHORT).show();

                // We could show a snackbar in Full screen, just a toast now

                // Just a basic Activity Transition:
                Intent i = new Intent( getApplicationContext(), FullScreenActivity.class);

                ActivityOptions opts = ActivityOptions.makeScaleUpAnimation(view, 0,
                      0, view.getWidth(), view.getHeight());

                // Can't do custom animation, Coverflow messes up with custom animation
                //ActivityOptions opts = ActivityOptions.makeCustomAnimation(
                //       MainActivity.this, R.anim.fade_in, R.anim.fade_out);

                i.putExtra("index", position);
                startActivity( i, opts.toBundle() );
            }
        });

        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener()
        {
            @Override
            public void onScrolledToPosition(int position)
            {
                mTitle.setText(mData2.get(position).getName());
            }

            @Override
            public void onScrolling()
            {
                mTitle.setText("");
            }
        });

    }

    //===============================================================================

}
