package eaj.ufrn.br.ecommercecomputadores.domain;

import org.springframework.data.util.Optionals;

import javax.script.Compilable;
import javax.swing.text.html.Option;
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
    public Optional<Computador> getComputador (Long id){
        for (Computador c : computadorList){
            if (c.getId().equals(id)){
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }
    public void removeComputador (Long id){
        for (Computador c : computadorList){
            if (c.getId().equals(id)){
                c.setQtd(c.getQtd()-1);

                if(c.getQtd() == 0) {
                    computadorList.remove(c);
                }
                return;
            }
        }
    }
    public void addComputador (Computador p){
        Optional<Computador> compCarrinho = getComputador(p.getId());
        if(compCarrinho.isPresent()){
            int novaQtd = compCarrinho.get().getQtd()+1;
            compCarrinho.get().setQtd(novaQtd);
            return;
        }
        p.setQtd(1);
        computadorList.add(p);
    }

    public float totalCompra(){
        float totalCompra = 0;
        for(Computador c : computadorList){
            totalCompra += c.getPreco() * c.getQtd();
        }

        return totalCompra;
    }

}
