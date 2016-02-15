package com.ufrgs.faltometro.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufrgs.faltometro.R;

/**
 * Created by theo on 2/14/16.
 */
public class AbsenceItemViewHolder extends RecyclerView.ViewHolder {

    public TextView absenceDate;
    public ImageView deleteButton;

    public AbsenceItemViewHolder(View itemView) {
        super(itemView);

        absenceDate = (TextView) itemView.findViewById(R.id.item_absence_name);
        deleteButton = (ImageView) itemView.findViewById(R.id.item_absence_delete);
    }
}
