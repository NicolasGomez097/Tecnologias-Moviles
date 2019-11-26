package org.moviles.activity.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.moviles.Context;
import org.moviles.activity.Adapters.ClimaAdapter;
import org.moviles.activity.R;
import org.moviles.business.ClimaBusiness;
import org.moviles.model.Ciudad;
import org.moviles.model.Clima;
import org.moviles.model.Configuracion;

import java.util.ArrayList;
import java.util.List;

public class FragmentClimaExtendido extends Fragment implements GestureDetector.OnGestureListener, View.OnTouchListener {

    private List<Clima> climaList;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private float MIN_SWIPE = 100;
    private Integer listIndex;
    private GestureDetector gestureDetector;
    private FragmentClimaExtendidoListener onClick;
    private Integer MAX_PAGES = 5;
    private boolean updateList;

    public interface FragmentClimaExtendidoListener{
        public void swipeRigth(Integer index);
        public void swipeLeft(Integer index);
    }

    public FragmentClimaExtendido(FragmentClimaExtendidoListener onClick,Integer listIndex, boolean updateList){
        this.onClick = onClick;
        this.listIndex = listIndex;
        this.updateList = updateList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contenedor = inflater.inflate(R.layout.fragment_clima_extendido,container,false);

        refreshLayout = contenedor.findViewById(R.id.refresh);

        recyclerView = contenedor.findViewById(R.id.contenedorClimaPorDia);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                actualizarLista();
            }
        });

        gestureDetector = new GestureDetector(this);

        recyclerView.setOnTouchListener(this);

        if(updateList)
            actualizarLista();
        else
            new getListaGuardadaAsyncTask().execute();

        return contenedor;
    }

    public void actualizarLista(){
        Configuracion conf = Context.getConfiguracionBusiness().getConfiguracion();
        Ciudad c = conf.getCiudad();
        if(c != null){
            if(!refreshLayout.isRefreshing())
                refreshLayout.setRefreshing(true);

            new updateListaAsyncTask(c).execute();

        }else{
            Toast.makeText(getContext(),"Seleccionar una ciudad",Toast.LENGTH_LONG).show();
        }
    }

    private void cargarListPart(){
        List<Clima> aux = new ArrayList<>();
        Integer dia;
        Integer idexAux = listIndex.intValue();

        if(climaList == null)
            return;

        dia = climaList.get(0).getDia();
        for(int i=0; i < climaList.size(); i++){
            if(idexAux == 0 && dia == climaList.get(i).getDia()){
                aux.add(climaList.get(i));
                continue;
            }
            if(dia != climaList.get(i).getDia()){
                dia = climaList.get(i).getDia();
                if(--idexAux < 0)
                    break;
            }
        }
        if(idexAux > 0)
            listIndex--;
        else
            recyclerView.setAdapter(new ClimaAdapter(aux));

    }

    private boolean hayMasPaginas(){
        return listIndex <= MAX_PAGES;
    }

    private class updateListaAsyncTask extends AsyncTask<Void,Void,Void>{

        private Ciudad ciudad;

        public updateListaAsyncTask(Ciudad ciudad){
            this.ciudad = ciudad;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ClimaBusiness climaBusiness = Context.getClimaBusiness(getActivity().getApplication());

            climaList = climaBusiness.getListaClima(getContext(),ciudad);
            if(climaList == null){
                climaList = climaBusiness.getListaClimaGuardado();
            }else{
                climaBusiness.limpiarExtendidoDB();
                for(Clima clima:climaList){
                    climaBusiness.insertarClima(clima);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            cargarListPart();
            if(refreshLayout.isRefreshing())
                    refreshLayout.setRefreshing(false);
        }
    }

    private class getListaGuardadaAsyncTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            ClimaBusiness climaBusiness = Context.getClimaBusiness(getContext());
            climaList  = climaBusiness.getListaClimaGuardado();
            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            cargarListPart();
        }
    }

    @Override
    public boolean onFling(MotionEvent pos2, MotionEvent pos1, float velocityX, float velocityY) {
        float diffX = pos2.getX()-pos1.getX();
        if(Math.abs(diffX) < MIN_SWIPE)
            return false;

        if(diffX > 0)
            swipeRigth();
        else
            swipeLeft();

        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    private void swipeRigth(){
        listIndex++;
        if(hayMasPaginas())
            onClick.swipeRigth(listIndex);
    }

    private void swipeLeft(){
        if(listIndex > 0){
            listIndex--;
            onClick.swipeLeft(listIndex);
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
