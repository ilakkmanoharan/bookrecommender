package com.ilakk.develop.bookrecommender.service;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.io.PrintWriter;
import java.net.URISyntaxException;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.common.TasteException;

import com.ilakk.develop.bookrecommender.factory.*;
import com.ilakk.develop.bookrecommender.recommender.*;
import com.ilakk.develop.bookrecommender.evaluator.*;
import com.ilakk.develop.bookrecommender.config.*;

/**
*
* @author Ilakkuvaselvi Manoharan, 2019
* @copyright GNU General Public License v3
* No reproduction in whole or part without maintaining this copyright notice
* and imposing this condition on any subsequent users.
*
*
*/

@Component("service")
public class BookRecommenderServiceImpl implements IBookRecommenderService {

	@Autowired
	@Qualifier("recommenderfactory")
	private RecommenderBuilderFactory recommenderfactory;

	private IRecommendations recommender;

	private IRecommenderEvaluations evaluator;

	@Override
	public String recommend(int userid, String recommenderType, String similarityType, String neighborhoodType)
			throws IOException, URISyntaxException, TasteException {

		recommender = recommenderfactory.getRecommender(recommenderType, similarityType, neighborhoodType);

		return recommender.recommend(userid);

	}

	@Override
	public String evaluate(String evalType, String recommenderType, String similarityType, String neighborhoodType)
			throws IOException, URISyntaxException, TasteException {

		evaluator = recommenderfactory.getEvaluator(evalType, recommenderType, similarityType, neighborhoodType);

		return evaluator.evaluate();

	}

}
