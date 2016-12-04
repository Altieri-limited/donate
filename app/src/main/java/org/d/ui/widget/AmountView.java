package org.d.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.d.App;
import org.d.Command;
import org.d.R;
import org.d.util.CompatUtil;

import javax.inject.Inject;

public class AmountView extends RelativeLayout {

    @Inject CompatUtil mCompatUtil;

    public static final double INVALID = -1.;
    private TextView mAmountSaved;

    private final Command<Double> NOT_READY = new Command<Double>() {
        private double mAmount;

        @Override
        public void exec(Double amount) {
            if (mAmountSaved == null) {
                mAmount = amount;
            } else {
                if (amount == INVALID) {
                    mCommand = AmountView.this.ACTIVE;
                    mCommand.exec(mAmount);
                    setVisibility(VISIBLE);
                }
            }
        }
    };

    private final Command<Double> ACTIVE = amount -> mAmountSaved.setText(getContext().getString(R.string.money, amount));
    private Command<Double> mCommand;

    private final Command<Double> CLEARED = amount -> {
        mCommand = AmountView.this.ACTIVE;
        mCommand.exec(amount);
        setVisibility(VISIBLE);
    };


    public AmountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AmountView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        ((App) getContext().getApplicationContext()).getAppComponent().inject(this);
        setGravity(Gravity.CENTER_VERTICAL);
        mCommand = NOT_READY;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mCompatUtil.removeOnGlobalLayoutListener(AmountView.this, this);
                for (int i = 0; i < getChildCount(); ++i) {
                    View childAt = getChildAt(i);
                    if (childAt instanceof TextView) {
                        mAmountSaved = (TextView) childAt;
                        break;
                    }
                }
                mCommand.exec(INVALID);
            }
        });

    }

    public void setAmount(double amount) {
        mCommand.exec(amount);
    }

    public void clear() {
        setVisibility(INVISIBLE);
        mCommand = CLEARED;
    }
}
