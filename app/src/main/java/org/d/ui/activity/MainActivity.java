package org.d.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.d.App;
import org.d.R;
import org.d.data.DataComponent;
import org.d.ui.HasComponent;
import org.d.ui.fragment.SavedMoneyBaseFragment;
import org.d.util.CompatUtil;

import java.util.List;

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
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                for (Fragment fragment : fragments) {
                    if (fragment instanceof SavedMoneyBaseFragment) {
                        ((SavedMoneyBaseFragment) fragment).update();
                    }

                };
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ((App)getApplication()).getAppComponent().inject(this);

        mCompatUtil.setStatusBarColor(this, R.color.colorPrimaryDark);
    }

    @Override
    public DataComponent getComponent() {
        return ((App) getApplication()).getDataComponent();
    }
}
