package org.d.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import org.d.App;
import org.d.R;
import org.d.data.DataComponent;
import org.d.model.lycs.Charity;
import org.d.ui.HasComponent;
import org.d.ui.widget.TitleHeaderWidget;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CharitiesActivity extends BaseActivity implements HasComponent<DataComponent> {
    public static final String CHARITIES_ARG = "charities";
    private ArrayList<Charity> mCharities;
    @BindView(R.id.header_widget) TitleHeaderWidget mHeader;
    @BindString(R.string.impact) String mTitle;
    @BindString(R.string.impact_subtitle) String mSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App)getApplication()).getAppComponent().inject(this);

        setContentView(R.layout.activity_charities);
        ButterKnife.bind(this);
        Bundle args = getIntent().getExtras();
        mCharities = args.getParcelableArrayList(CHARITIES_ARG);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //noinspection ConstantConditions
        mHeader.setTitle(mTitle);
        mHeader.setSubtitle(mSubtitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public DataComponent getComponent() {
        return ((App)getApplication()).getDataComponent();
    }
}
