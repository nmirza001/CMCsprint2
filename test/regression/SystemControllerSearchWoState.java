package regression;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import cmc.backend.SystemController;
import junit.framework.Assert;

public class SystemControllerSearchWoState {

	@Test
	public void test() {
		SystemController sc = new SystemController();
		List<String[]> results = sc.search("");
		Assert.assertTrue(results.size() > 0);
	}

}
