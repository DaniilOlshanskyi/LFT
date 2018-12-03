package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * The main screen of the application, which manages the interfaces and operations for the user's profile viewing screen, swiping screen, and conversation list screen
 */
public class MainAppScreen extends AppCompatActivity {

    private SharedPreferences sharedPrefs;
    private int usertype;

    private SectionsPageAdapter sPageAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get usertype to determine which layout to display
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        usertype = sharedPrefs.getInt(Const.SHAREDPREFS_USERTYPE_KEY, Const.USERTYPE_BASIC_USER);

        // set view layout and set up tabs based on usertype
        if (usertype == Const.USERTYPE_ADMIN){
            setContentView(R.layout.admin_main_screen);
            //TODO reference views
        }
        else if (usertype == Const.USERTYPE_MODERATOR){
            setContentView(R.layout.mod_main_screen);
            //TODO reference views
        }
        else {
            setContentView(R.layout.user_main_screen);
            viewPager = findViewById(R.id.userMainScreen_tabViewPager);
            tabLayout = findViewById(R.id.userMainScreen_tabs);
        }

        // set up ViewPager with sections adapter
        setupViewPager(viewPager);

        // set tab layout
        tabLayout.setupWithViewPager(viewPager);

        //TODO default to display swipe screen when activity is opened
    }

    // sets up view pager by adding fragments to a sections page adapter and using it to set the adapter of the view pager

    /**
     * Sets up a supplied ViewPager object with a SectionsPageAdapter containing fragments for the profile viewing, swiping, and conversation list interfaces
     * @param pager the supplied ViewPager
     */
    private void setupViewPager(ViewPager pager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new MyProfileFragment(), "myProfileTab");
        adapter.addFragment(new SwipingScreenFragment(), "swipingScreenTab");
        adapter.addFragment(new ConversationListFragment(), "conversationListTab");
        pager.setAdapter(adapter);
    }

    /**
     * Custom FragmentPagerAdapter class that keeps a list of Fragment objects and their titles
     */
    private class SectionsPageAdapter extends FragmentPagerAdapter{

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public SectionsPageAdapter(FragmentManager fm){
            super(fm);
        }

        public void addFragment(Fragment frag, String title){
            fragmentList.add(frag);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
