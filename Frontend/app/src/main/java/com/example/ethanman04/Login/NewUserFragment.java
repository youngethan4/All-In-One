package com.example.ethanman04.Login;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ethanman04.allone.Endpoints;
import com.example.ethanman04.allone.R;
import com.example.ethanman04.allone.VolleyRequests;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewUserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewUserFragment extends Fragment {
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

    public NewUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewUserFragment newInstance(String param1, String param2) {
        NewUserFragment fragment = new NewUserFragment();
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
        rootView = inflater.inflate(R.layout.fragment_new_user, container, false);
        // Inflate the layout for this fragment
        accountHelper = new AccountHelper();
        create();

        return rootView;
    }

    /**
     * At the time the create button is pressed, this method checks over all of the fields.
     */
    private void create(){
        Button createButton = rootView.findViewById(R.id.new_user_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = rootView.findViewById(R.id.new_user_first_name).toString().trim();
                String lastName = rootView.findViewById(R.id.new_user_last_name).toString().trim();
                String username = rootView.findViewById(R.id.new_user_username).toString().trim();
                String email = rootView.findViewById(R.id.new_user_email).toString().trim();
                String pass = rootView.findViewById(R.id.new_user_password).toString();
                boolean firstNameValid = accountHelper.checkName(firstName);
                if (!firstNameValid) displayError(0);
                boolean lastNameValid = accountHelper.checkName(lastName);
                if (!lastNameValid) displayError(1);
                boolean usernameValid = accountHelper.checkUsername(username);
                if (!usernameValid) displayError(2);
                boolean emailValid = accountHelper.checkEmail(email);
                if (!emailValid) displayError(3);
                String passHash = accountHelper.hashPass(pass);
                boolean passMatch = false;
                if(passHash == null) displayError(4);
                else {
                    if (pass.equals(rootView.findViewById(R.id.new_user_confirm_password).toString())){
                        passMatch = true;
                    }
                    else displayError(5);
                }
                if (passMatch && firstNameValid && lastNameValid && usernameValid && emailValid && passHash != null)
                    sendCreateAccountRequest();
            }
        });
    }

    /**
     * Displays an error depending on the input int
     * @param n
     */
    private void displayError(int n){
        TextView error = rootView.findViewById(R.id.new_user_error);
        String setError;
        switch (n) {
            case 0:
                setError = "Letters only in first name.";
                break;
            case 1:
                setError = "Letters only in last name.";
                break;
            case 2:
                setError = "Letters and numbers only in username.";
                break;
            case 3:
                setError = "Invalid email format.";
                break;
            case 4:
                setError = "Unsupported character in password.";
                break;
            case 5:
                setError = "Passwords do not match.";
                break;
            default:
                setError = "Error";
        }
        error.setText(setError);
    }

    /**
     * Send the account info to put into the database
     */
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
