package algorithms;

public interface Dictionary<T extends Comparable<T>> {
	T search(T element);
	void insert(T element);
	void remove(T element);
	T min();
	T max();
}