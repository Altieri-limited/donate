package org.d.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.d.R;
import org.d.model.lycs.Charity;

import java.util.ArrayList;

class CharitiesAdapter extends PagerAdapter {

    private Context mContext;
    @NonNull private ArrayList<Charity> mCharities = new ArrayList<>();

    CharitiesAdapter(Context context, @NonNull ArrayList<Charity> charities) {
        mContext = context;
        mCharities = charities;
    }

    @Override
    public int getCount() {
        return mCharities.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.charity_header_view, container, false);
        container.addView(layout);
        ImageView image = (ImageView) layout.findViewById(R.id.charity_header_image);
        String name = mCharities.get(position).getId();
        String imageUrl = mContext.getString(R.string.charity_header_image_url, name);
        Glide.with(mContext)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(image);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Charity charity = mCharities.get(position);
        return charity.getName();
    }
}
