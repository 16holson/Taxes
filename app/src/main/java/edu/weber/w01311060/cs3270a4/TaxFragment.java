package edu.weber.w01311060.cs3270a4;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaxFragment extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DecimalFormat format = new DecimalFormat("00.00%");
    DecimalFormat formatN = new DecimalFormat("$00.00");
    private View root;
    private SeekBar seek;
    private TextView taxRate;
    private double taxRateValue;
    private TextView taxAmount;
    private double taxAmountValue;
    private double amountTotal;
    private onAmountUpdate mCallBack;

    public interface onAmountUpdate
    {
        void updateTotal(double total);
    }

    public TaxFragment()
    {
        taxRateValue = 0.0;
        taxAmountValue = 0.0;
        amountTotal = 0.0;
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Activity activity)
    {
        super.onAttach(activity);

        try
        {
            mCallBack = (onAmountUpdate) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + "must implement TaxFragment.AmountUpdate");
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaxFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaxFragment newInstance(String param1, String param2)
    {
        TaxFragment fragment = new TaxFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPause()
    {
        super.onPause();

        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = prefs.edit();

        prefEdit.putInt("seekbar", seek.getProgress());
        prefEdit.putLong("taxrate", Double.doubleToRawLongBits(taxRateValue));
        prefEdit.putLong("taxamount", Double.doubleToRawLongBits(taxAmountValue));
        prefEdit.putLong("amounttotal", Double.doubleToRawLongBits(amountTotal));

        prefEdit.commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_tax, container, false);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        int progress = prefs.getInt("seekbar", 0);
        seek = root.findViewById(R.id.seekBar);
        taxRate = root.findViewById(R.id.taxRate);
        taxAmount = root.findViewById(R.id.taxAmount);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                taxRateValue = i/400.0;
                taxRate.setText("" + format.format(taxRateValue));
                Log.d("Tax", "beforeUpdateAmountSlide: " + amountTotal);
                updateAmount(amountTotal);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        taxRateValue = Double.longBitsToDouble(prefs.getLong("taxrate", 0));
        taxAmountValue = Double.longBitsToDouble(prefs.getLong("taxamount", 0));
        amountTotal = Double.longBitsToDouble(prefs.getLong("amounttotal", 0));
        Log.d("Tax", "Setting amountTotal: " + amountTotal);
        seek.setProgress(progress);
        taxRate.setText("" + format.format(taxRateValue));
        taxAmount.setText("" + formatN.format(taxAmountValue));
    }

    public void updateAmount(double total)
    {
        amountTotal = total;
        taxAmountValue = amountTotal * taxRateValue;
        Log.d("Tax totalAmount", "totalAmount: " + amountTotal);
        Log.d("TaxAmountValue", "taxAmountValue: " + taxAmountValue);
        taxAmount.setText("" + formatN.format(taxAmountValue));
        mCallBack.updateTotal(amountTotal + taxAmountValue);

    }
}