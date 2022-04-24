package hcmute.edu.vn.nhom20.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnGetStart;
    static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetStart = (Button) findViewById(R.id.btnGetStart);

        db = new Database(this,"foodie.sqlite",null,1);

        //table Account
        db.QueryData("CREATE TABLE IF NOT EXISTS Account(Username VARCHAR(200) PRIMARY KEY," +
                " Password VARCHAR(200) NOT NULL,Phone VARCHAR(12), Email VARCHAR(200) NOT NULL UNIQUE," +
                "Address NVARCHAR(200), Image BLOB, Role VARCHAR(30) NOT NULL)");

        //insert admin
        //db.InsertAccount("Kabuto","123456",null,"ngobuituananh@gmail.com",null,null,"Admin");

        btnGetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }
}