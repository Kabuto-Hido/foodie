package hcmute.edu.vn.nhom20.foodie.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hcmute.edu.vn.nhom20.foodie.AdminHomeActivity;
import hcmute.edu.vn.nhom20.foodie.MainActivity;
import hcmute.edu.vn.nhom20.foodie.R;
import hcmute.edu.vn.nhom20.foodie.model.Shop;

public class AdminShopAdapter extends BaseAdapter {
    private AdminHomeActivity context;
    private int layout;
    private List<Shop> lstShop;

    public AdminShopAdapter(AdminHomeActivity context, int layout, List<Shop> lstShop) {
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
        TextView textviewAdminShopName, textviewAdminQuantityDish;
        ImageView btnRemoveShop,btnEditShop;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            holder.textviewAdminQuantityDish = (TextView) view.findViewById(R.id.textviewAdminQuantityDish);
            holder.textviewAdminShopName = (TextView) view.findViewById(R.id.textviewAdminShopName);
            holder.btnRemoveShop = (ImageView) view.findViewById(R.id.btnRemoveShop);
            holder.btnEditShop = (ImageView) view.findViewById(R.id.btnEditShop);

            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        final Shop shop = lstShop.get(i);
        holder.textviewAdminShopName.setText(shop.getName());
        //get quantity dish
        Cursor cursor = MainActivity.db.GetData("SELECT * FROM Product WHERE ShopName = '"+shop.getName()+"'");

        if(!cursor.moveToFirst()){ //empty
            holder.textviewAdminQuantityDish.setText("0 Dish");
        }
        else{
            String amount = Integer.toString(cursor.getCount());
            holder.textviewAdminQuantityDish.setText(amount+" Dish");
        }

        holder.btnRemoveShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogDelete(shop.getName());
            }
        });
        holder.btnEditShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.EditShop(shop.getName());
            }
        });

        return view;
    }

}
