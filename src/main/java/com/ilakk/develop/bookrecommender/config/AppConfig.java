package com.ilakk.develop.bookrecommender.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.annotation.PostConstruct;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

/**
*
* @author Ilakkuvaselvi Manoharan, 2019
* @copyright GNU General Public License v3
* No reproduction in whole or part without maintaining this copyright notice
* and imposing this condition on any subsequent users.
*
*
*/

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = { "com.ilakk.*" })
@Component("appconfig")
public class AppConfig {

	@Value("${bookrecommender.file.userrating}")
	String userratingfile;

	@Value("${bookrecommender.recommender.nearestN}")
	String nearestN;

	@Value("${bookrecommender.recommender.neighbhorthreshold}")
	String neighbhorthres;

	@Bean("userratingfile")
	public String getUserRatingFile() {
		return userratingfile;
	}

	@Bean("nearestN")
	public int getNearestN() {
		return Integer.valueOf(nearestN);
	}

	@Bean("neighbhorthres")
	public double getNeighbhorThreshold() {
		return Double.parseDouble(neighbhorthres);
	}

}
