package caojun.com.logintest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import caojun.com.logintest.R;

/**
 * Created by tiger on 2017/3/17.
 */

public class ContentFragment extends Fragment {

    private String title;

    public static ContentFragment getInstance(String title) {
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        this.title = bundle.getString("title");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_content, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {

        TextView tv = (TextView) inflate.findViewById(R.id.tv_fragment);
        tv.setText(title);
    }
}
