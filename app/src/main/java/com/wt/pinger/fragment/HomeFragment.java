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
        recycler.setAdapter(mAdapter = new Adapter(v -> {

        }));

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

    public @interface ItemType {
        int PLACEHOLDER = 1;
        int ITEM = 2;
    }

    private static class Holder extends RecyclerView.ViewHolder {
        public Holder(@NonNull View itemView) {
            super(itemView);
        }
        public void update(@Nullable PingItem item, View.OnClickListener ocl) {
            itemView.setOnClickListener(ocl);
        }
    }

    private static class PlaceholderHolder extends Holder {
        public PlaceholderHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class Adapter extends RecyclerView.Adapter<Holder> {

        private ArrayList<PingItem> mItems = new ArrayList<>();
        private View.OnClickListener mOnPlaceholderClick;

        public Adapter(View.OnClickListener pc) {
            setHasStableIds(true);
            mOnPlaceholderClick = pc;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            switch(viewType) {
                default:
                case ItemType.ITEM: return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ping, parent, false));
                case ItemType.PLACEHOLDER: return new PlaceholderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ping_placeholder, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            PingItem item = mItems.get(position);
            holder.update(item, item == null ? mOnPlaceholderClick : null);
        }

        @Override
        public int getItemViewType(int position) {
            PingItem item = mItems.get(position);
            return item == null ? ItemType.PLACEHOLDER : ItemType.ITEM;
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        @Override
        public long getItemId(int position) {
            PingItem item = mItems.get(position);
            return item != null ? item._id : 0L;
        }

        public void swapData(@Nullable List<PingItem> newItems) {
            PingItemDiffCallback postDiffCallback = new PingItemDiffCallback(mItems, newItems == null ? new ArrayList<>() : newItems);
            mItems.clear();
            if (newItems == null || newItems.size() == 0) {
                mItems.add(null);
            } else {
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
            if (oldPosts.get(oldItemPosition) == null || newPosts.get(newItemPosition) == null) {
                return false;
            }
            return oldPosts.get(oldItemPosition)._id == newPosts.get(newItemPosition)._id;
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            if (oldPosts.get(oldItemPosition) == null || newPosts.get(newItemPosition) == null) {
                return false;
            }
            return oldPosts.get(oldItemPosition).equals(newPosts.get(newItemPosition));
        }
    }
}
