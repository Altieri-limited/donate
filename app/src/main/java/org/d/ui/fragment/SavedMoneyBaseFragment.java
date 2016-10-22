package org.d.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.d.R;
import org.d.data.PiggyBank;
import org.d.observable.ObservableUtil;
import org.d.ui.widget.AmountView;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public abstract class SavedMoneyBaseFragment extends BaseFragment {

    private static final String MONEY_SAVED = "MONEY_SAVED";
    protected BehaviorSubject<Void> mOnStoreClickedSubject;
    protected BehaviorSubject<Double> mOnAddClickedSubject;
    protected BehaviorSubject<Void> mOnSendMoneySubject;
    private Subscription mOnStoreClickedSubscription;
    private Subscription mOnAddClickedSubscription;
    private Subscription mSendMoneySubscription;

    @NonNull
    protected PiggyBank mPiggyBank;

    @BindView(R.id.fab)
    View mFAB;

    @BindView(R.id.amount_view)
    AmountView mMoneySaved;

    public SavedMoneyBaseFragment() {
        mPiggyBank = new PiggyBank();
        mOnStoreClickedSubject = BehaviorSubject.create();
        mOnAddClickedSubject = BehaviorSubject.create();
        mOnSendMoneySubject = BehaviorSubject.create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @OnClick(R.id.fab)
    void onSendMoney() {
        mOnSendMoneySubject.onNext(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        mOnStoreClickedSubscription = new ObservableUtil<Void>().asObservable(mOnStoreClickedSubject).subscribe(aVoid -> {
            mPiggyBank.store();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_save:
                mOnStoreClickedSubject.onNext(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected abstract void onMoneySavedChanged(double moneySaved);

    protected void onMoneySavedChanged() {
        double moneySaved = mPiggyBank.getMoneySaved();
        if (moneySaved > 0) {
            mFAB.setVisibility(View.VISIBLE);
            mMoneySaved.setAmount(moneySaved);
        } else {
            mFAB.setVisibility(View.GONE);
            mMoneySaved.clear();
        }

        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem saved = menu.findItem(R.id.action_save);
        saved.setVisible(mPiggyBank.getMoneySaved() > 0);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(MONEY_SAVED, mPiggyBank.getMoneySaved());
    }
}
