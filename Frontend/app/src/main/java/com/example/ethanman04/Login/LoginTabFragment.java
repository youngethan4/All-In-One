package com.example.ethanman04.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ethanman04.allone.Endpoints;
import com.example.ethanman04.allone.PreferenceKeys;
import com.example.ethanman04.allone.R;
import com.example.ethanman04.allone.VolleyRequests;
import com.example.ethanman04.memory.MemoryActivity;

import org.json.JSONObject;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginTabFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;
    private AccountHelper accountHelper;

    private OnFragmentInteractionListener mListener;

    public LoginTabFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginTabFragment newInstance(String param1, String param2) {
        LoginTabFragment fragment = new LoginTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login_tab, container, false);
        accountHelper = new AccountHelper();
        login();

        return rootView;
    }

    /**
     * This method checks the login credential before a request is even called to make sure of valid characters.
     */
    private void login(){
        Button loginButton = rootView.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootView.findViewById(R.id.login_error).setVisibility(View.INVISIBLE);
                EditText emailEditText = rootView.findViewById(R.id.login_email);
                String email = emailEditText.getText().toString().trim();
                EditText passEditText = rootView.findViewById(R.id.login_password);
                boolean validemail = accountHelper.emailSupportedFormat(email);
                if (!validemail) displayError();
                String hashPass = accountHelper.hashPass(passEditText.getText().toString());
                if (hashPass == null) displayError();

                if (validemail && hashPass != null) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("email", email);
                    hashMap.put("password", hashPass);
                    sendLoginRequest(new JSONObject(hashMap));
                }
            }
        });
    }

    /**
     * Changes the error text field to say the username or password is incorrect.
     */
    private void displayError(){
        TextView error = rootView.findViewById(R.id.login_error);
        String setText = "Invalid email or password.";
        error.setText(setText);
        error.setVisibility(View.VISIBLE);
    }

    /**
     * Send a request to the backend with the credentials. The response will contain the userid.
     * If the user id comes back as 0, it is not a valid login.
     */
    private void sendLoginRequest(JSONObject jsonObject){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Endpoints.getInstance().getLoginUserEndpoint(),
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sp.edit();
                int userID = 0;
                String username = null;
                int icon = R.drawable.avatar_dolphin;
                try {
                     userID = response.getInt("userid");
                     username = response.getString("username");
                     icon = response.getInt("icon");
                }
                catch(Exception e) {
                    e.printStackTrace();
                }

                if (userID > 2){
                    editor.putInt(PreferenceKeys.LOGGED_IN_USER_ID, userID);
                    editor.putString(PreferenceKeys.LOGGED_IN_USER_USERNAME, username);
                    editor.putInt(PreferenceKeys.LOGGED_IN_USER_ICON, icon);
                    editor.apply();
                    Intent intent = new Intent(getActivity(), MemoryActivity.class);
                    startActivity(intent);
                } else{
                    displayError();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        VolleyRequests.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
