package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard;

import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.playerFactory.PlayerFactory;
import uk.ac.bris.cs.scotlandyard.ui.ai.playerFactory.PlayerFactoryAdapterV2;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class PiecePlayerFactoryAdapter implements PiecePlayerFactory {
    private final Board board;

    public PiecePlayerFactoryAdapter(Board board) {
        this.board = Objects.requireNonNull(board);
    }

    @Override
    public Optional<Player> createPlayer(Piece piece) {
        try {
            PlayerFactory playerFactory = new PlayerFactoryAdapterV2();
            Player player = playerFactory.createFromPiece(board, piece);
            return Optional.of(player);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }
}
