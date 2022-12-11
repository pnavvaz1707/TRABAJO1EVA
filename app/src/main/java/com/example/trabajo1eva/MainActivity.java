package com.example.trabajo1eva;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Empleado> empleados = new ArrayList<>();
    private TableLayout tbEmpleados;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_window);

        String[] nombresEmpleados = getResources().getStringArray(R.array.empleadosNombre);
        int[] sueldosEmpleados = getResources().getIntArray(R.array.empleadosSueldo);
        tbEmpleados = findViewById(R.id.tbEmpleados);
        for (int i = 0; i < nombresEmpleados.length; i++) {

            TableRow fila = new TableRow(this);

            Empleado empleado = new Empleado(nombresEmpleados[i], sueldosEmpleados[i]);
            CheckBox checkBox = new CheckBox(this);

            checkBox.setTag(empleado);
            empleados.add(empleado);

            TextView tvEmpleadoNombre = new TextView(this);
            tvEmpleadoNombre.setText(empleado.getNombre());

            TextView tvEmpleadoSueldo = new TextView(this);
            tvEmpleadoSueldo.setText(String.valueOf(empleado.getSueldo()));

            fila.addView(checkBox);
            fila.addView(tvEmpleadoNombre);
            fila.addView(tvEmpleadoSueldo);

            fila.setBackground(getDrawable(R.drawable.border));
            fila.setPadding(10, 15, 20, 15);
            tbEmpleados.addView(fila);
        }
        Button btnSortear = (Button) findViewById(R.id.btnSortear);
        btnSortear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, VentanaSorteo.class);
        Bundle bundle = new Bundle();

        ArrayList<View> checkboxs = tbEmpleados.getTouchables();
        ArrayList<Empleado> empleadosSel = new ArrayList<>();

        for (View checkbox : checkboxs) {
            CheckBox c = (CheckBox) checkbox;
            if (c.isChecked()) {
                empleadosSel.add((Empleado) c.getTag());
            }
        }
        bundle.putParcelableArrayList("empleadosSel", empleadosSel);
        i.putExtras(bundle);
        startActivity(i);
    }
}
