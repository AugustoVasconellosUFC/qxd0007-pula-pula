import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PulaPula {

    private final List <Crianca> filaDeEspera = new ArrayList<>();

    private final List <Crianca> criancasPulando = new ArrayList<>();

    private final HashMap <String, Double> contas= new HashMap<>();

    private final int limiteMax;

    private double caixa = 0.00;

    public PulaPula(int limiteMax){
        this.limiteMax = limiteMax;
    }

    public List<Crianca> getFilaDeEspera() {
        return filaDeEspera;
    }

    public List<Crianca> getCriancasPulando() {
        return criancasPulando;
    }

    public int getLimiteMax() {
        return limiteMax;
    }

    public double getCaixa() {
        return caixa;
    }

    public Double getConta(String name){
        if(contas.get(name) == null || contas.get(name) < 2.500000)
            return null;
        else
            return contas.get(name);
    }

    public boolean entrarNaFila(Crianca crianca){
        if(!contas.containsKey(crianca.getName())) {
            contas.put(crianca.getName(), 0.00);
            filaDeEspera.add(crianca);
            return true;
        }
        else
            return false;
    }

    public boolean entrar(){
        if(criancasPulando.size() < limiteMax && filaDeEspera.size() > 0){
            String name = filaDeEspera.get(0).getName();
            contas.put(name, contas.get(name) + 2.50);
            criancasPulando.add(filaDeEspera.get(0));
            filaDeEspera.remove(0);
            return true;
        }
        else
            return false;
    }

    public boolean sair(){
        if(!criancasPulando.isEmpty()) {
            filaDeEspera.add(criancasPulando.get(0));
            criancasPulando.remove(0);
            return true;
        }
        else
            return false;
    }

    public boolean papaiChegou(String name){
        if(contas.containsKey(name)){
            caixa += contas.get(name);
            contas.remove(name);
            for(int i = 0; i < filaDeEspera.size(); i++){
                if(Objects.equals(filaDeEspera.get(i).getName(), name)) {
                    filaDeEspera.remove(i);
                    break;
                }
            }
            for(int i = 0; i < criancasPulando.size(); i++){
                if(Objects.equals(criancasPulando.get(i).getName(), name)) {
                    criancasPulando.remove(i);
                    break;
                }
            }
            return true;
        }
        else
            return false;
    }

    public double fechar(){
        criancasPulando.clear();
        filaDeEspera.clear();
        for(double d : contas.values()){
            caixa += d;
        }
        contas.clear();
        return caixa;
    }
}