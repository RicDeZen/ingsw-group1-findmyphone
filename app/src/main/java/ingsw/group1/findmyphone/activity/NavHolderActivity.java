package ingsw.group1.findmyphone.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import ingsw.group1.findmyphone.R;

public class NavHolderActivity extends FragmentActivity {

    private static final String CURRENT_FRAGMENT_TAG = "CURRENT_FRAGMENT";

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
        //TODO a tag for the fragment should be passed to allow building of back stack
        getSupportFragmentManager().beginTransaction().replace(
                R.id.root_layout,
                newFragment,
                CURRENT_FRAGMENT_TAG
        ).commit();
    }
}
