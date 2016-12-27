package org.d.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.d.R;
import org.d.data.DataComponent;
import org.d.data.PiggyBank;
import org.d.model.lycs.Charity;
import org.d.model.lycs.PricePoint;
import org.d.network.tlycs.TheLifeYouCanSaveHelper;
import org.d.util.UiUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CharityImpactFragment extends BaseFragment {
    private static final String CHARITY_ARG = "charity";
    private static final String MONEY = "money_saved";
    private Charity mCharity;
    private Double mMoney = 0.;

    @BindView(R.id.money) EditText mMoneyText;
    @BindView(R.id.impact_list) RecyclerView mImpactList;

    @Inject TheLifeYouCanSaveHelper mTLYCSHelper;
    @Inject UiUtil mUiUtil;
    @Inject PiggyBank mPiggyBank;

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
        mImpactList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mUiUtil.dismissKeyboard(rootView);
            }
        });
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
        if (savedInstanceState == null) {
            mPiggyBank.getTotalMoneySaved(money -> {
                money += mPiggyBank.getMoneyToSave();
                mMoneyText.setText(String.valueOf(money));
            });
        } else {
            mMoneyText.setText(String.valueOf(savedInstanceState.getDouble(MONEY)));
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(MONEY, mMoney);
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
            @BindDimen(R.dimen.impact_image_size) int mImpactImageSize;

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
                Glide.with(getContext())
                        .load(mTLYCSHelper.getImageUrl(mPricePoint.getIconURL()))
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into(new SimpleTarget<Bitmap>(mImpactImageSize,mImpactImageSize) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                mImpactDescription.setCompoundDrawablesWithIntrinsicBounds(null, new BitmapDrawable(mImpactDescription.getResources(),resource), null, null);
                            }
                });
                int amount = (int) (mMoney / pricePoint.getPrice());
                if (amount >= 2) {
                    try {
                        mImpactDescription.setText(String.format(mPricePoint.getText().getPlural(), amount));
                    } catch (Exception e) {
                        Timber.e(e);
                    }
                } else if (amount >= 1) {
                    mImpactDescription.setText(mPricePoint.getText().getSingle());
                } else {
                    mImpactDescription.setText(mCharity.getDefaultText());
                }
            }
        }
    }

}
