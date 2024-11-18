package com.rb.web2.repositories;

import com.rb.web2.domain.institution.Institution;

import java.util.Optional;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class InstitutionReposutoryTest {
	@Autowired
	private InstitutionRepository institutionRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void findById() {
		String id = "abc";
		Optional<Institution> expected = null;
		Optional<Institution> actual = institutionRepository.findById(id);

		assertEquals(expected, actual);
	}
}
