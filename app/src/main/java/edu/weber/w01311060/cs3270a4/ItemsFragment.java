package edu.weber.w01311060.cs3270a4;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.math.BigDecimal;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemsFragment extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View root;
    private EditText text1;
    private EditText text2;
    private EditText text3;
    private EditText text4;
    private BigDecimal text1Value;
    private BigDecimal text2Value;
    private BigDecimal text3Value;
    private BigDecimal text4Value;
    private onAmountChange mCallBack;

    public interface onAmountChange
    {
        void changeAmount(BigDecimal one, BigDecimal two, BigDecimal three, BigDecimal four);
    }

    public ItemsFragment()
    {
        text1Value = new BigDecimal(0);
        text2Value = new BigDecimal(0);
        text3Value = new BigDecimal(0);
        text4Value = new BigDecimal(0);
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemsFragment newInstance(String param1, String param2)
    {
        ItemsFragment fragment = new ItemsFragment();
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

        prefEdit.putString("text1", text1Value.toString());
        prefEdit.putString("text2", text2Value.toString());
        prefEdit.putString("text3", text3Value.toString());
        prefEdit.putString("text4", text4Value.toString());

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
        return root = inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onAttach(@NonNull Activity activity)
    {
        super.onAttach(activity);

        try
        {
            mCallBack = (onAmountChange) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + "must implement TaxFragment.AmountUpdate");
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);

        text1 = root.findViewById(R.id.editText1);
        text2 = root.findViewById(R.id.editText2);
        text3 = root.findViewById(R.id.editText3);
        text4 = root.findViewById(R.id.editText4);

        TextWatcher watcher = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                text1Value = new BigDecimal(0);
                text2Value = new BigDecimal(0);
                text3Value = new BigDecimal(0);
                text4Value = new BigDecimal(0);
                if(!text1.getText().toString().isEmpty())
                {
                    text1Value = BigDecimal.valueOf(Double.parseDouble(text1.getText().toString()));
                }
                if(!text2.getText().toString().isEmpty())
                {
                    text2Value = BigDecimal.valueOf(Double.parseDouble(text2.getText().toString()));
                }
                if(!text3.getText().toString().isEmpty())
                {
                    text3Value = BigDecimal.valueOf(Double.parseDouble(text3.getText().toString()));
                }
                if(!text4.getText().toString().isEmpty())
                {
                    text4Value = BigDecimal.valueOf(Double.parseDouble(text4.getText().toString()));
                }
                mCallBack.changeAmount(text1Value, text2Value, text3Value, text4Value);
            }
        };

        text1.setText(prefs.getString("text1", String.valueOf(0)));
        text2.setText(prefs.getString("text2", String.valueOf(0)));
        text3.setText(prefs.getString("text3", String.valueOf(0)));
        text4.setText(prefs.getString("text4", String.valueOf(0)));

        text1.addTextChangedListener(watcher);
        text2.addTextChangedListener(watcher);
        text3.addTextChangedListener(watcher);
        text4.addTextChangedListener(watcher);

    }
}