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
import butterknife.BindDimen;
import butterknife.ButterKnife;

public class KeyButton extends View {
    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private String mText;

    public @BindColor(R.color.colorAccent) int mColor;
    public @BindDimen(R.dimen.text_size_large) float mTextDimen;
    public @BindDimen(R.dimen.default_key_button_dim) int mDefaultButtonDim;

    private float mTextX;
    private float mTextY;

    public KeyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttrs(attrs, 0);
        init();
    }

    public KeyButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        parseAttrs(attrs, defStyle);
        init();
    }

    private void init() {
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        initViews();
    }

    private void parseAttrs(AttributeSet attrs, int defStyle) {
        ButterKnife.bind(getRootView());
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AppButton, defStyle, 0);
        mColor = a.getColor(R.styleable.AppButton_color, mColor);
        mText  = (a.hasValue(R.styleable.AppButton_text)) ? a.getString(R.styleable.AppButton_text) : "";
        mTextDimen  = a.getDimension(R.styleable.AppButton_textDim, mTextDimen);
        a.recycle();
    }

    private void initViews() {
        mTextPaint.setTextSize(mTextDimen);
        mTextPaint.setColor(mColor);
        mTextWidth = mTextPaint.measureText(mText);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom + fontMetrics.descent;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = resolveSizeAndState(mDefaultButtonDim, widthMeasureSpec, 0);
        int measuredHeight = resolveSizeAndState(mDefaultButtonDim, heightMeasureSpec, 0);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mTextX = (w - mTextWidth) / 2;
        mTextY = (h + mTextHeight) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(mText,
                mTextX,
                mTextY,
                mTextPaint);
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
       mText = text;
    }
}
