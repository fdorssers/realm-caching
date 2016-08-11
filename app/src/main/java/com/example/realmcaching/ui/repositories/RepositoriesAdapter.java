package com.example.realmcaching.ui.repositories;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.realmcaching.model.entity.Repository;
import com.example.realmcaching.utility.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by frank on 2-6-2016.
 */

public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesAdapter.RepositoryViewHolder> {

    private ImageLoader imageLoader;

    private List<Repository> repositories;

    public RepositoriesAdapter(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        repositories = new ArrayList<>();
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepositoryViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false));
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        Repository repository = repositories.get(position);
        holder.text1.setText(repository.name);
        holder.text2.setText(repository.owner.login);
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public void setData(List<Repository> repositories) {
        this.repositories = repositories;
    }

    class RepositoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(android.R.id.text1) TextView text1;
        @BindView(android.R.id.text2) TextView text2;

        public RepositoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
