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

/**
 * @Version : 6.0.3 Wed Nov 07 22:42:30 EST 2012
 * @Author  : WebNMS Agent Toolkit Java Edition
 */

// Any changes made to this file will be lost, if regenerated. 
// User code should be added within user tags for code merging support, if regenerated.

// Package Name (Don't Delete this comment)
package br.unisinos.gerenciaDeRedes;

// SNMP Agent API Imports
import com.adventnet.snmp.snmp2.agent.AgentRuntimeException;
import com.adventnet.snmp.snmp2.agent.AgentUtil;
import com.adventnet.snmp.snmp2.agent.PduRequestHandler;
import com.adventnet.snmp.snmp2.agent.SnmpAgent;
import com.adventnet.snmp.snmp2.agent.SnmpTrapService;
import com.adventnet.snmp.snmp2.agent.TrapRequestListener;
import com.adventnet.snmp.snmp2.agent.BaseTableEntry;
import com.adventnet.agent.snmp.configuration.AgentConfigInitializer;
import com.adventnet.snmp.snmp2.agent.SnmpAgentInitializer;

// SnmpV3 Imports
import com.adventnet.snmp.snmp2.agent.SnmpTargetAddrTableRequestHandler;
import com.adventnet.snmp.snmp2.agent.SnmpTargetAddrExtTableRequestHandler;
import com.adventnet.snmp.snmp2.agent.SnmpTargetObjectsRequestHandler;
import com.adventnet.snmp.snmp2.agent.SnmpTargetParamsTableRequestHandler;

import com.adventnet.snmp.snmp2.agent.community.SnmpCommunityTableRequestHandler;

import com.adventnet.snmp.snmp2.agent.notification.SnmpNotifyTableRequestHandler;
import com.adventnet.snmp.snmp2.agent.notification.SnmpNotifyFilterTableRequestHandler;
import com.adventnet.snmp.snmp2.agent.notification.SnmpNotifyFilterProfileTableRequestHandler;

import com.adventnet.snmp.snmp2.agent.usm.UsmUserRequestHandler;
import com.adventnet.snmp.snmp2.agent.usm.UsmUserTableRequestHandler;

import com.adventnet.snmp.snmp2.agent.vacm.VacmMIBViewsRequestHandler;
import com.adventnet.snmp.snmp2.agent.vacm.VacmAccessTableRequestHandler;
import com.adventnet.snmp.snmp2.agent.vacm.VacmContextTableRequestHandler;
import com.adventnet.snmp.snmp2.agent.vacm.VacmSecurityToGroupTableRequestHandler;
import com.adventnet.snmp.snmp2.agent.vacm.VacmViewTreeFamilyTableRequestHandler;

// Agent Utility API Imports
import com.adventnet.utils.agent.AgentParamOptions;
import com.adventnet.utils.agent.DynamicRegistration;
import com.adventnet.utils.agent.TableEntry;
import com.adventnet.utilities.common.CommonUtils;

// Java Imports
import com.adventnet.utils.agent.RegistrationListenerException;
import java.util.Vector;
import java.util.Hashtable;
import java.io.File;


/** 
 * SnmpAgent_brunomateus Main Class
 * <p>
 * This is the main file which extends the <code> SnmpAgent</code>
 * and instantiates <code>PduRequestHandler</code> which handles
 * the registration of the Agent SubTrees.
 * This includes methods for checking external Index availability.
 * <p> 
 */

public class SnmpAgent_brunomateus extends SnmpAgent implements Runnable{

	public static String agentDir = ".";
	private static String ipAddress="127.0.0.1";

	static {
		AgentUtil.setAgentDir(agentDir);
	}

	/**
	 * Primary constructor of the Agent
	 * @see #SnmpAgent_brunomateus(AgentParamOptions agentOptions)
	 * @see #SnmpAgent_brunomateus(String[] args)
	 */
	public SnmpAgent_brunomateus(){
		this(new AgentParamOptions());
	}

	/**
	 * Constructor with AgentParamOptions
	 * @see #SnmpAgent_brunomateus()
	 * @see #SnmpAgent_brunomateus(String[] args)
	 * @param agentOptions The Agent configuration options while starting.
	 */
	public SnmpAgent_brunomateus(AgentParamOptions agentOptions){
		this.agentOptions = agentOptions;
		if(!agentOptions.getAgentDir().equals(".")){
			AgentUtil.setAgentDir(agentOptions.getAgentDir());
		}
		Thread th = new Thread(this);
		th.start();
	}

