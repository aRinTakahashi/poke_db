package site.rinax.poke_db;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class PokeListRecyclerViewAdapter extends RecyclerView.Adapter<PokeListRecyclerViewAdapter.PokeListRecyclerViewHolder> implements FastScrollRecyclerView.SectionedAdapter {

    private ArrayList<String> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private int lastPosition = -1;
    private String mPokeId = "";
    public PokeListRecyclerViewAdapter(@NonNull Context context,ArrayList list) {
//        super(context);
        mList = list;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public PokeListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View inflate = mInflater.inflate(R.layout.poke_list_layout, parent,false);
        PokeListRecyclerViewHolder vh = new PokeListRecyclerViewHolder(mInflater.inflate(R.layout.poke_list_layout, parent,false));

        return vh;
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return position+"";
    }

    @Override
    public void onBindViewHolder(@NonNull final PokeListRecyclerViewHolder holder, final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                class DoThreadSettingTypeResorces implements Runnable{
                    private final int n;
                    private final TypeEffect t;
                    private final ImageView i;
                    DoThreadSettingTypeResorces(int _c,TypeEffect _t,ImageView _i){
                        n=_c;
                        t=_t;
                        i=_i;
                    }
                    @Override
                    public void run(){
                        Picasso.get().load(t.getImageResoceToNum(n)).into(i);
//                        i.setImageResource(t.getImageResoceToNum(n));
                    }
                }

                holder.tvName.post(new Runnable() {
                    @Override
                    public void run() {
                        holder.tvName.setText(mList.get(position));
                    }
                });



                DatabaseHelper helper = new DatabaseHelper(mContext);
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor c = null;
                try {
                    String sqlstr1 = "select type from type inner join master on type.num = master._id where name = '" + mList.get(position)+"'";
                    c = db.rawQuery(sqlstr1, null);
                    boolean mov = c.moveToFirst();
                    TypeEffect t = new TypeEffect();

                    int i=0;
                    while (mov) {

                        if(i==0) {
                            holder.ivType2.post(new Runnable() {
                                @Override
                                public void run() {
                                    holder.ivType2.setVisibility(View.GONE);
                                }
                            });
                            holder.ivType1.post(
                                  new DoThreadSettingTypeResorces(c.getInt(0),t,holder.ivType1)
                            );

                        }else if(i==1){
                            holder.ivType2.post(new Runnable() {
                                @Override
                                public void run() {
                                    holder.ivType2.setVisibility(View.VISIBLE);
                                }
                            });
                            holder.ivType2.post(
                                    new DoThreadSettingTypeResorces(c.getInt(0),t,holder.ivType2)

                            );
//                            holder.ivType2.setVisibility(View.VISIBLE);
//                            holder.ivType2.setImageResource(t.getImageResoceToNum(c.getInt(0)));
                        }
                        mov = c.moveToNext();
                        i++;
                    }

//-------------------------------




                    class DoThread2 implements Runnable{
                        private final String s;

                        DoThread2(String _s){
                            s=_s;

                        }
                        @Override
                        public void run(){
                            holder.tvNo.setText(s);
                        }
                    }

                    class DoThread3 implements Runnable{
                        private final String s;

                        DoThread3(String _s){
                            s=_s;

                        }
                        @Override
                        public void run(){
                            Picasso.get().load(mContext.getResources().getIdentifier(s,"drawable",mContext.getPackageName())).into(holder.ivPoke);
//                            holder.ivPoke.setImageResource(mContext.getResources().getIdentifier(s,"drawable",mContext.getPackageName()));
                        }
                    }



                    sqlstr1 = "select num from master where name = '" + mList.get(position)+"'";
                    c = db.rawQuery(sqlstr1, null);
                    c.moveToFirst();
                    String s = String.valueOf(c.getInt(0));
                    if(s.length()==1){
                        s="00"+s;
                    }else if(s.length()==2){
                        s="0"+s;
                    }

                    mPokeId = s;
                    holder.tvNo.post(new DoThread2(s));











                    sqlstr1 = "select file_name from img inner join master on img._id = master._id where name = '" + mList.get(position)+"'";
                    c = db.rawQuery(sqlstr1, null);
                    c.moveToFirst();
                    s = c.getString(0);

                    holder.ivPoke.post(new DoThread3(s));





                } finally {
                    db.close();
                    if (c != null) c.close();
                }


            }
        }).start();
