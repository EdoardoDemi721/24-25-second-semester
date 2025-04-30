package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends Element {

	/**
	 * Constructor
	 * @param name name of the sink element
	 */
	public Sink(String name) {
		super(name);
	}

	@Override
	public void connect(Element elem) {
		
	}

	@Override
	public void simulate(SimulationObserver observer, double inFlow) {
		observer.notifyFlow(this.getClass().getSimpleName(), getName(), inFlow, SimulationObserver.NO_FLOW);
	}

	@Override
	public void simulateConstrained(SimulationObserver observer, double inFlow) {
		// Check if maxFlow is defined and exceeded
		if (maxFlow != null && inFlow > maxFlow) {
			observer.notifyFlowError(this.getClass().getSimpleName(), getName(), inFlow, maxFlow);
		}

		// Notify the observer about the flow
		observer.notifyFlow(this.getClass().getSimpleName(), getName(), inFlow, SimulationObserver.NO_FLOW);
	}
}
