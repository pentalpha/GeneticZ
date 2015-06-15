package br.ufrn.imd.pitagoras.geneticz.windows;

import java.awt.EventQueue;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.Font;
import java.io.File;

import javax.swing.AbstractAction;

import br.ufrn.imd.pitagoras.geneticz.GeneticAnalysis;
import br.ufrn.imd.pitagoras.geneticz.SampleSimplifier;
import br.ufrn.imd.pitagoras.geneticz.TestFactory;
import br.ufrn.imd.pitagoras.geneticz.TestFactory.TestType;
import br.ufrn.imd.pitagoras.geneticz.dataStructures.AVLTreeDouble;
import br.ufrn.imd.pitagoras.geneticz.dataStructures.BinTreeIteratorDouble;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import com.alee.laf.WebLookAndFeel;

/**
 * Classe inicial e janela principal do software
 * que dá acesso a todas as funcionalidades
 * @author pitagoras
 *
 */
public class MainWindow extends JFrame {

	public static final long serialVersionUID = 0xA1;
	private JPanel contentPane;
	
	private JButton newAnalysisButton, openAnalysisButton, saveAnalysisButton, 
					showSamplesButton, showTreeButton, validationButton, 
					selectSampleButton, selectGroupsButton, calcSignatureButton;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;
	
	static public Font fontBig, font;
	//static private ImageIcon darkArrow;
	
	private ImageIcon loadingIcon;
	
	private JLabel labelMetodoEstatistico, labelPValue, labelArquivodeAmostras,
					labelArquivoDeGrupos;
	private JTextField sampleFileField, groupsFileField, pValueField;
	
	private GeneticAnalysis analysis;
	private JTextField genesField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel ( new WebLookAndFeel () );
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Cria a janela.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MainWindow() {
		fontBig = new Font("Segoe UI", Font.PLAIN, 16);
		font = new Font("Segoe UI", Font.PLAIN, 14);
		
		//darkArrow = new ImageIcon(MainWindow.class.getResource("/br/ufrn/imd/pitagoras/geneticz/img/arrow_dark_blue2.png"));
		loadingIcon = new ImageIcon(MainWindow.class.getResource("/com/alee/extended/tree/icons/snake.gif"));
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 450, 459);
		
		//analysis = new GeneticAnalysis();
		
		//JMenuBar menuBar_1 = new JMenuBar();
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		validationButton = new JButton();
		validationButton.setBounds(236, 269, 161, 50);
		contentPane.add(validationButton);
		
		showSamplesButton = new JButton();
		showSamplesButton.setBounds(236, 347, 161, 50);
		contentPane.add(showSamplesButton);
		
		showTreeButton = new JButton();
		showTreeButton.setBounds(47, 347, 161, 50);
		contentPane.add(showTreeButton);
		
		newAnalysisButton = new JButton();
		newAnalysisButton.setBounds(10, 11, 132, 41);
		contentPane.add(newAnalysisButton);
		
		openAnalysisButton = new JButton();
		openAnalysisButton.setBounds(152, 11, 140, 41);
		contentPane.add(openAnalysisButton);
		
		saveAnalysisButton = new JButton();
		saveAnalysisButton.setBounds(302, 11, 132, 41);
		contentPane.add(saveAnalysisButton);
		
		labelArquivodeAmostras = new JLabel();
		labelArquivodeAmostras.setBounds(10, 63, 249, 25);
		getContentPane().add(labelArquivodeAmostras);
		
		sampleFileField = new JTextField();
		sampleFileField.setBounds(10, 88, 368, 26);
		getContentPane().add(sampleFileField);
		sampleFileField.setColumns(10);
		
		selectSampleButton = new JButton();
		
		selectSampleButton.setBounds(388, 88, 46, 26);
		getContentPane().add(selectSampleButton);
		
