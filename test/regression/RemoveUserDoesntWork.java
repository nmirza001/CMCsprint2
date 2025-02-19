package regression;

import static org.junit.Assert.*;

import org.junit.Test;

import cmc.backend.*;
import junit.framework.Assert;
import cmc.*;

public class RemoveUserDoesntWork {

	private static final String PASSWD = "pass123$";
	private static final String U_ADMIN = "test_test_test_admin_user";
	private static final String U_USER = "test_test_test_regular_user";
	
	@Test
	public void test() throws CMCException {
		DatabaseController db = new DatabaseController();
		SystemController sc = new SystemController();
		
		db.addUser(U_USER, PASSWD, 'u', "Admin", "McAdministrator");
		db.addUser(U_ADMIN, PASSWD, 'a', "Regular", "MacUser");
		
		boolean succ = db.removeUser(U_USER);
		Assert.assertTrue(succ);

		succ = db.removeUser(PASSWD);
		Assert.assertTrue(succ);

				
	}

}
