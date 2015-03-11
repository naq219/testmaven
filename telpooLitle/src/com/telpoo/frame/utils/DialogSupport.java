package com.telpoo.frame.utils;

import java.lang.reflect.Field;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.delegate.WhereIdelegate;

public class DialogSupport {

	/**
	 * hàm này được thay thế vì thiếu title truyền vào
	 * 
	 * @param context
	 * @param resource
	 *            giao diện xml của dilog
	 * @param dldelegate
	 *            listener lắng nghe các thao tác dialog để truyền về cho class
	 *            trước
	 * @param where
	 *            xem
	 * @see @link support
	 */
	public static Dialog confirm(final Context context, Integer resource, String title, final Idelegate dldelegate,
			final int where) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layoutView = inflater.inflate(resource, null);
		builder.setView(layoutView);
		final Idelegate delegate = dldelegate;
		final Dialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(false);
		TextView tvTitle = (TextView) layoutView.findViewById(Cons.Id.dialogSupport_title);
		if (tvTitle != null)
			tvTitle.setText(title);
		Button cancel = (Button) layoutView.findViewById(Cons.Id.dialogSupport_cancel);
		if (cancel != null)
			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (dialog != null && dialog.isShowing())
						dialog.dismiss();

					delegate.callBack(false, where);
				}
			});

		Button ok = (Button) layoutView.findViewById(Cons.Id.dialogSupport_ok);
		if (ok != null)
			ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					delegate.callBack(true, where);
					dialog.dismiss();

				}
			});

		dialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface arg0) {
				delegate.callBack(1, where);
				dialog.dismiss();

			}
		});

		return dialog;

	}

	public static void dismisDialog(final Dialog dialog) {

		if (dialog != null && dialog.isShowing())
			dialog.dismiss();

	}

	public static void supportDialog(Activity activity) {
		Dialog dialog = new Dialog(activity.getApplicationContext());

		dialog.show();
	}

	public static Dialog datePicker(Context context, final Idelegate idelegate) {

		OnDateSetListener callBack = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker arg0, int year, int monofyear, int dayofmonth) {
				Calendar c = Calendar.getInstance();

				c.set(year, monofyear, dayofmonth);
				idelegate.callBack(c, WhereIdelegate.DIALOGUTILS_DATEPICKER);

			}
		};

		DatePickerDialog dialog = new DatePickerDialog(context, callBack, Calendar.getInstance().get(Calendar.YEAR),
				Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

		dialog.show();
		return dialog;
	}


	public static void DatePickerYearMonth(Context ct, final Idelegate idelegate) {
		

		DatePickerDialog.OnDateSetListener mDateSetListner = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

				
				Calendar c = Calendar.getInstance();

				c.set(year, monthOfYear, dayOfMonth);
				idelegate.callBack(c, WhereIdelegate.DIALOGUTILS_DATEPICKER_MONTH_YEAR);
				
				
			}
		};
		
		int myear= Calendar.getInstance().get(Calendar.YEAR);
		int mMonth= Calendar.getInstance().get(Calendar.MONTH);
		int mDay= Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

		DatePickerDialog dpd = new DatePickerDialog(ct, mDateSetListner, myear, mMonth, mDay);
		try {
			java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
			for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
				if (datePickerDialogField.getName().equals("mDatePicker")) {
					datePickerDialogField.setAccessible(true);
					DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
					Field datePickerFields[] = datePickerDialogField.getType().getDeclaredFields();
					for (Field datePickerField : datePickerFields) {
						if ("mDayPicker".equals(datePickerField.getName())
								|| "mDaySpinner".equals(datePickerField.getName())) {
							datePickerField.setAccessible(true);
							Object dayPicker = new Object();
							dayPicker = datePickerField.get(datePicker);
							((View) dayPicker).setVisibility(View.GONE);
						}
					}
				}
			}
		} catch (Exception ex) {
		}
		dpd.show();
	}
	
	
	public static void dialogSpinner(final TextView tv, Context ct, String title, final String[] items,final String[] itemsShow,
			final Idelegate delegate, final int where) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ct);
		builder.setTitle(title);
		builder.setItems(itemsShow, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				tv.setText("" + itemsShow[item]);
				delegate.callBack(items[item], where);
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	
	public static void Message(Context ct, String msg, final Idelegate idelegate){
		
		AlertDialog.Builder builder=new AlertDialog.Builder(ct);
		builder.setMessage(msg);
		
		builder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				if(idelegate!=null)
				idelegate.callBack(1, 1000000);
				
			}
		})		;
		builder.setPositiveButton("Ok", null);
		builder.create().show();		
	}

    public static void simpleYesNo(Context context, String textBtnYes, String textBtnNo, String title,String message, final Idelegate idelegate){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        if(title!=null&&title.length()>0)
            builder.setTitle(title);

        if(message!=null&&message.length()>0)
            builder.setMessage(message);

        builder.setPositiveButton(textBtnYes,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                idelegate.callBack(1,1);

            }
        });

        builder.setNegativeButton(textBtnNo,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                idelegate.callBack(0,0);
            }
        });

        builder.create().show();


    }
	
	
}
