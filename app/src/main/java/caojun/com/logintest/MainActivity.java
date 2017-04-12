package caojun.com.logintest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import caojun.com.logintest.fragment.ContentFragment;

/**
 * Created by tiger on 2017/3/17.
 */

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.design_navigation_view)
    NavigationView mDesignNavigationView;
    @BindView(R.id.drawerlayout)
    DrawerLayout mDrawerlayout;

    private String[] titles = {"one", "two", "three", "four"};
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
//        mToolbar.setTitle("主页");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerlayout, mToolbar, R.string.open_drawer, R.string.close_drawer);
        actionBarDrawerToggle.syncState();
        mDrawerlayout.setDrawerListener(actionBarDrawerToggle);

        for (String tit : titles) {
            fragments.add(ContentFragment.getInstance(tit));
        }

        mViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });


        mTablayout.setupWithViewPager(mViewpager);

//        View headerView = mDesignNavigationView.getHeaderView(0);
//        CircleImageView mAvator = (CircleImageView) headerView.findViewById(R.id.avatar_header);
//        mAvator.setImageResource(R.drawable.ic_launcher);
//        TextView mName = (TextView) headerView.findViewById(R.id.tv_name);
//        mName.setText("mDesignNavigationView");

        mDesignNavigationView.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_one:
                        break;
                    case R.id.drawer_two:

                        break;
                    case R.id.drawer_three:

                        break;
                }
                return true;
            }
        });


    }


}
