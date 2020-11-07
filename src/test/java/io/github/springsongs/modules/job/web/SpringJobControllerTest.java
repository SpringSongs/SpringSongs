package io.github.springsongs.modules.job.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
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

import io.github.springsongs.modules.job.domain.SpringJob;
import io.github.springsongs.modules.job.repo.SpringJobRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("Administrator")
@Transactional
class SpringJobControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private SpringJobRepo dao;

	@Autowired
	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void testGetPage() throws Exception {
		SpringJob entity = new SpringJob();
		entity.setGroupCode("QxJKoGcqYQWNLZAaDkTRMAenzesyUmrQXWRi");
		entity.setGroupTitle("IcTWFflFruInBohbdHuRrSFyOgwvhIUSmdhs");
		entity.setTaskTitle("fzbuMzYMueEJJfdgCZleMqDsakjLwxqlbKqn");
		entity.setTaskClassTitle("DYsxjqvXHYJLlpjNBwkBnmLILuNvsFfwFHAv");
		entity.setCronExpression("WjjvVzqMMuTqgqgSUnhKYVMmwCuaPrsJpRdA");
		entity.setRemark("HKNYAevANOkaSfuuQTTUBLhntNMaetlKIUfJ");
		entity.setCreatedUserId("XGDXLckLWGWCMLcBSeYTYZAHVAyUANNIRksI");
		entity.setCreatedBy("tIjByvUdxbNZCLCFVmhmPjUGngiDLzaxtHwT");
		entity.setCreatedIp("sYTWXhwxkijNhvaOoomFAxAtagOFSNIJhftW");
		entity.setUpdatedUserId("uUFptOLeJdseKWkTnAIFmMKbMQWFgFMYMVnD");
		entity.setUpdatedBy("IVSqJjOgSjibgzTVpKJLJmFlnYjpZJeurPjK");
		entity.setUpdatedIp("gQXezmxjFNFlizfwWXfYYfXjtyBBsxQlsHyf");
		this.mvc.perform(post("/SpringJob/ListByPage").param("page", "1").param("limit", "20")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(entity))).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.[*].createdBy").value(hasItem("tIjByvUdxbNZCLCFVmhmPjUGngiDLzaxtHwT")))
				.andExpect(jsonPath("$.data.[*].createdIp").value(hasItem("sYTWXhwxkijNhvaOoomFAxAtagOFSNIJhftW")))
				.andExpect(jsonPath("$.data.[*].createdUserId").value(hasItem("XGDXLckLWGWCMLcBSeYTYZAHVAyUANNIRksI")))
				.andExpect(jsonPath("$.data.[*].cronExpression").value(hasItem("WjjvVzqMMuTqgqgSUnhKYVMmwCuaPrsJpRdA")))
				.andExpect(jsonPath("$.data.[*].groupCode").value(hasItem("QxJKoGcqYQWNLZAaDkTRMAenzesyUmrQXWRi")))
				.andExpect(jsonPath("$.data.[*].groupTitle").value(hasItem("IcTWFflFruInBohbdHuRrSFyOgwvhIUSmdhs")))
				.andExpect(jsonPath("$.data.[*].remark").value(hasItem("HKNYAevANOkaSfuuQTTUBLhntNMaetlKIUfJ")))
				.andExpect(jsonPath("$.data.[*].taskClassTitle").value(hasItem("DYsxjqvXHYJLlpjNBwkBnmLILuNvsFfwFHAv")))
				.andExpect(jsonPath("$.data.[*].taskTitle").value(hasItem("fzbuMzYMueEJJfdgCZleMqDsakjLwxqlbKqn")))
				.andExpect(jsonPath("$.data.[*].updatedBy").value(hasItem("IVSqJjOgSjibgzTVpKJLJmFlnYjpZJeurPjK")))
				.andExpect(jsonPath("$.data.[*].updatedIp").value(hasItem("gQXezmxjFNFlizfwWXfYYfXjtyBBsxQlsHyf")))
				.andExpect(jsonPath("$.data.[*].updatedUserId").value(hasItem("uUFptOLeJdseKWkTnAIFmMKbMQWFgFMYMVnD")));
	}

	@Test
	void testGet() throws Exception {
		SpringJob entity = new SpringJob();
		entity.setGroupCode("mzIKdBYryYbmztoHkNufcvNZOODBeeTmAiwN");
		entity.setGroupTitle("HObqqhPoIHNRXYQZPBDKdukPnnjfpmcujHED");
		entity.setTaskTitle("TGSnWrHRDnKMiYWqiMHVJbJUeXNcFrAyLYLO");
		entity.setTaskClassTitle("BFjumJirwADFptyaTRFjqEpbXrVEVkCsxHff");
		entity.setCronExpression("DjaPaXSdzgBcXSjvfxBlkqyETzpyukdUoZtG");
		entity.setRemark("pCKgiDgorTTSInKqTbmNyOmznEmJACDKYNgd");
		entity.setCreatedUserId("uiTkUvKEZMuptomfpclVQxIMYWXEyAwaHWpZ");
		entity.setCreatedBy("zJMsVEpNtSHIJYxrUBhISwHbjzbGEaNGfkGN");
		entity.setCreatedIp("OWdePFmdLSyylkpbAfJVlePtHEokDPwlXCcQ");
		entity.setUpdatedUserId("lQAqojcTPKxwYyDJkVIPqKJSeognckgIpMED");
		entity.setUpdatedBy("aAjHAxydIJiQVMsnDqUjeZMhWTluqbVVQxhI");
		entity.setUpdatedIp("SvpgoknvNVSEbISUMuuEQScSjvJUHYPHPzdM");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringJob/Detail").param("id", entity.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$..createdBy").value(hasItem("zJMsVEpNtSHIJYxrUBhISwHbjzbGEaNGfkGN")))
				.andExpect(jsonPath("$..createdIp").value(hasItem("OWdePFmdLSyylkpbAfJVlePtHEokDPwlXCcQ")))
				.andExpect(jsonPath("$..createdUserId").value(hasItem("uiTkUvKEZMuptomfpclVQxIMYWXEyAwaHWpZ")))
				.andExpect(jsonPath("$..cronExpression").value(hasItem("DjaPaXSdzgBcXSjvfxBlkqyETzpyukdUoZtG")))
				.andExpect(jsonPath("$..groupCode").value(hasItem("mzIKdBYryYbmztoHkNufcvNZOODBeeTmAiwN")))
				.andExpect(jsonPath("$..groupTitle").value(hasItem("HObqqhPoIHNRXYQZPBDKdukPnnjfpmcujHED")))
				.andExpect(jsonPath("$..remark").value(hasItem("pCKgiDgorTTSInKqTbmNyOmznEmJACDKYNgd")))
				.andExpect(jsonPath("$..taskClassTitle").value(hasItem("BFjumJirwADFptyaTRFjqEpbXrVEVkCsxHff")))
				.andExpect(jsonPath("$..taskTitle").value(hasItem("TGSnWrHRDnKMiYWqiMHVJbJUeXNcFrAyLYLO")))
				.andExpect(jsonPath("$..updatedBy").value(hasItem("aAjHAxydIJiQVMsnDqUjeZMhWTluqbVVQxhI")))
				.andExpect(jsonPath("$..updatedIp").value(hasItem("SvpgoknvNVSEbISUMuuEQScSjvJUHYPHPzdM")))
				.andExpect(jsonPath("$..updatedUserId").value(hasItem("lQAqojcTPKxwYyDJkVIPqKJSeognckgIpMED")));
	}

	@Test
	void testSave() throws Exception {
		int databaseSizeBeforeCreate = dao.findAll().size();
		SpringJob entity = new SpringJob();
		entity.setGroupCode("sBFuIcoWWqlbjtELqotLoHuzKgxUnpBBqIYh");
		entity.setGroupTitle("gHcYirLdyoEKSJNgORoOmuxCmOXlgxGrRtCC");
		entity.setTaskTitle("JQErgMkeEUwuffbfgZTsYLeEbxCmDYrXHzsM");
		entity.setTaskClassTitle("jABflNDrgScCemAjKMOJRXLanNeQlRIDOXeC");
		entity.setCronExpression("FDyBtHEcTQpODmcshdxNmGGjrBwWuqbqRaTW");
		entity.setRemark("cqYTkFHQgEjzEXzWhQYFhEaJyBvWuVVmxBFN");
		entity.setCreatedUserId("QwhCFEBFIDGCgRBQRjimdtjAHJEtBZARcRmi");
		entity.setCreatedBy("zGjfASEIumOqkJAdcxyBJRdeUKLuGGufNEMf");
		entity.setCreatedIp("jyzHgPuyZIRSDpCxLzSNexUVdtHgHZUvKHaL");
		entity.setUpdatedUserId("BnENSGOPnFjADaEDYqDWqGUCCurdZULwVoxR");
		entity.setUpdatedBy("gEGbTiQIaxzUfXZRAPsBWLoJGNWNHxXxzllA");
		entity.setUpdatedIp("bgcXUcgoRlcItLxDLrUPtndCAspjeeQoWfme");
		this.mvc.perform(post("/SpringJob/Create").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(entity))).andExpect(status().isOk());
		List<SpringJob> SpringJobList = dao.findAll();
		assertThat(SpringJobList).hasSize(databaseSizeBeforeCreate + 1);
		SpringJob testSpringJob = SpringJobList.get(SpringJobList.size() - 1);
		assertThat(testSpringJob.getCreatedBy()).isEqualTo("zGjfASEIumOqkJAdcxyBJRdeUKLuGGufNEMf");
		assertThat(testSpringJob.getCreatedIp()).isEqualTo("jyzHgPuyZIRSDpCxLzSNexUVdtHgHZUvKHaL");
		assertThat(testSpringJob.getCreatedUserId()).isEqualTo("QwhCFEBFIDGCgRBQRjimdtjAHJEtBZARcRmi");
		assertThat(testSpringJob.getCronExpression()).isEqualTo("FDyBtHEcTQpODmcshdxNmGGjrBwWuqbqRaTW");
		assertThat(testSpringJob.getGroupCode()).isEqualTo("sBFuIcoWWqlbjtELqotLoHuzKgxUnpBBqIYh");
		assertThat(testSpringJob.getGroupTitle()).isEqualTo("gHcYirLdyoEKSJNgORoOmuxCmOXlgxGrRtCC");
		assertThat(testSpringJob.getRemark()).isEqualTo("cqYTkFHQgEjzEXzWhQYFhEaJyBvWuVVmxBFN");
		assertThat(testSpringJob.getTaskClassTitle()).isEqualTo("jABflNDrgScCemAjKMOJRXLanNeQlRIDOXeC");
		assertThat(testSpringJob.getTaskTitle()).isEqualTo("JQErgMkeEUwuffbfgZTsYLeEbxCmDYrXHzsM");
		assertThat(testSpringJob.getUpdatedBy()).isEqualTo("gEGbTiQIaxzUfXZRAPsBWLoJGNWNHxXxzllA");
		assertThat(testSpringJob.getUpdatedIp()).isEqualTo("bgcXUcgoRlcItLxDLrUPtndCAspjeeQoWfme");
		assertThat(testSpringJob.getUpdatedUserId()).isEqualTo("BnENSGOPnFjADaEDYqDWqGUCCurdZULwVoxR");
		;
	}

	@Test
	void testUpdate() throws Exception {
		SpringJob entity = new SpringJob();
		entity.setGroupCode("APkIptvpoodnnpqcyCqBkkcyYbGoVxzPVYIb");
		entity.setGroupTitle("zJreBmiElzyDIDjZIzfWJnFVTNJNpwmcjvjO");
		entity.setTaskTitle("bLKTwyLSHLxdChPcCMEMfzPVbjUnBOzUIEtG");
		entity.setTaskClassTitle("ErPeSaiTnznpjfVYuoRBQCaZVSRymrGhgIpW");
		entity.setCronExpression("pYlFeRoPhyIlQJORUmFSQNknIuhpPrwqaGuJ");
		entity.setRemark("GkzZfxCYRcraQrokBgzQHsaRPXwWJSnXtOPq");
		entity.setCreatedUserId("UFTEDqDtOXrDnNuJgOqeDOdzbvpAKvFmjhiq");
		entity.setCreatedBy("QhqVqUIygSsjHaWjUtVBPJmJqzmLyvgpwZvs");
		entity.setCreatedIp("TCLMyUPTcIdcaYTOMRgovdRDfxfrzxfChlcP");
		entity.setUpdatedUserId("jtGQmGCHqxuxPdOfGiHaHWpduDLOfOoOTTJF");
		entity.setUpdatedBy("orkJzULoBLVdbZccMUCCiIsAroymzzagWSHH");
		entity.setUpdatedIp("uAhphHjTDuvkMisGhLwPWPftIChWsruzCamb");
		dao.saveAndFlush(entity);
		int databaseSizeBeforeUpdate = dao.findAll().size();
		SpringJob updatedEntity = dao.findById(entity.getId()).get();
		updatedEntity.setGroupCode("rVpetkMhTkfdqipgnKNfZwdeKKfTOqzpmSyf");
		updatedEntity.setGroupTitle("mEiWzGjSGjBarScdxEehCVUdUEPDOpqrbDzb");
		updatedEntity.setTaskTitle("VXFmwRqKOQSjGlCsKhACyBnDWVqdZmOybnxd");
		updatedEntity.setTaskClassTitle("vbITQXqztnYRHFHVCdRDRtfthOVNBDqFYMKq");
		updatedEntity.setCronExpression("BqQJycMFRQOeCLUfavsjnfKTfIsJeIbflEgs");
		updatedEntity.setRemark("LnVWjiRpoCtBqFVhQtjYCISWaizrMdpPrqhP");
		updatedEntity.setCreatedUserId("eMFUEfQpPpBoDuUrQZeEowWkGbwTdzbTwOKg");
		updatedEntity.setCreatedBy("VnYaLrlzwgKtatQUCEHEWiHghZNPArxbxQuN");
		updatedEntity.setCreatedIp("TPJFGIHmLiDIVTbvXlZKnKKLWxaFsLQWQwzI");
		updatedEntity.setUpdatedUserId("IkXmlwpbKnvcWqKPTbRJNVAKIeZTTedKbJUa");
		updatedEntity.setUpdatedBy("keVufWzJCXgINkiolbWfOEqUXbYcqlECORZq");
		updatedEntity.setUpdatedIp("PkFczEEORcEYJsCGZwnauAPRpOScKcnfBjgn");
		this.mvc.perform(post("/SpringJob/Edit").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(updatedEntity)))
				.andExpect(status().isOk());
		List<SpringJob> SpringJobList = dao.findAll();
		assertThat(SpringJobList).hasSize(databaseSizeBeforeUpdate);
		SpringJob testSpringJob = SpringJobList.get(SpringJobList.size() - 1);
		assertThat(testSpringJob.getCreatedBy()).isEqualTo("VnYaLrlzwgKtatQUCEHEWiHghZNPArxbxQuN");
		assertThat(testSpringJob.getCreatedIp()).isEqualTo("TPJFGIHmLiDIVTbvXlZKnKKLWxaFsLQWQwzI");
		assertThat(testSpringJob.getCreatedUserId()).isEqualTo("eMFUEfQpPpBoDuUrQZeEowWkGbwTdzbTwOKg");
		assertThat(testSpringJob.getCronExpression()).isEqualTo("BqQJycMFRQOeCLUfavsjnfKTfIsJeIbflEgs");
		assertThat(testSpringJob.getGroupCode()).isEqualTo("rVpetkMhTkfdqipgnKNfZwdeKKfTOqzpmSyf");
		assertThat(testSpringJob.getGroupTitle()).isEqualTo("mEiWzGjSGjBarScdxEehCVUdUEPDOpqrbDzb");
		assertThat(testSpringJob.getRemark()).isEqualTo("LnVWjiRpoCtBqFVhQtjYCISWaizrMdpPrqhP");
		assertThat(testSpringJob.getTaskClassTitle()).isEqualTo("vbITQXqztnYRHFHVCdRDRtfthOVNBDqFYMKq");
		assertThat(testSpringJob.getTaskTitle()).isEqualTo("VXFmwRqKOQSjGlCsKhACyBnDWVqdZmOybnxd");
		assertThat(testSpringJob.getUpdatedBy()).isEqualTo("keVufWzJCXgINkiolbWfOEqUXbYcqlECORZq");
		assertThat(testSpringJob.getUpdatedIp()).isEqualTo("PkFczEEORcEYJsCGZwnauAPRpOScKcnfBjgn");
		assertThat(testSpringJob.getUpdatedUserId()).isEqualTo("IkXmlwpbKnvcWqKPTbRJNVAKIeZTTedKbJUa");
		;
	}

	@Test
	void testSetDeleted() throws Exception {
		SpringJob entity = new SpringJob();
		entity.setGroupCode("OCPxNyZAiQBFlwTLZVsgRwIxrlLQevDoVuRI");
		entity.setGroupTitle("kZQaVbpsaMVivgrTOtcVPFPsGYFMozqJwiMq");
		entity.setTaskTitle("AnlUNAIpeeYUfDdkdkssUMRmKACNLaXgFTUa");
		entity.setTaskClassTitle("TazMcMtSlOCuGobAyUvsGYxNdUvDtBpuvUSB");
		entity.setCronExpression("MmvFyYauzfjttefiBZMIZDWmIJzXfmWDpFGC");
		entity.setRemark("MxfxMpFKBmJngyBABDIQQfsRYIcFOOcBIjGy");
		entity.setCreatedUserId("qSvMkPLHfMYJFbIVkpObMfrHjGPFuPMDVGiv");
		entity.setCreatedBy("bAcHSglIjHmpQUjQYpHdNLLCCDEZmxaLYUay");
		entity.setCreatedIp("fSAKmhixjwRahNZmtwvHViVdJQOxqApWLiFh");
		entity.setUpdatedUserId("qZcAWqkCMiReZNAZqZwnXzNIBjtcNUWOvjyZ");
		entity.setUpdatedBy("MLnygOYEtDRZEGFbRcOziGQHRFzNycwXXWTL");
		entity.setUpdatedIp("TGQdSzNpkULibtmIoYTzPanPUkXOjqqbZyPw");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringJob/SetDeleted").param("ids", entity.getId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk());
	}

}
