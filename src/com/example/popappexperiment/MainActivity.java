package com.example.popappexperiment;

import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {
	final String TAG = "DEBUG";
	Button button;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        button = (Button) findViewById(R.id.btn_pop_APP);
        
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, APPsActivity.class);
				startActivity(intent);
			}
 
		});
		
		// View decorView = getWindow().getDecorView().setSystemUiVisibility();
    }
    
    protected void getPackageInfo() {
    	final PackageManager pm = getPackageManager();
		List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
		for (ApplicationInfo packageInfo : packages) {
			Log.d(TAG, "Installed package :" + packageInfo.packageName);
		}
    }
    
    protected void launchApp(String packageName) {
    	Intent mIntent = getPackageManager().getLaunchIntentForPackage(packageName);
    	if (mIntent != null) {
    		try {
    			startActivity(mIntent);
    		} catch (ActivityNotFoundException err) {
    			Toast t = Toast.makeText(getApplicationContext(),"APP NOT FOUND",Toast.LENGTH_SHORT);
    			t.show();
    		}
    	}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
}