	/**
	 * Constructor with commandline argument options
	 * @see #SnmpAgent_brunomateus()
	 * @see #SnmpAgent_brunomateus(AgentParamOptions agentOptions)
	 * @param args The Agent configuration options while starting.
	 */
	public SnmpAgent_brunomateus(String[] args){
		// This takes care of the options
		this(new AgentParamOptions( args));
	}

	/**
	 * This Starts the Agent
	 * @see #init
	 * @see #initSnmpAgent_brunomateus
	 */
	public void run(){
		try{
			init();
		}
		catch(AgentRuntimeException e){
			System.out.println("Bind Exception :: Port " + super.getPort() + " is in use");
		}
		catch(RegistrationListenerException te){
			System.out.println("RegistrationListenerException :: SnmpAgent is a Unicast Bean");
		}
	}

	/**
	 * This method is called by the Agent run method.
	 * @see #initSnmpAgent_brunomateus
	 * @exception AgentRuntimeException on inavailability of port to bind.
	 * @exception RegistrationListenerException if the registration tree is already registered.
	 */
	public void init() throws AgentRuntimeException, RegistrationListenerException{
		try{
			initSnmpAgent_brunomateus();
		}
		catch(AgentRuntimeException e){
			throw e;
		}
		catch(RegistrationListenerException te){
			throw te;
		}
	}

	/**
	 * Sets the Agent with the configuration options and restarts the SNMP Agent.
	 * @see #initSnmpExtensionNodes 
	 * @exception AgentRuntimeException on inavailability of port to bind.
	 * @exception RegistrationListenerException if the registration tree is already registered.
	 */
	public void initSnmpAgent_brunomateus() throws AgentRuntimeException, RegistrationListenerException{

		// Setting Agent parameters from Startup XML file
		Hashtable agentConf = null;
		try{
			agentConf = AgentConfigInitializer.parseInitializerProperties(agentDir+File.separator+"conf"+File.separator+"snmpproject02AgentStartup.xml");
		}catch(Exception e)
		{
		System.out.println("Exception while initializing agent details from XML file: snmpproject02AgentStartup.xml.");
		}
		SnmpAgentInitializer.setAgentReference((SnmpAgent) this);
		SnmpAgentInitializer.initializeAgentDetails(agentConf, agentOptions);

		String version = super.getSnmpVersion();
		trapListener = SnmpAgentInitializer.getTrapListener();

		 // Initializing V3 Compliance Tables
		SnmpAgentInitializer.initializeV3ComplianceTables();
		if(version.equalsIgnoreCase("V3")){
			initializeV3Settings();
		}

		// Initializing the Trap Forwarding Table
		SnmpAgentInitializer.initializeForwardingTables(hdlr);
		Hashtable configTableInstances = SnmpAgentInitializer.getConfigurationTables();
		forwardingTable = (com.adventnet.snmp.snmp2.agent.ForwardingTable)configTableInstances.get("V1V2TfTable");
		v3ForwardingTable = (com.adventnet.snmp.snmp2.agent.V3ForwardingTable)configTableInstances.get("V3TfTable");
		if(version.equalsIgnoreCase("V3")){
			v3ForwardingTable.addV3ForwardingEntry(com.adventnet.snmp.snmp2.agent.V3SimpleTrapForwardingTable.createV3ForwardingEntry("127.0.0.1", new Integer(8003), new Integer(3), "public", "noAuthUser", new Long(3), new Integer(1), "noAuth", new Long(5000), new Long(0)));
		}

		try{
			super.addSnmpPduRequestListener(hdlr);
			super.addTrapRequestListener((TrapRequestListener)trapListener);
		}
		catch(RegistrationListenerException e){
			throw e;
		}

		super.setDefaultTrap(false);

		try{
			super.restartSnmpAgent();
		}
 		catch(AgentRuntimeException ee){
			// Here add appropriate code to handle session.
			throw ee;
		}

		// Setting the agent reference in CommonUtils, so that
		// this can be used in xxxInstrument and xxxEntries 
		CommonUtils.setAgentReference(this);

		// Initializing tables for Group Counters and V2 Compliance 
		SnmpAgentInitializer.initializeV1V2ComplianceTables(hdlr);

		// Initializing authentication tables
		SnmpAgentInitializer.initializeAccessControlTables(hdlr);
		aclTable = (com.adventnet.snmp.snmp2.agent.AclTable)configTableInstances.get("AclTable");

		// Setting the Transport Protocol
		SnmpAgentInitializer.initializeTransportProvider();
		initSnmpExtensionNodes ();
	}

