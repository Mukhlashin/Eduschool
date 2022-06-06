package com.skillage.appedu;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class Database {
	public static boolean allowRefresh;
	public static boolean allowRefreshTambahTugas;
	public static boolean allowRefreshTambahMateri;
	public static void isiSpinner(Context context, Spinner combo, String tabel, String field, String kriteria)
	{
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item);
		spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
		combo.setAdapter(spinnerAdapter);
		DBAndroid db=new DBAndroid();
		db.getRecords("Select " + field + " from " + tabel + " where "+kriteria + " group by "+field);
		if (db.Records.size() != 0) {
			for (int i=0; i<db.Records.size(); i++)
			{
				spinnerAdapter.add(db.Records.get(i).get(field)+"");
			}
		}

		spinnerAdapter.notifyDataSetChanged();
	}

	public static void isiSpinnerWithOrderBy(Context context, Spinner combo, String tabel, String field, String kriteria,String orderBy)
	{
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item);
		spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
		combo.setAdapter(spinnerAdapter);
		DBAndroid db=new DBAndroid();
		db.getRecords("Select " + field + ","+orderBy+" from " + tabel + " where "+kriteria + " group by "+field+","+orderBy+" order by "+orderBy);
		if (db.Records.size() != 0) {
			for (int i=0; i<db.Records.size(); i++)
			{
				spinnerAdapter.add(db.Records.get(i).get(field)+"");
			}
		}

		spinnerAdapter.notifyDataSetChanged();
	}

	public static void isiSpinnerWithAlias(Context context, Spinner combo, String tabel, String field, String kriteria, String aliasfieldname)
	{
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item);
		spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
		combo.setAdapter(spinnerAdapter);
		DBAndroid db=new DBAndroid();
		db.getRecords("Select " + field + " as "+aliasfieldname+" from " + tabel + " where "+kriteria + " group by "+field);
		if (db.Records.size() != 0) {
			for (int i=0; i<db.Records.size(); i++)
			{
				spinnerAdapter.add(db.Records.get(i).get(aliasfieldname)+"");
			}
		}

		spinnerAdapter.notifyDataSetChanged();
	}

	public static void isiSpinnerWithAlias2(Context context, Spinner combo, String tabel, String field, String kriteria, String aliasfieldname)
	{
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context, R.layout.simple_spinner_item);
		spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
		combo.setAdapter(spinnerAdapter);
		DBAndroid db=new DBAndroid();
		db.getRecords("Select " + field + " as "+aliasfieldname+" from " + tabel + " where "+kriteria + " group by "+field);
		if (db.Records.size() != 0) {
			for (int i=0; i<db.Records.size(); i++)
			{
				spinnerAdapter.add(db.Records.get(i).get(aliasfieldname)+"");
			}
		}
		spinnerAdapter.notifyDataSetChanged();
	}

	public static void isiSpinnerDenganSemua(Context context, Spinner combo, String tabel, String field, String kriteria)
	{
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item);
		spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
		combo.setAdapter(spinnerAdapter);
		DBAndroid db=new DBAndroid();
		db.getRecords("Select " + field + " from " + tabel + " where "+kriteria + " group by "+field);
		if (db.Records.size() != 0) {
			for (int i=0; i<db.Records.size(); i++)
			{
				spinnerAdapter.add(db.Records.get(i).get(field)+"");
			}
		}
		spinnerAdapter.add("<< SEMUA >>");
		spinnerAdapter.notifyDataSetChanged();
	}
	public static String getValue(String tabel, String field, String kriteria){
		DBAndroid db=new DBAndroid();
		db.getRecords("Select " + field + " as x from " + tabel + " where "+kriteria);
		if (db.Records.size() != 0) {
			return db.Records.get(0).get("x");
		} else return "";
	}
	public static String getValueAgregat(String tabel, String field, String kriteria){
		DBAndroid db=new DBAndroid();
		db.getRecords("Select " + field + " as X from " + tabel + " where "+kriteria);
		if (db.Records.size() != 0) {
			return db.Records.get(0).get("X");
		} else return "";
	}

	public static Boolean found(String tabel, String field, String kriteria){
		DBAndroid db=new DBAndroid();
		db.getRecords("Select " + field + " from " + tabel + " where "+kriteria);
		if (db.Records.size() != 0) {
			return true;
		} else return false;
	}


}

