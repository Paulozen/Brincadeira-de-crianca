package Projeto;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.Semaphore;
import Projeto.Janela;


public class BrincadeiraDeCriancas {
    private static final int NUM_CRIANCAS = 10;
    private static final int CAPACIDADE_CESTO = 6;
    private static Semaphore semaforoCesto = new Semaphore(CAPACIDADE_CESTO);
    private static Semaphore semaforoJogando = new Semaphore(0); // quantidade de bolas inicial
    
    
    public static void adicionarCrianca(String id, boolean comecaComBola) {
    	
    	  int numeroInt = Integer.parseInt(id);
    	
    	  Crianca crianca = new Crianca(numeroInt,comecaComBola,10,10);
          crianca.start();
    	
    	
       
    }
//
//    public static void main(String[] args) {
//        for (int i = 1; i <= NUM_CRIANCAS; i++) {
//            Crianca crianca = new Crianca(i,false,10,10);
//            crianca.start();
//        }
//    }
    
    public static void Espera(int tempo) {
    	LocalDateTime inicio = LocalDateTime.now();    	
    	
    	while(true) {
    		LocalDateTime agora = LocalDateTime.now();
    		
    		if(ChronoUnit.SECONDS.between(inicio, agora) >= tempo) {
    			return;
    			}
    	}
    	
    }
    
    

    public static class Crianca extends Thread {
        private int id;
        private boolean comBola;
        private int tempoBrincando;
        private int tempoOutraAtividade;

        public Crianca(int id, boolean comBola, int tempoBrincando, int tempoOutraAtividade) {
            this.id = id;
            this.comBola = comBola;
            this.tempoBrincando = tempoBrincando;
            this.tempoOutraAtividade = tempoOutraAtividade;
        }

        public void run() {
            try {
                while (true) {
                    if (!comBola) {
                        semaforoCesto.acquire();
                        comBola = true;  
                        Janela.campoLogs.append("Crian�a " + id + " tentou pegar uma bola"  + "\n");
                        System.out.println("Crian�a " + id + " tentou pegar uma bola");
                    }else{
                        semaforoJogando.release();
                        semaforoCesto.acquire();
                        comBola = true;  
                        Janela.campoLogs.append("Crian�a " + id + " tentou pegar uma bola"  + "\n");
                        System.out.println("Crian�a " + id + " tentou pegar uma bola");
                    }
                    semaforoJogando.acquire();                    
                    System.out.println("Crian�a " + id + " pegou uma bola e est� brincando");
                    Janela.campoLogs.append("Crian�a " + id + " pegou uma bola e est� brincando"+ "\n");
                    Espera(tempoBrincando);
                    System.out.println("Crian�a " + id + " colocou a bola no cesto");
                    Janela.campoLogs.append("Crian�a " + id + " colocou a bola no cesto"+ "\n");
                    semaforoJogando.release();
                    comBola = false;
                    semaforoCesto.release();

                    System.out.println("Crian�a " + id + " est� em outra atividade");
                    Janela.campoLogs.append("Crian�a " + id + " est� em outra atividade"+ "\n");
                    Espera(tempoOutraAtividade);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
