package edu.uncc.finalexam;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.auth.User;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.finalexam.databinding.FragmentNewsBinding;

public class NewsFragment extends Fragment {
    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
    //!WIP------------------------------------------------------------------------------------------------------------------------------

    FragmentNewsBinding binding;
    NewsAdapter adapter;
   ArrayList<News> news = new ArrayList<>();
    ListView listView;


    //!WIP------------------------------------------------------------------------------------------------------------------------------

    //!uncoment 3 lines below to go to working state
//    FragmentNewsBinding binding;
//    ArrayList<News> news = new ArrayList<>();
//    ArrayAdapter<News> adapter;

    //!here are the updates betweehn these lines



    //!ends here with updates-------------------

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("News");
        getNews();
        binding.buttonMyLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToMyList();
            }
        });
        view.findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.logout();
            }
        });


        //!WIP------------------------------------------------------------------------------------------------------------------------------

//        adapter = new ArrayAdapter<>(this, binding.listViewNews.findViewById(R.id.listViewNews), android.R.id.text1, news);

//        binding.listViewNews.setOnClickListener(new AdapterView.onCl);
//instead of get context on first param its this on video
        listView = binding.listViewNews;
        adapter = new NewsAdapter(getContext(), R.layout.news_row_item, news);
        listView.setAdapter(adapter);


        //!WIP------------------------------------------------------------------------------------------------------------------------------^^^ BELOW UNCOMENT 2 LINES
//        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, news);
//
//
//        binding.listViewNews.setAdapter(adapter);

    }

    void getNews() {
        String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=ac33c4abec524be7a1ed3bb4948dc8ac&category=technology&pageSize=50";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    news.clear();
                    String body = response.body().string();
                    //Log.d("demo", "onResponse   : " + body);

                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray jsonArray = jsonObject.getJSONArray("news");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject newsObject = jsonArray.getJSONObject(i);
                            News newsArticle = new News(newsObject);

                            news.add(newsArticle);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                            getNews();
                                adapter.notifyDataSetChanged();

                                //todo above

                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }

    NewsFragmentListener mListener;

    class NewsAdapter2 extends RecyclerView.Adapter<NewsAdapter2.NewsViewHolder> {
        @NonNull
        @Override
        public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class NewsViewHolder extends RecyclerView.ViewHolder{

            public NewsViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (NewsFragmentListener) context;
    }

    public interface NewsFragmentListener{
        void logout();

        void goToMyList();
    }
}