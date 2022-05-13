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

public class EditDrinkActivity extends AppCompatActivity {
    ImageView btnUploadImageEditDrink, btnBackEditDrinkPage;
    EditText editTextEditDrinkName, editTextEditDrinkPrice, editTextEditDrinkQuantity;
    Button btnConfirmEditDrink, btnCancelEditDrink;
    int REQUEST_CODE = 223;

    int idDrink = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_drink);

        btnUploadImageEditDrink = (ImageView) findViewById(R.id.btnUploadImageEditDrink);
        btnBackEditDrinkPage = (ImageView) findViewById(R.id.btnBackEditDrinkPage);
        editTextEditDrinkName = (EditText) findViewById(R.id.editTextEditDrinkName);
        editTextEditDrinkPrice = (EditText) findViewById(R.id.editTextEditDrinkPrice);
        editTextEditDrinkQuantity = (EditText) findViewById(R.id.editTextEditDrinkQuantity);
        btnConfirmEditDrink = (Button) findViewById(R.id.btnConfirmEditDrink);
        btnCancelEditDrink = (Button) findViewById(R.id.btnCancelEditDrink);

        SharedPreferences sharedPreferencesEditDrink = getSharedPreferences("dataDrink", MODE_PRIVATE);
        idDrink = sharedPreferencesEditDrink.getInt("drinkId",0);

        Product pro = MainActivity.db.getAllProductData(idDrink);
        editTextEditDrinkName.setText(pro.getName());
        editTextEditDrinkPrice.setText(Float.toString(pro.getPrice()));
        editTextEditDrinkQuantity.setText(Integer.toString(pro.getQuantity()));

        byte[] picture = pro.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0,picture.length);
        btnUploadImageEditDrink.setImageBitmap(bitmap);

        btnBackEditDrinkPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditDrinkActivity.this,DrinkManageActivity.class));
                finish();
            }
        });

        btnUploadImageEditDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        EditDrinkActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE
                );
            }
        });

        btnConfirmEditDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) btnUploadImageEditDrink.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] img = byteArrayOutputStream.toByteArray();

                String name = editTextEditDrinkName.getText().toString().trim();
                Float newPrice = Float.parseFloat(editTextEditDrinkPrice.getText().toString().trim());
                int newQuantity = Integer.parseInt(editTextEditDrinkQuantity.getText().toString().trim());

                if(name.equals("") || editTextEditDrinkPrice.getText().toString().trim().equals("") ||
                        editTextEditDrinkQuantity.getText().toString().trim().equals("")){
                    Toast.makeText(EditDrinkActivity.this, "Please fill all fields!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    MainActivity.db.UpdateProduct(idDrink,name,newPrice,newQuantity,img);

                    Toast.makeText(EditDrinkActivity.this, "Succeed", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditDrinkActivity.this, DrinkManageActivity.class));
                    finish();
                }
            }
        });

        btnCancelEditDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditDrinkActivity.this,DrinkManageActivity.class));
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
                    btnUploadImageEditDrink.setScaleType(ImageView.ScaleType.FIT_XY);
                    btnUploadImageEditDrink.setImageBitmap(bitmap);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    });

}