package regression;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cmc.CMCException;
import cmc.backend.DatabaseController;
import junit.framework.Assert;

public class SaveSchoolThrowsUnexpectedly {

	private static final String USERNAME = "test_test_test_ayo";
	private static final String PASSWD = "onskibidi73";
	
	private DatabaseController db;
	
	@Before
	public void setUp() throws CMCException {
		db = new DatabaseController();
		db.addUser(USERNAME, PASSWD, 'u', "lebron", "james");
		db.deactivateUser(USERNAME);
	}
	
	@After
	public void tearDown() throws CMCException {
		db.removeUser(USERNAME);
	}
	
	@Test
	public void test() throws CMCException {
		
		boolean succ = db.saveSchool(USERNAME, "CHALMERS UNIVERSITY OF TECHNOLOGY");
		Assert.assertTrue(succ);
		
		succ = db.saveSchool(USERNAME, "CHALMERS UNIVERSITY OF TECHNOLOGY");
		Assert.assertFalse(succ);
	}

}
