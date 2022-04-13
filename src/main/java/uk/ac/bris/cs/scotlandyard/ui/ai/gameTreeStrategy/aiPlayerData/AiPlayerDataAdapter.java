package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.aiPlayerData;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameStateAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.PlayerFactoryAdapter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AiPlayerDataAdapter extends AiGameStateAdapter implements AiPlayerData {
    private final AiPlayerWithMoveFlag mrX;
    private final List<AiPlayerWithMoveFlag> detectives;

    public AiPlayerDataAdapter(Board board) {
        super(board);
        mrX = new AiPlayerWithMoveFlagAdapter(board, Piece.MrX.MRX);
        detectives = new PlayerFactoryAdapter(board).createDetectives().stream()
                .map(detective -> new AiPlayerWithMoveFlagAdapter(board, detective))
                .collect(Collectors.toList());
    }

    @Override
    public Set<Move> getAvailableMoves() {
        return null;
    }

    @Override
    public AiPlayerData advance(Move move) {
        return null;
    }
}
