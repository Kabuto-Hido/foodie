package hcmute.edu.vn.nhom20.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.nhom20.foodie.model.Account;


public class PayOnDeliverActivity extends AppCompatActivity {
    EditText editTextAddressDeliver;
    RadioButton radioBtn_Deliver, radioBtn_PreOrder;
    RadioGroup radioGroup;
    Button btnChangeAddress, btnContinuePageCart;
    TextView textviewOrderPriceDeliver, textviewDeliverPriceDeliver, textviewTotalPriceDeliver;

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_on_deliver);

        editTextAddressDeliver = (EditText) findViewById(R.id.editTextAddressDeliver);
        radioBtn_Deliver = (RadioButton) findViewById(R.id.radioBtn_Deliver);
        radioBtn_PreOrder = (RadioButton) findViewById(R.id.radioBtn_PreOrder);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        btnChangeAddress = (Button) findViewById(R.id.btnChangeAddress);
        btnContinuePageCart = (Button) findViewById(R.id.btnContinuePageCart);
        textviewOrderPriceDeliver = (TextView) findViewById(R.id.textviewOrderPriceDeliver);
        textviewDeliverPriceDeliver = (TextView) findViewById(R.id.textviewDeliverPriceDeliver);
        textviewTotalPriceDeliver = (TextView) findViewById(R.id.textviewTotalPriceDeliver);

        Intent intent = getIntent();
        int id = intent.getIntExtra("orderId",0);
        float orderPrice = intent.getFloatExtra("orderPrice",0F);

        SharedPreferences sharedPrefer = getSharedPreferences("dataLogin", MODE_PRIVATE);
        user = sharedPrefer.getString("userLogin","");

        textviewOrderPriceDeliver.setText(Float.toString(orderPrice));
        textviewDeliverPriceDeliver.setText(Float.toString(0f));
        textviewTotalPriceDeliver.setText(Float.toString(orderPrice));

        getAddress();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioBtn_PreOrder:
                        editTextAddressDeliver.setEnabled(false);
                        btnChangeAddress.setEnabled(false);

                        textviewDeliverPriceDeliver.setText(Float.toString(0f));
                        textviewTotalPriceDeliver.setText(Float.toString(orderPrice));
                        break;
                    case R.id.radioBtn_Deliver:
                        editTextAddressDeliver.setEnabled(true);
                        btnChangeAddress.setEnabled(true);
                        textviewDeliverPriceDeliver.setText(Float.toString(10000f));
                        Float totalPrice = (float) Float.parseFloat(textviewOrderPriceDeliver.getText().toString().trim()) +
                                10000f;
                        textviewTotalPriceDeliver.setText(Float.toString(totalPrice));
                        break;
                }
            }
        });

        btnChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newAddress = editTextAddressDeliver.getText().toString().trim();
                if(newAddress.equals("")){
                    Toast.makeText(PayOnDeliverActivity.this, "Please Enter Your Address", Toast.LENGTH_SHORT).show();
                }
                else {
                    MainActivity.db.QueryData("UPDATE ACCOUNT SET Address = '"+newAddress+"' WHERE Username = '"+user+"'");

                    textviewDeliverPriceDeliver.setText(Float.toString(15000f));

                    Float totalPrice = (float) Float.parseFloat(textviewOrderPriceDeliver.getText().toString().trim()) +
                            15000f;
                    textviewTotalPriceDeliver.setText(Float.toString(totalPrice));
                    editTextAddressDeliver.clearFocus();
                }
            }
        });

        btnContinuePageCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.db.QueryData("UPDATE Orders SET OrderPrice = " + orderPrice
                        + ", DeliveryPrice = " + Float.parseFloat(textviewDeliverPriceDeliver.getText().toString().trim())
                        + ", TotalPrice = " + Float.parseFloat(textviewTotalPriceDeliver.getText().toString().trim())
                        + " WHERE Id = " + id + "");

                MainActivity.db.QueryData("DELETE FROM Cart WHERE UserName = '"+user+"'");

                Intent completeOrder = new Intent(PayOnDeliverActivity.this,OrderCompleteActivity.class);
                completeOrder.putExtra("id",id);
                startActivity(completeOrder);
                finish();
            }
        });
    }

    private void getAddress(){
        Account dataAccount =MainActivity.db.getAllAccountData(user);
        if(dataAccount.getAddress().equals(null)){
            editTextAddressDeliver.setText("");
        }
        else{
            editTextAddressDeliver.setText(dataAccount.getAddress());
        }
    }
}