package hydraulic;

import java.util.Stack;

/**
 * Hydraulics system builder providing a fluent API
 */
public class HBuilder {
    private HSystem system;
    private Element lastElement;
    private Stack<Split> splitStack = new Stack<>();
    private Stack<Integer> outputIndexStack = new Stack<>();

    public HBuilder() {
        this.system = new HSystem();
    }

    /**
     * Add a source element with the given name
     * 
     * @param name name of the source element to be added
     * @return the builder itself for chaining 
     */
    public HBuilder addSource(String name) {
        Source source = new Source(name);
        system.addElement(source);
        lastElement = source;
        return this;
    }

    /**
     * returns the hydraulic system built with the previous operations
     * 
     * @return the hydraulic system
     */
    public HSystem complete() {
        return this.system;
    }

    /**
     * creates a new tap and links it to the previous element
     * 
     * @param name name of the tap
     * @return the builder itself for chaining 
     */
    public HBuilder linkToTap(String name) {
        Tap tap = new Tap(name);
        system.addElement(tap);
        if (lastElement instanceof Split) {
            int outputIndex = outputIndexStack.peek();
            ((Split) lastElement).connect(tap, outputIndex);
        } else {
            lastElement.connect(tap);
        }
        lastElement = tap;
        return this;
    }

    /**
     * creates a sink and link it to the previous element
     * @param name name of the sink
     * @return the builder itself for chaining 
     */
    public HBuilder linkToSink(String name) {
        Sink sink = new Sink(name);
        system.addElement(sink);
        if (lastElement instanceof Split) {
            int outputIndex = outputIndexStack.peek();
            ((Split) lastElement).connect(sink, outputIndex);
        } else {
            lastElement.connect(sink);
        }
        lastElement = sink;
        return this;
    }

    /**
     * creates a split and links it to the previous element
     * @param name of the split
     * @return the builder itself for chaining 
     */
    public HBuilder linkToSplit(String name) {
        Split split = new Split(name);
        system.addElement(split);
        if (lastElement instanceof Split) {
            int outputIndex = outputIndexStack.peek();
            ((Split) lastElement).connect(split, outputIndex);
        } else {
            lastElement.connect(split);
        }
        lastElement = split;
        return this;
    }

    /**
     * creates a multisplit and links it to the previous element
     * @param name name of the multisplit
     * @param numOutput the number of output of the multisplit
     * @return the builder itself for chaining 
     */
    public HBuilder linkToMultisplit(String name, int numOutput) {
        Multisplit multisplit = new Multisplit(name, numOutput);
        system.addElement(multisplit);
        if (lastElement instanceof Split) {
            int outputIndex = outputIndexStack.peek();
            ((Split) lastElement).connect(multisplit, outputIndex);
        } else {
            lastElement.connect(multisplit);
        }
        lastElement = multisplit;
        return this;
    }

    /**
     * introduces the elements connected to the first output 
     * of the latest split/multisplit.
     * The element connected to the following outputs are 
     * introduced by {@link #then()}.
     * 
     * @return the builder itself for chaining 
     */

    public HBuilder withOutputs() {
        splitStack.push((Split) lastElement);
        outputIndexStack.push(0);
        return this;
    }

    /**
     * inform the builder that the next element will be
     * linked to the successive output of the previous split or multisplit.
     * The element connected to the first output is
     * introduced by {@link #withOutputs()}.
     * 
     * @return the builder itself for chaining 
     */
    public HBuilder then() {
        int outputIndex = outputIndexStack.pop() + 1;
        outputIndexStack.push(outputIndex);
        lastElement = splitStack.peek();
        return this;
    }

    /**
     * completes the definition of elements connected
     * to outputs of a split/multisplit. 
     * 
     * @return the builder itself for chaining 
     */
    public HBuilder done() {
        splitStack.pop();
        outputIndexStack.pop();
        lastElement = splitStack.isEmpty() ? null : splitStack.peek();
        return this;
    }

    /**
     * define the flow of the previous source
     * 
     * @param flow flow used in the simulation
     * @return the builder itself for chaining 
     */
    public HBuilder withFlow(double flow) {
        Source source = (Source) lastElement;
        source.setFlow(flow);
        return this;
    }

    /**
     * define the status of a tap as open,
     * it will be used in the simulation
     * 
     * @return the builder itself for chaining 
     */
    public HBuilder open() {
        Tap tap = (Tap) lastElement;
        tap.setOpen(true);
        return this;
    }

    /**
     * define the status of a tap as closed,
     * it will be used in the simulation
     * 
     * @return the builder itself for chaining 
     */
    public HBuilder closed() {
        Tap tap = (Tap) lastElement;
        tap.setOpen(false);
        return this;
    }

    /**
     * define the proportions of input flow distributed
     * to each output of the preceding a multisplit
     * 
     * @param props the proportions
     * @return the builder itself for chaining 
     */
    public HBuilder withPropotions(double[] props) {
        Multisplit multisplit = (Multisplit) lastElement;
        multisplit.setProportions(props);
        return this;
    }

    /**
     * define the maximum flow theshold for the previous element
     * 
     * @param max flow threshold
     * @return the builder itself for chaining 
     */
    public HBuilder maxFlow(double max) {
        lastElement.setMaxFlow(max);
        return this;
    }
}
