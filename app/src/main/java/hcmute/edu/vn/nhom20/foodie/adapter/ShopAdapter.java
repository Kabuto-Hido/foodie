package hcmute.edu.vn.nhom20.foodie.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hcmute.edu.vn.nhom20.foodie.HomeActivity;
import hcmute.edu.vn.nhom20.foodie.MainActivity;
import hcmute.edu.vn.nhom20.foodie.R;
import hcmute.edu.vn.nhom20.foodie.model.Shop;

public class ShopAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Shop> lstShop;

    public ShopAdapter(Context context, int layout, List<Shop> lstShop) {
        this.context = context;
        this.layout = layout;
        this.lstShop = lstShop;
    }

    @Override
    public int getCount() {
        return lstShop.size();
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
        TextView textViewShopName;
        ImageView shopImage, btnFavorite;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            holder.shopImage = (ImageView) view.findViewById(R.id.shopImage2);
            holder.textViewShopName = (TextView) view.findViewById(R.id.textViewShopName);
            holder.btnFavorite = (ImageView) view.findViewById(R.id.btnFavorite);

            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        final Shop shop = lstShop.get(i);
        holder.textViewShopName.setText(shop.getName());
        //byte -> bitmap
        byte[] picture = shop.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0,picture.length);
        holder.shopImage.setImageBitmap(bitmap);

        SharedPreferences sharedPreferences = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("userLogin","");

        Cursor checkShop = MainActivity.db.GetData("SELECT * FROM FavoriteList WHERE UserName = '"+username
                +"' AND ShopName = '"+shop.getName()+"'");

        if(checkShop.moveToFirst()){       //have in favoriteList
            holder.btnFavorite.setImageResource(R.drawable.icon_active_heart);
            holder.btnFavorite.setTag(R.drawable.icon_active_heart);
        }
        else{
            holder.btnFavorite.setImageResource(R.drawable.icon_not_favorite);
            holder.btnFavorite.setTag(R.drawable.icon_not_favorite);
        }

        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( (int)holder.btnFavorite.getTag() == R.drawable.icon_not_favorite){       //don't have in favoriteList
                    MainActivity.db.InsertFavoriteList(username,shop.getName());
                    holder.btnFavorite.setImageResource(R.drawable.icon_active_heart);
                    holder.btnFavorite.setTag(R.drawable.icon_active_heart);
                }
                else{
                    MainActivity.db.QueryData("DELETE FROM FavoriteList WHERE UserName = '"+username +
                            "' AND ShopName = '"+shop.getName()+"'");
                    holder.btnFavorite.setImageResource(R.drawable.icon_not_favorite);
                    holder.btnFavorite.setTag(R.drawable.icon_not_favorite);
                }
            }
        });

        return view;
    }


}
