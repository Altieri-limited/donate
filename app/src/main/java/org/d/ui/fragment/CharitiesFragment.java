package org.d.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.d.App;
import org.d.R;
import org.d.data.AppData;
import org.d.model.lycs.Charity;
import org.d.ui.activity.CharityImpactActivity;
import org.d.util.ObservableUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.subjects.PublishSubject;
import timber.log.Timber;

public class CharitiesFragment extends BaseFragment {

    public static final int NUM_COLUMNS = 3;
    private ArrayList<Charity> mCharities;
    private CharityGridAdapter mAdapter;
    private Subscription mOnCharityClickSubscription;

    @Inject AppData mAppData;
    @Inject PublishSubject<Charity> mOnCharityClickSubject;
    @Inject ObservableUtil<Charity> mObservableUtil;

    @BindView(R.id.charities_grid) RecyclerView mCharitiesGrid;

    public CharitiesFragment() {
    }

    public static CharitiesFragment newInstance() {
        CharitiesFragment fragment = new CharitiesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_charities_grid, container, false);
        bind(rootView);
        ((App) getActivity().getApplication()).getDataComponent().inject(this);
        mAdapter = new CharityGridAdapter();

        mAppData.getCharities(new Observer<List<Charity>>() {
            @Override
            public void onCompleted() {
                mCharitiesGrid.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
            }

            @Override
            public void onNext(List<Charity> charities) {
                mCharities = new ArrayList<>();
                mCharities.addAll(charities);
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), NUM_COLUMNS);
        mCharitiesGrid.setLayoutManager(layoutManager);
        mCharitiesGrid.setHasFixedSize(true);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mOnCharityClickSubscription = mObservableUtil.asObservable(mOnCharityClickSubject).subscribe(charity -> {
            Intent intent = new Intent(getActivity(), CharityImpactActivity.class);
            intent.putExtra(CharityImpactActivity.CHARITY_ARG, charity);
            startActivity(intent);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mOnCharityClickSubscription.unsubscribe();
    }

    class CharityGridAdapter extends RecyclerView.Adapter {

        CharityGridAdapter() {
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.card_view_charity, parent, false);
            return new CharityViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if ((position >= 0) && (position < mCharities.size())) {
                Charity charity = mCharities.get(position);
                ((CharityViewHolder)holder).bind(charity);
            }
        }

        @Override
        public int getItemCount() {
            return mCharities.size();
        }

        class CharityViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.charity_name)
            TextView mTextView;
            View mItemView;
            private Charity mCharity;

            CharityViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                mItemView = itemView;
                mItemView.setOnClickListener(view -> {});
            }

            void bind(Charity charity) {
                mCharity = charity;
                mTextView.setText(charity.getName());
                mItemView.setOnClickListener(view -> {
                    mOnCharityClickSubject.onNext(mCharity);
                });
            }

        }
    }
}
