package org.d.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.d.App;
import org.d.R;
import org.d.data.DataComponent;
import org.d.model.lycs.Charity;
import org.d.ui.HasComponent;
import org.d.ui.fragment.CharityDetailsFragment;
import org.d.ui.fragment.CharityImpactFragment;
import org.d.ui.widget.CharityActivityHeaderListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharityActivity extends BaseActivity implements HasComponent<DataComponent>,CharityActivityHeaderListener {
    public static final String CHARITY_POS = "charity_pos";
    public static final String CHARITIES_ARG = "charities";
    private ArrayList<Charity> mCharities;

    @BindView(R.id.charity_header_image) ImageView mHeaderImageView;
    private Charity mCharity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App)getApplication()).getAppComponent().inject(this);

        setContentView(R.layout.activity_charity);
        ButterKnife.bind(this);
        Bundle args = getIntent().getExtras();
        mCharities = args.getParcelableArrayList(CHARITIES_ARG);
        int pos = args.getInt(CHARITY_POS);
        mCharity = mCharities.get(pos);
        String id = mCharity.getId();

        String imageUrl = getString(R.string.charity_header_image_url, id);
        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESULT)
            .into(mHeaderImageView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {

        });

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.impact_list_fragment, CharityImpactFragment.newInstance(mCharity));
            ft.add(R.id.charity_details, CharityDetailsFragment.newInstance(mCharity));
            ft.commit();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        String title = mCharity.getName();
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public DataComponent getComponent() {
        return ((App)getApplication()).getDataComponent();
    }

    @Override
    public void onCharitySelected(int position) {
        mCharity = mCharities.get(position);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.impact_list_fragment, CharityImpactFragment.newInstance(mCharity));
        ft.replace(R.id.charity_details, CharityDetailsFragment.newInstance(mCharity));
        ft.commit();
    }

}
