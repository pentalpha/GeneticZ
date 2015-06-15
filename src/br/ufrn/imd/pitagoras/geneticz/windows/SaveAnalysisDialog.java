package br.ufrn.imd.pitagoras.geneticz.windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

/**
 * Dialogo que possibilita ao usuario salvar a analise num arquivo.
 * @author pitagoras
 */
@SuppressWarnings("serial")
public class SaveAnalysisDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private JTextField directory;
	private JTextField name;
	private JButton cancelButton;
	private JButton okButton;
	private JLabel lblNomeDaAnalise;
	private JButton selectFolderButton;
	private JLabel lblSalvarEm;
	
	@SuppressWarnings("unused")
	private boolean saved;
	
	/**
	 * Cria o dialogo.
	 */
	public SaveAnalysisDialog() {
		setResizable(false);
		saved = false;
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		
		setBounds(100, 100, 257, 188);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblSalvarEm = new JLabel("Save in:");
		lblSalvarEm.setBounds(10, 11, 73, 14);
		contentPanel.add(lblSalvarEm);
		
		directory = new JTextField();
		directory.setBounds(10, 27, 171, 20);
		contentPanel.add(directory);
		directory.setColumns(10);
		directory.setText("./");
		
		selectFolderButton = new JButton();
		selectFolderButton.setAction(new SelectFolderAction());
		selectFolderButton.setText("...");
		selectFolderButton.setBounds(191, 26, 45, 23);
		contentPanel.add(selectFolderButton);
		
		lblNomeDaAnalise = new JLabel("Analysis name:");
		lblNomeDaAnalise.setBounds(10, 58, 107, 14);
		contentPanel.add(lblNomeDaAnalise);
		
		name = new JTextField();
		name.setBounds(10, 73, 171, 20);
		contentPanel.add(name);
		name.setColumns(10);
		name.setText("Analise 01");
		
		okButton = new JButton();
		okButton.setAction(new SaveAction());
		okButton.setText("Salvar");
		okButton.setBounds(38, 117, 83, 31);
		
		cancelButton = new JButton();
		cancelButton.setAction(new CancelAction());
		cancelButton.setText("Cancelar");
		cancelButton.setBounds(131, 117, 83, 31);
		
		contentPanel.add(okButton);
		contentPanel.add(cancelButton);
		getRootPane().setDefaultButton(okButton);
	}
	
	public String getSaveDirectory(){
		return directory.getText();
	}
	
	public void setSaveDirectory(String x){
		directory.setText(x);
	}
	
	public String getProjectName(){
		return name.getText();
	}
	
	public void setProjectName(String x){
		name.setText(x);
	}
	
	/**
	 * Abre dialogo secundario para a seleção de uma pasta.
	 */
	private String selectFolder(){
		String folder = null;
		JFileChooser fileChooser = new JFileChooser(".");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int dialogResult = fileChooser.showOpenDialog(this);
		
		if(dialogResult == JFileChooser.APPROVE_OPTION){
			File[] files = fileChooser.getSelectedFile().listFiles();
			boolean otherAnalysisFiles = false;
			
			for(int i = 0; i < files.length; i++){
				if(files[i].getName().contains(".tree")
					|| files[i].getName().contains(".dMatrix")
					|| files[i].getName().contains(".genes")){
					otherAnalysisFiles = true;
				}
			}
			
			if(otherAnalysisFiles){
				int response = JOptionPane.showConfirmDialog(null, "Apparently there are already files from another analysis in this folder. Do you really want save here?", 
						"Aviso", JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE);
				if(response != JOptionPane.YES_OPTION){
					return null;
				}
			}
			
			folder = fileChooser.getSelectedFile().getPath();
		}
		
		return folder;
	}
	
	/**
	 * Exibe o oculta este dialogo.
	 * @param x True para ser exibido, False para ocultar tudo.
	 */
	private void setDialogVisible(boolean x){
		this.setVisible(x);
	}
	
	/**
	 * Ação para selecionar diretorio.
	 * @author pitagoras
	 *
	 */
	private class SelectFolderAction extends AbstractAction {
		public SelectFolderAction() {
			putValue(SHORT_DESCRIPTION, "Select a folder.");
		}
		public void actionPerformed(ActionEvent e) {
			directory.setText(selectFolder());
		}
	}
	
	/**
	 * Ação para cancelar o salvamento da analise em arquivo.
	 * @author pitagoras
	 *
	 */
	private class CancelAction extends AbstractAction{
		public CancelAction(){

		}
		
		public void actionPerformed(ActionEvent e){
			saved = false;
			setDialogVisible(false);
		}
	}
	
	/**
	 * Ação para concluir o dialogo.
	 * @author pitagoras
	 *
	 */
	private class SaveAction extends AbstractAction{
		public SaveAction(){

		}
		
		public void actionPerformed(ActionEvent e){
			saved = true;
			setDialogVisible(false);
		}
	}
}
