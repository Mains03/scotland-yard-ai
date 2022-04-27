package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard;

import uk.ac.bris.cs.scotlandyard.model.Board;

public interface AiBoard extends Board.GameState {
    interface Visitor<T> {
        T visit(StandardAiBoard board);

        T visit(LocationAiBoard board);

        T visit(PotentialDetectiveLocationsAiBoard board);
    }

    <T> T accept(Visitor<T> visitor);
}
