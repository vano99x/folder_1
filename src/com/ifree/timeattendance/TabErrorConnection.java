package com.ifree.timeattendance;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.ifree.lib.tabui.Tab;
import com.ifree.lib.tabui.Tab.AnimationRunner;

import com.example.test6.R;

public class TabErrorConnection extends Tab
{
	private AnimationDrawable waitAnim = null;

	public TabErrorConnection(Context paramContext, ViewGroup paramViewGroup, int paramInt1, int paramInt2)
	{
		super(paramContext, paramViewGroup, paramInt1, paramInt2);
		ImageView localImageView = (ImageView)this.root.findViewById(R.id.wait_animation);
		this.waitAnim = ((AnimationDrawable)localImageView.getDrawable());

		//localImageView.post(new Tab.AnimationRunner(this, this.waitAnim));
		//add
		localImageView.post(new Tab.AnimationRunner(this.waitAnim));
	}
}

/* Location:           C:\Users\vano99\Desktop\jd-gui-0.3.5.windows\TandAOffline_dex2jar.jar
 * Qualified Name:     com.ifree.timeattendance.TabErrorConnection
 * JD-Core Version:    0.6.2
 */