package com.example.seekproject.Fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seekproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityFragment extends Fragment implements View.OnClickListener {

    private Button btn_1, btn_2, btn_3, btn_4, btn_5, btn_6;
//    private ArrayList<Button> activityButtonList;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    public ActivityFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_activity, container, false);
        btn_1 = v.findViewById(R.id.btn_1);
        btn_2 = v.findViewById(R.id.btn_2);
        btn_3 = v.findViewById(R.id.btn_3);
        btn_4 = v.findViewById(R.id.btn_4);
        btn_5 = v.findViewById(R.id.btn_5);
        btn_6 = v.findViewById(R.id.btn_6);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
//        btnName = getResources().getStringArray(R.array.btnName);
//        activityButtonList = new ArrayList<>(Arrays.asList(btn_1, btn_2, btn_3, btn_4, btn_5, btn_6));
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
    }

    public void showDiaryPopup(final String type) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_diary);

        final EditText et_letter = dialog.findViewById(R.id.et_letter);
        TextView txtTitle = dialog.findViewById(R.id.txt_diaryTitle);
        txtTitle.setText(type);
        Button btn_letterSave = dialog.findViewById(R.id.btn_letterSave);

        if (mAuth.getCurrentUser() != null) {
            String userID = mAuth.getCurrentUser().getUid();
            final DocumentReference letterRef = db.collection("users").document(userID).collection("userNotes").document(type);

            letterRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    et_letter.setText(documentSnapshot.getString("diary"));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "You do not have any " + type + " diary", Toast.LENGTH_SHORT).show();
                }
            });

            btn_letterSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // save
                    String diary = et_letter.getText().toString();
                    if (mAuth.getCurrentUser() != null) {
                        // create hashmap
                        HashMap<String, Object> noteMap = new HashMap<>();
                        noteMap.put("diary", diary);

//                        letterRef.set(noteMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    Toast.makeText(getActivity(), "Diary is saved", Toast.LENGTH_SHORT).show();
//                                }
//                                else {
//                                    Toast.makeText(getActivity(), "Diary failed to be saved", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
                        letterRef.set(noteMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Diary is saved", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), e.getMessage() + "Diary failed to be saved", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
//                showLetterPopup();
                showDiaryPopup("letter");
                Toast.makeText(getActivity(), "Self Compassion Letter", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_2:
                showDiaryPopup("vacation");
                Toast.makeText(getActivity(), "Taking a Daily Vacation", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_3:
                showDiaryPopup("self");
                Toast.makeText(getActivity(), "The Best Possible Self", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_4:
                showDiaryPopup("strength");
                Toast.makeText(getActivity(), "The Strengths Wheel", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_5:
                showDiaryPopup("personality");
                Toast.makeText(getActivity(), "Personality Test", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_6:
                showDiaryPopup("positive");
                Toast.makeText(getActivity(), "Positive Psychology Techniques", Toast.LENGTH_SHORT).show();
                break;

        }
    }


}
