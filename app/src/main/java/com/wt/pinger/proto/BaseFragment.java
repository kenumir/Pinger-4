package com.wt.pinger.proto;

import com.wt.pinger.data.DataRepository;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    public DataRepository getRepository() {
        if (getActivity() instanceof BaseActivity) {
            return ((BaseActivity) getActivity()).getRepository();
        }
        throw new RuntimeException("Parent activity is not extends BaseActivity");
    }

}
