package kariminf.as.postProcess.extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kariminf.as.process.Scorer;
import kariminf.as.process.ssfgc.SSFScoreHandler;
import kariminf.as.tools.Data;
import kariminf.as.tools.Tools;

public class GraphReOrderer extends Extractor {
	
	HashMap<Integer, List<Integer>> relatives;
	
	public GraphReOrderer(Scorer scorer){
		super(scorer);
		if (scorer.getScoreHandler() instanceof SSFScoreHandler){
			relatives = ((SSFScoreHandler)scorer.getScoreHandler()).getRelatives();
		}
	}

	@Override
	public void reOrder() {
		
		if (relatives == null) return;
		
		List<Integer> order0 = scorer.getOrdered();
		
		int index = order0.get(0);
		order.add(index);
		
		while(true){
			List<Integer> relIDs = relatives.get(index);
			index = -1;
			int minOrder = Integer.MAX_VALUE;
			if (relIDs == null) break;
			for(int relID: relIDs){
				int thisOrder = order0.indexOf(relID)+1;
				if (thisOrder >= 0 && minOrder > thisOrder)
					if(! order.contains(relID)){
						index = relID;
						minOrder = thisOrder;
					}
			}
			
			if (index < 0) break;
			
			order.add(index);
		}
		
	}

}
