package uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiPlayer;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiPlayer.moveGeneration.MoveGenerationBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiPlayer.moveGeneration.MoveGenerationBoardAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiPlayer.moveGeneration.MoveGenerationFactory;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiPlayer.moveGeneration.StandardMoveGenerationFactory;
import uk.ac.bris.cs.scotlandyard.ui.ai.playerFactory.PlayerFactory;
import uk.ac.bris.cs.scotlandyard.ui.ai.playerFactory.PlayerFactoryAdapter;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AiPlayerAdapter implements AiPlayer {
    // required for move generation
    private ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;
    private final Player player;

    public AiPlayerAdapter(Board board, Piece piece) {
        graph = getGraph(board);
        PlayerFactory playerFactory = createPlayerFactory();
        player = playerFactory.createPlayer(board, piece);
    }

    private ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> getGraph(Board board) {
        return board.getSetup().graph;
    }

    private PlayerFactory createPlayerFactory() {
        return new PlayerFactoryAdapter();
    }

    private AiPlayerAdapter(
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
    public AiPlayer applyMove(Move move) {
        Player player = applyMove(this.player, move);
        return new AiPlayerAdapter(graph, player);
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
        return player;
    }
}
