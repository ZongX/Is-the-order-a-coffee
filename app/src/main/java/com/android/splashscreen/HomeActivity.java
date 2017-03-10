package com.android.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.justjava.R;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int x = 0;
    int cnt = 0;
    String msg;

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        cnt++;
        if (cnt >= 2) {
            composeEmail(msg);
        }
        double price = calcPrice(x);
        String userName = getName();
        createOrderSummary(price, userName);
        Button orderButton = (Button) findViewById(R.id.conOrderButton);
        orderButton.setText("ORDER");
    }

    public String getName() {
        EditText textField = (EditText) findViewById(R.id.editText);
        String userName = textField.getText().toString();
        return userName;
    }

    public void createOrderSummary(double price, String name) {
        CheckBox creamBox = (CheckBox) findViewById(R.id.whippedCreamBox);
        boolean isCream = creamBox.isChecked();
        CheckBox chocBox = (CheckBox) findViewById(R.id.chocolateBox);
        boolean isChocolate = chocBox.isChecked();
        String toppingsMsg = "";
        if (isCream) {
            toppingsMsg = "\n1 Whipped Cream";
            price += 1;
        }
        if (isChocolate) {
            toppingsMsg += "\n1 Chocolate";
            price += 2;
        }
        msg = "Name: " + name + "\nQuantity: " + x + toppingsMsg + "\nTotal: $" + price + "\nThank you!";
        displayMessage(msg);
    }

    public double calcPrice(int quant) {
        return quant * 5;
    }

    public void decrement(View view) {
        if (x <= 0) {
            Toast popupText = Toast.makeText(this, "You cannot have fewer than zero cups of coffee", Toast.LENGTH_SHORT);
            popupText.show();
            return;
        }
        display(--x);
    }

    public void increment(View view) {
        if (x >= 10) {
            Toast popupText = Toast.makeText(this, "It is recommended that you only order a maximum of 10 cups of coffee at a time to ensure quality ", Toast.LENGTH_SHORT);
            popupText.show();
            return;
        }
        display(++x);
    }

    private void composeEmail(String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, "localcoffeeshop@coffee.ca");
        intent.putExtra(Intent.EXTRA_SUBJECT, "New order of coffee");
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}