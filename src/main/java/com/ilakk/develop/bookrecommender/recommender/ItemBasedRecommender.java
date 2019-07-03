package com.ilakk.develop.bookrecommender.recommender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.io.File;

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
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;

public class ItemBasedRecommender implements IRecommendations {

	private DataModel model;

	private RecommenderBuilder recommenderBuilder;

	public ItemBasedRecommender(DataModel model, RecommenderBuilder recommenderBuilder) {
		this.model = model;
		this.recommenderBuilder = recommenderBuilder;
	}

	@Override
	public String recommend(int userid) {

		List<RecommendedItem> recommendations = null;
		List<String> recommendationsStr = new ArrayList<String>();
		try {
			recommendations = recommenderBuilder.buildRecommender(model).recommend(userid, 3);
		} catch (TasteException e) {
			e.printStackTrace();
		}

		System.out.println("PearsonCorrelationSimilarity Results :\nItem-based recommendation");
		for (RecommendedItem recommendation : recommendations) {
			recommendationsStr.add(recommendation.toString());
		}

		String r = recommendationsStr.stream().collect(Collectors.joining(" , "));

		System.out.println(r);

		return r;

	}

}
