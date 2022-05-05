package hcmute.edu.vn.nhom20.foodie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hcmute.edu.vn.nhom20.foodie.DrinkManageActivity;
import hcmute.edu.vn.nhom20.foodie.R;
import hcmute.edu.vn.nhom20.foodie.model.Product;

public class DrinkManageAdapter extends BaseAdapter {
    private DrinkManageActivity context;
    private int layout;
    private List<Product> listDrink;

    public DrinkManageAdapter(DrinkManageActivity context, int layout, List<Product> listDrink) {
        this.context = context;
        this.layout = layout;
        this.listDrink = listDrink;
    }

    @Override
    public int getCount() {
        return listDrink.size();
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
        TextView textviewNameDrinkManage,textviewPriceDrinkManage;
        ImageView btnRemoveDrink;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            holder.btnRemoveDrink = (ImageView) view.findViewById(R.id.btnRemoveDrink);
            holder.textviewNameDrinkManage = (TextView) view.findViewById(R.id.textviewNameDrinkManage);
            holder.textviewPriceDrinkManage = (TextView) view.findViewById(R.id.textviewPriceDrinkManage);

            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        final Product drink = listDrink.get(i);
        holder.textviewNameDrinkManage.setText(drink.getName());
        String price = Float.toString(drink.getPrice());
        holder.textviewPriceDrinkManage.setText(price+"đ");

        holder.btnRemoveDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogDrinkDelete(drink.getId(),drink.getName());
            }
        });

        return view;
    }
}
