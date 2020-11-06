package io.github.springsongs.modules.sys.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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

import io.github.springsongs.modules.sys.domain.SpringResource;
import io.github.springsongs.modules.sys.repo.SpringResourceRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class BaseModuleControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private SpringResourceRepo dao;

	// @Autowired
	private MockMvc mvc;

	@BeforeEach
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void testGetPage() throws Exception {
		SpringResource entity = new SpringResource();
		entity.setCode("MjwNXFiWtUoWYTjYfVSAtgTcUCdnaTDbqbca");
		entity.setTitle("QLVyvfyodOVxYlRUnFiuurFYCJtIqeNLsnIH");
		entity.setVueUrl("bATsabtwPiOoNkvdrCgvGrmDTkIbondDBrdD");
		entity.setAngularUrl("eRcsdWDAeOOZfDkipnnflcuPIVdQOqYPbcSf");
		entity.setParentId("TSoQuYdiIaQeBHVSZDTIjKSNbPnQQfcJhDBn");
		entity.setParentName("RdXchTmZoscnzkHzZkYJkzmwXOoqVNloPNBr");
		entity.setSystemId("GcKxjWGWmnXbTmjlHZbirfiFWexUbrchpOgi");
		entity.setCreatedUserId("vQMXnzBIIavjqbFyTSiYvsifKLKyjKALlDwP");
		entity.setCreatedBy("XVDqnxYjBSowMgJwMrtcnAOOxpmypRxFnthz");
		entity.setCreatedIp("hsVFcjlVmIWQUUoPwgmUKWpcWYJHZzysntCn");
		entity.setUpdatedUserId("zHYeAVfxUDCECXvzVaagSyOUfMVMotDzlrow");
		entity.setUpdatedBy("gOgvQaUAaWLPQiHxcYPGaBGmczKCEmEeGUTJ");
		entity.setUpdatedIp("woshSnwczOZKLSpICpKDdLLDZaGibvflFtFL");
		this.mvc.perform(post("/BaseModule/ListByPage").with(csrf().useInvalidToken())
				.with(user("Administrator").password("qweasd").roles("USER", "Administrators")).param("page", "1").param("limit", "20")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(entity))).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.[*].angularUrl").value(hasItem("eRcsdWDAeOOZfDkipnnflcuPIVdQOqYPbcSf")))
				.andExpect(jsonPath("$.data.[*].code").value(hasItem("MjwNXFiWtUoWYTjYfVSAtgTcUCdnaTDbqbca")))
				.andExpect(jsonPath("$.data.[*].createdBy").value(hasItem("XVDqnxYjBSowMgJwMrtcnAOOxpmypRxFnthz")))
				.andExpect(jsonPath("$.data.[*].createdIp").value(hasItem("hsVFcjlVmIWQUUoPwgmUKWpcWYJHZzysntCn")))
				.andExpect(jsonPath("$.data.[*].createdUserId").value(hasItem("vQMXnzBIIavjqbFyTSiYvsifKLKyjKALlDwP")))
				.andExpect(jsonPath("$.data.[*].formName").value(hasItem("LQMgEuTSHuKFTesfdSPRhJpgJkiwRjhHtGPk")))
				.andExpect(jsonPath("$.data.[*].parentId").value(hasItem("TSoQuYdiIaQeBHVSZDTIjKSNbPnQQfcJhDBn")))
				.andExpect(jsonPath("$.data.[*].parentName").value(hasItem("RdXchTmZoscnzkHzZkYJkzmwXOoqVNloPNBr")))
				.andExpect(jsonPath("$.data.[*].systemId").value(hasItem("GcKxjWGWmnXbTmjlHZbirfiFWexUbrchpOgi")))
				.andExpect(jsonPath("$.data.[*].title").value(hasItem("QLVyvfyodOVxYlRUnFiuurFYCJtIqeNLsnIH")))
				.andExpect(jsonPath("$.data.[*].updatedBy").value(hasItem("gOgvQaUAaWLPQiHxcYPGaBGmczKCEmEeGUTJ")))
				.andExpect(jsonPath("$.data.[*].updatedIp").value(hasItem("woshSnwczOZKLSpICpKDdLLDZaGibvflFtFL")))
				.andExpect(jsonPath("$.data.[*].updatedUserId").value(hasItem("zHYeAVfxUDCECXvzVaagSyOUfMVMotDzlrow")))
				.andExpect(jsonPath("$.data.[*].vueUrl").value(hasItem("bATsabtwPiOoNkvdrCgvGrmDTkIbondDBrdD")));
	}

	@Test
	void testGet() throws Exception {
		SpringResource entity = new SpringResource();
		entity.setCode("VkCDZvITglIZfgwkNzdoEnjArKrzgecALXbY");
		entity.setTitle("nWZxRmcMJKexEADDmfmQLsXfxnYdAiSJgUBN");
		entity.setVueUrl("mEJaWyEBUKgceLseyrYiyWujAofSCIFfOwhb");
		entity.setAngularUrl("HJQVxeHHuGXQHGgbrKrVkysaZskvkQCkhyzP");
		entity.setParentId("JPzCNeiASbmfqZfIAFaxCYjWQKwCcPGZrFkZ");
		entity.setParentName("PAzWSRHNaenMedotCPuyMfoplORkUETICDwq");
		entity.setSystemId("qgzoQSkfmXFjuDFUnYCulPboNKtKeCJQTOGt");
		entity.setCreatedUserId("OIXmGsWnKbOnCUzOkPiwFGMLuyfjJjGNhKFJ");
		entity.setCreatedBy("wrRyDOStAIqipWPmfzJqiBqEhYUGsPJBzYQu");
		entity.setCreatedIp("cibGPDjxLOurkoxkRQPTykyJgCOyxfGngeai");
		entity.setUpdatedUserId("JkErCLFEUeKChqYJDDEOlKrBtgqhygdflxIP");
		entity.setUpdatedBy("DJJIOTVVezMTjDvWMpTDACVXzTfeWQmhvenP");
		entity.setUpdatedIp("rlcKdWnyvOyWVrrfPlDeXNtiRgDovVCbWoov");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/BaseModule/Detail").param("id", entity.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$..angularUrl").value(hasItem("HJQVxeHHuGXQHGgbrKrVkysaZskvkQCkhyzP")))
				.andExpect(jsonPath("$..code").value(hasItem("VkCDZvITglIZfgwkNzdoEnjArKrzgecALXbY")))
				.andExpect(jsonPath("$..createdBy").value(hasItem("wrRyDOStAIqipWPmfzJqiBqEhYUGsPJBzYQu")))
				.andExpect(jsonPath("$..createdIp").value(hasItem("cibGPDjxLOurkoxkRQPTykyJgCOyxfGngeai")))
				.andExpect(jsonPath("$..createdUserId").value(hasItem("OIXmGsWnKbOnCUzOkPiwFGMLuyfjJjGNhKFJ")))
				.andExpect(jsonPath("$..formName").value(hasItem("tYdYuBHlRRqlDDgarCTIShQZjcOxEnEiFMpn")))
				.andExpect(jsonPath("$..parentId").value(hasItem("JPzCNeiASbmfqZfIAFaxCYjWQKwCcPGZrFkZ")))
				.andExpect(jsonPath("$..parentName").value(hasItem("PAzWSRHNaenMedotCPuyMfoplORkUETICDwq")))
				.andExpect(jsonPath("$..systemId").value(hasItem("qgzoQSkfmXFjuDFUnYCulPboNKtKeCJQTOGt")))
				.andExpect(jsonPath("$..title").value(hasItem("nWZxRmcMJKexEADDmfmQLsXfxnYdAiSJgUBN")))
				.andExpect(jsonPath("$..updatedBy").value(hasItem("DJJIOTVVezMTjDvWMpTDACVXzTfeWQmhvenP")))
				.andExpect(jsonPath("$..updatedIp").value(hasItem("rlcKdWnyvOyWVrrfPlDeXNtiRgDovVCbWoov")))
				.andExpect(jsonPath("$..updatedUserId").value(hasItem("JkErCLFEUeKChqYJDDEOlKrBtgqhygdflxIP")))
				.andExpect(jsonPath("$..vueUrl").value(hasItem("mEJaWyEBUKgceLseyrYiyWujAofSCIFfOwhb")));
	}

	@Test
	void testSave() throws Exception {
		int databaseSizeBeforeCreate = dao.findAll().size();
		SpringResource entity = new SpringResource();
		entity.setCode("MGzAOcxYHlQLfIxXzvLoWwAuLlVdBjihLmrB");
		entity.setTitle("BLBvgpqIeyqloyvUeJlmewnKrVvFbBldYwNu");
		entity.setVueUrl("XRpKbNSlgPSVWVtanURaXjifJRhrUUzOzFTb");
		entity.setAngularUrl("JlXcmJxSCdUncYmbVfcLMAqGAZIERbTJVoJq");
		entity.setParentId("BwnheZcsBdndFPXtzHrxjXXmWaPvRexFJGyg");
		entity.setParentName("JyNrnTaPKhcbzgruuJoihAxCXorHRMfTroeL");
		entity.setSystemId("lfCmzeiXpXuCHhdwoCwAWcRGkEfMveewPJpu");
		entity.setCreatedUserId("bBfTjNyhgmxxrfLSGKqVIRbeBbsjyxIySkue");
		entity.setCreatedBy("ruVJFeQxnRbWHKgNmHEzcRioVjJDKGvNkxcE");
		entity.setCreatedIp("MTGUvzDnlVYyXRjWiPcIkYytBRkdMUUNabKL");
		entity.setUpdatedUserId("JQbGlPfWAQCpQIZRsNkwCTmIVxpAnlnrSiVE");
		entity.setUpdatedBy("RbODKCrjxkMsIwNeQxofRCLnMXgKQgtEOoPy");
		entity.setUpdatedIp("NEOLQoUwtnhcDAjWZkWTNSZXudgvWIaybUmR");
		this.mvc.perform(post("/BaseModule/Create").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(entity))).andExpect(status().isOk());
		List<SpringResource> baseModuleEntityList = dao.findAll();
		assertThat(baseModuleEntityList).hasSize(databaseSizeBeforeCreate + 1);
		SpringResource testBaseModuleEntity = baseModuleEntityList.get(baseModuleEntityList.size() - 1);
		assertThat(testBaseModuleEntity.getAngularUrl()).isEqualTo("JlXcmJxSCdUncYmbVfcLMAqGAZIERbTJVoJq");
		assertThat(testBaseModuleEntity.getCode()).isEqualTo("MGzAOcxYHlQLfIxXzvLoWwAuLlVdBjihLmrB");
		assertThat(testBaseModuleEntity.getCreatedBy()).isEqualTo("ruVJFeQxnRbWHKgNmHEzcRioVjJDKGvNkxcE");
		assertThat(testBaseModuleEntity.getCreatedIp()).isEqualTo("MTGUvzDnlVYyXRjWiPcIkYytBRkdMUUNabKL");
		assertThat(testBaseModuleEntity.getCreatedUserId()).isEqualTo("bBfTjNyhgmxxrfLSGKqVIRbeBbsjyxIySkue");
		assertThat(testBaseModuleEntity.getParentId()).isEqualTo("BwnheZcsBdndFPXtzHrxjXXmWaPvRexFJGyg");
		assertThat(testBaseModuleEntity.getParentName()).isEqualTo("JyNrnTaPKhcbzgruuJoihAxCXorHRMfTroeL");
		assertThat(testBaseModuleEntity.getSystemId()).isEqualTo("lfCmzeiXpXuCHhdwoCwAWcRGkEfMveewPJpu");
		assertThat(testBaseModuleEntity.getTitle()).isEqualTo("BLBvgpqIeyqloyvUeJlmewnKrVvFbBldYwNu");
		assertThat(testBaseModuleEntity.getUpdatedBy()).isEqualTo("RbODKCrjxkMsIwNeQxofRCLnMXgKQgtEOoPy");
		assertThat(testBaseModuleEntity.getUpdatedIp()).isEqualTo("NEOLQoUwtnhcDAjWZkWTNSZXudgvWIaybUmR");
		assertThat(testBaseModuleEntity.getUpdatedUserId()).isEqualTo("JQbGlPfWAQCpQIZRsNkwCTmIVxpAnlnrSiVE");
		assertThat(testBaseModuleEntity.getVueUrl()).isEqualTo("XRpKbNSlgPSVWVtanURaXjifJRhrUUzOzFTb");
		;
	}

	@Test
	void testUpdate() throws Exception {
		SpringResource entity = new SpringResource();
		entity.setCode("ZryUnohMDluLAYGXqwzfBUcykFEdjiRSBfsu");
		entity.setTitle("LbyNozRckWdAlByCIRpPHtXGwSOGrkFTHrPe");
		entity.setVueUrl("sXeqdBGyGOlwemjVEQZGZtzuXPWgpwnsvhTg");
		entity.setAngularUrl("rBqwQYTSghtfSIWOBRSXrWkgRMPvbZPCjLjJ");
		entity.setParentId("ImzzsSdmhMZhUzvhOUGqNFqtSGVyvCxqlaDK");
		entity.setParentName("ggUNKvoEzRfuKqeEmVSHlVvnxgfQBteGoZCB");
		entity.setSystemId("YOoFlWABoYiJkluMPqYUHVWwUDYhkjjsOLwK");
		entity.setCreatedUserId("tKNcnPyPsvhvfAVKxQoLFVqaSSlXdFOkcwBv");
		entity.setCreatedBy("eReCQKgAbtwkTfAJtTEIqAbSKPgvSjoreHjL");
		entity.setCreatedIp("mUzxZlQAaLyzTcLTEUJWkpjTpnEOBlbWcema");
		entity.setUpdatedUserId("yBvysftRbwsrKrthTHyFBoUFSmWrMTGVLtDI");
		entity.setUpdatedBy("IyyZkwvxerUvTOTNyLPyFdJuMJzOIPJNVljO");
		entity.setUpdatedIp("WEIBcjvsXFkQYLZDxNgkRmoRYYseHCriJKOe");
		dao.saveAndFlush(entity);
		int databaseSizeBeforeUpdate = dao.findAll().size();
		SpringResource updatedEntity = dao.findById(entity.getId()).get();
		updatedEntity.setCode("JweNdsixaqBNNVihYyBqirastrTSUTXDYzyT");
		updatedEntity.setTitle("UKFeOHxkwQjfNLRMQzfhotsWlILrUzpwNfUQ");
		updatedEntity.setVueUrl("AGayTXhKzswiijqpWiKeFZjCPWVKkzkprmqe");
		updatedEntity.setAngularUrl("orlZsEGcfENNYWqVixEWfOwsMpQBSrgKcIGn");
		updatedEntity.setParentId("wnaHkQxdgViNHtPmPGJdzcwUYhwIZAruYAMl");
		updatedEntity.setParentName("nnvfjoRcTCgOdrJCJGmhUzQQporKShWXlpdE");
		updatedEntity.setSystemId("NNRqFXZXvMrdoocYHaGzuPCvovFVHJFkHNeE");
		updatedEntity.setCreatedUserId("EBbwUOktzXtrACyoOWuzefijUrUDzlFKDrts");
		updatedEntity.setCreatedBy("XwYLMiXcZQCCHEBRztJxmQMDcifIZAmNcjoo");
		updatedEntity.setCreatedIp("XENPvtaIttDWVUxQvJyydNFXrmjoazDaNbrf");
		updatedEntity.setUpdatedUserId("ANdDCVlZEWNkLVxbPdCXNAZoRYMDnKKRgFcN");
		updatedEntity.setUpdatedBy("HbUpwFocBdFDMIWodvXIirebRfvHwEktEAOX");
		updatedEntity.setUpdatedIp("OuHiUJcGRpCAtNRWwmpKrnRjPZsnYNiRtXJY");
		this.mvc.perform(post("/BaseModule/Edit").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(updatedEntity)))
				.andExpect(status().isOk());
		List<SpringResource> baseModuleEntityList = dao.findAll();
		assertThat(baseModuleEntityList).hasSize(databaseSizeBeforeUpdate);
		SpringResource testBaseModuleEntity = baseModuleEntityList.get(baseModuleEntityList.size() - 1);
		assertThat(testBaseModuleEntity.getAngularUrl()).isEqualTo("orlZsEGcfENNYWqVixEWfOwsMpQBSrgKcIGn");
		assertThat(testBaseModuleEntity.getCode()).isEqualTo("JweNdsixaqBNNVihYyBqirastrTSUTXDYzyT");
		assertThat(testBaseModuleEntity.getCreatedBy()).isEqualTo("XwYLMiXcZQCCHEBRztJxmQMDcifIZAmNcjoo");
		assertThat(testBaseModuleEntity.getCreatedIp()).isEqualTo("XENPvtaIttDWVUxQvJyydNFXrmjoazDaNbrf");
		assertThat(testBaseModuleEntity.getCreatedUserId()).isEqualTo("EBbwUOktzXtrACyoOWuzefijUrUDzlFKDrts");
		assertThat(testBaseModuleEntity.getParentId()).isEqualTo("wnaHkQxdgViNHtPmPGJdzcwUYhwIZAruYAMl");
		assertThat(testBaseModuleEntity.getParentName()).isEqualTo("nnvfjoRcTCgOdrJCJGmhUzQQporKShWXlpdE");
		assertThat(testBaseModuleEntity.getSystemId()).isEqualTo("NNRqFXZXvMrdoocYHaGzuPCvovFVHJFkHNeE");
		assertThat(testBaseModuleEntity.getTitle()).isEqualTo("UKFeOHxkwQjfNLRMQzfhotsWlILrUzpwNfUQ");
		assertThat(testBaseModuleEntity.getUpdatedBy()).isEqualTo("HbUpwFocBdFDMIWodvXIirebRfvHwEktEAOX");
		assertThat(testBaseModuleEntity.getUpdatedIp()).isEqualTo("OuHiUJcGRpCAtNRWwmpKrnRjPZsnYNiRtXJY");
		assertThat(testBaseModuleEntity.getUpdatedUserId()).isEqualTo("ANdDCVlZEWNkLVxbPdCXNAZoRYMDnKKRgFcN");
		assertThat(testBaseModuleEntity.getVueUrl()).isEqualTo("AGayTXhKzswiijqpWiKeFZjCPWVKkzkprmqe");
		;
	}

	@Test
	void testSetDeleted() throws Exception {
		SpringResource entity = new SpringResource();
		entity.setCode("ewIzFfmXAdFniCteiDwOBjCSJNtvBDSJDqtK");
		entity.setTitle("cwvmbEEqbrXYrtcEnTgkTSXGUdZSqJFSNiQL");
		entity.setVueUrl("ZxwsUjoCDdMcaXXunkSSwCACVfuBCOWIFrvM");
		entity.setAngularUrl("pGhPJbrElTonQwFVvHcUNrEMImqkdkwyUcOO");
		entity.setParentId("yvAwLlHKvnCEeoiuUhQyTheIVzrfJeSuWvsM");
		entity.setParentName("vqQthlyiJMZxtmkkvBoeVyyeiWSdRYfiIDPR");
		entity.setSystemId("VWbMwEIvGNuqzaqZJsgHlNEQJmpBGWegQRrt");
		entity.setCreatedUserId("IxylMhbedvSSruOfoukcMLWTJFLRiKoLUEZh");
		entity.setCreatedBy("LAlDruHQVsuqKAHWFXGUawKgDNqIPrwnqFUC");
		entity.setCreatedIp("IaHBwEjpIIBBKCuhvMLqywAMRMupZodlMVKN");
		entity.setUpdatedUserId("fddHBLbiPrJvxpJBXSisDQIhsXguIzrKGCvn");
		entity.setUpdatedBy("rQNzlxwhgVSqLCqgdGYtDWypTblIDjbNLSKg");
		entity.setUpdatedIp("NIBFwXOgoFrBilpkrIgsvTbmlfxTOuRAyUPW");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/BaseModule/SetDeleted").param("ids", entity.getId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk());
	}

}
