package com.ilakk.develop.bookrecommender.service;

import java.util.List;
import java.util.UUID;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.common.TasteException;

/**
*
* @author Ilakkuvaselvi Manoharan, 2019
* @copyright GNU General Public License v3
* No reproduction in whole or part without maintaining this copyright notice
* and imposing this condition on any subsequent users.
*
*
*/

public interface IBookRecommenderService {

	String recommend(int userid, String recommenderType, String similarityType, String neighborhoodType)
			throws IOException, URISyntaxException, TasteException;

	String evaluate(String evalType, String recommenderType, String similarityType, String neighborhoodType)
			throws IOException, URISyntaxException, TasteException;

}
