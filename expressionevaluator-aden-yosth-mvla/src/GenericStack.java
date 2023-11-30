import java.util.ArrayList;
import java.util.EmptyStackException;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericStack. Implements a generic software stack for any element
 * IMPORTANT: You ned to replace with YOUR GenericStack Implementation!!!
 *
 *
 * @param <E> the element type
 */
public class GenericStack<E>  {
	
	/** The stack.  The stack will be built on a generic ArrayList, but will only
	 *  expose stack methods push, pop, peek, isEmpty and getSize.
	 */
	private ArrayList<E> stack;
	
	/**
	 * Instantiates a new generic stack. The stack is empty at the beginning
	 */
	public GenericStack() {
		stack = new ArrayList<>();
	}
	
	public boolean empty() {
		return (stack.isEmpty());
	}
	
	public int size() {
		return stack.size();
	}
	
	public E peek() {
		if(!stack.isEmpty()) {
			return (stack.get(stack.size() - 1));
		} 
		throw new EmptyStackException();
	}
	
	public E pop() {
		if(!stack.isEmpty()) {
			E temp = stack.get(stack.size() - 1);
			stack.remove(stack.size() - 1);
			return temp;
		}
		throw new EmptyStackException();
	}
	
	public void push(E object) {
		stack.add(object);
	}
	
	

}
