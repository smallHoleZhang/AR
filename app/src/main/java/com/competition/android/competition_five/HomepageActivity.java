package com.competition.android.competition_five;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.competition.android.competition_five.Fragment.ArFragment;
import com.competition.android.competition_five.Fragment.HomeFragment;
import com.competition.android.competition_five.Fragment.MineFragment;
import com.competition.android.competition_five.Fragment.NodeContextFrament;
import com.competition.android.competition_five.Fragment.NodeFragment;

import java.util.ArrayList;

public class HomepageActivity extends BaseActivity  implements BottomNavigationBar.OnTabSelectedListener{
    //几个主要布局
    private static ArrayList<Fragment>  functionfaragments;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_main);
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.home, "Home").setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.drawable.arscan, "Ar&Memory").setActiveColorResource(R.color.teal))
                .addItem(new BottomNavigationItem(R.drawable.node, "Node").setActiveColorResource(R.color.mediumslateblue))
                .addItem(new BottomNavigationItem(R.drawable.wode, "Mine").setActiveColorResource(R.color.grey))
                .setFirstSelectedPosition(0)
                .initialise();
        functionfaragments = getFragments();
        setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(this);

    }
    /**
     * 设置默认的
     */
    private  void setDefaultFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.hompage_frameLayout, functionfaragments.get(0));
        transaction.commit();

    }
    private  ArrayList<Fragment> getFragments()
    {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance());
        fragments.add(ArFragment.newInstance());
        fragments.add(NodeFragment.newInstance());
        fragments.add(MineFragment.newInstance());
        return fragments;
    }


    @Override
    public void onTabSelected(int position) {
        if(functionfaragments != null)
        {
            if(position<functionfaragments.size())
            {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = functionfaragments.get(position);
                        if(fragment.isAdded())
                        {
                            ft.replace(R.id.hompage_frameLayout,fragment);
                        }else {
                            ft.add(R.id.hompage_frameLayout,fragment);
                        }

                        ft.commitAllowingStateLoss();
            }
        }

    }

    @Override
    public void onTabUnselected(int position) {
        if (functionfaragments != null) {
            if (position < functionfaragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = functionfaragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }

    }

    @Override
    public void onTabReselected(int position) {
    }





}
