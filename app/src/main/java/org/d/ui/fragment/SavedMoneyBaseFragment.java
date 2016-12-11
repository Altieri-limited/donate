package org.d.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jakewharton.rxbinding.view.RxView;

import org.d.R;
import org.d.data.AppData;
import org.d.data.DataComponent;
import org.d.data.PiggyBank;
import org.d.model.MoneySaved;
import org.d.ui.widget.AmountView;
import org.d.util.ObservableUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import timber.log.Timber;

public abstract class SavedMoneyBaseFragment extends BaseFragment {
    private static final String MONEY_SAVED = "MONEY_SAVED";
    @Inject protected PublishSubject<Double> mOnAddClickedSubject;
    private Subscription mOnStoreClickedSubscription;
    private Subscription mOnAddClickedSubscription;
    private Subscription mSendMoneySubscription;

    @Inject protected PiggyBank mPiggyBank;
    @Inject protected AppData mAppData;

    @BindView(R.id.fab_save)
    View mSaveFab;

    @BindView(R.id.fab_send)
    View mSendFab;

    @BindView(R.id.amount_view)
    AmountView mMoneySaved;
    private Subscription mStoreOperationSubscription;

    public SavedMoneyBaseFragment() {
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

    @Override
    public void onResume() {
        super.onResume();
        Observer<Void> observer = new Observer<Void>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Void aVoid) {
                mAppData.listSavings(new Observer<ArrayList<MoneySaved>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<MoneySaved> moneySaveds) {
                        Timber.d(String.valueOf(moneySaveds));
                    }
                });
                onMoneySavedChanged();
            }
        };

        BehaviorSubject<Void> subject = BehaviorSubject.create();
        mStoreOperationSubscription = subject.subscribe(observer);

        mOnStoreClickedSubscription = RxView.clicks(mSaveFab).asObservable().subscribe(aVoid -> {
            mPiggyBank.store(subject);
        });

        mSendMoneySubscription = RxView.clicks(mSendFab).asObservable().subscribe(aVoid -> {
            Timber.d("send");
        });

        mOnAddClickedSubscription = new ObservableUtil<Double>().asObservable(mOnAddClickedSubject).subscribe(this::onMoneySavedChanged);
    }

    @Override
    protected void bind(View view) {
        super.bind(view);
        if (mPiggyBank.getMoneyToSave() != 0) {
            onMoneySavedChanged();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mOnAddClickedSubscription.unsubscribe();
        mOnStoreClickedSubscription.unsubscribe();
        mSendMoneySubscription.unsubscribe();
        mStoreOperationSubscription.unsubscribe();
    }

    protected abstract void onMoneySavedChanged(double moneySaved);

    protected void onMoneySavedChanged() {
        double moneySaved = mPiggyBank.getMoneyToSave();
        if (moneySaved > 0) {
            mMoneySaved.setAmount(moneySaved);
        } else {
            mMoneySaved.clear();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(MONEY_SAVED, mPiggyBank.getMoneyToSave());
    }
}
