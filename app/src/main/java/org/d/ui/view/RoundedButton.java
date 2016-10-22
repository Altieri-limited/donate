package org.d.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import org.d.R;

import butterknife.BindColor;
import butterknife.ButterKnife;

public class RoundedButton extends View {
    public static final int STROKE_WIDTH = 8;
    private float mTextDimen;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private String mText;
    private int mPaddingLeft;
    private int mPaddingTop;
    private int mPaddingRight;
    private int mPaddingBottom;
    private Paint mPaint;
    private float mButtonDimen;

    public @BindColor(R.color.colorAccent) int mColor;
    private float mTextX;
    private float mTextY;
    private int mCircleY;
    private int mCircleX;
    private int mRadius;

    public RoundedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttrs(attrs, 0);
        init();
    }

    public RoundedButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        parseAttrs(attrs, defStyle);
        init();
    }

    private void init() {
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        mPaddingLeft = getPaddingLeft();
        mPaddingTop = getPaddingTop();
        mPaddingRight = getPaddingRight();
        mPaddingBottom = getPaddingBottom();

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(STROKE_WIDTH);

        initViews();
    }

    private void parseAttrs(AttributeSet attrs, int defStyle) {
        ButterKnife.bind(getRootView());
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AppButton, defStyle, 0);
        mColor = a.getColor(R.styleable.AppButton_color, mColor);
        mText  = (a.hasValue(R.styleable.AppButton_text)) ? a.getString(R.styleable.AppButton_text) : "";
        mTextDimen = getResources().getDimension(R.dimen.text_size_large);
        mTextDimen  = a.getDimension(R.styleable.AppButton_textDim, mTextDimen);
        mButtonDimen  = getResources().getDimension(R.dimen.default_button_dim);
        mButtonDimen  = a.getDimension(R.styleable.AppButton_buttonDim, mButtonDimen);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredWidth = (int) mButtonDimen;
        int desiredHeight = (int) mButtonDimen;

        int measuredWidth = resolveSizeAndState(desiredWidth, widthMeasureSpec, 0);
        int measuredHeight = resolveSizeAndState(desiredHeight, heightMeasureSpec, 0);
        setMeasuredDimension(measuredWidth, measuredHeight);

    }

    private void initViews() {
        mTextPaint.setTextSize(mTextDimen);
        mTextPaint.setColor(mColor);
        mTextWidth = mTextPaint.measureText(mText);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom + fontMetrics.descent;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int mContentWidth = w - mPaddingLeft - mPaddingRight;
        int mContentHeight = h - mPaddingTop - mPaddingBottom;

        mTextX = mPaddingLeft + (mContentWidth - mTextWidth) / 2;
        mTextY = mPaddingTop + (mContentHeight + mTextHeight) / 2;
        mCircleX = mContentWidth / 2 + mPaddingLeft;
        mCircleY = mContentHeight / 2 + mPaddingTop;
        double ratio = 2.5;
        mRadius = (int) Math.min(mContentWidth / ratio, mContentWidth / ratio);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(mText,
                mTextX,
                mTextY,
                mTextPaint);
        canvas.drawCircle(mCircleX, mCircleY, mRadius, mPaint);
    }

    /**
     * Sets the text to draw.
     *
     * @param text The string value to use.
     */
    public void setText(String text) {
        mText = text;
        initViews();
        invalidate();
    }

    /**
     * Sets the text color
     *
     * @param color The color attribute value to use.
     */
    public void setColor(int color) {
        mColor = color;
        initViews();
        invalidate();
    }

    public String getText() {
        return mText;
    }
}
