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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.utsa.cs3343.rhdraft1.MainActivity;
import edu.utsa.cs3343.rhdraft1.R;

/**
 * The AccountActivity class represents a mobile banking app screen for an individual account
 * @author Chaz Ortiz
 */
public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Array of button IDs
        int[] buttonIds = {
                //R.id.deposit_button,
                //R.id.transfer_button,
                R.id.homeAct_button,
                R.id.logOutAct_button
        };

        // Array of button colors
        int[] buttonColors = {
                //R.color.button_color_1,
                //R.color.button_color_1,
                R.color.button_color_420,
                R.color.button_color_8,
        };

        for (int i = 0; i < buttonIds.length; i++) {
            Button button = findViewById(buttonIds[i]);
            button.setOnClickListener(this);
            button.setBackgroundColor(getResources().getColor(buttonColors[i])); // Use setBackgroundColor
        }

        TextView actTextView = findViewById(R.id.act_text);
        TextView accountTotalTextView = findViewById(R.id.accountTotal_text);
        ScrollView scrollView = findViewById(R.id.scroll_view);
        TextView act1or2TextView;
        act1or2TextView = findViewById(R.id.act1or2_text);

        int actButtonId = getIntent().getIntExtra("actButtonId", -1);

        String actFileName;
        String actDescription;

        if (actButtonId == R.id.actI_button) {
            actFileName = "checking.txt";
            actDescription = "Checking Account";
        } else if (actButtonId == R.id.actII_button) {
            actFileName = "savings.txt";
            actDescription = "Savings Account";
        } else {
            return;
        }

        String[] actContent = loadFileContent(actFileName);
        //actTextView.setText(actContent);
        act1or2TextView.setText(actDescription);
        String[] sortedData = mergeSort(actContent);

        // Format the sorted data back into the original format
        StringBuilder formattedData = new StringBuilder();
        for (String sortedLine : sortedData) {
            String originalLine = findOriginalLine(actContent, sortedLine);
            formattedData.append(originalLine).append("\n");
        }

        actTextView.setText(formattedData.toString());

        double total = calculateTotal(actFileName);
        accountTotalTextView.setText("$" + total);

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    private String findOriginalLine(String[] originalContent, String sortedLine) {
        for (String line : originalContent) {
            if (line.trim().equals(sortedLine.trim())) {
                return line;
            }
        }
        return "";  // Handle the case where the original line is not found
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.homeAct_button) {
            // Open Role_Activity when homeAct_button is clicked
            openRoleActivity();
        } else if (viewId == R.id.logOutAct_button) {
            logOut();
        }
    }

    // Method to open Role_Activity
    private void openRoleActivity() {
        Intent intent = new Intent(AccountActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    private void logOut() {
        Intent intent = new Intent(AccountActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private String[] mergeSort(String[] data) {
        if (data.length <= 1) {
            return data;
        }

        int middle = data.length / 2;
        String[] left = Arrays.copyOfRange(data, 0, middle);
        String[] right = Arrays.copyOfRange(data, middle, data.length);

        mergeSort(left);
        mergeSort(right);

        merge(data, left, right);
        return data;
    }

    private void merge(String[] data, String[] left, String[] right) {
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (compareDates(left[i], right[j]) <= 0) {
                data[k++] = left[i++];
            } else {
                data[k++] = right[j++];
            }
        }

        while (i < left.length) {
            data[k++] = left[i++];
        }

        while (j < right.length) {
            data[k++] = right[j++];
        }
    }

    private int compareDates(String line1, String line2) {
        // Assuming the date is the first comma-delimited token
        String[] tokens1 = line1.split(",");
        String[] tokens2 = line2.split(",");

        if (tokens1.length > 0 && tokens2.length > 0) {
            String date1Str = tokens1[0].trim();
            String date2Str = tokens2[0].trim();

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
                Date date1 = dateFormat.parse(date1Str);
                Date date2 = dateFormat.parse(date2Str);

                // Use compareTo for date comparison
                return date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
                // Handle the case where date parsing fails
                return 0;
            }
        } else {
            // Handle the case where the lines do not have the expected format
            return 0;
        }
    }

    private String[] loadFileContent(String fileName) {
        try {
            InputStream inputStream = getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            List<String> contentList = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                contentList.add(line);
            }

            inputStream.close();
            return contentList.toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return new String[]{"Error loading content"};
        }
    }

    private double calculateTotal(String fileName) {
        try {
            InputStream inputStream = getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            double sum = 0.0;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length > 1) {
                    try {
                        String cleanedAmount = tokens[1].trim().replaceAll("[^\\d.]", "");
                        double amount = Double.parseDouble(cleanedAmount);
                        sum += amount;
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }

            inputStream.close();

            // Round off to two decimal places
            sum = Math.round(sum * 100.0) / 100.0;

            return sum;
        } catch (IOException e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}