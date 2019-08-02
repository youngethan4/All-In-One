package com.example.ethanman04.Login;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ethanman04.allone.Endpoints;
import com.example.ethanman04.allone.R;
import com.example.ethanman04.allone.VolleyRequests;
import com.example.ethanman04.memory.MemoryActivity;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements LoginTabFragment.OnFragmentInteractionListener, NewUserFragment.OnFragmentInteractionListener {

    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupTabs();
    }

    private void setupTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.login_view_pager);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.login_tab_layout);
        tabLayout.setupWithViewPager(viewPager);


    }

    /**
     * Sets the Android back button to go to the home screen
     */
    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
