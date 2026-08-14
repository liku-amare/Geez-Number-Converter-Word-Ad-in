package com.likuamare.geeznumerals;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public final class MainActivity extends Activity {
    private EditText decimalInput;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        decimalInput = findViewById(R.id.decimal_input);
        resultText = findViewById(R.id.result_text);
        Button convertButton = findViewById(R.id.convert_button);
        Button copyButton = findViewById(R.id.copy_button);
        Button clearButton = findViewById(R.id.clear_button);
        Button aboutButton = findViewById(R.id.about_button);

        convertButton.setOnClickListener(view -> convert());
        copyButton.setOnClickListener(view -> copyResult());
        clearButton.setOnClickListener(view -> clear());
        aboutButton.setOnClickListener(view -> showAbout());

        decimalInput.setOnEditorActionListener((view, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                convert();
                return true;
            }
            return false;
        });
    }

    private void convert() {
        decimalInput.setError(null);
        try {
            String converted = EthiopicNumeralConverter.fromDecimal(decimalInput.getText().toString());
            resultText.setText(converted);
        } catch (IllegalArgumentException exception) {
            resultText.setText(R.string.empty_result);
            decimalInput.setError(exception.getMessage());
            decimalInput.requestFocus();
        }
    }

    private void copyResult() {
        String result = resultText.getText().toString();
        if (TextUtils.isEmpty(result) || getString(R.string.empty_result).equals(result)) {
            Toast.makeText(this, R.string.convert_before_copying, Toast.LENGTH_SHORT).show();
            return;
        }

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText(getString(R.string.clipboard_label), result));
        Toast.makeText(this, R.string.copied, Toast.LENGTH_SHORT).show();
    }

    private void clear() {
        decimalInput.setText("");
        decimalInput.setError(null);
        resultText.setText(R.string.empty_result);
        decimalInput.requestFocus();
    }

    private void showAbout() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.about_title)
                .setMessage(R.string.about_and_privacy)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
