package hcmute.edu.vn.nhom20.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUsernameLogin, editTextPasswordLogin;

    Button btnLoginLogin,btnSignUpLogin,btnForgotPasswordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsernameLogin = (EditText) findViewById(R.id.editTextUsernameLogin);
        editTextPasswordLogin = (EditText) findViewById(R.id.editTextPasswordLogin);
        btnLoginLogin = (Button) findViewById(R.id.btnLoginLogin);
        btnSignUpLogin = (Button) findViewById(R.id.btnSignUpLogin);
        btnForgotPasswordLogin = (Button) findViewById(R.id.btnForgotPasswordLogin);

        Login();
        SignUp();

    }

    private void Login(){
        btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsernameLogin.getText().toString().trim();
                String password = editTextPasswordLogin.getText().toString().trim();
                Cursor accountData = MainActivity.db.GetData("SELECT * FROM Account WHERE Username = '"
                        + username +"' AND Password = '"+ password +"'");

                if(accountData.moveToFirst() == false){ //empty
                    Toast.makeText(LoginActivity.this,"Wrong Username or Password",Toast.LENGTH_SHORT).show();
                }
                else{
                        String roleUser = accountData.getString(6);
                        if(roleUser.equals("Admin")){
                            startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
                        }
                        else{
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        }
                }
            }
        });
    }

    private void SignUp(){
        btnSignUpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}