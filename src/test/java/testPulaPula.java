import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;

public class testPulaPula {
    
    @Test
    public void testInicializacao(){
        PulaPula pulaPula = new PulaPula(5);
        assertEquals(5, pulaPula.getLimiteMax(),
            "Ao inicializar um pula-pula ela deve vir com o limite exato ao que foi passado na inicialização.");
        assertEquals(0, pulaPula.getCaixa(),
            "Ao inicializar um pula-pula ela deve vir com o caixa zerado.");
        assertTrue(pulaPula.getFilaDeEspera().isEmpty(), "Ao inicializar o pula-pula não deve haver ninguém na fila de espera.");
        assertTrue(pulaPula.getCriancasPulando().isEmpty(), "Ao inicializar o pula-pula não deve haver ninguém dentro do pula-pula.");
    }

    @Test
    public void testPrimeiroDaFila(){
        PulaPula pulaPula = new PulaPula(5);
        pulaPula.entrarNaFila(new Crianca("maria", 6));
        Crianca crianca = new Crianca("ana", 7);
        pulaPula.entrarNaFila(crianca);
        assertEquals(crianca, pulaPula.getFilaDeEspera().get(pulaPula.getFilaDeEspera().size() - 1),
            "Quando uma crianca chega na fila ela deve ser insirida na última posição.");
    }

    @Test
    public void testEntrarNoPulaPulaSemNinguemNaFila(){
        PulaPula pulaPula = new PulaPula(5);
        assertFalse(pulaPula.entrar(),
            "Não deve ser possível entrar no pula-pula se não estiver ninguém na fila.");
    }

    @Test
    public void testAdicionarDuasCriancasComNomeIgual(){
        PulaPula pulaPula = new PulaPula(1);
        assertTrue(pulaPula.entrarNaFila(new Crianca("pedro", 7)),
            "Deve ser possível entrar no pula-pula se não houver nenhuma restrição");
        assertFalse(pulaPula.entrarNaFila(new Crianca("pedro", 3)),
            "Não deve ser possível uma crianca entrar na fila se já existe outra crianca na fila ou no pula-pula");
    }

    @Test
    public void testEntrarNoPulaPulaNoLimiteMaximo(){
        PulaPula pulaPula = new PulaPula(1);
        pulaPula.entrarNaFila(new Crianca("maria", 6));
        pulaPula.entrarNaFila(new Crianca("ana", 3));
        assertTrue(pulaPula.entrar(),
            "Se o limite do pula-pula não foi alcançado, deve ser possível entrar no pula-pula.");
        assertFalse(pulaPula.entrar(),
            "Não deve ser possível entrar no pula-pula se o limite máximo ja foi alcançado.");
    }

    @Test
    public void testPrimeiroDaFilaEntraNoPulaPula(){
        PulaPula pulaPula = new PulaPula(1);
        Crianca crianca = new Crianca("maria", 6);
        pulaPula.entrarNaFila(crianca);
        pulaPula.entrarNaFila(new Crianca("ana", 3));
        assertTrue(pulaPula.entrar(),
            "Deve ser possível entrar no pula-pula se não houver nenhuma restrição");
        assertEquals(crianca, pulaPula.getCriancasPulando().get(0),
            "Quando uma crianca entra no pula-pula ela tem necessiaremente tem quer ser a primeira da fila.");
    }

    @Test
    public void testEntraNoPulaPulaSaiDaFila(){
        PulaPula pulaPula = new PulaPula(1);
        pulaPula.entrarNaFila(new Crianca("ana", 3));
        assertTrue(pulaPula.entrar(),
            "Se o limite do pula-pula não foi alcançado, deve ser possível entrar no pula-pula.");
        assertEquals(0, pulaPula.getFilaDeEspera().size(),
            "Quando uma crianca entra no pula-pula, ela automaticamente sai da fila de espera.");
    }

    @Test
    public void testSairDoPulaPulaSemNinguemNoPulaPula(){
        PulaPula pulaPula = new PulaPula(1);
        assertFalse(pulaPula.sair(),
            "Não deve ser possível sair do pula-pula se não nenhuma crianca dentro do pula-pula");
    }

    @Test
    public void testSairDoPulaPulaComSucesso(){
        PulaPula pulaPula = new PulaPula(1);
        pulaPula.entrarNaFila(new Crianca("ana", 3));
        assertTrue(pulaPula.entrar(),
            "Se o limite do pula-pula não foi alcançado, deve ser possível entrar no pula-pula.");
        assertTrue(pulaPula.sair(),
            "Deve ser possível sair se houver pelo menos uma crianca dentro do pula-pula");
    }

    @Test
    public void testPrimeiroQueEntraPrimeiroQueSai(){
        PulaPula pulaPula = new PulaPula(1);
        Crianca crianca = new Crianca("maria", 6);
        pulaPula.entrarNaFila(crianca);
        pulaPula.entrarNaFila(new Crianca("ana", 3));
        assertTrue(pulaPula.entrar(),
            "Se o limite do pula-pula não foi alcançado, deve ser possível entrar no pula-pula.");
        pulaPula.entrar();
        assertTrue(pulaPula.sair(),
            "Se houve crianca no pula-pula, deve ser possível a crianca sair do pula-pula.");
        assertEquals(crianca, pulaPula.getFilaDeEspera().get(pulaPula.getFilaDeEspera().size() - 1),
            "Quando uma crianca sai do pula-pula, ela automatica entra no fim da fila de espera");

    }

