package edu.uncc.finalexam;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, int resource, @NonNull List<News> objects) {
        super(context, resource, objects); //context, id of row, objects
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_row_item, parent, false);
        }

        News news = getItem(position);
        TextView textViewNewsTitle = convertView.findViewById(R.id.textViewNewsTitle);
        TextView textViewNewsAuthor = convertView.findViewById(R.id.textViewNewsAuthor);
        TextView textViewSourceName = convertView.findViewById(R.id.textViewSourceName);
        TextView textViewPublishedAt = convertView.findViewById(R.id.textViewPublishedAt);

        ImageView imageViewNewsIcon = convertView.findViewById(R.id.imageViewNewsIcon);


        textViewNewsTitle.setText(news.title);
        textViewNewsAuthor.setText(news.author);
        textViewSourceName.setText(news.source_name);
        textViewPublishedAt.setText(news.publishedAt);

        imageViewNewsIcon.setImageURI(Uri.parse(news.urlToImage));

        return convertView;


    }
}
