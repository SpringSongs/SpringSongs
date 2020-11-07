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

import io.github.springsongs.modules.sys.domain.SpringRole;
import io.github.springsongs.modules.sys.repo.SpringRoleRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithUserDetails("Administrator")
class SpringRoleControllerTest {
	@Autowired
	private WebApplicationContext context;

	@Autowired
	private SpringRoleRepo dao;

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void testGetPage() throws Exception {
		SpringRole entity = new SpringRole();
		entity.setTitle("ZnTXJtqlRHZeMUdqwXkIJsWLaydSolHPOTHk");
		entity.setDesc("ngLOuQPHvHjakasPetAQSvtXGERxxPHJSQRc");
		entity.setCreatedUserId("IDlIeOKZDHxntGsQVdKOzDnmBSpIPcLKYfXY");
		entity.setCreatedBy("sBuTsUYKNCZUpbVmsdefdQrBtiNnpHhcKdUL");
		entity.setCreatedIp("GxPtqwXgwYSkKnQDKsPdkzxQOWzbelSKPxyU");
		entity.setUpdatedUserId("rbjQkPZRknoRqLIyHKTdKlDMrhmVTtFpjsYW");
		entity.setUpdatedBy("JjzdvUbAeaxuUbYuudUhXkOtZqyKWDfFAoOi");
		entity.setUpdatedIp("EJmAtagplJlmCQcrdkrEVBLJWEwOahELoYnB");
		this.mvc.perform(post("/SpringRole/ListByPage").param("page", "1").param("limit", "20")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(entity))).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.[*].createdBy").value(hasItem("sBuTsUYKNCZUpbVmsdefdQrBtiNnpHhcKdUL")))
				.andExpect(jsonPath("$.data.[*].createdIp").value(hasItem("GxPtqwXgwYSkKnQDKsPdkzxQOWzbelSKPxyU")))
				.andExpect(jsonPath("$.data.[*].createdUserId").value(hasItem("IDlIeOKZDHxntGsQVdKOzDnmBSpIPcLKYfXY")))
				.andExpect(jsonPath("$.data.[*].desc").value(hasItem("ngLOuQPHvHjakasPetAQSvtXGERxxPHJSQRc")))
				.andExpect(jsonPath("$.data.[*].title").value(hasItem("ZnTXJtqlRHZeMUdqwXkIJsWLaydSolHPOTHk")))
				.andExpect(jsonPath("$.data.[*].updatedBy").value(hasItem("JjzdvUbAeaxuUbYuudUhXkOtZqyKWDfFAoOi")))
				.andExpect(jsonPath("$.data.[*].updatedIp").value(hasItem("EJmAtagplJlmCQcrdkrEVBLJWEwOahELoYnB")))
				.andExpect(jsonPath("$.data.[*].updatedUserId").value(hasItem("rbjQkPZRknoRqLIyHKTdKlDMrhmVTtFpjsYW")));
	}

	@Test
	void testGet() throws Exception {
		SpringRole entity = new SpringRole();
		entity.setTitle("DyllsAIAvGChkBgsOiDlaNVxsjGjeIsVqhCY");
		entity.setDesc("bAjZhUFcOMdTkztNfABqoAknjmSxrOhQPOye");
		entity.setCreatedUserId("EpEEDjiilLBaDIhecQUqqvgSiYPOFwTpyVWr");
		entity.setCreatedBy("bOjZJlABiYBNtPAHoHGOpnThDNmlphBFPBsU");
		entity.setCreatedIp("EDpaqLxLPyScYcGripNzACrsxuNIwFlIyiPR");
		entity.setUpdatedUserId("KdizAKGsYheJNXzpQftOtKJUCNdVRanzAqAP");
		entity.setUpdatedBy("sGYkbVmqcunmvdOJiXJnatAeVQJXglufHnit");
		entity.setUpdatedIp("iSwLUsWhgsfsJHZEWckhxmFzIguKXGBzdjgM");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringRole/Detail").param("id", entity.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$..createdBy").value(hasItem("bOjZJlABiYBNtPAHoHGOpnThDNmlphBFPBsU")))
				.andExpect(jsonPath("$..createdIp").value(hasItem("EDpaqLxLPyScYcGripNzACrsxuNIwFlIyiPR")))
				.andExpect(jsonPath("$..createdUserId").value(hasItem("EpEEDjiilLBaDIhecQUqqvgSiYPOFwTpyVWr")))
				.andExpect(jsonPath("$..desc").value(hasItem("bAjZhUFcOMdTkztNfABqoAknjmSxrOhQPOye")))
				.andExpect(jsonPath("$..title").value(hasItem("DyllsAIAvGChkBgsOiDlaNVxsjGjeIsVqhCY")))
				.andExpect(jsonPath("$..updatedBy").value(hasItem("sGYkbVmqcunmvdOJiXJnatAeVQJXglufHnit")))
				.andExpect(jsonPath("$..updatedIp").value(hasItem("iSwLUsWhgsfsJHZEWckhxmFzIguKXGBzdjgM")))
				.andExpect(jsonPath("$..updatedUserId").value(hasItem("KdizAKGsYheJNXzpQftOtKJUCNdVRanzAqAP")));
	}

	@Test
	void testSave() throws Exception {
		int databaseSizeBeforeCreate = dao.findAll().size();
		SpringRole entity = new SpringRole();
		entity.setTitle("tKwMWHROmqDReGROvzpqkhdzqbqGNPzUaNoH");
		entity.setDesc("NdqzlsKETrFclJuKzGCDNPtWfSwFHwjIMsBs");
		entity.setCreatedUserId("RRZOdzYUqwyJXCETOvgVWZQTdVowIhKneCiA");
		entity.setCreatedBy("RiXxNWqsNroyLxNCUMltKQGrRrQgHrCMkDaV");
		entity.setCreatedIp("rQVhorEKrYtHCsivEXqlQvGvgGSqChvBHYZA");
		entity.setUpdatedUserId("cYXGPZtSLKLszYpXtluIBbveKqrmbGHIoErH");
		entity.setUpdatedBy("pCudzbIyUPgwUJckMAVzuCExYBrBEAZzkVYD");
		entity.setUpdatedIp("EzgEwPkOXyhARkQQKXxBWeVibmePZssOUacs");
		this.mvc.perform(post("/SpringRole/Create").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(entity))).andExpect(status().isOk());
		List<SpringRole> SpringRoleEntityList = dao.findAll();
		assertThat(SpringRoleEntityList).hasSize(databaseSizeBeforeCreate + 1);
		SpringRole testSpringRoleEntity = SpringRoleEntityList.get(SpringRoleEntityList.size() - 1);
		assertThat(testSpringRoleEntity.getCreatedBy()).isEqualTo("RiXxNWqsNroyLxNCUMltKQGrRrQgHrCMkDaV");
		assertThat(testSpringRoleEntity.getCreatedIp()).isEqualTo("rQVhorEKrYtHCsivEXqlQvGvgGSqChvBHYZA");
		assertThat(testSpringRoleEntity.getCreatedUserId()).isEqualTo("RRZOdzYUqwyJXCETOvgVWZQTdVowIhKneCiA");
		assertThat(testSpringRoleEntity.getDesc()).isEqualTo("NdqzlsKETrFclJuKzGCDNPtWfSwFHwjIMsBs");
		assertThat(testSpringRoleEntity.getTitle()).isEqualTo("tKwMWHROmqDReGROvzpqkhdzqbqGNPzUaNoH");
		assertThat(testSpringRoleEntity.getUpdatedBy()).isEqualTo("pCudzbIyUPgwUJckMAVzuCExYBrBEAZzkVYD");
		assertThat(testSpringRoleEntity.getUpdatedIp()).isEqualTo("EzgEwPkOXyhARkQQKXxBWeVibmePZssOUacs");
		assertThat(testSpringRoleEntity.getUpdatedUserId()).isEqualTo("cYXGPZtSLKLszYpXtluIBbveKqrmbGHIoErH");
		;
	}

	@Test
	void testUpdate() throws Exception {
		SpringRole entity = new SpringRole();
		entity.setTitle("izPBOjixCDzdUrOjYqgOpHyyjLshTIEmVpSJ");
		entity.setDesc("BZGgqmhXkYQqOBdiSmDojENaIoGUEKnnPhPo");
		entity.setCreatedUserId("xhUNXEbzpkcrYOOFkXfrGMFqZxrztXMCnvtE");
		entity.setCreatedBy("XfctDBbytlGrWykkaDZzERMbrJGjXdfVbNIA");
		entity.setCreatedIp("BOjgtUVRNgyQOMDfSQNkNZpQMiVVViBaFwNt");
		entity.setUpdatedUserId("OfbJYpITiwYDoDNxyLnNtscQxUwDRpnJCPWa");
		entity.setUpdatedBy("uvjBTrnWLCGorcltINIHirfzUiAaoRVQrOwR");
		entity.setUpdatedIp("kTPINySszRorMTdeyVCTUSIXOmNIlevczoHo");
		dao.saveAndFlush(entity);
		int databaseSizeBeforeUpdate = dao.findAll().size();
		SpringRole updatedEntity = dao.findById(entity.getId()).get();
		updatedEntity.setTitle("dUlHLBaqWMFzYCqOLziVGIQRRwyOSVUXQxkm");
		updatedEntity.setDesc("feyoNklSmzQrUwyVBPlspnPUGMZeDteUWgSc");
		updatedEntity.setCreatedUserId("GhpltAsGUsHMcGzfLIEXTOxDfIYAShgwLyJu");
		updatedEntity.setCreatedBy("LaeBKmpNxAHVBpOWSRULnbyrQjHWZOxKKDTa");
		updatedEntity.setCreatedIp("NCQdaGrGWEKNQipfzJFNXyBoifPMvaTPhURT");
		updatedEntity.setUpdatedUserId("ggjOIsKvgwyqWwGlgwRBtTfyfMoTuHaabNLv");
		updatedEntity.setUpdatedBy("iRAmGQMKTFtLXngCoBdTVFSVmrnfqQvGGomX");
		updatedEntity.setUpdatedIp("LWQuqMwqgXbIVWBrxuSeSKREVEqKlRpfMMaF");
		this.mvc.perform(post("/SpringRole/Edit").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(updatedEntity)))
				.andExpect(status().isOk());
		List<SpringRole> SpringRoleEntityList = dao.findAll();
		assertThat(SpringRoleEntityList).hasSize(databaseSizeBeforeUpdate);
		SpringRole testSpringRoleEntity = SpringRoleEntityList.get(SpringRoleEntityList.size() - 1);
		assertThat(testSpringRoleEntity.getCreatedBy()).isEqualTo("LaeBKmpNxAHVBpOWSRULnbyrQjHWZOxKKDTa");
		assertThat(testSpringRoleEntity.getCreatedIp()).isEqualTo("NCQdaGrGWEKNQipfzJFNXyBoifPMvaTPhURT");
		assertThat(testSpringRoleEntity.getCreatedUserId()).isEqualTo("GhpltAsGUsHMcGzfLIEXTOxDfIYAShgwLyJu");
		assertThat(testSpringRoleEntity.getDesc()).isEqualTo("feyoNklSmzQrUwyVBPlspnPUGMZeDteUWgSc");
		assertThat(testSpringRoleEntity.getTitle()).isEqualTo("dUlHLBaqWMFzYCqOLziVGIQRRwyOSVUXQxkm");
		assertThat(testSpringRoleEntity.getUpdatedBy()).isEqualTo("iRAmGQMKTFtLXngCoBdTVFSVmrnfqQvGGomX");
		assertThat(testSpringRoleEntity.getUpdatedIp()).isEqualTo("LWQuqMwqgXbIVWBrxuSeSKREVEqKlRpfMMaF");
		assertThat(testSpringRoleEntity.getUpdatedUserId()).isEqualTo("ggjOIsKvgwyqWwGlgwRBtTfyfMoTuHaabNLv");
		;
	}

	@Test
	void testSetDeleted() throws Exception {
		SpringRole entity = new SpringRole();
		entity.setTitle("tuuGOyUrjPTgBMUspSqUjzKoJiBYZTcPLtoC");
		entity.setDesc("zxfvXmVjFSSwllIgDKrOHPAijtjRhCsPYvUC");
		entity.setCreatedUserId("aExUixAarJXnuEyiLRImsqUGBFghBZtgwnxO");
		entity.setCreatedBy("UKDptDHimqANtMFzTIZanZIxLdlEcsLmbppV");
		entity.setCreatedIp("hpgJxcYDGYmKlxyRpmEXZMLcXbdssuwImyTB");
		entity.setUpdatedUserId("zqEFwTphYxsUQwlvBcPmpoLMKltnYJtRSCCg");
		entity.setUpdatedBy("BbZptRzJtmHsaohGnQPeMWOFsjUXwIkPsLgt");
		entity.setUpdatedIp("bDacjQdELwzSptvwAVzQdlAWejoLqCnTvuNw");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringRole/SetDeleted").param("ids", entity.getId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk());
	}

}
