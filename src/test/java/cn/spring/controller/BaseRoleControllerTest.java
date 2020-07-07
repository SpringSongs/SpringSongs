package cn.spring.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;

import cn.spring.dao.SpringRoleDao;
import cn.spring.domain.SpringRole;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class BaseRoleControllerTest {
	@Autowired
	private WebApplicationContext context;

	@Autowired
	private SpringRoleDao dao;

	// @Autowired
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
		this.mvc.perform(post("/BaseRole/ListByPage").param("page", "1").param("limit", "20")
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
		this.mvc.perform(post("/BaseRole/Detail").param("id", entity.getId())).andExpect(status().isOk())
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
		this.mvc.perform(post("/BaseRole/Create").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(entity))).andExpect(status().isOk());
		List<SpringRole> baseRoleEntityList = dao.findAll();
		assertThat(baseRoleEntityList).hasSize(databaseSizeBeforeCreate + 1);
		SpringRole testBaseRoleEntity = baseRoleEntityList.get(baseRoleEntityList.size() - 1);
		assertThat(testBaseRoleEntity.getCreatedBy()).isEqualTo("RiXxNWqsNroyLxNCUMltKQGrRrQgHrCMkDaV");
		assertThat(testBaseRoleEntity.getCreatedIp()).isEqualTo("rQVhorEKrYtHCsivEXqlQvGvgGSqChvBHYZA");
		assertThat(testBaseRoleEntity.getCreatedUserId()).isEqualTo("RRZOdzYUqwyJXCETOvgVWZQTdVowIhKneCiA");
		assertThat(testBaseRoleEntity.getDesc()).isEqualTo("NdqzlsKETrFclJuKzGCDNPtWfSwFHwjIMsBs");
		assertThat(testBaseRoleEntity.getTitle()).isEqualTo("tKwMWHROmqDReGROvzpqkhdzqbqGNPzUaNoH");
		assertThat(testBaseRoleEntity.getUpdatedBy()).isEqualTo("pCudzbIyUPgwUJckMAVzuCExYBrBEAZzkVYD");
		assertThat(testBaseRoleEntity.getUpdatedIp()).isEqualTo("EzgEwPkOXyhARkQQKXxBWeVibmePZssOUacs");
		assertThat(testBaseRoleEntity.getUpdatedUserId()).isEqualTo("cYXGPZtSLKLszYpXtluIBbveKqrmbGHIoErH");
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
		this.mvc.perform(post("/BaseRole/Edit").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(updatedEntity)))
				.andExpect(status().isOk());
		List<SpringRole> baseRoleEntityList = dao.findAll();
		assertThat(baseRoleEntityList).hasSize(databaseSizeBeforeUpdate);
		SpringRole testBaseRoleEntity = baseRoleEntityList.get(baseRoleEntityList.size() - 1);
		assertThat(testBaseRoleEntity.getCreatedBy()).isEqualTo("LaeBKmpNxAHVBpOWSRULnbyrQjHWZOxKKDTa");
		assertThat(testBaseRoleEntity.getCreatedIp()).isEqualTo("NCQdaGrGWEKNQipfzJFNXyBoifPMvaTPhURT");
		assertThat(testBaseRoleEntity.getCreatedUserId()).isEqualTo("GhpltAsGUsHMcGzfLIEXTOxDfIYAShgwLyJu");
		assertThat(testBaseRoleEntity.getDesc()).isEqualTo("feyoNklSmzQrUwyVBPlspnPUGMZeDteUWgSc");
		assertThat(testBaseRoleEntity.getTitle()).isEqualTo("dUlHLBaqWMFzYCqOLziVGIQRRwyOSVUXQxkm");
		assertThat(testBaseRoleEntity.getUpdatedBy()).isEqualTo("iRAmGQMKTFtLXngCoBdTVFSVmrnfqQvGGomX");
		assertThat(testBaseRoleEntity.getUpdatedIp()).isEqualTo("LWQuqMwqgXbIVWBrxuSeSKREVEqKlRpfMMaF");
		assertThat(testBaseRoleEntity.getUpdatedUserId()).isEqualTo("ggjOIsKvgwyqWwGlgwRBtTfyfMoTuHaabNLv");
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
		this.mvc.perform(post("/BaseRole/SetDeleted").param("ids", entity.getId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk());
	}

}
