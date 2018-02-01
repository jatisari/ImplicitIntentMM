package net.agusharyanto.implicitintent;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /** Called when the activity is first created. */
        Button btnMcDonald = (Button) findViewById(R.id.btnMcDonald);
        Button btnKFC = (Button) findViewById(R.id.btnKFC);
        Button btnBelajarAndroid = (Button) findViewById(R.id.btnBelajarAndroid);
        Button btnJadwalPuasa = (Button) findViewById(R.id.btnDetik);
        Button btnKamera = (Button) findViewById(R.id.btnKamera);
        Button btnMap = (Button) findViewById(R.id.btnMap);
        Button btnBarcode = (Button) findViewById(R.id.btnBarcode);


        btnMcDonald.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // getRequest(txtResult,txtUrl);
                CallIntent(v);
            }
        });
        btnKFC.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // getRequest(txtResult,txtUrl);
                CallIntent(v);
            }
        });
        btnBelajarAndroid.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // getRequest(txtResult,txtUrl);
                CallIntent(v);
            }
        });
        btnJadwalPuasa.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // getRequest(txtResult,txtUrl);
                CallIntent(v);
            }
        });
        btnKamera.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // getRequest(txtResult,txtUrl);
                CallIntent(v);
            }
        });
        btnMap.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // getRequest(txtResult,txtUrl);
                CallIntent(v);
            }
        });
        btnBarcode.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // getRequest(txtResult,txtUrl);
                CallIntent(v);
            }
        });
    }

    public void CallIntent(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btnKFC:
                //akan melakukan Call ke nomor 14022
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkCALLPHONEPermission();
                }


                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CALL_PHONE)
                            == PackageManager.PERMISSION_GRANTED) {
                        intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:14022"));
                        startActivity(intent);
                    }
                } else {
                    intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:14022"));
                    startActivity(intent);
                }

                break;
            case R.id.btnMcDonald:
                //akan melakukan Call ke nomor 14045
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:14045"));
                startActivity(intent);
                break;
            case R.id.btnBelajarAndroid:

                intent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse("http://agusharyanto.net"));
                startActivity(intent);
                break;
            case R.id.btnDetik:

                intent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse("http://www.detik.com/"));
                startActivity(intent);
                break;
            case R.id.btnKamera:
                //akan memanggil fungsi Camera android
                intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, 0);
                break;
            case R.id.btnMap:
                intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=plaza mandiri&daddr=pasar festival"));
                startActivity(intent);
                break;
            case R.id.btnBarcode:
                scanBar(view);
                break;
            default:
                break;
        }
    }

    //product barcode mode
    public void scanBar(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }

    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                Log.d("TAG", "content:" + contents);
                toast.show();
            }
        }
    }

    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }


    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 99;

    public boolean checkCALLPHONEPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
            return false;
        } else {
            return true;
        }
    }
}
