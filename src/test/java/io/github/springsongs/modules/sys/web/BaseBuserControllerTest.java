package io.github.springsongs.modules.sys.web;

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

import io.github.springsongs.modules.sys.domain.SpringUser;
import io.github.springsongs.modules.sys.repo.SpringUserRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class BaseBuserControllerTest {
	@Autowired
	private WebApplicationContext context;

	@Autowired
	private SpringUserRepo dao;

	// @Autowired
	private MockMvc mvc;

	@BeforeEach
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void testGetPage() throws Exception {
		SpringUser entity = new SpringUser();
		entity.setUserName("hpQtyVBgyqTzwTHiGpbfswdRcjwPjXItXtJk");
		entity.setTrueName("fqzBvVNHUPuUHodRbpwnOUmwgncghzofJsKw");
		entity.setCreatedUserId("KFfglmzShGfcFAWJKnbnzkvOWeskMnHMnmhQ");
		entity.setCreatedBy("abLZGuRCAKuglBtzQhnZUtpTQGdSDlvnBKSF");
		entity.setCreatedIp("WrhreMxNAEGKXOtQwUUSrWMkocdQsewEUfYm");
		entity.setUpdatedUserId("pVeLIvTsRtOLMnlpKYguhIbHWLjicdgdCfZa");
		entity.setUpdatedBy("vHztwFZmpPkXDnvofpHGsvJMWWwrnYpXnBEU");
		entity.setUpdatedIp("jBmqMImNIsbKuqDsmukWJQiFBhrayPQuPBtf");
		this.mvc.perform(post("/BaseBuser/ListByPage").param("page", "1").param("limit", "20")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(entity))).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.[*].createdBy").value(hasItem("abLZGuRCAKuglBtzQhnZUtpTQGdSDlvnBKSF")))
				.andExpect(jsonPath("$.data.[*].createdIp").value(hasItem("WrhreMxNAEGKXOtQwUUSrWMkocdQsewEUfYm")))
				.andExpect(jsonPath("$.data.[*].createdUserId").value(hasItem("KFfglmzShGfcFAWJKnbnzkvOWeskMnHMnmhQ")))
				.andExpect(jsonPath("$.data.[*].truename").value(hasItem("fqzBvVNHUPuUHodRbpwnOUmwgncghzofJsKw")))
				.andExpect(jsonPath("$.data.[*].updatedBy").value(hasItem("vHztwFZmpPkXDnvofpHGsvJMWWwrnYpXnBEU")))
				.andExpect(jsonPath("$.data.[*].updatedIp").value(hasItem("jBmqMImNIsbKuqDsmukWJQiFBhrayPQuPBtf")))
				.andExpect(jsonPath("$.data.[*].updatedUserId").value(hasItem("pVeLIvTsRtOLMnlpKYguhIbHWLjicdgdCfZa")))
				.andExpect(jsonPath("$.data.[*].username").value(hasItem("hpQtyVBgyqTzwTHiGpbfswdRcjwPjXItXtJk")));
	}

	@Test
	void testGet() throws Exception {
		SpringUser entity = new SpringUser();
		entity.setUserName("OzesWRGgbviALmtsCcaxSOeWonUqfzAuVcQi");
		entity.setTrueName("YWiNuRDoUimdmmGxlKhxDkZPZhWaHMrFWclT");
		entity.setCreatedUserId("DMXqkhKiLEEcGRdecMbsjxzrLbyrJbrPTAAQ");
		entity.setCreatedBy("OHHyHCtZBsuygmqdCdMafWiNRPAVThqsgDav");
		entity.setCreatedIp("hFpiMxNvvjJBXZTSgLkgLHtZdwTPfAqBaOXv");
		entity.setUpdatedUserId("aeQeljYCRELNpfCwTGzyygEmRNzpPNvpLhLq");
		entity.setUpdatedBy("LOHufgMQKFjKCQWWnoNHNxTMlDvfLzEvttuv");
		entity.setUpdatedIp("DiBHHSzidiCFjfuCfnVsYTsJYZmHJYjaXQMc");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/BaseBuser/Detail").param("id", entity.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$..createdBy").value(hasItem("OHHyHCtZBsuygmqdCdMafWiNRPAVThqsgDav")))
				.andExpect(jsonPath("$..createdIp").value(hasItem("hFpiMxNvvjJBXZTSgLkgLHtZdwTPfAqBaOXv")))
				.andExpect(jsonPath("$..createdUserId").value(hasItem("DMXqkhKiLEEcGRdecMbsjxzrLbyrJbrPTAAQ")))
				.andExpect(jsonPath("$..truename").value(hasItem("YWiNuRDoUimdmmGxlKhxDkZPZhWaHMrFWclT")))
				.andExpect(jsonPath("$..updatedBy").value(hasItem("LOHufgMQKFjKCQWWnoNHNxTMlDvfLzEvttuv")))
				.andExpect(jsonPath("$..updatedIp").value(hasItem("DiBHHSzidiCFjfuCfnVsYTsJYZmHJYjaXQMc")))
				.andExpect(jsonPath("$..updatedUserId").value(hasItem("aeQeljYCRELNpfCwTGzyygEmRNzpPNvpLhLq")))
				.andExpect(jsonPath("$..username").value(hasItem("OzesWRGgbviALmtsCcaxSOeWonUqfzAuVcQi")));
	}

	@Test
	void testSave() throws Exception {
		int databaseSizeBeforeCreate = dao.findAll().size();
		SpringUser entity = new SpringUser();
		entity.setUserName("okkcBpmdcJIBfaknbqrxkzqhpzDhNcUQfnDN");
		entity.setTrueName("KflkmVtCfcgJskcXYgzGNBTwVqQJuIIFSNon");
		entity.setCreatedUserId("jYDlZQvvbBonbeHwubnTyFQYfFceYEkoFXCK");
		entity.setCreatedBy("gusAnYWpyTadKCuiJpicrwJIGPAAmxlHfBgY");
		entity.setCreatedIp("NnDZiRgAkRGYorzhDTcoxzAPhimxLHdmQBxa");
		entity.setUpdatedUserId("CagTcSKargeSvBEspDzKbUyrmHrBeDoQwPsr");
		entity.setUpdatedBy("PoLaERVfeqANpvAGNIhCjHvlMsaivtgZMAtk");
		entity.setUpdatedIp("GlqsEYEhioUwWrzesnKIZwUXmaRedwrWtWzC");
		this.mvc.perform(post("/BaseBuser/Create").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(entity))).andExpect(status().isOk());
		List<SpringUser> baseBuserEntityList = dao.findAll();
		assertThat(baseBuserEntityList).hasSize(databaseSizeBeforeCreate + 1);
		SpringUser testBaseBuserEntity = baseBuserEntityList.get(baseBuserEntityList.size() - 1);
		assertThat(testBaseBuserEntity.getCreatedBy()).isEqualTo("gusAnYWpyTadKCuiJpicrwJIGPAAmxlHfBgY");
		assertThat(testBaseBuserEntity.getCreatedIp()).isEqualTo("NnDZiRgAkRGYorzhDTcoxzAPhimxLHdmQBxa");
		assertThat(testBaseBuserEntity.getCreatedUserId()).isEqualTo("jYDlZQvvbBonbeHwubnTyFQYfFceYEkoFXCK");
		assertThat(testBaseBuserEntity.getTrueName()).isEqualTo("KflkmVtCfcgJskcXYgzGNBTwVqQJuIIFSNon");
		assertThat(testBaseBuserEntity.getUpdatedBy()).isEqualTo("PoLaERVfeqANpvAGNIhCjHvlMsaivtgZMAtk");
		assertThat(testBaseBuserEntity.getUpdatedIp()).isEqualTo("GlqsEYEhioUwWrzesnKIZwUXmaRedwrWtWzC");
		assertThat(testBaseBuserEntity.getUpdatedUserId()).isEqualTo("CagTcSKargeSvBEspDzKbUyrmHrBeDoQwPsr");
		assertThat(testBaseBuserEntity.getUserName()).isEqualTo("okkcBpmdcJIBfaknbqrxkzqhpzDhNcUQfnDN");
		;
	}

	@Test
	void testUpdate() throws Exception {
		SpringUser entity = new SpringUser();
		entity.setUserName("EyBMKBlxnbPmAxUGzucRhqPUpcsMjOBAFXrQ");
		entity.setTrueName("MhUGlapoSGaXkmGgVyXtRsOldNWjbWXEqQmI");
		entity.setCreatedUserId("atFDgDnTfIDGkZyKOdhYfziUVbhVIsoRiFLr");
		entity.setCreatedBy("asAppPhVWKKoXXVOTcXBmcfcnjIdfeCcQTUA");
		entity.setCreatedIp("TlPsQUxIDGONmeGSDHkFRnbdtAPTjUbmzbOt");
		entity.setUpdatedUserId("apFDPjIZAueTWnVYPCthLhOtTlwgjrqUchmp");
		entity.setUpdatedBy("QRunFRqryHCIsgehdUDzOwEYoxvtPvVzatPP");
		entity.setUpdatedIp("dZYVmFJGvSCqzDIEdUcYaqDGcbSsSzFBwxQZ");
		dao.saveAndFlush(entity);
		int databaseSizeBeforeUpdate = dao.findAll().size();
		SpringUser updatedEntity = dao.findById(entity.getId()).get();
		updatedEntity.setUserName("qjOaCdemUCyVMqhPWBwmPgpuTRfsbIJHVGZu");
		updatedEntity.setTrueName("ZbVAoxTfhWwIoKBnxyWXqiEZvDHJEBbOyUWt");
		updatedEntity.setCreatedUserId("uVtjLKRxSbmwfXIOEbqOtKIPJeKozaOXLDcq");
		updatedEntity.setCreatedBy("CvQJOFogArZaDQHjjchctEyooYCnDHLSrELO");
		updatedEntity.setCreatedIp("pfOyFpMsuXZbDkotIImuHWwQSwbPZfXfhgVN");
		updatedEntity.setUpdatedUserId("zVgUTOtdYqLhbbyHgwGxIoDzDaMnzjsZoKoj");
		updatedEntity.setUpdatedBy("xeXRjiVnpsahfJJWSskbbLcdQSmuXNPhubBA");
		updatedEntity.setUpdatedIp("moiNPAIxYYFEREFYWrUFTdAjpsNfLSVPFWCg");
		this.mvc.perform(post("/BaseBuser/Edit").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(updatedEntity)))
				.andExpect(status().isOk());
		List<SpringUser> baseBuserEntityList = dao.findAll();
		assertThat(baseBuserEntityList).hasSize(databaseSizeBeforeUpdate);
		SpringUser testBaseBuserEntity = baseBuserEntityList.get(baseBuserEntityList.size() - 1);
		assertThat(testBaseBuserEntity.getCreatedBy()).isEqualTo("CvQJOFogArZaDQHjjchctEyooYCnDHLSrELO");
		assertThat(testBaseBuserEntity.getCreatedIp()).isEqualTo("pfOyFpMsuXZbDkotIImuHWwQSwbPZfXfhgVN");
		assertThat(testBaseBuserEntity.getCreatedUserId()).isEqualTo("uVtjLKRxSbmwfXIOEbqOtKIPJeKozaOXLDcq");
		assertThat(testBaseBuserEntity.getTrueName()).isEqualTo("ZbVAoxTfhWwIoKBnxyWXqiEZvDHJEBbOyUWt");
		assertThat(testBaseBuserEntity.getUpdatedBy()).isEqualTo("xeXRjiVnpsahfJJWSskbbLcdQSmuXNPhubBA");
		assertThat(testBaseBuserEntity.getUpdatedIp()).isEqualTo("moiNPAIxYYFEREFYWrUFTdAjpsNfLSVPFWCg");
		assertThat(testBaseBuserEntity.getUpdatedUserId()).isEqualTo("zVgUTOtdYqLhbbyHgwGxIoDzDaMnzjsZoKoj");
		assertThat(testBaseBuserEntity.getUserName()).isEqualTo("qjOaCdemUCyVMqhPWBwmPgpuTRfsbIJHVGZu");
		;
	}

	@Test
	void testSetDeleted() throws Exception {
		SpringUser entity = new SpringUser();
		entity.setUserName("fhpwWxSMWHFEIxKFnipHjFFLfsYcvmdPRrDp");
		entity.setTrueName("AIaaUVcLzjQBRBvfqqKtGVSPCHJvANmecYGH");
		entity.setCreatedUserId("HPVSZDdRAWjNBhZfquzQZrQBLRBFLxHGVNUj");
		entity.setCreatedBy("UsTKbvzpOIyZObsPoBqluDozJFBVVbyyhmIP");
		entity.setCreatedIp("RBClPwyzXHiesbvnVTXpHsjahhfAKMczOGrN");
		entity.setUpdatedUserId("XArPfNBhEbeLtqupYxGlTzcqmTumrtTIqQug");
		entity.setUpdatedBy("qnDMqFAOAtkXyMngHFLrUrUbrsknyRUDUDck");
		entity.setUpdatedIp("IwIZXujDHlWzCAjdiMuTmglapNKIsnwCyNpC");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/BaseBuser/SetDeleted").param("ids", entity.getId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk());
	}

}