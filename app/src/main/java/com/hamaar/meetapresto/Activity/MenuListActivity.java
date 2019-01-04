package com.hamaar.meetapresto.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.hamaar.meetapresto.Adapter.MenuAdapter;
import com.hamaar.meetapresto.Model.Menu;
import com.hamaar.meetapresto.R;
import com.hamaar.meetapresto.Utils.GlobalVars;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MenuListActivity extends AppCompatActivity {
    private RecyclerView rv;
    private MenuAdapter adapter;
    private Gson gson;
    private List<Menu> allList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        gson = new Gson();
        allList = new ArrayList<>();
        rv = findViewById(R.id.rvMenuList);

        adapter = new MenuAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());

        rv.setAdapter(adapter);

        loadData();

    }

    private void loadData() {

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage(getString(R.string.please_wait_onprocess));
        progress.setTitle(getString(R.string.please_wait_onprocess));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        if (adapter != null)
            adapter.clearAll();

        AndroidNetworking.get(GlobalVars.BASE_IP + "menu/readAll.php")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Menu> results = new ArrayList<>();
                        try {

                            if (results != null)
                                results.clear();

                            String message = response.getString("message");

                            if (message.equals("success")) {
                                String records = response.getString("records");

                                JSONArray dataArr = new JSONArray(records);

                                if (dataArr.length() > 0) {

                                    for (int i = 0; i < dataArr.length(); i++) {
                                        Menu menu = gson.fromJson(dataArr.getJSONObject(i).toString(), Menu.class);
                                        results.add(menu);
                                    }
                                }
                            }

                            progress.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();

                            progress.dismiss();
                        }

                        adapter.addAll(results);

                    }

                    @Override
                    public void onError(ANError anError) {
                        progress.dismiss();
                    }
                });
    }

}
