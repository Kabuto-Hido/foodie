package hcmute.edu.vn.nhom20.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MorePageActivity extends AppCompatActivity {
    ImageView imageBackPageMorePage;
    Button btnLogOut, btnEditProfile, btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_page);

        imageBackPageMorePage= (ImageView) findViewById(R.id.imageBackPageMorePage);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnEditProfile = (Button) findViewById(R.id.btnEditProfile);
        btnChangePassword = (Button) findViewById(R.id.btnChangePassword);

        SharedPreferences sharedPrefer = getSharedPreferences("dataLogin", MODE_PRIVATE);
        String username = sharedPrefer.getString("userLogin","");
        Account acc = MainActivity.db.getAllAccountData(username);
        String role = acc.getRole();

        imageBackPageMorePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(role.equals("Admin")){
                    startActivity(new Intent(MorePageActivity.this, AdminHomeActivity.class));
                }
                else{
                    startActivity(new Intent(MorePageActivity.this, HomeActivity.class));
                }
                finish();
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MorePageActivity.this,ProfileActivity.class));
                finish();
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MorePageActivity.this,ChangePasswordActivity.class));
                finish();
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences myPrefs1 = getSharedPreferences("dataLogin", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = myPrefs1.edit();
                editor1.clear();
                editor1.commit();

                SharedPreferences myPrefs2 = getSharedPreferences("dataShop", MODE_PRIVATE);
                SharedPreferences.Editor editor2 = myPrefs2.edit();
                editor2.clear();
                editor2.commit();

                SharedPreferences myPrefs3 = getSharedPreferences("dataFood", MODE_PRIVATE);
                SharedPreferences.Editor editor3 = myPrefs3.edit();
                editor3.clear();
                editor3.commit();

                SharedPreferences myPrefs4 = getSharedPreferences("dataDrink",MODE_PRIVATE);
                SharedPreferences.Editor editor4 = myPrefs4.edit();
                editor4.clear();
                editor4.commit();

                Intent intent = new Intent(MorePageActivity.this,
                        LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}