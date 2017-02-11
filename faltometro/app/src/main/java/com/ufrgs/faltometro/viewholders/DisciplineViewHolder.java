package com.ufrgs.faltometro.viewholders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hookedonplay.decoviewlib.DecoView;
import com.ufrgs.faltometro.R;


/**
 * Created by theo on 2/13/16.
 */
public class DisciplineViewHolder extends RecyclerView.ViewHolder {

    public DecoView decoView;
    public CardView card;
    public TextView name;
    public TextView faults;
    public TextView days;
    public TextView time;

    public DisciplineViewHolder(View itemView) {
        super(itemView);

        decoView = (DecoView) itemView.findViewById(R.id.dynamicArcView);
        name = (TextView) itemView.findViewById(R.id.item_discipline_name);
        faults = (TextView) itemView.findViewById(R.id.item_discipline_faults);
        days = (TextView) itemView.findViewById(R.id.item_discipline_days);
        time = (TextView) itemView.findViewById(R.id.item_discipline_time);
        card = (CardView) itemView.findViewById(R.id.item_discipline_card);

    }
}