		labelArquivoDeGrupos = new JLabel();
		labelArquivoDeGrupos.setBounds(10, 125, 249, 25);
		getContentPane().add(labelArquivoDeGrupos);
		
		groupsFileField = new JTextField();
		groupsFileField.setBounds(10, 150, 368, 26);
		getContentPane().add(groupsFileField);
		groupsFileField.setColumns(10);
		
		selectGroupsButton  = new JButton();
		selectGroupsButton.setAction(new selectGroupsAction(this));
		
		selectGroupsButton.setBounds(388, 150, 46, 26);
		getContentPane().add(selectGroupsButton);
		
		
		calcSignatureButton = new JButton();
		
		
		calcSignatureButton.setBounds(47, 269, 161, 50);
		getContentPane().add(calcSignatureButton);
		
		
		labelPValue = new JLabel();
		labelPValue.setBounds(302, 195, 46, 14);
		getContentPane().add(labelPValue);
		
		pValueField = new JTextField();
		pValueField.setBounds(302, 216, 132, 30);
		getContentPane().add(pValueField);
		pValueField.setColumns(10);
		
		
		labelMetodoEstatistico = new JLabel();
		labelMetodoEstatistico.setBounds(10, 195, 109, 14);
		getContentPane().add(labelMetodoEstatistico);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(TestType.values()));
		comboBox.setMaximumRowCount(4);
		comboBox.setBounds(10, 216, 132, 30);
		contentPane.add(comboBox);
		
		
		JLabel lblTamanhoMaximoDa = new JLabel();
		lblTamanhoMaximoDa.setText("Max. Genes:");
		lblTamanhoMaximoDa.setBounds(152, 194, 104, 17);
		contentPane.add(lblTamanhoMaximoDa);
		
		genesField = new JTextField();
		genesField.setText("37");
		genesField.setColumns(10);
		genesField.setBounds(152, 216, 140, 30);
		contentPane.add(genesField);
		
		ApplyComponentActions();
		
		ApplyComponentFonts();
		
		ApplyComponentTexts();
		
		ApplyComponentIcons();
		
		DefineOptionsAvaiable();
	}
	
	/**
	 * Define as ações de cada componente.
	 */
	private void ApplyComponentActions(){
		newAnalysisButton.setAction(new newAnalysisAction());
		saveAnalysisButton.setAction(new saveAnalysisAction());
		openAnalysisButton.setAction(new openAnalysisAction(this));
		showSamplesButton.setAction(new showSignatureAction());
		showTreeButton.setAction(new showTreeAction());
		selectSampleButton.setAction(new selectSampleAction(this));
		calcSignatureButton.setAction(new loadSamplesAction(this));
		validationButton.setAction(new validationAction());
		
	}
	
	/**
	 * Define os icones de cada componente.
	 */
	private void ApplyComponentIcons(){
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/com/alee/laf/filechooser/icons/settings.png")));
		saveAnalysisButton.setIcon(new ImageIcon(MainWindow.class.getResource("/com/alee/managers/notification/icons/types/database.png")));
		openAnalysisButton.setIcon(new ImageIcon(MainWindow.class.getResource("/com/alee/managers/notification/icons/types/folder.png")));
		newAnalysisButton.setIcon(new ImageIcon(MainWindow.class.getResource("/com/alee/managers/notification/icons/types/file.png")));
		showTreeButton.setIcon(new ImageIcon(MainWindow.class.getResource("/com/jtattoo/plaf/icons/medium/pearl_red_28x28.png")));
		showSamplesButton.setIcon(new ImageIcon(MainWindow.class.getResource("/com/jtattoo/plaf/icons/small/pearl_yellow_24x24.png")));
		//calcSignatureButton.setIcon(new ImageIcon(MainWindow.class.getResource("/com/alee/extended/tree/icons/snake.gif")));
	}
	
	/**
	 * Define os textos exibidos em cada componente.
	 */
	private void ApplyComponentTexts(){
		setTitle("Geneticz");
		labelArquivodeAmostras.setText("Samples file:");
		labelMetodoEstatistico.setText("Statistical Method:");
		pValueField.setText("0.05");
		labelPValue.setText("p-Value:");
		labelArquivoDeGrupos.setText("Sample groups file:");
		calcSignatureButton.setText("Start");
		selectGroupsButton.setText("...");
		selectSampleButton.setText("...");
		validationButton.setText("Cross-Validation");
		showSamplesButton.setText("Signature");
		showTreeButton.setText("Dendogram");
		saveAnalysisButton.setText("Save");
		openAnalysisButton.setText("Open");
		this.newAnalysisButton.setText("New");
	}
	
	/**
	 * Define as fontes de cada componente.
	 */
	private void ApplyComponentFonts(){
		if(fontBig != null){
			setFont(fontBig);
			/*validationButton.setFont(fontBig);
			showSamplesButton.setFont(fontBig);
			newAnalysisButton.setFont(fontBig);
			showTreeButton.setFont(fontBig);
			saveAnalysisButton.setFont(fontBig);
			openAnalysisButton.setFont(fontBig);
			comboBox.setFont(fontBig);
			labelMetodoEstatistico.set*/
		}
		
		if(font != null){
			
		}
	}
	
	/**
	 * Atualiza quais são os botões disponiveis para o usuario.
	 */
	private void DefineOptionsAvaiable(){
		if(analysis == null){
			validationButton.setEnabled(false);
			showSamplesButton.setEnabled(false);
			showTreeButton.setEnabled(false);
			saveAnalysisButton.setEnabled(false);
		}else{
			validationButton.setEnabled(true);
			showSamplesButton.setEnabled(true);
			showTreeButton.setEnabled(true);
			saveAnalysisButton.setEnabled(true);
		}
	}
	
	/**
	 * Cria um dialogo de alerta.
	 */
	private void showAlertDialog(String title, String error, int messageType){
		JOptionPane.showMessageDialog(this, error, title, messageType);
	}
	
	/**
	 * Cria um dialogo de seleção de arquivo.
	 * @param parent Janela ou dialogo a partir do qual este dialogo de seleção de arquivo será criado.
	 * @param typeDescription Descrição.
	 * @param extension Restrição de extensões de arquivo.
	 * @return Caminho do arquivo selecionado.
	 */
	public static String selectFile(Component parent, String typeDescription, String extension){
		String fileName = null;
		JFileChooser fileChooser = new JFileChooser(".");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileNameExtensionFilter(
				typeDescription, extension));
		
		int dialogResult = fileChooser.showOpenDialog(parent);
		
		if(dialogResult == JFileChooser.APPROVE_OPTION){
			fileName = fileChooser.getSelectedFile().getAbsolutePath();
		}
		return fileName;
	}
	
	/**
	 * Ação para criar nova analise.
	 * @author pitagoras
	 *
	 */
	@SuppressWarnings("serial")
	private class newAnalysisAction extends AbstractAction {
		public newAnalysisAction() {
			putValue(SHORT_DESCRIPTION, "Starts a new analysis");
		}
		public void actionPerformed(ActionEvent e) {
			
			analysis = null;
			DefineOptionsAvaiable();
		}
	}
	
	/**
	 * Ação para abrir uma analise a partir de um arquivo.
	 * @author pitagoras
	 *
	 */
	@SuppressWarnings("serial")
	private class openAnalysisAction extends AbstractAction {
		private Component parent;
		public openAnalysisAction(Component parent){
			putValue(SHORT_DESCRIPTION, "Open existing analysis");
			this.parent = parent;
		}
		public void actionPerformed(ActionEvent e){
			
			String fileName = selectFile(parent, "Geneticz Analysis", "gAnalysis");
			if(fileName != null){
				File analysisFile = new File(fileName);
				if(analysisFile.exists()){
					analysis = new GeneticAnalysis(analysisFile);
				}
			}
			DefineOptionsAvaiable();
		}
	}
	
	/**
	 * Ação para salvar a analise.
	 * @author pitagoras
	 *
	 */
	@SuppressWarnings("serial")
	private class saveAnalysisAction extends AbstractAction {
		public saveAnalysisAction(){
			putValue(SHORT_DESCRIPTION, "Save current analysis.");
		}
		public void actionPerformed(ActionEvent e){
			if(analysis != null){
				SaveAnalysisDialog dialog = new SaveAnalysisDialog();
			
				if(analysis.getFolder() != null){
					dialog.setSaveDirectory(analysis.getFolder());
				}
				
				if(analysis.getName() != null){
					dialog.setProjectName(analysis.getName());
				}
			
			
				dialog.setVisible(true);
				
				analysis.setName(dialog.getProjectName());
				analysis.setFolder(dialog.getSaveDirectory());
				
				analysis.saveData();
				DefineOptionsAvaiable();
			}
		}
	}
	
	/**
	 * Exibe uma mensagem para o usuario avisando que esta opção ainda
	 * será implementada.
	 * @author pitagoras
	 *
	 */
	@SuppressWarnings({ "serial", "unused" })
	private class opcaoNaoImplementada extends AbstractAction {
		public opcaoNaoImplementada() {
			putValue(SHORT_DESCRIPTION, "Esta opção ainda não foi implementada");
		}
		public void actionPerformed(ActionEvent e) {
			showAlertDialog("Error", "Ação ainda não implementada.", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Ação para exibir a assinatura gênica.
	 * @author pitagoras
	 *
	 */
	@SuppressWarnings("serial")
	private class showSignatureAction extends AbstractAction {
		public showSignatureAction(){
			putValue(SHORT_DESCRIPTION, "Display table with the gene signature.");
		}
		public void actionPerformed(ActionEvent e){
			AVLTreeDouble[] signature = analysis.getSignature();
			
			String[] columns = new String[signature.length];
			for(int i = 0; i < columns.length; i++){
				columns[i] = SampleSimplifier.groupsData.getSampleName(i);
			}
			
			int rowsNumber = 0;
			for(BinTreeIteratorDouble a = signature[0].getIterator(); a.hasActual(); a.iterate()){
	            rowsNumber++;
	        }
			
			Double[][] tableData = new Double[rowsNumber][columns.length];
			
			/*
			for(BinTreeIteratorDouble i = signature[0].getIterator(); i.hasActual(); i.iterate()){
				tableData[row][0] = (double)i.getKey();
				row++;
			}*/
			int row = 0;
			for(int column = 0; column < columns.length; column++){
				row = 0;
				for(BinTreeIteratorDouble i = signature[column].getIterator(); i.hasActual(); i.iterate()){
					tableData[row][column] = i.get();
					row++;
				}
			}
			
			TableDialog dialog = new TableDialog(tableData, columns, "Genetic Signature", 20, 20);
			dialog.setVisible(true);
		}
	}
	
	/**
	 * Ação para exibir o dendograma.
	 * @author pitagoras
	 *
	 */
	@SuppressWarnings("serial")
	private class showTreeAction extends AbstractAction{

		public showTreeAction(){
			
		}
		public void actionPerformed(ActionEvent e){
			DendogramDialog dialog = new DendogramDialog(analysis.getDendogram());
			dialog.setVisible(true);
		}
	}
	
	/**
	 * Ação para selecionar o arquivo de amostras.
	 * @author pitagoras
	 *
	 */
	private class selectSampleAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4842529053978761390L;
		private Component parent;
		public selectSampleAction(Component parent){
			putValue(SHORT_DESCRIPTION, "Open a sample file.");
			this.parent = parent;
		}
		public void actionPerformed(ActionEvent e){
			sampleFileField.setText(MainWindow.selectFile(parent, "Geneticz Analysis", "gSamples"));
		}
	}
	
	/**
	 * Ação para selecionar o arquivo de grupos de amostras.
	 * @author pitagoras
	 *
	 */
	private class selectGroupsAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = -26754056611130955L;
		private Component parent;
		public selectGroupsAction(Component parent){
			putValue(SHORT_DESCRIPTION, "Open a sample groups file.");
			this.parent = parent;
		}
		public void actionPerformed(ActionEvent e){
			groupsFileField.setText(MainWindow.selectFile(parent, "Geneticz Analysis Groups", "groups"));
		}
	}
	
	/**
	 * Carrega a assinatura gênica e inicia a construção da analise.
	 * @author pitagoras
	 *
	 */
	private class loadSamplesAction extends AbstractAction{
		/**
		 * 
		 */
		private static final long serialVersionUID = -3335394433650360930L;
		Component parent;
		public loadSamplesAction(Component parent)
		{
			this.parent = parent;
			putValue(SHORT_DESCRIPTION, "Determine signature and dendogram.");
		}
		public void actionPerformed(ActionEvent e){
			
			//boolean validInputs = checkForValidInput();
			
			File sampleFile = new File(sampleFileField.getText());
			if(!sampleFile.exists()){
				JOptionPane.showMessageDialog(parent, "The specified file (" + sampleFileField.getText() +
						") does not exist. Enter a valid path to a file."
						, "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			File groupsFile = new File(groupsFileField.getText());
			if(!groupsFile.exists()){
				JOptionPane.showMessageDialog(parent, "The specified file (" + groupsFileField.getText() +
						") does not exist. Enter a valid path to a file."
						, "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			double pValue = 0.0;
			try{
				pValue = Double.parseDouble(pValueField.getText());
			}catch(Exception except){
				JOptionPane.showMessageDialog(parent, "The p-Value entered is not an actual numerical value", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (!(pValue > 0.0 && pValue < 1.0)){
				JOptionPane.showMessageDialog(parent, "The p-Value entered (" + pValueField.getText() +
						") is not valid. Insert a value less than 1 and bigger than 0."
						, "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			int maxGenes = 50;
			
			try{
				maxGenes = Integer.parseInt(genesField.getText());
			}catch(Exception except){
				JOptionPane.showMessageDialog(parent, "The maximum number of genes inserted is not a natural numeric value", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(!(maxGenes > 0 && maxGenes <= 1000)){
				JOptionPane.showMessageDialog(parent, "The maximum number of genes inserted (" + genesField.getText() +
						") is not valid. Insert a natural value bigger than 0 and less than 1001."
						, "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			/*String test;
			if(wilcoxonButton.isSelected()){
				test = "Wilcoxon";
			}else{
				test = "t de Student";
			}*/
			TestType testType = (TestType) comboBox.getSelectedItem();
			SampleSimplifier test = TestFactory.create(testType);
			calcSignatureButton.setIcon(loadingIcon);
			AVLTreeDouble[] newSignature = test.getSignature(sampleFile, groupsFile, pValue, maxGenes);
			if(newSignature != null){
				analysis = new GeneticAnalysis(newSignature, testType, pValue);
				
				DefineOptionsAvaiable();
				//saved = true;
				
				//setVisible(false);
			}else{
				showAlertDialog("Error", "A empty genetic signature was generated", JOptionPane.ERROR_MESSAGE);
			}
			calcSignatureButton.setIcon(null);
		}
		
		
	}
	
	/**
	 * Ação de validação cruzada da analise gênica.
	 * @author pitagoras
	 *
	 */
	@SuppressWarnings("serial")
	private class validationAction extends AbstractAction{
		public validationAction(){
			
		}
		public void actionPerformed(ActionEvent e){
			validationButton.setIcon(loadingIcon);
			showAlertDialog("Results", analysis.crossValidation(), JOptionPane.INFORMATION_MESSAGE);
			validationButton.setIcon(null);
		}
	}
}
