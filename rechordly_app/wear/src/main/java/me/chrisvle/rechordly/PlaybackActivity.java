package me.chrisvle.rechordly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PlaybackActivity extends Activity {
    private ImageView mImageButton;
    private GestureDetector tapDetector;
    private GestureDetector mDetector;

    private ImageView playBtn;
    private ImageView pauseBtn;
    private RelativeLayout parentView;

//    private boolean swipe = true;
    private ImageView swipeRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                playBtn = (ImageButton) stub.findViewById(R.id.play_btn);
                playBtn.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent e) {
                        if (tapDetector.onTouchEvent(e)) {
                            // single tap
                            return true;
                        } else {
                            return mDetector.onTouchEvent(e);
                        }
                    }
                });
                pauseBtn = (ImageView) findViewById(R.id.pause_btn);
                pauseBtn.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent e) {
                        if (tapDetector.onTouchEvent(e)) {
                            // single tap
                            return true;
                        } else {
                            return mDetector.onTouchEvent(e);
                        }
                    }
                });
                parentView = (RelativeLayout) findViewById(R.id.play_screen);
                swipeRight = (ImageView) findViewById(R.id.swipe_play);
            }
        });

        //Configure single tap detector
        tapDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                return true;
            }
        });


        // Configure a gesture detector
        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public void onLongPress(MotionEvent event) {
                return;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                    float distanceY) {
       //         if (!swipe) {
       //             return true;
       //         }
                if (distanceX > 5.0) {
                    Intent intent = new Intent(getBaseContext(), Main2Activity.class);
                    intent.putExtra("swipe", "right");
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    // Capture long presses
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mDetector.onTouchEvent(ev) || super.onTouchEvent(ev);
    }

    public void play_click(View v) {
        Log.d("Playback Activity", "Play Request");
        Intent intent = new Intent("/play");
        sendBroadcast(intent);
//        swipe = false;
        pauseBtn.bringToFront();
//        swipeRight.setVisibility(View.INVISIBLE);
        parentView.invalidate();

        //FIXME add play music service logic

    }

    public void pause_click(View v) {
        Log.d("Playback Activity", "Pause Request");
        Intent intent = new Intent("/pause");
        sendBroadcast(intent);

 //       swipe = true;
        playBtn.bringToFront();
 //       swipeRight.setVisibility(View.VISIBLE);
        parentView.invalidate();

        //FIXME add play music service logic

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        overridePendingTransition(android.R.anim.slide_in_left, 0);

    }

}