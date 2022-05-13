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
import android.content.SharedPreferences;
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

import hcmute.edu.vn.nhom20.foodie.model.Product;

public class EditFoodActivity extends AppCompatActivity {
    ImageView btnUploadImageEditFood, btnBackEditFoodPage;
    EditText editTextEditFoodName, editTextEditFoodPrice, editTextEditFoodQuantity;
    Button btnConfirmEditFood, btnCancelEditFood;
    int REQUEST_CODE = 114;

    int idFood = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);

        btnUploadImageEditFood = (ImageView) findViewById(R.id.btnUploadImageEditFood);
        btnBackEditFoodPage = (ImageView) findViewById(R.id.btnBackEditFoodPage);
        editTextEditFoodName = (EditText) findViewById(R.id.editTextEditFoodName);
        editTextEditFoodPrice = (EditText) findViewById(R.id.editTextEditFoodPrice);
        editTextEditFoodQuantity = (EditText) findViewById(R.id.editTextEditFoodQuantity);
        btnConfirmEditFood = (Button) findViewById(R.id.btnConfirmEditFood);
        btnCancelEditFood = (Button)  findViewById(R.id.btnCancelEditFood);

        SharedPreferences sharedPreferencesEditFood = getSharedPreferences("dataFood", MODE_PRIVATE);
        idFood = sharedPreferencesEditFood.getInt("foodId",0);

        Product pro = MainActivity.db.getAllProductData(idFood);
        editTextEditFoodName.setText(pro.getName());
        editTextEditFoodPrice.setText(Float.toString(pro.getPrice()));
        editTextEditFoodQuantity.setText(Integer.toString(pro.getQuantity()));

        byte[] picture = pro.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0,picture.length);
        btnUploadImageEditFood.setScaleType(ImageView.ScaleType.FIT_XY);
        btnUploadImageEditFood.setImageBitmap(bitmap);

        btnBackEditFoodPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditFoodActivity.this,FoodManageActivity.class));
                finish();
            }
        });

        btnUploadImageEditFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        EditFoodActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE
                );
            }
        });

        btnConfirmEditFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) btnUploadImageEditFood.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] img = byteArrayOutputStream.toByteArray();

                String name = editTextEditFoodName.getText().toString().trim();
                Float newPrice = Float.parseFloat(editTextEditFoodPrice.getText().toString().trim());
                int newQuantity = Integer.parseInt(editTextEditFoodQuantity.getText().toString().trim());

                if(name.equals("") || editTextEditFoodPrice.getText().toString().trim().equals("") ||
                        editTextEditFoodQuantity.getText().toString().trim().equals("")){
                    Toast.makeText(EditFoodActivity.this, "Please fill all fields!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    MainActivity.db.UpdateProduct(idFood,name,newPrice,newQuantity,img);

                    Toast.makeText(EditFoodActivity.this, "Succeed", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditFoodActivity.this, FoodManageActivity.class));
                    finish();
                }
            }

        });

        btnCancelEditFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditFoodActivity.this,FoodManageActivity.class));
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
                    btnUploadImageEditFood.setScaleType(ImageView.ScaleType.FIT_XY);
                    btnUploadImageEditFood.setImageBitmap(bitmap);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    });
}