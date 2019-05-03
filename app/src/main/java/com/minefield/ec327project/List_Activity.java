package com.minefield.ec327project;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageButton;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

// This activity is requesting data from firebase and showing our leaderboard

public class List_Activity extends BaseActivity {
    @BindView(R.id.List_Title)
    TextView     tv;
    @BindView(R.id.List_Recy)
    RecyclerView mRecy;
    @BindView(R.id.noData)
    TextView     tv_noData;
    @BindView(R.id.backButton)
    ImageButton     back_button;
    private ListAdapter       mAdapter;
    private FirebaseFirestore instance;
    private PostScoreDialog   dialog;
    private String            level;
    private int scoresToDisplay;
    @Override
    protected int getLayoutId() {
        return R.layout.score_list;
    }




    @Override
    protected void initView() {
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (v.getId() == R.id.backButton) {
                    startActivity(new Intent(mActivity, HomeScreen.class));
                    //finish();
                }
            }

        });
        dialog = new PostScoreDialog(this);
        instance = FirebaseFirestore.getInstance();
        level = getIntent().getStringExtra("Level");
        tv.setText("Level:" + level);
        mAdapter = new ListAdapter(new ArrayList<ScoreBean>(), this);
        mRecy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecy.setAdapter(mAdapter);
        getList();
    }

    //Requesting data from firebase
    void getList() {
        dialog.progressShow("Loading");
        final List<ScoreBean> list = new ArrayList<>();
        CollectionReference collection = instance.collection(Utils.getTableName());
        collection.whereEqualTo("level", level)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        Log.e("yxs", "Length：" + queryDocumentSnapshots.getDocuments().size());
                        if(queryDocumentSnapshots.getDocuments().size() > 5){
                            scoresToDisplay = 5;
                        }else{
                            scoresToDisplay = queryDocumentSnapshots.getDocuments().size();
                        }
                        for (int i = 0; i < scoresToDisplay; i++) {
                            Log.e("yxs", "Data：" + queryDocumentSnapshots.getDocuments().toString());
                            list.add(new ScoreBean(queryDocumentSnapshots.getDocuments().get(i).get("phone").toString(),
                                    queryDocumentSnapshots.getDocuments().get(i).get("level").toString(),
                                    Long.parseLong(queryDocumentSnapshots.getDocuments().get(i).get("time").toString()))
                            );
                        }
                        if (list.isEmpty()) {
                            dialog.dismiss();
                            tv_noData.setVisibility(View.VISIBLE);
                            mRecy.setVisibility(View.GONE);
                            return;
                        }
                        Collections.sort(list, Utils.comparator);
                        mAdapter.updateList(list);
                        dialog.dismiss();
                    }
                });
    }

    //RecyclerView is used for updating leaderboard
    //It recycles item_score_list to show items in score_list
    class ListAdapter extends RecyclerView.Adapter<ListAdapter.Holder> {
        private List<ScoreBean> mList;
        private Context         context;

        ListAdapter(List<ScoreBean> mList, Context context) {
            this.mList = mList;
            this.context = context;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new Holder(LayoutInflater.from(context).inflate(R.layout.item_score_list, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int i) {
            ScoreBean bean = mList.get(i);
            String text = "Rank:" + Integer.toString(i+ 1) + "        User: " + bean.getPhone() + "     " + Utils.formatTime(bean.getTime());
            holder.tv_rank.setText(text);
            //holder.tv_user.setText("User:" + bean.getPhone());
            //holder.tv_time.setText("" + Utils.formatTime(bean.getTime()));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public void updateList(List<ScoreBean> list) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }

        class Holder extends RecyclerView.ViewHolder {
            private TextView tv_user;
            private TextView tv_time;
                private TextView tv_rank;

                Holder(@NonNull View itemView) {
                    super(itemView);
                    //tv_user = itemView.findViewById(R.id.Item_List_Title);
                    //tv_time = itemView.findViewById(R.id.Item_List_Time);
                    tv_rank = itemView.findViewById(R.id.Item_List_ranking);
            }
        }
    }
}
