package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Board;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class GameTree {
    private final Board.GameState gameState;

    public GameTree(final Board.GameState gameState) {
        Objects.requireNonNull(gameState);
        this.gameState = gameState;
    }

    public Stream<GameTree> generate() {
        return generate(this);
    }

    private Stream<GameTree> generate(GameTree node) {
        Collection<GameTree> children = createChildren();
        return Stream.concat(
                Stream.of(node),
                children.stream().flatMap(this::generate)
        );
    }

    private Collection<GameTree> createChildren() {
        return gameState.getAvailableMoves().stream()
                .map(move -> new GameTree(gameState.advance(move)))
                .collect(Collectors.toList());
    }

    public Board.GameState getGameState() { return gameState; }
}
