package hydraulic;


/**
 * Represents the generic abstract element of an hydraulics system.
 * It is the base class for all elements.
 *
 * Any element can be connect to a downstream element
 * using the method {@link #connect(Element) connect()}.
 * 
 * The class is abstract since it is not intended to be instantiated,
 * though all methods are defined to make subclass implementation easier.
 */
public abstract class Element {
	private String name;
	protected Element next;
	protected Element input; // New attribute to track the upstream element
	protected Double maxFlow; // Attribute to store the maximum flow, null if not set

	public Element (String name){
		this.name=name;
	}

	/**
	 * getter method for the name of the element
	 * 
	 * @return the name of the element
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Connects this element to a given element.
	 * The given element will be connected downstream of this element
	 * 
	 * In case of element with multiple outputs this method operates on the first one,
	 * it is equivalent to calling {@code connect(elem,0)}. 
	 * 
	 * @param elem the element that will be placed downstream
	 */
	public void connect(Element elem) {
		if (next!=null){
			System.err.println(this.getName()+" already connected to "+next.getName());
		}
		else{
			this.next=elem;
			elem.input = this; // Set the upstream element
		}
	}
	
	/**
	 * Connects a specific output of this element to a given element.
	 * The given element will be connected downstream of this element
	 * 
	 * @param elem the element that will be placed downstream
	 * @param index the output index that will be used for the connection
	 */
	public void connect(Element elem, int index){
		
	}
	
	/**
	 * Retrieves the single element connected downstream of this element
	 * 
	 * @return downstream element
	 */
	public Element getOutput(){
		return next;
	}

	/**
	 * Retrieves the elements connected downstream of this element
	 * 
	 * @return downstream element
	 */
	public Element[] getOutputs(){
		return null;
	}
	
	/**
	 * Defines the maximum input flow acceptable for this element.
	 * 
	 * @param maxFlow maximum allowed input flow
	 */
	public void setMaxFlow(double maxFlow) {
		this.maxFlow = maxFlow;
	}

	/**
	 * Retrieves the maximum input flow for this element.
	 * 
	 * @return the maximum flow, or null if not set
	 */
	public Double getMaxFlow() {
		return maxFlow;
	}

	public void simulate(SimulationObserver observer, double inFlow) {
		observer.notifyFlow(this.getClass().getSimpleName(), name, inFlow, SimulationObserver.NO_FLOW);
		if (next != null) {
			next.simulate(observer, SimulationObserver.NO_FLOW);
		}
	}

	/**
	 * Simulates the element with maximum flow constraints.
	 * 
	 * @param observer the observer receiving notifications
	 * @param inFlow the input flow for this element
	 */
	public void simulateConstrained(SimulationObserver observer, double inFlow) {
		// Check if maxFlow is defined and exceeded
		if (maxFlow != null && inFlow > maxFlow) {
			observer.notifyFlowError(this.getClass().getSimpleName(), getName(), inFlow, maxFlow);
		}

		// Notify the observer about the flow
		observer.notifyFlow(this.getClass().getSimpleName(), getName(), inFlow, SimulationObserver.NO_FLOW);

		// Propagate the flow to the next element
		if (next != null) {
			next.simulateConstrained(observer, SimulationObserver.NO_FLOW);
		}
	}

	protected static String pad(String current, String down){
		int n = current.length();
		final String fmt = "\n%"+n+"s";
		return current + down.replace("\n", fmt.formatted("") );
	}

	@Override
	public String toString(){
		String res = "[%s] ".formatted(getName());
		Element[] out = getOutputs();
		if( out != null){
			StringBuilder buffer = new StringBuilder();
			for(int i=0; i<out.length; ++i) {
				if(i>0) buffer.append("\n");
				if (out[i] == null) buffer.append("+-> *");
				else buffer.append(pad("+-> ", out[i].toString()));
			}
			res = pad(res,buffer.toString());
		}
		return res;
	}

	public Element getInput() {
		return input;
	}

	/**
	 * Disconnects this element from its downstream element.
	 */
	public void disconnect() {
		this.next = null; // Break the connection to the downstream element
	}

	public void disconnect(int index) {
		// Default implementation does nothing, can be overridden in subclasses
	}

}
