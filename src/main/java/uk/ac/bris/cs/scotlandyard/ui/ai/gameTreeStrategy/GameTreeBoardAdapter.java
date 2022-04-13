package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameState;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GameTreeBoardAdapter implements GameTreeBoard {
    private final GameTreePlayer mrX;
    private final List<GameTreePlayer> detectives;
    private boolean mrXTurn;

    public GameTreeBoardAdapter(Board board) {
        mrX = new GameTreePlayerAdapter(board, Piece.MrX.MRX);
        detectives = createDetectives(Objects.requireNonNull(board));
        mrXTurn = true;
    }

    private GameTreeBoardAdapter(
            GameTreePlayer mrX,
            List<GameTreePlayer> detectives,
            boolean mrXTurn
    ) {
        this.mrX = mrX;
        this.detectives = detectives;
        this.mrXTurn = mrXTurn;
    }

    private List<GameTreePlayer> createDetectives(Board board) {
        return board.getPlayers().stream()
                .filter(Piece::isDetective)
                .map(piece -> new GameTreePlayerAdapter(board, piece))
                .collect(Collectors.toList());
    }

    @Override
    public ImmutableSet<Move> getAvailableMoves() {
        Set<Move> moves;
        if (mrXTurn)
            moves = mrX.getAvailableMoves();
        else {
            moves = new HashSet<>();
            for (GameTreePlayer detective : detectives) {
                if (!detective.hasMoved())
                    moves.addAll(detective.getAvailableMoves());
            }
        }
        return ImmutableSet.copyOf(moves);
    }

    @Override
    public GameTreeBoard advance(Move move) {
        GameTreePlayer newMrX;
        List<GameTreePlayer> newDetectives;
        if (move.commencedBy().isMrX()) {

        }
        return new GameTreeBoardAdapter(
                newMrX,
                newDetectives,

        );
    }

    @Override
    public AiGameState asAiGameState() {
        return null;
    }
}
