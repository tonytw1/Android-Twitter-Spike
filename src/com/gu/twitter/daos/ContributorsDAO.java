package com.gu.twitter.daos;

import java.util.ArrayList;
import java.util.List;

import com.gu.twitter.model.Contributor;

public class ContributorsDAO {

	
	public List<Contributor> getContributors() {
		List<Contributor> contributors = new ArrayList<Contributor>();
		
		Contributor AndrewSparrow = new Contributor("Andrew Sparrow", "Andrew Sparrow is the senior political correspondent on the Guardian website", "AndrewSparrow");
		contributors.add(AndrewSparrow);
				
		Contributor LucyMangan = new Contributor("Lucy Mangan", "Guardian columnist, writes books, comes late to technology. ", "LucyMangan");
		contributors.add(LucyMangan);
		return contributors;
	}
	
}
