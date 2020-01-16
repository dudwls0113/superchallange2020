package com.softsquared.android.superchallange2020.src.verify;

import android.app.ProgressDialog;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.softsquared.android.superchallange2020.src.verify.interfaces.VerifyActivityView;
import com.softsquared.android.superchallange2020.src.verify.interfaces.VerifyRetrofitInterface;
import com.softsquared.android.superchallange2020.src.verify.model.SignUpResponse;

import org.json.JSONObject;

import java.util.Date;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.softsquared.android.superchallange2020.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.softsquared.android.superchallange2020.src.ApplicationClass.getRetrofit;

class VerifyService {
    private final VerifyActivityView mVerifyActivityView;
    private JSONObject mParams;

    VerifyService(final VerifyActivityView verifyActivityView, JSONObject params) {
        this.mVerifyActivityView = verifyActivityView;
        this.mParams = params;
    }

    VerifyService(final VerifyActivityView verifyActivityView) {
        this.mVerifyActivityView = verifyActivityView;
    }

    void signUp() {
        final VerifyRetrofitInterface verifyRetrofitInterface = getRetrofit().create(VerifyRetrofitInterface.class);
        verifyRetrofitInterface.postSignUp(RequestBody.create(MEDIA_TYPE_JSON, mParams.toString())).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                final SignUpResponse signUpResponse = response.body();
                if (signUpResponse == null) {
                    mVerifyActivityView.signUpFailure(null);
                    return;
                }
                mVerifyActivityView.signUpSuccess(signUpResponse.getMessage());
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                mVerifyActivityView.signUpFailure(null);
            }
        });
    }

    void uploadFileToFireBase(Uri mImageUri) {
        final ProgressDialog progressDialog = new ProgressDialog((VerifyActivity)mVerifyActivityView);
        progressDialog.setTitle("업로드중...");
        progressDialog.show();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        Date now = new Date();
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://superchallange2020.appspot.com/").child("images/" + mImageUri.getLastPathSegment());
        UploadTask uploadTask = storageRef.putFile(mImageUri);
        storageRef.putFile(mImageUri)
                //성공시
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                    }
                })
                //실패시
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        mVerifyActivityView.validateFailure("업로드 실패");
                    }
                })
                //진행중
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        @SuppressWarnings("VisibleForTests")
                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                    }
                });

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return storageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();  // downloadUri -> 이게 업로드 완료된  url임
                    progressDialog.dismiss();
                    mVerifyActivityView.validateSuccess(downloadUri.toString());

                } else {
                    //실패시
                }
            }
        });
    }
}