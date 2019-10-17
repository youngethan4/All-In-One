package com.example.ethanman04.multiplayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ethanman04.allone.PreferenceKeys;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.example.ethanman04.allone.R;

import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private OnFragmentInteractionListener mListener;
    private View rootView;
    private Socket mSocket;
    private LinearLayoutManager lm;
    private RecyclerView rv;
    private ArrayList<OtherCardData> cardData;
    private OtherAdapter adapt;

    public ChatFragment() { }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mSocket = IO.socket("http://173.22.77.190:3000/api/multiplayer/chat");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        ini();
        return rootView;
    }

    private void ini(){
        rv = rootView.findViewById(R.id.multiplayer_chat_rv);
        cardData = new ArrayList<>();
        lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        adapt = new OtherAdapter(cardData);
        rv.setAdapter(adapt);

    }

    /**
     * class for holding other card data
     */
    class OtherCardData  {
        private String poster;
        private String message;

        public OtherCardData(String poster, String message)  {
            this.poster = poster;
            this.message = message;
        }
    }

    /**
     * Creates an adapter for the recycler view
     */
    class OtherAdapter extends RecyclerView.Adapter<ChatFragment.OtherAdapter.MyViewHolder> {
        ArrayList<ChatFragment.OtherCardData> ocd;

        OtherAdapter(ArrayList<OtherCardData> data) {
            ocd = data;
        }

        @Override
        public int getItemCount()
        {
            return ocd.size();
        }

        @Override
        public int getItemViewType(int position)
        {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
            if (ocd.get(position).poster.equals(sp.getString(PreferenceKeys.LOGGED_IN_USER_USERNAME, ""))) {
                return R.layout.layout_chat_sent;
            }
            return R.layout.layout_chat_received;
        }

        @NonNull
        @Override
        public ChatFragment.OtherAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            if (viewType == R.layout.layout_chat_sent) {
                View v = null;
                ChatFragment.OtherAdapter.MyViewHolder viewHolder = null;
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chat_sent, parent, false);
                viewHolder = new ChatFragment.OtherAdapter.UserViewHolder(v);
                return viewHolder;
            }
            View v = null;
            ChatFragment.OtherAdapter.MyViewHolder viewHolder = null;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chat_received, parent, false);
            viewHolder = new ChatFragment.OtherAdapter.OtherViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ChatFragment.OtherAdapter.MyViewHolder baseHolder, int position) {
            if(getItemViewType(position) == R.layout.layout_chat_sent)
            {
                ChatFragment.OtherAdapter.UserViewHolder holder = (ChatFragment.OtherAdapter.UserViewHolder) baseHolder;
                holder.messageUser.setText(ocd.get(position).message);
            }
            else {
                ChatFragment.OtherAdapter.OtherViewHolder holder = (ChatFragment.OtherAdapter.OtherViewHolder) baseHolder;
                holder.posterOther.setText(ocd.get(position).poster);
                holder.messageOther.setText(ocd.get(position).message);
            }
        }

        abstract class MyViewHolder extends RecyclerView.ViewHolder
        {
            public MyViewHolder(View itemView)
            {
                super(itemView);
            }
        }

        class OtherViewHolder extends ChatFragment.OtherAdapter.MyViewHolder {

            CardView receivedCard;
            TextView posterOther;
            TextView messageOther;

            public OtherViewHolder(View itemView){
                super(itemView);

                receivedCard = itemView.findViewById(R.id.multiplayer_chat_received_card);
                posterOther = itemView.findViewById(R.id.multiplayer_chat_received_poster);
                messageOther = itemView.findViewById(R.id.muliplayer_chat_received_message);
            }
        }

        class UserViewHolder extends ChatFragment.OtherAdapter.MyViewHolder {

            CardView sentCard;
            TextView messageUser;

            public UserViewHolder(View itemView){
                super(itemView);

                sentCard = itemView.findViewById(R.id.multiplayer_chat_sent_card);
                messageUser = itemView.findViewById(R.id.multiplayer_chat_sent_message);
            }
        }

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
