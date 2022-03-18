package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class GameTree {
    private final Board.GameState gameState;
    private final Optional<Move> moveMade;

    private List<GameTree> children;
    private Optional<Move> evaluation;

    public GameTree(final Board.GameState gameState, final Optional<Move> moveMade) {
        Objects.requireNonNull(gameState);
        this.gameState = gameState;
        this.moveMade = moveMade;
        children = new ArrayList<>();
    }

    public Stream<GameTree> generateTree() {
        return generateTree(this);
    }

    private Stream<GameTree> generateTree(GameTree node) {
        createChildren();
        return children.stream()
                .flatMap(this::generateTree);
    }

    private void createChildren() {
        children = gameState.getAvailableMoves().stream()
                .map(move -> new GameTree(gameState.advance(move)))
                .collect(Collectors.toList());
    }

    public Move evaluate(ToIntFunction<Board.GameState> staticEvaluationStrategy) {
        if (evaluation.isPresent()) return evaluation.get();
        else {
            evaluation = children.stream()
                    .map(child -> evaluate(staticEvaluationStrategy))
                    .max(Integer::compareTo);
            if (evaluation.isPresent()) {
                // contains best value for children
                return evaluation.get();
            } else {
                // no children
                return staticEvaluationStrategy.applyAsInt(gameState);
            }
        }
    }
}
