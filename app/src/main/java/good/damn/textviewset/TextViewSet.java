package good.damn.textviewset;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import good.damn.textviewset.models.Text;

public class TextViewSet extends View {

    private static final String TAG = "TextViewSet";

    private Paint mPaint;
    private Paint mPaintAnimate;

    private Text[] mTexts;

    private ValueAnimator mAnimatorAlpha;

    private byte mCurrentAnimationIndex;

    private float mBeginY;
    private float midWidth;
    private float midHeight;

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(0xffff0000);
        mPaint.setTextSize(18.0f);

        mPaintAnimate = new Paint();
        mPaintAnimate.setColor(0xffff0000);
        mPaintAnimate.setTextSize(18.0f);

        mAnimatorAlpha = new ValueAnimator();

        mAnimatorAlpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                mPaintAnimate.setAlpha((int) (valueAnimator.getAnimatedFraction() * 255));
                invalidate();
            }
        });
        mAnimatorAlpha.setIntValues(0,1);
        mAnimatorAlpha.setDuration(1000);

        mAnimatorAlpha.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(@NonNull Animator animator) {
                mCurrentAnimationIndex++;
                if (mCurrentAnimationIndex >= mTexts.length) {
                    return;
                }
                mAnimatorAlpha.start();
            }
            @Override public void onAnimationStart(@NonNull Animator animator) {}
            @Override public void onAnimationCancel(@NonNull Animator animator) {}
            @Override public void onAnimationRepeat(@NonNull Animator animator) {}
        });
    }

    public TextViewSet(Context context) {
        super(context);
        init();
    }

    public TextViewSet(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewSet(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setTypeface(Typeface typeface) {
        mPaint.setTypeface(typeface);
    }

    public void setSource(String[] arr) {
        mTexts = new Text[arr.length];
        for (byte i = 0; i < arr.length; i++) {
            mTexts[i] = new Text(arr[i], mPaint.measureText(arr[i]));
        }
    }

    public void start() {
        mCurrentAnimationIndex = 0;
        mAnimatorAlpha.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mCurrentAnimationIndex >= mTexts.length) {
            mCurrentAnimationIndex = 4;
        }

        canvas.drawLine(midWidth,0,midWidth,getHeight(),mPaint);
        canvas.drawLine(0,midHeight,getWidth(),midHeight,mPaint);

        float y = mPaint.getTextSize();
        for (byte i = 0; i < mCurrentAnimationIndex; i++) {
            canvas.drawText(mTexts[i].getText(),midWidth-mTexts[i].getWidth()/ 2,mBeginY+y,mPaint);
            y += mPaint.getTextSize();
        }

        canvas.drawText(mTexts[mCurrentAnimationIndex].getText(),
                midWidth-mTexts[mCurrentAnimationIndex].getWidth() / 2,
                mBeginY+y,
                mPaintAnimate);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        midWidth = getWidth() >> 1;
        midHeight = getHeight() >> 1;

        mBeginY = midHeight - mPaint.getTextSize() * mTexts.length / 2;
    }

}
