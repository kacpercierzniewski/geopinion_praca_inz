package com.example.kacper.geopinion;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RootCheckerDialog extends DialogFragment
{
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
     setCancelable(false);
     return inflater.inflate(R.layout.dialog,null);
 }


}
