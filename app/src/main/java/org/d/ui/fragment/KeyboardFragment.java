package org.d.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.d.R;
import org.d.ui.view.KeyButton;

import java.text.DecimalFormatSymbols;

import butterknife.BindView;
import butterknife.OnClick;

public class KeyboardFragment extends SavedMoneyBaseFragment {
    public static final double MAX_VALUE = 99999.99;
    private final char mDecimalSeparator;
    @NonNull private String mText = "";

    @BindView(R.id.dot)
    KeyButton mDot;

    public KeyboardFragment() {
        super();
        mDecimalSeparator = DecimalFormatSymbols.getInstance().getDecimalSeparator();
    }

    public static KeyboardFragment newInstance() {
        KeyboardFragment fragment = new KeyboardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onMoneySavedChanged(double moneySaved) {
        mPiggyBank.set(moneySaved);
        onMoneySavedChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.keyboard_layout, container, false);
        setRetainInstance(true);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind(view);
        mDot.setText(String.valueOf(mDecimalSeparator));
    }

    @OnClick({R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9})
    public void newDigit(View view) {
            int posDot = mText.indexOf(mDecimalSeparator);
            if (posDot < 0 || posDot != mText.length() - 3) {
                String newText = mText + ((KeyButton) view).getText();
                Double value = Double.valueOf(newText);
                if (value <= MAX_VALUE) {
                    mText = newText;
                    mOnAddClickedSubject.onNext(value);
                }
            }
    }

    @OnClick(R.id.back_space_button)
    void onBackSpace() {
        if (mText.length() > 1) {
            int endIndex = mText.length() - 1;
            char endChar = mText.charAt(endIndex - 1);
            mText = mText.substring(0, endChar == mDecimalSeparator ? endIndex - 1 : endIndex);
            mOnAddClickedSubject.onNext(Double.valueOf(mText));
        } else {
            mText = "";
            mOnAddClickedSubject.onNext(0d);
        }
    }

    @OnClick(R.id.dot)
    void onDotClicked() {
        if (mText.indexOf(mDecimalSeparator) < 0) {
            mText += mDecimalSeparator;
        }
        onMoneySavedChanged();
    }

    @Override
    protected void onMoneySavedChanged() {
        super.onMoneySavedChanged();
        if (mPiggyBank.getMoneySaved() == 0) {
            mText = "";
        }
    }
}
