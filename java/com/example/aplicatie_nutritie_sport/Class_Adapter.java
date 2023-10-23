package com.example.aplicatie_nutritie_sport;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Class_Adapter extends BaseAdapter {
    private List<Produs> listaProduse;
    private Context context;
    private int layout;

    public Class_Adapter(List<Produs> listaProduse, Context context) {
        this.listaProduse = listaProduse;
        this.context = context;


    }

    public Class_Adapter(List<Produs> listaProduse, Context context, int layout) {
        this.listaProduse = listaProduse;
        this.context = context;
        this.layout = layout;


    }

    private static class Element {
        public TextView denumire;
        public TextView cantitate;
        public TextView calorii;
    }

    @Override
    public int getCount() {
        //numarul de inregistrari din lista mea
        return this.listaProduse.size();
    }

    @Override
    public Object getItem(int position) {
        // returneaza inregistrarea de pe pozitia i
        if (position >= 0 && position < this.getCount()) {
            return this.listaProduse.get(position);
        } else {
            throw new IllegalArgumentException("Parametru incorect");
        }
    }

    @Override
    public long getItemId(int position) {
        //returneaza id-ul de pe pozitia position
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //face maparea efectiva intre ob si layout

        //obtin un view pe baza unui layout
        LayoutInflater inflater = LayoutInflater.from(this.context);
        final View v = inflater.inflate(layout, parent, false);

        final Produs produs = (Produs) getItem(position);
        TextView tvDen = v.findViewById(R.id.tv_den);
        TextView tvCantitate = v.findViewById(R.id.tv_cantitate);
        TextView tvCalorii = v.findViewById(R.id.tv_calorii);

        tvDen.setText(" " + produs.getDenumire() + "- ");
        tvCalorii.setText(" "+produs.getCalorii() + "kcal\\");
        tvCantitate.setText(produs.getCantitate() + "g ");


        return v;
    }
}
