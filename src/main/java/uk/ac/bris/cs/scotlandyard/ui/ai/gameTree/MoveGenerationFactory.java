package uk.ac.bris.cs.scotlandyard.ui.ai.gameTree;

import javafx.util.Pair;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.moves.AllMoves;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Singleton class to generate moves at a state of the game.
 */
class MoveGenerationFactory {
    private static MoveGenerationFactory moveGeneration;

    static MoveGenerationFactory getInstance() {
        if (moveGeneration == null)
            moveGeneration = new MoveGenerationFactory();
        return moveGeneration;
    }

    private MoveGenerationFactory() {}

    /**
     * Generates the possible moves for MrX at the current state of the game.
     * @param gameState the state of the game
     * @return the possible moves
     */
    List<Move> generateMrXMoves(final GameState gameState) {
        return allMovesWithTicket(gameState.getMrX()).stream()
                .filter(move -> movePossible(gameState, move))
                .collect(Collectors.toList());
    }

    /**
     * Generates all the moves each detective can make.
     * @param gameState state of the game
     * @return a list mapping each detective to a move if the detective can move
     */
    List<Map<Player, Move>> generateDetectivesMoves(final GameState gameState) {
        Map<Player, List<Move>> detectiveMoves = gameState.getDetectives().stream()
                .map(detective -> new Pair<>(detective, allMovesWithTicket(detective)))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        List<Map<Player, Move>> combinations = generateCombinations(detectiveMoves);
        return pruneIllegalStates(combinations);
    }

    /**
     * Generates the possible combinations in which the detectives can move.
     * @param detectiveMoves the moves each detective can make
     * @return the possible combinations
     */
    private List<Map<Player, Move>> generateCombinations(final Map<Player, List<Move>> detectiveMoves) {
        List<Map<Player, Move>> combinations = new ArrayList<>();
        for (Player player : detectiveMoves.keySet()) {
            if (combinations.size() == 0) {
                for (Move move : detectiveMoves.get(player))
                    combinations.add(Map.of(player, move));
            } else {
                List<Map<Player, Move>> newCombinations = new ArrayList<>();
                for (Move move : detectiveMoves.get(player)) {
                    for (Map<Player, Move> combination : combinations) {
                        Map<Player, Move> newCombination = Map.copyOf(combination);
                        newCombination.put(player, move);
                        newCombinations.add(newCombination);
                    }
                }
                combinations = newCombinations;
            }
        }
        return combinations;
    }

    /**
     * Replaces impossible states with possible variations.
     * @param detectiveMoves moves the detectives can make
     * @return possible moves the detectives can make
     */
    private List<Map<Player, Move>> pruneIllegalStates(final List<Map<Player, Move>> detectiveMoves) {
        List<Map<Player, Move>> legalStates = new ArrayList<>();
        for (int i=0; i<detectiveMoves.size(); ++i) {
            Map<Player, Move> state = detectiveMoves.get(i);
            if (isStateLegal(state))
                legalStates.add(state);
            else
                legalStates.addAll(generateLegalStates(state));
        }
        return legalStates;
    }

    /**
     * Determine whether the moves are the detectives are valid.
     * @param state moves of the detectives
     * @return whether the detectives can make those moves
     */
    private boolean isStateLegal(final Map<Player, Move> state) {
        // check if any detectives occupy the same destination
        Set<Integer> detectiveDestinations = new HashSet<>();
        for (Player player : state.keySet()) {
            int playerDestination = getMoveDestination(state.get(player));
            if (detectiveDestinations.contains(playerDestination)) return false;
            detectiveDestinations.add(playerDestination);
        }
        // no duplicate destinations, state legal
        return true;
    }

    private int getMoveDestination(final Move move) {
        return move.accept(new Move.Visitor<Integer>() {
            @Override
            public Integer visit(Move.SingleMove move) {
                return move.destination;
            }

            @Override
            public Integer visit(Move.DoubleMove move) {
                return move.destination2;
            }
        });
    }

    /**
     * Given an illegal state, derive legal variations where one (or more) detective doesn't move
     * @param illegalState the illegal state
     * @return legal derivations
     */
    private List<Map<Player, Move>> generateLegalStates(final Map<Player, Move> illegalState) {
        Map<Integer, List<Player>> sameDestinations = determinePlayersWithSameDestination(illegalState);

        return null;
    }

    private Map<Integer, List<Player>> determinePlayersWithSameDestination(final Map<Player, Move> states) {
        Map<Integer, List<Player>> destinations = new HashMap<>();
        for (Player player : states.keySet()) {
            int destination = getMoveDestination(states.get(player));
            if (destinations.containsKey(destination))
                destinations.get(destination).add(player);
            else
                destinations.put(destination, new ArrayList<>());
        }
        Map<Integer, List<Player>> sameDestinations = new HashMap<>();
        for (int destination : destinations.keySet()) {
            if (destinations.get(destination).size() > 1)
                sameDestinations.put(destination, destinations.get(destination));
        }
        return sameDestinations;
    }

    /**
     * All the moves a player can make with the current tickets they have.
     * @param player the player
     * @return the moves they can make with the tickets they have.
     */
    private List<Move> allMovesWithTicket(final Player player) {
        return AllMoves.getInstance().getAvailableMoves(player.piece(), player.location()).stream()
                .filter(move -> playerHasRequiredTickets(player, move))
                .collect(Collectors.toList());
    }

    /**
     * Check whether the player has the required ticket(s) to make a move.
     * @param player the player
     * @param move the move
     * @return whether the player has the required ticket(s)
     */
    private boolean playerHasRequiredTickets(final Player player, final Move move) {
        return move.accept(new Move.Visitor<Boolean>() {
            @Override
            public Boolean visit(Move.SingleMove move) {
                return player.has(move.ticket);
            }

            @Override
            public Boolean visit(Move.DoubleMove move) {
                if (move.ticket1 == move.ticket2)
                    return player.hasAtLeast(move.ticket1, 2);
                else
                    return (player.has(move.ticket1)) && (player.has(move.ticket2));
            }
        });
    }

    private boolean movePossible(final GameState gameState, final Move move) {
        return false;
    }
}
