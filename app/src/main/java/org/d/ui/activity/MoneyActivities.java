package org.d.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import org.d.App;
import org.d.R;
import org.d.data.DataComponent;
import org.d.ui.HasComponent;

import butterknife.ButterKnife;

public class MoneyActivities extends BaseActivity  implements HasComponent<DataComponent> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App)getApplication()).getAppComponent().inject(this);

        setContentView(R.layout.activity_money);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public DataComponent getComponent() {
        return ((App)getApplication()).getDataComponent();
    }
}
