package DimmerAgent;

import jade.core.Agent;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.util.leap.Iterator;
import jade.lang.acl.ACLMessage;

public class DimmerAgent extends Agent {

	public void setup() {
		String agent;
		agent = this.searchAgent();
		if(agent != "False"){
			this.sendMessage("blackboard", "Hola");
			System.out.println(agent);	
		}else{
			
		}
	}
	
	public String searchAgent(){
		
		String service = "BlackboardAgent";
		
		String name = "False";
		
		try {
			DFAgentDescription template = new DFAgentDescription();
	  		ServiceDescription templateSd = new ServiceDescription();
	  		templateSd.setType(service);
	  		template.addServices(templateSd);
	  		
	  		SearchConstraints sc = new SearchConstraints();
	  		// We want to receive 10 results at most
	  		sc.setMaxResults(new Long(10));
	  		
	  		DFAgentDescription[] results = DFService.search(this, template, sc);
	  		if (results.length > 0) {
	  			//System.out.println("Agent "+getLocalName()+" found the following "+service+" services:");
	  			for (int i = 0; i < results.length; ++i) {
	  				DFAgentDescription dfd = results[i];
	  				AID provider = dfd.getName();
	  				Iterator it = dfd.getAllServices();
	  				while (it.hasNext()) {
	  					ServiceDescription sd = (ServiceDescription) it.next();
	  					if (sd.getType().equals(service)) {
	  						name = sd.getName();
	  						return name;
 						    //System.out.println("- Service \""+sd.getName()+"\" provided by agent "+provider.getName());
	  					}
	  				}
	  			}
	  		}else {
	  			name = "False";

 			    //System.out.println("Agent "+getLocalName()+" did not find any BlackboardAgent service");
	  		}
	  		
	  		return name; 
	  	}
	  	catch (FIPAException fe) {
	  		//fe.printStackTrace();
	  		String error = "False";
  			return error;
	  	}
	}
	
	private void sendMessage(String recept, String message){
		ACLMessage messages = new ACLMessage(ACLMessage.REQUEST);
	    messages.setContent(message);
	    messages.addReceiver(new AID(recept, AID.ISLOCALNAME));
	    send(messages);
	    System.out.println("Message sended");
	}
	
}
