package com.devbruto.treetorrah.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devbruto.treetorrah.Model.Registro;
import com.devbruto.treetorrah.R;

import java.util.ArrayList;
import java.util.List;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ListaViewHolder> {

    private Context context;
    private List<Registro> registroList = new ArrayList<>();
    private ListaViewHolder lvh;
    private Boolean filtroAno = false;
    private Boolean filtroEstado = false;

    public ListaAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ListaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        lvh = new ListaViewHolder(LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false));
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ListaViewHolder holder, int position) {

        Registro registro = registroList.get(position);

        holder.ano.setText(context.getString(R.string.ano_valor, Integer.toString(registro.getAno())));
        holder.arvoresRepostas.setText(context.getString(R.string.arvore_reposta_valor, Integer.toString(registro.getArvoresRespostas())));
        holder.volumeTotal.setText(context.getString(R.string.volume_valor, Integer.toString(registro.getVolume())));
        holder.arvoreCortada.setText(context.getString(R.string.arvore_cortada_valor, Integer.toString(registro.getArvoresCortadas())));
        holder.multa.setText(context.getString(R.string.multa_valor, Float.toString(registro.getValorMulta())));
        holder.estado.setText(context.getString(R.string.estado_valor, registro.getEstado()));

        if ((position % 2) == 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        }

        if (filtroAno) {
            holder.ano.setVisibility(View.INVISIBLE);
            holder.estado.setVisibility(View.VISIBLE);
        }
        if (filtroEstado) {
            holder.estado.setVisibility(View.INVISIBLE);
            holder.ano.setVisibility(View.VISIBLE);
        }
        if (!filtroEstado && !filtroAno){
            holder.ano.setVisibility(View.VISIBLE);
            holder.estado.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return registroList.size();
    }

    public void updateList(List<Registro> registroList) {
        this.registroList.clear();
        this.registroList.addAll(registroList);
        notifyDataSetChanged();
    }

    public void setFiltroAno(Boolean filtroAno) {
        this.filtroAno = filtroAno;
        notifyDataSetChanged();
    }

    public void setFiltroEstado(Boolean filtroEstado) {
        this.filtroEstado = filtroEstado;
        notifyDataSetChanged();
    }

    public void defaultFilter() {
        filtroAno = false;
        filtroEstado = false;
        notifyDataSetChanged();
    }

    class ListaViewHolder extends RecyclerView.ViewHolder {

        TextView multa;
        TextView ano;
        TextView arvoreCortada;
        TextView volumeTotal;
        TextView arvoresRepostas;
        TextView estado;

        public ListaViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View itemView) {
            multa = itemView.findViewById(R.id.tv_multa);
            ano = itemView.findViewById(R.id.tv_ano);
            arvoreCortada = itemView.findViewById(R.id.tv_arvore_cortadas);
            volumeTotal = itemView.findViewById(R.id.tv_volume);
            arvoresRepostas = itemView.findViewById(R.id.tv_arvore_repostas);
            estado = itemView.findViewById(R.id.tv_estado);
        }

    }
}
