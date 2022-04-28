package hcmute.edu.vn.nhom20.foodie;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
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

public class EditShopActivity extends AppCompatActivity {
    ImageView btnUploadImageEditShop,btnBackEditShopPage;
    Button btnConfirmEditShop, btnCancelEditShop;
    EditText editTextEditShopName, editTextEditShopAddress, editTextEditShopPhone;
    int REQUEST_CODE = 789;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shop);

        btnUploadImageEditShop = (ImageView) findViewById(R.id.btnUploadImageEditShop);
        btnBackEditShopPage = (ImageView) findViewById(R.id.btnBackEditShopPage);
        btnConfirmEditShop = (Button) findViewById(R.id.btnConfirmEditShop);
        btnCancelEditShop = (Button) findViewById(R.id.btnCancelEditShop);
        editTextEditShopName = (EditText) findViewById(R.id.editTextEditShopName);
        editTextEditShopAddress = (EditText) findViewById(R.id.editTextEditShopAddress);
        editTextEditShopPhone = (EditText) findViewById(R.id.editTextEditShopPhone);


        SharedPreferences sharedPreferences = getSharedPreferences("dataShop", MODE_PRIVATE);
        String name = sharedPreferences.getString("editShopName","");

        Shop shop = MainActivity.db.getAllShopData(name);

        editTextEditShopName.setText(name);
        editTextEditShopAddress.setText(shop.getAddress());
        editTextEditShopPhone.setText(shop.getPhone());

        byte[] picture = shop.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0,picture.length);
        btnUploadImageEditShop.setImageBitmap(bitmap);

        btnBackEditShopPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditShopActivity.this, AdminHomeActivity.class));
            }
        });

        btnUploadImageEditShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        EditShopActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE
                );
            }
        });

        btnConfirmEditShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BitmapDrawable bitmapDrawable = (BitmapDrawable) btnUploadImageEditShop.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] img = byteArrayOutputStream.toByteArray();

                MainActivity.db.QueryData("UPDATE Shop SET Image = '"+img
                        +"', Address = '" +editTextEditShopAddress.getText().toString().trim()
                        +"', Phone = '"+editTextEditShopPhone.getText().toString().trim()
                        +"' WHERE Name = '"+editTextEditShopName.getText().toString().trim()+"'");

                Toast.makeText(EditShopActivity.this, "Succeed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditShopActivity.this, AdminHomeActivity.class));
                finish();
            }
        });

        btnCancelEditShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditShopActivity.this, AdminHomeActivity.class));
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
                    btnUploadImageEditShop.setScaleType(ImageView.ScaleType.FIT_XY);
                    btnUploadImageEditShop.setImageBitmap(bitmap);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    });

}