package com.wt.pinger.dialog;

import android.app.Dialog;
import android.content.AsyncQueryHandler;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.wt.pinger.R;
import com.wt.pinger.data.PingItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
/**
 * Created by Kenumir on 2016-09-01.
 *
 */
public class AddressDialog extends DialogFragment {

    public static AddressDialog newInstance(@Nullable PingItem a) {
        AddressDialog d = new AddressDialog();
        Bundle args = new Bundle();
        if (a != null) {
            a.saveToBundle(args);
        }
        d.setArguments(args);
        return d;
    }

    @BindView(R.id.editText1) EditText editText1;
    @BindView(R.id.editText0) EditText editText0;
    @BindView(R.id.editText2) EditText editText2;
    @BindView(R.id.editText3) EditText editText3;
    @BindView(R.id.editText4) EditText editText4;

    private PingItem item;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            item = ItemProto.fromBundle(savedInstanceState, AddressItem.class);
        } else {
            item = ItemProto.fromBundle(getArguments(), AddressItem.class);
        }
        if (item == null) {
            item = new PingItem();
        }

        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.label_entry)
                .autoDismiss(false)
                .customView(R.layout.dialog_address_form, true)
                .positiveText(R.string.label_ok)
                .negativeText(R.string.label_cancel)
                .typeface(ResourcesCompat.getFont(getActivity(), R.font.medium), ResourcesCompat.getFont(getActivity(), R.font.regular))
                .neutralText(item._id != null ? R.string.label_delete : 0)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        item.addres = editText1.getText().toString().trim();
                        item.display_name = editText0.getText().toString();
                        if (item.addres.trim().length() == 0) {
                            editText1.requestFocus();
                            Toast.makeText(getActivity(), R.string.toast_enter_url, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                            item.packet = Integer.parseInt(editText2.getText().toString());
                        } catch (Exception e) {
                            item.packet = null;
                        }
                        try {
                            item.pings = Integer.parseInt(editText3.getText().toString());
                        } catch (Exception e) {
                            item.pings = null;
                        }
                        try {
                            item.interval = Integer.parseInt(editText4.getText().toString());
                        } catch (Exception e) {
                            item.interval = null;
                        }
                        if (item._id == null) {
                            // insert
                            new AsyncQueryHandler(getActivity().getContentResolver()){}.
                                    startInsert(0, null, DbContentProvider.URI_CONTENT, item.toContentValues(true));
                            EventClip.deliver(new EventParam(EventNames.ADDRESS_ADDED));
                        } else {
                            // update
                            new AsyncQueryHandler(getActivity().getContentResolver()){}.
                                    startUpdate(0, null, DbContentProvider.URI_CONTENT, item.toContentValues(false), AddressItem.FIELD_ID + "=?", new String[]{item._id.toString()});
                        }
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (item._id != null) {
                            new AsyncQueryHandler(getActivity().getContentResolver()) {}
                                    .startDelete(0, null, DbContentProvider.URI_CONTENT, null, new String[]{item._id.toString()});
                        }
                        dialog.dismiss();
                    }
                })
                .build();

        if (dialog.getCustomView() != null) {
            ButterKnife.bind(this, dialog.getCustomView());

            editText1.setText(item.addres);
            editText0.setText(item.display_name);
            editText2.setText(item.packet == null ? "" : item.packet.toString());
            editText3.setText(item.pings == null ? "" : item.pings.toString());
            editText4.setText(item.interval == null ? "" : item.interval.toString());
        }

        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        Answers.getInstance().logContentView(
                new ContentViewEvent()
                        .putContentId("address-dialog")
                        .putContentName("Address Dialog")
                        .putContentType("dialog")
        );
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (item != null) {
            item.saveToBundle(outState);
        }
        super.onSaveInstanceState(outState);
    }
}