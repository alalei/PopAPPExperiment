package com.example.popappexperiment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class APPsActivity extends Activity {
	ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apps);

		final String[] packageNames = new String[]{"com.google.android.youtube", "com.android.calculator2"};
		String[] titles = new String[]{"Youtube", "Calculator"};
		APPViewAdapter appViewAdapter = new APPViewAdapter (this, packageNames, titles);
		lv = (ListView) findViewById(R.id.listview_apps);
		lv.setAdapter(appViewAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				launchApp(packageNames[position]);
			}
			
		});

	}

    @Override
    protected void onResume() {
        super.onResume();
        showCloseButton(false);
    }

    protected void launchApp(String packageName) {
    	Intent mIntent = getPackageManager().getLaunchIntentForPackage(packageName);
    	if (mIntent != null) {
    		try {
                showCloseButton(true);
    			startActivity(mIntent);
    		} catch (ActivityNotFoundException err) {
    			Toast t = Toast.makeText(getApplicationContext(),"APP NOT FOUND",Toast.LENGTH_SHORT);
    			t.show();
    		}
    	}
    }

    private void showCloseButton(boolean visible) {
        Intent intent = new Intent(this, CloseButtonService.class);
        if (visible)
            startService(intent);
        else
            stopService(intent);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.apps, menu);
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
	
	/*
	@Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
     
        int mActivityWindowWidth = 200;
        int mActivityWindowHeight = 200;
        
            final View view = getWindow().getDecorView();
            final WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
     
            lp.gravity = Gravity.CENTER;
     
            lp.width = mActivityWindowWidth;
            lp.height = mActivityWindowHeight;
            lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
            getWindowManager().updateViewLayout(view, lp);
    }*/
	
	
	class APPViewAdapter extends BaseAdapter {
		
		private Context mContext;
		private String[] mPackageNames;
		private String[] mTitles;
		private Drawable[] mIcons;
		
		public APPViewAdapter(Context context, String[] packageNames, String[] titles) {
			mContext = context;
			mPackageNames = packageNames;
			mTitles = titles;
			
			PackageManager pm = getPackageManager();
			mIcons = new Drawable[titles.length];
			for (int i = 0; i < mIcons.length ; i++) {
				try {
					mIcons[i] = pm.getApplicationIcon(packageNames[i]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}

		@Override
		public int getCount() {
			return mPackageNames.length;
		}

		@Override
		public Object getItem(int position) {
			return mPackageNames[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.listview_item_app, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (mTitles[position] != null) {
				holder.mTitleTV.setText(mTitles[position]);
			}
			if (mIcons[position] != null) {
				holder.mImageIV.setImageDrawable(mIcons[position]);
			}
			
			return convertView;
		}

		class ViewHolder {
			ImageView mImageIV;
			TextView mTitleTV;

			ViewHolder(View view) {
				mTitleTV = (TextView) view.findViewById(R.id.listview_item_title);
				mImageIV = (ImageView) view.findViewById(R.id.listview_item_img);
			}
		}
	}
}
