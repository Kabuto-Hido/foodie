package hcmute.edu.vn.nhom20.foodie.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hcmute.edu.vn.nhom20.foodie.CartActivity;
import hcmute.edu.vn.nhom20.foodie.MainActivity;
import hcmute.edu.vn.nhom20.foodie.R;
import hcmute.edu.vn.nhom20.foodie.model.Cart;
import hcmute.edu.vn.nhom20.foodie.model.Product;

public class CartAdapter extends BaseAdapter {
    private CartActivity context;
    private int layout;
    private List<Cart> lstCart;

    public CartAdapter(CartActivity context, int layout, List<Cart> lstCart) {
        this.context = context;
        this.layout = layout;
        this.lstCart = lstCart;
    }

    @Override
    public int getCount() {
        return lstCart.size();
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
        TextView textviewNameProductCart, textviewProductCartTotalPrice, textviewProductCartQuantity;
        ImageView imageProductCart,btnRemoveProductInCart;
        Button btnDecreaseQuantityCart, btnIncreaseQuantityCart;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            holder.textviewNameProductCart = (TextView) view.findViewById(R.id.textviewNameProductCart);
            holder.textviewProductCartQuantity = (TextView) view.findViewById(R.id.textviewProductCartQuantity);
            holder.textviewProductCartTotalPrice = (TextView) view.findViewById(R.id.textviewProductCartTotalPrice);
            holder.imageProductCart = (ImageView) view.findViewById(R.id.imageProductCart);
            holder.btnRemoveProductInCart = (ImageView) view.findViewById(R.id.btnRemoveProductInCart);
            holder.btnDecreaseQuantityCart = (Button) view.findViewById(R.id.btnDecreaseQuantityCart);
            holder.btnIncreaseQuantityCart = (Button) view.findViewById(R.id.btnIncreaseQuantityCart);

            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        final Cart cart = lstCart.get(i);

        Product productInCart = MainActivity.db.getAllProductData(cart.getIdProduct());
        holder.textviewNameProductCart.setText(productInCart.getName());

        byte[] picture = productInCart.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0,picture.length);
        holder.imageProductCart.setImageBitmap(bitmap);

        int quantityInCart = cart.getQuantity();
        holder.textviewProductCartQuantity.setText(Integer.toString(quantityInCart));
        float priceProduct = productInCart.getPrice();
        float totalPriceProductInCart = (float) priceProduct * quantityInCart;
        holder.textviewProductCartTotalPrice.setText(Float.toString(totalPriceProductInCart) +" đ");

        holder.btnRemoveProductInCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogCartDelete(cart.getId(),productInCart.getName());
            }
        });

        holder.btnDecreaseQuantityCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentQuantity = holder.textviewProductCartQuantity.getText().toString().trim();
                int quantity = Integer.parseInt(currentQuantity);
                quantity --;
                if(quantity < 1){
                    quantity = 1;
                }
                context.updateCart(cart.getId(),quantity);
            }
        });

        holder.btnIncreaseQuantityCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentQuantity = holder.textviewProductCartQuantity.getText().toString().trim();
                int quantity = Integer.parseInt(currentQuantity);
                quantity ++;
                int quantityInStock = productInCart.getQuantity();
                if(quantity > quantityInStock){
                    quantity = quantityInStock;
                }
                context.updateCart(cart.getId(),quantity);
            }
        });

        return view;
    }
}
