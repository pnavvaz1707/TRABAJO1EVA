package com.example.trabajo1eva;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class VentanaSorteo extends AppCompatActivity implements View.OnClickListener {

    private Button btnEmpezarSorteo;
    private TableLayout tbSorteo;
    private ArrayList<Empleado> empleadosSel;
    private ArrayList<Empleado> empleadosTotal;
    private ArrayList<Plaza> plazas;

    private Spinner spinnerEmpleados;
    private Spinner spinnerPlazas;
    private LinearLayout layoutFiltrar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sorteo_window);

        Bundle bundle = getIntent().getExtras();
        empleadosSel = bundle.getParcelableArrayList("empleadosSel");
        empleadosTotal = bundle.getParcelableArrayList("empleadosTotal");
        plazas = new ArrayList<>();

        spinnerEmpleados = findViewById(R.id.spinnerEmpleados);
        spinnerPlazas = findViewById(R.id.spinnerPlazas);

        tbSorteo = findViewById(R.id.tbSorteo);

        for (Empleado empleado : empleadosSel) {
            TableRow fila = new TableRow(this);

            TextView tvEmpleadoNombre = new TextView(this);
            tvEmpleadoNombre.setText(empleado.getNombre());
            tvEmpleadoNombre.setPadding(0, 0, 100, 0);

            TextView tvEmpleadoSueldo = new TextView(this);
            tvEmpleadoSueldo.setText(String.valueOf(empleado.getSueldo()));

            fila.addView(tvEmpleadoNombre);
            fila.addView(tvEmpleadoSueldo);

            fila.setBackground(getDrawable(R.drawable.border));
            fila.setPadding(30, 15, 30, 15);
            tbSorteo.addView(fila);
        }
        btnEmpezarSorteo = findViewById(R.id.btnEmpezarSorteo);
        btnEmpezarSorteo.setOnClickListener(this);

        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(this);

        layoutFiltrar = findViewById(R.id.layoutFiltrar);

        Button btnFiltrarEmpleado = findViewById(R.id.btnFiltrarEmpleado);
        btnFiltrarEmpleado.setOnClickListener(this);

        Button btnFiltrarPlaza = findViewById(R.id.btnFiltrarPlazas);
        btnFiltrarPlaza.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int idBtnPulsado = view.getId();
        if (idBtnPulsado == R.id.btnEmpezarSorteo) {

            tbSorteo.removeAllViews();

            TableLayout tbGanadores = findViewById(R.id.tbGanadores);
            tbGanadores.setVisibility(View.VISIBLE);

            ArrayList<Integer> numerosGanadores = generarNumerosRandom(10, 0, empleadosSel.size());

            ArrayList<Empleado> empleadosSpinner = new ArrayList<>();
            empleadosSpinner.add(new Empleado(getString(R.string.primer_item_spinner_empleado_txt), 0));
            empleadosSpinner.addAll(empleadosSel);
            spinnerEmpleados.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, empleadosSpinner));

            plazas.add(new Plaza(getString(R.string.primer_item_spinner_plaza_txt)));

            for (int i = 0; i < empleadosSel.size(); i++) {
                TableRow fila = new TableRow(this);

                TextView tvEmpleadoNombre = new TextView(this);
                tvEmpleadoNombre.setText(empleadosSel.get(i).getNombre());
                tvEmpleadoNombre.setPadding(0, 0, 100, 0);

                fila.addView(tvEmpleadoNombre);

                TextView tvEmpleadoPlaza = new TextView(this);

                if (numerosGanadores.contains(i)) {
                    Plaza plaza = new Plaza();
                    plazas.add(plaza);
                    empleadosSel.get(i).setPlaza(plaza);

                    tvEmpleadoPlaza.setText(String.valueOf(empleadosSel.get(i).getPlaza().getNumPlaza()));
                    fila.setBackground(getDrawable(R.drawable.border_ganador_sorteo));
                } else {
                    fila.setBackground(getDrawable(R.drawable.border));
                }

                fila.addView(tvEmpleadoPlaza);

                fila.setPadding(30, 15, 30, 15);
                tbGanadores.addView(fila);
            }

            spinnerPlazas.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, plazas));

            btnEmpezarSorteo.setEnabled(false);
            layoutFiltrar.setVisibility(View.VISIBLE);

        } else if (idBtnPulsado == R.id.btnVolver) {
            Plaza.numPlaza = 0;
            Intent i = new Intent(this, MainActivity.class);
            Bundle bundle = new Bundle();

            bundle.putParcelableArrayList("empleadosTotal",empleadosTotal);
            i.putExtras(bundle);

            startActivity(i);

        } else if (idBtnPulsado == R.id.btnFiltrarEmpleado) {
            Empleado empleadoSel = (Empleado) spinnerEmpleados.getSelectedItem();

            if (!empleadoSel.toString().endsWith(getString(R.string.primer_item_spinner))) {
                if (empleadoSel.getPlaza() != null){
                    new AlertDialog.Builder(this).setMessage(getString(R.string.filtrar_empleado_txt1) + empleadoSel.getNombre() + getString(R.string.filtrar_empleado_txt2) + empleadoSel.getPlaza().getNombrePlaza()).show();
                }else {
                    new AlertDialog.Builder(this).setMessage(getString(R.string.filtrar_empleado_txt1) + empleadoSel.getNombre() + getString(R.string.filtrar_empleado_txt3)).show();
                }
            }

        } else {
            Plaza plazaSel = (Plaza) spinnerPlazas.getSelectedItem();

            if (!plazaSel.toString().endsWith(getString(R.string.primer_item_spinner))) {

                for (Empleado empleado : empleadosSel) {

                    if (empleado.getPlaza() != null) {

                        if (empleado.getPlaza().getNombrePlaza().equals(plazaSel.getNombrePlaza())) {
                            new AlertDialog.Builder(this).setMessage(getString(R.string.filtrar_plaza_txt1) + plazaSel.getNombrePlaza() + getString(R.string.filtrar_plaza_txt2) + empleado.getNombre()).show();
                        }
                    }
                }
            }
        }
    }

    private ArrayList<Integer> generarNumerosRandom(int cantidad, int limiteInferior, int limiteSuperior) {
        ArrayList<Integer> numRandoms = new ArrayList<>();
        int contador = 0;
        if (limiteSuperior < cantidad) {
            cantidad = limiteSuperior;
        }
        while (contador < cantidad) {
            int numRandom = (int) (Math.random() * (limiteSuperior - limiteInferior) + limiteInferior);
            if (!numRandoms.contains(numRandom)) {
                numRandoms.add(numRandom);
                contador++;
            }
        }
        return numRandoms;
    }
}
