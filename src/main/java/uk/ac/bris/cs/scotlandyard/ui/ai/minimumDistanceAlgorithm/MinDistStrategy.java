package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm;

/**
 * Generic since different strategies require different data.
 */
public interface MinDistStrategy<T> {
    int getMinimumDistance(T data);
}
