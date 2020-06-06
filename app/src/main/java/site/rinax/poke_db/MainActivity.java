package site.rinax.poke_db;

import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.navigation.NavigationView;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    boolean mIsPressedFirstBack = false;
    FastScrollRecyclerView mRecyclerView;
    PokeListRecyclerViewAdapter adapter;
    String orderSQL = "ASC";
    String typeSQL = "*";
    String paramSQL = "master._id";
    String textSQL = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.drawer_open,
                R.string.drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(this);
        mRecyclerView = findViewById(R.id.poke_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        String sql = "select name from master";
        adapter = new PokeListRecyclerViewAdapter(MainActivity.this,SQLSelect(sql));

        mRecyclerView.setAdapter(adapter);

        initSppiner();
    }
    private void initSppiner(){
        String[] spinnerItems = new String[]{
                "すべて",
                "ノーマル",
                "ほのお",
                "みず",
                "でんき",
                "くさ",
                "こおり",
                "かくとう",
                "どく",
                "じめん",
                "ひこう",
                "エスパー",
                "むし",
                "いわ",
                "ゴースト",
                "ドラゴン",
                "あく",
                "はがね",
                "フェアリー"
        };
        Spinner typeSpinner = findViewById(R.id.type_sort);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                MainActivity.this,
                R.layout.type_sppiner_layout,
                spinnerItems
        );

        adapter.setDropDownViewResource(R.layout.type_spinner_dropdown_item);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                int pos = position-1;
                typeSQL = pos+"";
                if(position==0) {
                    typeSQL = "*";
                }
                updateViewList(createSql(typeSQL,paramSQL, orderSQL,textSQL));
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        typeSpinner.setAdapter(adapter);


        Spinner paramSpinner = findViewById(R.id.param_sort);
        spinnerItems = new String[]{
                "ID",
                "HP",
                "攻撃",
                "防御",
                "特攻",
                "特防",
                "素早さ",
                "合計"
        };
        adapter = new ArrayAdapter<>(
                MainActivity.this,
                R.layout.type_sppiner_layout,
                spinnerItems
        );

        adapter.setDropDownViewResource(R.layout.type_spinner_dropdown_item);
        paramSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                switch (position){
                    case 0:
                        paramSQL = "master._id";
                        break;
                    case 1:
                        paramSQL = "h";
                        break;
                    case 2:
                        paramSQL = "a";
                        break;
                    case 3:
                        paramSQL = "b";
                        break;
                    case 4:
                        paramSQL = "c";
                        break;
                    case 5:
                        paramSQL = "d";
                        break;
                    case 6:
                        paramSQL = "s";
                        break;
                    case 7:
                        paramSQL = "sum";
                        break;
                }
                updateViewList(createSql(typeSQL,paramSQL, orderSQL,textSQL));

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        paramSpinner.setAdapter(adapter);


        Spinner orderSpinner = findViewById(R.id.order_sort);
        spinnerItems = new String[]{
                "昇順",
                "降順"
        };
        adapter = new ArrayAdapter<>(
                MainActivity.this,
                R.layout.type_sppiner_layout,
                spinnerItems
        );

        adapter.setDropDownViewResource(R.layout.type_spinner_dropdown_item);

        orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                switch (position){
                    case 0:
                        orderSQL = "ASC";
                        break;
                    case 1:
                        orderSQL = "DESC";
                        break;

                }
                updateViewList(createSql(typeSQL,paramSQL, orderSQL,textSQL));

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        orderSpinner.setAdapter(adapter);


    }
    private String createSql(String type,String param,String order,String text){
        String sql = "";
        if(type.equals("*")){
            sql = "select name from master inner join param on master._id = param._id ";
            sql += "where name like \"" + text+"%\" or hira like \""+text+"%\" order by " + param+" "+order;
        }else{
            sql = "select name from (type inner join master on master._id = type.num) inner join param on type.num = param._id ";
            sql += "where (name like \"" + text+"%\" or hira like \""+text+"%\" )and type = " +type+" order by "+param +" "+order;
        }
        return sql;
    }

    public ArrayList SQLSelect(String sql) {
        ArrayList mList = new ArrayList();
        DatabaseHelper helper = new DatabaseHelper(MainActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = null;
        try {
//            String sql = "select name from master";
            c = db.rawQuery(sql, null);
            boolean mov = c.moveToFirst();
            while (mov) {
                mList.add(c.getString(0));
                mov = c.moveToNext();
            }
        } finally {
            db.close();
            if (c != null) c.close();
        }
        return mList;
    }

    private void updateViewList(String sql){
        adapter.setList(SQLSelect(sql));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent=null;
        switch (menuItem.getItemId()) {
            case R.id.menu_item1:
                intent = new Intent(MainActivity.this,PersonalityListActivity.class);
                break;
            case R.id.menu_item2:
                intent = new Intent(MainActivity.this,TypeEffectActivity.class);
                break;
            case R.id.menu_item3:
                intent = new Intent(MainActivity.this,MyFavoriteActivity.class);
                break;
        }
        startActivity(intent);
        return false;
    }

    //searchView start
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        MenuItem mSearch = menu.findItem(R.id.menu_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
//        mSearchView.setQueryHint("Search");
        mSearchView.setQueryHint(Html.fromHtml("<font color = #ffffff>Search</font>"));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                textSQL = newText;
                updateViewList(createSql(typeSQL,paramSQL, orderSQL,textSQL));
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    //searchView end



    @Override
    protected void onPause() {
        drawerLayout.closeDrawer(Gravity.LEFT);
        super.onPause();
    }

    @Override
    protected void onResume() {
//        mRecyclerView.startAnimation(getResources().getAnimation(R.anim.slide_out_left));
        super.onResume();
    }

    //終了確認start
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() != KeyEvent.KEYCODE_BACK) {
            mIsPressedFirstBack = false;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mIsPressedFirstBack = false;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent ev) {
        mIsPressedFirstBack = false;
        return super.dispatchTrackballEvent(ev);
    }

    @Override
    public void onBackPressed() {
        drawerLayout.closeDrawer(Gravity.LEFT);
        if (!mIsPressedFirstBack) {
            mIsPressedFirstBack = true;
            Toast.makeText(this, R.string.backkey_guard_message, Toast.LENGTH_SHORT).show();
            return;
        }

        super.onBackPressed();
    }
    //終了確認end
}
