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

import hcmute.edu.vn.nhom20.foodie.model.Account;

public class ProfileActivity extends AppCompatActivity {
    ImageView imageBackPageEditProfile, imageUser;
    EditText editTextUserNameEditProfile, editTextEmailEditProfile,
            editTextPhoneNumberEditProfile, editTextAddressEditProfile;
    Button btnConfirmEditProfile;

    int REQUEST_CODE = 990;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageBackPageEditProfile = (ImageView) findViewById(R.id.imageBackPageEditProfile);
        imageUser = (ImageView) findViewById(R.id.imageUser);
        editTextUserNameEditProfile = (EditText) findViewById(R.id.editTextUserNameEditProfile);
        editTextEmailEditProfile = (EditText) findViewById(R.id.editTextEmailEditProfile);
        editTextPhoneNumberEditProfile = (EditText) findViewById(R.id.editTextPhoneNumberEditProfile);
        editTextAddressEditProfile = (EditText) findViewById(R.id.editTextAddressEditProfile);
        btnConfirmEditProfile = (Button) findViewById(R.id.btnConfirmEditProfile);

        SharedPreferences sharedPrefer = getSharedPreferences("dataLogin", MODE_PRIVATE);
        String username = sharedPrefer.getString("userLogin","");

        Account acc = MainActivity.db.getAllAccountData(username);
        editTextUserNameEditProfile.setText(username);
        editTextEmailEditProfile.setText(acc.getEmail());

        editTextPhoneNumberEditProfile.setText(acc.getPhone());
        editTextAddressEditProfile.setText(acc.getAddress());

        byte[] picture = acc.getImage();
        if(picture!=null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0,picture.length);
            imageUser.setImageBitmap(bitmap);
            imageUser.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        else {
            imageUser.setImageResource(R.drawable.icon_image_not_found);
        }

        imageBackPageEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,MorePageActivity.class));
                finish();
            }
        });

        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        ProfileActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE
                );
            }
        });

        btnConfirmEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageUser.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                byte[] img;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                img = byteArrayOutputStream.toByteArray();

                String newEmail = editTextEmailEditProfile.getText().toString().trim();
                String newPhone = editTextPhoneNumberEditProfile.getText().toString().trim();
                String newAddress = editTextAddressEditProfile.getText().toString().trim();

                if(newEmail.equals("") || newPhone.equals("") || newAddress.equals("")){
                    Toast.makeText(ProfileActivity.this, "Please fill all fields!!", Toast.LENGTH_SHORT).show();
                }
                else if(!checkEmail(newEmail) && !newEmail.equals(acc.getEmail())){
                    Toast.makeText(ProfileActivity.this, "Email already exist", Toast.LENGTH_SHORT).show();
                }
                else{
                    MainActivity.db.UpdateAccount(username,newPhone,newEmail,newAddress,img);
                    Toast.makeText(ProfileActivity.this, "Succeed", Toast.LENGTH_SHORT).show();
                }

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
                    imageUser.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageUser.setImageBitmap(bitmap);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    });

    private boolean checkEmail(String email){
        Cursor accountData = MainActivity.db.GetData("SELECT * FROM Account WHERE Email = '"
                + email +"'");
        if(!accountData.moveToFirst()){ //empty
            return true;
        }
        return false;
    }

}