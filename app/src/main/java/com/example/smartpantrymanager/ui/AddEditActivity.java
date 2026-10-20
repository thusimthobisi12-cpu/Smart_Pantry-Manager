package com.example.smartpantrymanager.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.database.AppDatabase;
import com.example.smartpantrymanager.model.PantryItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

// Screen used to ADD a new pantry item or EDIT/DELETE an existing one.
public class AddEditActivity extends AppCompatActivity {


    public static final String EXTRA_ITEM_ID = "item_id";

    private static final List<String> UNITS = Arrays.asList("g", "kg", "ml", "l", "pcs");

    private EditText editName, editQuantity, editExpiry;
    private Spinner spinnerUnit;
    private Button buttonDelete;
    private AppDatabase db;


    private PantryItem editingItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        db = AppDatabase.getInstance(this);

        editName = findViewById(R.id.editName);
        editQuantity = findViewById(R.id.editQuantity);
        editExpiry = findViewById(R.id.editExpiry);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        buttonDelete = findViewById(R.id.buttonDelete);
        Button buttonSave = findViewById(R.id.buttonSave);
        TextView title = findViewById(R.id.textFormTitle);

        spinnerUnit.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, UNITS));


        int itemId = getIntent().getIntExtra(EXTRA_ITEM_ID, -1);
        if (itemId != -1) {
            editingItem = db.pantryItemDAO().getItemById(itemId);
        }

        if (editingItem != null) {
            title.setText("Edit Ingredient");
            editName.setText(editingItem.name);
            editQuantity.setText(formatQuantity(editingItem.quantity));
            editExpiry.setText(editingItem.expiryDate);
            spinnerUnit.setSelection(UNITS.indexOf(editingItem.unit));
            buttonDelete.setVisibility(View.VISIBLE);
        }

        buttonSave.setOnClickListener(v -> saveItem());
        buttonDelete.setOnClickListener(v -> confirmDelete());
    }


    private void saveItem() {
        String name = editName.getText().toString().trim().toLowerCase(Locale.ROOT);
        String qtyText = editQuantity.getText().toString().trim();
        String expiry = editExpiry.getText().toString().trim();
        String unit = spinnerUnit.getSelectedItem().toString();


        if (name.isEmpty()) {
            editName.setError("Please enter a name");
            return;
        }
        if (qtyText.isEmpty()) {
            editQuantity.setError("Please enter a quantity");
            return;
        }
        double quantity;
        try {
            quantity = Double.parseDouble(qtyText);
        } catch (NumberFormatException e) {
            editQuantity.setError("Quantity must be a number");
            return;
        }
        if (quantity <= 0) {
            editQuantity.setError("Quantity must be more than 0");
            return;
        }
        if (!expiry.isEmpty() && !isValidDate(expiry)) {
            editExpiry.setError("Use the format YYYY-MM-DD");
            return;
        }

        if (editingItem == null) {
            // CREATE
            db.pantryItemDAO().insert(new PantryItem(name, quantity, unit, expiry));
            Toast.makeText(this, "Ingredient added", Toast.LENGTH_SHORT).show();
        } else {

            editingItem.name = name;
            editingItem.quantity = quantity;
            editingItem.unit = unit;
            editingItem.expiryDate = expiry;
            db.pantryItemDAO().update(editingItem);
            Toast.makeText(this, "Ingredient updated", Toast.LENGTH_SHORT).show();
        }
        finish();
    }


    private void confirmDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Delete ingredient")
                .setMessage("Remove " + editingItem.name + " from your pantry?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // DELETE
                    db.pantryItemDAO().delete(editingItem);
                    Toast.makeText(this, "Ingredient deleted", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }


    private boolean isValidDate(String text) {
        if (!text.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        format.setLenient(false);
        try {
            format.parse(text);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


    private String formatQuantity(double q) {
        return (q == (long) q) ? String.valueOf((long) q) : String.valueOf(q);
    }
}