package edu.weber.w01311060.cs3270a4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

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
    public void changeAmount(double one, double two, double three, double four)
    {
        if(tf == null)
        {
            tf = (TaxFragment) fm.findFragmentByTag("taxFrag");
        }
        if(tf != null)
        {
            double total = one + two + three + four;
            tf.updateAmount(total);
        }

    }


    @Override
    public void updateTotal(double total)
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