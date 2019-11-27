package com.example.katane.workoutcompanion;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.support.design.widget.TabLayout;

import com.example.katane.workoutcompanion.Fragments.GraphsFragment;
import com.example.katane.workoutcompanion.Fragments.HistoryFragment;
import com.example.katane.workoutcompanion.Fragments.HomeFragment;
import com.example.katane.workoutcompanion.Fragments.PicturesFragment;

public class MainActivity extends AppCompatActivity {
    MyPageAdapter pageAdapter;
    ViewPager pager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        List<Fragment> fragments = getFragments();

        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        pager = findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public void onBackPressed() {
        if(pager!=null && pager.getCurrentItem()==0){
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            }
            else{
                super.onBackPressed();
            }
        }
        else{
            super.onBackPressed();
        }

    }



    public boolean onCreateOptionsMenu(Menu menu){

        return false;
    }


    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<>();

        fList.add(HomeFragment.newInstance());
        fList.add(GraphsFragment.newInstance());
        fList.add(HistoryFragment.newInstance());
        fList.add(PicturesFragment.newInstance());
        return fList;
    }

    private static class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;
        private Context mContext;

        public MyPageAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.mContext=context;
        }

        private MyPageAdapter(FragmentManager fm, List<Fragment> fragments ) {
            super(fm);
            this.fragments = fragments;
        }
        @Override
        public Fragment getItem(int position){
            if(position==0){
                return HomeFragment.newInstance();
            }
            else{
                return this.fragments.get(position);
            }
        }

        @Override
        public int getCount()
        {
            return this.fragments.size();

        }
        @Override
        public CharSequence getPageTitle(int position){
            if (position == 0){
                return "Home";
            }
            else {
                return fragments.get(position).toString();
            }
        }
    }
}
