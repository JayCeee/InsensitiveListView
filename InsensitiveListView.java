import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.ListView;

/**
 * ListView that isn't sensitive to horizontal scrolling
 * 
 * @author duncandee@gmail.com
 * 
 */
public class InsensitiveListView extends ListView {
	private static final String TAG = "InsensitiveListView";

	private VelocityTracker mVelocityTracker;
	private float mDownX;
	private int mViewWidth = 1;
	private float mDownY;

	public InsensitiveListView(Context context) {
		super(context);
		init();
	}

	public InsensitiveListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public InsensitiveListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (mViewWidth < 2) {
			mViewWidth = getWidth();
		}

		switch (ev.getActionMasked()) {
		case MotionEvent.ACTION_DOWN: {

			mVelocityTracker = VelocityTracker.obtain();
			mVelocityTracker.addMovement(ev);

			mDownX = ev.getRawX();
			mDownY = ev.getRawY();

			break;
		}
		case MotionEvent.ACTION_UP: {
			if (mVelocityTracker == null) {
				break;
			}

			mVelocityTracker.addMovement(ev);
			mVelocityTracker.computeCurrentVelocity(1000);
			mDownX = 0;

			break;
		}

		case MotionEvent.ACTION_MOVE: {
			if (mVelocityTracker == null) {
				break;
			}

			mVelocityTracker.addMovement(ev);
			float deltaX = ev.getRawX() - mDownX;
			float deltaY = ev.getRawY() - mDownY;

			if (Math.abs(deltaY) < Math.abs(deltaX)) {
				return false;
			}

			break;
		}
		}

		return super.onInterceptTouchEvent(ev);
	}

}
