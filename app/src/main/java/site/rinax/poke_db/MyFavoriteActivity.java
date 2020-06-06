package site.rinax.poke_db;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MyFavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button bt = findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLSelect();
            }
        });
    }
    public void SQLSelect() {

        DatabaseHelper helper = new DatabaseHelper(MyFavoriteActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = null;
        try {
            String sqlstr1 = "select * from type cross join master";
            c = db.rawQuery(sqlstr1, null);
            boolean mov = c.moveToFirst();
            int i = 0;
            while (mov) {
//                int n = c.getColumnIndex("name");
                System.out.println("Select >>>>　" +c.getString(0)+c.getString(1)+c.getString(2));
                i++;
                mov = c.moveToNext();
//Log.d("rinrin",n+"");

            }
        }finally {
            db.close();
            if (c != null) c.close();
        }



    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}
