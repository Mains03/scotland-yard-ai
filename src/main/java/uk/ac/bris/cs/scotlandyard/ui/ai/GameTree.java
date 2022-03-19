package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import uk.ac.bris.cs.scotlandyard.model.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class GameTree {
    private static final int POSITIVE_INFINITY = 10000000;

    private final Move move;
    private final ImmutableList<GameTree> children;
    private final boolean maximise;

    private final GameData gameData;

    public GameTree(
            final GameSetup gameSetup,
            final Player mrX,
            final ImmutableList<Player> detectives,
            final ImmutableList<Piece> remaining,
            final int depth
    ) {
        this(
                new GameData(gameSetup, mrX, detectives, remaining),
                null,
                true,
                depth
        );
    }

    private GameTree(final GameData gameData, final Move move, final boolean maximise, final int depth) {
        this.move = move;
        this.gameData = gameData;
        this.maximise = maximise;
        if (depth > 0) {
            children = createChildren(gameData, depth);
        } else
            children = ImmutableList.of();
    }

    private ImmutableList<GameTree> createChildren(final GameData gameData, final int depth) {
        List<GameTree> children = new ArrayList<>();
        for (Move move : gameData.getAvailableMoves()) {
            children.add(
                    new GameTree(gameData.advance(move), move, !maximise, depth-1)
            );
        }
        return ImmutableList.copyOf(children);
    }

    public Move determineBestMove() {
        if (maximise) {
            return children.stream()
                    .max(Comparator.comparingInt(GameTree::evaluate))
                    .get()
                    .move;
        } else {
            return children.stream()
                    .min(Comparator.comparingInt(GameTree::evaluate))
                    .get()
                    .move;
        }
    }

    private int evaluate() {
        if (children.size() > 0) {
            if (maximise) {
                return children.stream()
                        .map(GameTree::evaluate)
                        .max(Integer::compareTo)
                        .get();
            } else {
                return children.stream()
                        .map(GameTree::evaluate)
                        .min(Integer::compareTo)
                        .get();
            }
        } else
            return staticEvaluation();
    }

    private int staticEvaluation() {
        Dijkstra dijkstra = Dijkstra.getInstance();
        Optional<Integer> minDist = gameData.getDetectives().stream()
                .map(detective -> dijkstra.minimumRouteLength(detective, gameData.getMrXLocation()))
                .flatMap(Optional::stream)
                .min(Integer::compareTo);
        if (minDist.isPresent())
            return minDist.get();
        else
            return POSITIVE_INFINITY;
    }
}

final class GameData {
    private final GameSetup gameSetup;
    private final Player mrX;
    private final ImmutableList<Player> detectives;
    private final ImmutableList<Piece> remaining;

    GameData(
            final GameSetup gameSetup,
            final Player mrX,
            final ImmutableList<Player> detectives,
            final ImmutableList<Piece> remaining
    ) {
        this.gameSetup = gameSetup;
        this.mrX = mrX;
        this.detectives = detectives;
        this.remaining = remaining;
    }

    private GameData(final GameData oldGameData, final Move move) {
        gameSetup = oldGameData.gameSetup;
        mrX = newMrX(oldGameData, move);
        detectives = newDetectives(oldGameData, move);
        remaining = newRemaining(oldGameData, move);
    }

    private Player newMrX(final GameData oldGameData, final Move move) {
        if (move.commencedBy().isDetective())
            return oldGameData.mrX.give(move.tickets());
        else {
            Player newMrX = oldGameData.mrX.use(move.tickets());
            return move.accept(new Move.Visitor<Player>() {
                @Override
                public Player visit(Move.SingleMove move) {
                    return newMrX.at(move.destination);
                }

                @Override
                public Player visit(Move.DoubleMove move) {
                    return newMrX.at(move.destination2);
                }
            });
        }
    }

