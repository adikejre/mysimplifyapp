package com.example.myatlabproject1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GroceryList extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutList;
    Button buttonAdd;
    Button buttonSubmitList;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        layoutList = findViewById(R.id.layout_list);
        buttonAdd = findViewById(R.id.button_add);
        buttonSubmitList = findViewById(R.id.button_submit_list);

        buttonAdd.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_add:
                addView();
                break;

            case R.id.button_submit_list:
                Read();
                break;

        }


    }


    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private void Read()
    {
        double sum=0,v1,v2,lim;
        String temp1,temp2,temp3;
        EditText limit=findViewById(R.id.Expense_limit);
        temp3=limit.getText().toString();
        if(!isNumeric(temp3)){
            Toast.makeText(this, "Enter numbers only!", Toast.LENGTH_SHORT).show();
            return;
        }

        for(int i=0;i<layoutList.getChildCount();i++)
        {
            View GroceryView= layoutList.getChildAt(i);
            EditText Upr = (EditText)GroceryView.findViewById(R.id.Unit_price);
            EditText Un = (EditText)GroceryView.findViewById(R.id.Unit);

            temp1=Upr.getText().toString();
            temp2=Un.getText().toString();
            if(!isNumeric(temp1)||!isNumeric(temp2)){
                Toast.makeText(this, "Enter numbers only!", Toast.LENGTH_SHORT).show();
                return;
            }


            v1=Double.parseDouble(temp1);
            v2=Double.parseDouble(temp2);
            sum+=v1*v2;
        }

        lim=Double.parseDouble(temp3);
        if(sum>lim){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(GroceryList.this);
            builder1.setCancelable(true);
//            CharSequence text = "EXPENSE LIMIT EXCEEDED!!";
            builder1
                    .setTitle("Warning")
                    .setMessage("EXPENSE LIMIT EXCEEDED!!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();

//            int duration = Toast.LENGTH_SHORT;
//            Toast toast = Toast.makeText(GroceryList.this, text, duration);
//            toast.show();
        }
        else{

            String text = "YOUR TOTAL EXPENSE IS \n" +sum;
            AlertDialog.Builder builder2 = new AlertDialog.Builder(GroceryList.this);
            builder2.setCancelable(true);
            builder2
                    .setTitle("Happy shopping!")
                    .setMessage(text)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert12 = builder2.create();
            alert12.show();

//            int duration = Toast.LENGTH_LONG;
//            Toast toast = Toast.makeText(GroceryList.this, text, duration);
//            toast.show();
        }

    }
    private void addView() {

        final View GroceryView = getLayoutInflater().inflate(R.layout.add_item,null,false);

        EditText editText1 = (EditText)GroceryView.findViewById(R.id.Item_name);
        EditText editText2 = (EditText)GroceryView.findViewById(R.id.Unit_price);
        EditText editText3 = (EditText)GroceryView.findViewById(R.id.Unit);
        ImageView imageClose = (ImageView)GroceryView.findViewById(R.id.image_remove);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(GroceryView);
            }
        });

        layoutList.addView(GroceryView);

    }

    private void removeView(View view){

        layoutList.removeView(view);

    }

}