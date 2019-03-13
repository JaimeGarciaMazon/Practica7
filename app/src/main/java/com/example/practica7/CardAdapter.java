package com.example.practica7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.practica7.Model.Lugar;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends ArrayAdapter {
    private List <Lugar> listado = new ArrayList<>();

    static class CardViewHolder {
        TextView line1;
    }

    public CardAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }


    public void add(Lugar object) {
        listado.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.listado.size();
    }

    @Override
    public Lugar getItem(int index) {
        return (Lugar) this.listado.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_card, parent, false);
            CardViewHolder viewHolder = new CardViewHolder();
            viewHolder.line1 = row.findViewById(R.id.line1);
            Lugar p = getItem(position);
            viewHolder.line1.setText(p.getNombre());
            RatingBar rb = row.findViewById(R.id.ratingBar);
            rb.setRating(p.getValoracion());
            row.setTag(viewHolder);
        }
        return row;
    }

}
