package mediHelper.listener;

import java.util.List;

import mediHelper.entities.Dane;
import mediHelper.entities.Stats;

// interfejs Listenera obiektu Data Access Object (wzorzec MVC)

public interface DatabaseListener {

	void dataAmountDelivered(Integer numer);

	void dataIsRead(List<Dane> lista);

	void categoryChange();

	void nextQuestionGiven(Dane dane);

	public void onStatsDelivered(Stats stats);
}
