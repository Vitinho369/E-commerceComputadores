package eaj.ufrn.br.ecommercecomputadores.domain;

import javax.script.Compilable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Carrinho {
    private List<Computador> computadorList = new ArrayList<Computador>();

    public List<Computador> getComputadores() {
        return computadorList;
    }
    public void setComputadores(ArrayList<Computador> computador) {
        this.computadorList = computador;
    }
    public Optional<Computador> getComputador (int id){
        Optional<Computador> mp = computadorList.stream().filter(computador -> computador.getId() == id).findFirst();
        return mp;
    }
    public void removeComputador (int id){
        Optional<Computador> p = getComputador(id);
        computadorList.remove(p.get());
    }
    public void addComputador (Computador p){

        computadorList.add(p);
    }

}
