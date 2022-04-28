package hcmute.edu.vn.nhom20.foodie;

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

        return view;
    }


}
