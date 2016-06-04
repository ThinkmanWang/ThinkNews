package com.thinkman.thinkutils.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

/**
 * Created by wangx on 2016/6/4.
 */
public class CommonDialogUtils {

    public static void showInputDialog(Context context
            , String szTitle
            , String szMsg
            , String szHint
            , final OnInputDialogResult result) {
        final EditText etText = new EditText(context);
        etText.setHint(szHint);

        new AlertDialog.Builder(context)
                .setTitle(szTitle)
                .setMessage(szMsg)
                .setView(etText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (null != result) {
                            result.onOk(etText.getText().toString());
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }


    public interface OnInputDialogResult {
        public void onOk(String szText);
    }
}
