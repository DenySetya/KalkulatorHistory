package com.example.kalkulatorDeny;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public ArrayAdapter<String> historyAdapter;
    public FirebaseFirestore db;
    public ArrayList<String> itemList;
    public ListView lv;
    //RecyclerView recyclerView;
    //AdapterHistory adapterHistory;
    //List<History> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.historyList);
        itemList = new ArrayList<>();
        historyAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, itemList);

        db = FirebaseFirestore.getInstance();
    }

    public void hitung(View view) {
        EditText firstNumberInput = findViewById(R.id.angka1);
        EditText secondNumberInput = findViewById(R.id.angka2);
        float angkaSatu = Float.parseFloat(firstNumberInput.getText().toString());
        float angkaDua = Float.parseFloat(secondNumberInput.getText().toString());
        RadioButton add = findViewById(R.id.tambahBut);
        RadioButton min = findViewById(R.id.kurangBut);
        RadioButton dob = findViewById(R.id.kaliBut);
        RadioButton div = findViewById(R.id.bagiBut);

        float result = 0;

        if (add.isChecked()){
            result = angkaSatu + angkaDua;
        } else if (min.isChecked()){
            result = angkaSatu - angkaDua;
        } else if (dob.isChecked()){
            result = angkaSatu * angkaDua;
        } else if (div.isChecked()){
            result = angkaSatu / angkaDua;
        }

        TextView hasilField = findViewById(R.id.resultField);
        hasilField.setText(" " + result);
        Map<String, Object> his = new HashMap<>();

        if (add.isChecked()){
            his.put("h"," "+angkaSatu+" + "+angkaDua+" = "+result);
        } else if (min.isChecked()){
            his.put("h"," "+angkaSatu+" - "+angkaDua+" = "+result);
        } else if (dob.isChecked()){
            his.put("h"," "+angkaSatu+" x "+angkaDua+" = "+result);
        } else if (div.isChecked()){
            his.put("h"," "+angkaSatu+" / "+angkaDua+" = "+result);
        }

        db.collection("data_his")
                .add(his)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getBaseContext(),
                                "Input Data Sukses",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("firebase-error", e.getMessage());
                    }
                });

        db.collection("data_his")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String teks = "";
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot doc: task.getResult()){
                                teks += doc.getData().get("h")+"\n";
                            }
                            itemList.add(teks);
                            lv.setAdapter(historyAdapter);
                        }else {

                        }
                    }
                });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int which_item = position;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are You Sure ?")
                        .setMessage("Do you want to delete this item")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                itemList.remove(which_item);
                                historyAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            }
        });

//        if (add.isChecked()){
//            historyAdapter.add(
//                    " "+angkaSatu+" + "+angkaDua+" = "+result
//            );
//        } else if (min.isChecked()){
//            historyAdapter.add(
//                    " "+angkaSatu+" - "+angkaDua+" = "+result
//            );
//        } else if (dob.isChecked()){
//            historyAdapter.add(
//                    " "+angkaSatu+" x "+angkaDua+" = "+result
//            );
//        } else if (div.isChecked()){
//            historyAdapter.add(
//                    " "+angkaSatu+" / "+angkaDua+" = "+result
//            );
//        }
//        adapterHistory = new AdapterHistory(this,historyList);
//        recyclerView.setAdapter(adapterHistory);

    }
}
