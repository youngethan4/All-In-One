package com.example.ethanman04.Login;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupTabs();

    }

    private void setupTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.login_view_pager);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.login_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

//        Button loginButton = findViewById(R.id.login_button);
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, MemoryActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void sendCreateAccountRequest(){
        JSONObject jsonObject = new JSONObject();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Endpoints.getInstance().getCreateUserEndpoint(), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: Create user response.
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleyRequests.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
