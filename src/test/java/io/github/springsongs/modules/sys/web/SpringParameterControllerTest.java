package io.github.springsongs.modules.sys.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;

import io.github.springsongs.modules.sys.domain.SpringParameter;
import io.github.springsongs.modules.sys.repo.SpringParameterRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithUserDetails("Administrator")
class SpringParameterControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private SpringParameterRepo dao;

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void testGetPage() throws Exception {
		SpringParameter entity = new SpringParameter();
		entity.setCode("NkYnVBmguyHzXBAgTErvwDbJYohHfUYamaUm");
		entity.setK("oMkrTOCYcoxNijwBOsKmYakRuoeOZuRZoTuB");
		entity.setV("qKUFmBBOrAoKeZUyaNtOLQWZOxjMJvOEKTZg");
		entity.setCreatedUserId("kBdLHbtlVsjBbXnmnxZOpBIKRRJnQkHIFfov");
		entity.setCreatedBy("bIErDnNrGmtntzrRreviHSXiAYmuNiGjSzRE");
		entity.setCreatedIp("YeLXeFcuvkgKgOOoVcCwzeHnySbdnGyuqNBY");
		entity.setUpdatedUserId("DpYAjaXlowBzLudOXESVIcwNRpGDGRnTPyAO");
		entity.setUpdatedBy("FAMGJvCFnQoqCNIYxXdJzQTmUmsjPKTkWouw");
		entity.setUpdatedIp("AUryLDZOKbgYUJpVXLJiomdhrzKhVnjUVFPc");
		this.mvc.perform(post("/SpringParameter/ListByPage").param("page", "1").param("limit", "20")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(entity))).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.[*].code").value(hasItem("NkYnVBmguyHzXBAgTErvwDbJYohHfUYamaUm")))
				.andExpect(jsonPath("$.data.[*].content").value(hasItem("qKUFmBBOrAoKeZUyaNtOLQWZOxjMJvOEKTZg")))
				.andExpect(jsonPath("$.data.[*].createdBy").value(hasItem("bIErDnNrGmtntzrRreviHSXiAYmuNiGjSzRE")))
				.andExpect(jsonPath("$.data.[*].createdIp").value(hasItem("YeLXeFcuvkgKgOOoVcCwzeHnySbdnGyuqNBY")))
				.andExpect(jsonPath("$.data.[*].createdUserId").value(hasItem("kBdLHbtlVsjBbXnmnxZOpBIKRRJnQkHIFfov")))
				.andExpect(jsonPath("$.data.[*].title").value(hasItem("oMkrTOCYcoxNijwBOsKmYakRuoeOZuRZoTuB")))
				.andExpect(jsonPath("$.data.[*].updatedBy").value(hasItem("FAMGJvCFnQoqCNIYxXdJzQTmUmsjPKTkWouw")))
				.andExpect(jsonPath("$.data.[*].updatedIp").value(hasItem("AUryLDZOKbgYUJpVXLJiomdhrzKhVnjUVFPc")))
				.andExpect(jsonPath("$.data.[*].updatedUserId").value(hasItem("DpYAjaXlowBzLudOXESVIcwNRpGDGRnTPyAO")));
	}

	@Test
	void testGet() throws Exception {
		SpringParameter entity = new SpringParameter();
		entity.setCode("qCuCvvqwddtSwqWaYLpHfvSHFrTQvzFxjVBa");
		entity.setK("OpdYGNDmGhEJinaekdQqDoCtzLlsZfDGsRGM");
		entity.setV("jwfZjSQABjZSNoosVlrEqvRPAXJMgSbTfuei");
		entity.setCreatedUserId("idLjQEkTocUAOEXccvKdosytzCqZfbSXHdUL");
		entity.setCreatedBy("FPEnHwCuilbJPcVfosCHGtsayBMAOuAWZEDp");
		entity.setCreatedIp("PMonDMZIZkUjCtBWdksWLajaLYyaEehquMhi");
		entity.setUpdatedUserId("SraqnabCWAAbbLtvlWORLDHYGzWpgMtxovMD");
		entity.setUpdatedBy("UVcnEulqtyPQWbQCVperwflavUXZMLpXzkBC");
		entity.setUpdatedIp("CXEvWcDiPcXSuFQAJrLbzMusNBqRhHQeHUTU");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringParameter/Detail").param("id", entity.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$..code").value(hasItem("qCuCvvqwddtSwqWaYLpHfvSHFrTQvzFxjVBa")))
				.andExpect(jsonPath("$..content").value(hasItem("jwfZjSQABjZSNoosVlrEqvRPAXJMgSbTfuei")))
				.andExpect(jsonPath("$..createdBy").value(hasItem("FPEnHwCuilbJPcVfosCHGtsayBMAOuAWZEDp")))
				.andExpect(jsonPath("$..createdIp").value(hasItem("PMonDMZIZkUjCtBWdksWLajaLYyaEehquMhi")))
				.andExpect(jsonPath("$..createdUserId").value(hasItem("idLjQEkTocUAOEXccvKdosytzCqZfbSXHdUL")))
				.andExpect(jsonPath("$..title").value(hasItem("OpdYGNDmGhEJinaekdQqDoCtzLlsZfDGsRGM")))
				.andExpect(jsonPath("$..updatedBy").value(hasItem("UVcnEulqtyPQWbQCVperwflavUXZMLpXzkBC")))
				.andExpect(jsonPath("$..updatedIp").value(hasItem("CXEvWcDiPcXSuFQAJrLbzMusNBqRhHQeHUTU")))
				.andExpect(jsonPath("$..updatedUserId").value(hasItem("SraqnabCWAAbbLtvlWORLDHYGzWpgMtxovMD")));
	}

	@Test
	void testSave() throws Exception {
		int databaseSizeBeforeCreate = dao.findAll().size();
		SpringParameter entity = new SpringParameter();
		entity.setCode("RjjPInNaSVBPbCZwxEHKMjZvBDqdDVJiompk");
		entity.setK("ibIOWeSDiQqzFXYNFquLisDdHnDTwCeNXjvc");
		entity.setV("GbPgKGIMVuUIszqnufeuNhMVfmulrCuLvJeA");
		entity.setCreatedUserId("puGLhEvIENodOdOOFjUEsYUitPbBmJHHXVeG");
		entity.setCreatedBy("jRxzOYSQtclsgSFojOiJVcfLgwAGgFcbNTWs");
		entity.setCreatedIp("QejRDsoVLRCHMmuTikddglLOsJEkxXoOuHdY");
		entity.setUpdatedUserId("XhuGcRhINRDIuoJiQMsasyMuRtmXSjlXzrxU");
		entity.setUpdatedBy("SaLfJZezmPwVIDybPInHtBjAnoUFnztIireN");
		entity.setUpdatedIp("uxCuYsYfJWczspGtObPdjkKIBbxBuJmHvTwr");
		this.mvc.perform(post("/SpringParameter/Create").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(entity))).andExpect(status().isOk());
		List<SpringParameter> SpringParameterEntityList = dao.findAll();
		assertThat(SpringParameterEntityList).hasSize(databaseSizeBeforeCreate + 1);
		SpringParameter testSpringParameterEntity = SpringParameterEntityList.get(SpringParameterEntityList.size() - 1);
		assertThat(testSpringParameterEntity.getCode()).isEqualTo("RjjPInNaSVBPbCZwxEHKMjZvBDqdDVJiompk");
		assertThat(testSpringParameterEntity.getV()).isEqualTo("GbPgKGIMVuUIszqnufeuNhMVfmulrCuLvJeA");
		assertThat(testSpringParameterEntity.getCreatedBy()).isEqualTo("jRxzOYSQtclsgSFojOiJVcfLgwAGgFcbNTWs");
		assertThat(testSpringParameterEntity.getCreatedIp()).isEqualTo("QejRDsoVLRCHMmuTikddglLOsJEkxXoOuHdY");
		assertThat(testSpringParameterEntity.getCreatedUserId()).isEqualTo("puGLhEvIENodOdOOFjUEsYUitPbBmJHHXVeG");
		assertThat(testSpringParameterEntity.getK()).isEqualTo("ibIOWeSDiQqzFXYNFquLisDdHnDTwCeNXjvc");
		assertThat(testSpringParameterEntity.getUpdatedBy()).isEqualTo("SaLfJZezmPwVIDybPInHtBjAnoUFnztIireN");
		assertThat(testSpringParameterEntity.getUpdatedIp()).isEqualTo("uxCuYsYfJWczspGtObPdjkKIBbxBuJmHvTwr");
		assertThat(testSpringParameterEntity.getUpdatedUserId()).isEqualTo("XhuGcRhINRDIuoJiQMsasyMuRtmXSjlXzrxU");
		;
	}

	@Test
	void testUpdate() throws Exception {
		SpringParameter entity = new SpringParameter();
		entity.setCode("bVCtxcyMmrpzBAAiQoBXvSsuAGFRTZffHKzX");
		entity.setK("aZswdpcPaLQMxZSIlhJUzhnRfhVAycrEqFTo");
		entity.setV("pdLuBjtXggZButPPlNEhbYhUsShYBaWKdOqd");
		entity.setCreatedUserId("GetTzdlVgsIZhKmynRsFWyNJSbbZmOlcDhoU");
		entity.setCreatedBy("qKijjdlmweojOlIhRNVncSbIzoeNUgwHAmER");
		entity.setCreatedIp("JPCFCKBjluQfociTIhYWlshlybOelqeIEDRW");
		entity.setUpdatedUserId("ZPpKLSUqmUmEyXWnEqXZhtKfYvYfwXZKEXRZ");
		entity.setUpdatedBy("sEoqHlSVzrfpiRNWXQkbOpwiExLgxLqXzzXT");
		entity.setUpdatedIp("QsQcTzyuLiQBnjQEotKFVBejxkRMhFQGyslu");
		dao.saveAndFlush(entity);
		int databaseSizeBeforeUpdate = dao.findAll().size();
		SpringParameter updatedEntity = dao.findById(entity.getId()).get();
		updatedEntity.setCode("sksXdqbXuPKMvRXovkibPyXcqsGSrWnzporP");
		updatedEntity.setK("FOjerpnnhsNsExLymMEfrsNAQUToGNZFmtEY");
		updatedEntity.setV("twuHcNnKrBOrdcWoDnLBHBMerLowPRIDuJUv");
		updatedEntity.setCreatedUserId("KgSWTzLDieOqEqOAVIWSHStEknWewZCkoNTh");
		updatedEntity.setCreatedBy("YdsACKflnhyjhAnmSewoKsxYPkyTBjmHlaPW");
		updatedEntity.setCreatedIp("iQvWOKKghRFcfrNtAhcEfyvSOjuitLEaZCzT");
		updatedEntity.setUpdatedUserId("FMCTgXngaDyfKrEOZQjDxvGMFXrawjAUVPOL");
		updatedEntity.setUpdatedBy("LPQRaTXqXSKMHjwDuOaGZRGCldgYjWFyLMCU");
		updatedEntity.setUpdatedIp("ssdJYkdqHTaEHnYitMXgTyicCpDbzpYZKttR");
		this.mvc.perform(post("/SpringParameter/Edit").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(updatedEntity)))
				.andExpect(status().isOk());
		List<SpringParameter> SpringParameterEntityList = dao.findAll();
		assertThat(SpringParameterEntityList).hasSize(databaseSizeBeforeUpdate);
		SpringParameter testSpringParameterEntity = SpringParameterEntityList.get(SpringParameterEntityList.size() - 1);
		assertThat(testSpringParameterEntity.getCode()).isEqualTo("sksXdqbXuPKMvRXovkibPyXcqsGSrWnzporP");
		assertThat(testSpringParameterEntity.getV()).isEqualTo("twuHcNnKrBOrdcWoDnLBHBMerLowPRIDuJUv");
		assertThat(testSpringParameterEntity.getCreatedBy()).isEqualTo("YdsACKflnhyjhAnmSewoKsxYPkyTBjmHlaPW");
		assertThat(testSpringParameterEntity.getCreatedIp()).isEqualTo("iQvWOKKghRFcfrNtAhcEfyvSOjuitLEaZCzT");
		assertThat(testSpringParameterEntity.getCreatedUserId()).isEqualTo("KgSWTzLDieOqEqOAVIWSHStEknWewZCkoNTh");
		assertThat(testSpringParameterEntity.getK()).isEqualTo("FOjerpnnhsNsExLymMEfrsNAQUToGNZFmtEY");
		assertThat(testSpringParameterEntity.getUpdatedBy()).isEqualTo("LPQRaTXqXSKMHjwDuOaGZRGCldgYjWFyLMCU");
		assertThat(testSpringParameterEntity.getUpdatedIp()).isEqualTo("ssdJYkdqHTaEHnYitMXgTyicCpDbzpYZKttR");
		assertThat(testSpringParameterEntity.getUpdatedUserId()).isEqualTo("FMCTgXngaDyfKrEOZQjDxvGMFXrawjAUVPOL");
		;
	}

	@Test
	void testSetDeleted() throws Exception {
		SpringParameter entity = new SpringParameter();
		entity.setCode("bKkqkgqWfOEdeGtUmgYTEcquZLpwUZbESPtp");
		entity.setK("PQrwhpgZSeRIlWXPxlTcerYvVlgkXbsPJRPF");
		entity.setV("kfbMFRCAwLVOkmwIiUvRuUBHOcyrMrRmMLjC");
		entity.setCreatedUserId("obwsDsDwaAFlIvFapznbMxsnWNAvfDSBFGKy");
		entity.setCreatedBy("hhJiRjmXGclWBjWSBjLTJNNFecrjkPIInAgW");
		entity.setCreatedIp("CMrCFyrLYRxfvZcARKZGrgkHwkLzOgzAJvSi");
		entity.setUpdatedUserId("IHwbOHkXdrkqTpAgAMRoAnnivpQOoYYwIzAF");
		entity.setUpdatedBy("hgathQRLUzJNSFqPNhmxFYnHPuVIHyLkpGTz");
		entity.setUpdatedIp("JlwUjIEKIHWTYsznBwxoKkqaKPYLOxmYqBsf");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringParameter/SetDeleted").param("ids", entity.getId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk());
	}

}
