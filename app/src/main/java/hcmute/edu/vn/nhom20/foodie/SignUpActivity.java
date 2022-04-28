package hcmute.edu.vn.nhom20.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    EditText editTextUserNameSignUp,editTextEmailAddressSignUp,editTextPasswordSignUp;
    Button btnSignUpSignUp, btnLoginSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextUserNameSignUp = (EditText) findViewById(R.id.editTextUserNameSignUp);
        editTextEmailAddressSignUp= (EditText) findViewById(R.id.editTextEmailAddressSignUp);
        editTextPasswordSignUp = (EditText) findViewById(R.id.editTextPasswordSignUp);
        btnSignUpSignUp = (Button) findViewById(R.id.btnSignUpSignUp);
        btnLoginSignUp = (Button) findViewById(R.id.btnLoginSignUp);

        btnLoginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        btnSignUpSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUserNameSignUp.getText().toString().trim();
                String email = editTextEmailAddressSignUp.getText().toString().trim();
                String password = editTextPasswordSignUp.getText().toString().trim();

                if(username.equals("") || email.equals("") || password.equals("")){
                    Toast.makeText(SignUpActivity.this,"You must fill all fields",Toast.LENGTH_SHORT).show();
                }
                else if(!checkUsername(username)){            //username have in database
                    Toast.makeText(SignUpActivity.this,"Username already exist",Toast.LENGTH_SHORT).show();
                }else if(!checkPassword(password)){         //password haven't satisfy the condition
                    Toast.makeText(SignUpActivity.this,"Password have at least eight characters " +
                            "and consists of only letters and digits",Toast.LENGTH_LONG).show();
                }else if(!checkEmail(email)){
                    Toast.makeText(SignUpActivity.this,"Email already exist",Toast.LENGTH_SHORT).show();
                }
                else{
                    MainActivity.db.InsertAccount(username,password,null,email,null,null,"Customer");
                    Toast.makeText(SignUpActivity.this,"Succeed",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });
    }
    private boolean checkUsername(String username){
        Cursor accountData = MainActivity.db.GetData("SELECT * FROM Account WHERE Username = '"
                + username +"'");
        if(!accountData.moveToFirst()){ //empty
            return true;
        }
        return false;
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
    private boolean checkEmail(String email){
        Cursor accountData = MainActivity.db.GetData("SELECT * FROM Account WHERE Email = '"
                + email +"'");
        if(!accountData.moveToFirst()){ //empty
            return true;
        }
        return false;
    }
}