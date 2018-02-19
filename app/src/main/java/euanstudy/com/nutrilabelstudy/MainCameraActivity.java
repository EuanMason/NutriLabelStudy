package euanstudy.com.nutrilabelstudy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainCameraActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    Uri capturedImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_camera);
        takePicture();


    }

    private void takePicture(){
        Intent captureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);

        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "output2.jpeg");



        Uri capturedImageUri = Uri.parse("file://"+file.getPath());

        Uri photoURI = FileProvider.getUriForFile(MainCameraActivity.this,
                BuildConfig.APPLICATION_ID + ".fileprovider",
                file);

        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        Log.e("Camera", "starting first camera");
        // we will handle the returned data in onActivityResult
        startActivityForResult(captureIntent, REQUEST_IMAGE_CAPTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Class cls;

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
//                ((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());
                try {
                    saveImageToExternalStorage(result.getUri());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(
                        this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG)
                        .show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        } else {
            crop();

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String value = extras.getString("nextClass");
                if (value == "OverlayCameraActivity") {
                    cls = OverlayCameraActivity.class;
                } else {
                    cls = ProcessData.class;
                }
//                startActivity(new Intent(getApplicationContext(), cls));
            }
        }

    }

    private void crop(){
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "output2.jpeg");

        Uri uri = Uri.parse("file://" + file.getPath());


        Log.e("Camera", "starting crop activity");

        CropImage.activity(uri)
                .start(this);
    }

    public void saveImageToExternalStorage(Uri uriImage) throws IOException {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "output4.png");
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriImage);
        try
        {
//            File dir = new File(fullPath);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
            OutputStream fOut = null;
//            File file = new File(fullPath, "image.png");
            if(file.exists())
                file.delete();
            file.createNewFile();
            fOut = new FileOutputStream(file);
            // 100 means no compression, the lower you go, the stronger the compression
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        }
        catch (Exception e)
        {
            Log.e("saveToExternalStorage()", e.getMessage());
        }
    }





}
