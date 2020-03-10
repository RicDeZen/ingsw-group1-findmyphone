package ingsw.group1.findmyphone.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import ingsw.group1.findmyphone.R;

/**
 * Fragment representing the pages of the instructions for the app.
 * The Fragments are delivered through an Object Pool, so they cannot be instantiated
 * automatically by the Android system.
 * Due to the fact the Pool recycles the Fragments, each instance of each page shares its
 * internal data.
 *
 * @author Riccardo De Zen.
 */
public final class InstructionsPageFragment extends Fragment {
    /**
     * The resource ids for the page icons.
     */
    private static final int[] PAGE_ICONS = {
            R.drawable.mail_icon,
            R.drawable.key_icon,
            R.drawable.speaker_icon,
            R.drawable.round_people_alt_24,
            R.drawable.round_schedule_24,
    };

    /**
     * The resource ids for the page text.
     */
    private static final int[] PAGE_TEXT = {
            R.string.instruction_page_0,
            R.string.instruction_page_1,
            R.string.instruction_page_2,
            R.string.instruction_page_3,
            R.string.instruction_page_4,
    };

    /**
     * Map containing the instantiated fragments, with their corresponding page number as key.
     * The pages are numbered from 0 to {@link InstructionsPageFragment#PAGE_COUNT} - 1.
     */
    private static Map<Integer, InstructionsPageFragment> activeFragments = new HashMap<>();

    /**
     * The number of available pages.
     */
    private static final int PAGE_COUNT = PAGE_ICONS.length;

    /**
     * The icon resource id for this Fragment.
     */
    private int iconId;

    /**
     * The string resource id for this Fragment.
     */
    private int textId;

    /**
     * The actual Drawable to display.
     */
    private Drawable pageIcon;

    /**
     * The actual Spanned String to display. Is assumed to already be ready to be passed to
     * {@link android.widget.TextView#setText(CharSequence)}.
     */
    private Spanned pageText;

    /**
     * Private constructor, assigns the correct ids to the Fragment based on the page number.
     *
     * @param page The page number.
     * @throws ArrayIndexOutOfBoundsException if the page number is lower than 0 or higher than
     *                                        {@link InstructionsPageFragment#PAGE_COUNT} - 1.
     */
    private InstructionsPageFragment(int page) throws ArrayIndexOutOfBoundsException {
        iconId = PAGE_ICONS[page];
        textId = PAGE_TEXT[page];
    }

    /**
     * Method called when the Fragment is attached, caches the resources for the Fragment's view.
     *
     * @param context The calling context.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        pageIcon = context.getDrawable(iconId);
        pageText = formatHtmlString(context.getString(textId));
    }

    /**
     * Method returning the appropriately formatted spanned string based on the api level.
     *
     * @param aString The String to format.
     * @return The {@link Spanned} Object built with the appropriate method based on api level.
     */
    private Spanned formatHtmlString(String aString) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
            return Html.fromHtml(aString);
        else
            return Html.fromHtml(aString, Html.FROM_HTML_MODE_COMPACT);
    }

    /**
     * The View is created and the children are populated.
     *
     * @param inflater           The inflater to use when inflating.
     * @param container          The container for this fragment's view.
     * @param savedInstanceState Any saved instance state parameters.
     * @return The inflated View.
     */
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.instructions_page_fragment, container, false);

        ImageView iconView = root.findViewById(R.id.instructions_icon);
        iconView.setImageDrawable(pageIcon);

        TextView pageTextView = root.findViewById(R.id.instructions_page_text);
        pageTextView.setText(pageText);

        return root;
    }

    /**
     * Only intended way to retrieve one of these Fragments.
     *
     * @param page The page number. Must be ranging from 0 to
     *             {@link InstructionsPageFragment#PAGE_COUNT} - 1 in order to retrieve a valid
     *             Fragment.
     * @return The Fragment corresponding to the requested page, or {@code null} if the page
     * number is invalid.
     */
    @Nullable
    public static InstructionsPageFragment getFragment(int page) {
        if (page < 0 || page > PAGE_COUNT - 1)
            return null;
        if (activeFragments.get(page) != null)
            return activeFragments.get(page);
        InstructionsPageFragment newFragment = new InstructionsPageFragment(page);
        activeFragments.put(page, newFragment);
        return newFragment;
    }

    /**
     * Method returning the number of possible pages for the instructions.
     *
     * @return {@link InstructionsPageFragment#PAGE_ICONS}.
     */
    public static int getPageCount() {
        return PAGE_COUNT;
    }

    /**
     * This method clear the internal reference for the Fragments, allowing them to be garbage
     * collected.
     */
    public static void clearFragments() {
        activeFragments.clear();
    }
}
