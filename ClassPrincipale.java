import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class ClassPrincipale {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		jade.core.Runtime rt = jade.core.Runtime.instance();
		ProfileImpl pMain = new ProfileImpl(null, 2019, "Plateforme");
		AgentContainer mc = rt.createMainContainer(pMain);

		AgentController rma; // =null ?
		try {
			rma = mc.createNewAgent("rma", "jade.tools.rma.rma", null);
			rma.start();
			
			Integer tab[]=new Integer[2];
			tab[0]=new Integer(10);
			tab[1]=new Integer(20);
			
			AgentController AgentAG = mc.createNewAgent("AgentAG","myAgents.AgentAG", null);
			AgentAG.start();
			
			/*
			AgentController AgentA = mc.createNewAgent("AgentA","myAgents.AgentA", null);
			AgentA.start();
			AgentController AgentB = mc.createNewAgent("AgentB","myAgents.AgentB", null);
			AgentB.start();
			AgentController boucle = mc.createNewAgent("boucle","myAgents.boucle", null);
			boucle.start();
			AgentController hello = mc.createNewAgent("toto","myAgents.HelloWorldAgent", tab); 
			hello.start();
			*/
						
		} catch (StaleProxyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
