package com.wt.pinger.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wt.pinger.R;
import com.wt.pinger.data.Ping;
import com.wt.pinger.data.PingViewModel;
import com.wt.pinger.dialog.AddressDialog;
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
    //private PingViewModel mPingViewModel;
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

        PingViewModel mPingViewModel = ViewModelProviders.of(this).get(PingViewModel.class);
        mPingViewModel.getAll().observe(this, posts -> {
            Log.i("pinger", "Home: observe " + posts);
            mAdapter.swapData(posts);
        });

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
        menu.add("Add").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new AddressDialog()
                        .show(getFragmentManager(), "address_dialog");
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public @interface ItemType {
        int PLACEHOLDER = 1;
        int ITEM = 2;
    }

    private static class Holder extends RecyclerView.ViewHolder {
        private TextView txtAddress;
        public Holder(@NonNull View itemView) {
            super(itemView);
            txtAddress = itemView.findViewById(R.id.txtAddress);
        }
        public void update(@Nullable Ping item, View.OnClickListener ocl) {
            itemView.setOnClickListener(ocl);
            txtAddress.setText(item.address);
        }
    }

    private static class PlaceholderHolder extends Holder {
        public PlaceholderHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class Adapter extends RecyclerView.Adapter<Holder> {

        private ArrayList<Ping> mItems = new ArrayList<>();
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
            Ping item = mItems.get(position);
            holder.update(item, item == null ? mOnPlaceholderClick : null);
        }

        @Override
        public int getItemViewType(int position) {
            Ping item = mItems.get(position);
            return item == null ? ItemType.PLACEHOLDER : ItemType.ITEM;
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        @Override
        public long getItemId(int position) {
            Ping item = mItems.get(position);
            return item != null ? item.id : 0;
        }

        public void swapData(@Nullable List<Ping> newItems) {
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

        private final List<Ping> oldPosts, newPosts;

        PingItemDiffCallback(List<Ping> oldPosts, List<Ping> newPosts) {
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
            return oldPosts.get(oldItemPosition).id == newPosts.get(newItemPosition).id;
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
