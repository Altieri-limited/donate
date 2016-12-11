package org.d.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.d.R;
import org.d.data.DataComponent;
import org.d.model.lycs.Charity;
import org.d.util.CompatUtil;

import javax.inject.Inject;

import butterknife.BindView;

public class CharityDetailsFragment extends BaseFragment {
    private static final String CHARITY_ARG = "charity";
    private Charity mCharity;
    @Inject CompatUtil mCompatUtil;

    @BindView(R.id.organization) TextView mOrganization;
    @BindView(R.id.recommendation) TextView mRecommendation;
    @BindView(R.id.evidence) TextView mEvidence;

    public static CharityDetailsFragment newInstance(Charity charity) {
        CharityDetailsFragment fragment = new CharityDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(CHARITY_ARG, charity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCharity = getArguments().getParcelable(CHARITY_ARG);
        getComponent(DataComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_charity_description, container, false);
        setRetainInstance(true);
        bind(rootView);
        FragmentActivity activity = getActivity();
        mOrganization.setText(mCompatUtil.htmlFromHtml(activity, mCharity.getOrganization()));
        mRecommendation.setText(mCompatUtil.htmlFromHtml(activity, mCharity.getRecommendation()));
        mEvidence.setText(mCompatUtil.htmlFromHtml(activity, mCharity.getEvidence()));
        return rootView;
    }
}
