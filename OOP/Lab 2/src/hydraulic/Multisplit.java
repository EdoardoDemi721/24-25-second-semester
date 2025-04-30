package hydraulic;

/**
 * Represents a multisplit element, an extension of the Split that allows many outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */

public class Multisplit extends Split {

	private final int OUTPUTS;
	private double[] proportions;
	/**
	 * Constructor
	 * @param name the name of the multi-split element
	 * @param numOutput the number of outputs
	 */
	public Multisplit(String name, int numOutput) {
		super(name);
		this.OUTPUTS=numOutput;
		this.next=new Element[OUTPUTS];
		this.proportions=new double[OUTPUTS];
	}
	
	@Override
	public void connect(Element elem, int index){
		if(index>=0 && index<OUTPUTS) {
			if (next[index] != null) {
				System.err.println(this.getName()+" already connected to "+next[index].getName());
				return;
			}
			next[index]=elem;
			elem.input = this; // Set the upstream element
		} else {
			System.err.println("Index has to be >=0 and <"+OUTPUTS);
		}
	}

	@Override
	public void disconnect(int index) {
		if (index >= 0 && index < next.length) {
			next[index] = null; // Break the connection at the specified index
		} else {
			System.err.println("Index has to be >=0 and <" + next.length);
		}
	}

	@Override
	public void simulate(SimulationObserver observer, double inFlow) {
		double[] outFlows = new double[OUTPUTS];
		for (int i = 0; i < OUTPUTS; i++) {
			outFlows[i] = inFlow * proportions[i]; // Calculate flow for each output based on proportions
		}
		observer.notifyFlow(this.getClass().getSimpleName(), getName(), inFlow, outFlows);

		// Simulate each output
		for (int i = 0; i < OUTPUTS; i++) {
			if (next[i] != null) {
				next[i].simulate(observer, outFlows[i]);
			}
		}
	}

	@Override
	public void simulateConstrained(SimulationObserver observer, double inFlow) {
		// Check if maxFlow is defined and exceeded
		if (maxFlow != null && inFlow > maxFlow) {
			observer.notifyFlowError(this.getClass().getSimpleName(), getName(), inFlow, maxFlow);
		}

		// Calculate the output flow for each branch based on proportions
		double[] outFlows = new double[OUTPUTS];
		for (int i = 0; i < OUTPUTS; i++) {
			outFlows[i] = inFlow * proportions[i];
		}

		// Notify the observer about the flow
		observer.notifyFlow(this.getClass().getSimpleName(), getName(), inFlow, outFlows);

		// Propagate the flow to each connected output
		for (int i = 0; i < OUTPUTS; i++) {
			if (next[i] != null) {
				next[i].simulateConstrained(observer, outFlows[i]);
			}
		}
	}

	@Override
	public Element getOutput() {
		int connectedOutputs = 0;
		Element singleOutput = null;
		for (Element output : next) {
			if (output != null) {
				connectedOutputs++;
				if (connectedOutputs > 1) {
					return null; // More than one output connected, return null
				}
				singleOutput = output;
			}
		}
		return singleOutput; // Return the single connected output or null
	}

	/**
	 * Define the proportion of the output flows w.r.t. the input flow.
	 * 
	 * The sum of the proportions should be 1.0 and 
	 * the number of proportions should be equals to the number of outputs.
	 * Otherwise a check would detect an error.
	 * 
	 * @param proportions the proportions of flow for each output
	 */
	public void setProportions(double... proportions) {
		System.arraycopy(proportions, 0, this.proportions, 0, OUTPUTS);
	}
	
}
