package hcmute.edu.vn.nhom20.foodie;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddShopActivity extends AppCompatActivity {
    ImageView btnUploadImageShop,btnBackAddShopPage;
    Button btnConfirmAddShop, btnCancelAddShop;
    EditText editTextShopName, editTextShopAddress, editTextShopPhone;
    int REQUEST_CODE = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        btnUploadImageShop = (ImageView) findViewById(R.id.btnUploadImageShop);
        btnBackAddShopPage = (ImageView) findViewById(R.id.btnBackAddShopPage);
        btnConfirmAddShop = (Button) findViewById(R.id.btnConfirmAddShop);
        btnCancelAddShop = (Button) findViewById(R.id.btnCancelAddShop);
        editTextShopName = (EditText) findViewById(R.id.editTextShopName);
        editTextShopAddress = (EditText) findViewById(R.id.editTextShopAddress);
        editTextShopPhone = (EditText) findViewById(R.id.editTextShopPhone);

        btnBackAddShopPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddShopActivity.this, AdminHomeActivity.class));
                finish();
            }
        });

        btnUploadImageShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        AddShopActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE
                );
            }
        });

        btnConfirmAddShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) btnUploadImageShop.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] img = byteArrayOutputStream.toByteArray();

                MainActivity.db.InsertShop(editTextShopName.getText().toString().trim(),
                        img,editTextShopAddress.getText().toString().trim(),
                        editTextShopPhone.getText().toString().trim());

                Toast.makeText(AddShopActivity.this, "Succeed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddShopActivity.this, AdminHomeActivity.class));
            }
        });

        btnCancelAddShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddShopActivity.this, AdminHomeActivity.class));
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
                    btnUploadImageShop.setScaleType(ImageView.ScaleType.FIT_XY);
                    btnUploadImageShop.setImageBitmap(bitmap);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    });


}