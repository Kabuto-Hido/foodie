package hcmute.edu.vn.nhom20.foodie;

import android.content.Context;
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

public class FoodOrDrinkAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Product> lstProduct;

    public FoodOrDrinkAdapter(Context context, int layout, List<Product> lstProduct) {
        this.context = context;
        this.layout = layout;
        this.lstProduct = lstProduct;
    }

    @Override
    public int getCount() {
        return lstProduct.size();
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
        TextView textviewFoodOrDrinkName,textviewFoodOrDrinkPrice;
        ImageView imageFoodOrDrink;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            holder.imageFoodOrDrink = (ImageView) view.findViewById(R.id.imageFoodOrDrink);
            holder.textviewFoodOrDrinkName = (TextView) view.findViewById(R.id.textviewFoodOrDrinkName);
            holder.textviewFoodOrDrinkPrice = (TextView) view.findViewById(R.id.textviewFoodOrDrinkPrice);

            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        final Product product = lstProduct.get(i);
        holder.textviewFoodOrDrinkName.setText(product.getName());
        String price = Float.toString(product.getPrice());
        holder.textviewFoodOrDrinkPrice.setText(price +"đ");

        //byte -> bitmap
        byte[] picture = product.getImage();
        if(picture.length < 0){
            holder.imageFoodOrDrink.setImageResource(R.drawable.icon_image_not_found);
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0,picture.length);
        holder.imageFoodOrDrink.setImageBitmap(bitmap);

        return view;
    }

}
