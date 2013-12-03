package com.Tabs.CheckinList;

import android.widget.Filter;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

import com.ifree.Database.*;
import com.ifree.lib.*;

	class CheckinFilter extends Filter
	{
		private CheckinAdapter3 _checkinAdapter3;

		CheckinFilter(CheckinAdapter3 checkinAdapter3)
		{
			this._checkinAdapter3 = checkinAdapter3;

			//this._checkinAdapter3._context.DatePickerCallBack = 
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint)
		{
			FilterResults result = new FilterResults();

			int year1  = this._checkinAdapter3.listener._year1;
			int month1 = this._checkinAdapter3.listener._month1;
			int day1   = this._checkinAdapter3.listener._day1;
			int year2  = this._checkinAdapter3.listener._year2;
			int month2 = this._checkinAdapter3.listener._month2;
			int day2   = this._checkinAdapter3.listener._day2;

			//long currentMS     = System.currentTimeMillis();
			//Date currentDate   = new Date(currentMS);
			//Date selectedDate1 = new Date( year1 - 1900, month1, day1,0,0,0);
			//Date selectedDate2 = new Date( year2 - 1900, month2, day2,0,0,0);
			Calendar selectedDate1 = Calendar.getInstance();
			Calendar selectedDate2 = Calendar.getInstance();
			selectedDate1.set( year1 , month1, day1,  0,  0,  0);
			selectedDate2.set( year2 , month2, day2, 23, 59, 59);

			List<Checkin> founded = new ArrayList<Checkin>();
			for (Checkin ch : this._checkinAdapter3.original)
			{
				//Date checkinDate = ch.get_DateObj();
				Calendar checkinDate = ch.get_CalendarObj();

				if(checkinDate.after(selectedDate1) && checkinDate.before(selectedDate2))
				{
					founded.add(ch);
				}
			}

			result.values = founded;
			result.count = founded.size();

			return result;
		}

		@Override
		protected void publishResults(CharSequence charSequence, FilterResults filterResults)
		{
			//clear();
			//for (Checkin ch : (List<Checkin>) filterResults.values)
			//{
			//    //add(ch);
			//}
			List<Checkin> list = (List<Checkin>) filterResults.values;

			this._checkinAdapter3.items.clear();
			this._checkinAdapter3.items.addAll(list);
			this._checkinAdapter3.notifyDataSetChanged();
		}



		////@Override
		//protected FilterResults performFiltering(CharSequence constraint)
		//{
		//    FilterResults result = new FilterResults();
		//    // NOTE: this function is *always* called from a background thread, and
		//    // not the UI thread.
		//    String filterTypeStr = constraint.toString().toLowerCase();
		//    int    filterTypeInt = Integer.parseInt(filterTypeStr);
		//    //if (constraint != null && constraint.toString().length() > 0)
		//    //{

		//    switch(filterTypeInt){
		//    case 1:{

		//        long currentMS     = System.currentTimeMillis();
		//        long dayBeforeMS   = DateHelper.AddDays(currentMS, -1);
		//        Date currentDate   = new Date(currentMS);
		//        Date dayBeforeDate = new Date(dayBeforeMS);

		//        List<Checkin> founded = new ArrayList<Checkin>();
		//        for (Checkin ch : this._checkinAdapter3.original)
		//        {
		//            Date checkinDate = ch.get_DateObj();

		//            //if(
		//            //    t.getArtistName().toLowerCase().contains(constraint) || 
		//            //    t.getTrackName().toLowerCase().contains(constraint)
		//            //)
		//            //if(ch.CompareDate(constraint))
		//            if(checkinDate.after(dayBeforeDate) && checkinDate.before(currentDate))
		//            {
		//                founded.add(ch);
		//            }
		//        }

		//        result.values = founded;
		//        result.count = founded.size();

		//    break;}
		//    case 2:{

		//        result.values = this._checkinAdapter3.original;
		//        result.count =  this._checkinAdapter3.original.size();

		//    break;}
		//    }

		//    //}
		//    //else
		//    //{
		//    //    result.values = original;
		//    //    result.count = original.size();
		//    //}

		//    return result;
		//}
	}