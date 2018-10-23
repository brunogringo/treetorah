package com.devbruto.treetorrah;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.devbruto.treetorrah.DAO.RegistroDAO;
import com.devbruto.treetorrah.Model.Registro;
import com.devbruto.treetorrah.adapters.ListaAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView lista;
    RegistroDAO dao;
    ArrayList<Registro> lstResultado;
    Registro registro;
    ListaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (RecyclerView) findViewById(R.id.lstResultado);
        registerForContextMenu(lista);

//        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Registro registroEscolhido = (Registro) parent.getItemAtPosition(position);
//
//                Intent i = new Intent(MainActivity.this, Formulario.class);
//                i.putExtra("registro-escolhido", registroEscolhido);
//                startActivity(i);
//            }
//        });
//
//        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                registro = (Registro) parent.getItemAtPosition(position);
//                return false;
//            }
//        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Formulario.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Apagar");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dao = new RegistroDAO(MainActivity.this);
                dao.apagar(registro);
                dao.close();

                carregarLista();
                return true;
            }
        });
    }

    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    public void carregarLista() {
        dao = new RegistroDAO(MainActivity.this);
        lstResultado = dao.getLista();
        dao.close();

        if (lstResultado != null) {

            adapter = new ListaAdapter(this);
            adapter.updateList(lstResultado);
            lista.setLayoutManager(new LinearLayoutManager(this));
            lista.setAdapter(adapter);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.filtroAno) {
            adapter.updateList(dao.getListaAno());
            adapter.setFiltroEstado(true);
        } else if (id == R.id.filtroEstado) {
            adapter.updateList(dao.getListaEstado());
            adapter.setFiltroAno(true);

        } else if (id == R.id.todos) {
            adapter.updateList(dao.getLista());
            adapter.defaultFilter();
        }

        return true;
    }
}
