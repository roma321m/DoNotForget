package roman.app.donotforget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import roman.app.donotforget.R;

public class ViewDialog_Confirmation {

    public interface Callback_ViewDialogConfirmation{
        void confirmClicked();
    }

    private MaterialButton dialog_confirm_BTN_back, dialog_confirm_BTN_confirm;
    private MaterialTextView dialog_list_LBL_title, dialog_list_LBL_body;
    private Callback_ViewDialogConfirmation callback_viewDialogConfirmation;

    public void showDialog(Activity activity, String title, String body, Callback_ViewDialogConfirmation callback_viewDialogConfirmation) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogbox_confirm);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        this.callback_viewDialogConfirmation = callback_viewDialogConfirmation;

        findViews(dialog);
        setText(title, body);
        initButtons(dialog);

        dialog.show();
    }

    private void setText(String title, String body){
        dialog_list_LBL_title.setText(title);
        dialog_list_LBL_body.setText(body);
    }

    private void initButtons(Dialog dialog){
        dialog_confirm_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog_confirm_BTN_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback_viewDialogConfirmation.confirmClicked();
                dialog.cancel();
            }
        });
    }

    private void findViews(Dialog dialog){
        dialog_confirm_BTN_back = dialog.findViewById(R.id.dialog_confirm_BTN_back);
        dialog_confirm_BTN_confirm = dialog.findViewById(R.id.dialog_confirm_BTN_confirm);
        dialog_list_LBL_title = dialog.findViewById(R.id.dialog_list_LBL_title);
        dialog_list_LBL_body = dialog.findViewById(R.id.dialog_list_LBL_body);
    }
}
