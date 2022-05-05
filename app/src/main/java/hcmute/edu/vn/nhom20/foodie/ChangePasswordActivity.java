package hcmute.edu.vn.nhom20.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

import hcmute.edu.vn.nhom20.foodie.model.Account;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText editTextOldPassword, editTextNewPassword;
    Button btnConfirmChangePassword;
    ImageView imageBackPageChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        editTextOldPassword = (EditText) findViewById(R.id.editTextOldPassword);
        editTextNewPassword = (EditText) findViewById(R.id.editTextNewPassword);
        btnConfirmChangePassword = (Button) findViewById(R.id.btnConfirmChangePassword);
        imageBackPageChangePassword = (ImageView) findViewById(R.id.imageBackPageChangePassword);

        SharedPreferences sharedPrefer = getSharedPreferences("dataLogin", MODE_PRIVATE);
        String username = sharedPrefer.getString("userLogin","");

        Account acc = MainActivity.db.getAllAccountData(username);

        imageBackPageChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangePasswordActivity.this,MorePageActivity.class));
                finish();
            }
        });

        btnConfirmChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPass = editTextOldPassword.getText().toString().trim();
                String newPass = editTextNewPassword.getText().toString().trim();

                if(oldPass.equals(acc.getPassword())){
                    if(checkPassword(newPass)){
                        MainActivity.db.QueryData("UPDATE Account SET Password = '"+newPass
                                +"' WHERE Username = '"+username+"'");

                        Toast.makeText(ChangePasswordActivity.this, "Succeed", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ChangePasswordActivity.this,LoginActivity.class));
                        finish();

                    }
                    else {
                        Toast.makeText(ChangePasswordActivity.this, "Password have at least eight characters " +
                                "and consists of only letters and digits", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(ChangePasswordActivity.this, "Please enter correct old password!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean checkPassword(String password){
        if (password.length() < 8) {
            return false;
        } else {
            char c;
            int count = 1;
            for (int i = 0; i < password.length() - 1; i++) {
                c = password.charAt(i);
                if (!Character.isLetterOrDigit(c)) {
                    return false;
                } else if (Character.isDigit(c)) {
                    count++;
                }
                if (count < 1){
                    return false;
                }
            }
        }
        return true;
    }
    public String GetPassword(){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();

        Random rand = new Random();

        for(int i = 0; i < 6; i++){
            char c = chars[rand.nextInt(chars.length)];
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }

}