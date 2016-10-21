package com.timelinekeeping.service.algorithm;

import com.timelinekeeping.entity.ConnectionPointEntity;
import com.timelinekeeping.model.CoordinateModel;
import com.timelinekeeping.repository.ConnectionRepo;
import com.timelinekeeping.repository.CoordinateRepo;
import com.timelinekeeping.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by HienTQSE60896 on 10/21/2016.
 */
public class BeaconAlgorithm {

    @Autowired
    private CoordinateRepo coordinateRepo;

    @Autowired
    private ConnectionRepo connectionRepo;

    Map<Long, List<CoordinateModel>> graph;
    Map<Long, Double> distance;

    Queue<Long> queue;

    public BeaconAlgorithm() {
        prepareGraph();
        distance = new HashMap<>();
        queue = new ArrayDeque<>();
    }

    private void prepareGraph() {
        graph = new HashMap<>();
        distance = new HashMap<>();

        //convert to graph
        List<ConnectionPointEntity> listConnection = connectionRepo.findAll();
        for (ConnectionPointEntity connection : listConnection) {

            //vertex 1;
            CoordinateModel vertex1 = new CoordinateModel(connection.getVertex1());
            CoordinateModel vertex2 = new CoordinateModel(connection.getVertex2());
            List<CoordinateModel> listVertex1 = graph.get(vertex1.getId());
            if (listVertex1 == null) {
                listVertex1 = new ArrayList<>();
                graph.put(vertex1.getId(), listVertex1);
            }
            listVertex1.add(vertex2);

            //vertex 2
            List<CoordinateModel> listVertex2 = graph.get(vertex1.getId());
            if (listVertex2 == null) {
                listVertex2 = new ArrayList<>();
                graph.put(vertex1.getId(), listVertex2);
            }
            listVertex2.add(vertex2);
        }
    }

    public void findSortPath(Long beginVertex, Long endVertex) {
        queue.add(beginVertex);
        //add vertex to queue
        while (queue.size() > 0) {
            Long vertexId = queue.remove();

            //get oneVertexr
            CoordinateModel point1 = new CoordinateModel(coordinateRepo.findOne(vertexId));
            List<CoordinateModel> listVertex = graph.get(vertexId);
            if (ValidateUtil.isEmpty(listVertex)) {
                for (CoordinateModel pointTo : listVertex) {

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
