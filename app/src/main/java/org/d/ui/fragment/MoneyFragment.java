package org.d.ui.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.d.App;
import org.d.R;
import org.d.data.AppData;
import org.d.model.MoneySaved;
import org.d.model.MoneySavedAdapterEntry;
import org.d.util.DateUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import static org.d.ui.fragment.MoneyAdapter.UNDO_DELETE_TIMEOUT;

public class MoneyFragment extends Fragment {

    @Inject AppData mAppData;

    private RecyclerView mRecyclerView;
    @Inject DateUtil mDateUtil;

    public MoneyFragment() {
    }

    @SuppressWarnings("unused")
    public static MoneyFragment newInstance() {
        MoneyFragment fragment = new MoneyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_money_list, container, false);
        App app = (App) getActivity().getApplication();
        app.getDataComponent().inject(this);
        app.getAppComponent().inject(this);

        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAppData.listSavings(moneySavedArray -> {
            ArrayList<MoneySavedAdapterEntry> moneySaved = new ArrayList<>();
            mRecyclerView.setAdapter(new MoneyAdapter(app, moneySavedArray, mDateUtil));
            setUpItemTouchHelper();
            setUpAnimationDecoratorHelper();
        });

        return view;
    }

    /**
     * This is the standard support library way of implementing "swipe to delete" feature. You can do custom drawing in onChildDraw method
     * but whatever you draw will disappear once the swipe is over, and while the items are animating to their new position the recycler view
     * background will be visible. That is rarely an desired effect.
     */
    private void setUpItemTouchHelper() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // we want to cache these and not allocate anything repeatedly in the onChildDraw method
            Drawable mBackground;
            Drawable mUndoMark;
            int mUndoMarkMargin;
            boolean mInitiated;

            private void init() {
                mBackground = new ColorDrawable(Color.RED);
                mUndoMark = ContextCompat.getDrawable(getContext(), R.drawable.ic_clear);
                mUndoMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                mUndoMarkMargin = (int) getResources().getDimension(R.dimen.default_margin);
                mInitiated = true;
            }

            // not important, we don't want drag & drop
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                MoneyAdapter adapter = (MoneyAdapter)recyclerView.getAdapter();
                if (adapter.isPendingRemoval(position)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                View undo = mRecyclerView.getLayoutManager().findViewByPosition(position).findViewById(R.id.undo);
                undo.setVisibility(View.VISIBLE);
                MoneyAdapter adapter = (MoneyAdapter) mRecyclerView.getAdapter();
                Message message = new Message();
                if (adapter.pendingRemoval(position)) {
                    mHandler.dispatchMessage();
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        adapter.remove(position);
                    }, UNDO_DELETE_TIMEOUT);
                }
            }

            Handler mHandler = new MoneyHandler();

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.getAdapterPosition() == -1) {
                    // not interested in those
                    return;
                }

                if (!mInitiated) {
                    init();
                }

                // draw red mBackground
                mBackground.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                mBackground.draw(c);

                // draw x mark
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = mUndoMark.getIntrinsicWidth();
                int intrinsicHeight = mUndoMark.getIntrinsicWidth();

                int xMarkLeft = itemView.getRight() - mUndoMarkMargin - intrinsicWidth;
                int xMarkRight = itemView.getRight() - mUndoMarkMargin;
                int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight)/2;
                int xMarkBottom = xMarkTop + intrinsicHeight;
                mUndoMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                mUndoMark.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void setUpAnimationDecoratorHelper() {
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            // we want to cache this and not allocate anything repeatedly in the onDraw method
            Drawable mBackground;
            boolean mInitiated;

            private void init() {
                mBackground = new ColorDrawable(Color.RED);
                mInitiated = true;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                if (!mInitiated) {
                    init();
                }

                // only if animation is in progress
                if (parent.getItemAnimator().isRunning()) {

                    // some items might be animating down and some items might be animating up to close the gap left by the removed item
                    // this is not exclusive, both movement can be happening at the same time
                    // to reproduce this leave just enough items so the first one and the last one would be just a little off screen
                    // then remove one from the middle

                    // find first child with translationY > 0
                    // and last one with translationY < 0
                    // we're after a rect that is not covered in recycler-view views at this point in time
                    View lastViewComingDown = null;
                    View firstViewComingUp = null;

                    // this is fixed
                    int left = 0;
                    int right = parent.getWidth();

                    // this we need to find out
                    int top = 0;
                    int bottom = 0;

                    // find relevant translating views
                    int childCount = parent.getLayoutManager().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = parent.getLayoutManager().getChildAt(i);
                        if (child.getTranslationY() < 0) {
                            // view is coming down
                            lastViewComingDown = child;
                        } else if (child.getTranslationY() > 0) {
                            // view is coming up
                            if (firstViewComingUp == null) {
                                firstViewComingUp = child;
                            }
                        }
                    }

                    if (lastViewComingDown != null && firstViewComingUp != null) {
                        // views are coming down AND going up to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    } else if (lastViewComingDown != null) {
                        // views are going down to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = lastViewComingDown.getBottom();
                    } else if (firstViewComingUp != null) {
                        // views are coming up to fill the void
                        top = firstViewComingUp.getTop();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    }

                    mBackground.setBounds(left, top, right, bottom);
                    mBackground.draw(c);

                }
                super.onDraw(c, parent, state);
            }

        });
    }

    static private class MoneyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }

    }
}
