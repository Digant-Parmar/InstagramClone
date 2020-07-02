package com.example.instagramclone;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SharePictureTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SharePictureTab extends Fragment implements View.OnClickListener {

    private ImageView imageView ;
    private EditText edtDiscription;
    private Button btnShare;
    Bitmap receivedImageBitmap;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SharePictureTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SharePictureTab.
     */
    // TODO: Rename and change types and number of parameters
    public static SharePictureTab newInstance(String param1, String param2) {
        SharePictureTab fragment = new SharePictureTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_share_picture_tab, container, false);

        imageView = view.findViewById(R.id.imageView);
        edtDiscription = view.findViewById(R.id.edtDiscription);
        btnShare = view.findViewById(R.id.btnShare);

        imageView.setOnClickListener(SharePictureTab.this);
        btnShare.setOnClickListener(SharePictureTab.this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.imageView:

                if(Build.VERSION.SDK_INT>=23 && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){

                    requestPermissions(new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE},1000);
                }else {
                    getChosenImage();
                }

                break;


            case R.id.btnShare:

                if(receivedImageBitmap !=null){
                    if(edtDiscription.getText().toString().equals("")){
                        FancyToast.makeText(getContext(),"Enter the Discription", Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                    }else {

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile("pic.png",bytes);
                        ParseObject parseObject = new ParseObject("Photo");
                        parseObject.put("picture",parseFile);
                        parseObject.put("image_des",edtDiscription.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());

                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null){
                                    FancyToast.makeText(getContext(),"Done!!!", Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();

                                }else {
                                    FancyToast.makeText(getContext(),"Unknown error: "+e.getMessage(), Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();

                                }
                            }
                        });


                    }

                }else {
                    FancyToast.makeText(getContext(),"Select an image", Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();

                }

                break;
        }

    }

    private void getChosenImage() {

       // FancyToast.makeText(getContext(),"Now we Can exess images", Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();

        Intent intent = new Intent(Intent.ACTION_PICK,
                                 MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1000){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getChosenImage();
            }
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2000){
            if(resultCode == Activity.RESULT_OK){

                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePathColumn,null,null,null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    receivedImageBitmap = BitmapFactory.decodeFile(picturePath);
                    FancyToast.makeText(getContext(),"Image came", Toast.LENGTH_SHORT, FancyToast.SUCCESS,true).show();

                    imageView.setImageBitmap(receivedImageBitmap);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }

    }




}