	/**
	 * Adds VarBindRequestListeners to SnmpAgent.
	 * Registers the Agent SubTrees to the <code>PduRequestHandler</code>
	 */
	public void initSnmpExtensionNodes(){

		brunomateusListener =  new BrunomateusRequestHandler(this);
		brunomateusListener.setBrunomateusInstrument(getBrunomateusInstance());
		brunomateusListener.addRegistrationListener(hdlr);



	}


	/**
	 * Getter for BrunomateusRequestHandler 
	 * @return Object of BrunomateusRequestHandler
	 */
	public BrunomateusRequestHandler getBrunomateus(){
		return brunomateusListener;
	}

	/**
	 * Getter for BrunomateusInstrument 
	 * @return Object of BrunomateusInstrument 
	 */
	public BrunomateusInstrument getBrunomateusInstance(){
		return new BrunomateusInstrument();
	}


	// Variable Declarations
	protected AgentParamOptions agentOptions = null;

	// The Registration Listener
	protected PduRequestHandler hdlr = new PduRequestHandler ();

	//  The Snmp Trap Service to send traps
	protected  SnmpTrapService trapListener = null;

	// Acl Table Support
	private com.adventnet.snmp.snmp2.agent.AclTable aclTable = null;

	// V3Trap Forwarding Table Support.
	private com.adventnet.snmp.snmp2.agent.V3ForwardingTable v3ForwardingTable = null;

	// V1V2Trap Forwarding Table Support.
	private com.adventnet.snmp.snmp2.agent.ForwardingTable forwardingTable = null;


	// V3 Support.
	private com.adventnet.snmp.snmp2.agent.usm.UsmUserRequestHandler usmUserListener = null;

	private com.adventnet.snmp.snmp2.agent.usm.UsmUserTableRequestHandler usmUserTableListener = null;

	private com.adventnet.snmp.snmp2.agent.vacm.VacmMIBViewsRequestHandler vacmMIBViewsListener = null;

	private com.adventnet.snmp.snmp2.agent.vacm.VacmContextTableRequestHandler vacmContextTableListener = null;

	private com.adventnet.snmp.snmp2.agent.vacm.VacmSecurityToGroupTableRequestHandler vacmSecurityToGroupTableListener = null;

	private com.adventnet.snmp.snmp2.agent.vacm.VacmAccessTableRequestHandler vacmAccessTableListener = null;

	private com.adventnet.snmp.snmp2.agent.vacm.VacmViewTreeFamilyTableRequestHandler vacmviewTreeFamilyTableListener = null;

	private com.adventnet.snmp.snmp2.agent.community.SnmpCommunityTableRequestHandler snmpCommunityTableListener = null;

	private com.adventnet.snmp.snmp2.agent.SnmpTargetObjectsRequestHandler snmpTargetObjectsListener = null;

	private com.adventnet.snmp.snmp2.agent.SnmpTargetParamsTableRequestHandler snmpTargetParamsTableListener = null;

	private com.adventnet.snmp.snmp2.agent.SnmpTargetAddrTableRequestHandler snmpTargetAddrTableListener = null;

	private com.adventnet.snmp.snmp2.agent.SnmpTargetAddrExtTableRequestHandler snmpTargetAddrExtTableListener = null;

	private com.adventnet.snmp.snmp2.agent.notification.SnmpNotifyTableRequestHandler snmpNotifyTableListener = null;

	private com.adventnet.snmp.snmp2.agent.notification.SnmpNotifyFilterTableRequestHandler snmpNotifyFilterTableListener = null;

	private com.adventnet.snmp.snmp2.agent.notification.SnmpNotifyFilterProfileTableRequestHandler snmpNotifyFilterProfileTableListener = null;

	private BrunomateusRequestHandler brunomateusListener = null;

