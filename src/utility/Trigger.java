package utility;

import java.util.ArrayList;

/**
 * 
 * 
 * @author poirierk2
 *
 * @param <T>
 */
public class Trigger<T, R> {
	private ArrayList<Action<T, R>> actions = new ArrayList<Action<T, R>>(); 
	
	public Trigger () {
	}
	
	public ArrayList<R> trigger(T args) {
		ArrayList<R> output = new ArrayList<R>(actions.size());
		for (int i = actions.size() -1; i >= 0; i--) {
			output.set(i, actions.get(i).exec(args));
			if (!(output.get(i) == null)) {
				actions.remove(i);
			}
		}
		return output;
	}
	
	public void addAction(Action<T, R> action) {
		actions.add(action);
	}
	
	public boolean remove(Action<T, R> action) {
		for (int i = actions.size() -1; i >= 0; i--) {
			if (actions.get(i) == action) {
				actions.remove(i);
				return false;
			}
		}
		return false;
	}
	
	public void clear() {
		actions = new ArrayList<Action<T, R>>();
	}
}
