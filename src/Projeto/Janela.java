package Projeto;
import javax.swing.*;


import Projeto.BrincadeiraDeCriancas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.Semaphore;



public class Janela extends JFrame {

    private ImageIcon imagemCrianca1;
    private ImageIcon imagemCrianca2;
    private JLabel labelCrianca1;
    private JLabel labelCrianca2;
    private boolean movimentaCrianca1;
    private boolean movimentaCrianca2;   
    private Timer timer;
    private int x, y;
    JPanel painelCrianca = new JPanel();
    
    
    public static JTextArea campoLogs = new JTextArea();
    JScrollPane scrollPane = new JScrollPane(campoLogs);


   
    public static int[] separarVirgula(String args) {
        String[] substrings = args.split(","); // Divide a string em substrings separadas por vírgulas
        int[] vetor = new int[substrings.length]; // Cria um novo vetor de inteiros com o tamanho do array de substrings

        for (int i = 0; i < substrings.length; i++) {
            vetor[i] = Integer.parseInt(substrings[i]); // Converte cada substring em um inteiro e armazena no vetor
        }
        return vetor;
    }
    
    
    public Janela() {

       
        // Define o t�tulo da janela
        setTitle("Brincadeira de criança");

        // Define o tamanho da janela
        setSize(1920, 1080);

        // Define a opera��o padr�o ao fechar a janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        

        

        
        //Cria painel de configuração inicial
       
        JPanel painelConfiguracaoInicial = new JPanel(new GridLayout(2,10 ));
        painelConfiguracaoInicial.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //Criar campos para config inicial
        JTextField campoQuantidadeInicial = new JTextField();
        JTextField campoComecaComBola = new JTextField();
        JTextField campoTempoBrincando = new JTextField();
        JTextField campoTempoOutraAtividade = new JTextField();

        //Adiciona os campos criados ao painel        
        painelConfiguracaoInicial.add(new JLabel("Quantas crianças começam? (EX: 5)"));
        painelConfiguracaoInicial.add(campoQuantidadeInicial);
        painelConfiguracaoInicial.add(new JLabel("Quais crianças começam com bola? (EX: 1,3)"));
        painelConfiguracaoInicial.add(campoComecaComBola);
        painelConfiguracaoInicial.add(new JLabel("Quanto tempo cada criança ficará brincando? (EX: 5,5,6,7,8)"));
        painelConfiguracaoInicial.add(campoTempoBrincando);
        painelConfiguracaoInicial.add(new JLabel("Quanto tempo cada criança ficará em outra tividade? (EX: 5,5,6,7,8)"));
        painelConfiguracaoInicial.add(campoTempoOutraAtividade);
        JButton botaoIniciar = new JButton("Iniciar");
        painelConfiguracaoInicial.add(botaoIniciar);        
                // Define o comportamento do botão iniciar
                botaoIniciar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        Integer QuantidadeInicial = Integer.parseInt(campoQuantidadeInicial.getText());
                        String ComecaComBola = campoComecaComBola.getText();
                        int[] intComecaComBola = separarVirgula(ComecaComBola);
                        String TempoBrincando = campoTempoBrincando.getText();
                        int[] intTempoBrincando = separarVirgula(TempoBrincando);
                        String TempoOutraAtividade = campoTempoOutraAtividade.getText();
                        int[] intTempoOutraAtividade = separarVirgula(TempoOutraAtividade);

                        for(Integer i = 0; i < QuantidadeInicial; i++){
                            boolean boolComecaComBola = false;
                            String id = Integer.toString(i+1);
                            for(Integer j = 0; j<intComecaComBola.length;j++){
                                if(intComecaComBola[j] == j+1){
                                    boolComecaComBola = true;
                                    break;
                                }
                            }
                            BrincadeiraDeCriancas.adicionarCrianca(id,boolComecaComBola,intTempoBrincando[i],intTempoOutraAtividade[i]);


                        }
                    }
                });
        
       
    

        // Cria um painel para a configura��o e exibi��o de logs
        JPanel painelConfiguracao = new JPanel(new GridLayout(3, 2));
        painelConfiguracao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Cria campos de texto e bot�o para a configura��o
        JTextField campoNome = new JTextField();
        JTextField TempoBrincando = new JTextField();
        JTextField TempoOutraAtividade = new JTextField();
        JCheckBox checkBoxBola = new JCheckBox("Crian�a come�a com bola?");

        // Adiciona os componentes ao painel de configura��o
        painelConfiguracao.add(new JLabel("ID da crian�a:"));
        painelConfiguracao.add(campoNome);
        painelConfiguracao.add(new JLabel("Quanto tempo a crianca vai brincar?"));
        painelConfiguracao.add(TempoBrincando);
        painelConfiguracao.add(new JLabel("Quanto tempo a crianca vai ficar em outra atividade?"));
        painelConfiguracao.add(TempoOutraAtividade);
        painelConfiguracao.add(new JLabel());
        painelConfiguracao.add(checkBoxBola);
        painelConfiguracao.add(new JLabel());

        // Cria um campo de texto para exibir os logs
        
        scrollPane.setPreferredSize(new Dimension(300, 300));
        
         // Cria um painel para a imagem de fundo e as imagens das crian�as
    JPanel painelImagem = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon fundo = new ImageIcon(getClass().getResource("/resources/fundo.png"));
            g.drawImage(fundo.getImage(), 0, 0, getWidth(), getHeight(), this);
           
        }
    };
                
        

        // Adiciona os pain�is ao painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.add(painelConfiguracaoInicial, BorderLayout.NORTH);
        painelPrincipal.add(painelImagem, BorderLayout.CENTER);
        painelPrincipal.add(painelConfiguracao, BorderLayout.EAST);
        JButton botaoAdicionar = new JButton("Adicionar crian�a");
        painelConfiguracao.add(botaoAdicionar);
        
                // Define o comportamento do bot�o de adicionar crian�a
                botaoAdicionar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        AdicionarPainelCrianca();
                        String nome = campoNome.getText();
                        Integer intTempoBrincando = Integer.parseInt(TempoBrincando.getText());
                        Integer intTempoOutraAtividade = Integer.parseInt(TempoOutraAtividade.getText());
                        boolean comecaComBola = checkBoxBola.isSelected();
                       // Janela.campoLogs.append("TESTE SE ESTA COM BOLA" + comecaComBola);
                        BrincadeiraDeCriancas.adicionarCrianca(nome, comecaComBola,intTempoBrincando,intTempoOutraAtividade);
                    }
                });
        painelPrincipal.add(scrollPane, BorderLayout.SOUTH);

        imagemCrianca1 = new ImageIcon(getClass().getResource("/resources/crianca.jpg"));
        JLabel labelCima = new JLabel(imagemCrianca1);
        painelCrianca = new JPanel(new BorderLayout());
        painelCrianca.setOpaque(false); // torna o painel transparente
        painelCrianca.add(labelCima);
        painelImagem.add(painelCrianca, BorderLayout.CENTER);
        

        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Atualiza as coordenadas da imagem de cima
                x += 4; // atualiza a coordenada x (pode ser alterado para o valor desejado)
                
                painelCrianca.setLocation(x, y); // define a nova posição da imagem de cima
            }
        });
        timer.start(); // inicia o timer para começar a movimentação
       
        // Adiciona o painel principal � janela
        getContentPane().add(painelPrincipal);

        // Exibe a janela
        setVisible(true);
    }

    

    public void AdicionarPainelCrianca(){
        Janela.campoLogs.append("DEBUG");
        

    }
    
  


    public static void main(String[] args) {
       
        new Janela();
    }}

