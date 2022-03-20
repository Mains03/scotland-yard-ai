package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MinimumDistance {
    private static final int INFINITY = 10000000;

    private static MinimumDistance minimumDistance;

    public static void createInstance(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        Objects.requireNonNull(graph);
        if (minimumDistance == null)
            minimumDistance = new MinimumDistance(graph);
    }

    public static MinimumDistance getInstance() {
        Objects.requireNonNull(minimumDistance);
        return minimumDistance;
    }

    private final ImmutableList<ImmutableList<Integer>> minimumDistances;

    private MinimumDistance(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph) {
        minimumDistances = createMinimumDistances(graph);
    }

    private ImmutableList<ImmutableList<Integer>> createMinimumDistances(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph
    ) {
        Collection<Pair<Integer, Integer>> edges = createEdges(graph);
        return floydWarshall(graph, edges, graph.nodes().size());
    }

    private Collection<Pair<Integer, Integer>> createEdges(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph
    ) {
        return graph.nodes().stream()
                .map(source -> graph.adjacentNodes(source).stream()
                        .map(destination -> new Pair<>(source, destination))
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private ImmutableList<ImmutableList<Integer>> floydWarshall(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            Collection<Pair<Integer, Integer>> edges,
            int numNodes
    ) {
        List<List<Integer>> distances = createDistancesMatrix(graph, numNodes);
        for (int k=0; k<numNodes; ++k) {
            for (int i=0; i<numNodes; ++i) {
                for (int j=0; j<numNodes; ++j) {
                    int distanceThroughK = distances.get(i).get(k) + distances.get(k).get(j);
                    if (distances.get(i).get(j) > distanceThroughK)
                        distances.get(i).set(j, distanceThroughK);
                }
            }
        }
        return ImmutableList.copyOf(
                distances.stream()
                    .map(l -> ImmutableList.copyOf(l))
                    .collect(Collectors.toList())
        );
    }

    private List<List<Integer>> createDistancesMatrix(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            int numNodes
    ) {
        List<List<Integer>> distances = new ArrayList<>();
        for (int i=0; i<numNodes; ++i) {
            distances.add(new ArrayList<>());
            for (int j=0; j<numNodes; ++j) {
                distances.get(i).add(INFINITY);
                if (graph.edgeValue(i+1, j+1).isPresent())
                    distances.get(i).set(j, 1);
            }
        }
        return distances;
    }

    public int getMinimumDistance(int source, int destination) {
        return minimumDistances.get(source-1).get(destination-1);
    }
}
