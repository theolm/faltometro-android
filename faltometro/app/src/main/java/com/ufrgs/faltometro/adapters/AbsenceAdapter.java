package com.ufrgs.faltometro.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufrgs.faltometro.R;
import com.ufrgs.faltometro.support.DatabaseHandler;
import com.ufrgs.faltometro.viewholders.AbsenceItemViewHolder;
import com.ufrgs.faltometro.vos.AbsenceVo;
import com.ufrgs.faltometro.vos.DisciplineVo;

import java.util.List;

/**
 * Created by theo on 2/14/16.
 */
public class AbsenceAdapter extends RecyclerView.Adapter<AbsenceItemViewHolder> {

    private List<AbsenceVo> absenceVoList;
    private DisciplineVo disciplineVo;
    private Context mContext;

    public AbsenceAdapter (Context context, DisciplineVo discipline){
        mContext = context;
        absenceVoList = discipline.absenceVoList;
        disciplineVo = discipline;
    }

    @Override
    public AbsenceItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AbsenceItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_absence, parent, false));
    }

    @Override
    public void onBindViewHolder(AbsenceItemViewHolder holder, final int position) {
        AbsenceVo absenceVo = absenceVoList.get(position);
        holder.absenceDate.setText(absenceVo.date);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return absenceVoList.size();
    }

    public void updateList(DisciplineVo discipline){
        disciplineVo = discipline;
        absenceVoList = discipline.absenceVoList;
        notifyDataSetChanged();
    }

    private void showDeleteDialog(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getString(R.string.dialog_absence_delete_title));
        builder.setMessage(mContext.getString(R.string.dialog_absence_delete_message));
        builder.setPositiveButton("Remover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHandler db = new DatabaseHandler(mContext);
                db.removeAbsence(disciplineVo, absenceVoList.get(position));
                disciplineVo = db.getDiscipline(disciplineVo.id);
                absenceVoList = disciplineVo.absenceVoList;
                db.close();
                notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }
}
