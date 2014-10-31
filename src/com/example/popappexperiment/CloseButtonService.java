package com.example.popappexperiment;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;


public class CloseButtonService extends Service implements View.OnClickListener {

    View mView;
    Button closeButton;

    @Override
    public IBinder onBind(Intent i) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        mView = new LinearLayout(this);
//        mView.setBackgroundColor(0x88ff0000); // The translucent red color
        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = layoutInflater.inflate(R.layout.close_button, null);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                0,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.RIGHT | Gravity.TOP;
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        mView.findViewById(R.id.close_button).setOnClickListener(this);
        wm.addView (mView, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mView!=null){
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.removeView(mView);
        }
    }

    @Override
    public void onClick(View view) {
        if(view == mView) {
            //stopSelf();
            startActivity(new Intent(this, APPsActivity.class));
        }
    }
}