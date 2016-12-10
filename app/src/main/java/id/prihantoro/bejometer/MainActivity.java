package id.prihantoro.bejometer;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.prihantoro.bejometer.api.eventresult.BejoLaunchResult;
import id.prihantoro.bejometer.fragment.BejometerFragment_;
import id.prihantoro.bejometer.fragment.TebakGenderFragment_;
import id.prihantoro.bejometer.util.DateTimeUtils;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @Extra
    String tanggalSaiki;
    @ViewById
    TabLayout tabs;
    @ViewById
    ViewPager viewPager;

    @ViewById
    Toolbar toolbar;

    @AfterViews
    void init() {
        setSupportActionBar(toolbar);
        Log.d("wahyu", tanggalSaiki);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(BejometerFragment_.builder().arg("tanggalSaiki", tanggalSaiki).build(), null);
        adapter.addFragment(TebakGenderFragment_.builder().arg("tanggalSaiki", tanggalSaiki).build(), null);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(R.drawable.ic_love);
        tabs.getTabAt(1).setIcon(R.drawable.gender);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_us:
                AboutActivity_.intent(this).start();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
