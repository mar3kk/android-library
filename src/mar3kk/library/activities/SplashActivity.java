package mar3kk.library.activities;


import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class SplashActivity extends Activity {
	
	private static final String TAG = "SPLASH";
	private Context mContext;
	private Bitmap mSplashBmp;
	private boolean mShowDialog;
	private int mTime;
	private Dialog mSplashDialog;
	private AsyncTask<Integer, Integer, Integer> st;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG,"onCreate");
		mContext = this;
		mShowDialog = true;
		mTime = 0;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG,"onStart");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG,"onResume");
		if (mSplashBmp != null) {
			st = new SplashThread().execute(6000);
		}
	}
	
	@Override
	public void onPause() {
		Log.d(TAG,"onPause");
		super.onPause();
		st.cancel(false);
	}
	
	@Override
	public void onStop() {
		Log.d(TAG,"onStop");
		super.onStop();
	}
	
	@Override
	public void onDestroy() {
		Log.d(TAG,"onDestroy");
		super.onDestroy();
		mSplashBmp.recycle();
	}
	
	@Override
	public void onRestart() {
		Log.d(TAG,"onRestart");
		super.onRestart();
	}
	
	protected class SplashThread extends AsyncTask<Integer,Integer,Integer> {

		@Override
		protected void onPreExecute() {
			Log.d(TAG,"onPreExecute");
			if (mShowDialog) {
				mSplashDialog = new Dialog(mContext, R.style.Theme_Light_NoTitleBar_Fullscreen);
				ImageView iv = new ImageView(mContext);
				iv.setImageBitmap(mSplashBmp);
				iv.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						mShowDialog = false;
					}
				});
				mSplashDialog.setContentView(iv);
				mSplashDialog.show();
			}
		}
		
		@Override
		protected Integer doInBackground(Integer... arg) {
			Log.d(TAG,"doInBackground");
			while (mShowDialog && mTime < arg[0]) {
				try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
				mTime += 10;
				if (mTime > arg[0]) {mShowDialog = false;Log.d(TAG,"mTime > arg[0]W");}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Integer arg0) {
			Log.d(TAG,"onPostExecute");
			try {mSplashDialog.dismiss();}catch(Exception e) {e.printStackTrace();}
		}
		
		@Override
		protected void onCancelled() {
			Log.d(TAG,"onCancelled");
			try {mSplashDialog.dismiss();}catch(Exception e) {e.printStackTrace();}
		}
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    outState.putInt("mTime", mTime);
	    outState.putBoolean("mShowDialog", mShowDialog);
	    super.onSaveInstanceState(outState);
	    Log.d(TAG,"onSaveInstanceState mShowDialog " + mShowDialog + " mTime " + mTime);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle outState) {
	    super.onRestoreInstanceState(outState);
	    mTime = outState.getInt("mTime");
	    mShowDialog = outState.getBoolean("mShowDialog");  
	    Log.d(TAG,"onRestoreInstanceState mShowDialog " + mShowDialog + " mTime " + mTime);
	}
	
	protected void setSplashImage(Bitmap bmp) {
		mSplashBmp = bmp;
	}
	
}
