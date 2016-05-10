package mediHelper.listener;

import java.util.List;

import mediHelper.entities.Dane;

public interface DatabaseListener {

	void dataAmount(Integer numer);

	void dataIsRead(List<Dane> lista);

	void categoryChange();

	void nextQuestionGiven(Dane dane);
}
