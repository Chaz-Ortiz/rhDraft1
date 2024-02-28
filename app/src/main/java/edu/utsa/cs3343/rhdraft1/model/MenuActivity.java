// ========================================
// Code by:
// ========================================
/**
 //  ██████╗██╗   ██╗ █████╗ ╔███████
 // ██╔════╝██║   ██║██╔══██╗╚════╗██
 // ██║     ████████║███████║╔███████
 // ██║     ██╔═══██║██╔══██║║██════╝
 // ╚██████╗██║   ██║██║  ██║║███████
 //  ╚═════╝╚═╝   ╚═╝╚═╝  ╚═╝╘══════╝
 */
//  █████╗ ██████╗ ████████╗██╗╔███████
// ██╔══██╗██║  ██║╚══██╔══╝██║╚════╗██
// ██║  ██║███████║   ██║   ██║╔███████
// ██║  ██║██╔═██╗    ██║   ██║║██════╝
// ╚█████╔╝██║  ██╗   ██║   ██║║███████
//  ╚════╝ ╚═╝  ╚═╝   ╚═╝   ╚═╝╘══════╝
/**
 *  RowdyHacks 2024
 */
package edu.utsa.cs3343.rhdraft1.model;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.utsa.cs3343.rhdraft1.MainActivity;
import edu.utsa.cs3343.rhdraft1.R;

/**
 * The MenuActivity class represents the main menu page of a mobile banking application,
 * with buttons to navigate to individual accounts and products
 * @author Chaz Ortiz
 */
public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nameTextView;
    private TextView role1TextView;
    private TextView role2TextView;
    private View[] buttons;
    private User user; // Declare user as a class-level variable

    private TextView timestampTextView;
    private Handler handler;
    private ExecutorService executorService;

    /**
     * Called when the activity is first created. Responsible for initializing
     * the activity, including setting up UI elements and handling button clicks.
     *
     * @param savedInstanceState A Bundle containing the activity's previously
     * saved state, if there was any.
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Initialize TextViews
        nameTextView = findViewById(R.id.name_text);
        role1TextView = findViewById(R.id.role1_text);
        role2TextView = findViewById(R.id.role2_text);

        // Initialize timestamp TextView
        timestampTextView = findViewById(R.id.timestamp_text);

        // Initialize handler
        handler = new Handler();

        // Initialize executor service for background tasks
        executorService = Executors.newSingleThreadExecutor();

        try {
            // Retrieve the user object and roles from the Intent
            user = (User) getIntent().getSerializableExtra("user");
            String role1 = getIntent().getStringExtra("role1");
            String role2 = getIntent().getStringExtra("role2");

            if (user != null) {
                // Set realName to nameTextView
                nameTextView.setText(user.getRealName());

                // Display roles in role1TextView and role2TextView
                if (role1 != null) {
                    role1TextView.setText(role1);
                }

                if (role2 != null) {
                    role2TextView.setText(role2);
                }
            }

        } catch (Exception e) {
            // Handle exceptions related to data retrieval
            Log.e("Role_Activity", "Error retrieving data from Intent: " + e.getMessage());
            Toast.makeText(this, "Error displaying user roles. Please try again.", Toast.LENGTH_SHORT).show();
        }

        int[] buttonIds = {R.id.actI_button, R.id.actII_button, R.id.logOut_button, R.id.home_button,
                R.id.creditCard_button, R.id.autoLoan_button, R.id.autoInsurance_button};
        int[] buttonColors = new int[]{
                R.color.button_color_2,
                R.color.button_color_2,
                R.color.button_color_8, // log out
                R.color.button_color_420,
                R.color.button_color_3,
                R.color.button_color_1,
                R.color.button_color_1
        };

        // Initialize buttons array
        buttons = new View[]{
                findViewById(R.id.actI_button),
                findViewById(R.id.actII_button),
                findViewById(R.id.logOut_button)
        };

        for (int i = 0; i < buttonIds.length; i++) {
            setupButton(buttonIds[i], buttonColors[i]);
        }

        // set up the home_button click listener
        Button homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(this);

        // set up the creditCard_button click listener
        Button creditCardButton = findViewById(R.id.creditCard_button);
        creditCardButton.setOnClickListener(this);

        // set up the autoLoan_button click listener
        Button autoLoanbutton = findViewById(R.id.autoLoan_button);
        autoLoanbutton.setOnClickListener(this);

        // set up the autoLoan_button click listener
        Button autoInsurancebutton = findViewById(R.id.autoInsurance_button);
        autoInsurancebutton.setOnClickListener(this);

        // Start updating timestamp using a separate thread
        startUpdatingTimestamp();

    }

    private void startUpdatingTimestamp() {
        Runnable timestampRunnable = new Runnable() {
            @Override
            public void run() {
                // Update timestamp in the UI thread
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateTimestamp();
                    }
                });

                // Schedule the next update after 1 second
                handler.postDelayed(this, 1000);
            }
        };

        // Start the initial update
        handler.post(timestampRunnable);
    }

    private void updateTimestamp() {
        // Get the current time
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);

        // Format the time as you desire
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss", Locale.getDefault());
        String formattedTime = dateFormat.format(date);

        // Update the TextView with the formatted time
        timestampTextView.setText(formattedTime);
    }

    /**
     * Handles the click events for the buttons in the activity.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.actI_button || view.getId() == R.id.actII_button) {
            int actButtonId = view.getId();
            Act_Activity(actButtonId);
        } else if (view.getId() == R.id.logOut_button) {
            logOut();
        } else if (view.getId() == R.id.home_button) {
            // Home Button Clicked
            showToast("Currently on Home Screen");
        } else if (view.getId() == R.id.creditCard_button) {
            // Home Button Clicked
            showToast("Coming Soon!");
        } else if (view.getId() == R.id.autoLoan_button ||
                view.getId() == R.id.autoInsurance_button) {
            // Credit Card, Auto Loan, and Auto Insurance Buttons Clicked
            showToast("No Account owned");
        }
    }

    /**
     * Displays a Toast message.
     *
     * @param message The message to be displayed in the Toast.
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Sets up a button with the specified ID to listen for click events.
     *
     * @param buttonID The resource ID of the button.
     * @param dotColor The color resource ID for the button.
     */
    private void setupButton(int buttonID, int dotColor) {
        Button button = findViewById(buttonID);
        button.setOnClickListener(this);
        button.setBackgroundColor(getResources().getColor(dotColor));
    }

    /**
     * Starts the Act_Activity with the specified act button ID.
     *
     * @param actButtonId The resource ID of the act button clicked.
     */
    private void Act_Activity(int actButtonId) {
        Intent intent = new Intent(MenuActivity.this, AccountActivity.class);
        intent.putExtra("actButtonId", actButtonId);
        intent.putExtra("user", user); // Pass the user object as an extra
        startActivity(intent);
    }

    /**
     * Logs out the user and navigates back to the main activity.
     */
    private void logOut() {
        // logout logic
        // go back to the MainActivity and clear login credentials
        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(intent);
        // additional logic to clear user credentials
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Shutdown executor service when the activity is destroyed
        executorService.shutdown();
    }
}