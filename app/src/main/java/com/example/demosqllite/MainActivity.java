package com.example.demosqllite;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    EditText editWord, editWordMean, editID;
    Button btnXoaHetDL, btnThemTu, btnXoaTu, btnLayDL, btnSuaTu;
    ImageView imgClose;
    ListView lvDsTu;
    AdapterWorld adapter = null;
    List<World> dsTu = new ArrayList<>();

    MyDatabaseHelper db = null;
    World worldSelected = new World();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFormWidget();

        db = MyDatabaseHelper.getInstance(this);

        if (db.getTotalWorld() == 0) fakeData();

        refreshDataForListView();

        Toast.makeText(MainActivity.this, "so bg:" + dsTu.size(),
                Toast.LENGTH_LONG).show();

        btnXoaHetDL.setOnClickListener(MainActivity.this);
        btnThemTu.setOnClickListener(MainActivity.this);
        btnXoaTu.setOnClickListener(MainActivity.this);
        btnLayDL.setOnClickListener(MainActivity.this);
        btnSuaTu.setOnClickListener(MainActivity.this);
        imgClose.setOnClickListener(MainActivity.this);

        lvDsTu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                worldSelected = dsTu.get(position);
                editID.setText(worldSelected.getmID() + "");
                editWord.setText(worldSelected.getmWorld());
                editWordMean.setText(worldSelected.getmMean());
            }
        });
    }

    private void refreshDataForListView() {
        try {
            dsTu = db.getAllWorld();
            adapter = new AdapterWorld(
                    this,
                    R.layout.item_listview,
                    dsTu);
            lvDsTu.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(TAG, "refreshDataForListView: " + e.getMessage());
        }
    }

    @SuppressLint("WrongViewCast")
    private void getFormWidget() {
        editID = findViewById(R.id.etID);
        editWord = findViewById(R.id.etWord);
        editWordMean = findViewById(R.id.etMean);
        btnThemTu = findViewById(R.id.btnThem);
        btnLayDL = findViewById(R.id.btnLayAllDL);
        btnXoaTu = findViewById(R.id.btnXoa);
        btnSuaTu = findViewById(R.id.btnSua);
        btnXoaHetDL = findViewById(R.id.btnXoaAllDL);
        imgClose = findViewById(R.id.imgClose);
        lvDsTu = findViewById(R.id.listView);
        //tự sinh mã số
        editID.setEnabled(false);
    }

    public void fakeData() {
        db.deleteAllWorld();
        db.insertWorld(new World("book", "Sách(n), đặt chỗ(v)"));
        db.insertWorld(new World("table", "bàn(n)"));
        db.insertWorld(new World("action movie", "Phim hành động "));
    }

    @Override
    public void onClick(View view) {
        int idView = view.getId();
        if (R.id.btnXoaAllDL == idView) {
            db.deleteAllWorld();
            refreshDataForListView();
        } else if (R.id.btnThem == idView) them();
        else if (R.id.btnXoa == idView) xoaTu();
        else if (R.id.btnLayAllDL == idView) refreshDataForListView();
        else if (R.id.btnSua == idView) capNhatTu();
        else if (R.id.imgClose == idView) finish();
    }

    private void capNhatTu() {
        World w = worldSelected;
        w.setmWorld(editWord.getText().toString());
        w.setmMean(editWordMean.getText().toString());
        db.updateWorld(w);
        Toast.makeText(this, " sửa : "+w.toString(), Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }

    private void xoaTu() {
        db.deleteWorld(worldSelected);
        refreshDataForListView();
    }

    private void them() {
        World world = new World();
        world.setmWorld(editWord.getText().toString());
        world.setmMean(editWordMean.getText().toString());

        boolean t = db.insertWorld(world);
        Toast.makeText(this, " chèn dòng + " + t + ":" + world.toString(),
                Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }
}




