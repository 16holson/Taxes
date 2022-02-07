package edu.weber.w01311060.cs3270a4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements ItemsFragment.onAmountChange, TaxFragment.onAmountUpdate
{
    private FragmentManager fm;
    private TaxFragment tf;
    private TotalsFragment tsf;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        fm = getSupportFragmentManager();

        fm.beginTransaction()
                .replace(R.id.totalsFragmentContainer, new TotalsFragment(), "totalsFrag")
                .replace(R.id.taxFragmentContainer, new TaxFragment(), "taxFrag")
                .replace(R.id.itemsFragmentContainer, new ItemsFragment(), "itemsFrag")
                .commit();
    }

    @Override
    public void changeAmount(BigDecimal one, BigDecimal two, BigDecimal three, BigDecimal four)
    {
        if(tf == null)
        {
            tf = (TaxFragment) fm.findFragmentByTag("taxFrag");
        }
        if(tf != null)
        {
            tf.updateAmount(one.add(two).add(three).add(four));
        }

    }


    @Override
    public void updateTotal(BigDecimal total)
    {
        if(tsf == null)
        {
            tsf = (TotalsFragment) fm.findFragmentByTag("totalsFrag");
        }
        if(tsf != null)
        {
            tsf.updateTotal(total);
        }
    }
}