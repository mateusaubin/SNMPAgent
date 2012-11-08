/* Copyright (c)  2009  ZOHO Corp. All Rights Reserved.
 * PLEASE READ THE ASSOCIATED COPYRIGHTS FILE FOR MORE DETAILS.
 * ZOHO CORPORATION MAKES NO REPRESENTATIONS OR WARRANTIES  ABOUT THE
 * SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT.
 * ZOHO CORPORATION SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE 
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE
 * OR ITS DERIVATIVES.
 */

 package br.unisinos.gerenciaDeRedes;

// Agent Utility API Imports
import com.adventnet.utilities.common.AgentException;
import com.adventnet.utilities.common.CommonUtils;
import com.adventnet.utils.agent.InstrumentHandlerInterface;

import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;

// Imports da Implementacao
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

/**
 * Classe contendo o codigo relativo ao 
 * atendimento de requests gerados por 
 * um MIBBrowser
 * @Version : 6.0.3 Tue Nov 06 23:35:22 EST 2012
 * @Author  : WebNMS Agent Toolkit Java Edition
 * @Author  : Bruno Schmidt Marques
 * @Author  : Mateus Rauback Aubin
 */
public class BrunomateusInstrument implements InstrumentHandlerInterface{

	protected Long queryCounter = new Long(0L);
	protected Integer screenResolutionWidth = new Integer(0);
	protected Integer screenResolutionHeight = new Integer(0);
	protected String folderToCount = "C:";
	protected Integer filesInFolder = new Integer(0);
	protected Long filesystemCount = new Long(0L);

	// Variaveis da Implementacao
	protected Dimension screenSize;
	protected File folderDirectory;

	final static int QUERYCOUNTER = 1;
	final static int SCREENRESOLUTIONWIDTH = 2;
	final static int SCREENRESOLUTIONHEIGHT = 3;
	final static int FOLDERTOCOUNT = 4;
	final static int FILESINFOLDER = 5;
	final static int FILESYSTEMCOUNT = 6;

	static Integer failedSubId = new Integer(-1);

	/**
	 * Contador de queries realizadas nesta MIB, nesta sessao.
	 * A cada requisicao o contador sera incrementado, retornado 
	 * ao cliente contendo a quantidade de requisicoes 
	 * realizadas ate o momento.
	 * As requisicoes sao referente a qualquer no desta MIB, 
	 * nao limitadas ao proprio no.
	 */
	public Long getQueryCounter() throws AgentException{
		queryCounter = new Long(queryCounter.longValue() + 1L);
		return queryCounter;
	}

	/**
	 * Retorna a largura da tela, em pixels, do host onde o 
	 * agente esta sendo executado.
	 */
	public Integer getScreenResolutionWidth() throws AgentException{
		if (screenSize == null)
			screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		screenResolutionWidth = new Integer((int)screenSize.getWidth());
		this.getQueryCounter(); // Incrementa contagem.
		return screenResolutionWidth;
	}

	/**
	 * Retorna a altura da tela, em pixels, do host onde o 
	 * agente esta sendo executado.
	 */
	public Integer getScreenResolutionHeight() throws AgentException{
		if (screenSize == null)
			screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		screenResolutionHeight = new Integer((int)screenSize.getHeight());
		this.getQueryCounter(); // Incrementa contagem.
		return screenResolutionHeight;
	}

	/**
	 * Retorna a pasta (caminho) onde sera executada a 
	 * contagem de objetos existentes na invocacao do 
	 * metodo getFilesInFolder(). 
	 */
	public String getFolderToCount() throws AgentException{
		this.getQueryCounter(); // Incrementa contagem.
		return folderToCount;
	}

	/**
	 * Define a pasta (caminho) onde sera executada a 
	 * contagem de objetos existentes na invocacao do 
	 * metodo getFilesInFolder(). 
	 */
	public synchronized void setFolderToCount(String value) throws AgentException{
		// Validacoes
		if(value == null){
			throw new AgentException("valor nulo", CommonUtils.WRONGVALUE);
		}
		if(!(((value.length()>=0)&&(value.length()<=255)))){
			throw new AgentException("tamanho invalido [0-255]", CommonUtils.WRONGVALUE);
		}
		folderDirectory = new File(value);
		if (!folderDirectory.exists()) {
	     	folderDirectory = null;
			throw new AgentException("caminho malformado", CommonUtils.BADVALUE);
		}
		folderToCount = value;
	}

