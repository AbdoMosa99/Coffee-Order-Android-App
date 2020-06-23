/**
 * Coffee Order app makes the user to choose the order of coffee he/she want by selecting how many cups,
 * and how he/she wants the coffee with, then order it by sending an email with the details to the coffee shop
 */

package com.example.android.justjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
{
    // declaring an integer for the quantity of coffee
    int quantity = 1;
    // declaring an integer for the price of the order
    int price = 7;
    // declaring a string to contain the price message that has the details
    String priceMessage;
    // declaring 2 checkboxes to add whippes cream or chocolate on the coffee
    CheckBox whipped;
    CheckBox chocolate;
    // declaring an Edit text box to enter and save the name of the user
    EditText name;
    // declaring the textview that holds the quantity value
    TextView quantityTextView;
    // declaring the textview that holds the price value
    TextView priceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // attaching the java objects within the xml objects
        whipped = findViewById(R.id.checkBox);
        chocolate = findViewById(R.id.checkBox2);
        name = findViewById(R.id.editText);
        quantityTextView = findViewById(R.id.quantityTextView);
        priceTextView = findViewById(R.id.priceTextView);
    }

    /**
     * this method is called when the order button is clicked
     */
    public void submitOrder(View view)
    {
        // make sure the user enters the name
        if(name.getText().toString().equals("")) {
            // if not, show a toast saying the error
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.nameError), Toast.LENGTH_LONG);
            toast.show();
        } else {
            // get the last vesion of the price message
            orderMessage();
            // make an intent to open an email app with the information of the order
            Intent orderMail = new Intent(Intent.ACTION_SEND);
            orderMail.setType("message/rfc822");
            orderMail.putExtra(Intent.EXTRA_EMAIL, new String[] { "coffeeShop@gmail.com" });
            orderMail.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.coffeeOrder));
            orderMail.putExtra(Intent.EXTRA_TEXT, priceMessage);
            // check and start the intent
            if(orderMail.resolveActivity(getPackageManager()) != null) {
                startActivity(orderMail);
            }
        }

    }

    /**
     * This method displays the given quantity value on the screen
     */
    private void displayQuantity(int number)
    {
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen
     */
    private void displayPrice(String message)
    {
        priceTextView.setText(message);
    }

    /**
     * This method increases the quantity when the + button is called
     */
    public void increaseQuantity(View view)
    {
        // check that the quantity is not over 10
        if (quantity < 10)
        {
            quantity++;
            displayQuantity(quantity);
        } else {
            // if not, make a toast to show the error
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.incrementToast), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * This method decreases the quantity when the - button is called
     */
    public void decreaseQuantity(View view)
    {
        // check that the quantity is not under 1
        if(quantity > 1)
        {
            quantity--;
            displayQuantity(quantity);
        } else {
            // if not, make a toast to show the error
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.decrementToast), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * This method calculates and displays the Price when calculate button is called
     */
    public void calculatePrice(View view)
    {
        // calculate that a cup of coffee costs 5 dollars
        price = quantity * 5;
        // and if it's with whipped cream add 2 dollars for each cup
        if(whipped.isChecked()) {
            price += quantity * 2;
        }
        // and if it's with chocolate add 1 dollars for each cup
        if(chocolate.isChecked()) {
            price += quantity * 1;
        }
        // finally add 2-dollar service on the overall
        price += 2;

        // Handle the price message
        orderMessage();
        priceMessage += "\n" + getString(R.string.total) + " " + NumberFormat.getCurrencyInstance().format(price);
        priceMessage += "\n" + getString(R.string.thankYou);

        // if the user doesn't provide the name warn him with an intent
        if(name.getText().toString().equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.nameWarning), Toast.LENGTH_LONG);
            toast.show();
        }
        // display the price message
        displayPrice(priceMessage);

    }

    /**
     * this method identify the price message and displays it
     */
    private void orderMessage() {
        // displaying the name
        priceMessage = getString(R.string.name) + " " + name.getText();
        // displaying the quantity
        priceMessage += "\n" + quantity + " " +  getString(R.string.cupsOfCoffee);
        // check what the topping he ordered and displaying it
        if(whipped.isChecked() && !chocolate.isChecked()) {
            priceMessage += "\n" + getString(R.string.withWhipped);
        } else if(whipped.isChecked() && chocolate.isChecked()) {
            priceMessage += "\n" + getString(R.string.withWhippednChocolate);
        } else if(!whipped.isChecked() && chocolate.isChecked()) {
            priceMessage += "\n" + getString(R.string.withChocolate);
        }
    }

}
