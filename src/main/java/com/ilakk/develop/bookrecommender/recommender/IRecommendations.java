package com.ilakk.develop.bookrecommender.recommender;

import java.io.IOException;
import java.net.URISyntaxException;

@FunctionalInterface
public interface IRecommendations {

	String recommend(int userid);

}
