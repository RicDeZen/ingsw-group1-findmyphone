package ingsw.group1.findmyphone.log;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.TestUtils;
import ingsw.group1.findmyphone.log.holders.LogViewHolder;
import ingsw.group1.findmyphone.log.holders.LogViewHolderBuilder;
import ingsw.group1.findmyphone.log.items.LogItem;
import ingsw.group1.findmyphone.log.items.LogItemFormatter;
import ingsw.group1.findmyphone.log.items.MapLinkListener;
import ingsw.group1.findmyphone.random.RandomGeoPositionGenerator;
import ingsw.group1.findmyphone.random.RandomSMSLogEventGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test class for {@link ingsw.group1.findmyphone.log.holders.LogViewHolderBuilder}.
 * Checking that various types of item flags result in the various kinds of behaviours.
 *
 * @author Riccardo De Zen.
 */
@RunWith(RobolectricTestRunner.class)
public class LogViewHolderBuilderTest {

    private static final int ITEM_LAYOUT_ID = R.layout.log_item;

    private static final String EXAMPLE_ADDRESS = "+3928818118";
    private static final LogItem FLAG_ITEM = new LogItem(
            EXAMPLE_ADDRESS,
            "",
            "",
            "",
            new ShapeDrawable(),
            0L,
            null
    );
    private static final LogItem POSITION_ITEM = new LogItem(
            EXAMPLE_ADDRESS,
            "",
            "",
            "",
            new ShapeDrawable(),
            0L,
            new RandomGeoPositionGenerator().getRandomPosition()
    );

    private LogViewHolderBuilder holderBuilder;
    private LogViewHolder logViewHolder;
    private Context context;
    private View testView;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    public MapLinkListener mockListener;

    @Before
    public void createView() {
        context = ApplicationProvider.getApplicationContext();
        holderBuilder = new LogViewHolderBuilder(context.getResources())
                .setMapLinkListener(mockListener);
        testView = LayoutInflater.from(context).inflate(ITEM_LAYOUT_ID, null);
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
        if (logItem == null) fail();

        logViewHolder = holderBuilder.build(testView, logItem.getFlags());
        logViewHolder.populate(logItem);

        boolean stateBeforeClick = logItem.getState();
        logViewHolder.itemView.findViewById(R.id.log_info_layout).callOnClick();
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
        if (logItem == null) fail();

        logViewHolder = holderBuilder.build(testView, logItem.getFlags());
        logViewHolder.populate(logItem);

        boolean stateBeforeClick = logItem.getState();
        logViewHolder.itemView.findViewById(R.id.log_info_layout).callOnClick();
        boolean stateAfterClick = logItem.getState();

        assertEquals(stateBeforeClick, stateAfterClick);
    }

    /**
     * Testing that a holder with no name shows the address as the name.
     */
    @Test
    public void addressReplacesNameIfNameless() {
        logViewHolder = holderBuilder.build(testView, FLAG_ITEM.getFlags());
        logViewHolder.populate(FLAG_ITEM);

        String actualName = ((TextView) logViewHolder.itemView.findViewById(R.id.log_textView_name))
                .getText().toString();

        assertEquals(EXAMPLE_ADDRESS, actualName);
    }

    /**
     * Testing that a holder with no name hides the address bar since it's not used.
     */
    @Test
    public void holderHidesAddressBarIfNameless() {
        logViewHolder = holderBuilder.build(testView, FLAG_ITEM.getFlags());
        logViewHolder.populate(FLAG_ITEM);
        TextView hiddenView = logViewHolder.itemView.findViewById(R.id.log_textView_address);
        assertEquals(View.GONE, hiddenView.getVisibility());
    }

    /**
     * Testing that a holder with an attached position fires the callback on the listener.
     */
    @Test
    public void callbackOnMapButtonClick() {
        logViewHolder = holderBuilder.build(testView, POSITION_ITEM.getFlags());
        logViewHolder.populate(POSITION_ITEM);
        logViewHolder.itemView.findViewById(R.id.map_link_Button).performClick();
        verify(mockListener, times(1)).onLinkOpened(any());
    }

    /**
     * Testing that a holder with a null position does not fire the callback.
     */
    @Test
    public void noCallbackIfNullPosition() {
        logViewHolder = holderBuilder.build(testView, FLAG_ITEM.getFlags());
        logViewHolder.populate(POSITION_ITEM);
        logViewHolder.itemView.findViewById(R.id.map_link_Button).performClick();
        verify(mockListener, times(0)).onLinkOpened(any());
    }

    /**
     * Testing that a holder with no position hides the Map button.
     */
    @Test
    public void holderHidesMapButtonIfNoPosition() {
        logViewHolder = holderBuilder.build(testView, FLAG_ITEM.getFlags());
        logViewHolder.populate(FLAG_ITEM);
        View hiddenView = logViewHolder.itemView.findViewById(R.id.map_link_Button);
        assertEquals(View.GONE, hiddenView.getVisibility());
    }

}
