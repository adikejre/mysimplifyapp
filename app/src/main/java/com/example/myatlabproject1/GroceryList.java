package com.example.myatlabproject1;

import androidx.appcompat.app.AppCompatActivity;

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

    private void Read()
    {
        double sum=0,v1,v2,lim;
        String temp;
        EditText limit=findViewById(R.id.Expense_limit);
        for(int i=0;i<layoutList.getChildCount();i++)
        {
            View GroceryView= layoutList.getChildAt(i);
            EditText Upr = (EditText)GroceryView.findViewById(R.id.Unit_price);
            EditText Un = (EditText)GroceryView.findViewById(R.id.Unit);

            temp=Upr.getText().toString();
            v1=Double.parseDouble(temp);
            temp=Un.getText().toString();
            v2=Double.parseDouble(temp);
            sum+=v1*v2;
        }
        temp=limit.getText().toString();
        lim=Double.parseDouble(temp);
        if(sum>lim){

            CharSequence text = "EXPENSE LIMIT EXCEEDED!!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(GroceryList.this, text, duration);
            toast.show();
        }
        else{

            String text = "YOUR TOTAL EXPENSE IS " +sum+"\n HAPPY SHOPPING!!!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(GroceryList.this, text, duration);
            toast.show();
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