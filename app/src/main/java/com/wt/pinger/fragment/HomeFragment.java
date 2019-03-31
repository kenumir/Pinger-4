package com.wt.pinger.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wt.pinger.R;
import com.wt.pinger.data.PingItem;
import com.wt.pinger.data.PingItemViewModel;
import com.wt.pinger.proto.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends BaseFragment {

    private RecyclerView recycler;
    private PingItemViewModel mPingItemViewModel;
    private Adapter mAdapter;

    public HomeFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View res = inflater.inflate(R.layout.fragment_home, container, false);
        recycler = res.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(mAdapter = new Adapter());

        mPingItemViewModel = ViewModelProviders.of(this).get(PingItemViewModel.class);
        mPingItemViewModel.getAll().observe(this, posts -> mAdapter.swapData(posts));
        return res;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private static class Holder extends RecyclerView.ViewHolder {

        public Holder(@NonNull View itemView) {
            super(itemView);
        }

        public void update(@NonNull PingItem item) {

        }
    }

    private static class Adapter extends RecyclerView.Adapter<Holder> {

        private ArrayList<PingItem> mItems = new ArrayList<>();

        public Adapter() {
            setHasStableIds(true);
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ping, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            PingItem item = mItems.get(position);
            holder.update(item);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        @Override
        public long getItemId(int position) {
            return mItems.get(position)._id;
        }

        public void swapData(@Nullable List<PingItem> newItems) {
            PingItemDiffCallback postDiffCallback = new PingItemDiffCallback(mItems, newItems == null ? new ArrayList<>() : newItems);
            mItems.clear();
            if (newItems != null) {
                mItems.addAll(newItems);
            }
            DiffUtil.calculateDiff(postDiffCallback).dispatchUpdatesTo(this);
        }
    }

    public static class PingItemDiffCallback extends DiffUtil.Callback {

        private final List<PingItem> oldPosts, newPosts;

        PingItemDiffCallback(List<PingItem> oldPosts, List<PingItem> newPosts) {
            this.oldPosts = oldPosts;
            this.newPosts = newPosts;
        }

        @Override
        public int getOldListSize() {
            return oldPosts.size();
        }

        @Override
        public int getNewListSize() {
            return newPosts.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldPosts.get(oldItemPosition)._id == newPosts.get(newItemPosition)._id;
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldPosts.get(oldItemPosition).equals(newPosts.get(newItemPosition));
        }
    }
}
