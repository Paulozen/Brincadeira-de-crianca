package Projeto;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.Semaphore;
import Projeto.Janela;
import javax.swing.*;


public class BrincadeiraDeCriancas {
    private static final int NUM_CRIANCAS = 10;
    private static Semaphore semaforoCestoCheio = new Semaphore(0); // capacidade do cesto
    private static Semaphore semaforoCestoVazio = new Semaphore(0); // quantidade de bolas inicial
    //static ImageIcon imagemCriancaVazio = new ImageIcon(getClass().getResource("/resources/vazio.png"));
    //static ImageIcon imagemCrianca2 = new ImageIcon(getClass().getResource("/resources/fundo.png"));

    
    
    public static void adicionarCrianca(String id, boolean comecaComBola, Integer tempoBrincando, Integer tempoOutraAtividad) {
    	
    	  int numeroInt = Integer.parseInt(id);
          //System.out.println("criança 1:" + id + comecaComBola + tempoBrincando + tempoOutraAtividad);
    	  Crianca crianca = new Crianca(numeroInt,comecaComBola,tempoBrincando,tempoOutraAtividad);
          crianca.start();
    	
    	
       
    }

    static void definirSemaforoCesto(int i){
       semaforoCestoCheio.release(i);
       Janela.LabelCapacidadeCesto.setIcon(Janela.imagemCapacidadeCesto[i-1]);
    }
//
//    public static void main(String[] args) {
//        for (int i = 1; i <= NUM_CRIANCAS; i++) {
//            Crianca crianca = new Crianca(i,false,10,10);
//            crianca.start();
//        }
//    }
    
    public static void Espera(int tempo,int opcao,int id) {
    	LocalDateTime inicio = LocalDateTime.now(); 
        
    	while(true) {
    		LocalDateTime agora = LocalDateTime.now();
            if( opcao == 1){
    		if(ChronoUnit.SECONDS.between(inicio, agora)%2 == 1){
                Janela.LabelCrianca[id-1].setIcon(Janela.imagemCriancaBrincando1);
            }else{
                Janela.LabelCrianca[id-1].setIcon(Janela.imagemCriancaBrincando2);
            }}
    		if(ChronoUnit.SECONDS.between(inicio, agora) >= tempo) {
    			return;
    			}
    	}
    	
    }
    public static void AdicionarBola() {
        semaforoCestoVazio.release();
        Janela.LabelQuantidadeAtual.setIcon(Janela.imagemQuantidadeAtual[semaforoCestoVazio.availablePermits()]);
    	
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
                    if (comBola) {
                        Janela.campoLogs.append("Crian�a " + id + " começou com bola e está brincando"  + "\n");
                        System.out.println("Crian�a " + id + " começou com bola e está brincando");
                        comBola = true;
                        Janela.LabelCrianca[id-1].setIcon(Janela.imagemCriancaBrincando1);            
                        
                    }else{
                        Janela.campoLogs.append("Crian�a " + id + " tentou pegar uma bola"  + "\n");
                        System.out.println("Crian�a " + id + " tentou pegar uma bola");
                        Janela.LabelCrianca[id-1].setIcon(Janela.imagemCriancaTentandoPegar);   
                        semaforoCestoVazio.acquire();
                        semaforoCestoCheio.release();
                        Janela.LabelQuantidadeAtual.setIcon(Janela.imagemQuantidadeAtual[semaforoCestoVazio.availablePermits()]);
                        comBola = true; 
                        Janela.campoLogs.append("Crian�a " + id + " pegou uma bola do cesto e est� brincando"  + "\n");
                        System.out.println("Crian�a " + id + " pegou uma bola do cesto e est� brincando");  
                        Janela.LabelCrianca[id-1].setIcon(Janela.imagemCriancaBrincando1);                                    
                    }                    
                    Espera(tempoBrincando,1,id);
                    System.out.println("Crian�a " + id + " tentou colocar a bola no cesto");
                    Janela.campoLogs.append("Crian�a " + id + " tentou colocar a bola no cesto"+ "\n");
                    Janela.LabelCrianca[id-1].setIcon(Janela.imagemCriancaTentandoColocar);   
                    semaforoCestoCheio.acquire();
                    semaforoCestoVazio.release();
                    Janela.LabelQuantidadeAtual.setIcon(Janela.imagemQuantidadeAtual[semaforoCestoVazio.availablePermits()]);
                    System.out.println("Crian�a " + id + " colocou a bola no cesto");
                    Janela.campoLogs.append("Crian�a " + id + " colocou a bola no cesto"+ "\n");
                    comBola = false;
                    

                    System.out.println("Criança " + id + " esta em outra atividade");
                    Janela.campoLogs.append("Criança " + id + " esta em outra atividade"+ "\n");
                    Janela.LabelCrianca[id-1].setIcon(Janela.imagemCriancaOutraAtv1); 
                    Espera(tempoOutraAtividade,0,id);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
