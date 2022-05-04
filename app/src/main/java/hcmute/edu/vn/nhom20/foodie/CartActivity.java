package hcmute.edu.vn.nhom20.foodie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    ImageView btnBackPageCart, icon_inactive_home_CartPage, icon_inactive_list_love_CartPage,
            icon_active_cart_CartPage, icon_inactive_more_CartPage;
    ListView listviewProductInCart;
    Button btnProcessPageCart;

    ArrayList<Cart> cartArrayList;
    CartAdapter adapter;

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        btnBackPageCart = (ImageView) findViewById(R.id.btnBackPageCart);
        icon_inactive_home_CartPage = (ImageView) findViewById(R.id.icon_inactive_home_CartPage);
        icon_inactive_list_love_CartPage = (ImageView) findViewById(R.id.icon_inactive_list_love_CartPage);
        icon_active_cart_CartPage = (ImageView) findViewById(R.id.icon_active_cart_CartPage);
        icon_inactive_more_CartPage = (ImageView) findViewById(R.id.icon_inactive_more_CartPage);
        listviewProductInCart = (ListView) findViewById(R.id.listviewProductInCart);
        btnProcessPageCart = (Button) findViewById(R.id.btnProcessPageCart);

        SharedPreferences sharedPrefer = getSharedPreferences("dataLogin", MODE_PRIVATE);
        user = sharedPrefer.getString("userLogin","");

        cartArrayList = new ArrayList<>();
        adapter = new CartAdapter(this,R.layout.product_cart_row,cartArrayList);
        listviewProductInCart.setAdapter(adapter);

        getDataCart();

        btnBackPageCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToShopIntent = new Intent(CartActivity.this, HomeActivity.class);
                startActivity(backToShopIntent);
                finish();
            }
        });

        icon_inactive_home_CartPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, HomeActivity.class));
                finish();
            }
        });

        icon_inactive_list_love_CartPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, FavoriteListActivity.class));
                finish();
            }
        });

        icon_inactive_more_CartPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, MorePageActivity.class));
                finish();
            }
        });

        btnProcessPageCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogNotification = new AlertDialog.Builder(CartActivity.this);
                dialogNotification.setMessage("You will not be able to return to the shopping cart. Are you sure you want to continue?");
                dialogNotification.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //create order
                        MainActivity.db.InsertOrder(user);

                        //Get newest order
                        Cursor newestOrder = MainActivity.db.GetData("SELECT * FROM Orders WHERE UserName = '"+user+"'");

                        Float orderPrice = Float.valueOf(0);

                        if(newestOrder.moveToLast()){
                            int idOrder = newestOrder.getInt(0);

                            for (Cart cart : cartArrayList){
                                //calculator unitPrice each product
                                Product productInCart = MainActivity.db.getAllProductData(cart.getIdProduct());
                                float priceProduct = productInCart.getPrice();
                                float unitPriceProductInCart = (float) priceProduct * cart.getQuantity();
                                orderPrice += unitPriceProductInCart;

                                MainActivity.db.InsertOrderDetail(cart.getIdProduct(),idOrder,cart.getQuantity(),unitPriceProductInCart);
                            }

                            //MainActivity.db.QueryData("UPDATE Orders SET OrderPrice = "+orderPrice+" WHERE Id = "+idOrder+"");

                            Intent payOnDelivery = new Intent(CartActivity.this, PayOnDeliverActivity.class);
                            payOnDelivery.putExtra("orderId",idOrder);
                            payOnDelivery.putExtra("orderPrice",orderPrice);
                            startActivity(payOnDelivery);
                            finish();
                        }
                    }
                });

                dialogNotification.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialogNotification.show();

            }
        });

    }

    private void getDataCart(){
        Cursor CartData = MainActivity.db.GetData("SELECT * FROM Cart WHERE UserName = '"+user+"'");
        cartArrayList.clear();
        while (CartData.moveToNext()){
            cartArrayList.add(new Cart(
                    CartData.getInt(0),
                    CartData.getString(1),
                    CartData.getInt(2),
                    CartData.getInt(3)));
        }
        adapter.notifyDataSetChanged();
    }

    public void updateCart(int id,int quantity){
        MainActivity.db.QueryData("Update Cart SET Quantity = "+quantity+" WHERE Id = "+id+"");
        getDataCart();
    }

    public void DialogCartDelete(int id, String productName){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this);
        dialogDelete.setMessage("Do you want to delete "+productName+" ?");
        dialogDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.db.QueryData("DELETE FROM Cart WHERE Id = "+id+"");
                Toast.makeText(CartActivity.this,"Delete "+productName,Toast.LENGTH_SHORT).show();
                getDataCart();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogDelete.show();
    }
}