package org.d.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.d.App;
import org.d.R;
import org.d.data.DataComponent;
import org.d.ui.HasComponent;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements HasComponent<DataComponent> {

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

    }

    @Override
    public DataComponent getComponent() {
        return ((App) getApplication()).getDataComponent();
    }
}
