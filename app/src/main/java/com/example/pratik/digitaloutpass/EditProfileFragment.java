package com.example.pratik.digitaloutpass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    private static final int REQUEST_CAMERA = 3;
    private static final int SELECT_FILE = 2;

    EditText nameS;
    EditText phone;
    ImageView prof;
    Button save;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    DatabaseReference uref;
    StorageReference mStorageRef;

    //IMAGE HOLD URI
    Uri imageHoldUri = null;

    //PROGRESS DIALOG
    ProgressDialog mProgress;

    private OnFragmentInteractionListener mListener;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    public static EditProfileFragment newInstance() {
        EditProfileFragment fragment = new EditProfileFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //Logic check user
                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                }
            }
        };

        uref = FirebaseDatabase.getInstance().getReference().child("users");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        // Inflate the layout for this fragment
        nameS = v.findViewById(R.id.edit_name_stu);
        phone = v.findViewById(R.id.edit_phone_stu);
        prof =v.findViewById(R.id.profPicSet);
        save = v.findViewById(R.id.btn_save_stu);

        initialize();



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveUserProfile();

            }
        });

        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickProfilePic();
            }
        });


        return v;


    }

    private void initialize() {

    }

    private void saveUserProfile() {


        final String username;
        final long phoneno;

        username = nameS.getText().toString().trim();
        phoneno = Long.parseLong(phone.getText().toString());

        if( !TextUtils.isEmpty(username) && !TextUtils.isEmpty(String.valueOf(phoneno)))
        {

            if( imageHoldUri != null )
            {

                mProgress.setTitle("Saveing Profile");
                mProgress.setMessage("Please wait....");
                mProgress.show();

                StorageReference mChildStorage = mStorageRef.child("User_Profile").child(imageHoldUri.getLastPathSegment());
                String profilePicUrl = imageHoldUri.getLastPathSegment();

                mChildStorage.putFile(imageHoldUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        final String imageUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                        uref.child("name").setValue(username);
                        uref.child("phoneNo").setValue(phoneno);

                        uref.child("imageurl").setValue(imageUrl);

                        mProgress.dismiss();

                        MyOutpassesFragment myOutpassesFragment = MyOutpassesFragment.newInstance();
                        getActivity().getFragmentManager().popBackStack();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_main_relative, myOutpassesFragment, "findThisFragment")
                                .addToBackStack(null)
                                .commit();

                        //finish fragment


                    }
                });
            }else
            {

                Toast.makeText(getActivity(), "Please select the profile pic", Toast.LENGTH_LONG).show();

            }

        }else
        {

            Toast.makeText(getActivity(), "Please enter username and status", Toast.LENGTH_LONG).show();

        }



    }

    private void galleryIntent() {

        //CHOOSE IMAGE FROM GALLERY
        Log.d("gola", "entered here");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_FILE);
    }

    private void cameraIntent() {

        //CHOOSE CAMERA

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void pickProfilePic() {
        //DISPLAY DIALOG TO CHOOSE CAMERA OR GALLERY

        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");

        //SET ITEMS AND THERE LISTENERS
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
