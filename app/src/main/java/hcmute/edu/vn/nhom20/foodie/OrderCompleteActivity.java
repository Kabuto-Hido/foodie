package hcmute.edu.vn.nhom20.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderCompleteActivity extends AppCompatActivity {
    TextView textviewIdOrderComplete, textviewOrderPriceDeliverComplete,
            textviewDeliverPriceDeliverComplete, textviewTotalPriceDeliverComplete;
    Button btnContinueShopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);

        textviewIdOrderComplete = (TextView) findViewById(R.id.textviewIdOrderComplete);
        textviewOrderPriceDeliverComplete = (TextView) findViewById(R.id.textviewOrderPriceDeliverComplete);
        textviewDeliverPriceDeliverComplete = (TextView) findViewById(R.id.textviewDeliverPriceDeliverComplete);
        textviewTotalPriceDeliverComplete = (TextView) findViewById(R.id.textviewTotalPriceDeliverComplete);
        btnContinueShopping = (Button) findViewById(R.id.btnContinueShopping);

        Intent getData = getIntent();
        int idOrder = getData.getIntExtra("id",0);

        Cursor dataOrder = MainActivity.db.GetData("SELECT * FROM Orders WHERE Id = "+idOrder+"");
        if(dataOrder.moveToFirst()){
            textviewIdOrderComplete.append(Integer.toString(dataOrder.getInt(0)));
            textviewOrderPriceDeliverComplete.setText(Float.toString(dataOrder.getFloat(2)));
            textviewDeliverPriceDeliverComplete.setText(Float.toString(dataOrder.getFloat(3)));
            textviewTotalPriceDeliverComplete.setText(Float.toString(dataOrder.getFloat(4)));
        }

        btnContinueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderCompleteActivity.this, HomeActivity.class));
                finish();
            }
        });

    }
}