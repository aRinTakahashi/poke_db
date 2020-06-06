package site.rinax.poke_db;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class PersonalityListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<String> cap = initArrayList();

        int categoryNum = 24;
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        for(int i=0; i<categoryNum; i++){
            TableRow tableRow = (TableRow)getLayoutInflater().inflate(R.layout.type_table_row, null);
            TextView name = tableRow.findViewById(R.id.rowtext1);
            name.setText(cap.get(i));
            String maru = "◯";
            String batu = "×";
            TextView a = tableRow.findViewById(R.id.rowtext2);
            TextView b = tableRow.findViewById(R.id.rowtext3);
            TextView c = tableRow.findViewById(R.id.rowtext4);
            TextView d = tableRow.findViewById(R.id.rowtext5);
            TextView s = tableRow.findViewById(R.id.rowtext6);
            if(i<4) {
                setTextAndColor(a,maru);
                if(i%4==0){
                    setTextAndColor(b,batu);
                }else if(i%4==1){
                    setTextAndColor(c,batu);
                }else if(i%4==2){
                    setTextAndColor(d,batu);
                }else if(i%4==3){
                    setTextAndColor(s,batu);
                }
            }else if(i<8){
                setTextAndColor(b,maru);
                if(i%4==0){
                    setTextAndColor(a,batu);
                }else if(i%4==1){
                    setTextAndColor(c,batu);
                }else if(i%4==2){
                    setTextAndColor(d,batu);
                }else if(i%4==3){
                    setTextAndColor(s,batu);
                }
            }else if(i<12){
                setTextAndColor(c,maru);
                if(i%4==0){
                    setTextAndColor(a,batu);
                }else if(i%4==1){
                    setTextAndColor(b,batu);
                }else if(i%4==2){
                    setTextAndColor(d,batu);
                }else if(i%4==3){
                    setTextAndColor(s,batu);
                }
            }else if(i<16){
                setTextAndColor(d,maru);
                if(i%4==0){
                    setTextAndColor(a,batu);
                }else if(i%4==1){
                    setTextAndColor(b,batu);
                }else if(i%4==2){
                    setTextAndColor(c,batu);
                }else if(i%4==3){
                    setTextAndColor(s,batu);
                }
            }else if(i<20){
                setTextAndColor(s,maru);
                if(i%4==0){
                    setTextAndColor(a,batu);
                }else if(i%4==1){
                    setTextAndColor(b,batu);
                }else if(i%4==2){
                    setTextAndColor(c,batu);
                }else if(i%4==3){
                    setTextAndColor(d,batu);
                }
            }
            if((i+1)%2 == 0){
                int color = getResources().getColor(R.color.table_row_color);
                name.setBackgroundColor(color);
                a.setBackgroundColor(color);
                b.setBackgroundColor(color);
                c.setBackgroundColor(color);
                d.setBackgroundColor(color);
                s.setBackgroundColor(color);

            }

            tableLayout.addView(tableRow, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }
    
    private void setTextAndColor(TextView tv,String str){
        int color;
        tv.setText(str);
        if(str.equals("◯")){
            color = getResources().getColor(R.color.red);
            tv.setTypeface(Typeface.DEFAULT_BOLD);
        }else{
            color = getResources().getColor(R.color.blue);
        }
        tv.setTextColor(color);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    private ArrayList<String> initArrayList(){
        ArrayList<String> cap = new ArrayList<>();
        cap.add("さみしがり");
        cap.add("いじっぱり");
        cap.add("やんちゃ");
        cap.add("ゆうかん");
        cap.add("ずぶとい");
        cap.add("わんぱく");
        cap.add("のうてんき");
        cap.add("のんき");
        cap.add("ひかえめ");
        cap.add("おっとり");
        cap.add("うっかりや");
        cap.add("れいせい");
        cap.add("おだやか");
        cap.add("おとなしい");
        cap.add("しんちょう");
        cap.add("なまいき");
        cap.add("おくびょう");
        cap.add("せっかち");
        cap.add("ようき");
        cap.add("むじゃき");
        cap.add("てれや");
        cap.add("がんばりや");
        cap.add("すなお");
        cap.add("きまぐれ");
        cap.add("まじめ");
        return cap;
    }
    
}
