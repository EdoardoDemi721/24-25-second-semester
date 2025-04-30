package hydraulic;

/**
 * Represents a tap that can interrupt the flow.
 * 
 * The status of the tap is defined by the method
 * {@link #setOpen(boolean) setOpen()}.
 */

public class Tap extends Element {

	/**
	 * Constructor
	 * @param name name of the tap element
	 */
	public Tap(String name) {
		super(name);
	}

	private boolean open;

	/**
	 * Set whether the tap is open or not. The status is used during the simulation.
	 *
	 * @param open opening status of the tap
	 */
	public void setOpen(boolean open){
		this.open = open;
	}

	@Override
	public void simulate(SimulationObserver observer, double inFlow) {
		double outFlow = open ? inFlow : 0.0;
		observer.notifyFlow(this.getClass().getSimpleName(), getName(), inFlow, outFlow);
		if (next != null) {
			next.simulate(observer, outFlow);
		}
	}

	@Override
	public void simulateConstrained(SimulationObserver observer, double inFlow) {
		// Check if maxFlow is defined and exceeded
		if (maxFlow != null && inFlow > maxFlow) {
			observer.notifyFlowError(this.getClass().getSimpleName(), getName(), inFlow, maxFlow);
		}

		// Calculate the output flow based on the tap's status
		double outFlow = open ? inFlow : 0.0;

		// Notify the observer about the flow
		observer.notifyFlow(this.getClass().getSimpleName(), getName(), inFlow, outFlow);

		// Propagate the flow to the next element
		if (next != null) {
			next.simulateConstrained(observer, outFlow);
		}
	}
	
}