//        holder.tvName.setText(mList.get(position));

        if(!(mContext instanceof PokeDetailActivity)){
            holder.wrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PokeDetailActivity.class);
                    intent.putExtra("POKE_NAME", mList.get(position));
                    mContext.startActivity(intent);
                }
            });
        }

        setAnimation(holder.itemView,position);


//
//        DatabaseHelper helper = new DatabaseHelper(mContext);
//        SQLiteDatabase db = helper.getWritableDatabase();
//        Cursor c = null;
//        try {
//            String sqlstr1 = "select type from type inner join master on type.num = master._id where name = '" + mList.get(position)+"'";
//            c = db.rawQuery(sqlstr1, null);
//            boolean mov = c.moveToFirst();
//            TypeEffect t = new TypeEffect();
//            int i=0;
//            while (mov) {
//                if(i==0) {
//                    holder.ivType2.setVisibility(View.GONE);
//                    holder.ivType1.setImageResource(t.getImageResoceToNum(c.getInt(0)));
//                }else if(i==1){
//                    holder.ivType2.setVisibility(View.VISIBLE);
//                    holder.ivType2.setImageResource(t.getImageResoceToNum(c.getInt(0)));
//                }
//                mov = c.moveToNext();
//                i++;
//            }
//
////-------------------------------
//
//
//
//            sqlstr1 = "select num from master where name = '" + mList.get(position)+"'";
//            c = db.rawQuery(sqlstr1, null);
//            c.moveToFirst();
//            String s = String.valueOf(c.getInt(0));
//            if(s.length()==1){
//                s="00"+s;
//            }else if(s.length()==2){
//                s="0"+s;
//            }
//
//            holder.tvNo.setText(s);
//
//
//
//            sqlstr1 = "select file_name from img inner join master on img._id = master._id where name = '" + mList.get(position)+"'";
//            c = db.rawQuery(sqlstr1, null);
//            c.moveToFirst();
//            s = c.getString(0);
//
//            holder.ivPoke.setImageResource(mContext.getResources().getIdentifier(s,"drawable",mContext.getPackageName()));
//
//
//
//
//
//        } finally {
//            db.close();
//            if (c != null) c.close();
//        }
    }




    //    public ArrayList SQLSelect() {
//        ArrayList mList = new ArrayList();
//        DatabaseHelper helper = new DatabaseHelper(mContext);
//        SQLiteDatabase db = helper.getWritableDatabase();
//        Cursor c = null;
//        try {
//            String sqlstr1 = "select " + mList.get(position) + " from type";
//            c = db.rawQuery(sqlstr1, null);
//            boolean mov = c.moveToFirst();
//            while (mov) {
//                mList.add(c.getString(0));
//                mov = c.moveToNext();
//            }
//        } finally {
//            db.close();
//            if (c != null) c.close();
//        }
//        return mList;
//    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
    public void setList(ArrayList list){
        mList=list;
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated

        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
        if (position < lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    class PokeListRecyclerViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivPoke;
        public TextView tvNo;
        public TextView tvName;
        public ImageView ivType1;
        public ImageView ivType2;
        public LinearLayout wrapper;

        public PokeListRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoke = itemView.findViewById(R.id.poke_image);
            tvNo = itemView.findViewById(R.id.poke_no);
            tvName = itemView.findViewById(R.id.poke_name);
            ivType1 = itemView.findViewById(R.id.poke_type1);
            ivType2 = itemView.findViewById(R.id.poke_type2);
            wrapper = itemView.findViewById(R.id.poke_layout_wrapper);

        }

    }


}
