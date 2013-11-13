package com.ifree.timeattendance;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.ifree.lib.tabui.Tab;

import android.widget.Button;

import com.ifree.lib.*;
import com.example.test6.R;

public class TabModeSelection extends Tab implements View.OnClickListener
{
	MainEngine _engine;
	public final String ACTION_START_WORK_SELECTION = "START.WORK";

	public TabModeSelection(Context paramContext, ViewGroup paramViewGroup, int paramInt1, int paramInt2)
	{
		super(paramContext, paramViewGroup, paramInt1, paramInt2);
		//TALog.Log("===========TabModeSelection=================:" + this);
		this._engine = MainEngine.getInstance();

		Button start_button = (Button)this.root.findViewById(R.id.PageModeSelection_start_button);
		start_button.setOnClickListener(this);
		start_button.setTag(R.id.PageModeSelection_start_button);

		Button end_button = (Button)this.root.findViewById(R.id.PageModeSelection_end_button);
		end_button.setOnClickListener(this);
		end_button.setTag(R.id.PageModeSelection_end_button);

		Button check_button = (Button)this.root.findViewById(R.id.PageModeSelection_check_button);
		check_button.setOnClickListener(this);
		check_button.setTag(R.id.PageModeSelection_check_button);
	}

	public void onClick_start_button()
	{
		this._engine.setCurrentMode(Mode.StartWork);
		//this.mEngine.showScreen(State.WAIT_MODE, 0L);
		               UIHelper.Instance().switchState(MainActivity.State.WAIT_MODE);
	}

	public void onClick_end_button()
	{
		this._engine.setCurrentMode(Mode.EndWork);
		//this.mEngine.showScreen(State.WAIT_MODE, 0L);
		               UIHelper.Instance().switchState(MainActivity.State.WAIT_MODE);
	}

	public void onClick_check_button()
	{
		this._engine.setCurrentMode(Mode.Check);
		//this.mEngine.showScreen(State.WAIT_MODE, 0L);
		               UIHelper.Instance().switchState(MainActivity.State.WAIT_MODE);
	}

	public void onClick(View paramView)
	{
		Object tag = paramView.getTag();

		Integer integer = operator.as(Integer.class, tag);

		if( integer != null)
		{
		    switch(integer)
		    {
		        case R.id.PageModeSelection_start_button:{
		        	onClick_start_button();
		        break;}
		        case R.id.PageModeSelection_end_button:{
		            onClick_end_button();
		        break;}
		        case R.id.PageModeSelection_check_button:{
		            onClick_check_button();
		        break;}
		    }
		}
	}
}