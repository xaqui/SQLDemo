package com.example.sqldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // references to buttons and other controls (member variables)
    Button btn_add, btn_viewAll;
    EditText et_name, et_age;
    Switch sw_activeCustomer;
    ListView lv_customerList;

    ArrayAdapter customerArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        btn_viewAll = findViewById(R.id.btn_viewAll);
        et_age = findViewById(R.id.et_age);
        et_name = findViewById(R.id.et_name);
        sw_activeCustomer = findViewById(R.id.sw_active);
        lv_customerList = findViewById(R.id.lv_customerList);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        ShowCustomersOnListView(dataBaseHelper);

        // button listeners
        btn_add.setOnClickListener(new View.OnClickListener() {
            CustomerModel customerModel;
            @Override
            public void onClick(View view) {
                try {
                    customerModel = new CustomerModel(-1,et_name.getText().toString(), Integer.parseInt(et_age.getText().toString()), sw_activeCustomer.isChecked());
                    Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();

                    DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

                    boolean success = dataBaseHelper.addOne(customerModel);

                    ShowCustomersOnListView(dataBaseHelper);

                } catch(Exception e) {
                    Toast.makeText(MainActivity.this, "Error Creating Customer", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

                ShowCustomersOnListView(dataBaseHelper);
                //Toast.makeText(MainActivity.this, allCustomers.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        lv_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel clickedCustomer = (CustomerModel) parent.getItemAtPosition(position);
                dataBaseHelper.deleteOne(clickedCustomer);
                ShowCustomersOnListView(dataBaseHelper);
                Toast.makeText(MainActivity.this, "Deleted "+clickedCustomer.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ShowCustomersOnListView(DataBaseHelper dataBaseHelper) {
        customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getAllCustomers());
        lv_customerList.setAdapter(customerArrayAdapter);
    }
}