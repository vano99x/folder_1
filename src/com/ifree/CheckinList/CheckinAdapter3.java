package com.ifree.CheckinList;

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

	private SimpleDateFormat _dateFormat;
	private SimpleDateFormat get_DateFormat()
	{
		if( this._dateFormat == null )
		{
			this._dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
		}
		return this._dateFormat;
	}

	private String GetTextForItem(Checkin checkin)
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
			paramView = this.mInflater.inflate(R.layout.list_item, null);
			paramView.setTag(null);
		}

		TextView     textView = (TextView)    paramView.findViewById(R.id.txtName);
		LinearLayout baseView = (LinearLayout)paramView.findViewById(R.id.item);
		baseView.setOnClickListener(this.listener);

		if( this.items != null && this.items.size() != 0 )
		{
			try
			{
				Checkin checkin = this.items.get(paramInt);
				String str = GetTextForItem(checkin);

				textView.setText(str);
				baseView.setTag(checkin);
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