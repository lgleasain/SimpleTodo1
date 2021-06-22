package com.example.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  public static final String KEY_ITEM_TEXT = "KEY_ITEM_TEXT";
  public static final String KEY_ITEM_POSITION = "KEY_ITEM_POSITION";
  public static final int EDIT_RETURN_CODE = 20;

  List<String> items;

  Button btnAdd;
  EditText etItem;
  RecyclerView rvItems;
  ItemsAdapter itemsAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    btnAdd = findViewById(R.id.btnAdd);
    etItem = findViewById(R.id.edtItem);
    rvItems = findViewById(R.id.rvtiems);

    /*items = new ArrayList<>();
    items.add("buy milk");
    items.add("check flux capacitor");
    items.add("watch grand tour");*/
    loadItems();

    ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
      @Override
      public void onLongClickListener(int positon) {
        items.remove(positon);
        itemsAdapter.notifyItemRemoved(positon);
        saveItems();
      }
    };

    ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
      @Override
      public void onItemClicked(int position) {
        Log.i("MainActivity", "edit clicked at " + position);
        Log.i("MainActivity", "edit text " + items.get(position));

        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra(KEY_ITEM_POSITION, position);
        intent.putExtra(KEY_ITEM_TEXT, items.get(position));
        startActivityForResult(intent, EDIT_RETURN_CODE);
      }
    };

    itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListener);
    rvItems.setAdapter(itemsAdapter);
    rvItems.setLayoutManager(new LinearLayoutManager(this));

    btnAdd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String textToAdd = etItem.getText().toString();
        items.add(textToAdd);
        itemsAdapter.notifyItemInserted(items.size() - 1);
        Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
        saveItems();
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if(resultCode == RESULT_OK && requestCode == EDIT_RETURN_CODE){
      int itemPosition = data.getExtras().getInt(KEY_ITEM_POSITION);
      String itemText = data.getStringExtra(KEY_ITEM_TEXT);
      items.set(itemPosition, itemText);
      itemsAdapter.notifyItemChanged(itemPosition);
      saveItems();
      Toast.makeText(getApplicationContext(), "Item updated successfully", Toast.LENGTH_SHORT).show();
    } else {
      Log.w("MainActivity", "unknown call to onActivityResult");
    }

    super.onActivityResult(requestCode, resultCode, data);
  }

  private File getDataFile(){
    return new File(getFilesDir(), "data.txt");
  }

  private void loadItems(){
    try {
      items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
    } catch (IOException e) {
      Log.e("MainActvity", "Error reading items", e);
      items = new ArrayList<>();
    }
  }

  private void saveItems(){
    try {
      FileUtils.writeLines(getDataFile(), items);
    } catch (IOException e){
      Log.e("MainActivity", "Error writing items", e);
    }
  }
}