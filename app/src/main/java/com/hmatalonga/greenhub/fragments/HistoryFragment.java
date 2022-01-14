/*
 * Copyright (c) 2016 Hugo Matalonga & João Paulo Fernandes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hmatalonga.greenhub.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sharejourny.Utils.UserPrefs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hmatalonga.greenhub.R;
import com.hmatalonga.greenhub.response.AppResponse;
import com.hmatalonga.greenhub.ui.TaskListActivity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * HistoryFragment.
 */
public class HistoryFragment extends Fragment {

    private static final String TAG = "HistoryFragment";

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    ArrayList<AppResponse> list = new ArrayList<>();
    ListView listView;
    FirebaseFirestore firebase_db =  FirebaseFirestore.getInstance();
    CustomListAdapter customListAdapter;
    SwipeRefreshLayout pullToRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getApps();
            }
        });

        listView = view.findViewById(R.id.lv_apps);

        customListAdapter = new CustomListAdapter(getActivity(),list);
        listView.setAdapter(customListAdapter);
        getApps();






        return  view;
    }

    void getApps(){
        list.clear();
        firebase_db.collection("currentApps")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if(document.get("id").equals(new UserPrefs(getActivity()).getUserId())){

                                    String strDate = document.get("date").toString();

                                    SimpleDateFormat sdf=new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
                                    try {
                                        Date currentdate = sdf.parse(strDate);

                                        SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd, hh:mm");

                                        AppResponse appResponse = new AppResponse();
                                        appResponse.setDate(sdf2.format(currentdate));
                                        appResponse.setList(document.get("list").toString());
                                        appResponse.setApps(document.get("apps").toString());
                                        appResponse.setBluetooth(document.get("bluetooth").toString());
                                        appResponse.setHealth(document.get("health").toString());
                                        appResponse.setLevel(document.get("level").toString());
                                        appResponse.setMemory(document.get("memory").toString());
                                        appResponse.setTemperature(document.get("temperature").toString());
                                        appResponse.setTourch(document.get("tourch").toString());
                                        list.add(appResponse);

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }


                            customListAdapter.notifyDataSetChanged();
                        }

                        pullToRefresh.setRefreshing(false);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    public class CustomListAdapter extends BaseAdapter {


        private List<AppResponse> listData;
        private LayoutInflater layoutInflater;
        private Context context;

        public CustomListAdapter(Context aContext,  List<AppResponse> listData) {
            this.context = aContext;
            this.listData = listData;
            layoutInflater = LayoutInflater.from(aContext);
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.tip_list, null);
                holder = new ViewHolder();
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final AppResponse tip = this.listData.get(position);
            holder.tv_title.setText(tip.getDate());

            holder.tv_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TaskListActivity.class);
                    intent.putExtra("list", (Serializable) tip);
                    context.startActivity(intent);
                }
            });



            return convertView;
        }


        class ViewHolder {
            TextView tv_title;

        }

    }

}
