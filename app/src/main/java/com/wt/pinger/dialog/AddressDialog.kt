package com.wt.pinger.dialog

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.textfield.TextInputEditText
import com.wt.pinger.R
import com.wt.pinger.data.Ping
import com.wt.pinger.data.PingViewModel

class AddressDialog : DialogFragment() {

    var editText0 : TextInputEditText? = null
    var editText1 : TextInputEditText? = null
    var editText2 : TextInputEditText? = null
    var editText3 : TextInputEditText? = null
    var editText4 : TextInputEditText? = null
    var item : Ping? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (savedInstanceState != null) {
            item = Ping.fromBundle(savedInstanceState);
        } else {
            item = Ping.fromBundle(arguments);
        }
        val dialog = MaterialDialog(this.activity!!)
                .title(R.string.label_entry)
                .customView(
                        viewRes = R.layout.dialog_address_form,
                        scrollable = true,
                        noVerticalPadding = true
                )
                .positiveButton(R.string.label_ok) { dialog ->
                    run {
                        item?.address = editText1?.getText().toString().trim()
                        //item?.displayName = editText0?.getText().toString().trim()

                        if (item?.address!!.trim().length == 0) {
                            editText1?.requestFocus()
                            Toast.makeText(activity, R.string.toast_enter_url, Toast.LENGTH_SHORT).show()
                        } else {

                            //item?.packet = parseInt(editText2?.getText().toString())
                            //item?.pings = parseInt(editText3?.getText().toString())
                            //item?.interval = parseInt(editText4?.getText().toString())

                            if (item?.id == 0) {
                                ViewModelProviders.of(this)
                                        .get(PingViewModel::class.java)
                                        .addItem(item)
                            } else {
                                //ViewModelProviders.of(this)
                                //        .get(PingViewModel::class.java)
                                //        .updateItem(item)
                            }

                            dialog.dismiss()
                        }

                        /*
                        item.addres = editText1.getText().toString().trim()
                        item.display_name = editText0.getText().toString()
                        if (item.addres.trim().length() === 0) {
                            editText1.requestFocus()
                            Toast.makeText(activity, R.string.toast_enter_url, Toast.LENGTH_SHORT).show()
                            return
                        }
                        try {
                            item.packet = Integer.parseInt(editText2.getText().toString())
                        } catch (e: Exception) {
                            item.packet = null
                        }

                        try {
                            item.pings = Integer.parseInt(editText3.getText().toString())
                        } catch (e: Exception) {
                            item.pings = null
                        }

                        try {
                            item.interval = Integer.parseInt(editText4.getText().toString())
                        } catch (e: Exception) {
                            item.interval = null
                        }

                        if (item._id == null) {
                            // insert
                            object : AsyncQueryHandler(activity!!.contentResolver) {

                            }.startInsert(0, null, DbContentProvider.URI_CONTENT, item.toContentValues(true))
                            EventClip.deliver(EventParam(EventNames.ADDRESS_ADDED))
                        } else {
                            // update
                            object : AsyncQueryHandler(activity!!.contentResolver) {

                            }.startUpdate(0, null, DbContentProvider.URI_CONTENT, item.toContentValues(false), AddressItem.FIELD_ID + "=?", arrayOf(item._id.toString()))
                        }
                        dialog.dismiss()
                        */
                    }
                }
                .negativeButton(R.string.label_cancel)
                .neutralButton(if (item?.id != 0) R.string.label_delete else 0)

            editText0 = dialog.getCustomView().findViewById(R.id.editText0)
            editText1 = dialog.getCustomView().findViewById(R.id.editText1)
            editText2 = dialog.getCustomView().findViewById(R.id.editText2)
            editText3 = dialog.getCustomView().findViewById(R.id.editText3)
            editText4 = dialog.getCustomView().findViewById(R.id.editText4)

            editText1?.setText(item?.address)

            //editText0.setText(item.display_name);
            //editText2.setText(item.packet == null ? "" : item.packet.toString());
            //editText3.setText(item.pings == null ? "" : item.pings.toString());
            //editText4.setText(item.interval == null ? "" : item.interval.toString());



        return dialog
    }

    fun parseInt(d : String) : Int {
        try {
            return Integer.parseInt(editText2?.getText().toString())
        } catch (e : Exception) {
            return 0
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putAll(item?.toBundle())
        super.onSaveInstanceState(outState)
    }

}