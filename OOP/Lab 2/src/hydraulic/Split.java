package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends Element {

	protected Element[] next;
	/**
	 * Constructor
	 * @param name name of the split element
	 */
	public Split(String name) {
		super(name);
		this.next= new Element[2];
	}

	@Override
	public void connect(Element elem, int index){
		if(index>=0 && index<2) {
			if (next[index] != null) {
				System.err.println(this.getName()+" already connected to "+next[index].getName());
				return;
			}
			next[index] = elem;
			elem.input = this; // Set the upstream element
		} else {
			System.err.println("Index has to be >=0 and <"+2);
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
	public Element[] getOutputs(){
		return next;
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

	@Override
	public void simulate(SimulationObserver observer, double inFlow) {
		double outFlow = inFlow / 2;
		observer.notifyFlow(this.getClass().getSimpleName(), getName(), inFlow, outFlow, outFlow);
		for (Element output : next) {
			if (output != null) {
				output.simulate(observer, outFlow);
			}
		}
	}

	@Override
	public void simulateConstrained(SimulationObserver observer, double inFlow) {
		// Check if maxFlow is defined and exceeded
		if (maxFlow != null && inFlow > maxFlow) {
			observer.notifyFlowError(this.getClass().getSimpleName(), getName(), inFlow, maxFlow);
		}

		// Calculate the output flow for each branch
		double outFlow = inFlow / 2;

		// Notify the observer about the flow
		observer.notifyFlow(this.getClass().getSimpleName(), getName(), inFlow, outFlow, outFlow);

		// Propagate the flow to each connected output
		for (Element output : next) {
			if (output != null) {
				output.simulateConstrained(observer, outFlow);
			}
		}
	}
}