    private ImmutableList<Player> newDetectives(final GameData oldGameData, final Move move) {
        return ImmutableList.copyOf(
                oldGameData.detectives.stream()
                        .map(detective -> newDetective(move, detective))
                        .collect(Collectors.toList())
        );
    }

    private Player newDetective(final Move move, final Player player) {
        if (move.commencedBy().webColour().equals(player.piece().webColour())) {
            return move.accept(new Move.Visitor<Player>() {
                @Override
                public Player visit(Move.SingleMove move) {
                    return player.use(move.ticket).at(move.destination);
                }

                @Override
                public Player visit(Move.DoubleMove move) {
                    return player.use(move.tickets()).at(move.destination2);
                }
            });
        } else
            return player;
    }

    private ImmutableList<Piece> newRemaining(final GameData oldGameData, final Move move) {
        Collection<Piece> updatedRemaining = oldGameData.remaining.stream()
                .filter(piece -> move.commencedBy().webColour().equals(piece.webColour()))
                .collect(Collectors.toList());
        if (updatedRemaining.size() == 0) {
            if (move.commencedBy().isMrX()) {
                return ImmutableList.copyOf(
                        oldGameData.detectives.stream()
                                .map(Player::piece)
                                .collect(Collectors.toList())
                );
            } else
                return ImmutableList.of(Piece.MrX.MRX);
        } else
            return ImmutableList.copyOf(updatedRemaining);
    }

    int getMrXLocation() { return mrX.location(); }

    Collection<Player> getDetectives() {
        return detectives.stream()
                .map(detective -> new Player(
                        detective.piece(),
                        ImmutableMap.copyOf(detective.tickets()),
                        detective.location()
                )).collect(Collectors.toList());
    }

    Collection<Move> getAvailableMoves() {
        return remaining.stream()
                .map(piece ->  getPlayerByPiece(piece))
                .map(player -> getAvailableMoves(player))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private Player getPlayerByPiece(final Piece piece) {
        if (piece.isMrX()) return mrX;
        else {
            return detectives.stream()
                    .filter(detective -> detective.piece().webColour().equals(piece.webColour()))
                    .findFirst()
                    .get();
        }
    }

    private Collection<Move> getAvailableMoves(final Player player) {
        return allMoves(player).stream()
                .filter(move -> movePossible(player, move))
                .collect(Collectors.toList());
    }

    private Collection<Move> allMoves(final Player player) {
        Collection<Move.SingleMove> singleMoves = AllSingleMoves.getInstance().getSingleMoves(player.location()).stream()
                .map(move -> new Move.SingleMove(
                        player.piece(), move.source(), move.ticket, move.destination
                )).collect(Collectors.toList());
        Collection<Move> doubleMoves = new ArrayList<>();
        if (player.isMrX()) {
            doubleMoves = singleMoves.stream()
                    .map(this::createDoubleMoves)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
        return Stream.concat(
                singleMoves.stream(),
                doubleMoves.stream()
        ).collect(Collectors.toList());
    }

    private Collection<Move> createDoubleMoves(Move.SingleMove move) {
        return AllSingleMoves.getInstance().getSingleMoves(move.destination).stream()
                .map(move2 -> new Move.DoubleMove(
                        Piece.MrX.MRX, move.source(), move.ticket, move.destination, move2.ticket, move2.destination
                )).collect(Collectors.toList());
    }

    private boolean movePossible(final Player player, final Move move) {
        return move.accept(new Move.Visitor<Boolean>() {
            @Override
            public Boolean visit(Move.SingleMove move) {
                return player.has(move.ticket);
            }

            @Override
            public Boolean visit(Move.DoubleMove move) {
                if (player.isDetective()) return false;
                if (!player.has(ScotlandYard.Ticket.DOUBLE)) return false;
                if (move.ticket1 == move.ticket2)
                    return player.hasAtLeast(move.ticket1, 2);
                else {
                    return player.has(move.ticket1)
                            && player.has(move.ticket2);
                }
            }
        });
    }

    GameData advance(final Move move) {
        return new GameData(this, move);
    }
}
