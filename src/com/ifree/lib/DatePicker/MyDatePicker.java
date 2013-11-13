package com.ifree.lib.DatePicker;

//import android.app.*;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.support.v4.app.FragmentManager;

import com.ifree.lib.*;

import com.example.test6.R;

public class MyDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener , View.OnClickListener
{
	DatePicker _datePicker;
	FragmentManager _fragmentManager;
	String _name;

	public MyDatePicker()
	{
		this._datePicker = null;
		this._name = null;
	}

	public void Init(String name, FragmentManager fragmentManager)
	{
		this._name = name;
		this._fragmentManager = fragmentManager;

		this.SelectedDateChanged = new Event<RunnableWithArgs>();
	}

    public View onCreateView(
			LayoutInflater inflater, 
			ViewGroup container,
            Bundle savedInstanceState
		)
	{
        this.getDialog().getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE); // !!!

        View view = inflater.inflate(R.layout.ctrl_date_picker, container);
		//this.setStyle( STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog);
        this._datePicker = (DatePicker) view.findViewById(R.id.DatePicker);

		Button ok = (Button)view.findViewById(R.id.MyDatePickerView_OkId);
		ok.setOnClickListener(this);
		ok.setTag(R.id.MyDatePickerView_OkId);

		Button cancel = (Button)view.findViewById(R.id.MyDatePickerView_CancelId);
		cancel.setOnClickListener(this);
		cancel.setTag(R.id.MyDatePickerView_CancelId);

        return view;
    }

	public Event<RunnableWithArgs> SelectedDateChanged;

//@Override
//  public Dialog onCreateDialog(Bundle savedInstanceState)
//  {
//    // Use the current date as the default date in the picker
//    final Calendar c = Calendar.getInstance();
//    int year = c.get(Calendar.YEAR);
//    int month = c.get(Calendar.MONTH);
//    int day = c.get(Calendar.DAY_OF_MONTH);

//    // Create a new instance of DatePickerDialog and return it
//    return new DatePickerDialog(getActivity(), this, year, month, day);
//  }

  public void onDateSet(DatePicker view, int year, int month, int day)
  {
//    pYear = year;
//    pDay = day;
//    pMonth = month;
  }

	public void Show()
	{
		this.show( this._fragmentManager, "fragment_edit_name");
	}

	//*********************************************************************************************
	//*      Ctrl Handler
	public void onClick_okBtn()
	{
		int year  = this._datePicker.getYear();
		int month = this._datePicker.getMonth();
		int day   = this._datePicker.getDayOfMonth();

		SelectedDateChanged.RunEvent( new Object[]{ this._name, year, month, day});

		this.dismiss();
	}
	public void onClick_cancelBtn()
	{
		this.dismiss();
	}

	public void onClick(View paramView)
	{
		Object tag = paramView.getTag();

		Integer integer = operator.as(Integer.class, tag);

		if( integer != null)
		{
		    switch(integer)
		    {
		        case R.id.MyDatePickerView_OkId:{
		            onClick_okBtn();
		        break;}
		        case R.id.MyDatePickerView_CancelId:{
		            onClick_cancelBtn();
		        break;}
		    }
		}
	}
}