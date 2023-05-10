package edu.uncc.finalexam;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    FragmentNewsBinding binding;
    ArrayList<News> news = new ArrayList<>();
    ArrayAdapter <News> adapter;
//    NewsAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

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


//        ListView listView;
//        listView = findViewById(R.id.listView);
//        adapter = new ArrayAdapter<> ( context: this, android.R. layout.simple_list_item_1, android.R.id.texti, users);
//        listView.setAdapter (adapter);
//        listView.setOnItemClickListener(new AdapterView. OnItemClickListener () {
//
//

        //https://newsapi.org/v2/top-headlines?country=us&apiKey=ac33c4abec524be7a1ed3bb4948dc8ac&category=technology&pageSize=50
        //this is the api .. need to set apiKey to your API Key!!

//key: https://newsapi.org/v2/top-headlines?country=us&apiKey=ac33c4abec524be7a1ed3bb4948dc8ac&category=technology&pageSize=50

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,students);
        adapter = new ArrayAdapter<>(getActivity(),binding.listViewNews,news);
        binding.listViewNews.setAdapter(adapter);

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
                    Log.d("demo", "onResponse   : " + body);

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
                                //adapter.notifyDataSetChanged

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