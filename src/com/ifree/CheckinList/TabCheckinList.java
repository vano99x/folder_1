package com.ifree.CheckinList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.support.v4.app.FragmentManager;

//import java.util.Date;
import java.util.Calendar;
import java.util.Arrays;
import java.util.ArrayList;

import com.ifree.lib.*;
import com.ifree.timeattendance.MainEngine;
import com.ifree.lib.DatePicker.MyDatePicker;
import com.ifree.Database.Checkin;
import com.ifree.lib.tabui.Tab;
import com.example.test6.R;

public class TabCheckinList extends Tab implements View.OnClickListener
{
	private ListView listView;
	com.ifree.timeattendance.MainActivityProxy _mainActivity;

	public int _year1;
	public int _month1;
	public int _day1;
	public int _year2;
	public int _month2;
	public int _day2;

	public TabCheckinList(com.ifree.timeattendance.MainActivityProxy paramContext, ViewGroup paramViewGroup, int paramInt1, int paramInt2)
	{
		super(paramContext, paramViewGroup, paramInt1, paramInt2);
		this._myDatePickerLeft = null;
		this._mainActivity = paramContext;

		Calendar currentDate = Calendar.getInstance();
		this._year1  = this._year2  = currentDate.get(Calendar.YEAR);
		this._month1 = this._month2 = currentDate.get(Calendar.MONTH);
		this._day1   = this._day2   = currentDate.get(Calendar.DAY_OF_MONTH);

		//android.widget.ScrollView.setScrollbarFadingEnabled(false);
		listView = (ListView)this.root.findViewById(R.id.list);
		listView.setScrollbarFadingEnabled(false);
		
		Button checkin_btn_filter_left = (Button)this.root.findViewById(R.id.checkin_btn_filter_left);
		checkin_btn_filter_left.setOnClickListener(this);
		checkin_btn_filter_left.setTag(R.id.checkin_btn_filter_left);
		
		Button checkin_btn_filter_right = (Button)this.root.findViewById(R.id.checkin_btn_filter_right);
		checkin_btn_filter_right.setOnClickListener(this);
		checkin_btn_filter_right.setTag(R.id.checkin_btn_filter_right);
	}
	
	//*********************************************************************************************
	//       MyDatePicker
	private MyDatePicker _myDatePickerLeft;
	private MyDatePicker get_MyDatePickerLeft()
	{
		if(_myDatePickerLeft == null)
		{
			FragmentManager fragmentManager = _mainActivity.get_FragmentManager();
			_myDatePickerLeft = new MyDatePicker();
			_myDatePickerLeft.Init("left",fragmentManager);
		
			_myDatePickerLeft.SelectedDateChanged.Add(get_onSelectedDateChanged());
		}
		return _myDatePickerLeft;
	}

	private MyDatePicker _myDatePickerRight;
	private MyDatePicker get_MyDatePickerRight()
	{
		if(_myDatePickerRight == null)
		{
			FragmentManager fragmentManager = _mainActivity.get_FragmentManager();
			_myDatePickerRight = new MyDatePicker();
			_myDatePickerRight.Init("right",fragmentManager);
		
			_myDatePickerRight.SelectedDateChanged.Add(get_onSelectedDateChanged());
		}
		return _myDatePickerRight;
	}

	//*********************************************************************************************
	//*      Event Handler
	private onSelectedDateChanged get_onSelectedDateChanged()
	{
		onSelectedDateChanged sd = new onSelectedDateChanged();
		sd.arg1 = this;
		return sd;
	}
	class onSelectedDateChanged extends RunnableWithArgs { public void run()
	{
		TabCheckinList _this = (TabCheckinList)this.arg1;

		Object[] resultArr = (Object[])this.result;

		String pickerName = (String)resultArr[0];
		int year          = (Integer)resultArr[1];
		int monthOfYear   = (Integer)resultArr[2];
		int dayOfMonth    = (Integer)resultArr[3];

		if(pickerName.equals("left"))
		{
			_this._year1  = year;
			_this._month1 = monthOfYear;
			_this._day1   = dayOfMonth;
		}else if(pickerName.equals("right")) {
			_this._year2  = year;
			_this._month2 = monthOfYear;
			_this._day2   = dayOfMonth;
		}
		Filter f = _this._checkinAdapter.getFilter();
		f.filter("1");
	}}

	private CheckinAdapter3 _checkinAdapter;
	private void CreateCheckinAdapter(Checkin[] paramArrayOfPoint)
	{
		if (this._checkinAdapter != null)
		{
			this._checkinAdapter = null;
		}
		//this._checkinAdapter = new CheckinAdapter(this.context, paramArrayOfPoint, this);
		//ArrayList al = new ArrayList<Checkin>(Arrays.asList(paramArrayOfPoint));
		//this._checkinAdapter = new CheckinAdapter2(this.context, R.id.checkin_input_filter, al);
		//this._checkinAdapter = new CheckinAdapter2(this.context, R.layout.list_item, al);

		this._checkinAdapter = new CheckinAdapter3(this.context, paramArrayOfPoint, this);
		this.listView.setAdapter(this._checkinAdapter);
	}

	public void Show()
	{
		super.Show();
		MainEngine engine = MainEngine.getInstance();

		if (engine != null)
		{
			Checkin [] checkinArr = Checkin.GetAllCheckin( this.context);

			CreateCheckinAdapter(checkinArr);
		}
	}

	private void FilterLeft()
	{
		try
		{
			get_MyDatePickerLeft().Show();
		}
		catch(Exception e)
		{
			Exception ex = e;
		}
		int aaa = 9;
	}

	private void FilterRight()
	{
		get_MyDatePickerRight().Show();
	}

	//private void FilterAll()
	//{
	//    Filter f = this._checkinAdapter.getFilter();
	//    f.filter("2");
	//    int aaa = 9;
	//}

	public void onClick(View paramView)
	{
		Object tag = paramView.getTag();

		MainEngine engine = MainEngine.getInstance();
		Integer integer = operator.as(Integer.class, tag);

		if( engine != null && integer != null)
		{
		    switch(integer)
		    {
		        case R.id.checkin_btn_filter_left:{
		            FilterLeft();
		        break;}
				case R.id.checkin_btn_filter_right:{
				    FilterRight();
				break;}
		    }
		}
	}
}