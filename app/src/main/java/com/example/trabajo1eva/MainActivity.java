package com.example.trabajo1eva;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Empleado> empleados = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_window);

        String[] nombresEmpleados = getResources().getStringArray(R.array.empleadosNombre);
        int[] sueldosEmpleados = getResources().getIntArray(R.array.empleadosSueldo);
        GridLayout gridEmpleados = findViewById(R.id.gridEmpleados);
        gridEmpleados.setColumnCount(3);
        gridEmpleados.setRowCount(30);
        for (int i = 0; i < nombresEmpleados.length; i++) {
            Empleado empleado = new Empleado(nombresEmpleados[i], sueldosEmpleados[i]);
            CheckBox checkBox = new CheckBox(this);

            checkBox.setTag(empleado);
            empleados.add(empleado);

            TextView tvEmpleadoNombre = new TextView(this);
            tvEmpleadoNombre.setText(empleado.getNombre());

            TextView tvEmpleadoSueldo = new TextView(this);
            tvEmpleadoSueldo.setText(String.valueOf(empleado.getSueldo()));

            gridEmpleados.addView(checkBox);
            gridEmpleados.addView(tvEmpleadoNombre);
            gridEmpleados.addView(tvEmpleadoSueldo);
        }
    }
}
