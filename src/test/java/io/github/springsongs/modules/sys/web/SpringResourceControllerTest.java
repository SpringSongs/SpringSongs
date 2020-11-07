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

import io.github.springsongs.modules.sys.domain.SpringResource;
import io.github.springsongs.modules.sys.repo.SpringResourceRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithUserDetails("Administrator")
class SpringResourceControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private SpringResourceRepo dao;

	@Autowired
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
		this.mvc.perform(post("/SpringResource/ListByPage").with(csrf().useInvalidToken())
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
		this.mvc.perform(post("/SpringResource/Detail").param("id", entity.getId())).andExpect(status().isOk())
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
		this.mvc.perform(post("/SpringResource/Create").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(entity))).andExpect(status().isOk());
		List<SpringResource> SpringResourceEntityList = dao.findAll();
		assertThat(SpringResourceEntityList).hasSize(databaseSizeBeforeCreate + 1);
		SpringResource testSpringResourceEntity = SpringResourceEntityList.get(SpringResourceEntityList.size() - 1);
		assertThat(testSpringResourceEntity.getAngularUrl()).isEqualTo("JlXcmJxSCdUncYmbVfcLMAqGAZIERbTJVoJq");
		assertThat(testSpringResourceEntity.getCode()).isEqualTo("MGzAOcxYHlQLfIxXzvLoWwAuLlVdBjihLmrB");
		assertThat(testSpringResourceEntity.getCreatedBy()).isEqualTo("ruVJFeQxnRbWHKgNmHEzcRioVjJDKGvNkxcE");
		assertThat(testSpringResourceEntity.getCreatedIp()).isEqualTo("MTGUvzDnlVYyXRjWiPcIkYytBRkdMUUNabKL");
		assertThat(testSpringResourceEntity.getCreatedUserId()).isEqualTo("bBfTjNyhgmxxrfLSGKqVIRbeBbsjyxIySkue");
		assertThat(testSpringResourceEntity.getParentId()).isEqualTo("BwnheZcsBdndFPXtzHrxjXXmWaPvRexFJGyg");
		assertThat(testSpringResourceEntity.getParentName()).isEqualTo("JyNrnTaPKhcbzgruuJoihAxCXorHRMfTroeL");
		assertThat(testSpringResourceEntity.getSystemId()).isEqualTo("lfCmzeiXpXuCHhdwoCwAWcRGkEfMveewPJpu");
		assertThat(testSpringResourceEntity.getTitle()).isEqualTo("BLBvgpqIeyqloyvUeJlmewnKrVvFbBldYwNu");
		assertThat(testSpringResourceEntity.getUpdatedBy()).isEqualTo("RbODKCrjxkMsIwNeQxofRCLnMXgKQgtEOoPy");
		assertThat(testSpringResourceEntity.getUpdatedIp()).isEqualTo("NEOLQoUwtnhcDAjWZkWTNSZXudgvWIaybUmR");
		assertThat(testSpringResourceEntity.getUpdatedUserId()).isEqualTo("JQbGlPfWAQCpQIZRsNkwCTmIVxpAnlnrSiVE");
		assertThat(testSpringResourceEntity.getVueUrl()).isEqualTo("XRpKbNSlgPSVWVtanURaXjifJRhrUUzOzFTb");
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
		this.mvc.perform(post("/SpringResource/Edit").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(updatedEntity)))
				.andExpect(status().isOk());
		List<SpringResource> SpringResourceEntityList = dao.findAll();
		assertThat(SpringResourceEntityList).hasSize(databaseSizeBeforeUpdate);
		SpringResource testSpringResourceEntity = SpringResourceEntityList.get(SpringResourceEntityList.size() - 1);
		assertThat(testSpringResourceEntity.getAngularUrl()).isEqualTo("orlZsEGcfENNYWqVixEWfOwsMpQBSrgKcIGn");
		assertThat(testSpringResourceEntity.getCode()).isEqualTo("JweNdsixaqBNNVihYyBqirastrTSUTXDYzyT");
		assertThat(testSpringResourceEntity.getCreatedBy()).isEqualTo("XwYLMiXcZQCCHEBRztJxmQMDcifIZAmNcjoo");
		assertThat(testSpringResourceEntity.getCreatedIp()).isEqualTo("XENPvtaIttDWVUxQvJyydNFXrmjoazDaNbrf");
		assertThat(testSpringResourceEntity.getCreatedUserId()).isEqualTo("EBbwUOktzXtrACyoOWuzefijUrUDzlFKDrts");
		assertThat(testSpringResourceEntity.getParentId()).isEqualTo("wnaHkQxdgViNHtPmPGJdzcwUYhwIZAruYAMl");
		assertThat(testSpringResourceEntity.getParentName()).isEqualTo("nnvfjoRcTCgOdrJCJGmhUzQQporKShWXlpdE");
		assertThat(testSpringResourceEntity.getSystemId()).isEqualTo("NNRqFXZXvMrdoocYHaGzuPCvovFVHJFkHNeE");
		assertThat(testSpringResourceEntity.getTitle()).isEqualTo("UKFeOHxkwQjfNLRMQzfhotsWlILrUzpwNfUQ");
		assertThat(testSpringResourceEntity.getUpdatedBy()).isEqualTo("HbUpwFocBdFDMIWodvXIirebRfvHwEktEAOX");
		assertThat(testSpringResourceEntity.getUpdatedIp()).isEqualTo("OuHiUJcGRpCAtNRWwmpKrnRjPZsnYNiRtXJY");
		assertThat(testSpringResourceEntity.getUpdatedUserId()).isEqualTo("ANdDCVlZEWNkLVxbPdCXNAZoRYMDnKKRgFcN");
		assertThat(testSpringResourceEntity.getVueUrl()).isEqualTo("AGayTXhKzswiijqpWiKeFZjCPWVKkzkprmqe");
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
		this.mvc.perform(post("/SpringResource/SetDeleted").param("ids", entity.getId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk());
	}

}
