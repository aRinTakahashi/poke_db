package site.rinax.poke_db;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;

public class SpinnerImageAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int[] names;

    static class ViewHolder {
        ImageView imageView;

    }

    SpinnerImageAdapter(Context context){
        inflater = LayoutInflater.from(context);
        names = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17};
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.spinner_image_layout,parent,false);
        TypeEffect t = new TypeEffect();
        ViewHolder holder = new ViewHolder();
        holder.imageView = convertView.findViewById(R.id.sppiner_image);
        holder.imageView.setImageResource(t.getImageResoceToNum(position));

        return convertView;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
