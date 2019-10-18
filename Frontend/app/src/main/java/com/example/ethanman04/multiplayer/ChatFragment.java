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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ethanman04.allone.PreferenceKeys;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.example.ethanman04.allone.R;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

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
    private ArrayList<MessageCardData> cardData;
    private MessageAdapter adapt;
    private SharedPreferences sp;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        ini();
        setSocket();
        return rootView;
    }

    /**
     * Sets up the socket and creates a listener for incoming messages.
     */
    private void setSocket(){
        try {
            mSocket = IO.socket("http://173.22.77.190:3000/api/multiplayer/chat");
            mSocket.connect();
            mSocket.emit("join", sp.getString(PreferenceKeys.LOGGED_IN_USER_USERNAME, ""));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Emitter.Listener onNewMessage = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject data = (JSONObject) args[0];
                            String username = "";
                            String message = "";
                            try {
                                username = data.getString("username");
                                message = data.getString("message");
                                System.out.println(message);
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                            cardData.add(new MessageCardData(username, message));
                            adapt.notifyDataSetChanged();
                            rv.refreshDrawableState();
                        }
                    });
                } catch(NullPointerException e){
                    e.printStackTrace();
                }
            }
        };
        mSocket.on("new message", onNewMessage);

    }

    /**
     * Initializes the recycler view, shared preference, card data, layout manager, and adapter.
     */
    private void ini(){
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        rv = rootView.findViewById(R.id.multiplayer_chat_rv);
        cardData = new ArrayList<>();
        lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        adapt = new MessageAdapter(cardData);
        rv.setAdapter(adapt);
        setSend();
    }

    /**
     * Sets the send button to emit the message text to the socket.
     * Also resets the message text back to nothing and refreshes the recycler view.
     */
    private void setSend(){
        Button send = rootView.findViewById(R.id.multiplayer_chat_send);
        EditText message = rootView.findViewById(R.id.multiplayer_chat_message);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendMessage = message.getText().toString().trim();
                message.setText("");
                mSocket.emit("new message", sendMessage);
            }
        });
    }

    /**
     * class for holding message card data
     */
    class MessageCardData {
        private String username;
        private String message;

        MessageCardData(String username, String message)  {
            this.username = username;
            this.message = message;
        }
    }

    /**
     * Creates an adapter for the recycler view with a received and sent view holder.
     */
    class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
        ArrayList<MessageCardData> mcd;

        MessageAdapter(ArrayList<MessageCardData> data) {
            mcd = data;
        }

        @Override
        public int getItemCount()
        {
            return mcd.size();
        }

        @Override
        public int getItemViewType(int position)
        {
            if (mcd.get(position).username.equals(sp.getString(PreferenceKeys.LOGGED_IN_USER_USERNAME, ""))) {
                return R.layout.layout_chat_sent;
            }
            return R.layout.layout_chat_received;
        }

        @NonNull
        @Override
        public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            if (viewType == R.layout.layout_chat_sent) {
                View v = null;
                MessageViewHolder viewHolder = null;
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chat_sent, parent, false);
                viewHolder = new SentViewHolder(v);
                return viewHolder;
            }
            View v = null;
            MessageViewHolder viewHolder = null;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chat_received, parent, false);
            viewHolder = new ReceivedViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MessageViewHolder baseHolder, int position) {
            if(getItemViewType(position) == R.layout.layout_chat_sent)
            {
                SentViewHolder holder = (SentViewHolder) baseHolder;
                holder.messageUser.setText(mcd.get(position).message);
            }
            else {
                ReceivedViewHolder holder = (ReceivedViewHolder) baseHolder;
                holder.posterOther.setText(mcd.get(position).username);
                holder.messageOther.setText(mcd.get(position).message);
            }
        }

        abstract class MessageViewHolder extends RecyclerView.ViewHolder
        {
            MessageViewHolder(View itemView)
            {
                super(itemView);
            }
        }

        class ReceivedViewHolder extends MessageViewHolder {

            CardView receivedCard;
            TextView posterOther;
            TextView messageOther;

            ReceivedViewHolder(View itemView){
                super(itemView);

                receivedCard = itemView.findViewById(R.id.multiplayer_chat_received_card);
                posterOther = itemView.findViewById(R.id.multiplayer_chat_received_poster);
                messageOther = itemView.findViewById(R.id.muliplayer_chat_received_message);
            }
        }

        class SentViewHolder extends MessageViewHolder {

            CardView sentCard;
            TextView messageUser;

            SentViewHolder(View itemView){
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
        mSocket.disconnect();
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
