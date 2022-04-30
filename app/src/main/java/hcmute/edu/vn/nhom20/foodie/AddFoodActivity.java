package hcmute.edu.vn.nhom20.foodie;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddFoodActivity extends AppCompatActivity {
    ImageView btnUploadImageFood, btnBackAddFoodPage;
    EditText editTextFoodName, editTextFoodPrice, editTextFoodQuantity;
    Button btnConfirmAddFood, btnCancelAddFood;
    int REQUEST_CODE = 113;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        btnUploadImageFood = (ImageView) findViewById(R.id.btnUploadImageFood);
        btnBackAddFoodPage = (ImageView) findViewById(R.id.btnBackAddFoodPage);
        editTextFoodName = (EditText) findViewById(R.id.editTextFoodName);
        editTextFoodPrice = (EditText) findViewById(R.id.editTextFoodPrice);
        editTextFoodQuantity = (EditText) findViewById(R.id.editTextFoodQuantity);
        btnConfirmAddFood = (Button) findViewById(R.id.btnConfirmAddFood);
        btnCancelAddFood = (Button) findViewById(R.id.btnCancelAddFood);

        btnBackAddFoodPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddFoodActivity.this, FoodManageActivity.class));
                finish();
            }
        });

        btnUploadImageFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        AddFoodActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE
                );
            }
        });

        btnConfirmAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent getShopAddFood = getIntent();
                String shopNameAddFood = getShopAddFood.getStringExtra("shopNameAddFood");

                BitmapDrawable bitmapDrawable = (BitmapDrawable) btnUploadImageFood.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                byte[] img;
                if(bitmap != null) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    img = byteArrayOutputStream.toByteArray();

                }
                else {
                    img = null;
                }

                Float foodPrice = Float.parseFloat(editTextFoodPrice.getText().toString().trim());
                int foodQuantity = Integer.parseInt(editTextFoodQuantity.getText().toString().trim());

                MainActivity.db.InsertProduct(editTextFoodName.getText().toString().trim(),
                        null,foodPrice,foodQuantity, img, "Food",shopNameAddFood);

                Toast.makeText(AddFoodActivity.this, "Succeed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddFoodActivity.this, FoodManageActivity.class));
                finish();
            }
        });

        btnCancelAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddFoodActivity.this, FoodManageActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                getResult.launch(intent);
            }
            else {
                Toast.makeText(this, "You don't have permission to access the folder", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    ActivityResultLauncher<Intent> getResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK && result.getData()!=null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    btnUploadImageFood.setScaleType(ImageView.ScaleType.FIT_XY);
                    btnUploadImageFood.setImageBitmap(bitmap);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    });
}