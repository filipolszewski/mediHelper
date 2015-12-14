package mediHelper;

import java.util.List;

public interface DatabaseListener {

	void dataAmount(Integer numer);

	void dataIsRead(List<DataRow[]> lista);
}