    @Test
    public void testEntrandoNoPulaPulaEAdicionandoSaldoNaConta(){
        PulaPula pulaPula = new PulaPula(1);
        Crianca crianca = new Crianca("maria", 6);
        pulaPula.entrarNaFila(crianca);
        assertTrue(pulaPula.entrar(),
            "Se o limite do pula-pula não foi alcançado, deve ser possível entrar no pula-pula.");
        assertTrue(pulaPula.sair(),
            "Se houve crianca no pula-pula, deve ser possível a crianca sair do pula-pula.");
        pulaPula.entrar();
        assertEquals(5.00, pulaPula.getConta(crianca.getName()),
            "Tpda vez que uma crianca entra no pula-pula é acrescido na sua conta o valor de R$ 2,50.");
    }

    @Test
    public void testPaipaiChegouENaoTinhaNinguem(){
        PulaPula pulaPula = new PulaPula(1);
        assertFalse(pulaPula.papaiChegou("Pedro"),
            "Não deve ser possível chamar uma crianca se não ninguem na fila ou no pula-pula");
    }

    @Test
    public void testPapaiChamouPorUmNomeErrado(){
        PulaPula pulaPula = new PulaPula(1);
        pulaPula.entrarNaFila(new Crianca("Eduardo", 7));
        assertFalse(pulaPula.papaiChegou("Diego"),
            "Não deve ser possível encontrar uma crianca com o nome diferente daquelas que estão na fila ou pula-pula.");
    }

    @Test
    public void testPapaiChamouCriancaDaFila(){
        PulaPula pulaPula = new PulaPula(1);
        pulaPula.entrarNaFila(new Crianca("Eduardo", 7));
        assertTrue(pulaPula.papaiChegou("Eduardo"),
            "Deve ser possível encontrar uma crianca com o nome certo quando ela está na fila");
    }

    @Test
    public void testPaipaiChamouCriancaDoPulaPula(){
        PulaPula pulaPula = new PulaPula(1);
        pulaPula.entrarNaFila(new Crianca("Luiz", 8));
        pulaPula.entrar();
        assertTrue(pulaPula.papaiChegou("Luiz"),
            "Deve ser possível encontrar uma crianca com o nome certo quando ela está no pula-pula");
    }

    @Test
    public void testPaipaiChamouCriancaPulandoEPagouAConta(){
        PulaPula pulaPula = new PulaPula(1);
        pulaPula.entrarNaFila(new Crianca("Eduardo", 7));
        assertTrue(pulaPula.entrar(),
                "Se o limite do pula-pula não foi alcançado, deve ser possível entrar no pula-pula.");
        assertTrue(pulaPula.sair(),
                "Se houver crianca no pula-pula, deve ser possível a crianca sair do pula-pula.");
        pulaPula.entrar();
        pulaPula.papaiChegou("Eduardo");
        assertEquals(5.00, pulaPula.getCaixa(),
                "Quando um crianca vai embora, o seu pai deve pagar a conta e o dinheiro vai direto para o caixa.");
    }

    @Test
    public void testPaipaiChamouCriancaNaFilaEPagouAConta(){
        PulaPula pulaPula = new PulaPula(1);
        pulaPula.entrarNaFila(new Crianca("Eduardo", 7));
        assertTrue(pulaPula.entrar(),
                "Se o limite do pula-pula não foi alcançado, deve ser possível entrar no pula-pula.");
        assertTrue(pulaPula.sair(),
                "Se houver crianca no pula-pula, deve ser possível a crianca sair do pula-pula.");
        pulaPula.papaiChegou("Eduardo");
        assertEquals(2.50, pulaPula.getCaixa(),
                "Quando um crianca vai embora, o seu pai deve pagar a conta e o dinheiro vai direto para o caixa.");
    }

    @Test
    public void testFecharPulaPula(){
        PulaPula pulaPula = new PulaPula(2);
        pulaPula.entrarNaFila(new Crianca("Eduardo", 7));
        pulaPula.entrar();
        pulaPula.entrarNaFila(new Crianca("Luiz", 8));
        assertEquals(2.50, pulaPula.fechar(),
            "Ao fechar todas as criancas que entraram no pula-pula devem pagar as contas.");
        assertEquals(0, pulaPula.getFilaDeEspera().size(),
            "Ao fechar o pula-pula todas as crianças são retiradas da fila.");
        assertEquals(0, pulaPula.getCriancasPulando().size(),
            "Ao fechar o pula-pula todas as crianças são retiradas da fila.");
        assertNull(pulaPula.getConta("Eduardo"),
            "Ao fechar o pula-pula, todas as contas de todas as crianças são excluídas");
    }

    @Test
    public void testPegarContaQueNaoExiste(){
        PulaPula pulaPula = new PulaPula(2);
        pulaPula.entrarNaFila(new Crianca("Eduardo", 7));
        assertNull(pulaPula.getConta("Eduardo"),
            "Não deve ser possível pegar a conta de uma criança que não entrou no pula-pula.");
        
    }
}
