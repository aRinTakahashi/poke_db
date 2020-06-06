package site.rinax.poke_db;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class TypeEffectActivity extends AppCompatActivity {
    ArrayList<Integer> buttonList = new ArrayList<>();
    TypeButton[] bt = new TypeButton[18];
    TypeEffect mTypeEffect = new TypeEffect();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_effect);


        initView();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void initView(){

        buttonList.add(R.id.nomaru);
        buttonList.add(R.id.honoo);
        buttonList.add(R.id.mizu);
        buttonList.add(R.id.denki);
        buttonList.add(R.id.kusa);
        buttonList.add(R.id.koori);
        buttonList.add(R.id.kakutou);
        buttonList.add(R.id.doku);
        buttonList.add(R.id.jimen);
        buttonList.add(R.id.hikou);
        buttonList.add(R.id.esupa);
        buttonList.add(R.id.musi);
        buttonList.add(R.id.iwa);
        buttonList.add(R.id.gosuto);
        buttonList.add(R.id.doragon);
        buttonList.add(R.id.aku);
        buttonList.add(R.id.hagane);
        buttonList.add(R.id.feari);

        for (int i=0;i<buttonList.size();i++){
            bt[i] = new TypeButton();
            bt[i].type = mTypeEffect.getDamageResult(i);
        }




        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColorButton(v);
                dispAllDamageTable();

            }
        };
        for(int i=0;i<buttonList.size();i++){
            bt[i].onPushed=false;
            bt[i].bt = findViewById(buttonList.get(i));
            bt[i].bt.setOnClickListener(listener);
            bt[i].bt.setColorFilter(0x55ffffff);

        }


    }

    private int getOnPushed(){
        int num=0;
        for (int i=0;i<buttonList.size();i++) {
            if(bt[i].onPushed) {
                num++;
            }
        }
        return num;
    }

    private void changeColorButton(View v) {
        resetView();
        int onPushed = getOnPushed();
        for (int i = 0; i < buttonList.size(); i++) {
            if (bt[i].bt.getId() == v.getId()) {
                if (bt[i].onPushed) {
                    bt[i].onPushed = false;
                    bt[i].bt.setColorFilter(0x55ffffff);
                } else if (!bt[i].onPushed && onPushed < 2) {
                    bt[i].onPushed = true;
                    bt[i].bt.clearColorFilter();
                }else if(onPushed==2){
                    Toast.makeText(TypeEffectActivity.this,getResources().getString(R.string.type_warning_message),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void dispAllDamageTable(){
        int onPushed = getOnPushed();

        if(onPushed==1){
            for(int i=0;i<18;i++) {
                if(bt[i].onPushed){
                    for(int j=0;j<18;j++) {
                        if(bt[i].type[j]!=1){
                            dispTypeImage(bt[i].type[j],j,TypeEffectActivity.this);
                        }
                    }
                    break;
                }
            }
        }else if(onPushed==2){
            int type1=-1;
            for(int i=0;i<18;i++) {
                if(bt[i].onPushed){
                    if (type1 == -1) {
                        type1=i;
                    } else {
                        float[] result = mTypeEffect.getDamageResult(type1,i);
                        for(int j=0;j<18;j++){
                            dispTypeImage(result[j], j,TypeEffectActivity.this);
                        }
                    }

                }
            }

        }
    }
    private void resetView(){
        LinearLayout layout = findViewById(R.id.type_resistant);
        layout.removeAllViews();
        layout = findViewById(R.id.type_resistant2);
        layout.removeAllViews();
        layout = findViewById(R.id.type_effective);
        layout.removeAllViews();
        layout = findViewById(R.id.type_effective2);
        layout.removeAllViews();
        layout = findViewById(R.id.type_immune);
        layout.removeAllViews();
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
                ImageView img = new ImageView(context);
                img.setPadding(padding,padding,0,0);
                img.setImageResource(te.getImageResoceToNum(type));
                layout.addView(img);
            }

    }
}

class TypeButton{
    boolean onPushed=false;
    ImageButton bt;
    float[] type;

}

class TypeEffect{
    public static final int TYPE_NOMARU = 0;
    public static final int TYPE_HONOO = 1;
    public static final int TYPE_MIZU = 2;
    public static final int TYPE_DENKI = 3;
    public static final int TYPE_KUSA = 4;
    public static final int TYPE_KOORI = 5;
    public static final int TYPE_KAKUTOU = 6;
    public static final int TYPE_DOKU = 7;
    public static final int TYPE_JIMEN = 8;
    public static final int TYPE_HIKOU = 9;
    public static final int TYPE_ESUPA = 10;
    public static final int TYPE_MUSI = 11;
    public static final int TYPE_IWA = 12;
    public static final int TYPE_GOSUTO = 13;
    public static final int TYPE_DORAGON = 14;
    public static final int TYPE_AKU = 15;
    public static final int TYPE_HAGANE = 16;
    public static final int TYPE_FEARI = 17;

    private float[] nomaru;
    private float[] honoo;
    private float[] mizu;
    private float[] denki;
    private float[] kusa;
    private float[] koori;
    private float[] kakutou;
    private float[] doku;
    private float[] jimen;
    private float[] hikou;
    private float[] esupa;
    private float[] musi;
    private float[] iwa;
    private float[] gosuto;
    private float[] doragon;
    private float[] aku;
    private float[] hagane;
    private float[] feari;

    private float[][] mType;

    public TypeEffect(){

        nomaru = new float[]{1,1,1,1,1,1,2,1,1,1,1,1,1,0,1,1,1,1};
        honoo = new float[]{1,0.5f,2,1,0.5f,0.5f,1,1,2,1,1,0.5f,2,1,1,1,0.5f,0.5f};
        mizu = new float[]{1,0.5f,0.5f,2,2,0.5f,1,1,1,1,1,1,1,1,1,1,0.5f,1};
        denki = new float[]{1,1,1,0.5f,1,1,1,1,2,0.5f,1,1,1,1,1,1,0.5f,1};
        kusa = new float[]{1,2,0.5f,0.5f,0.5f,2,1,2,0.5f,2,1,2,1,1,1,1,1,1};
        koori = new float[]{1,2,1,1,1,0.5f,2,1,1,1,1,1,2,1,1,1,2,1};
        kakutou = new float[]{1,1,1,1,1,1,1,1,1,2,2,0.5f,0.5f,1,1,0.5f,1,2};
        doku = new float[]{1,1,1,1,0.5f,1,0.5f,0.5f,2,1,2,0.5f,1,1,1,1,1,0.5f};
        jimen = new float[]{1,1,2,0,2,2,1,0.5f,1,1,1,1,0.5f,1,1,1,1,1};
        hikou = new float[]{1,1,1,2,0.5f,2,0.5f,1,0,1,1,0.5f,2,1,1,1,1,1};
        esupa = new float[]{1,1,1,1,1,1,0.5f,1,1,1,0.5f,2,1,2,1,2,1,1};
        musi = new float[]{1,2,1,1,0.5f,1,0.5f,1,0.5f,2,1,1,2,1,1,1,1,1};
        iwa = new float[]{0.5f,0.5f,2,1,2,1,2,0.5f,2,0.5f,1,1,1,1,1,1,2,1};
        gosuto = new float[]{0,1,1,1,1,1,0,0.5f,1,1,1,0.5f,1,2,1,2,1,1};
        doragon = new float[]{1,0.5f,0.5f,0.5f,0.5f,2,1,1,1,1,1,1,1,1,2,1,1,2};
        aku = new float[]{1,1,1,1,1,1,2,1,1,1,0,2,1,0.5f,1,0.5f,1,2};
        hagane = new float[]{0.5f,2,1,1,0.5f,0.5f,2,0,2,0.5f,0.5f,0.5f,0.5f,1,0.5f,1,0.5f,0.5f};
        feari = new float[]{1,1,1,1,1,1,0.5f,2,1,1,1,0.5f,1,1,0,0.5f,2,1};

        mType = new float[][]{nomaru,honoo,mizu,denki,kusa,koori,kakutou,doku,jimen,hikou,esupa,musi,iwa,gosuto,doragon,aku,hagane,feari};
    }

    public int getImageResoceToNum(int num){
        int result;
        switch (num){
            case 0:
                result= R.drawable.type_nomaru;
                break;
            case 1:
                result= R.drawable.type_honoo;
                break;
            case 2:
                result= R.drawable.type_mizu;
                break;
            case 3:
                result= R.drawable.type_denki;
                break;
            case 4:
                result= R.drawable.type_kusa;
                break;
            case 5:
                result= R.drawable.type_koori;
                break;
            case 6:
                result= R.drawable.type_kakutou;
                break;
            case 7:
                result= R.drawable.type_doku;
                break;
            case 8:
                result= R.drawable.type_jimen;
                break;
            case 9:
                result= R.drawable.type_hikou;
                break;
            case 10:
                result= R.drawable.type_esupa;
                break;
            case 11:
                result= R.drawable.type_musi;
                break;
            case 12:
                result= R.drawable.type_iwa;
                break;
            case 13:
                result= R.drawable.type_gosuto;
                break;
            case 14:
                result= R.drawable.type_doragon;
                break;
            case 15:
                result= R.drawable.type_aku;
                break;
            case 16:
                result= R.drawable.type_hagane;
                break;
            case 17:
                result= R.drawable.type_feari;
                break;
            default:
                result=0;
                break;
        }
        return result;
    }

    public float[] getDamageResult(int type1){
        return mType[type1];
    }
    public float[] getDamageResult(int type1,int type2){
        float[] t1 = mType[type1];
        float[] t2 = mType[type2];
        float[] result = new float[18];
        for (int i=0;i<18;i++){
            result[i]=t1[i]*t2[i];
        }
        return result;
    }

}