package cmc.backend;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import cmc.backend.entities.University;

/**
 * Test the database and its interactions with the University class.
 * @author Roman Lefler
 * @version Mar 15, 2024
 */
public class UniversityTest {
	
	private DatabaseController db;
	private static final String NAME = "TEST SCHOOL NAME";
	
	private University getUni(String name) {
		List<University> us = db.getAllSchools();
		for(University u : us) {
			if(u.getName().equals(name)) return u;
		}
		return null;
	}
	
	@Before
	public void setUp() {
		db = new DatabaseController();
		University uni = new University(NAME);
		boolean succ = db.addNewUniversity(uni);
		Assert.assertTrue(succ);
	}
	
	@After
	public void tearDown() {
		University u = getUni(NAME);
		Assert.assertNotNull(u);
		boolean succ = db.removeUniversity(u);
		Assert.assertTrue(succ);
		db = null;
	}
	
	@Test
	public void testSetUp() {
		// Right now we don't have University methods
		// to actually test so just test the setup
	}

}
