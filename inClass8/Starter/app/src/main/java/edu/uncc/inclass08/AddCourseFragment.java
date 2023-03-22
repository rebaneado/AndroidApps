package edu.uncc.inclass08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;

import edu.uncc.inclass08.databinding.FragmentAddCourseBinding;

public class AddCourseFragment extends Fragment {
    public AddCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentAddCourseBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Course");
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goBackToGrades();
            }
        });
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = binding.radioGroupGrades.getCheckedRadioButtonId();
                String courseName = binding.editTextCourseName.getText().toString();
                String courseNumber = binding.editTextCourseNumber.getText().toString();
                String creditHours = binding.editTextCourseHours.getText().toString();
//todo: the declaration below is something i am going to have to deal with soon


                //double courseHours = Double.parseDouble(binding.editTextCourseHours.getText().toString());
                //String stringCourseHours = String.valueOf(courseHours);

                if(courseName.isEmpty() || courseNumber.isEmpty() || binding.editTextCourseHours.getText().toString().isEmpty()) {
                   Toast.makeText(getContext(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else if(selectedId == -1){
                    Toast.makeText(getContext(), "Please select a letter grade !!", Toast.LENGTH_SHORT).show();
                } else {
                    String courseLetterGrade;
                    if(selectedId == R.id.radioButtonA) {
                        courseLetterGrade = "A";
                    } else if(selectedId == R.id.radioButtonB) {
                        courseLetterGrade = "B";
                    } else if(selectedId == R.id.radioButtonC) {
                        courseLetterGrade = "C";
                    } else if(selectedId == R.id.radioButtonD) {
                        courseLetterGrade = "D";
                    } else {
                        courseLetterGrade = "F";
                    }



                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference documentReference = db.collection("grades").document();

                    HashMap<String, Object> data = new HashMap<>();

                    data.put("Post_id", documentReference.getId());
                    data.put("courseGrade", courseLetterGrade);
                    data.put("courseNumber", courseNumber);
                    data.put("courseName", courseName);
                    data.put("courseHours", creditHours);
                    data.put("studentID", mAuth.getCurrentUser().getUid());

                    documentReference.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mListener.goBackToGrades();
                            } else {
                                //todo: create a error message for the else
                            }
                        }
                    });





                }

//!here is where i implement my code
                FirebaseFirestore db = FirebaseFirestore.getInstance().getInstance();
                db.collection("grades").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                        } else {

                        }
                    }
                });

            }
        });

    }

    AddCourseListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddCourseListener) context;
    }

    interface AddCourseListener {
        void goBackToGrades();

    }
}