	/**
	 * Retorna a quantidade de objetos, geralmente 
	 * arquivos, presentes na pasta (caminho) 
	 * definido anteriormente.
	 */
	public Integer getFilesInFolder() throws AgentException{		
		if (folderDirectory == null)
			this.setFolderToCount(folderToCount);
		
		try {
			filesInFolder = new Integer(folderDirectory.list().length);
		} catch (Exception e){
			throw new AgentException("diretorio nao encontrado", CommonUtils.RESOURCEUNAVAILABLE);
		}
		this.getQueryCounter(); // Incrementa contagem.
		return filesInFolder;
	}

	/**
	 * Retorna a quantidade de unidades mapeadas
	 * no sistema
	 */
	public Long getFilesystemCount() throws AgentException{
		filesystemCount = new Long(File.listRoots().length);
		this.getQueryCounter(); // Incrementa contagem.
		return filesystemCount;
	}

	/**
	*
	* @return the subId of the attribute for which Set could not be performed
	*/
	public Integer getFailedSubId(){
		return failedSubId;
	}

	/**
	*
	* @param subId failed SubId
	*/
	public void setFailedSubId(Integer subId){
		failedSubId = subId;
	}

	/**
	*
	* @return Hashtable having values of all the Objects belonging to this Group
	* @throws Exception
	*/
	public Hashtable getAttributes() throws Exception { 
		Hashtable toReturn = new Hashtable();

		toReturn.put(new Integer(QUERYCOUNTER),getQueryCounter());
		toReturn.put(new Integer(SCREENRESOLUTIONWIDTH),getScreenResolutionWidth());
		toReturn.put(new Integer(SCREENRESOLUTIONHEIGHT),getScreenResolutionHeight());
		toReturn.put(new Integer(FOLDERTOCOUNT),getFolderToCount());
		toReturn.put(new Integer(FILESINFOLDER),getFilesInFolder());
		toReturn.put(new Integer(FILESYSTEMCOUNT),getFilesystemCount());
		return toReturn;
	}

	/**
	*
	* @param enumer Enumeration having the Objects for which value is sought
	* @return Hashtable having values of the Objects present in the Enumeration enumer
	* @throws Exception
	*/
	public Hashtable getAttributes(Enumeration enumer) throws Exception { 
		Hashtable toReturn = new Hashtable();

		while(enumer.hasMoreElements()){
			try{
				Integer key =(Integer) enumer.nextElement();
				switch(key.intValue()){
					case QUERYCOUNTER:
						toReturn.put(new Integer(QUERYCOUNTER),getQueryCounter());
						break;
					case SCREENRESOLUTIONWIDTH:
						toReturn.put(new Integer(SCREENRESOLUTIONWIDTH),getScreenResolutionWidth());
						break;
					case SCREENRESOLUTIONHEIGHT:
						toReturn.put(new Integer(SCREENRESOLUTIONHEIGHT),getScreenResolutionHeight());
						break;
					case FOLDERTOCOUNT:
						toReturn.put(new Integer(FOLDERTOCOUNT),getFolderToCount());
						break;
					case FILESINFOLDER:
						toReturn.put(new Integer(FILESINFOLDER),getFilesInFolder());
						break;
					case FILESYSTEMCOUNT:
						toReturn.put(new Integer(FILESYSTEMCOUNT),getFilesystemCount());
						break;
					default:
				}
			} catch(Exception exp) {
				//exp.printStackTrace();
				// If Exception occurs while fetching the value of a attribute, it should be handled appropriately in this catch block itself and the
				// corresponding attribute should *not* be updated in the Hashtable returned by this method.
				// In the generated *RequestHandler file, appropriate Snmp Error Message will be updated for the missing attribute(s) while processing
				// GET/GET-NEXT/GET-BULK requests from the Snmp Managers.
			}
		} // end of while 
 		return toReturn;
	}

	/**
	*
	* @param values Hashtable having values of Objects,for which SET is to be performed
	* @param subIdList Vector having list of Objects for which SET is to be performed
	* @throws AgentException
	*/
	public void setAttributes(Hashtable values,Vector subIdList) throws AgentException { 
		Object var = null;
		int size = subIdList.size();
		for(int i = 0;i < size;i++) {
			failedSubId = (Integer)subIdList.elementAt(i);
			var = values.get(failedSubId);

			switch(failedSubId.intValue()){
				case QUERYCOUNTER:
					// This attribute is not Writable !!! 
					break;
				case SCREENRESOLUTIONWIDTH:
					// This attribute is not Writable !!! 
					break;
				case SCREENRESOLUTIONHEIGHT:
					// This attribute is not Writable !!! 
					break;
				case FOLDERTOCOUNT:
					setFolderToCount((String)var);
					break;
				case FILESINFOLDER:
					// This attribute is not Writable !!! 
					break;
				case FILESYSTEMCOUNT:
					// This attribute is not Writable !!! 
					break;
				default:

			}
		}
	}
}
