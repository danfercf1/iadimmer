package Blackboard;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.util.Logger;
import java.util.*;


public class Blackboard extends Agent {

	private Logger myLogger = Logger.getMyLogger(getClass().getName());
	
	private class WaitMessageAndReplyBehavior extends CyclicBehaviour {
		
		private Hashtable<String, String> catalogue = new Hashtable<String, String>();

		public WaitMessageAndReplyBehavior(Agent a) {
			super(a);
		}

		public void action() {
			ACLMessage  msg = myAgent.receive();
			if(msg != null){
				ACLMessage reply = msg.createReply();
				
				System.out.println(this);
				
				if(msg.getPerformative() == ACLMessage.REQUEST){
					String content = msg.getContent();
					myLogger.log(Logger.INFO, "Agent "+getLocalName()+" - Received Request from "+msg.getSender().getLocalName());
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent("Data saved");
					String name  = msg.getSender().getLocalName();
					this.updateBlackboard(name, content);
					this.getDataBlackboard();
				}else {
					myLogger.log(Logger.INFO, "Agent "+getLocalName()+" - Unexpected message ["+ACLMessage.getPerformative(msg.getPerformative())+"] received from "+msg.getSender().getLocalName());
					reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
					reply.setContent("( (Unexpected-act "+ACLMessage.getPerformative(msg.getPerformative())+") )");   
				}
				send(reply);
			}
			else {
				block();
			}
		}
		
		public void updateBlackboard(final String agent, final String msg) {
			addBehaviour(new OneShotBehaviour() {
				public void action() {
					catalogue.put(new String(agent), new String(msg));
					Integer num = catalogue.size();
					System.out.println("Data has been inserted into blackboard by Agent: "+agent+". Total = "+num);
				}
			} );
		}
		
		public void getDataBlackboard(){
			if(catalogue.isEmpty() == false){
				Set<String> keys = catalogue.keySet();
				System.out.println("BlackBoard data:");
		        for(String key: keys){
		            System.out.println("Value of "+key+" is: "+catalogue.get(key));
		        }	
			}else{
				System.out.println("Empty Dic");
			}
		}
		
		
	}
	

	protected void setup() {
		// Registration with the DF 
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();   
		sd.setType("BlackboardAgent"); 
		sd.setName(getName());
		sd.setOwnership("BOARD");
		dfd.setName(getAID());
		dfd.addServices(sd);
		try {
			DFService.register(this,dfd);
			WaitMessageAndReplyBehavior BlackboardBehavior = new  WaitMessageAndReplyBehavior(this);
			addBehaviour(BlackboardBehavior);
		} catch (FIPAException e) {
			myLogger.log(Logger.SEVERE, "Agent "+getLocalName()+" - Cannot register with DF", e);
			doDelete();
		}
	}
}
