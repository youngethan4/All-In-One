package com.example.ethanman04.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ethanman04.allone.Endpoints;
import com.example.ethanman04.allone.PreferenceKeys;
import com.example.ethanman04.allone.R;
import com.example.ethanman04.allone.VolleyRequests;
import com.example.ethanman04.memory.MemoryActivity;
import com.example.ethanman04.memory.SetSound;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private GridView gv;
    private MyAdapter adapter;
    private ArrayList<Integer> icons;
    private int currentIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setupTheme();
        getIcon();
        setupGridView();
        setListeners();
    }

    /**
     * Sets the color accents in the activity to the proper theme.
     */
    private void setupTheme(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Button save = findViewById(R.id.edit_profile_save);
        Button cancel = findViewById(R.id.edit_profile_cancel);
        View line = findViewById(R.id.edit_profile_line);
        EditText username = findViewById(R.id.edit_profile_username_edit);
        EditText newPass = findViewById(R.id.edit_profile_new_password_edit);
        EditText confirmPass = findViewById(R.id.edit_profile_confirm_password_edit);
        EditText currentPass = findViewById(R.id.edit_profile_current_password_edit);
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_enabled}, // enabled
                new int[] {-android.R.attr.state_enabled}, // disabled
                new int[] {-android.R.attr.state_checked}, // unchecked
                new int[] { android.R.attr.state_pressed}  // pressed
        };

        int[] colors = new int[] {
                getResources().getColor(sp.getInt(PreferenceKeys.MEMORY_THEME_COLOR, R.color.blue)),
                getResources().getColor(sp.getInt(PreferenceKeys.MEMORY_THEME_COLOR, R.color.blue)),
                getResources().getColor(sp.getInt(PreferenceKeys.MEMORY_THEME_COLOR, R.color.blue)),
                getResources().getColor(sp.getInt(PreferenceKeys.MEMORY_THEME_COLOR, R.color.blue))
        };

        save.setBackgroundResource(sp.getInt(PreferenceKeys.MEMORY_THEME_BOARDER, R.drawable.memory_boarder_blue));
        cancel.setBackgroundResource(sp.getInt(PreferenceKeys.MEMORY_THEME_BOARDER, R.drawable.memory_boarder_blue));
        line.setBackgroundColor(getResources().getColor(sp.getInt(PreferenceKeys.MEMORY_THEME_COLOR, R.color.blue)));
        username.setBackgroundTintList(new ColorStateList(states, colors));
        newPass.setBackgroundTintList(new ColorStateList(states, colors));
        confirmPass.setBackgroundTintList(new ColorStateList(states, colors));
        currentPass.setBackgroundTintList(new ColorStateList(states, colors));
    }

    /**
     * Sets the current icon to the one saved in shared preferences.
     */
    private void getIcon(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        currentIcon = sp.getInt(PreferenceKeys.LOGGED_IN_USER_ICON, R.drawable.avatar_dolphin);
        ImageView current = findViewById(R.id.edit_profile_current_icon);
        current.setImageDrawable(getDrawable(currentIcon));
    }

    /**
     * Populates an array list with all of the icons that the user can choose from.
     * Then it establishes the gridview.
     */
    private void setupGridView(){
        icons = new ArrayList<>();
        icons.add(R.drawable.avatar_ant);
        icons.add(R.drawable.avatar_bee);
        icons.add(R.drawable.avatar_bird);
        icons.add(R.drawable.avatar_butterfly);
        icons.add(R.drawable.avatar_cat);
        icons.add(R.drawable.avatar_crab);
        icons.add(R.drawable.avatar_dog);
        icons.add(R.drawable.avatar_dolphin);
        icons.add(R.drawable.avatar_dragon);
        icons.add(R.drawable.avatar_elephant);
        icons.add(R.drawable.avatar_fenix);
        icons.add(R.drawable.avatar_fish);
        icons.add(R.drawable.avatar_flamingo);
        icons.add(R.drawable.avatar_fly);
        icons.add(R.drawable.avatar_frog);
        icons.add(R.drawable.avatar_mouse);
        icons.add(R.drawable.avatar_octopus);
        icons.add(R.drawable.avatar_owl);
        icons.add(R.drawable.avatar_puffin);
        icons.add(R.drawable.avatar_rabbit);
        icons.add(R.drawable.avatar_seahorse);
        icons.add(R.drawable.avatar_snake);
        icons.add(R.drawable.avatar_seal);
        icons.add(R.drawable.avatar_squirel);
        icons.add(R.drawable.avatar_swan);
        icons.add(R.drawable.avatar_turtle);
        icons.add(R.drawable.avatar_whale);
        gv = findViewById(R.id.edit_profile_icons_gv);
        adapter = new MyAdapter(icons, this);
        gv.setAdapter(adapter);
    }

    /**
     * Sets the button listeners for the save and cancel button. Also sets username edit text
     * to the users current username.
     */
    private void setListeners(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Button cancelButton = findViewById(R.id.edit_profile_cancel);
        Button saveButton = findViewById(R.id.edit_profile_save);
        EditText username = findViewById(R.id.edit_profile_username_edit);
        username.setText(sp.getString(PreferenceKeys.LOGGED_IN_USER_USERNAME, ""));

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAccountUpdates();
            }
        });
    }

    /**
     * checks the account updates using the account helper method. During these updates,
     * entries are added to the hashmap that will converted into a jsonRequest.
     * An update will only send if all entries in the ui are valid.
     */
    private void checkAccountUpdates(){
        boolean sendUpdate = true;
        AccountHelper accountHelper = new AccountHelper();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        HashMap<String, Object> hashMap = new HashMap<>();
        EditText usernameEditText = findViewById(R.id.edit_profile_username_edit);
        String username = usernameEditText.getText().toString().trim();
        EditText passwordEditText = findViewById(R.id.edit_profile_confirm_password_edit);
        String password = passwordEditText.getText().toString();
        EditText oldPassEditText = findViewById(R.id.edit_profile_current_password_edit);
        String oldPass = oldPassEditText.getText().toString();

        //Check username
        if (username.equals(sp.getString(PreferenceKeys.LOGGED_IN_USER_USERNAME, "")) || username.equals("")){
            hashMap.put("username", "no");
        } else {
            if (accountHelper.usernameSupportedChars(username)) {
                hashMap.put("username", username);
            } else {
                sendUpdate = false;
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Unsupported characters in username.",
                        Toast.LENGTH_LONG);
                toast.show();
            }
        }

        //Check icon
        if (currentIcon == sp.getInt(PreferenceKeys.LOGGED_IN_USER_ICON, 0)){
            hashMap.put("icon", sp.getInt(PreferenceKeys.LOGGED_IN_USER_ICON, R.drawable.avatar_dolphin));
        } else {
            hashMap.put("icon", currentIcon);
        }

        //Check password
        if (password.length() > 0){
            boolean has2CharSets = accountHelper.passContain2CharSets(password);
            boolean isLongEnough = accountHelper.passLongEnough(password);

            if (has2CharSets && isLongEnough){
                String passHash = accountHelper.hashPass(password);
                if (passHash != null){
                    hashMap.put("password", passHash);
                    hashMap.put("oldpass", accountHelper.hashPass(oldPass));
                } else {
                    sendUpdate = false;
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Error hashing password.",
                            Toast.LENGTH_LONG);
                    toast.show();
                }

            }
            else {
                sendUpdate = false;
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Password must be 8 characters and have two character sets!",
                        Toast.LENGTH_LONG);
                toast.show();
            }
        } else {
            hashMap.put("password", "no");
        }

        //Send update and make changes to shared preferences.
        if (sendUpdate){
            hashMap.put("userid", sp.getInt(PreferenceKeys.LOGGED_IN_USER_ID, 0));
            sendUpdateRequest(new JSONObject(hashMap));
        }
    }

    /**
     * Sends the request to update the account. On response, sets the shared preferences for
     * username and icon.
     * @param jsonObject
     */
    private void sendUpdateRequest(final JSONObject jsonObject){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Endpoints.getInstance().getEditProfileEndpoint(),
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                final SharedPreferences.Editor editor = sp.edit();
                String username = "";
                String res = "";
                int icon = currentIcon;
                try {
                    username = jsonObject.getString("username");
                    res = response.getString("password");
                    icon = jsonObject.getInt("icon");
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Username and/or password not updated. Please report this issue.",
                            Toast.LENGTH_LONG);
                    toast.show();
                    e.printStackTrace();
                }
                editor.putInt(PreferenceKeys.LOGGED_IN_USER_ICON, icon);
                if (!username.equals("no") && !res.equals("error")){
                    editor.putString(PreferenceKeys.LOGGED_IN_USER_USERNAME, username);
                }
                editor.apply();
                if (res.equals("error") || res.equals("incorrect")){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Password incorrect and will not be updated.",
                            Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Intent intent = new Intent(ProfileActivity.this, MemoryActivity.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("request.error", error.toString());
            }
        });

        VolleyRequests.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    /**
     * The adaper for the grid view being used.
     */
    private class MyAdapter extends BaseAdapter {
        ArrayList<Integer> cd;
        Context con;

        MyAdapter(ArrayList data, Context context) {
            cd = data;
            con = context;
        }

        @Override
        public int getCount()
        {
            return cd.size();
        }

        @Override
        public int getItemViewType(int position)
        {
            return R.layout.layout_edit_profile;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return  cd.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                final LayoutInflater layoutInflater = LayoutInflater.from(con);
                convertView = layoutInflater.inflate(R.layout.layout_edit_profile, parent, false);
            }
            final ImageView iv = convertView.findViewById(R.id.edit_profile_gv_icon);

            ViewHolder viewHolder = new ViewHolder(iv, cd.get(position));
            viewHolder.iv.setImageDrawable(getDrawable(cd.get(position)));
            return convertView;
        }
    }

    /**
     * The view holder for each icon.
     */
    private class ViewHolder {
        ImageView iv;

        private ViewHolder(ImageView imageView, final int iconID) {
            iv = imageView;
            iv.setMaxWidth(200);
            //final Drawable d = iv.getDrawable();

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView ivCurrent = findViewById(R.id.edit_profile_current_icon);
                    ivCurrent.setImageDrawable(getDrawable(iconID));
                    currentIcon = iconID;
                }
            });
        }
    }

    /**
     * When the activity is paused, the music will as well.
     */
    @Override
    protected void onPause()
    {
        super.onPause();
        SetSound.getInstance().pauseMusic();
    }

    /**
     * Once the activity is resumed, the music will also be resumed.
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        SetSound.getInstance().resumeMusic(this);
    }

    /**TODO: Big todo!! - Delete First Name & Last Name from database
     *                  - put high scores on server side.
     */
}
