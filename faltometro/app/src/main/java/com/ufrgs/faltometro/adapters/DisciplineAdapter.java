package com.ufrgs.faltometro.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.charts.SeriesLabel;
import com.hookedonplay.decoviewlib.events.DecoEvent;
import com.ufrgs.faltometro.R;
import com.ufrgs.faltometro.activities.AddDisciplineActivity;
import com.ufrgs.faltometro.support.DatabaseHandler;
import com.ufrgs.faltometro.vos.DisciplineVo;

import java.util.ArrayList;
import java.util.List;

import carbon.widget.Button;
import carbon.widget.CardView;

/**
 * Created by theo on 12/29/15.
 */
public class DisciplineAdapter extends RecyclerView.Adapter<DisciplineViewHolder> {

    public static List<DisciplineVo> mList = new ArrayList<>();
    private Context mContext;

    private static int HEADER_TYPE = 0;
    private static int OTHERS_TYPE = 1;


    public DisciplineAdapter(Context context, List<DisciplineVo> list){
        mContext = context;
        mList = list;
    }

    @Override
    public DisciplineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE)
            return new DisciplineViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_header, parent, false));
        else
            return new DisciplineViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return HEADER_TYPE;
        }
        else{
            return OTHERS_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(final DisciplineViewHolder holder, final int position) {

        final DisciplineVo vo = mList.get(position);


        holder.card.setBackgroundColor(Color.parseColor(vo.cor));
        holder.name.setText(vo.name);
        holder.days.setText(returnDays(vo.days));
        holder.time.setText(vo.time);
        holder.faults.setText(String.valueOf(vo.totalFaults) + " faltas de " + String.valueOf(vo.maxFaults));

        holder.decoView.configureAngles(360, 0);
        holder.decoView.addSeries(new SeriesItem.Builder(mContext.getResources().getColor(R.color.dark_icon))
                .setRange(0, vo.maxFaults, vo.maxFaults)
                .setInitialVisibility(true)
                .setLineWidth(24f)
                .setSpinDuration(6000)
                .build());



        //Create data series track
        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.parseColor("#90FFFFFF"))
                .setRange(0, vo.maxFaults, 0)
                .setInitialVisibility(false)
                .setLineWidth(20f)
                .build();

        int series1Index = holder.decoView.addSeries(seriesItem1);

        holder.decoView.addEvent(new DecoEvent.Builder(vo.totalFaults).setIndex(series1Index).setDelay(0).build());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void reloadAdapter(List<DisciplineVo> list){
        mList = list;
        notifyDataSetChanged();
    }

    private String returnDays (String days){
        String out = "";

        if (days.charAt(0) == '1')
            out = "Seg ";
        if (days.charAt(1) == '1')
            out = out + "Ter ";
        if (days.charAt(2) == '1')
            out = out + "Qua ";
        if (days.charAt(3) == '1')
            out = out + "Qui ";
        if (days.charAt(4) == '1')
            out = out + "Sex ";
        if (days.charAt(5) == '1')
            out = out + "Sab ";
        if (days.charAt(6) == '1')
            out = out + "Dom";

        if(days.contentEquals("0000000"))
            out = "Dias não informados";


        return out;
    }

    public DisciplineVo getDisciplineAt(int position){
        return mList.get(position);
    }

    public void removeDisciplineAt(int position) {
        mList.remove(position);
        notifyItemChanged(position);
    }
}

class DisciplineViewHolder extends RecyclerView.ViewHolder {

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
