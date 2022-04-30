package hcmute.edu.vn.nhom20.foodie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FoodManageAdapter extends BaseAdapter {
    private FoodManageActivity context;
    private int layout;
    private List<Product> listFood;

    public FoodManageAdapter(FoodManageActivity context, int layout, List<Product> listFood) {
        this.context = context;
        this.layout = layout;
        this.listFood = listFood;
    }

    @Override
    public int getCount() {
        return listFood.size();
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
        TextView textviewNameFoodManage,textviewPriceFoodManage;
        ImageView btnRemoveFood;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            holder.btnRemoveFood = (ImageView) view.findViewById(R.id.btnRemoveFood);
            holder.textviewNameFoodManage = (TextView) view.findViewById(R.id.textviewNameFoodManage);
            holder.textviewPriceFoodManage = (TextView) view.findViewById(R.id.textviewPriceFoodManage);

            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        final Product food = listFood.get(i);
        holder.textviewNameFoodManage.setText(food.getName());
        String price = Float.toString(food.getPrice());
        holder.textviewPriceFoodManage.setText(price+"đ");

        holder.btnRemoveFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogFoodDelete(food.getId(),food.getName());
            }
        });

        return view;
    }
}
