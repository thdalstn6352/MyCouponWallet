package com.example.mycouponwallet;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class RecyclerActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MyAdapter myAdapter;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        mRecyclerView = findViewById(R.id.recyclerView);

        preferences = this.getSharedPreferences("My_Pref", MODE_PRIVATE);

        getMyList();
    }

    private void getMyList() {
        ArrayList<Model> models = new ArrayList<>();

        Model m = new Model();
        m.setTitle("Ediya");
        m.setDescription("ediya Cafe.");
        m.setImg(R.drawable.ediya);
        models.add(m);

        m = new Model();
        m.setTitle("Starbucks");
        m.setDescription("starbucks Cafe.");
        m.setImg(R.drawable.starbucks);
        models.add(m);

        m = new Model();
        m.setTitle("빽다방");
        m.setDescription("bback Cafe.");
        m.setImg(R.drawable.bbackdabang);
        models.add(m);

        m = new Model();
        m.setTitle("Blue bottle");
        m.setDescription("blue_bottle Cafe.");
        m.setImg(R.drawable.bluebottle);
        models.add(m);

        m = new Model();
        m.setTitle("Angel in us");
        m.setDescription("angel_in_us Cafe.");
        m.setImg(R.drawable.angel_in_us);
        models.add(m);

        m = new Model();
        m.setTitle("TomNToms");
        m.setDescription("Tom N Toms Cafe.");
        m.setImg(R.drawable.tomntoms);
        models.add(m);

        m = new Model();
        m.setTitle("Hollys");
        m.setDescription("Hollys Cafe.");
        m.setImg(R.drawable.hollys);
        models.add(m);

        String mSortSetting = preferences.getString("Sort", "ascending");

        if (mSortSetting.equals("ascending")) {
            Collections.sort(models, Model.By_TITLE_ASCENDING);
        }
        else if (mSortSetting.equals("descending")) {
            Collections.sort(models, Model.By_TITLE_DESCENDING);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(this, models);
        mRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                myAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                myAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sorting) {
            sortDialog();
            return true;
        }
        if (id == R.id.logout) {
            Logout();
        }

        return super.onOptionsItemSelected(item);
    }

    private void Logout() {
        Intent intent = new Intent(RecyclerActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(RecyclerActivity.this, "성공적으로 로그아웃 하였습니다.", Toast.LENGTH_SHORT).show();
    }
    private void sortDialog() {

        String[] options = {"Ascending", "Descending"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Sort by");
        builder.setIcon(R.drawable.ic_action_sort);

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Sort", "ascending");
                    editor.apply();
                    getMyList();
                }

                if (which == 1) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Sort", "descending");
                    editor.apply();
                    getMyList();
                }
            }
        });

        builder.create().show();
    }

}
