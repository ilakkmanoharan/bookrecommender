package com.ilakk.develop.bookrecommender;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.common.TasteException;

import com.ilakk.develop.bookrecommender.service.*;

/**
*
* @author Ilakkuvaselvi Manoharan, 2019
* @copyright GNU General Public License v3
* No reproduction in whole or part without maintaining this copyright notice
* and imposing this condition on any subsequent users.
*
*
*/

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class BookrecommenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookrecommenderApplication.class, args);
	}

	@Autowired
	private IBookRecommenderService iBookRecommenderService;

	@GetMapping("/recommend/{userid}")
	String recommend(@PathVariable int userid,
			@RequestParam(value = "recommendertype", required = true, defaultValue = "UserBasedRecommendation") String recommendertype,
			@RequestParam(value = "similarityType", required = true, defaultValue = "Spearman") String similaritytype,
			@RequestParam(value = "neighborhoodType", required = true, defaultValue = "NearestN") String neighborhoodtype)
			throws IOException, URISyntaxException, TasteException {

		return iBookRecommenderService.recommend(userid, recommendertype, similaritytype, neighborhoodtype);

	}

	@GetMapping("/evaluate")
	String recommendForUser(
			@RequestParam(value = "evaltype", required = true, defaultValue = "absoluteDifferenceEvaluation") String evaltype,
			@RequestParam(value = "recommendertype", required = true, defaultValue = "UserBasedRecommendation") String recommendertype,
			@RequestParam(value = "similarityType", required = true, defaultValue = "Spearman") String similaritytype,
			@RequestParam(value = "neighborhoodType", required = true, defaultValue = "NearestN") String neighborhoodtype)
			throws IOException, URISyntaxException, TasteException {

		return iBookRecommenderService.evaluate(evaltype, recommendertype, similaritytype, neighborhoodtype);

	}

}
