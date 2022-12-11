package com.example.trabajo1eva;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Empleado> empleados = new ArrayList<>();
    private ListView lstEmpleados;
    private EditText etNombreEmpleado;
    private EditText etSueldoEmpleado;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_window);

        lstEmpleados = findViewById(R.id.lstEmpleados);

        Bundle bundle;
        if ((bundle = getIntent().getExtras()) != null) {
            empleados = bundle.getParcelableArrayList("empleadosTotal");
            actualizarListaEmpleados();

        } else {

            String[] nombresEmpleados = getResources().getStringArray(R.array.empleadosNombre);
            int[] sueldosEmpleados = getResources().getIntArray(R.array.empleadosSueldo);

            for (int i = 0; i < nombresEmpleados.length; i++) {

                Empleado empleado = new Empleado(nombresEmpleados[i], sueldosEmpleados[i]);

                empleados.add(empleado);
            }
            actualizarListaEmpleados();
        }

        Button btnSortear = (Button) findViewById(R.id.btnSortear);
        btnSortear.setOnClickListener(this);

        Button btnAnadirEmpleado = (Button) findViewById(R.id.btnAnadirEmpleado);
        btnAnadirEmpleado.setOnClickListener(this);

        etNombreEmpleado = (EditText) findViewById(R.id.et_nombre_form);
        etSueldoEmpleado = (EditText) findViewById(R.id.et_sueldo_form);
    }

    private void actualizarListaEmpleados() {
        Adaptador adaptador = new Adaptador(this, empleados);
        lstEmpleados.setAdapter(adaptador);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSortear) {
            Intent i = new Intent(this, VentanaSorteo.class);
            Bundle bundle = new Bundle();

            ArrayList<View> filas = lstEmpleados.getTouchables();
            ArrayList<Empleado> empleadosSel = new ArrayList<>();

            for (View fila : filas) {

                CheckBox checkBox = (CheckBox) fila.getTouchables().get(0);
                if (checkBox.isChecked()) {

                    if (!empleadosSel.contains((Empleado) checkBox.getTag())) {
                        empleadosSel.add((Empleado) checkBox.getTag());
                    }
                }
            }
            bundle.putParcelableArrayList("empleadosTotal", empleados);
            bundle.putParcelableArrayList("empleadosSel", empleadosSel);
            i.putExtras(bundle);
            startActivity(i);

        } else {
            if (!etNombreEmpleado.toString().equals("") && !etSueldoEmpleado.toString().equals("")) {
                empleados.add(new Empleado(etNombreEmpleado.getText().toString(), Integer.parseInt(etSueldoEmpleado.getText().toString())));
                actualizarListaEmpleados();
            }
        }
    }
}
