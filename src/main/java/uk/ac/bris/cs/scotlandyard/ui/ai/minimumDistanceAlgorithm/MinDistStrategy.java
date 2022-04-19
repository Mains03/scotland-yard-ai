package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm;

/**
 *
 * @param <T> data required to calculate minimum distance
 */
public interface MinDistStrategy<T> {
    int getMinimumDistance(T data);
}
