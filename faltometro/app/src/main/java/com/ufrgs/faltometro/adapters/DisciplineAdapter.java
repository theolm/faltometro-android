package com.ufrgs.faltometro.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;
import com.ufrgs.faltometro.R;
import com.ufrgs.faltometro.vos.DisciplineVo;

import java.util.ArrayList;
import java.util.List;

import com.ufrgs.faltometro.viewholders.DisciplineViewHolder;

/**
 * Created by theo on 12/29/15.
 */
public class DisciplineAdapter extends RecyclerView.Adapter<DisciplineViewHolder> {

    private List<DisciplineVo> mList;
    private Context mContext;

    private static int HEADER_TYPE = 0;
    private static int OTHERS_TYPE = 1;


    public DisciplineAdapter(Context mContext) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
    }

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

//        holder.card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(mContext, AbsencesActivity_old.class);
//                i.putExtra("position", position);
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    ActivityOptionsCompat options = ActivityOptionsCompat.
//                            makeSceneTransitionAnimation((Activity) mContext , holder.card, mContext.getString(R.string.transition_card));
//                    mContext.startActivity(i, options.toBundle());
//                }
//                else {
//                    mContext.startActivity(i);
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void reloadAdapter(List<DisciplineVo> list){
        mList.clear();
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

