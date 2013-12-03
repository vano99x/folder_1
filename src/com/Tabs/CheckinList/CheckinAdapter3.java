package com.Tabs.CheckinList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.ifree.Database.*;

import com.example.test6.R;

public class CheckinAdapter3 extends BaseAdapter implements Filterable
{
	//private View.OnClickListener listener;
	public TabCheckinList listener;
	private LayoutInflater mInflater;
	public Context _context;

	public ArrayList<Checkin> items;
	public ArrayList<Checkin> original;

	public CheckinAdapter3(
		Context paramContext, 
		Checkin[] checkinArr, 
		TabCheckinList paramOnClickListener)//Object paramOnClickListener)
	{
		this.mInflater = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
		this.listener = paramOnClickListener;
		this._context = paramContext;

		this.items = new ArrayList<Checkin>(Arrays.asList(checkinArr));
		
		//List<Checkin> o = Arrays.asList(checkinArr);
		
		this.original = new ArrayList<Checkin>();
		this.original.addAll(this.items);
	}


	
	//*********************************************************************************************
	//*      properties
	//private

	public int getCount()
	{
		if (this.items == null)
		{
			return 0;
		}
		return this.items.size();
	}

	public Object getItem(int paramInt)
	{
		return Integer.valueOf(paramInt);
	}

	public long getItemId(int paramInt)
	{
		return paramInt;
	}

	private static SimpleDateFormat _dateFormat;
	private static SimpleDateFormat get_DateFormat()
	{
		if( CheckinAdapter3._dateFormat == null )
		{
			CheckinAdapter3._dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
		}
		return CheckinAdapter3._dateFormat;
	}

	private static String GetTextForItem(Checkin checkin)
	{
		long dt = Long.parseLong(checkin.DateTime);
		Date date = new Date(dt);
		String dateStr = get_DateFormat().format(date);

		Personel personel = checkin.get_Personel();
		String nameStr = personel.FirstName;

		return nameStr + " "+ dateStr;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
	{
		if( paramView == null )
		{
			paramView = this.mInflater.inflate(R.layout._4_checkin_list_item, null);
			//paramView.setTag(null);
		}

		TextView     chView  = (TextView)paramView.findViewById(R.id.CheckinListItem_TextId);
		TextView     stateView = (TextView)paramView.findViewById(R.id.CheckinListItem_StateId);
		//LinearLayout baseView = (LinearLayout)paramView.findViewById(R.id.CheckinListItem_RootId);
		//baseView.setOnClickListener(this.listener);

		if( this.items != null && this.items.size() != 0 )
		{
			try
			{
				Checkin checkin = this.items.get(paramInt);
				String text = GetTextForItem(checkin);
				String state = Integer.toString(checkin.get_StateCheckinOnServer());

				chView.setText(text);

				switch(checkin.get_StateCheckinOnServer())
				{
					case -2:{
						stateView.setBackgroundColor(0xffff0000);
						stateView.setText("");
					break;}
					case -1:{
						stateView.setBackgroundColor(0xfff5d50b);
						stateView.setText("");
					break;}
					default :{
						stateView.setBackgroundColor(0xff46b525);
						stateView.setText(state);
					}
				}
			}
			catch (Exception e)
			{
				Exception ex = e;
			}
		}
		return paramView;
	}

	private Filter _filter;
	@Override
	public Filter getFilter()
	{
		if( this._filter == null )
		{
			this._filter = new CheckinFilter(this);
		}
		return this._filter;
	}

}