/*
 * This view extends ImageView and allow using zoom gesture on it
 */

package mar3kk.library.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;


public class BetterImageView extends ImageView implements OnTouchListener {
	
	private static final int MODE_NONE = 0;
	private static final int MODE_ZOOM = 1;
	private static final int MODE_MOVE = 2;
	
	private Matrix matrix, savedMatrix;
	private int mMode;
	private float mStartX,mStartY,mOldDistance,mNewDistance,mMidX,mMidY;
	
	public BetterImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setOnTouchListener(this);
		matrix = new Matrix();
		savedMatrix = new Matrix();

	}
	
	public BetterImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnTouchListener(this);
		matrix = new Matrix();
		savedMatrix = new Matrix();
	
	}
	
	public BetterImageView(Context context) {
		super(context);
		this.setOnTouchListener(this);
		matrix = new Matrix();
		savedMatrix = new Matrix();
	
	}
	
	@Override
	public void onDraw(Canvas c) {
		//c.setMatrix(matrix);
		super.onDraw(c);
		
		Log.d("DRAW","onDraw");
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		
		case MotionEvent.ACTION_DOWN:
			matrix = this.getImageMatrix();
			mMode = MODE_MOVE;
			mStartX = event.getX();
			mStartY = event.getY();
			savedMatrix.set(matrix);
			break;
		
		case MotionEvent.ACTION_MOVE:
			if (mMode == MODE_MOVE) {
				matrix.set(savedMatrix);
				matrix.postTranslate(event.getX()-mStartX, event.getY()-mStartY);
			}
			else if (mMode == MODE_ZOOM) {
				mNewDistance = spacing(event);
        		if (mNewDistance > 20f) {
        			matrix.set(savedMatrix);
        			matrix.postScale(mNewDistance/mOldDistance,mNewDistance/mOldDistance,mMidX,mMidY);
        		}
			}
			invalidate();
			break;
		
		case MotionEvent.ACTION_POINTER_DOWN:
			mMode = MODE_ZOOM;
			mMidX = (event.getX(0) + event.getX(1))/2;
	    	mMidY = (event.getY(0) + event.getY(1))/2;
	    	mOldDistance = spacing(event);
			break;
			
		case MotionEvent.ACTION_POINTER_UP:
			mMode = MODE_NONE;
			break;
			
		case MotionEvent.ACTION_UP:
			mMode = MODE_NONE;
			break;
		}
		return true;
	}
	
	private float spacing(MotionEvent event) {
    	float x = event.getX(0) - event.getX(1);
    	float y = event.getY(0) - event.getY(1);
    	return (float)Math.sqrt(x * x + y * y);
	}
    


	
}
