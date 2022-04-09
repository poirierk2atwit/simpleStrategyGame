package utility;

import java.util.ArrayList;

/**
 * Stores an operation and an actor to allow for an Action.
 * 
 * @author poirierk2
 *
 * @param <T>
 */
public class Action<T, R> {
	private Object actor;
	private Operation<T, R> operation;
	private ArrayList<Object> data = null;
	
	public Action(Object actor, Operation<T, R> operation) {
		this.operation = operation;
		this.actor = actor;
	}
	
	public Action(Object actor, Operation<T, R> operation, ArrayList<Object> data) {
		this.operation = operation;
		this.actor = actor;
		this.data = data;
	}
	
	public R exec(T args) {
		if (data == null) {
			return operation.exec(args, actor);
		} else {
			return operation.exec(args, actor, data);
		}
	}
	
	public R exec() {
		if (data == null) {
			return operation.exec(null, actor);
		} else {
			return operation.exec(null, actor, data);
		}
	}
	
	private static interface Operation<T, R> {
		public R exec(T args, Object actor);
		
		public R exec(T args, Object actor, ArrayList<Object> data);
	}
}
