package com.example.xander.fxconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class ConvertActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);

        final TextView txt = (TextView) findViewById(R.id.txtRate);
        final TextView txtR = (TextView) findViewById(R.id.txtJMD);
        final TextView txtF = (TextView) findViewById(R.id.txtFarin);

            // Receiving value into activity using intent.
            String TempHolder = getIntent().getStringExtra("ItemClick");
            TempHolder = TempHolder.replaceAll("\\D+","");
            final Double amt = Double.parseDouble(TempHolder) / 100;


            txtF.setText("1");
            txt.setText("$ "+ amt.toString());
            txtR.setText(amt.toString());



            txtF.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        try {
                            Double farinVal = Double.parseDouble(txtF.getText().toString());
                            Log.e("FARIN: ", farinVal.toString());
                            Toast.makeText(ConvertActivity.this, farinVal.toString(), Toast.LENGTH_SHORT).show();
                            txtR.setText(fromFarin(farinVal, amt).toString());
                        } catch (Exception e) {
                            Log.e("Ontext Exception: ", e.toString());
                        }

                }

                @Override
                public void afterTextChanged(Editable editable) {  }
            });

//            txtR.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {  }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                        try {
//                            Double JMDVal = Double.parseDouble(txtR.getText().toString());
//                            Log.e("JMD: ", JMDVal.toString());
//                            Toast.makeText(ConvertActivity.this, JMDVal.toString(), Toast.LENGTH_SHORT).show();
//                            txtF.setText(fromJMD(JMDVal, amt).toString());
//                        } catch (Exception e) {
//                            Log.e("Ontext Exception: ", e.toString());
//                        }
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {  }
//            });

    }

    private Double fromFarin(Double cur, Double curr) {
        Double ans = 0.0;
        ans = cur * curr;
        return ans;
    }
    private Double fromJMD(Double jmd, Double cur) {
        Double ans = 0.0;
        ans = jmd / cur;
        return ans;
    }

}
