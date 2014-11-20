package com.example.popappexperiment;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


public class CloseButtonService extends Service implements View.OnClickListener, OnTouchListener {
	public static final String EXTRA_APP_LAUNCHED_PACKAGE_NAME = "app_launched_package_name";
	
	View mView;
    Button closeButton;
    String launchedPackageName = null;
    WindowManager.LayoutParams layoutParams = null;
    
    // Params for moving button
    int offTimeCount = 0;
    boolean movable = false;
	Rect rawRect = null;

    @Override
    public IBinder onBind(Intent i) {
        return null;
    }

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
        closeButton.setOnTouchListener(this);
        
        layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                //2007, 8, -3);
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        layoutParams.gravity = Gravity.LEFT | Gravity.CENTER;
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView (mView, layoutParams);
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

    	Intent homeIntent = new Intent(getBaseContext(), MainActivity.class);
    	homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(homeIntent);
        }
    }
    
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) { // each action 0-4 ms
		case MotionEvent.ACTION_DOWN: {
			Log.d("Touch", "ACTION_DOWN");
			movable = false;
			rawRect = new Rect(mView.getLeft(), mView.getTop(), mView.getRight(), mView.getBottom());
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			Log.d("Touch","ACTION_MOVE");
			if (!movable) {
				if (rawRect.contains((int)event.getX(), (int)event.getY())) {
					return false;
				} else {
					movable = true;
				}
			}
			
			if (++offTimeCount%3 != 0) {
				return false;
			}
			offTimeCount = 0;
			int x = (int) event.getX();
			int y = (int) event.getY();
			moveButton(x, y);
			return true;
		}
		case MotionEvent.ACTION_POINTER_UP: {
			Log.d("Touch","ACTION_POINTER_UP");
			break;
		}
		case MotionEvent.ACTION_UP: {
			Log.d("Touch","ACTION_UP");
			if (!movable) {
				return false;
			} else {
				int x = (int) event.getX();
				int y = (int) event.getY();
				moveButton(x, y);
				return true;
			}
		}
		}
		return false;
	}
	
	private void moveButton(int relX, int relY) {
		if (layoutParams == null) {
			return;
		}
		layoutParams.x = layoutParams.x + relX;
		layoutParams.y =  layoutParams.y + relY;
		WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		wm.updateViewLayout(mView, layoutParams);
	}
    
}