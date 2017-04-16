package com.zhiao.develop.macaumagazine.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.zhiao.develop.macaumagazine.MainActivity;
import com.zhiao.develop.macaumagazine.R;
import com.zhiao.develop.macaumagazine.adapter.CategoryAdapter;
import com.zhiao.develop.macaumagazine.bean.Categorys;
import com.zhiao.develop.macaumagazine.bean.Contants;
import com.zhiao.develop.macaumagazine.ui.ImageAcitivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.zhiao.baselib.base.BaseFragment;
import cn.zhiao.baselib.custom.weight.FullyLinearLayoutManager;

/**
 * Created by ymn on 2017/4/12.
 */
public class MenuFragment extends BaseFragment {
    @Bind(R.id.map)
    ImageView map;
    @Bind(R.id.recycler)
    EasyRecyclerView recycler;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    private CategoryAdapter adapter;
    private List<Categorys> categoryses = new ArrayList<>();

    public static MenuFragment newInstance() {
        Bundle args = new Bundle();
        MenuFragment fragment = new MenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static void jumpIn(AppCompatActivity at) {
        FragmentManager fragmentmanager = at.getSupportFragmentManager();
        MenuFragment fragment = MenuFragment.newInstance();
        fragmentmanager
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(R.anim.slide_right_in,R.anim.slide_left_out)
                .replace(R.id.content_container, fragment, MenuFragment.MY_TAG)
                .commitAllowingStateLoss();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView() {
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setLogo(R.mipmap.alogo);
//        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.mipmap.arrow);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void onResume() {
        super.onResume();
        initAdapte();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.aty_menu;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.info_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_setting:
//                gt(SettingActivity.class);
//                break;
//            case android.R.id.home:
//                setResult(1003, new Intent(getContext(),MainActivity.class));
//                finish();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
    private void initAdapte(){
        categoryses.clear();
        recycler.setLayoutManager(new FullyLinearLayoutManager(getContext()));
        for (int i = 0; i < Contants.categoryName.length; i++) {
            Categorys categorys = new Categorys();
            categorys.setImageUrl(Contants.imageUrl[i]);
            categorys.setTags(Contants.tags[i]);
            categorys.setAtitle(getResources().getString(Contants.categoryName[i]));
            categoryses.add(categorys);
        }
        adapter = new CategoryAdapter(getContext(), categoryses);
        //recycler.setNestedScrollingEnabled(false);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("tags",categoryses.get(position).getTags());
                getActivity().setResult(1002, intent);
                getActivity().finish();
            }
        });
    }
    @OnClick(R.id.map)
    public void onClick() {
        Intent intent = new Intent(getContext(), ImageAcitivity.class);
        intent.putExtra("images", "drawable://R.drawable.splash");//非必须
        int[] location = new int[2];
        map.getLocationOnScreen(location);
        intent.putExtra("locationX", location[0]);//必须
        intent.putExtra("locationY", location[1]);//必须
        intent.putExtra("width", map.getWidth());//必须
        intent.putExtra("height", map.getHeight());//必须
        startActivity(intent);
        getActivity().overridePendingTransition(0, 0);
    }
}
