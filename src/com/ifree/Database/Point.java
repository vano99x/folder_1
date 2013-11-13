package com.ifree.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import org.json.JSONObject;

public class Point //extends EntityBase
{
	public static final String COLUMN_ID = "Id";
	public static final String COLUMN_NAME = "Name";
	private static final int NUM_COLUMN_ID = 0;
	private static final int NUM_COLUMN_NAME = 1;
	public static final String TABLE_NAME = "Point";
	public int Id;
	public String Name;

	public Point()
	{
	}

	public Point(int paramInt, String paramString)
	{
		this.Id = paramInt;
		this.Name = paramString;
	}

	private static void delete(int paramInt, Context paramContext)
	{
		DbConnector db = DbConnector.getInstance();
		db.delete("Point", "Id", String.valueOf(paramInt));
	}

	public static Point[] getBySuperviser(int paramInt, Context paramContext)
	{
		DbConnector db = DbConnector.getInstance();
		String[] arrayOfString = new String[1];
		arrayOfString[0] = String.valueOf(paramInt);

		Cursor localCursor = 
		    db.Select(
		        "SELECT * FROM Point p INNER JOIN PersonelPoint pp ON p.Id=pp.PointId WHERE pp.PersonelId=?", 
		        arrayOfString
		    );

		ArrayList localArrayList = new ArrayList();
		if (!localCursor.isAfterLast())
		{
		    do
		    {
		        localArrayList.add(new Point(localCursor.getInt(0), localCursor.getString(1)));
		    }
		    while (localCursor.moveToNext());
		}
		localCursor.close();
		return (Point[])localArrayList.toArray(new Point[0]);

		//return null;
	}

	public static Point getInstance(JSONObject paramJSONObject)
	{
		Point localPoint = new Point();
		try
		{
			localPoint.Id = paramJSONObject.getInt("Id");
			localPoint.Name = paramJSONObject.getString("Name");
			return localPoint;
		}
		catch (Exception localException)
		{
		}
		return null;
	}

	private static void save(Point paramPoint, Context paramContext)
	{
		DbConnector db = DbConnector.getInstance();
		ContentValues cv = new ContentValues();
		cv.put("Id", Integer.valueOf(paramPoint.Id));
		cv.put("Name", paramPoint.Name);
		db.insert("Point", cv);
	}

	public static void sync(Point[] paramArrayOfPoint, Context paramContext)
	{
		for (int i = 0; i < paramArrayOfPoint.length; i++)
		{
			delete(paramArrayOfPoint[i].Id, paramContext);
			save(paramArrayOfPoint[i], paramContext);
		}
	}
}

/* Location:           C:\Users\vano99\Desktop\jd-gui-0.3.5.windows\TandAOffline_dex2jar.jar
 * Qualified Name:     com.ifree.Database.Point
 * JD-Core Version:    0.6.2
 */