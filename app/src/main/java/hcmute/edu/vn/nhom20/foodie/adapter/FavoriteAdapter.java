package hcmute.edu.vn.nhom20.foodie.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hcmute.edu.vn.nhom20.foodie.FavoriteListActivity;
import hcmute.edu.vn.nhom20.foodie.MainActivity;
import hcmute.edu.vn.nhom20.foodie.R;
import hcmute.edu.vn.nhom20.foodie.model.FavoriteList;
import hcmute.edu.vn.nhom20.foodie.model.Shop;

public class FavoriteAdapter extends BaseAdapter {
    private FavoriteListActivity context;
    private int layout;
    private List<FavoriteList> lstFavorite;

    public FavoriteAdapter(FavoriteListActivity context, int layout, List<FavoriteList> lstFavorite) {
        this.context = context;
        this.layout = layout;
        this.lstFavorite = lstFavorite;
    }

    @Override
    public int getCount() {
        return lstFavorite.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView textviewNameProductFavorite;
        ImageView imageProductFavorite;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            holder.textviewNameProductFavorite = (TextView) view.findViewById(R.id.textviewNameProductFavorite);
            holder.imageProductFavorite = (ImageView) view.findViewById(R.id.imageProductFavorite);

            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        final FavoriteList Favorite = lstFavorite.get(i);

        Shop dataShop = MainActivity.db.getAllShopData(Favorite.getShopName());

        holder.textviewNameProductFavorite.setText(dataShop.getName());
        //byte -> bitmap
        byte[] picture = dataShop.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0,picture.length);
        holder.imageProductFavorite.setImageBitmap(bitmap);

        return view;
    }
}
