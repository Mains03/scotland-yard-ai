package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiMove.AiMove;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiMove.AiMoveAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer.AiPlayer;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer.AiPlayerAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration.MoveGenerationBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration.MoveGenerationBoardAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration.MoveGenerationFactory;
import uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration.StandardMoveGenerationFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @deprecated since {@link AiBoard} is deprecated, use {@link AiBoardV2Adapter}.
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

    @Override
    public ImmutableSet<AiMove> getAvailableMoves() {
        Set<Move> moves = new HashSet<>();
        moves.addAll(generateMrXMoves());
        moves.addAll(generateDetectiveMoves());
        Set<AiMove> aiMoves = new HashSet<>();
        for (Move move : moves)
            aiMoves.add(createAiMove(move));
        return ImmutableSet.copyOf(aiMoves);
    }

    private AiMove createAiMove(Move move) {
        return new AiMoveAdapter(move);
    }

    private Set<Move> generateMrXMoves() {
        Player mrX = this.mrX.asPlayer();
        MoveGenerationFactory factory = createMoveGenerationFactory();
        MoveGenerationBoard board = createMoveGenerationBoard();
        return factory.generateMoves(board, mrX);
    }

    private Set<Move> generateDetectiveMoves() {
        MoveGenerationFactory factory = createMoveGenerationFactory();
        MoveGenerationBoard board = createMoveGenerationBoard();
        Set<Move> moves = new HashSet<>();
        for (AiPlayer detective : detectives) {
            Player detectivePlayer = detective.asPlayer();
            Set<Move> detectiveMoves = factory.generateMoves(board, detectivePlayer);
            moves.addAll(detectiveMoves);
        }
        return moves;
    }

    private MoveGenerationFactory createMoveGenerationFactory() {
        return StandardMoveGenerationFactory.getInstance();
    }

    private MoveGenerationBoard createMoveGenerationBoard() {
        var graph = board.getSetup().graph;
        List<Integer> detectiveLocations = generateDetectiveLocations();
        return new MoveGenerationBoardAdapter(graph, detectiveLocations);
    }

    private List<Integer> generateDetectiveLocations() {
        List<Integer> detectiveLocations = new ArrayList<>();
        for (AiPlayer detective : detectives)
            detectiveLocations.add(detective.getLocation());
        return detectiveLocations;
    }

    @Override
    public AiPlayer getMrX() {
        return mrX;
    }

    @Override
    public ImmutableList<AiPlayer> getDetectives() {
        return ImmutableList.copyOf(detectives);
    }

    @Override
    public AiBoard applyMove(Move move) {
        throw new UnsupportedOperationException();
    }
}
