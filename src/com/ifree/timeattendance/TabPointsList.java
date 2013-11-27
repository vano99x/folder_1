package com.ifree.timeattendance;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ifree.lib.*;
import com.ifree.Database.*;
import com.ifree.lib.tabui.*;

import com.example.test6.R;

public class TabPointsList extends Tab implements View.OnClickListener
{
	//public final String ACTION_START_PROCESS_POINT = "START.PROCESS.POINT";
	MainEngine _engine;
	private ListPointsAdapter listRouteAdapter;
	private ListView listView;
	private TextView mLableEmptyList;
	private TextView mLableHead;

	public TabPointsList(Context paramContext, ViewGroup paramViewGroup, int paramInt1, int paramInt2)
	{
		super(paramContext, paramViewGroup, paramInt1, paramInt2);
		this._engine = MainEngine.getInstance();

		listView =        (ListView)this.root.findViewById(R.id.PagePointsList_ListView_Id);
		mLableEmptyList = (TextView)this.root.findViewById(R.id.lable_empty_list);
		mLableHead =      (TextView)this.root.findViewById(R.id.lable_head);
	}

	private void createListRouteAdapter(Point[] paramArrayOfPoint)
	{
		if (this.listRouteAdapter != null)
		{
			this.listRouteAdapter = null;
		}
		this.listRouteAdapter = new ListPointsAdapter(this.context, paramArrayOfPoint, this);
		this.listView.setAdapter(this.listRouteAdapter);
	}

	//public void hide()
	//{
	//    super.Hide();
	//}
	//public void onClick(View paramView)
	//{
	//    Object tag = paramView.getTag();
	//    if (tag != null) {
	//        Point point = (Point)tag;
	//        this.listener.doAction(this, "START.PROCESS.POINT", point);
	//    }
	//}

	public void PointsListItem_Selected(Point point)
	{
		this._engine.set_CurrentPointId(   ((Point)point).Id   );
		UIHelper.Instance().switchState(com.ifree.timeattendance.MainActivity.State.MODE_SELECTION);
	}

	public void onClick(View ctrl)
	{
		Object tag = ctrl.getTag();
		Object [] arr = (Object[])tag;
		Integer integer = (Integer)arr[0];

		if( integer != null)
		{
		    switch(integer)
		    {
		        case R.id.PointsListItem_Id:{
		            PointsListItem_Selected((Point)arr[1]);
		        break;}
		    }
		}
	}

	public void Show()
	{
		super.Show();
		MainEngine localMainEngine = MainEngine.getInstance();
		//TALog.Log("engine = " + localMainEngine);

		TextView localTextView2;
		if (localMainEngine != null)
		{
			Personel superviser = MainActivityProxy.get_SvModel().get_CurrentSuperviser();
			Point [] pointArr   = Point.getBySuperviser( superviser.Id, this.context);

			createListRouteAdapter(pointArr);

			if(this.listRouteAdapter != null && this.listRouteAdapter.getCount() > 0)
			{
				this.mLableHead.setVisibility(     View.VISIBLE);
				this.mLableEmptyList.setVisibility(View.INVISIBLE);
			}
			else
			{
				this.mLableHead.setVisibility(     View.INVISIBLE);
				this.mLableEmptyList.setVisibility(View.VISIBLE);
			}
		}
	}
}

/* Location:           C:\Users\vano99\Desktop\jd-gui-0.3.5.windows\TandAOffline_dex2jar.jar
 * Qualified Name:     com.ifree.timeattendance.TabPointsList
 * JD-Core Version:    0.6.2
 */