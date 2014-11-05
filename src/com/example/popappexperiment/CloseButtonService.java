package com.example.popappexperiment;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.IntentService;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


public class CloseButtonService extends Service implements View.OnClickListener {
	public static final String EXTRA_APP_LAUNCHED_PACKAGE_NAME = "app_launched_package_name";
	
	View mView;
    Button closeButton;
    String launchedPackageName = null;
    
    /*
    public CloseButtonService(String name) {
		super(name);
	}*/
	/*
	public CloseButtonService() {
		super(CloseButtonService.class.getName());
	}*/

    /*
	@Override
	protected void onHandleIntent(Intent intent) {
		launchedPackageName = intent.getStringExtra(EXTRA_APP_LAUNCHED_PACKAGE_NAME);
		if (null == mView) {
			setFloatingWindow ();
		}
	}*/

    @Override
    public IBinder onBind(Intent i) {
        return null;
    }
    
//    @Override
//    public int onStartCommand (Intent intent, int flags, int startId) {
//    	launchedPackageName = intent.getStringExtra(EXTRA_APP_LAUNCHED_PACKAGE_NAME);
//    	Toast.makeText(getBaseContext(), "onStartCommand", Toast.LENGTH_LONG).show();
//		return startId;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        showCloseButton();
    }
    
    private void showCloseButton() {
    	Toast.makeText(getApplicationContext(),"showCloseButton",Toast.LENGTH_SHORT).show();

    	LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = layoutInflater.inflate(R.layout.close_button, null);
        
        closeButton = (Button) mView.findViewById(R.id.close_button);
        closeButton.setOnClickListener(this);
        
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                //2007, 8, -3);
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.LEFT | Gravity.CENTER;
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView (mView, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideCloseButton();
    }

    private void hideCloseButton() {
        if(mView != null){
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.removeView(mView);
        }
    }

    @Override
    public void onClick(View view) {
        if(view == closeButton) {
        stopSelf();
    	Toast.makeText(getBaseContext(), "Close Button Clicked", Toast.LENGTH_LONG).show();

<<<<<<< HEAD
    	Intent shareIntent = new Intent(getBaseContext(), MainActivity.class);
    	shareIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	startActivity(shareIntent);	
=======
    	Intent homeIntent = new Intent(getBaseContext(), MainActivity.class);
    	homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(homeIntent);
        }
>>>>>>> 96477f32bde69a7d164326e1593ea5e2a38faa8b
    }
}