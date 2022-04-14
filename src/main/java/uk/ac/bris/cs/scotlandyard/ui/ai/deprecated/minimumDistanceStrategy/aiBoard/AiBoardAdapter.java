package uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.aiBoard;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.aiMove.AiMove;
import uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.aiMove.AiMoveAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.aiPlayer.AiPlayer;
import uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.aiPlayer.AiPlayerAdapter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @deprecated Deprecated since AiBoard is deprecated.
 */
@Deprecated
public class AiBoardAdapter implements AiBoard {
    protected final Board board;
    protected final AiPlayer mrX;
    protected final List<AiPlayer> detectives;

    public AiBoardAdapter(Board board) {
        this.board = Objects.requireNonNull(board);
        PiecePlayerFactory piecePlayerFactory = new PiecePlayerFactoryAdapter(board);
        ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph = board.getSetup().graph;
        Optional<Player> mMrX = piecePlayerFactory.createPlayer(Piece.MrX.MRX);
        if (mMrX.isEmpty())
            throw new RuntimeException("Failed to create MrX");
        mrX = new AiPlayerAdapter(
                graph,
                mMrX.get()
        );
        detectives = createDetectives(graph, piecePlayerFactory);
    }

    private List<AiPlayer> createDetectives(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            PiecePlayerFactory piecePlayerFactory
    ) {
        return board.getPlayers().stream()
                .filter(Piece::isDetective)
                .map(piece -> piecePlayerFactory.createPlayer(piece))
                .flatMap(Optional::stream)
                .map(detective -> new AiPlayerAdapter(graph, detective))
                .collect(Collectors.toList());
    }

    @Override
    public ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> getGraph() {
        return board.getSetup().graph;
    }

    /**
     * Only returns the moves immediately available at the start of the round.
     * @return the available moves
     */
    @Override
    public ImmutableSet<AiMove> getAvailableMoves() {
        return ImmutableSet.copyOf(
                board.getAvailableMoves().stream()
                        .map(AiMoveAdapter::new)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public AiPlayer getMrX() {
        return mrX;
    }

    @Override
    public ImmutableList<AiPlayer> getDetectives() {
        return ImmutableList.copyOf(detectives);
    }
}
