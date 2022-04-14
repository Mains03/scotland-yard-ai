package uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Piece;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class MoveGenerationBoardAdapter implements MoveGenerationBoard {
    private final List<Integer> detectiveLocations;

    public MoveGenerationBoardAdapter(Board board) {
        detectiveLocations = generateDetectiveLocations(Objects.requireNonNull(board));
    }

    private List<Integer> generateDetectiveLocations(Board board) {
        List<Piece> detectives = board.getPlayers().stream()
                .filter(Piece::isDetective)
                .collect(Collectors.toList());
        return detectives.stream()
                .map(detective -> board.getDetectiveLocation((Piece.Detective) detective))
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<Integer> getDetectiveLocations() {
        return detectiveLocations;
    }
}
