package com.example.practice_2_product_spinner;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    ArrayList<Product> products = new ArrayList<Product>();


    Spinner productSpinner;
    TextView priceLabel, totalLabel;
    EditText quantityField;
    Button addButton;

    int selectedProductIndex = 0;
    double total = 0.0;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        products.add(new Product("Original Coffee", 1.77, 12.0));
        products.add(new Product("Cappuccino", 2.6, 12.0));
        products.add(new Product("Hot chocolate", 2.4, 12.0));
        products.add(new Product("Tea", 1.5, 12.0));
        products.add(new Product("Espresso", 0.8, 12.0));
        products.add(new Product("Latte", 3.15, 12.0));


        productSpinner = findViewById(R.id.productSpinnerElement);
        priceLabel = findViewById(R.id.priceLabelElement);
        quantityField = findViewById(R.id.quantityFieldElement);
        addButton = findViewById(R.id.addButtonElement);
        productSpinner = findViewById(R.id.productSpinnerElement);
        totalLabel = findViewById(R.id.totalLabelElement);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getProductsName().toArray() );
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, products.stream().map(s -> s.getName()) );
        productSpinner.setAdapter(adapter);
        productSpinner.setOnItemSelectedListener(this);
        addButton.setOnClickListener(this);

    }

    public ArrayList<String> getProductsName(){
        ArrayList<String> names = new ArrayList<String>();
        for (Product p: products) {
            names.add(p.getName());
        }
        return names;
    }

    @Override
    public void onClick(View v) {
        System.out.println(v);

        if (quantityField.getText().toString().isEmpty()){
            Toast.makeText(getBaseContext(),"Please Enter the required quantity",Toast.LENGTH_SHORT).show();
        }else{
            double quantity = Double.parseDouble(quantityField.getText().toString());
            Product selProd = products.get(selectedProductIndex);
            if(quantity > selProd.getStock()){
                Toast.makeText(getBaseContext(),"There is not enough stock for this product",Toast.LENGTH_SHORT).show();
            }else{
                double drinkPrice=selProd.getPrice();
                if(quantity > 10){
                    total+= (drinkPrice*quantity *0.95);
                }else{
                    total+=drinkPrice*quantity;
                }


                totalLabel.setText(String.format("%.2f",total));
                selProd.setStock(selProd.getStock() - quantity);
                Toast.makeText(getBaseContext(),"Product added successfully",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        priceLabel.setText(products.get(position).getPrice().toString());
        selectedProductIndex = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        priceLabel.setText(products.get(0).getPrice().toString());
    }
}