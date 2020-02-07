package ingsw.group1.findmyphone.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import ingsw.group1.findmyphone.R;

public class NavHolderActivity extends FragmentActivity {

    private Fragment currentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_holder);
    }

    /**
     * Method called to replace the currently displayed fragment.
     *
     * @param newFragment The new Fragment to display.
     */
    public void replaceFragment(Fragment newFragment) {
        currentFragment = newFragment;
        getSupportFragmentManager().beginTransaction().replace(
                R.id.root_layout,
                newFragment
        ).commit();
    }
}
