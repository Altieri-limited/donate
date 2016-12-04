package org.d.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import org.d.App;
import org.d.R;
import org.d.data.DataComponent;
import org.d.model.lycs.Charity;
import org.d.ui.HasComponent;
import org.d.ui.fragment.CharityImpactFragment;

import butterknife.ButterKnife;

public class CharityImpactActivity extends BaseActivity implements HasComponent<DataComponent> {

    public static final String CHARITY_ARG = "charity";
    private Charity mCharity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App)getApplication()).getAppComponent().inject(this);

        setContentView(R.layout.activity_charity_impact);
        ButterKnife.bind(this);
        Bundle args = getIntent().getExtras();
        mCharity = args.getParcelable(CHARITY_ARG);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {

        });
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.content, CharityImpactFragment.newInstance(mCharity));
        ft.commit();
    }

    @Override
    public DataComponent getComponent() {
        return ((App)getApplication()).getDataComponent();
    }
}
