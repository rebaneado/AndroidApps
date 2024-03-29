package edu.uncc.assessment07.shopping;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import edu.uncc.assessment07.databinding.FragmentShoppingListBinding;
import edu.uncc.assessment07.databinding.ShoppingListItemRowItemBinding;
import edu.uncc.assessment07.models.ShoppingList;
import edu.uncc.assessment07.models.ShoppingListItem;

public class ShoppingListFragment extends Fragment {
    private static final String ARG_PARAM_SHOPPING_LIST = "ARG_PARAM_SHOPPING_LIST";
    private ShoppingList mShoppingList;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    public static ShoppingListFragment newInstance(ShoppingList shoppingList) {
        ShoppingListFragment fragment = new ShoppingListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_SHOPPING_LIST, shoppingList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mShoppingList = (ShoppingList) getArguments().getSerializable(ARG_PARAM_SHOPPING_LIST);
        }
    }

    FragmentShoppingListBinding binding;
    ArrayList<ShoppingListItem> shoppingListItems = new ArrayList<>();
    ShoppingListAdapter adapter;
    ListenerRegistration listenerRegistration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShoppingListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Shopping List");

        binding.textViewListName.setText(mShoppingList.getName());


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ShoppingListAdapter();
        binding.recyclerView.setAdapter(adapter);

        binding.buttonAddNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddListItem(mShoppingList);
            }
        });
        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goBackToShoppingLists();
            }
        });

        //TODO: setup a snapshot listener to get the shopping list items
        //!this is the todo


        listenerRegistration = db.collection("ShoppingList").document(mShoppingList.getDocId())
                .collection("ShoppingListItem").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    error.printStackTrace();
                    return;
                }
                shoppingListItems.clear();
                for (QueryDocumentSnapshot doc: value) {
                    ShoppingListItem shoppingListItem = doc.toObject(ShoppingListItem.class);
                    shoppingListItems.add(shoppingListItem);
                }

                //binding.textViewCommentsCount.setText(mComments.size() + " Comments");

                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listenerRegistration != null) {
            listenerRegistration.remove();
            listenerRegistration = null;
        }
    }

    class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>{

        @NonNull
        @Override
        public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ShoppingListItemRowItemBinding itemBinding = ShoppingListItemRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new ShoppingListViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
            ShoppingListItem shoppingListItem = shoppingListItems.get(position);
            holder.setupUI(shoppingListItem);
        }

        @Override
        public int getItemCount() {
            return shoppingListItems.size();
        }

        class ShoppingListViewHolder extends RecyclerView.ViewHolder{
            ShoppingListItemRowItemBinding mBinding;
            ShoppingListItem mShoppingListItem;
            public ShoppingListViewHolder(ShoppingListItemRowItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.mBinding = itemBinding;
            }

            public void setupUI(ShoppingListItem shoppingListItem){
                mShoppingListItem = shoppingListItem;
                mBinding.textViewItemName.setText(mShoppingListItem.getName());
                mBinding.textViewQuantity.setText(String.valueOf(mShoppingListItem.getQuantity()));

                mBinding.imageViewAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.collection("ShoppingListItem").document(mShoppingListItem.getDocId()).update("quantity", FieldValue.increment(1));
                        HashMap<String, Object> updateData = new HashMap<>();

                        updateData.put("quantity", FieldValue.arrayRemove(mShoppingListItem.quantity + 1));
                        //FirebaseFirestore.getInstance().collection("ShoppingList").document(mShoppingListItem.getDocId()).update(updateData);
                        FirebaseFirestore.getInstance().collection("ShoppingList").document(mShoppingList.getDocId()).collection("ShoppingListItem").document().update(updateData);

                    }
                });

                mBinding.imageViewMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }
    }


    ShoppingListListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ShoppingListListener) context;
    }

    public interface ShoppingListListener{
        void gotoAddListItem(ShoppingList shoppingList);
        void goBackToShoppingLists();
    }
}