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

public class AddDrinkActivity extends AppCompatActivity {
    ImageView btnBackAddDrinkPage,btnUploadImageDrink;
    EditText editTextDrinkName, editTextDrinkPrice, editTextDrinkQuantity;
    Button btnConfirmAddDrink, btnCancelAddDrink;
    int REQUEST_CODE = 211;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);

        btnBackAddDrinkPage = (ImageView) findViewById(R.id.btnBackAddDrinkPage);
        btnUploadImageDrink = (ImageView) findViewById(R.id.btnUploadImageDrink);
        editTextDrinkName = (EditText) findViewById(R.id.editTextDrinkName);
        editTextDrinkPrice = (EditText) findViewById(R.id.editTextDrinkPrice);
        editTextDrinkQuantity = (EditText) findViewById(R.id.editTextDrinkQuantity);
        btnConfirmAddDrink = (Button) findViewById(R.id.btnConfirmAddDrink);
        btnCancelAddDrink = (Button) findViewById(R.id.btnCancelAddDrink);

        btnBackAddDrinkPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddDrinkActivity.this, DrinkManageActivity.class));
                finish();
            }
        });

        btnUploadImageDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        AddDrinkActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE
                );
            }
        });

        btnConfirmAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getShopAddDrink = getIntent();
                String shopNameAddDrink = getShopAddDrink.getStringExtra("shopNameAddDrink");

                BitmapDrawable bitmapDrawable = (BitmapDrawable) btnUploadImageDrink.getDrawable();
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

                Float drinkPrice = Float.parseFloat(editTextDrinkPrice.getText().toString().trim());
                int drinkQuantity = Integer.parseInt(editTextDrinkQuantity.getText().toString().trim());

                MainActivity.db.InsertProduct(editTextDrinkName.getText().toString().trim(),
                        null,drinkPrice,drinkQuantity, img, "Drink",shopNameAddDrink);

                Toast.makeText(AddDrinkActivity.this, "Succeed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddDrinkActivity.this, DrinkManageActivity.class));
                finish();
            }
        });

        btnCancelAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddDrinkActivity.this, DrinkManageActivity.class));
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
                    btnUploadImageDrink.setScaleType(ImageView.ScaleType.FIT_XY);
                    btnUploadImageDrink.setImageBitmap(bitmap);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    });

}