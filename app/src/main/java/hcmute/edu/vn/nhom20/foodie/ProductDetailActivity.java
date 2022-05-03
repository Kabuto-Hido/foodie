package hcmute.edu.vn.nhom20.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetailActivity extends AppCompatActivity {
    ImageView imageDetailProduct, imageBackPageProductDetail, icon_active_home_productDetailPage,
            icon_inactive_list_love_productDetailPage, icon_inactive_cart_productDetailPage,
            icon_inactive_more_productDetailPage;
    TextView textviewNameProduct, textviewAmountProduct, textviewProductDescription,
            textviewProductPrice, textviewQuantityProduct, textviewProductTotalPrice;
    Button btnDecreaseQuantity, btnIncreaseQuantity, btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imageDetailProduct = (ImageView) findViewById(R.id.imageDetailProduct);
        imageBackPageProductDetail = (ImageView) findViewById(R.id.imageBackPageProductDetail);
        icon_active_home_productDetailPage = (ImageView) findViewById(R.id.icon_active_home_productDetailPage);
        icon_inactive_list_love_productDetailPage = (ImageView) findViewById(R.id.icon_inactive_list_love_productDetailPage);
        icon_inactive_cart_productDetailPage = (ImageView) findViewById(R.id.icon_inactive_cart_productDetailPage);
        icon_inactive_more_productDetailPage = (ImageView) findViewById(R.id.icon_inactive_more_productDetailPage);

        textviewNameProduct = (TextView) findViewById(R.id.textviewNameProduct);
        textviewAmountProduct = (TextView) findViewById(R.id.textviewAmountProduct);
        textviewProductDescription = (TextView) findViewById(R.id.textviewProductDescription);
        textviewProductPrice = (TextView) findViewById(R.id.textviewProductPrice);
        textviewQuantityProduct = (TextView) findViewById(R.id.textviewQuantityProduct);
        textviewProductTotalPrice = (TextView) findViewById(R.id.textviewProductTotalPrice);

        btnDecreaseQuantity = (Button) findViewById(R.id.btnDecreaseQuantity);
        btnIncreaseQuantity = (Button) findViewById(R.id.btnIncreaseQuantity);
        btnAddToCart = (Button) findViewById(R.id.btnAddToCart);

        Intent getIdProduct = getIntent();
        int id = getIdProduct.getIntExtra("idFoodOrDrink",0);

        Product productDetail = MainActivity.db.getAllProductData(id);
        textviewNameProduct.setText(productDetail.getName());
        textviewAmountProduct.setText(Integer.toString(productDetail.getQuantity()) +" in stocks");
        if(productDetail.getDescription().equals(null)){
            textviewProductDescription.setText("");
        }
        else{
            textviewProductDescription.setText(productDetail.getDescription());
        }
        textviewProductPrice.setText(Float.toString(productDetail.getPrice()) +" đ");

        byte[] picture = productDetail.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0,picture.length);
        imageDetailProduct.setImageBitmap(bitmap);

        imageBackPageProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToShopIntent = new Intent(ProductDetailActivity.this, ShopActivity.class);
                backToShopIntent.putExtra("shopName",productDetail.getShopName());
                startActivity(backToShopIntent);
                finish();
            }
        });

        icon_active_home_productDetailPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetailActivity.this, HomeActivity.class));
                finish();
            }
        });

        icon_inactive_list_love_productDetailPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetailActivity.this, FavoriteListActivity.class));
                finish();
            }
        });

        icon_inactive_cart_productDetailPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetailActivity.this, CartActivity.class));
                finish();
            }
        });

        icon_inactive_more_productDetailPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetailActivity.this, MorePageActivity.class));
                finish();
            }
        });

        btnDecreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentQuantity = textviewQuantityProduct.getText().toString().trim();
                int quantity = Integer.parseInt(currentQuantity);
                quantity --;
                if(quantity < 1){
                    quantity = 1;
                }

                float price = productDetail.getPrice();
                float totalPrice = (float) price * quantity;

                textviewQuantityProduct.setText(Integer.toString(quantity));
                textviewProductTotalPrice.setText(Float.toString(totalPrice) + " đ");

            }
        });

        btnIncreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentQuantity = textviewQuantityProduct.getText().toString().trim();
                int quantity = Integer.parseInt(currentQuantity);
                quantity++;
                int quantityInStock = productDetail.getQuantity();
                if(quantity > quantityInStock){
                    quantity = quantityInStock;
                }

                float price = productDetail.getPrice();
                float totalPrice = (float) price * quantity;

                textviewQuantityProduct.setText(Integer.toString(quantity));
                textviewProductTotalPrice.setText(Float.toString(totalPrice) + " đ");
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
                String username = sharedPreferences.getString("userLogin","");

                Cursor checkProduct = MainActivity.db.GetData("SELECT * FROM Cart WHERE UserName = '"+ username +
                        "' AND ProductId = "+ id +"");

                if(!checkProduct.moveToFirst()){
                    MainActivity.db.InsertCart(username,id,Integer.parseInt(textviewQuantityProduct.getText().toString().trim()));
                }
                else{
                    int currentQuantity = Integer.parseInt(textviewQuantityProduct.getText().toString().trim());
                    int newQuantity = currentQuantity + checkProduct.getInt(3);
                    MainActivity.db.QueryData("Update Cart SET Quantity = "+newQuantity+" WHERE UserName = '"+username+
                            "' AND ProductId = "+ id +"");
                }

                startActivity(new Intent(ProductDetailActivity.this,CartActivity.class));
                finish();
            }
        });

    }
}