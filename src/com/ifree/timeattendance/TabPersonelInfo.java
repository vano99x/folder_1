package com.ifree.timeattendance;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ifree.Database.Personel;
import com.ifree.lib.tabui.Tab;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Button;

import com.ifree.lib.*;
import com.ifree.timeattendance.MainActivity;

import com.example.test6.R;

public class TabPersonelInfo extends Tab implements View.OnClickListener
{
	private static final long TIME_OUT = 3000L;
	private ImageView _iconMode;
	private TextView  _labelMode;
	private TextView  _labelLastName;
	private TextView  _labelName;
	private TextView  _labelThirdName;
	private ImageView _photo;

	private TextView  _hex_number_card;

	private Button _checkin_btn;
	private LinearLayout _block_exit_tracked;

	public boolean IsShowCheckiedWorker;

	public TabPersonelInfo(Context paramContext, ViewGroup paramViewGroup, int paramInt1, int paramInt2)
	{
		super(paramContext, paramViewGroup, paramInt1, paramInt2);
		this.IsShowCheckiedWorker = false;

		_iconMode =          (ImageView)this.root.findViewById(R.id.iconMode);
		_labelMode =      (TextView)this.root.findViewById(R.id.mode);

		_labelLastName   = (TextView)this.root.findViewById(R.id.last_name);
		_labelName       = (TextView)this.root.findViewById(R.id.name);
		_labelThirdName  = (TextView)this.root.findViewById(R.id.third_name);
		_photo           = (ImageView)this.root.findViewById(R.id.personel_photo);
		_hex_number_card = (TextView)this.root.findViewById(R.id.hex_number_card);

		_checkin_btn = (Button)this.root.findViewById(R.id.checkin_btn);
		_checkin_btn.setOnClickListener(this);
		_checkin_btn.setTag(R.id.checkin_btn);

		_block_exit_tracked = (LinearLayout)this.root.findViewById(R.id.block_exit_tracked);
		
		
		//// subscribe on event
		//MainEngine.getInstance().SaveCheckinCompleteEvent.Add(get_onSaveCheckinCompleteEventHandler());
	}
	
	@Override
	public void Show()
	{
		super.Show();
		MainEngine engine = MainEngine.getInstance();
		Personel p = engine.get_CurrentPersonel();
		if( p != null && p.Id != -1 )
		{
			this._labelLastName.setText(p.LastName);
			this._labelName.setText(p.FirstName);
			this._labelThirdName.setText(p.ThirdName);
			
			if(p.CardId != null)
			{
				long idLong = Long.parseLong(p.CardId);
				String str = Long.toHexString(idLong);
				_hex_number_card.setText(str);
			}

			p.loadCachedPhoto(this.context);

			if( p.Photo != null )
			{
				//Bitmap bitmap = BitmapFactory.decodeByteArray( p.Photo, 0, p.Photo.length );
				//this._photo.setImageBitmap(bitmap);
				Bitmap b = BitmapFactory.decodeByteArray(p.Photo, 0, p.Photo.length);
				float origWidth = b.getWidth();
				float origHeight = b.getHeight();
				float scale = origWidth / origHeight;
				float newHeight = 135 / scale;
				this._photo.setImageBitmap(Bitmap.createScaledBitmap(b, 135, (int)newHeight, false));
			}

			if (engine.getCurrentMode() == Mode.Check)
			{
				this._iconMode.setImageResource(R.drawable.check);
				this._labelMode.setText(R.string.mode_check_result);
				//UIHelper.Instance().ShowScreenFromBackground(MainActivity.State.MODE_SELECTION, TIME_OUT);
			}
			else if (engine.getCurrentMode() == Mode.StartWork)
			{
				this._iconMode.setImageResource(R.drawable.start);
				this._labelMode.setText(R.string.mode_start_result);
			}
			else
			{
				this._iconMode.setImageResource(R.drawable.finish);
				this._labelMode.setText(R.string.mode_end_result);
			}
			//localMainEngine.showScreen(MainActivity.State.WAIT_MODE, TIME_OUT);
		
			if(this.IsShowCheckiedWorker)
			{
				this._checkin_btn.setVisibility( View.INVISIBLE );
				this._block_exit_tracked.setVisibility( View.VISIBLE );
			}
			else
			{
				this._checkin_btn.setVisibility( View.VISIBLE );
				this._block_exit_tracked.setVisibility( View.INVISIBLE );
			}
		}
		/*else
		{
			//this._labelLastName.setText("Сотрудника");
			//this._labelName.setText("нет");
			//this._labelThirdName.setText("в базе");
			//this._photo.setImageResource(R.drawable.no_photo);

			this._checkin_btn.setVisibility( View.INVISIBLE );

			this._block_exit_tracked.setVisibility( View.INVISIBLE );
			//this._iconMode.setVisibility( View.INVISIBLE );
			//this._labelMode.setVisibility( View.INVISIBLE );

			long idLong = Long.parseLong(p.CardId);
			//int idInt = (int)idLong;
			String str = Long.toHexString(idLong);
			_hex_number_card.setText(str);
		}*/
	}



	////*********************************************************************************************
	////       Event Handler
	//private onSaveCheckinCompleteEventHandler get_onSaveCheckinCompleteEventHandler()
	//{
	//    onSaveCheckinCompleteEventHandler a = new onSaveCheckinCompleteEventHandler();
	//    return a;
	//}
	//class onSaveCheckinCompleteEventHandler extends RunnableWithArgs { public void run()
	//{
	//    Object[] resultArr = (Object[])this.result;
	//    boolean result = ((Boolean)resultArr[0]).booleanValue();

	//    if(result)
	//    {
	//        UIHelper.Instance().switchState(MainActivity.State.WAIT_MODE);
	//    }
	//}}

	public void onClick(View paramView)
	{
		int aaa = 9;
		Object tag = paramView.getTag();
		
		MainEngine engine = MainEngine.getInstance();
		Integer integer = operator.as(Integer.class, tag);

		if( engine != null && integer != null)
		{
		    switch(integer)
		    {
		        case R.id.checkin_btn:{
					engine.SaveCheckin();
		        break;}
		    }
		}
	}
}