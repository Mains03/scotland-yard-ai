package uk.ac.bris.cs.scotlandyard.ui.ai;

import ch.qos.logback.core.util.InvocationGate;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import com.google.common.graph.ValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.Optional;

public class TestsAI {
    TestsAI(Board board, Player mrX, ImmutableList<Player> detectives) {
        Integer source = mrX.location();
        Dijkstra dijkstra = new Dijkstra(board.getSetup().graph);

        Integer destination = board.getSetup().graph.adjacentNodes(source).stream().max((node1, node2) -> {

                                Optional<Player> min1 = detectives.stream().min((detective1, detective2) -> {
                                    Optional<Integer> dist1 = dijkstra.minimumRouteLength(detective1, node1);
                                    Optional<Integer> dist2 = dijkstra.minimumRouteLength(detective2, node1);

                                    if (dist1.isEmpty() && dist2.isEmpty()) return 0;
                                    else if (dist1.isEmpty()) return -1;
                                    else if (dist2.isEmpty()) return 1;
                                    else return dist1.get() - dist2.get();

                                });
                                Optional<Player> min2 = detectives.stream().min((detective1, detective2) -> {
                Optional<Integer> dist1 = dijkstra.minimumRouteLength(detective1, node2);
                Optional<Integer> dist2 = dijkstra.minimumRouteLength(detective2, node2);

                if (dist1.isEmpty() && dist2.isEmpty()) return 0;
                else if (dist1.isEmpty()) return -1;
                else if (dist2.isEmpty()) return 1;
                else return dist1.get() - dist2.get();

            });


                        });

        ImmutableList<Integer> locations;
        for (Integer node : board.getSetup().graph.adjacentNodes(source)) {

            Optional<Player> detective = detectives.stream().min((detective1, detective2) -> {
                Optional<Integer> dist1 = dijkstra.minimumRouteLength(detective1, node);
                Optional<Integer> dist2 = dijkstra.minimumRouteLength(detective2, node);

                if (dist1.isEmpty() && dist2.isEmpty()) return 0;
                else if (dist1.isEmpty()) return -1;
                else if (dist2.isEmpty()) return 1;
                else return dist1.get() - dist2.get();

            });
            Optional<Integer> distance = dijkstra.minimumRouteLength(detective.get(), node);

            ImmutableList<Player> canReach =
            for (Player detective : canReach) {
                re
            }

        }




        System.out.println("Best node is: " + destination);

    }
}
