package org.d.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.d.R;
import org.d.model.lycs.Charity;
import org.d.model.lycs.PricePoint;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharityImpactFragment extends BaseFragment {
    private static final String CHARITY_ARG = "charity";
    private Charity mCharity;

    public static CharityImpactFragment newInstance(Charity charity) {
        CharityImpactFragment fragment = new CharityImpactFragment();
        Bundle args = new Bundle();
        args.putParcelable(CHARITY_ARG, charity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCharity = getArguments().getParcelable(CHARITY_ARG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_charity_impact, container, false);
        setRetainInstance(true);
        bind(rootView);
        return rootView;
    }

    class ImpactGridAdapter extends RecyclerView.Adapter {

        private final List<PricePoint> mPricePoints;

        ImpactGridAdapter(List<PricePoint> pricePoints) {
            mPricePoints = pricePoints;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.card_view_charity_impact, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if ((position >= 0) && (position < mPricePoints.size())) {
                PricePoint pricePoint = mPricePoints.get(position);
                ((ImpactGridAdapter.ViewHolder)holder).bind(pricePoint);
            }
        }

        @Override
        public int getItemCount() {
            return mPricePoints.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.impact_description)
            TextView mImpactDesctription;
            View mItemView;
            private PricePoint mPricePoint;

            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                mItemView = itemView;
                mItemView.setOnClickListener(view -> {});
            }

            void bind(PricePoint pricePoint) {
                mPricePoint = pricePoint;
                mImpactDesctription.setText(mPricePoint.getText().getSingle());
            }

        }
    }

}
