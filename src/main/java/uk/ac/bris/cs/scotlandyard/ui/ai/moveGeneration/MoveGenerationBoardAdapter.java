package uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class MoveGenerationBoardAdapter implements MoveGenerationBoard {
    private final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;
    private final List<Integer> detectiveLocations;

    public MoveGenerationBoardAdapter(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            List<Integer> detectiveLocations
    ) {
        this.graph = Objects.requireNonNull(graph);
        this.detectiveLocations = Objects.requireNonNull(detectiveLocations);
    }

    @Override
    public ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> getGraph() {
        return graph;
    }

    @Override
    public List<Integer> getDetectiveLocations() {
        return detectiveLocations;
    }
}
