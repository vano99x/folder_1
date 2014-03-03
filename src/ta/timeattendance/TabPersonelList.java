package ta.timeattendance;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import ta.Database.Personel;
import ta.lib.tabui.Tab;
//import com.ifree.lib.tabui.TabActionListener;

import ta.lib.*;

import ta.timeattendance.R;

public class TabPersonelList extends Tab implements View.OnClickListener
{
	public final String ACTION_PERSONEL_SELECT = "PERSONEL_SELECT";
	MainEngine _engine;
	private PersonelListAdapter listAdapter;
	private ListView listView;
	private TextView mLableEmptyList;
	private EditText txtSearch;

	public TabPersonelList(Context paramContext, ViewGroup paramViewGroup, int paramInt1, int paramInt2)
	{
		super(paramContext, paramViewGroup, paramInt1, paramInt2);
		this._engine = MainEngine.getInstance();

		listView        = (ListView)this.root.findViewById(R.id.PagePersonelList_ListView_Id);
		mLableEmptyList = (TextView)this.root.findViewById(R.id.lable_empty_list);
		txtSearch       = (EditText)this.root.findViewById(R.id.txt_lastname);//continue_button

		Button searchBtn = ((Button)this.root.findViewById(R.id.SearchButton_Id));
		searchBtn.setOnClickListener(this);
		searchBtn.setTag(new Object[]{R.id.SearchButton_Id});
		
		// subscribe on event
		this._engine.WorkerFound.Add(get_onWorkerFound());
	}



	//*********************************************************************************************
	//       Event Handler
	private onWorkerFound get_onWorkerFound() { onWorkerFound a = new onWorkerFound(); a.arg1 = this; return a; }
	class onWorkerFound extends RunnableWithArgs<Personel[],Object> { public void run()
	{
		TabPersonelList _this = (TabPersonelList)this.arg1;
		
		Personel[] arrPersonel = this.arg;

		_this.changeList(arrPersonel);
	}}



	private void createAdapter(Personel[] paramArrayOfPersonel)
	{
		int i = 1;
		try
		{
			if( paramArrayOfPersonel.length != 0 )
			{
				this.mLableEmptyList.setVisibility(View.GONE);
			}

			if (this.listAdapter != null)
			{
				this.listAdapter = null;
			}
			this.listAdapter = new PersonelListAdapter(this.context, paramArrayOfPersonel, this);
			this.listView.setAdapter(this.listAdapter);
		}
		catch (Exception localException)
		{
			//TALog.Log(localException.toString());
		}
	}

	public void changeList(Personel[] paramArrayOfPersonel)
	{
		createAdapter(paramArrayOfPersonel);
	}

	public String getSearchString()
	{
		return this.txtSearch.getText().toString();
	}



	//*********************************************************************************************
	//       Control Handler
	private void SearchButton_Click()
	{
		String str = getSearchString();
		this._engine.searchPersonel(str);
	}

	private void PersonelListItem_Selected(Personel personel)
	{
		//this._engine.ShowTabPersonelInfo( p );
		this._engine.set_CurrentWorker( personel );
		UIHelper.Instance().tabPersonelInfo.IsShowCheckiedWorker = false;
		UIHelper.Instance().switchState(ta.timeattendance.MainActivity.State.PERSONEL_INFO);
	}

	public void onClick(View ctrl)
	{
		//Object localObject = ctrl.getTag();
		//if (localObject != null)
		//{
		//    Personel localPersonel = (Personel)localObject;
		//    this.listener.doAction(this, "PERSONEL_SELECT", localPersonel);
		//}

		Object tag = ctrl.getTag();
		//Integer integer = com.ifree.lib.operator.as(Integer.class, tag);
		Object [] arr = (Object[])tag;
		Integer integer = (Integer)arr[0];

		if( integer != null)
		{
		    switch(integer)
		    {
		        case R.id.SearchButton_Id:{
		            SearchButton_Click();
		        break;}
		        case R.id.PersonelListItem_Id:{
		            PersonelListItem_Selected((Personel)arr[1]);
		        break;}
		    }
		}
	}
}

/* Location:           C:\Users\vano99\Desktop\jd-gui-0.3.5.windows\TandAOffline_dex2jar.jar
 * Qualified Name:     com.ifree.timeattendance.TabPersonelList
 * JD-Core Version:    0.6.2
 */