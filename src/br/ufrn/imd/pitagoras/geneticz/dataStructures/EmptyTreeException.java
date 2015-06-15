package br.ufrn.imd.pitagoras.geneticz.dataStructures;


/**
 * Exceçao que ocorre em arvores vazias, pois elas nao suportam certas operaçoes.
 * 
 * @author Pitagoras Alves
 * @version 24-04-2015
 */
public class EmptyTreeException extends Exception
{
	private static final long serialVersionUID = -819705805659209625L;
	// variáveis de instância - substitua o exemplo abaixo pelo seu próprio
    private String notes;

    /**
     * Construtor para objetos da classe EmptyTreeException
     * 
     * @param notes Detalhes adicionais passados para explicar a possivel origem da exceção.
     */
    public EmptyTreeException(String notes)
    {
        // inicializa variáveis de instância
        this.notes = notes;
    }
    
    /**
     * @return Detalhes adicionais passados para explicar a possivel origem da exceção.
     */
    public String getNote(){
        return notes;
    }
    
    /**
     * @return String de texto que descreve a classe.
     */
    public String toString(){
         return "EmptyTreeException:\n" +
                    "You have tried to use a operation that is invalid for empty trees in an empty tree\n" +
                    "More details: " + notes + "\n";
    }
}
