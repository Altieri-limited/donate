package org.d.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;

import org.d.R;

public class ClearableEditText extends TextInputEditText implements OnTouchListener,
        OnFocusChangeListener, TextWatcher {
    final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    final int DRAWABLE_BOTTOM = 3;
    // index associated with the setCompoundDrawablesRelative method
    final int DRAWABLE_START = DRAWABLE_LEFT;
    final int DRAWABLE_END = DRAWABLE_RIGHT;

    public interface Listener {
        void didClearText();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private Drawable mIcon;
    private Listener listener;

    public ClearableEditText(Context context) {
        super(context);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        this.l = l;
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener f) {
        this.f = f;
    }

    private OnTouchListener l;
    private OnFocusChangeListener f;

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (getCompoundDrawables()[DRAWABLE_END] != null) {
            float rightIconPosition = getWidth() - getPaddingRight() - mIcon.getIntrinsicWidth();
            boolean tappedX = event.getX() >= rightIconPosition;

            if (tappedX) {
                onIconClick(event);
                return true;
            }
        } else if (getCompoundDrawables()[DRAWABLE_START] != null) {
            float leftIconPosition = getPaddingLeft() + mIcon.getIntrinsicWidth();
            boolean tappedX = event.getX() <= leftIconPosition;

            if (tappedX) {
                onIconClick(event);
                return true;
            }
        }

        return l != null && l.onTouch(v, event);
    }

    private boolean onIconClick(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            clearText();
            return true;
        }
        return false;
    }

    private void clearText() {
        setText("");
        if (listener != null) {
            listener.didClearText();
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            String text = getText().toString();
            setClearIconVisible(!text.isEmpty());
        } else {
            setClearIconVisible(false);
        }
        if (f != null) {
            f.onFocusChange(v, hasFocus);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isFocused()) {
            setClearIconVisible(s != null && !s.toString().isEmpty());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void init() {
        mIcon = getCompoundDrawableEnd();
        if (mIcon == null) {
            // this means a drawableRight was not set through XML
            mIcon = getResources()
                    .getDrawable(R.drawable.ic_clear);
        }
        mIcon.setBounds(0, 0, mIcon.getIntrinsicWidth(), mIcon.getIntrinsicHeight());

        setClearIconVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    private Drawable getCompoundDrawableEnd() {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            drawable = getCompoundDrawablesRelative()[DRAWABLE_END];
        } else {
            drawable = getCompoundDrawables()[DRAWABLE_RIGHT];
        }
        return drawable;
    }

    private Drawable getCompoundDrawableStart() {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            drawable = getCompoundDrawablesRelative()[DRAWABLE_START];
        } else {
            drawable = getCompoundDrawables()[DRAWABLE_LEFT];
        }
        return drawable;
    }

    protected void setClearIconVisible(boolean visible) {
        Drawable iconToShow = visible ? mIcon : null;
        setCompoundDrawableEnd(iconToShow);
    }

    /**
     * sets the icon to the correct place inside the editText. when jelly bean or above we get the
     * drawableEnd. the drawableEnd changes position depending if the app is in LTR or RTL mode
     *
     * @param iconToShow the icon to show
     */
    private void setCompoundDrawableEnd(@Nullable Drawable iconToShow) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setCompoundDrawablesRelative(getCompoundDrawableStart(), null, iconToShow, null);
        } else{
            setCompoundDrawables(getCompoundDrawableStart(), null, iconToShow, null);
        }
    }
}