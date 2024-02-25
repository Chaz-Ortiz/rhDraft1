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

package edu.utsa.cs3343.rhdraft1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import edu.utsa.cs3343.rhdraft1.model.MenuActivity;
import edu.utsa.cs3343.rhdraft1.model.User;
import edu.utsa.cs3343.rhdraft1.model.UserBank;

/**
 * The MainActivity class represents the login screen of the application, responsible for user authentication
 * and navigation to the Role_Activity based on the provided credentials.
 * @author Chaz Ortiz
 */
public class MainActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button Login_button;
    private User user; // instance of the User class
    private UserBank userBank; // UserBank to store user data
    private View[] buttons;

    private TextView timestampTextView;
    private Handler handler;

    private int consecutiveFailedAttempts = 0;

    /**
     * Called when the activity is first created. Responsible for initializing the activity,
     * including setting up UI elements and handling button clicks.
     * @param savedInstanceState A Bundle containing the activity's previously saved state, if there was any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        timestampTextView = findViewById(R.id.timestamp_text);
        handler = new Handler(Looper.getMainLooper());

        // Array of dot colors
        int[] dotColors = new int[]{
                R.color.button_color_69,
        };

        // Initialize buttons array
        buttons = new View[]{
                findViewById(R.id.Login_button),
        };

        // Set up click listeners for each dot
        for (int i = 0; i < buttons.length; i++) {
            final int dotIndex = i;

            // Set background color for each button
            buttons[i].setBackgroundColor(ContextCompat.getColor(this, dotColors[i]));

            // Set click listener for each button
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String textUsername = editTextUsername.getText().toString();
                    String textPassword = editTextPassword.getText().toString();
                    User currentUser = validateUserAndGetUser(textUsername, textPassword);

                    if (currentUser != null) {
                        openRoleActivity(currentUser);
                    } else {
                        handleFailedLogin();
                    }
                }
            });
        }

        // Initialize timestamp TextView
        //timestampTextView = findViewById(R.id.timestamp_text);

        // Initialize handler
        handler = new Handler();

        // Start updating timestamp using a separate thread
        startUpdatingTimestamp();

        //Login_button = findViewById(R.id.Login_button);

        // Initialize the UserBank and load user data from the CSV file
        userBank = new UserBank();
        userBank.loadUsers(this);
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

                // Schedule the next update after 10 seconds
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

        Log.d("Timestamp", "Formatted Time: " + formattedTime);

        // Update the TextView with the formatted time
        timestampTextView.setText(formattedTime);
    }

    /**
     * Validates the provided username and password against the stored user data
     * and returns the corresponding User object if authentication is successful.
     *
     * @param username The username to validate.
     * @param password The password to validate.
     * @return The authenticated User object or null if authentication fails.
     */
    private User validateUserAndGetUser(String username, String password) {
        for (User user : userBank.getUsers()) {
            if (user.validate(username, password)) {
                return user;
            }
        }
        return null; // Authentication failed
    }

    /**
     * Opens the Role_Activity with the specified user and role information as extras.
     * @param user The authenticated User object.
     */
    private void openRoleActivity(User user) {
        // Create an Intent for Role_Activity
        Intent intent = new Intent(this, MenuActivity.class);

        // Pass the user object as an extra
        intent.putExtra("user", user);

        // Retrieve the roles from the user object
        ArrayList<String> roles = user.getRoles();

        // Check if the user has roles and pass them as extras
        if (roles != null && roles.size() >= 2) {
            intent.putExtra("role1", roles.get(0));
            intent.putExtra("role2", roles.get(1));
        }

        // Start the Role_Activity
        startActivity(intent);
    }

    private void handleFailedLogin() {
        // Increment the counter for consecutive failed attempts
        consecutiveFailedAttempts++;

        // Display a toast for individual failed attempts
        if (consecutiveFailedAttempts <= 3) {
            // Only show "Authentication failed" message for the first 2 failed attempts
            Toast.makeText(MainActivity.this, "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show();
        }

        // Check if there are 3 consecutive failed attempts
        if (consecutiveFailedAttempts > 3) {
            // Display an alert for unusual activity
            Toast.makeText(MainActivity.this, "Unusual activity detected. Maximum failed login attempts.", Toast.LENGTH_LONG).show();
        }
    }
}