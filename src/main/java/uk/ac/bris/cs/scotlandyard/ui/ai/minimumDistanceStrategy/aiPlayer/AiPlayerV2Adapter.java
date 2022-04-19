package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration.MoveGenerationBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration.MoveGenerationBoardAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration.MoveGenerationFactory;
import uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration.StandardMoveGenerationFactory;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AiPlayerV2Adapter implements AiPlayerV2 {
    // required for move generation
    private ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;
    private final Player player;

    public AiPlayerV2Adapter(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            Player player
    ) {
        this.graph = Objects.requireNonNull(graph);
        this.player = Objects.requireNonNull(player);
    }

    @Override
    public Set<Move> getAvailableMoves(List<Integer> detectiveLocations) {
        MoveGenerationFactory moveGenerationFactory = createMoveGenerationFactory();
        MoveGenerationBoard board = createMoveGenerationBoard(detectiveLocations);
        return moveGenerationFactory.generateMoves(board, player);
    }

    @Override
    public AiPlayerV2 applyMove(Move move) {
        Player player = applyMove(this.player, move);
        return new AiPlayerV2Adapter(graph, player);
    }

    private Player applyMove(Player player, Move move) {
        MoveApplyFactory moveApplyFactory = MoveApplyFactory.getInstance();
        return moveApplyFactory.applyMove(player, move);
    }

    private MoveGenerationFactory createMoveGenerationFactory() {
        return StandardMoveGenerationFactory.getInstance();
    }

    private MoveGenerationBoard createMoveGenerationBoard(List<Integer> detectiveLocations) {
        return new MoveGenerationBoardAdapter(graph, detectiveLocations);
    }

    @Override
    public Player asPlayer() {
        return null;
    }
}
