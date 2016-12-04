package org.d.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.d.App;
import org.d.R;
import org.d.data.DataComponent;
import org.d.ui.HasComponent;
import org.d.util.CompatUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements HasComponent<DataComponent> {

    @Inject CompatUtil mCompatUtil;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ViewPager viewPager = ButterKnife.findById(this, R.id.view_pager);
        TabLayout tabLayout = ButterKnife.findById(this, R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(MainActivity.this, getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);

        ((App)getApplication()).getAppComponent().inject(this);

        mCompatUtil.setStatusBarColor(this, R.color.colorPrimaryDark);
    }

    @Override
    public DataComponent getDataComponent() {
        return ((App) getApplication()).getDataComponent();
    }
}
