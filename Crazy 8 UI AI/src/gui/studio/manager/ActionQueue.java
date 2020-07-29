package gui.studio.manager;

import java.util.Arrays;
import java.util.NoSuchElementException;

//TODO: May just use a default Queue instead
public class ActionQueue {

	private final int initialSize;
	private int current;
	private Action[] queue;
	
	public ActionQueue() {
		queue = new Action[0];
		initialSize = 0;
		current = -1;
	}
	
	public ActionQueue(int initialSize) {
		if (initialSize <= 0) 
			throw new IllegalArgumentException("initialSize must be greater than 0.");
		
		queue = new Action[initialSize];
		this.initialSize = initialSize;
		current = -1;
	}
	
	public int getCurrent() {
		return current;
	}
	
	public boolean enqueue(Action action) {
		if (initialSize != 0) {
			
			if (current < initialSize) {
				for (int i = 0; i < queue.length; i++) {
					if (queue[i] == null) 
						continue;
					
					queue[i] = action;
					current = i;
				}
				return true;
			}
			
			throw new IllegalStateException("ActionQueue is full.");
			
		} else {
			Action[] copy = Arrays.copyOf(queue, queue.length);
			queue = new Action[copy.length + 1];
			
			for (int i = 0; i < copy.length; i++) {
				queue[i] = copy[i];
			}
			
			queue[queue.length - 1] = action;
			current++;
			
			return true;
		}
	}
	
	public boolean offer(Action action) {
		if (initialSize != 0) {
			
			if (current < initialSize) {
				for (int i = 0; i < queue.length; i++) {
					if (queue[i] == null) 
						continue;
					
					queue[i] = action;
					current = i;
				}
				return true;
			}
			
			return false;
			
		} else {
			Action[] copy = Arrays.copyOf(queue, queue.length);
			queue = new Action[copy.length + 1];
			
			for (int i = 0; i < copy.length; i++) {
				queue[i] = copy[i];
			}
			
			queue[queue.length - 1] = action;
			current++;
			
			return true;
		}
	}
	
	public Action dequeue() {
		if (current >= 0 && queue.length > 0) {
			Action action = queue[current];
			current = (current > 0) ? current - 1: 0;
			
			if (initialSize != 0) {
				queue[current + 1] = null;
			} else {
				queue = Arrays.copyOf(queue,  queue.length - 1);
			}
			
			return action;
		} 
		
		throw new NoSuchElementException("Action Queue is empty.");
	}
	
	public Action poll() {
		if (current >= 0 && queue.length > 0) {
			Action action = queue[current];
			current = (current > 0) ? current - 1: 0;
			
			if (initialSize != 0) {
				queue[current + 1] = null;
			} else {
				queue = Arrays.copyOf(queue,  queue.length - 1);
			}
			
			return action;
		} 
		
		return null;
	}
	
	public Action element() {
		if (queue.length > 0) {
			return queue[current];
		}
		
		throw new NoSuchElementException("Action Queue is empty.");
	}
	
	public Action peek() {
		if (queue.length > 0) {
			return queue[current];
		}
		return null;
	}
}
