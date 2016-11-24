package org.d.ui.fragment;

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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import timber.log.Timber;

public class CharitiesFragment extends BaseFragment {

    public static final int NUM_COLUMNS = 3;
    private static final String CHARITIES = "charities";
    private ArrayList<Charity> mCharities;
    @Inject
    AppData mAppData;

    @BindView(R.id.charities_grid)
    RecyclerView mCharitiesGrid;

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
        View rootView = inflater.inflate(R.layout.charities_grid_fragment, container, false);
        bind(rootView);
        ((App) getActivity().getApplication()).getDataComponent().inject(this);

        mAppData.getCharities(new Observer<List<Charity>>() {
            @Override
            public void onCompleted() {
                mCharitiesGrid.setAdapter(new CharityGridAdapter());
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

    class CharityGridAdapter extends RecyclerView.Adapter {

        CharityGridAdapter() {
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.charity_card_view, parent, false);
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

            CharityViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                mItemView = itemView;
            }

            void bind(Charity charity) {
                mTextView.setText(charity.getName());
            }

        }
    }
}
