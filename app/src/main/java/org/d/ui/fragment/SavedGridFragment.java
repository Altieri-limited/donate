package org.d.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.d.R;
import org.d.model.MoneySavedOption;
import org.d.ui.view.RoundedButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SavedGridFragment extends SavedMoneyBaseFragment {

    private static final String MONEY_SAVED_OPTION_ARGS = "money_saved_args";
    public static final int NUM_COLUMNS = 2;
    private ArrayList<MoneySavedOption> mMoneySavedOptions;

    @BindView(R.id.options_grid)
    RecyclerView mOptionsGrid;

    public SavedGridFragment() {
    }

    public static SavedGridFragment newInstance(ArrayList<MoneySavedOption> moneySavedOptions) {
        SavedGridFragment fragment = new SavedGridFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(MONEY_SAVED_OPTION_ARGS, moneySavedOptions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onMoneySavedChanged(double newSaved) {
        mPiggyBank.add(newSaved);
        onMoneySavedChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_options_grid, container, false);
        bind(rootView);
        mMoneySavedOptions = getArguments().getParcelableArrayList(MONEY_SAVED_OPTION_ARGS);
        mOptionsGrid.setAdapter(new OptionsGridAdapter());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), NUM_COLUMNS);
        mOptionsGrid.setLayoutManager(layoutManager);
        mOptionsGrid.setHasFixedSize(true);
        return rootView;
    }

    class OptionsGridAdapter extends RecyclerView.Adapter {

        OptionsGridAdapter() {
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.view_rounded_option, parent, false);
            return new OptionViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if ((position >= 0) && (position < mMoneySavedOptions.size())) {
                MoneySavedOption option = mMoneySavedOptions.get(position);
                ((OptionViewHolder)holder).bind(option);
            }
        }

        @Override
        public int getItemCount() {
            return mMoneySavedOptions.size();
        }

        class OptionViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.option_text)
            RoundedButton mTextView;
            View mItemView;
            private MoneySavedOption mOption;

            OptionViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                mItemView = itemView;
            }

            void bind(MoneySavedOption option) {
                mOption = option;
                mTextView.setText(option.getText());
            }

            @OnClick(R.id.option_text)
            void onSave() {
                mOnAddClickedSubject.onNext(mOption.getValue());
            }
        }
    }
}
