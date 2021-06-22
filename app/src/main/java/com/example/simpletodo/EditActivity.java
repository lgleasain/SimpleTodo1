package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

  EditText editText;
  Button saveButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit);

    editText = findViewById(R.id.editText);
    saveButton = findViewById(R.id.saveButton);

    getSupportActionBar().setTitle("Edit item");

    Log.i("Edit Activity", getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));
    editText.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent returnIntent = new Intent();

        returnIntent.putExtra(MainActivity.KEY_ITEM_TEXT, editText.getText().toString());
        returnIntent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));

        setResult(RESULT_OK, returnIntent);

        finish();
      }
    });
  }
}