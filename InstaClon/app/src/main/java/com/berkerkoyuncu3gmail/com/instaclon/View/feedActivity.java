package com.berkerkoyuncu3gmail.com.instaclon.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.berkerkoyuncu3gmail.com.instaclon.Adapter.PostAdapter;
import com.berkerkoyuncu3gmail.com.instaclon.Model.Posts;
import com.berkerkoyuncu3gmail.com.instaclon.R;
import com.berkerkoyuncu3gmail.com.instaclon.databinding.ActivityFeedBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class feedActivity extends AppCompatActivity {
    private ActivityFeedBinding binding;
    ArrayList<Posts> postsArrayList ;
    FirebaseFirestore firebaseFirestore;

    PostAdapter adapter;

   private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        postsArrayList = new ArrayList<>();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PostAdapter(postsArrayList);
        binding.recyclerView.setAdapter(adapter);
        getData();
    }

    @Override
   public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        return super.onCreateOptionsMenu(menu);

   }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.addPost){
            // upload activity
            Intent intent = new Intent(feedActivity.this, SharedActivity.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.signOut){
            // SignOut

            auth.signOut();

            Intent intent = new Intent(feedActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, "Failed Transaction", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getData(){
        firebaseFirestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
           if(error != null){
               Toast.makeText(feedActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
           }

           if(value != null){

               for (DocumentSnapshot documentSnapshot : value.getDocuments()){

                   Map<String , Object> data = documentSnapshot.getData();

                   //Casting
                   String email = (String) data.get("useremail");
                   String comment = (String) data.get("comment");
                   String downloadurl = (String) data.get("downloadurl");



                   Posts post = new Posts(email,comment,downloadurl);
                   postsArrayList.add(post);

                   System.out.println(comment);

               }

               adapter.notifyDataSetChanged();

           }
            }
        });
    }
}