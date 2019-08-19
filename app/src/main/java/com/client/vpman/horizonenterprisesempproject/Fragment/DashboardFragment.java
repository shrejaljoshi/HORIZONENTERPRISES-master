package com.client.vpman.horizonenterprisesempproject.Fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.client.vpman.horizonenterprisesempproject.Adapter.CustomPagerDasboardAdapter;
import com.client.vpman.horizonenterprisesempproject.Adapter.RecsAdapter;
import com.client.vpman.horizonenterprisesempproject.R;
import com.client.vpman.horizonenterprisesempproject.pojo.BeanSchoolDetail;
import com.client.vpman.horizonenterprisesempproject.useful.GridSpacingItemDecorationDashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.client.vpman.horizonenterprisesempproject.Activity.HomeActivity.mDrawereLayout;

public class DashboardFragment extends Fragment
{
    View view;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    RecsAdapter adapter;
    Context mContext;
    List<BeanSchoolDetail>  mList ;
    Toolbar toolbar;
    TextView toolbar_title;
    ImageView imgNotification;
    ViewPager viewPager;
    CustomPagerDasboardAdapter CustomPagerDasboardAdapter;
    int images[] = {R.drawable.oxford_school, R.drawable.school, R.drawable.oxford_school, R.drawable.school};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_dashboard,container,false);
        recyclerView=view.findViewById(R.id.recyclerview);
        mContext = getActivity();
        mList = new ArrayList<>();

        toolbar = view.findViewById(R.id.toolbar);
        toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        imgNotification = toolbar.findViewById(R.id.imgNotification);
        imgNotification.setVisibility(View.VISIBLE);

        toolbar_title.setText(mContext.getString(R.string.home));
        toolbar.setNavigationIcon(R.drawable.ic_dehaze_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawereLayout.openDrawer(GravityCompat.START);

            }
        });

        viewPager = view.findViewById(R.id.viewPager);
        CustomPagerDasboardAdapter = new CustomPagerDasboardAdapter(getActivity(), images);
        viewPager.setAdapter(CustomPagerDasboardAdapter);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                viewPager.post(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % images.length);
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);

        layoutManager = new GridLayoutManager(mContext,2);
        recyclerView.addItemDecoration(new GridSpacingItemDecorationDashboard(3, dpToPx(0), true, 0));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new RecsAdapter(mContext,mList);
        recyclerView.setAdapter(adapter);
        setData();
        return view;
    }

    private void setData() {
        mList.add(new BeanSchoolDetail(R.drawable.ops,"Oxford Public School"));
        mList.add(new BeanSchoolDetail(R.drawable.oxford_school,"Oxford School"));
        mList.add(new BeanSchoolDetail(R.drawable.cropped,"St.Xaviers Jr/Sr School"));
        mList.add(new BeanSchoolDetail(R.drawable.ontonagon_school,"Cambridge School"));
        mList.add(new BeanSchoolDetail(R.drawable.school,"Harvard School"));
    }

    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}