	/**
	 * Used to run Agent as a stand-alone application.
	 * @param args The Agent configuration parameters passed from commandline.
	 */
	public static void main(String[] args){
		AgentUtil.parseLoggingParameters(agentDir+File.separator+"conf"+File.separator+"snmpproject02AgentStartup.xml",args);
		SnmpAgent_brunomateus agent_sim = new SnmpAgent_brunomateus (args);
	}

	/**
	 * This method stops this Snmp Agent
	 */
	public void shutDownSNMPAgent(){
		killSnmpAgent();
	}

	/**
	 * This method initializes all the V3Tables.
	 */

	public void initializeV3Settings(){

		boolean targetComNotifSupport =SnmpAgentInitializer.getTargetComNotifSupport();
		byte[] engineID = AgentUtil.genEngineID(2162,ipAddress, super.getPort());
	/**
	 * 2162 is enterprise no for ZOHO Corp.You can give your own enterprise prise number 
	 */

		super.getSnmpAPI().setSnmpEngineID(engineID);

		usmUserListener =  new UsmUserRequestHandler(this);
		usmUserListener.addRegistrationListener(hdlr);

		usmUserTableListener =  new UsmUserTableRequestHandler(this, "conf", "UsmUserTable.xml", "xml");
		usmUserTableListener.addRegistrationListener(hdlr);

		vacmMIBViewsListener = new VacmMIBViewsRequestHandler(this);
		vacmMIBViewsListener.addRegistrationListener(hdlr);

		vacmContextTableListener =  new VacmContextTableRequestHandler(this, "conf", "VacmContextTable.xml", "xml");
		vacmContextTableListener.addRegistrationListener(hdlr);

		vacmSecurityToGroupTableListener =  new VacmSecurityToGroupTableRequestHandler(this, "conf", "VacmSecurityToGroupTable.xml", "xml");
		vacmSecurityToGroupTableListener.addRegistrationListener(hdlr);

		vacmAccessTableListener = new VacmAccessTableRequestHandler(this, "conf", "VacmAccessTable.xml", "xml");
		vacmAccessTableListener.addRegistrationListener(hdlr);

		vacmviewTreeFamilyTableListener = new VacmViewTreeFamilyTableRequestHandler(this, "conf", "VacmViewTreeFamilyTable.xml", "xml");
		vacmviewTreeFamilyTableListener.addRegistrationListener(hdlr);

		if (targetComNotifSupport){
			snmpCommunityTableListener = new SnmpCommunityTableRequestHandler(this, "conf", "SnmpCommunityTable.xml", "xml");
			snmpCommunityTableListener.addRegistrationListener(hdlr);

			snmpTargetObjectsListener = new SnmpTargetObjectsRequestHandler(this);
			snmpTargetObjectsListener.addRegistrationListener(hdlr);

			snmpTargetParamsTableListener = new SnmpTargetParamsTableRequestHandler(this, "conf", "SnmpTargetParamsTable.xml", "xml");
			snmpTargetParamsTableListener.addRegistrationListener(hdlr);

			snmpTargetAddrTableListener = new SnmpTargetAddrTableRequestHandler(this, "conf", "SnmpTargetAddrTable.xml", "xml");
			snmpTargetAddrTableListener.addRegistrationListener(hdlr);

			snmpTargetAddrExtTableListener = new SnmpTargetAddrExtTableRequestHandler(this, "conf", "SnmpTargetAddrExtTable.xml", "xml");
			snmpTargetAddrExtTableListener.addRegistrationListener(hdlr);

			snmpNotifyTableListener = new SnmpNotifyTableRequestHandler(this, "conf", "SnmpNotifyTable.xml", "xml");
			snmpNotifyTableListener.addRegistrationListener(hdlr);

			snmpNotifyFilterTableListener = new SnmpNotifyFilterTableRequestHandler(this, "conf", "SnmpNotifyFilterTable.xml", "xml");
			snmpNotifyFilterTableListener.addRegistrationListener(hdlr);

			snmpNotifyFilterProfileTableListener = new SnmpNotifyFilterProfileTableRequestHandler(this, "conf", "SnmpNotifyFilterProfileTable.xml", "xml");
			snmpNotifyFilterProfileTableListener.addRegistrationListener(hdlr);
		}
	}
}
