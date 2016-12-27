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
import org.d.util.UiUtil;

import javax.inject.Inject;

import butterknife.BindView;

public class CharityDetailsFragment extends BaseFragment {
    private static final String CHARITY_ARG = "charity";
    private Charity mCharity;
    @Inject CompatUtil mCompatUtil;
    @Inject UiUtil mUiUtil;

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
        mUiUtil.htmlFromHtml(activity, mOrganization, mCharity.getOrganization());
        mUiUtil.htmlFromHtml(activity, mRecommendation, mCharity.getRecommendation());
        mUiUtil.htmlFromHtml(activity, mEvidence, mCharity.getEvidence());
        return rootView;
    }
}
