package com.example.assessment5;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assessment5.databinding.ForumRowItemBinding;
import com.example.assessment5.databinding.FragmentForumsBinding;
import com.example.assessment5.models.Auth;
import com.example.assessment5.models.Forum;
import com.example.assessment5.models.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ForumsFragment extends Fragment {


    public ForumsFragment() {
        // Required empty public constructor
    }
//!the below code takes care of passing AUTH objectt

    Auth mAuth;
    private static final String ARG_AUTH = "ARG_AUTH";
    public static ForumsFragment newInstance(Auth auth) {
        ForumsFragment fragment = new ForumsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_AUTH,auth);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAuth = (Auth) getArguments().getSerializable(ARG_AUTH  );
        }
    }
//!to here





    FragmentForumsBinding binding;
    ArrayList<Forum> forums = new ArrayList<>();
    ForumsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForumsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Forums");

        binding.textViewWelcome.setText("welcome: " + mAuth.getUser_fname() + " " + mAuth.user_lname);

        binding.buttonCreateForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoCreateForum();
            }
        });

        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.logout();
            }
        });

        adapter = new ForumsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        getForums();
    }

    private final OkHttpClient client = new OkHttpClient();


    void getForums() {

        //token received /posts/login or /posts/signup
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/threads")
                .addHeader("Authorization", "BEARER " + mAuth.getToken())
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String body = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray jsonArray = jsonObject.getJSONArray("threads");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject forumObject = jsonArray.getJSONObject(i);
                            Forum forum = new Forum(forumObject);

                            forums.add(forum);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                } else {



                }
            }
        });

    }

    class ForumsAdapter extends RecyclerView.Adapter<ForumsAdapter.ForumsViewHolder> {

        @NonNull
        @Override
        public ForumsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ForumsViewHolder(ForumRowItemBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ForumsViewHolder holder, int position) {
            Forum forum = forums.get(position);
            holder.setupUI(forum);
        }

        @Override
        public int getItemCount() {
            return forums.size();
        }

        class ForumsViewHolder extends RecyclerView.ViewHolder {
            Forum mForum;
            ForumRowItemBinding mBinding;
            public ForumsViewHolder(ForumRowItemBinding mBinding) {
                super(mBinding.getRoot());
                this.mBinding = mBinding;
                mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.gotoForumMessages(mForum);
                    }
                });
            }

            void setupUI(Forum forum){
                this.mForum = forum;
                mBinding.textViewForumTitle.setText(mForum.getTitle());
                mBinding.textViewForumCreatedAt.setText(forum.getCreated_at());
                mBinding.textViewForumCreatorName.setText(forum.getCreatedByFname() + " " + forum.getCreatedByLname());
                //TODO: setup the rest of the UI the delete icon ..
            }
        }
    }

    ForumsFragmentListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ForumsFragmentListener) {
            mListener = (ForumsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ForumsFragmentListener");
        }
    }

    interface ForumsFragmentListener {
        void logout();
        void gotoCreateForum();
        void gotoForumMessages(Forum forum);
    }
}