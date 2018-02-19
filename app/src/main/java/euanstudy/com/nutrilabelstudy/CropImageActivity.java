package euanstudy.com.nutrilabelstudy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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

public class CropImageActivity extends AppCompatActivity {
    Uri mCropImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);






            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "output2.jpeg");
            Log.d("File ", "File path = " + file.toString());
            File directory = new File(String.valueOf(getExternalFilesDir(Environment.DIRECTORY_PICTURES)));
            File[] files = directory.listFiles();
            Log.d("Files", "Size: "+ files.length);
            for (int i = 0; i < files.length; i++)
            {
                Log.d("Files", "FileName:" + files[i].getName() + ". Path = " + files[i].getPath());
            }


//        Uri uri = Uri.fromFile(file);
            Uri uri = Uri.parse("file://" + file.getPath());

            if (uri !=null){
                Log.d("Uri", "uri = " + uri.toString());
            }
            //((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageURI(uri);





            CropImage.activity(uri)
                    .start(this);
//        CropImage.activity(uri)
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .setActivityTitle("My Crop")
//                .setCropShape(CropImageView.CropShape.RECTANGLE)
//                .setCropMenuCropButtonTitle("Done")
//                .setRequestedSize(400, 400)
//                .setCropMenuCropButtonIcon(R.drawable.ic_launcher)
//                .start(this);


            // Bitmap bitmap = BitmapFactory.decodeFile(path);
//        Uri uri = Uri.parse("C:\\Users\\Euan\\Documents\\NutriLabel\\app\\src\\main\\res\\drawable\\test.png");
//        CropImageView cropImageView = (CropImageView) findViewById(R.id.cropImageView);
//        onSelectImageClick();
            //startCropImageActivity(uri);

    }


        public void onSelectImageClick(View view) {

        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            // handle result of CropImageActivity
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    //((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());
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
            }


//        try {
//            saveImageToExternalStorage(data.getData());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


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
