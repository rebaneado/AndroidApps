package edu.uncc.assessment06;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.uncc.assessment06.databinding.CartRowItemBinding;
import edu.uncc.assessment06.databinding.FragmentCartBinding;

public class CartFragment extends Fragment {

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentCartBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ListenerRegistration listenerRegistration;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("My Cart: ");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productsAdaper = new ProductsAdapter();
        binding.recyclerView.setAdapter(productsAdaper);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        listenerRegistration = db.collection("products").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                mProducts.clear();

                for (QueryDocumentSnapshot document : value) {
                    Product product = document.toObject(Product.class);
                    mProducts.add(product);
                }
            }
        });

//        listenerRegistration = db.collection("products").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                if (error != null) {
//                    Log.w("demo", "listen Failed. ", error);
//                    return;
//                }
//
//                mProducts.clear();
//                for (QueryDocumentSnapshot document : value) {
//                    Product product = document.toObject(Product.class);
//                    mProducts.add(product);
//                }
//                productsAdaper.notifyDataSetChanged();
//            }
//        });


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("demo", "onDestroy has been ran");
        if (listenerRegistration != null) {
            listenerRegistration.remove();

        }
    }
    ProductsAdapter productsAdaper;
    ArrayList<Product> mProducts = new ArrayList<>();

    class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {
        @NonNull
        @Override
        public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CartRowItemBinding binding = CartRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new ProductsViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
            Product product = mProducts.get(position);
            holder.setupUI(product);
        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }

        class ProductsViewHolder extends RecyclerView.ViewHolder {
            CartRowItemBinding mBinding;
            Product mProduct;
            public ProductsViewHolder(CartRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(Product product){
                //!below is my code
                mProduct = product;
                mBinding.textViewProductName.setText(product.getName());
                mBinding.textViewProductPrice.setText(product.getPrice());

                if (mAuth.getCurrentUser().getUid().equals(product.getCustomerID())) {
                    mBinding.imageViewDeleteFromCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("products").document(mProduct.getDocumentID()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    //removed this to show the snapshot listener
                                    //getPosts();

                                }
                            });

                        }
                    });
                    mBinding.imageViewProductIcon.setVisibility(View.VISIBLE);

                } else {
                    //mBinding.imageViewDelete.setVisibility(View.INVISIBLE);

                }

            }
        }

    }

}