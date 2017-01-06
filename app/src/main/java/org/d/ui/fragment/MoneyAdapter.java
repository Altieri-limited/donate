package org.d.ui.fragment;

import android.graphics.Color;
import android.os.Message;
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
import rx.subjects.PublishSubject;

public class MoneyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private PublishSubject<Message> mUndoSubject;
    private ArrayList<MoneySaved> mMoneySavedArray;
    private ArrayList<Message> mItems;
    @Inject CompatUtil mCompatUtil;
    @Inject DateUtil mDateUtil;

    MoneyAdapter(App app, PublishSubject<Message> undoSubject) {
        app.getAppComponent().inject(this);
        mMoneySavedArray = new ArrayList<>();
        mItems = new ArrayList<>(Arrays.asList(new Message[mMoneySavedArray.size()]));
        Collections.fill(mItems, null);
        mUndoSubject = undoSubject;
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
        if (mItems.get(position) != null) {
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
        return mItems.get(position) != null;
    }

    void pendingRemoval(int position, Message message) {
        mItems.set(position, message);
        notifyItemChanged(position);
    }

    String remove(int position) {
        if (position >=0 && position < mItems.size() && position < mMoneySavedArray.size()) {
            mItems.remove(position);
            MoneySaved moneySaved = mMoneySavedArray.get(position);
            mMoneySavedArray.remove(position);
            notifyItemRemoved(position);
            return moneySaved.getTime();
        }
        return null;
    }

    void init(ArrayList<MoneySaved> moneySavedArray) {
        mMoneySavedArray = moneySavedArray;
        mItems = new ArrayList<>(Arrays.asList(new Message[mMoneySavedArray.size()]));
        Collections.fill(mItems, null);
        notifyDataSetChanged();
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
                if (position  >= 0 && position < mItems.size()) {
                    mUndoSubject.onNext(mItems.get(position));
                    mItems.set(position, null);
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
