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

import io.github.springsongs.modules.sys.domain.SpringAttachmentCategory;
import io.github.springsongs.modules.sys.repo.SpringAttachmentCategoryRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithUserDetails("Administrator")
class SpringAttachmentCategoryControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private SpringAttachmentCategoryRepo dao;

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void testGetPage() throws Exception {
		SpringAttachmentCategory entity = new SpringAttachmentCategory();
		entity.setTitle("LlCmcIstsMnnzBHIBlsDxYbmdGhQPcWcATKz");
		entity.setDescription("EdwKhNtxmbgMPAGwqAazdFUOoRBXSMWRvHPU");
		entity.setDictionaryCode("mPQtqOoYPdACvqAHVqCcvOnGEumKQosvAkBw");
		entity.setDictionaryName("uZOYKKwdDFtHuXeWSoOvPdTTKDaOBGSZBsEu");
		entity.setCreatedUserId("TUKjUmglrXVPPjrKIGczVOlQHByZpGYIxwGt");
		entity.setCreatedBy("okgTFXijuKTrZKIhYEIhBJPQdktGqZlaEQWV");
		entity.setCreatedIp("RwDJSLXrwJfkjFIYIJKKjTNdcTFLxfIyshoS");
		entity.setUpdatedUserId("kizhuLzTGJUezhIopTvsczKBjmxTCYrygeUP");
		entity.setUpdatedBy("yIzZysYnJpHWrdZkDVuDLltSvokROGixHnZi");
		entity.setUpdatedIp("NVnoOZCJEGgzHyELJjCaEATfvHHYXgfToISP");
		this.mvc.perform(post("/SpringAttachmentCategory/ListByPage").param("page", "1").param("limit", "20")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(entity))).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.[*].createdBy").value(hasItem("okgTFXijuKTrZKIhYEIhBJPQdktGqZlaEQWV")))
				.andExpect(jsonPath("$.data.[*].createdIp").value(hasItem("RwDJSLXrwJfkjFIYIJKKjTNdcTFLxfIyshoS")))
				.andExpect(jsonPath("$.data.[*].createdUserId").value(hasItem("TUKjUmglrXVPPjrKIGczVOlQHByZpGYIxwGt")))
				.andExpect(jsonPath("$.data.[*].desc").value(hasItem("EdwKhNtxmbgMPAGwqAazdFUOoRBXSMWRvHPU")))
				.andExpect(jsonPath("$.data.[*].dictionaryCode").value(hasItem("mPQtqOoYPdACvqAHVqCcvOnGEumKQosvAkBw")))
				.andExpect(jsonPath("$.data.[*].dictionaryName").value(hasItem("uZOYKKwdDFtHuXeWSoOvPdTTKDaOBGSZBsEu")))
				.andExpect(jsonPath("$.data.[*].title").value(hasItem("LlCmcIstsMnnzBHIBlsDxYbmdGhQPcWcATKz")))
				.andExpect(jsonPath("$.data.[*].updatedBy").value(hasItem("yIzZysYnJpHWrdZkDVuDLltSvokROGixHnZi")))
				.andExpect(jsonPath("$.data.[*].updatedIp").value(hasItem("NVnoOZCJEGgzHyELJjCaEATfvHHYXgfToISP")))
				.andExpect(jsonPath("$.data.[*].updatedUserId").value(hasItem("kizhuLzTGJUezhIopTvsczKBjmxTCYrygeUP")));
	}

	@Test
	void testGet() throws Exception {
		SpringAttachmentCategory entity = new SpringAttachmentCategory();
		entity.setTitle("KGQYneclkuOPFYGbJzMvdKibDRooTgOXVrjB");
		entity.setDescription("UMsPmMzoYVdtvcqzGGkEXLSJcGLMaYWVAMwR");
		entity.setDictionaryCode("LfBftIufUrfQBVggRNKMAWtFIKDCYAgfCkyo");
		entity.setDictionaryName("kLFewAOpLqoxMWxmskNUEgdFBrztBqdUAdBO");
		entity.setCreatedUserId("SKwPBxQvypqQeHVAKxkjHtyeDqBEStoXHUuN");
		entity.setCreatedBy("QiMSwPGoljprEUUGuPiEBWibEAnbGWjCcRDb");
		entity.setCreatedIp("llHGWtEDvJxBkUQdipBPtOYpNAiAeBOPNuDf");
		entity.setUpdatedUserId("KmTmmwBWgMuNBkGcqsseGiDweVWeHfZEYMtR");
		entity.setUpdatedBy("OViRrKdhubhRwJlGIXwlLTPcxQiUBKMtfRmz");
		entity.setUpdatedIp("zcerBKdlkepeyZyUZKGZvBwapRmhlrKqEpOJ");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringAttachmentCategory/Detail").param("id", entity.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$..createdBy").value(hasItem("QiMSwPGoljprEUUGuPiEBWibEAnbGWjCcRDb")))
				.andExpect(jsonPath("$..createdIp").value(hasItem("llHGWtEDvJxBkUQdipBPtOYpNAiAeBOPNuDf")))
				.andExpect(jsonPath("$..createdUserId").value(hasItem("SKwPBxQvypqQeHVAKxkjHtyeDqBEStoXHUuN")))
				.andExpect(jsonPath("$..desc").value(hasItem("UMsPmMzoYVdtvcqzGGkEXLSJcGLMaYWVAMwR")))
				.andExpect(jsonPath("$..dictionaryCode").value(hasItem("LfBftIufUrfQBVggRNKMAWtFIKDCYAgfCkyo")))
				.andExpect(jsonPath("$..dictionaryName").value(hasItem("kLFewAOpLqoxMWxmskNUEgdFBrztBqdUAdBO")))
				.andExpect(jsonPath("$..title").value(hasItem("KGQYneclkuOPFYGbJzMvdKibDRooTgOXVrjB")))
				.andExpect(jsonPath("$..updatedBy").value(hasItem("OViRrKdhubhRwJlGIXwlLTPcxQiUBKMtfRmz")))
				.andExpect(jsonPath("$..updatedIp").value(hasItem("zcerBKdlkepeyZyUZKGZvBwapRmhlrKqEpOJ")))
				.andExpect(jsonPath("$..updatedUserId").value(hasItem("KmTmmwBWgMuNBkGcqsseGiDweVWeHfZEYMtR")));
	}

	@Test
	void testSave() throws Exception {
		int databaseSizeBeforeCreate = dao.findAll().size();
		SpringAttachmentCategory entity = new SpringAttachmentCategory();
		entity.setTitle("sCRtYTkrgYSILttVqUCprjljXByBihJtDbBm");
		entity.setDescription("TWJjGxxFhUyNEKiPWmDYbMzmVYpKvuqgRSXe");
		entity.setDictionaryCode("qzsYrSDLthrgAfdKygWqrMgSqAvWNoiVPCqS");
		entity.setDictionaryName("FPodnyppeFZiOziYFGtTLyMlZyflFpMmbGPw");
		entity.setCreatedUserId("DUMrntyZwOwSuscCusStlwrnXdafZtKilRJn");
		entity.setCreatedBy("BKZNyHAzhFXTZCmRLpRMerydMrJZavXBRPqz");
		entity.setCreatedIp("ADksZaeEBcYXKTBZiyjSfLdWAtuCzGzkLfro");
		entity.setUpdatedUserId("PiBPeVsdlRdbsDQTFvFoSiZVjfRgNydlQjNu");
		entity.setUpdatedBy("lqPdxGGnUdaHzvrlNHutjEXoPocKHTwefkZH");
		entity.setUpdatedIp("WRlUgOBxXnimyCqfBbNDqWRbwIUrSkbiVpjl");
		this.mvc.perform(post("/SpringAttachmentCategory/Create").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(entity))).andExpect(status().isOk());
		List<SpringAttachmentCategory> SpringAttachmentCategoryEntityList = dao.findAll();
		assertThat(SpringAttachmentCategoryEntityList).hasSize(databaseSizeBeforeCreate + 1);
		SpringAttachmentCategory testSpringAttachmentCategoryEntity = SpringAttachmentCategoryEntityList.get(SpringAttachmentCategoryEntityList.size() - 1);
		assertThat(testSpringAttachmentCategoryEntity.getCreatedBy()).isEqualTo("BKZNyHAzhFXTZCmRLpRMerydMrJZavXBRPqz");
		assertThat(testSpringAttachmentCategoryEntity.getCreatedIp()).isEqualTo("ADksZaeEBcYXKTBZiyjSfLdWAtuCzGzkLfro");
		assertThat(testSpringAttachmentCategoryEntity.getCreatedUserId()).isEqualTo("DUMrntyZwOwSuscCusStlwrnXdafZtKilRJn");
		assertThat(testSpringAttachmentCategoryEntity.getDescription()).isEqualTo("TWJjGxxFhUyNEKiPWmDYbMzmVYpKvuqgRSXe");
		assertThat(testSpringAttachmentCategoryEntity.getDictionaryCode()).isEqualTo("qzsYrSDLthrgAfdKygWqrMgSqAvWNoiVPCqS");
		assertThat(testSpringAttachmentCategoryEntity.getDictionaryName()).isEqualTo("FPodnyppeFZiOziYFGtTLyMlZyflFpMmbGPw");
		assertThat(testSpringAttachmentCategoryEntity.getTitle()).isEqualTo("sCRtYTkrgYSILttVqUCprjljXByBihJtDbBm");
		assertThat(testSpringAttachmentCategoryEntity.getUpdatedBy()).isEqualTo("lqPdxGGnUdaHzvrlNHutjEXoPocKHTwefkZH");
		assertThat(testSpringAttachmentCategoryEntity.getUpdatedIp()).isEqualTo("WRlUgOBxXnimyCqfBbNDqWRbwIUrSkbiVpjl");
		assertThat(testSpringAttachmentCategoryEntity.getUpdatedUserId()).isEqualTo("PiBPeVsdlRdbsDQTFvFoSiZVjfRgNydlQjNu");
		;
	}

	@Test
	void testUpdate() throws Exception {
		SpringAttachmentCategory entity = new SpringAttachmentCategory();
		entity.setTitle("zxEDcTEHdaaeePCNEIfYxTgntBwwypohjDKZ");
		entity.setDescription("MjZVStaSEpYswSzNsDVPnkLtdqoRseDdrKJj");
		entity.setDictionaryCode("daafsPBbmsqzxltcVlKRlCKCaRZSktDHmjwx");
		entity.setDictionaryName("jfIBvOspFRfoQqwDQezkaZvDOMCrKcHKWaFq");
		entity.setCreatedUserId("TOnysUDDXmxSwxNEavkADLoyrpCuvkwlhwnx");
		entity.setCreatedBy("GWAIhcojCkMailKWmVvDVTCsHFnPOzhrjKsi");
		entity.setCreatedIp("vupJVhxCZTaWzHFvcfsvKRrCwTOrNmnvPqnd");
		entity.setUpdatedUserId("eYePRpuFPDKCjjfiHtKSOfrUlcIndSjnXXQl");
		entity.setUpdatedBy("ElHGpkFqsLMVkuhJpxUHPCxiSqLxCMqijWxw");
		entity.setUpdatedIp("JUVWmFNJeQMzMthYVnAIishiYliWOnXMJxHg");
		dao.saveAndFlush(entity);
		int databaseSizeBeforeUpdate = dao.findAll().size();
		SpringAttachmentCategory updatedEntity = dao.findById(entity.getId()).get();
		updatedEntity.setTitle("ggGDNHIrdHETRKUqUmkSZAjQwfDBlgaSvBej");
		updatedEntity.setDescription("aYAiqRdjriCBcMZBXrkMswpcuGjnTaYYXuMV");
		updatedEntity.setDictionaryCode("fcgmGLmmasGZdmNLofxScPToWbsGyPtKQHqe");
		updatedEntity.setDictionaryName("DUjwkEWkQCvhbMiMdlmPSiqVbJUdEHQksYYF");
		updatedEntity.setCreatedUserId("DkGsQBdqMIPaiZOqGtsJyJceoGEZcbXQfxpL");
		updatedEntity.setCreatedBy("eVRhCiGfpiYBdPojPowfJURFGrzBulpBzdCs");
		updatedEntity.setCreatedIp("fIGvDEBgRDeBNsMtpQcqvcoTAUMHiPQKfcqw");
		updatedEntity.setUpdatedUserId("MSldmbdlKEMzQMAydVthclRLEIKsHZSTkJSa");
		updatedEntity.setUpdatedBy("tiimpXojSTnsbWnYaGuRmePuMvRjXblECsal");
		updatedEntity.setUpdatedIp("WSxZhYDkYmoWBwmBWoLRHJIARUPcxVUvWGCW");
		this.mvc.perform(post("/SpringAttachmentCategory/Edit").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(updatedEntity)))
				.andExpect(status().isOk());
		List<SpringAttachmentCategory> SpringAttachmentCategoryEntityList = dao.findAll();
		assertThat(SpringAttachmentCategoryEntityList).hasSize(databaseSizeBeforeUpdate);
		SpringAttachmentCategory testSpringAttachmentCategoryEntity = SpringAttachmentCategoryEntityList.get(SpringAttachmentCategoryEntityList.size() - 1);
		assertThat(testSpringAttachmentCategoryEntity.getCreatedBy()).isEqualTo("eVRhCiGfpiYBdPojPowfJURFGrzBulpBzdCs");
		assertThat(testSpringAttachmentCategoryEntity.getCreatedIp()).isEqualTo("fIGvDEBgRDeBNsMtpQcqvcoTAUMHiPQKfcqw");
		assertThat(testSpringAttachmentCategoryEntity.getCreatedUserId()).isEqualTo("DkGsQBdqMIPaiZOqGtsJyJceoGEZcbXQfxpL");
		assertThat(testSpringAttachmentCategoryEntity.getDescription()).isEqualTo("aYAiqRdjriCBcMZBXrkMswpcuGjnTaYYXuMV");
		assertThat(testSpringAttachmentCategoryEntity.getDictionaryCode()).isEqualTo("fcgmGLmmasGZdmNLofxScPToWbsGyPtKQHqe");
		assertThat(testSpringAttachmentCategoryEntity.getDictionaryName()).isEqualTo("DUjwkEWkQCvhbMiMdlmPSiqVbJUdEHQksYYF");
		assertThat(testSpringAttachmentCategoryEntity.getTitle()).isEqualTo("ggGDNHIrdHETRKUqUmkSZAjQwfDBlgaSvBej");
		assertThat(testSpringAttachmentCategoryEntity.getUpdatedBy()).isEqualTo("tiimpXojSTnsbWnYaGuRmePuMvRjXblECsal");
		assertThat(testSpringAttachmentCategoryEntity.getUpdatedIp()).isEqualTo("WSxZhYDkYmoWBwmBWoLRHJIARUPcxVUvWGCW");
		assertThat(testSpringAttachmentCategoryEntity.getUpdatedUserId()).isEqualTo("MSldmbdlKEMzQMAydVthclRLEIKsHZSTkJSa");
		;
	}

	@Test
	void testSetDeleted() throws Exception {
		SpringAttachmentCategory entity = new SpringAttachmentCategory();
		entity.setTitle("UOmVcQRHheOElCKeRpuRXuuZKkDwMYRlTkcA");
		entity.setDescription("XIqRiLpZHqbUMrrGwxEbSTcSOKcHxmiTqGSQ");
		entity.setDictionaryCode("iqKJAroFeSltIJqadDGGXJbzIvOfrLRSTcyi");
		entity.setDictionaryName("kIwRSfRDVEjsHULfkVvrGHaNQrMCJAvZzpLx");
		entity.setCreatedUserId("rTCajpwqAHYNVXqtclyNXcHooTgTDPZdyqgU");
		entity.setCreatedBy("dZhCBMiectSTWKkdvOMUEofDQZMstPVsAZpM");
		entity.setCreatedIp("nbwuvPkRFmQBYjtFefRKXNnlwcmqivsdArut");
		entity.setUpdatedUserId("czKNGSPumIHmQiDoVgsCwBlIQKDgMeipHzNO");
		entity.setUpdatedBy("LIzpZxGWivvlidNivEcNRnXgoXQLkssMIDDx");
		entity.setUpdatedIp("WojrXaTzUQiejMsHuIpYZLxwzppezzHeDphL");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringAttachmentCategory/SetDeleted").param("ids", entity.getId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk());
	}

}
