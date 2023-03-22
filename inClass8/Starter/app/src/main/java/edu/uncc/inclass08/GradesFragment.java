package edu.uncc.inclass08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;

import edu.uncc.inclass08.databinding.FragmentGradesBinding;
import edu.uncc.inclass08.databinding.GradeRowItemBinding;

public class GradesFragment extends Fragment {
    public GradesFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentGradesBinding binding;
    GradeAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGradesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    ListenerRegistration listenerRegistration;
    ArrayList<Grade> mGrades = new ArrayList<>();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth != null) {
            getActivity().setTitle("welcome: " + mAuth.getCurrentUser().getDisplayName());

        } else {
            //todo: this is if there is no curernt instance i can probably puyt something like this in the main.. ithink that is what is on the class activity 7

        }
        binding.buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoAddCourse();
            }
        });

        //!here below is what i have coded myself to get the Snapshot listener
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        listenerRegistration = db.collection("grades").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("demo", "onEvent: There is a error for the onevent listener for snapshot listener");
                }
                mGrades.clear();
                for (QueryDocumentSnapshot document : value) {
                    Grade grade = document.toObject(Grade.class);
                    mGrades.add(grade);
                }
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new GradeAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
    }

    class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder>{
        @NonNull
        @Override
        public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            GradeRowItemBinding rowBinding = GradeRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new GradeViewHolder(rowBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
            Grade grade = mGrades.get(position);
            holder.setupUI(grade);
        }

        @Override
        public int getItemCount() {
            return mGrades.size();
        }

        class GradeViewHolder extends RecyclerView.ViewHolder{
            GradeRowItemBinding mBinding;
            Grade mGrade;
            public GradeViewHolder(@NonNull GradeRowItemBinding rowBinding) {
                super(rowBinding.getRoot());
                this.mBinding = rowBinding;
            }

            public void setupUI(Grade grade){
                //! idk why there is a 'THIS' below
                //this.mGrade = grade;
                this.mGrade = grade;
                mBinding.textViewCourseNumber.setText(this.mGrade.getCourseNumber());
                mBinding.textViewCourseLetterGrade.setText(this.mGrade.getCourseGrade());
                mBinding.textViewCourseName.setText(this.mGrade.getCourseName());
                mBinding.textViewCourseHours.setText(this.mGrade.getCreditHours());


               // mBinding.textViewCourseHours.setText(mGrade.getCreditHours());
                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                if (mAuth.getCurrentUser().getUid().equals(grade.getStudentID())) {
                    mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("grades").document(mGrade.getPost_id()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    //removed this to show the snapshot listener
                                    //getPosts();
                                }
                            });

                        }
                    });

                    mBinding.imageViewDelete.setVisibility(View.VISIBLE);

                } else {
                    mBinding.imageViewDelete.setVisibility(View.INVISIBLE);

                }
            }
        }
    }

    GradesListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof GradesListener) {
            mListener = (GradesListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement GradesListener");
        }
    }

    interface GradesListener{
        void gotoAddCourse();
    }
}