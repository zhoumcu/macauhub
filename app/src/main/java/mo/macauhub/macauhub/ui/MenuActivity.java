package mo.macauhub.macauhub.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.zhiao.baselib.base.BaseActivity;
import cn.zhiao.baselib.custom.weight.FullyLinearLayoutManager;
import mo.macauhub.macauhub.MainActivity;
import mo.macauhub.macauhub.R;
import mo.macauhub.macauhub.adapter.CategoryAdapter;
import mo.macauhub.macauhub.bean.Categorys;
import mo.macauhub.macauhub.bean.Contants;

/**
 * Created by ymn on 2017/4/12.
 */
public class MenuActivity extends BaseActivity {
    @Bind(R.id.map)
    ImageView map;
    @Bind(R.id.recycler)
    EasyRecyclerView recycler;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private CategoryAdapter adapter;
    private List<Categorys> categoryses = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            categoryses = (List<Categorys>)  msg.obj;
            adapter.addAll(categoryses);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView() {
        toolbar.setNavigationIcon(R.mipmap.arrow);
        setSupportActionBar(toolbar);

        recycler.setLayoutManager(new FullyLinearLayoutManager(getContext()));
        adapter = new CategoryAdapter(getContext());
        //recycler.setNestedScrollingEnabled(false);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("tags",categoryses.get(position).getTags());
                setResult(1002, intent);
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        initAdapte();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.aty_menu;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.info_menu, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_setting:
//                gt(SettingActivity.class);
//                break;
            case android.R.id.home:
                setResult(1003, new Intent(getContext(),MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initAdapte(){
        categoryses.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < Contants.categoryName.length; i++) {
                    Categorys categorys = new Categorys();
                    categorys.setImageUrl(Contants.imageUrl[i]);
                    categorys.setTags(Contants.tags[i]);
                    categorys.setAtitle(getResources().getString(Contants.categoryName[i]));
                    categoryses.add(categorys);
                }
                Message msg = new Message();
                msg.what = 1;
                msg.obj = categoryses;
                handler.sendMessage(msg);
            }
        }).start();
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
        overridePendingTransition(0, 0);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //do something...
            setResult(1003, new Intent(getContext(),MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void logoBack(View v){
        finish();
    }

    @Override
    public Object newP() {
        return null;
    }
}
