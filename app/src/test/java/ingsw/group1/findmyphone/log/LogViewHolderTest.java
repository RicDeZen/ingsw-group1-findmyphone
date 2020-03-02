package ingsw.group1.findmyphone.log;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.TestUtils;
import ingsw.group1.findmyphone.random.RandomSMSLogEventGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

/**
 * Test class for {@link LogViewHolder}.
 * Not much to test, just the expand on touch behaviour of the items.
 *
 * @author Riccardo De Zen.
 */
@RunWith(RobolectricTestRunner.class)
public class LogViewHolderTest {

    private static final int ITEM_LAYOUT_ID = R.layout.log_item;

    private LogViewHolder logViewHolder;
    private Context context;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void createHolder() {
        context = ApplicationProvider.getApplicationContext();
        View testView = LayoutInflater.from(context)
                .inflate(ITEM_LAYOUT_ID, null);
        logViewHolder = new LogViewHolder(testView, context.getResources());
    }

    /**
     * Method to clean up the Contact database.
     */
    @After
    public void cleanup() {
        // Necessary due to an issue with singleton SQL databases.
        TestUtils.resetContactManager();
    }

    /**
     * Testing a click on the holder's view changes the item's state, if the item should expand.
     */
    @Test
    public void holderExpandsExpandable() {
        LogItem logItem = new LogItemFormatter(context)
                .formatItem(new RandomSMSLogEventGenerator().getRandomEvent());
        //Item should expand cause the event is successful
        if (logItem == null || !logItem.shouldExpand()) fail();
        logViewHolder.populate(logItem, false);

        boolean stateBeforeClick = logItem.getState();
        logViewHolder.itemView.callOnClick();
        boolean stateAfterClick = logItem.getState();

        assertNotEquals(stateBeforeClick, stateAfterClick);
    }

    /**
     * Testing a click on the holder's view does not change the state of the item if the item
     * should not expand.
     */
    @Test
    public void holderDoesNotExpandNonExpandable() {
        LogItem logItem = new LogItemFormatter(context)
                .formatItem(new RandomSMSLogEventGenerator().getRandomFailedEvent());
        //Item should NOT expand cause the event is failed
        if (logItem == null || logItem.shouldExpand()) fail();
        logViewHolder.populate(logItem, false);

        boolean stateBeforeClick = logItem.getState();
        logViewHolder.itemView.callOnClick();
        boolean stateAfterClick = logItem.getState();

        assertEquals(stateBeforeClick, stateAfterClick);
    }

}
