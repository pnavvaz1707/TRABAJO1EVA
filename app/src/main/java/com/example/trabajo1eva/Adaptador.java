package com.example.trabajo1eva;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Adaptador extends ArrayAdapter<Empleado> {
    Activity context;
    ArrayList<Empleado> empleados;

    public Adaptador(Activity context, ArrayList<Empleado> empleados) {
        super(context, R.layout.list_view_empleados, empleados);
        this.context = context;
        this.empleados = empleados;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.list_view_empleados, null);

        CheckBox checkBox = (CheckBox) item.findViewById(R.id.empleado_checkbox);
        checkBox.setTag(empleados.get(position));

        TextView tvEmpleadoNombre = (TextView) item.findViewById(R.id.empleado_nombre);
        tvEmpleadoNombre.setText(empleados.get(position).getNombre());

        TextView tvEmpleadoSueldo = (TextView) item.findViewById(R.id.empleado_sueldo);
        tvEmpleadoSueldo.setText(String.valueOf(empleados.get(position).getSueldo()));

        return item;
    }
}
