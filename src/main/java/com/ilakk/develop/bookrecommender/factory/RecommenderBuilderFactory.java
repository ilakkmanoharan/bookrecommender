package com.ilakk.develop.bookrecommender.factory;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;

import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;

import com.ilakk.develop.bookrecommender.recommender.*;
import com.ilakk.develop.bookrecommender.evaluator.*;
import org.springframework.util.ResourceUtils;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
*
* @author Ilakkuvaselvi Manoharan, 2019
* @copyright GNU General Public License v3
* No reproduction in whole or part without maintaining this copyright notice
* and imposing this condition on any subsequent users.
*
*
*/

@Component("recommenderfactory")
public class RecommenderBuilderFactory {

	private File file;

	private DataModel model;

	private int nearestN;

	private double neighbhorthres;

	private String userratingfile;

	@Autowired
	@Qualifier("nearestN")
	private void setNearestN(int nearestN) {
		this.nearestN = nearestN;
	}

	@Autowired
	@Qualifier("userratingfile")
	private void setUserRatingFileName(String userratingfile) {
		this.userratingfile = userratingfile;
	}

	@Autowired
	@Qualifier("neighbhorthres")
	private void setNeighbhorThres(double neighbhorthres) {
		this.neighbhorthres = neighbhorthres;
	}

	@PostConstruct
	public void init() throws IOException, FileNotFoundException {

		this.file = ResourceUtils.getFile(userratingfile);

		this.model = new FileDataModel(new File(file.toURI()));

	}

	/*
	 * public RecommenderBuilder getUserBasedRecommender(){
	 * 
	 * RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
	 * 
	 * public Recommender buildRecommender(DataModel model) throws TasteException {
	 * UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
	 * UserNeighborhood neighborhood = new NearestNUserNeighborhood(nearestN,
	 * similarity, model); return new GenericUserBasedRecommender(model,
	 * neighborhood, similarity);
	 * 
	 * } };
	 * 
	 * return recommenderBuilder;
	 * 
	 * }
	 * 
	 * public RecommenderBuilder getNearestNSpearman(){
	 * 
	 * RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
	 * 
	 * public Recommender buildRecommender(DataModel model) throws TasteException {
	 * UserSimilarity similarity = new SpearmanCorrelationSimilarity(model);
	 * UserNeighborhood neighborhood = new NearestNUserNeighborhood(nearestN,
	 * similarity, model); //UserNeighborhood neighborhood = new
	 * ThresholdUserNeighborhood(0.1, similarity, model); return new
	 * GenericUserBasedRecommender(model, neighborhood, similarity);
	 * 
	 * } };
	 * 
	 * return recommenderBuilder;
	 * 
	 * }
	 */
	public RecommenderBuilder getItemBasedRecommender() {

		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel model) throws TasteException {
				ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
				return new GenericItemBasedRecommender(model, similarity);
			}
		};

		return recommenderBuilder;

	}

	public RecommenderBuilder getUserBasedRecommender(UserSimilarity similarity, UserNeighborhood neighborhood) {

		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {

			public Recommender buildRecommender(DataModel model) throws TasteException {

				return new GenericUserBasedRecommender(model, neighborhood, similarity);

			}
		};

		return recommenderBuilder;

	}

	public UserNeighborhood getUserNeighborhood(String neighborhoodType, String similarityType) throws TasteException {
		if (similarityType == null || neighborhoodType == null) {
			return null; // check for null before switch statement
		} else {
			switch (neighborhoodType) {
			case "NearestN": {
				return new NearestNUserNeighborhood(nearestN, this.getUserSimilarity(similarityType), model);

			}
			case "Threshold": {
				return new ThresholdUserNeighborhood(neighbhorthres, this.getUserSimilarity(similarityType), model);
			}

			default:
				return null;
			}
		}
	}

	public UserSimilarity getUserSimilarity(String similarityType) throws TasteException {

		if (similarityType == null) {
			return null; // check for null before switch statement
		} else {
			switch (similarityType) {
			case "Pearson": {
				return new PearsonCorrelationSimilarity(model);

			}
			case "Spearman": {
				return new SpearmanCorrelationSimilarity(model);
			}
			case "Euclidean": {
				return new EuclideanDistanceSimilarity(model);
			}
			case "Cosine": {
				return new UncenteredCosineSimilarity(model);
			}
			default:
				return null;
			}
		}

	}

	public RecommenderBuilder getRecommenderBuilder(String recommendertype, String similarityType,
			String neighbhorhoodType) throws TasteException {

		if (recommendertype == null) {
			return null; // check for null before switch statement
		} else {
			switch (recommendertype) {
			case "UserBasedRecommendation": {

				UserSimilarity similarity = this.getUserSimilarity(similarityType);

				UserNeighborhood neighborhood = this.getUserNeighborhood(neighbhorhoodType, similarityType);

				return getUserBasedRecommender(similarity, neighborhood);
			}
			case "ItemBasedRecommendation": {
				return getItemBasedRecommender();
			}

			default:
				return null;
			}
		}

	}

	public IRecommendations getRecommender(String recommendertype, String similarityType, String neighbhorhoodType)
			throws TasteException {

		if (recommendertype == null) {
			return null; // check for null before switch statement
		} else {
			switch (recommendertype) {
			case "UserBasedRecommendation": {

				return new UserBasedRecommender(model,
						this.getRecommenderBuilder(recommendertype, similarityType, neighbhorhoodType));

			}
			case "ItemBasedRecommendation": {

				return new ItemBasedRecommender(model, this.getItemBasedRecommender());
			}

			default:
				return null;
			}
		}

	}

	public IRecommenderEvaluations getEvaluator(String evalType, String recommendertype, String similarityType,
			String neighbhorhoodType) throws TasteException {

		if (recommendertype == null || evalType == null) {
			return null; // check for null before switch statement
		} else {
			switch (evalType) {
			case "absoluteDifferenceEvaluation": {

				return new AbsoluteDifferenceEvaluator(model,
						this.getRecommenderBuilder(recommendertype, similarityType, neighbhorhoodType));
			}
			case "rmsEvaluation": {

				return new RMSEvaluator(model,
						this.getRecommenderBuilder(recommendertype, similarityType, neighbhorhoodType));
			}
			case "irStatsEvaluation": {
				return new IRSStatsEvaluator(model,
						this.getRecommenderBuilder(recommendertype, similarityType, neighbhorhoodType));
			}

			default:
				return null;
			}
		}

	}

}
