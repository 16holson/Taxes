package edu.weber.w01311060.cs3270a4;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TotalsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TotalsFragment extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View root;
    private TextView totalAmount;
    private DecimalFormat format = new DecimalFormat("$00.00");
    private double value;

    public TotalsFragment()
    {
        value = 0.0;
        // Required empty public constructor
    }

    @Override
    public void onPause()
    {
        super.onPause();

        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = prefs.edit();

        prefEdit.putLong("total", Double.doubleToRawLongBits(value));

        prefEdit.commit();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TotalsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TotalsFragment newInstance(String param1, String param2)
    {
        TotalsFragment fragment = new TotalsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        totalAmount = root.findViewById(R.id.totalAmount);
        totalAmount.setText("" + format.format(value));
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        value = Double.longBitsToDouble(prefs.getLong("total", 0));
        totalAmount.setText("" + format.format(value));
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
        return root = inflater.inflate(R.layout.fragment_totals, container, false);
    }

    public void updateTotal(double total)
    {
        value = total;
        if(totalAmount != null)
        {
            totalAmount.setText("" + format.format(value));
        }
    }
}