package ingsw.group1.findmyphone.log.holders;

import android.content.res.Resources;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 *
 */
public abstract class LogViewHolderAbstract extends RecyclerView.ViewHolder {

    protected Resources resources;

    public LogViewHolderAbstract(View view, Resources resources) {
        super(view);
        this.resources = resources;
    }

}
