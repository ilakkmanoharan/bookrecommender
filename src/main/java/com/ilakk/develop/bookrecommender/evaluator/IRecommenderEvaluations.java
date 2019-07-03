package com.ilakk.develop.bookrecommender.evaluator;

import java.io.IOException;
import java.net.URISyntaxException;
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

public interface IRecommenderEvaluations {

	public String evaluate() throws TasteException;

}
