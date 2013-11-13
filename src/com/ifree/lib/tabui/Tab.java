package com.ifree.lib.tabui;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

public abstract class Tab
{
	protected Context context; // main activity
	//protected TabActionListener listener;
	protected ViewGroup parent;    // root of Tab
	protected View      tabView; // attached view
	protected ViewGroup root;    // root of Tab

		//rootView - view of main activity
		//paramInt1 - view
		//paramInt2 - id in attached view
	public Tab(Context mainActivity, ViewGroup rootView, int paramInt1, int paramInt2)
	{
		this.context = mainActivity;
		this.parent  = rootView;
		this.tabView = LayoutInflater.from(mainActivity).inflate( paramInt1, this.parent, true);
		this.root    = ((ViewGroup)rootView.findViewById(paramInt2));
	}
	public void Clear()
	{
		this.Hide();
		//this.parent.removeView(this.tabView);
		this.root    = null;
		this.tabView = null;
		this.parent  = null;
		this.context = null;
	}

	public static void Show(View ctrl)
	{
		ctrl.setVisibility(View.VISIBLE);
	}
	public static void Hide(View ctrl)
	{
		ctrl.setVisibility(View.GONE);
	}

	public void Show()
	{
		this.root.setVisibility(View.VISIBLE);
	}

	public void Hide()
	{
		this.root.setVisibility(View.GONE);
	}

	public ViewGroup getRoot()
	{
		return this.root;
	}

	//public void setListener(TabActionListener paramTabActionListener)
	//{
	//    this.listener = paramTabActionListener;
	//}

	//public void showDialog(
	//    String paramString1, 
	//    String paramString2, 
	//    DialogInterface.OnClickListener paramOnClickListener)
	//{
	//    AlertDialog.Builder b = new AlertDialog.Builder(this.context);
	//    b.setMessage(paramString1);
	//    b.setTitle(paramString2);
	//    b.setCancelable(true);
	//    b.setNeutralButton("Ok", paramOnClickListener);
	//    b.create().show();
	//}

	public class AnimationRunner implements Runnable
	{
		AnimationDrawable animation = null;

		public AnimationRunner(AnimationDrawable arg2)
		{
			this.animation = arg2;
		}

		public void run()
		{
			if (this.animation != null)
			{
				this.animation.start();
			}
		}
	}
}