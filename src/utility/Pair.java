package utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Pair {
	public static Path directPath(Pair pos1, Pair pos2) {
		Path output = new Path();
		double length = pos1.distanceX(pos2);
		double height = pos1.distanceY(pos2);
		int signL = (length > 0) ? 1 : -1;
		int signH = (height > 0) ? 1 : -1;
		Pair loc = new Pair(pos1);
		if (!(Math.abs(height) < 0.001) && Math.abs(length / height) < 1) {
			double ratio = Math.abs(length / height);
			double progress = 0.5;
			for (int i = 0; i < Math.abs(height); i++) {
				progress += ratio;
				loc.pair[1] += (1 * signH);
				if (Math.abs(progress - 1) < 0.001 || progress > 1) {
					loc.pair[0] += (1 * signL);
					progress -= 1;
				}
				output.add(new Pair(loc));
			}
		} else {
			double ratio = height / length;
			double progress = 0.5;
			for (int i = 0; i < Math.abs(length); i++) {
				progress += ratio;
				loc.pair[0] += (1 * signL);
				if (Math.abs(progress - 1) < 0.001 || progress > 1) {
					loc.pair[1] += (1 * signH);
					progress -= 1;
				}
				output.add(new Pair(loc));
			}
		}
		return output;
	}
	
	protected int[] pair;
	final boolean hasAuxiliary;
	
	public static class Path implements List<Pair> {
		public ArrayList<Pair> path = new ArrayList<Pair>();
		
		public Path () {
			
		}
		
		public Path (ArrayList<Pair> path) {
			this.path = path;
		}
		
		public Pair pop () {
			return path.remove(0);
		}
		
		public ArrayList<Pair> getPath () {
			return path;
		}

		@Override
		public int size() {
			return path.size();
		}

		@Override
		public boolean isEmpty() {
			return path.isEmpty();
		}

		@Override
		public boolean contains(Object o) {
			return path.contains(o);
		}

		@Override
		public Iterator iterator() {
			return path.iterator();
		}

		@Override
		public Object[] toArray() {
			return path.toArray();
		}

		@Override
		public Object[] toArray(Object[] a) {
			return path.toArray(a);
		}

		public boolean add(Pair e) {
			return path.add(e);
		}

		@Override
		public boolean remove(Object o) {
			return path.remove((Pair)o);
		}

		@Override
		public boolean containsAll(Collection c) {
			return path.containsAll(c);
		}

		@Override
		public boolean addAll(Collection c) {
			return path.addAll(c);
		}

		@Override
		public boolean addAll(int index, Collection c) {
			return path.addAll(c);
		}

		@Override
		public boolean removeAll(Collection c) {
			return path.removeAll(c);
		}

		@Override
		public boolean retainAll(Collection c) {
			return path.retainAll(c);
		}

		@Override
		public void clear() {
			path.clear();
		}

		@Override
		public Pair get(int index) {
			return path.get(index);
		}

		@Override
		public Pair set(int index, Pair element) {
			return path.set(index, element);
		}

		@Override
		public void add(int index, Pair element) {
			path.add(index, element);
		}

		@Override
		public Pair remove(int index) {
			return path.remove(index);
		}

		@Override
		public int indexOf(Object o) {
			return path.indexOf(o);
		}

		@Override
		public int lastIndexOf(Object o) {
			return path.lastIndexOf(o);
		}

		@Override
		public ListIterator listIterator() {
			return path.listIterator();
		}

		@Override
		public ListIterator listIterator(int index) {
			return path.listIterator(index);
		}

		@Override
		public List subList(int fromIndex, int toIndex) {
			return path.subList(fromIndex, toIndex);
		}
	}
	
	public Pair () {
		this.hasAuxiliary = false;
	}
	
	public Pair (int x, int y) {
		pair = new int[2];
		pair[0] = x;
		pair[1] = y;
		hasAuxiliary = false;
	}
	
	public Pair (int x, int y, int auxiliary) {
		pair = new int[3];
		pair[0] = x;
		pair[1] = y;
		pair[2] = auxiliary;
		hasAuxiliary = true;
	}
	
	public Pair (Pair pair, int aux) {
		this.pair = new int[3];
		this.pair[0] = pair.x();
		this.pair[1] = pair.y();
		this.pair[2] = aux;
		hasAuxiliary = true;
	}
	
	public Pair (Pair pair) {
		int[] newPair;
		if (pair.hasAuxiliary) {
			newPair = new int[3];
			newPair[3] = pair.getAux();
		} else {
			newPair = new int[2];
		}
		newPair[0] = pair.pair[0];
		newPair[1] = pair.pair[1];
		this.pair = newPair;
		this.hasAuxiliary = pair.hasAuxiliary;
	}
	
	public String toString () {
		return "x:" + x() + " y:" + y();
	}
	
	public int x() {
		return pair[0];
	}
	
	public int y() {
		return pair[1];
	}
	
	public void setX (int x) {
		pair[0] = x;
	}
	
	public void setY (int y) {
		pair[1] = y;
	}
	
	public int getAux() {
		return pair[2];
	}
	
	public int[] toArray() {
		return toArray(false);
	}
	
	public int[] toArray(boolean auxiliary) {
		if (auxiliary) {
			return pair;
		} else {
			return new int[] {pair[0], pair[1]};
		}
	}
	
	public boolean equals(Pair p) {
		return this.x() == p.x() && this.y() == p.y();
	}
	
	public Object getFrom(Object[][] o) {
		return o[x()][y()];
	}
	
	public Object getFrom(Object[][] o, Pair offset) {
		return o[x() + offset.x()][y() + offset.y()];
	}
	
	public int getFrom(int[][] i) {
		return i[x()][y()];
	}
	
	public int getFrom(int[][] i, Pair offset) {
		return i[x() + offset.x()][y() + offset.y()];
	}
	
	public boolean getFrom(boolean[][] i) {
		return i[x()][y()];
	}
	
	public boolean getFrom(boolean[][] i, Pair offset) {
		return i[x() + offset.x()][y() + offset.y()];
	}
	
	public Pair combine(Pair p) {
		return new Pair (x() + p.x(), y() + p.y());
	}
	
	public int distanceX(Pair pair2) {
		return pair2.x() - x();
	}
	
	public int distanceY(Pair pair2) {
		return pair2.y() - y();
	}
}
