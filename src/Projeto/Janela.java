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
import java.util.concurrent.Semaphore;



public class Janela extends JFrame {

    private ImageIcon imagemCrianca1;
    private ImageIcon imagemCrianca2;
    private JLabel labelCrianca1;
    private JLabel labelCrianca2;
    private boolean movimentaCrianca1;
    private boolean movimentaCrianca2;   
    
    public static JTextArea campoLogs = new JTextArea();
    JScrollPane scrollPane = new JScrollPane(campoLogs);
   
    
    
    public Janela() {
        // Define o t�tulo da janela
        setTitle("Brincadeira de criança");

        // Define o tamanho da janela
        setSize(800, 600);

        // Define a opera��o padr�o ao fechar a janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cria um painel para a imagem de fundo e as imagens das crian�as
        JPanel painelImagem = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon fundo = new ImageIcon(getClass().getResource("/resources/fundo.png"));
                g.drawImage(fundo.getImage(), 0, 0, getWidth(), getHeight(), this);
                if (imagemCrianca1 != null) {
                    labelCrianca1.setIcon(imagemCrianca1);
                }
                if (imagemCrianca2 != null) {
                    labelCrianca2.setIcon(imagemCrianca2);
                }
            }
        };

        // Adiciona as imagens das crian�as ao painel
        imagemCrianca1 = new ImageIcon("crianca1.jpg");
        imagemCrianca2 = new ImageIcon("crianca2.jpg");
        labelCrianca1 = new JLabel(imagemCrianca1);
        labelCrianca2 = new JLabel(imagemCrianca2);
        painelImagem.add(labelCrianca1);
        painelImagem.add(labelCrianca2);

        // Cria um painel para a configura��o e exibi��o de logs
        JPanel painelConfiguracao = new JPanel(new GridLayout(3, 2));
        painelConfiguracao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Cria campos de texto e bot�o para a configura��o
        JTextField campoNome = new JTextField();
        JCheckBox checkBoxBola = new JCheckBox("Crian�a come�a com bola?");

        // Adiciona os componentes ao painel de configura��o
        painelConfiguracao.add(new JLabel("ID da crian�a:"));
        painelConfiguracao.add(campoNome);
        painelConfiguracao.add(new JLabel());
        painelConfiguracao.add(checkBoxBola);
        painelConfiguracao.add(new JLabel());

        // Cria um campo de texto para exibir os logs
        
        scrollPane.setPreferredSize(new Dimension(200, 200));
        
    
                
        

        // Adiciona os pain�is ao painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.add(painelImagem, BorderLayout.CENTER);
        painelPrincipal.add(painelConfiguracao, BorderLayout.EAST);
        JButton botaoAdicionar = new JButton("Adicionar crian�a");
        painelConfiguracao.add(botaoAdicionar);
        
                // Define o comportamento do bot�o de adicionar crian�a
                botaoAdicionar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String nome = campoNome.getText();
                        boolean comecaComBola = checkBoxBola.isSelected();
                       // Janela.campoLogs.append("TESTE SE ESTA COM BOLA" + comecaComBola);
                        BrincadeiraDeCriancas.adicionarCrianca(nome, comecaComBola);
                    }
                });
        painelPrincipal.add(scrollPane, BorderLayout.SOUTH);

       
        // Adiciona o painel principal � janela
        getContentPane().add(painelPrincipal);

        // Exibe a janela
        setVisible(true);
    }
    
    
  


    public static void main(String[] args) {
        new Janela();
    }}

