package com.softsquared.android.superchallange2020.src.verify;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.softsquared.android.superchallange2020.R;
import com.softsquared.android.superchallange2020.src.BaseActivity;
import com.softsquared.android.superchallange2020.src.curation.CurationActivity;
import com.softsquared.android.superchallange2020.src.verify.interfaces.VerifyActivityView;
import static com.softsquared.android.superchallange2020.src.ApplicationClass.sSharedPreferences;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class VerifyActivity extends BaseActivity implements VerifyActivityView {

    private ImageView mImageViewCover;
    private String closure = "Y";
    private int scrapNo;
    private String title;
    Context mContext;
    Intent mIntent;
    private String downloadUri;
    private String mId;

    private static final int PICK_FROM_CAMERA = 1; //카메라 촬영으로 사진 가져오기
    private static final int PICK_FROM_ALBUM = 2; //앨범에서 사진 가져오기
    private static final int CROP_FROM_CAMERA = 3; //가져온 사진을 자르기 위한 변수

    private static final int BEFORE_IMAGE = 4; //가져온 사진을 자르기 위한 변수
    private static final int AFTER_IMAGE = 5; //가져온 사진을 자르기 위한 변수
    private static final int AFTER_SEVER_UPLOAD = 6; //가져온 사진을 자르기 위한 변수

    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}; //권한 설정 변수
    private static final int MULTIPLE_PERMISSIONS = 101; //권한 동의 여부 문의 후 CallBack 함수에 쓰일 변수
    private Uri photoUri;

    int activityMode;
    public static final int SCRAP_WRITE_MODE = 10; //가져온 사진을 자르기 위한 변수
    public static final int SCRAP_EDIT_MODE = 11; //가져온 사진을 자르기 위한 변수
    private boolean isPermitedAll = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        mContext = this;
        initView();
        checkPermissions();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkPermissionNoAskAgain();
        isPermitedAll = checkPermissions();
    }

    private boolean checkPermissions() {
        int result;
        List<String> permissionList = new ArrayList<>();
        for (String pm : permissions) {
            result = ContextCompat.checkSelfPermission(this, pm);
            if (result != PackageManager.PERMISSION_GRANTED) { //사용자가 해당 권한을 가지고 있지 않을 경우 리스트에 해당 권한명 추가
                permissionList.add(pm);
            }
        }
        if (!permissionList.isEmpty()) { //권한이 추가되었으면 해당 리스트가 empty가 아니므로 request 즉 권한을 요청합니다.
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    void checkPermissionNoAskAgain() {
        int result;
        for (String pm : permissions) {
            result = ContextCompat.checkSelfPermission(this, pm);
            if (result != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, pm)) {
                    showCustomToast("글라이드 바로 연결");
                    return;
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (permissions[i].equals(this.permissions[0])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                isPermitedAll = false;
                            }
                        } else if (permissions[i].equals(this.permissions[1])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                isPermitedAll = false;
                            }
                        } else if (permissions[i].equals(this.permissions[2])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                isPermitedAll = false;
                            }
                        } else if (permissions[i].equals(this.permissions[3])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                isPermitedAll = false;
                            }
                        } else if (permissions[i].equals(this.permissions[4])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                isPermitedAll = false;
                            }
                        }
                    }
                }
                if (!isPermitedAll) {
                    showCustomToast("권한을 허용하셔야 이용이 가능합니다");
                    finish();
                }
                return;
            }
        }
    }

    void initView() {
        mImageViewCover = findViewById(R.id.iv_verify);
        mImageViewCover.setClipToOutline(true);
        mIntent = getIntent();
    }

    private void goToAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK); //ACTION_PICK 즉 사진을 고르겠다!
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "IP" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }


    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //사진을 찍기 위하여 설정합니다.
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            showCustomToast(mContext.getString(R.string.pregnant_certification_image_error));
            finish();
        }
        if (photoFile != null) {
            photoUri = FileProvider.getUriForFile(mContext,
                    "com.softsquared.android.superchallange2020.provider", photoFile); //FileProvider의 경우 이전 포스트를 참고하세요.
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); //사진을 찍어 해당 Content uri를 photoUri에 적용시키기 위함
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            showCustomToast("이미지 처리 오류! 다시 시도해주세요.");
            return;
        }
        if (requestCode == PICK_FROM_ALBUM) {
            if (data == null) {
                return;
            }
            photoUri = data.getData();
            cropImage();
        } else if (requestCode == PICK_FROM_CAMERA) {
            cropImage();
            MediaScannerConnection.scanFile(mContext, //앨범에 사진을 보여주기 위해 Scan을 합니다.
                    new String[]{photoUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
        } else if (requestCode == CROP_FROM_CAMERA) {
            try { //저는 bitmap 형태의 이미지로 가져오기 위해 아래와 같이 작업하였으며 Thumbnail을 추출하였습니다.

//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
//                Bitmap thumbImage = ThumbnailUtils.extractThumbnail(bitmap, 128, 128);
//                ByteArrayOutputStream bs = new ByteArrayOutputStream();
//                thumbImage.compress(Bitmap.CompressFormat.JPEG, 100, bs); //이미지가 클 경우 OutOfMemoryException 발생이 예상되어 압축
//
//                mImageViewCover.setImageBitmap(thumbImage);
                uploadFileToFireBase();

            } catch (Exception e) {
            }
        }
    }

    //모든 작업에 있어 사전에 FALG_GRANT_WRITE_URI_PERMISSION과 READ 퍼미션을 줘야 uri를 활용한 작업에 지장을 받지 않는다는 것이 핵심입니다.
    public void cropImage() {
        this.grantUriPermission("com.android.camera", photoUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");

        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
        grantUriPermission(list.get(0).activityInfo.packageName, photoUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        int size = list.size();
        if (size == 0) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(this, "용량이 큰 사진의 경우 시간이 오래 걸릴 수 있습니다.", Toast.LENGTH_SHORT).show();
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            File croppedFileName = null;
            try {
                croppedFileName = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            File folder = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File tempFile = new File(folder.toString(), croppedFileName.getName());

            photoUri = FileProvider.getUriForFile(mContext,
                    "com.softsquared.android.superchallange2020.provider", tempFile);

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString()); //Bitmap 형태로 받기 위해 해당 작업 진행

            Intent i = new Intent(intent);
            ResolveInfo res = list.get(0);
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            grantUriPermission(res.activityInfo.packageName, photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            startActivityForResult(i, CROP_FROM_CAMERA);

        }

    }

    void imageUploadChoiceDialog() {
        DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                takePhoto();
            }
        };
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goToAlbum();
            }
        };
        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        new AlertDialog.Builder(mContext)
                .setTitle("업로드할 이미지 선택")
                .setPositiveButton("사진촬영", cameraListener)
                .setNeutralButton("앨범선택", albumListener)
                .setNegativeButton("취소", cancelListener)
                .show();
    }

    private void uploadFileToFireBase() {
        final VerifyService verifyService = new VerifyService(this);
        verifyService.uploadFileToFireBase(photoUri);
    }

    public void customOnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_verify:
                imageUploadChoiceDialog();
                break;
            case R.id.iv_verify_close:
                finish();
                break;
            case R.id.tv_verify_btn:
                String name = mIntent.getExtras().getString("name");
                String id = mIntent.getExtras().getString("id");
                String password = mIntent.getExtras().getString("password");
                Log.d("로그", "name: " + name + " id : " + id + " password: " + password + " uri: " + downloadUri);
                mId = id;
                JSONObject params = new JSONObject();
                try {
                    params.put("id", id);
                    params.put("password", password);
                    params.put("name", name);
                    params.put("url", downloadUri);
                } catch (Exception e) {
                    //   Log.d(TAG, "error: " + e);
                    return;
                }
                showProgressDialog();
                final VerifyService verifyService = new VerifyService(this, params);
                verifyService.signUp();
                break;
            default:
                break;
        }
    }

    @Override
    public void validateSuccess(String downloadUri) {
        hideProgressDialog();
//        setResult(RESULT_OK);
        this.downloadUri = downloadUri;
        RequestOptions sharedOptions2 =
                new RequestOptions()
                        .override(600, 600)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .centerCrop();
        Glide.with(this).load(downloadUri).apply(sharedOptions2).into(mImageViewCover);
    }

    @Override
    public void validateFailure(String message) {
        hideProgressDialog();
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }

    @Override
    public void signUpSuccess(String message) {
        Log.d("로그", "회원가입 성공");
        hideProgressDialog();
        showCustomToast("회원가입 성공");
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        Log.d("로그", mId);
        editor.putString("id", mId);
        editor.apply();
        Intent intent = new Intent(mContext, CurationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void signUpFailure(String message) {
        hideProgressDialog();
        showCustomToast("회원가입 실패");
    }

}
