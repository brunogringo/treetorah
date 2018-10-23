package com.devbruto.treetorrah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.devbruto.treetorrah.DAO.RegistroDAO;
import com.devbruto.treetorrah.Model.Registro;

public class Formulario extends AppCompatActivity {

    EditText txtAno, txtVolume, txtArvoreCortada, txtArvoreReposta;
    Spinner estadoSpinner;
    Button btnSalvar;
    Registro editarRegistro, registro;
    RegistroDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        registro = new Registro();
        dao = new RegistroDAO(Formulario.this);

        estadoSpinner = (Spinner) findViewById(R.id.estadoSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.estados_drop, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estadoSpinner.setAdapter(adapter);

        Intent intent = getIntent();
        editarRegistro = (Registro) intent.getSerializableExtra("registro-escolhido");

        txtAno = (EditText) findViewById(R.id.txtAno);
        txtVolume = (EditText) findViewById(R.id.txtVolume);
        txtArvoreCortada = (EditText) findViewById(R.id.txtArvoreCortada);
        txtArvoreReposta = (EditText) findViewById(R.id.txtArvoreReposta);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        if(editarRegistro != null){
            btnSalvar.setText("Modificar");

            txtAno.setText(Integer.toString(editarRegistro.getAno()));
            txtVolume.setText(Integer.toString(editarRegistro.getVolume()));
            txtArvoreCortada.setText(Integer.toString(editarRegistro.getArvoresCortadas()));
            txtArvoreReposta.setText(Integer.toString(editarRegistro.getArvoresRespostas()));
            estadoSpinner.setSelection(adapter.getPosition(editarRegistro.getEstado()));

        }else{
            btnSalvar.setText("Cadastrar");
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vol = Integer.parseInt(txtVolume.getText().toString());
                int arvReposta =  Integer.parseInt(txtArvoreReposta.getText().toString());

                if((vol * 6) == arvReposta)
                {
                    registro.setValorMulta(0);
                }
                else{
                    float total = (vol * 6) - arvReposta;
                    total = (total / 6) * 0.75f;
                    registro.setValorMulta(total);
                }

                registro.setAno(Integer.parseInt(txtAno.getText().toString()));
                registro.setArvoresRespostas(arvReposta);
                registro.setArvoresCortadas(Integer.parseInt(txtArvoreCortada.getText().toString()));
                registro.setVolume(vol);
                registro.setEstado(estadoSpinner.getSelectedItem().toString());

                if(btnSalvar.getText().toString().equals("Cadastrar")){
                    dao.salvarRegistro(registro);
                    dao.close();
                }
                else{
                    registro.setId(editarRegistro.getId());
                    dao.alterarRegistro(registro);
                    dao.close();
                }

                finish();
            }
        });
    }
}