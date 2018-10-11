package com.example.administrator.demoroom;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.demoroom.db.UserEntity;
import com.example.administrator.demoroom.utils.ItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Button mButtonAdd;
    private SwipeRefreshLayout mRefreshLayout;
    private UserAdapter mUserAdapter;
    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mUserViewModel.getUserByLiveData();
        mUserViewModel.getObservableUsers().observe(this, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(@Nullable List<UserEntity> userEntities) {
                mUserAdapter.setNewData(userEntities);
                mRefreshLayout.setRefreshing(false);
            }
        });
        initView();
    }

    private void initView() {
        mRefreshLayout = findViewById(R.id.refresh);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                mUserViewModel.getUserFromDb();
                mUserViewModel.getUserByLiveData();
            }
        });
        mButtonAdd = findViewById(R.id.btn_add);
        mButtonAdd.setOnClickListener(view -> {
            List<UserEntity> userEntities = new ArrayList<>();
            UserEntity userEntity = new UserEntity();
            userEntity.setName("增加啦");
            userEntity.setAddress("增加啦lalala");
            userEntities.add(userEntity);
            mUserViewModel.insertUser(userEntities);
        });


        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUserAdapter = new UserAdapter();
        mUserAdapter.setOnDeleteListener(userEntity -> {
            mUserViewModel.deleteUser(userEntity);
        });
        mUserAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                UserEntity userEntity = (UserEntity) adapter.getData().get(position);
                mUserViewModel.updateUser(userEntity);
            }
        });
        mRecyclerView.setAdapter(mUserAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(mUserAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }
}
