package com.example.ukartapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.ukartapp.Adapters.MyPagerAdapter;
import com.example.ukartapp.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private Bundle bundle;
    public static String idAuthUser;

    private Toolbar myToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private MyPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bundle = getIntent().getExtras();

        idAuthUser = bundle.getString("ID");

        Toast.makeText(getApplicationContext(),"ID"+ idAuthUser,Toast.LENGTH_SHORT).show();

        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
//
        tabLayout = findViewById(R.id.tabLayout);
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_action_cart));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_action_home));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_action_camera));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_action_settings));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
