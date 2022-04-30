package hcmute.edu.vn.nhom20.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MorePageActivity extends AppCompatActivity {
    Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_page);

        btnLogOut = (Button) findViewById(R.id.btnLogOut);

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

                Intent intent = new Intent(MorePageActivity.this,
                        LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}