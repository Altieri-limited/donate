package org.d.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.d.R;
import org.d.data.DataComponent;
import org.d.model.lycs.Charity;
import org.d.model.lycs.PricePoint;
import org.d.network.tlycs.TheLifeYouCanSaveHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharityImpactFragment extends BaseFragment {
    private static final String CHARITY_ARG = "charity";
    private Charity mCharity;
    private Double mMoney = 0.;

    @BindView(R.id.money) EditText mMoneyText;
    @BindView(R.id.impact_list) RecyclerView mImpactList;

    @Inject TheLifeYouCanSaveHelper mTLYCSHelper;

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
        getComponent(DataComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_charity_impact, container, false);
        setRetainInstance(true);
        bind(rootView);
        mImpactList.setLayoutManager(new LinearLayoutManager(getContext()));
        ImpactGridAdapter adapter = new ImpactGridAdapter(mCharity.getPricePoints());
        mImpactList.setAdapter(adapter);
        mMoneyText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String value = editable.toString();
                mMoney = (value.length() > 0) ? Double.valueOf(value) : 0;
                adapter.notifyDataSetChanged();
            }
        });
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
            @BindView(R.id.impact_description) TextView mImpactDescription;
            @BindView(R.id.impact_image) ImageView mImpactimage;

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
                Glide.with(getContext()).load(mTLYCSHelper.getImageUrl(mPricePoint.getIconURL())).into(mImpactimage);
                int amount = (int) (mMoney / pricePoint.getPrice());
                if (amount > 2) {
                    mImpactDescription.setText(String.format(mPricePoint.getText().getPlural(), amount));
                } else if (amount > 1) {
                    mImpactDescription.setText(mPricePoint.getText().getSingle());
                } else {
                    mImpactDescription.setText(mCharity.getDefaultText());
                }
            }
        }
    }

}
