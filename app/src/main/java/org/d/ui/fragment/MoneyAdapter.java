package org.d.ui.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import org.d.App;
import org.d.R;
import org.d.model.MoneySaved;
import org.d.util.CompatUtil;
import org.d.util.DateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoneyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DateUtil mDateUtil;
    private final ArrayList<MoneySaved> mMoneySavedArray;
    private ArrayList<Boolean> mItems;
    @Inject CompatUtil mCompatUtil;

    MoneyAdapter(App app, ArrayList<MoneySaved> moneySavedArray, DateUtil dateUtil) {
        app.getAppComponent().inject(this);
        mMoneySavedArray = moneySavedArray;
        mItems = new ArrayList<>(Arrays.asList(new Boolean[mMoneySavedArray.size()]));
        Collections.fill(mItems, Boolean.FALSE);
        mDateUtil = dateUtil;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_money, parent, false);
        return new MoneyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        MoneyViewHolder moneyViewHolder = (MoneyViewHolder) holder;

        TextView time = moneyViewHolder.getTime();
        TextView money = moneyViewHolder.getMoney();
        TextView undo = moneyViewHolder.getUndo();
        if (mItems.get(position)) {
            moneyViewHolder.itemView.setBackgroundColor(Color.RED);
            time.setVisibility(View.GONE);
            money.setVisibility(View.GONE);
            undo.setVisibility(View.VISIBLE);
        } else {
            moneyViewHolder.itemView.setBackgroundColor(mCompatUtil.getColor(R.color.colorPrimary));
            MoneySaved moneySaved = mMoneySavedArray.get(position);
            time.setVisibility(View.VISIBLE);
            time.setText(mDateUtil.formatDateTime(moneySaved.getTime()));
            money.setText(String.valueOf(moneySaved.getMoney()));
            money.setVisibility(View.VISIBLE);
            undo.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mMoneySavedArray.size();
    }

    boolean isPendingRemoval(int position) {
        return mItems.get(position);
    }

    void pendingRemoval(int position) {
        mItems.set(position, true);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (position < mItems.size() && mItems.get(position)) {
                remove(position);
            }
        }, 2000);
        notifyItemChanged(position);
    }

    private void remove(int position) {
        mItems.remove(position);
        mMoneySavedArray.remove(position);
        notifyItemRemoved(position);
    }

    @SuppressWarnings("WeakerAccess")
    class MoneyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.date) TextView mTime;
        @BindView(R.id.money) TextView mMoney;
        @BindView(R.id.undo) TextView mUndo;

        MoneyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            RxView.clicks(mUndo).subscribe(aVoid -> {
                int position = getAdapterPosition();
                if (position < mItems.size()) {
                    mItems.set(position, false);
                    notifyItemChanged(position);
                }
            });
        }

        public TextView getMoney() {
            return mMoney;
        }

        public TextView getUndo() {
            return mUndo;
        }

        public TextView getTime() {
            return mTime;
        }
    }
}
