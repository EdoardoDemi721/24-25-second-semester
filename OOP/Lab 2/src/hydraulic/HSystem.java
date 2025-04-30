package hydraulic;

/**
 * Main class that acts as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem {

// R1
	private static final int MAX_ELEMENTS=100;
	private Element[] elements = new Element[MAX_ELEMENTS];
	private int count=0;

	/**
	 * Adds a new element to the system
	 * 
	 * @param elem the new element to be added to the system
	 */
	public void addElement(Element elem){
		if(count<MAX_ELEMENTS)
		{
			this.elements[count++]=elem;
		}
		else
		{
			System.err.println("Error, limit reached");
		}
	}

	/**
	 * returns the number of element currently present in the system
	 * 
	 * @return count of elements
	 */
	public int size() {
		return count;
    }

	/**
	 * returns the element added so far to the system
	 * 
	 * @return an array of elements whose length is equal to 
	 * 							the number of added elements
	 */
	public Element[] getElements(){
		Element[] toReturn = new Element[count];
		System.arraycopy(this.elements, 0, toReturn, 0, count);
		return toReturn;
	}

// R4
	/**
	 * starts the simulation of the system
	 * 
	 * The notification about the simulations are sent
	 * to an observer object
	 * 
	 * Before starting simulation the parameters of the
	 * elements of the system must be defined
	 * 
	 * @param observer the observer receiving notifications
	 */
	public void simulate(SimulationObserver observer) {
		for (Element element : elements) {
			if (element instanceof Source) {
				element.simulate(observer, SimulationObserver.NO_FLOW);
			}
		}
	}


// R6
	/**
	 * Deletes a previously added element 
	 * with the given name from the system
	 */
	public boolean deleteElement(String name) {
		for (int i = 0; i < count; i++) {
			if (elements[i].getName().equals(name)) {
				Element el = elements[i];

				// Check if the element is a Split or Multisplit
				if (el instanceof Split || el instanceof Multisplit) {
					Element[] outputs = el.getOutputs();
					int connectedOutputs = 0;
					for (Element output : outputs) {
						if (output != null) connectedOutputs++;
					}
					if (connectedOutputs > 1) {
						return false; // Cannot delete if more than one output is connected
					}
				}

				// Retrieve input and output
				Element input = el.getInput();
				Element output = el.getOutput();

				// Handle Split or Multisplit as input
				if (input instanceof Split || input instanceof Multisplit) {
					Element[] inputOutputs = input.getOutputs();
					for (int index = 0; index < inputOutputs.length; index++) {
						if (inputOutputs[index] == el) {
							input.disconnect(index); // Disconnect at the correct index
							if (output != null) {
								input.connect(output, index); // Reconnect output at the same index
							}
							break;
						}
					}
				} else if (input != null) {
					// Regular input handling
					input.disconnect();
					if (output != null) {
						input.connect(output);
					}
				}

				// Shift elements to remove the deleted one
				for (int j = i; j < count - 1; j++) {
					elements[j] = elements[j + 1];
				}
				elements[--count] = null; // Clear the last element
				
				return true;
			}
		}
		return false; // No match found
	}

// R7
	/**
	 * starts the simulation of the system; if {@code enableMaxFlowCheck} is {@code true},
	 * checks also the elements maximum flows against the input flow
	 * 
	 * If {@code enableMaxFlowCheck} is {@code false}  a normals simulation as
	 * the method {@link #simulate(SimulationObserver)} is performed
	 * 
	 * Before performing a checked simulation the max flows of the elements in thes
	 * system must be ddefined.
	 */
	public void simulate(SimulationObserver observer, boolean enableMaxFlowCheck) {
		if (!enableMaxFlowCheck) {
			// Call the existing simulation method
			simulate(observer);
			return;
		}

		// Iterate through elements to find sources and start constrained simulation
		for (Element element : elements) {
			if (element instanceof Source) {
				element.simulateConstrained(observer, SimulationObserver.NO_FLOW);
			}
		}
	}

// R8
	/**
	 * creates a new builder that can be used to create a 
	 * hydraulic system through a fluent API 
	 * 
	 * @return the builder object
	 */
    public static HBuilder build() {
		return new HBuilder();
    }
}
