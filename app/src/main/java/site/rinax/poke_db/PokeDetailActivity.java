package site.rinax.poke_db;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PokeDetailActivity extends AppCompatActivity {
    private String mName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_detail);

//        Toolbar toolbar = findViewById(R.id.toolbar);
        Intent intent = getIntent();
//        String toolbarTitle = intent.getStringExtra("POKE_NAME");
//        toolbar.setTitle(toolbarTitle);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.detail_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(PokeDetailActivity.this));
        ArrayList list = new ArrayList();
        mName = intent.getStringExtra("POKE_NAME");
        list.add(mName);
        PokeListRecyclerViewAdapter adapter = new PokeListRecyclerViewAdapter(PokeDetailActivity.this,list);
        recyclerView.setAdapter(adapter);
        dispAllDamageTable();
        dispStatus();
        dispCharaView();
    }
    private void dispStatus(){
        DatabaseHelper helper = new DatabaseHelper(PokeDetailActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = null;
        try {
            String sql = "select * from param inner join master on param._id = master._id where name = '"+mName+"'";
            c = db.rawQuery(sql, null);
            boolean mov = c.moveToFirst();
            while (mov) {
                int sum = 0;
                TextView tv = findViewById(R.id.tv_h);
                tv.setText(c.getInt(1)+"");
                sum = c.getInt(1);
                tv = findViewById(R.id.tv_a);
                tv.setText(c.getInt(2)+"");
                sum += c.getInt(2);
                tv = findViewById(R.id.tv_b);
                tv.setText(c.getInt(3)+"");
                sum += c.getInt(3);
                tv = findViewById(R.id.tv_c);
                tv.setText(c.getInt(4)+"");
                sum += c.getInt(4);
                tv = findViewById(R.id.tv_d);
                tv.setText(c.getInt(5)+"");
                sum += c.getInt(5);
                tv = findViewById(R.id.tv_s);
                tv.setText(c.getInt(6)+"");
                sum += c.getInt(6);
                tv = findViewById(R.id.tv_all);
                tv.setText(sum+"");

                mov = c.moveToNext();
            }
        } finally {
            db.close();
            if (c != null) c.close();
        }

    }
    private void dispAllDamageTable(){
        DatabaseHelper helper = new DatabaseHelper(PokeDetailActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = null;
        try {
            String sqlstr1 = "select type from type inner join master on type.num = master._id where name = '" + mName+"'";
            c = db.rawQuery(sqlstr1, null);
            boolean mov = c.moveToFirst();
            TypeEffect t = new TypeEffect();

            int i=0;
            int type=0;
            float[] mTypeEffect = null;
            while (mov) {

                if(i==0) {
                    type = c.getInt(0);
                    mTypeEffect = t.getDamageResult(type);
                }else if(i==1){
                    mTypeEffect = t.getDamageResult(type,c.getInt(0));
                }
                mov = c.moveToNext();
                i++;
            }
            for(int j=0;j<18;j++) {
                if(mTypeEffect[j]!=1){
                    dispTypeImage(mTypeEffect[j],j,PokeDetailActivity.this);
                }
            }

            TextView text = findViewById(R.id.text_immune);
            LinearLayout layout = findViewById(R.id.type_immune);
            if(layout.getChildCount() == 0){
                text.setVisibility(View.GONE);
            }else{
                text.setVisibility(View.VISIBLE);
            }
            text = findViewById(R.id.text_resistant);
            layout = findViewById(R.id.type_resistant2);
            if(layout.getChildCount() == 0){
                text.setVisibility(View.GONE);
            }else{
                text.setVisibility(View.VISIBLE);
            }
        }finally {
                db.close();
                if (c != null) c.close();
        }

    }
    public void dispTypeImage(float num, int type, Context context){
        TypeEffect te = new TypeEffect();
        int padding =  getResources().getDimensionPixelSize(R.dimen.type_padding)/2;
        int textSize = 17;
        if (num == 0.5f||num == 0.25f) {
            LinearLayout layout;
            LinearLayout newLayout = new LinearLayout(context);
            newLayout.setVerticalGravity(Gravity.BOTTOM);
            newLayout.setOrientation(LinearLayout.HORIZONTAL);
            newLayout.setPadding(padding,0,0,0);
            TextView tv = new TextView(context);
            tv.setTextSize(textSize);
            if(num==0.5f){
                layout = findViewById(R.id.type_resistant2);
                tv.setText("0.5   ×");
            }else{
                layout = findViewById(R.id.type_resistant);
                tv.setText("0.25 ×");
            }
            newLayout.addView(tv);
            ImageView img = new ImageView(context);
            img.setPadding(padding,padding,0,0);
            img.setImageResource(te.getImageResoceToNum(type));
            newLayout.addView(img);
            layout.addView(newLayout);

        } else if (num == 2||num == 4) {
            LinearLayout layout;
            LinearLayout newLayout = new LinearLayout(context);
            newLayout.setPadding(padding,0,0,0);
            newLayout.setOrientation(LinearLayout.HORIZONTAL);
            newLayout.setVerticalGravity(Gravity.BOTTOM);
            TextView tv = new TextView(context);
            tv.setTextSize(textSize);
            if(num==2){
                layout = findViewById(R.id.type_effective2);
                tv.setText("2 ×");
            }else{
                layout = findViewById(R.id.type_effective);
                tv.setText("4 ×");
            }
            newLayout.addView(tv);
            ImageView img = new ImageView(context);

            img.setPadding(padding,padding,0,0);
            img.setImageResource(te.getImageResoceToNum(type));
            newLayout.addView(img);
            layout.addView(newLayout);

        } else if (num == 0) {
            LinearLayout layout = findViewById(R.id.type_immune);
            layout.setVisibility(View.VISIBLE);
            ImageView img = new ImageView(context);
            img.setPadding(padding,padding,0,0);
            img.setImageResource(te.getImageResoceToNum(type));
            layout.addView(img);
        }

    }
    private void dispCharaView(){
        DatabaseHelper helper = new DatabaseHelper(PokeDetailActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = null;
        try {
            LinearLayout layout = findViewById(R.id.chara_wrapper);
            String sql = "select chara from chara inner join master on chara._id = master._id where name = '"+mName+"'";
            c = db.rawQuery(sql, null);
            boolean mov = c.moveToFirst();
            while (mov) {
                TextView tv = new TextView(PokeDetailActivity.this);
                tv.setText(c.getString(0));
                tv.setGravity(Gravity.CENTER);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(PokeDetailActivity.this,"text",Toast.LENGTH_SHORT).show();
//                        if(expandableView.getVisibility() != View.VISIBLE) {
//                            expandableView.animate().alphaBy(0).alpha(1).translationYBy(-10).translationY(0).withStartAction(new TimerTask() {
//                                @Override
//                                public void run() {
//                                    // アニメーションが始まる前にViewをVISIBLEにする
//                                    expandableView.setVisibility(View.VISIBLE);
//                                }
//                            });
//                        }
//                        // 開じるようなアニメーション
//                        else {
//                            expandableView.animate().alpha(0).translationY(-10).withEndAction(new TimerTask(){
//                                @Override
//                                public void run() {
//
//                                    // アニメーションが終わったらViewをGONEにする
//                                    expandableView.setVisibility(View.GONE);
//                                }
//                            });
//                        }
                    }
                });
                layout.addView(tv,new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                mov = c.moveToNext();
            }
        } finally {
            db.close();
            if (c != null) c.close();
        }
    }
//    @Override
//    public boolean onSupportNavigateUp() {
//        finish();
//        return super.onSupportNavigateUp();
//    }
}
