package com.thinkman.thinkutilssample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.thinkman.thinkutils.dialog.CommonDialogUtils;

public class MainActivity extends AppCompatActivity {

    EditText m_etResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        m_etResult = (EditText) findViewById(R.id.et_result);

        Button btnInputDialog = (Button) findViewById(R.id.btn_input_dlg);
        btnInputDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialogUtils.showInputDialog(MainActivity.this, "Title", "Hello World", "haha", new CommonDialogUtils.OnInputDialogResult() {
                    @Override
                    public void onOk(String szText) {
                        m_etResult.setText(szText);
                    }
                });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
