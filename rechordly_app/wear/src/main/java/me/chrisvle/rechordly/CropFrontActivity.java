package me.chrisvle.rechordly;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CropFrontActivity extends Activity {

    private TextView time;
    private String totalTime;
    private CropSliderViewFront slider;
    private RelativeLayout rel_circular;
    private Button doneButton;
    private boolean oldEdit;
    private boolean oldLyric;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_front);
        totalTime= getIntent().getStringExtra("time");
        oldEdit = getIntent().getBooleanExtra("oldEdit", false);
        oldLyric = getIntent().getBooleanExtra("oldLyric", false);



                time = (TextView) findViewById(R.id.timeF);
                doneButton = (Button) findViewById(R.id.crop_f_done);
                slider = (CropSliderViewFront) findViewById(R.id.crop_f_slider);
                rel_circular = (RelativeLayout) findViewById(R.id.rel_crop_f_circular);

                String boldfontPath = "fonts/Mission_Gothic_Regular.otf";
                Typeface tf = Typeface.createFromAsset(getAssets(), boldfontPath);
                time.setTypeface(tf);

                if (totalTime != null) {
                    String[] tArray = totalTime.split(":");
                    int t = 60 * Integer.parseInt(tArray[0]) + Integer.parseInt(tArray[1]);
                    slider.setTime(t);
                } else {
                    Log.d("CropTimeForward", "Did not receive time for crop");
                }

                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2;
                        if (oldEdit) {
                            intent2 = new Intent(getBaseContext(), OldEditActivity.class);
                            intent2.putExtra("start", 2);
                            intent2.putExtra("time", totalTime);
                            intent2.putExtra("lyrics", oldLyric);
                        } else {
                            intent2 = new Intent(getBaseContext(), SliderNavActivity.class);
                            intent2.putExtra("start", 2);
                            intent2.putExtra("start2", 2);
                            intent2.putExtra("time", totalTime);
                        }
                        intent2.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent2);
                        sendCropF(v);
                    }
                });


    }

    public void sendCropF(View view) {
        String t = slider.getTime();
        Log.d("CropFrontActivity", "Clicked: crop time is " + t);
        //Currently t is a string in MM:SS format
        Intent intent = new Intent("/crop_front");
        intent.putExtra("time", t);
        sendBroadcast(intent);
    }
}
