package org.d.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.d.R;
import org.d.data.PiggyBank;
import org.d.data.DataComponent;
import org.d.model.MoneySaved;
import org.d.ui.widget.AmountView;
import org.d.util.ObservableUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;
import rx.subjects.BehaviorSubject;
import timber.log.Timber;

public abstract class SavedMoneyBaseFragment extends BaseFragment {
    private static final String MONEY_SAVED = "MONEY_SAVED";
    protected BehaviorSubject<Void> mOnStoreClickedSubject;
    protected BehaviorSubject<Double> mOnAddClickedSubject;
    protected BehaviorSubject<Void> mOnSendMoneySubject;
    private Subscription mOnStoreClickedSubscription;
    private Subscription mOnAddClickedSubscription;
    private Subscription mSendMoneySubscription;

    @Inject protected PiggyBank mPiggyBank;

    @BindView(R.id.fab_menu)
    View mFAB;

    @BindView(R.id.amount_view)
    AmountView mMoneySaved;

    @OnClick(R.id.fab_save)
    void onSave() {
        mPiggyBank.store();
    }

    @OnClick(R.id.fab_send)
    void onSend() {
        Timber.d("sending");
    }

    @OnClick(R.id.fab_calc)
    void onCalc() {
        Timber.d("calc");
    }

    public SavedMoneyBaseFragment() {
        mOnStoreClickedSubject = BehaviorSubject.create();
        mOnAddClickedSubject = BehaviorSubject.create();
        mOnSendMoneySubject = BehaviorSubject.create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(DataComponent.class).inject(this);
        if (savedInstanceState != null) {
            double moneySaved = savedInstanceState.getDouble(MONEY_SAVED);
            mPiggyBank.set(moneySaved);
        }
    }

    @OnClick(R.id.clear_button)
    void clear() {
        mPiggyBank.set(0);
        onMoneySavedChanged();
    }

    @OnClick(R.id.fab_save)
    void onSendMoney() {
        mOnSendMoneySubject.onNext(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        mOnStoreClickedSubscription = new ObservableUtil<Void>().asObservable(mOnStoreClickedSubject).subscribe(aVoid -> {
            mPiggyBank.store();
            List<MoneySaved> all = mPiggyBank.listAll();
            Timber.d(String.valueOf(all));
        });
        mOnAddClickedSubscription = new ObservableUtil<Double>().asObservable(mOnAddClickedSubject).subscribe(this::onMoneySavedChanged);
        mSendMoneySubscription = new ObservableUtil<Void>().asObservable(mOnSendMoneySubject).subscribe();
    }

    @Override
    protected void bind(View view) {
        super.bind(view);
        if (mPiggyBank.getMoneySaved() != 0) {
            onMoneySavedChanged();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mOnAddClickedSubscription.unsubscribe();
        mOnStoreClickedSubscription.unsubscribe();
        mSendMoneySubscription.unsubscribe();
    }

    protected abstract void onMoneySavedChanged(double moneySaved);

    protected void onMoneySavedChanged() {
        double moneySaved = mPiggyBank.getMoneySaved();
        if (moneySaved > 0) {
            mMoneySaved.setAmount(moneySaved);
        } else {
            mMoneySaved.clear();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(MONEY_SAVED, mPiggyBank.getMoneySaved());
    }
}
