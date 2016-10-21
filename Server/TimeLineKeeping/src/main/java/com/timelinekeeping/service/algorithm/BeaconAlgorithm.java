package com.timelinekeeping.service.algorithm;

import com.timelinekeeping.common.Pair;
import com.timelinekeeping.entity.ConnectionPointEntity;
import com.timelinekeeping.model.CoordinateModel;
import com.timelinekeeping.repository.ConnectionRepo;
import com.timelinekeeping.repository.CoordinateRepo;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created by HienTQSE60896 on 10/21/2016.
 */
@Service
@Component
public class BeaconAlgorithm {

    @Autowired
    private CoordinateRepo coordinateRepo;

    @Autowired
    private ConnectionRepo connectionRepo;

    Map<Long, Set<CoordinateModel>> graph;
    Map<Long, Double> distance;
    Map<Long, Pair<Long, Boolean>> path;//key: path, value: traveled

    Queue<Long> queue;


    private Logger logger = LogManager.getLogger(BeaconAlgorithm.class);

    public BeaconAlgorithm() {
        distance = new HashMap<>();
        queue = new ArrayDeque<>();
        path = new HashMap<>();
    }

    private void prepareGraph() {
        graph = new HashMap<>();
        distance = new HashMap<>();

        //convert to graph
        List<ConnectionPointEntity> listConnection = connectionRepo.findAll();
        for (ConnectionPointEntity connection : listConnection) {


            CoordinateModel vertex1 = new CoordinateModel(connection.getVertex1());
            CoordinateModel vertex2 = new CoordinateModel(connection.getVertex2());

            //vertex 1;
            Set<CoordinateModel> listVertex1 = graph.get(vertex1.getId());
            if (listVertex1 == null) {
                listVertex1 = new HashSet<>();
                graph.put(vertex1.getId(), listVertex1);
            }
            listVertex1.add(vertex2);

            //vertex 2
            Set<CoordinateModel> listVertex2 = graph.get(vertex2.getId());
            if (listVertex2 == null) {
                listVertex2 = new HashSet<>();
                graph.put(vertex2.getId(), listVertex2);
            }
            listVertex2.add(vertex1);
        }
    }

    public void findShortPath(Long beginVertex, Long endVertex) {

        prepareGraph();
        queue.add(beginVertex);
        distance.put(beginVertex, 0d);

        //add vertex to queue
        while (queue.size() > 0) {

            //get vertex in queue
            Long vertexId = queue.remove();


            //find min path in from vertex
            travel(vertexId);


            //set traveled in point
            if (path.get(vertexId) == null){
                path.put(vertexId, new Pair<>(-1l, true));
            }else {
                path.get(vertexId).setValue(true);
            }
            //break value
            // find endPath to break;
            if (vertexId == endVertex) {
                break;
            }

        }

        logger.info("Min Path value:" + distance.get(endVertex));
        String pathString = "Path: " + endVertex;
        boolean isFound = false;
        Long currentVertex = endVertex;
        while (currentVertex != null && currentVertex != beginVertex) {
            Pair<Long, Boolean> pairValue = path.get(currentVertex);
            pathString += " <-- " + pairValue.getKey();
            currentVertex = pairValue.getKey();
        }
        logger.info(pathString);


    }


    //find mind path from vertex to other vertex ha agde with vertex
    private void travel(Long vertexId ){
        //get oneVertex
        CoordinateModel pointFrom = new CoordinateModel(coordinateRepo.findOne(vertexId));
        //get list vertex connect with this vertex
        Set<CoordinateModel> listVertex = graph.get(vertexId);

        //get value
        if (ValidateUtil.isNotEmpty(listVertex)) {
            for (CoordinateModel pointTo : listVertex) {

                //check point is traveled? if never travel point, allow travel.
                Pair<Long, Boolean> pathTravel = path.get(pointTo.getId());

                if ( pathTravel== null || pathTravel.getValue() != true) {
                    //get DistanceMin
                    Double distanceMin = distance.get(pointTo.getId());
                    if (distanceMin == null) distanceMin = Double.MAX_VALUE;

                    //distance
                    Double distanceValue = distance(pointFrom, pointTo);

                    //get distanceMin between two distance
                    if (distanceMin > distanceValue) {
                        //add point to in queue
                        queue.add(pointTo.getId());
                        distance.put(pointTo.getId(), distanceValue);
                        path.put(pointTo.getId(), new Pair<>(pointFrom.getId(), false));
                    }
                }
            }
        }
    }

    public Double distance(CoordinateModel point1, CoordinateModel point2) {
        return Math.sqrt(Math.pow(point1.getLatitude() - point2.getLatitude(), 2) + Math.pow(point1.getLongitude() - point2.getLongitude(), 2));
    }

    public static void main(String[] args) {

        System.out.println(new BeaconAlgorithm().distance(new CoordinateModel(2d, 2d), new CoordinateModel(3d, 3d)));
    }

}
