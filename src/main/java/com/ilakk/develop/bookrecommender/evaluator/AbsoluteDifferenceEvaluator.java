package com.ilakk.develop.bookrecommender.evaluator;

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

public class AbsoluteDifferenceEvaluator implements IRecommenderEvaluations {

	private DataModel model;

	private RecommenderBuilder recommenderBuilder;

	public AbsoluteDifferenceEvaluator(DataModel model, RecommenderBuilder recommenderBuilder) {
		this.model = model;
		this.recommenderBuilder = recommenderBuilder;
	}

	@Override
	public String evaluate() throws TasteException {

		// Recommender Evaluation -- Average Absolute Difference Evaluator
		RecommenderEvaluator absoluteDifferenceEvaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
		double score = absoluteDifferenceEvaluator.evaluate(recommenderBuilder, null, model, 0.9, 1.0);
		return "ALS-based Recommender Average Score is: " + score;

	}

}
