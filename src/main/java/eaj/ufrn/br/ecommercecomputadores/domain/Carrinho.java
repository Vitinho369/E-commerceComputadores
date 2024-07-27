package eaj.ufrn.br.ecommercecomputadores.domain;

import javax.script.Compilable;
import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private List<Computador> computadorList;

    public List<Computador> getComputadores() {
        return computadorList;
    }
    public void setProdutos(ArrayList<Computador> computador) {
        this.computadorList = computador;
    }
    public Computador getProduto (int id){
        Computador mp = null;
        for (Computador p : computadorList){
            if (p.getId() == id){
                return p;
            }
        }
        return mp;
    }
    public void removeProduto (int id){
        Computador p = getProduto(id);
        computadorList.remove(p);
    }
    public void addProduto (Computador p){
        computadorList.add(p);
    }

}
