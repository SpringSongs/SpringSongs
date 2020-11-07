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

import io.github.springsongs.modules.sys.domain.SpringArticleComment;
import io.github.springsongs.modules.sys.repo.SpringArticleCommentRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithUserDetails("Administrator")
class SpringArticleCommentControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private SpringArticleCommentRepo dao;

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void testGetPage() throws Exception {
		SpringArticleComment entity = new SpringArticleComment();
		entity.setContent("DTmyuXaWowENMcwWSiXRSTlMZlaqjdkOoaun");
		entity.setArticleId("KQfdLTgAjRblTuSuLERZdWTbLjfeqCGoJito");
		entity.setCreatedUserId("wULYfnzYZrcvcZyCxkfJmCzJuBFcTxpysnvm");
		entity.setCreatedBy("yqIzHbDpFmSNbUGxBNwzKvNXDfHhlynviXkL");
		entity.setCreatedIp("QWtuDiUYCduoVqUofOCeUPOmSHqHnHARvgWS");
		entity.setUpdatedUserId("XlDpBZYDnGMCVKUSrfESvfPUFbVFYSTvBJmN");
		entity.setUpdatedBy("NiAMwhbbKOmdIxPuiLyOjaJLOsSWrtJVYeaW");
		entity.setUpdatedIp("GTzdfZMreraVDgGPwCxCKlwaEWuJjBalJUWI");
		this.mvc.perform(post("/SpringArticleComment/ListByPage").param("page", "1").param("limit", "20")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(entity))).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.[*].content").value(hasItem("DTmyuXaWowENMcwWSiXRSTlMZlaqjdkOoaun")))
				.andExpect(jsonPath("$.data.[*].createdBy").value(hasItem("yqIzHbDpFmSNbUGxBNwzKvNXDfHhlynviXkL")))
				.andExpect(jsonPath("$.data.[*].createdIp").value(hasItem("QWtuDiUYCduoVqUofOCeUPOmSHqHnHARvgWS")))
				.andExpect(jsonPath("$.data.[*].createdUserId").value(hasItem("wULYfnzYZrcvcZyCxkfJmCzJuBFcTxpysnvm")))
				.andExpect(jsonPath("$.data.[*].objectId").value(hasItem("KQfdLTgAjRblTuSuLERZdWTbLjfeqCGoJito")))
				.andExpect(jsonPath("$.data.[*].updatedBy").value(hasItem("NiAMwhbbKOmdIxPuiLyOjaJLOsSWrtJVYeaW")))
				.andExpect(jsonPath("$.data.[*].updatedIp").value(hasItem("GTzdfZMreraVDgGPwCxCKlwaEWuJjBalJUWI")))
				.andExpect(jsonPath("$.data.[*].updatedUserId").value(hasItem("XlDpBZYDnGMCVKUSrfESvfPUFbVFYSTvBJmN")));
	}

	@Test
	void testGet() throws Exception {
		SpringArticleComment entity = new SpringArticleComment();
		entity.setContent("PgqBNHROELCJVGwKAUxiBHscEvkzfJVbeGRb");
		entity.setArticleId("SUwOwAObmaEpaDtuKrdXiLhyfdWjYSkzICka");
		entity.setCreatedUserId("KEkrbEEorbkAbfjdaniElratfehHzWCkaLjE");
		entity.setCreatedBy("jvnkALCzZcbtlNQtjkJXtJJOtfFdYStFiIZT");
		entity.setCreatedIp("WICSJNoSerZdtKySIvNJmSJTEGTvoDcDBpNH");
		entity.setUpdatedUserId("iFxqIZJDsywlEEpbrQLlVHNcoOwWAmWoLLWr");
		entity.setUpdatedBy("VGuPitzBYtKjWXXJOSpPEBCjVQHnFscAPfWn");
		entity.setUpdatedIp("CGdltuDBRIVBexJuFxxbswfkxPDSQRyqFYZY");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringArticleComment/Detail").param("id", entity.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$..content").value(hasItem("PgqBNHROELCJVGwKAUxiBHscEvkzfJVbeGRb")))
				.andExpect(jsonPath("$..createdBy").value(hasItem("jvnkALCzZcbtlNQtjkJXtJJOtfFdYStFiIZT")))
				.andExpect(jsonPath("$..createdIp").value(hasItem("WICSJNoSerZdtKySIvNJmSJTEGTvoDcDBpNH")))
				.andExpect(jsonPath("$..createdUserId").value(hasItem("KEkrbEEorbkAbfjdaniElratfehHzWCkaLjE")))
				.andExpect(jsonPath("$..objectId").value(hasItem("SUwOwAObmaEpaDtuKrdXiLhyfdWjYSkzICka")))
				.andExpect(jsonPath("$..updatedBy").value(hasItem("VGuPitzBYtKjWXXJOSpPEBCjVQHnFscAPfWn")))
				.andExpect(jsonPath("$..updatedIp").value(hasItem("CGdltuDBRIVBexJuFxxbswfkxPDSQRyqFYZY")))
				.andExpect(jsonPath("$..updatedUserId").value(hasItem("iFxqIZJDsywlEEpbrQLlVHNcoOwWAmWoLLWr")));
	}

	@Test
	void testSave() throws Exception {
		int databaseSizeBeforeCreate = dao.findAll().size();
		SpringArticleComment entity = new SpringArticleComment();
		entity.setContent("HYvUIfQWRjiivpIOvzWEQqLpkIhyRQjPewKY");
		entity.setArticleId("CwjUetoENVbLBiJDqxylSxOKdkKXNxvToYQf");
		entity.setCreatedUserId("CIBxsVepbxqHyEmOFWYrepvUwJaBWAeRtqYV");
		entity.setCreatedBy("IxAZlqHBKgoiLFZDHSqDAzzFOQNqFpTvwJOw");
		entity.setCreatedIp("SMYdGFEaAAPyiZkHkRXkxBetPVvAczlcaIDp");
		entity.setUpdatedUserId("IEJhrsGBwxrXuDWwHIdlQPKLatWzdBKbLZmH");
		entity.setUpdatedBy("rNMXZUWwNHNMPpmnJgSdKiaAelZauRTXAxkq");
		entity.setUpdatedIp("vDcBYAnJRNsTAYDusWVgiZVvOpZZZwncmUrn");
		this.mvc.perform(post("/SpringArticleComment/Create").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(entity))).andExpect(status().isOk());
		List<SpringArticleComment> SpringArticleCommentEntityList = dao.findAll();
		assertThat(SpringArticleCommentEntityList).hasSize(databaseSizeBeforeCreate + 1);
		SpringArticleComment testSpringArticleCommentEntity = SpringArticleCommentEntityList.get(SpringArticleCommentEntityList.size() - 1);
		assertThat(testSpringArticleCommentEntity.getContent()).isEqualTo("HYvUIfQWRjiivpIOvzWEQqLpkIhyRQjPewKY");
		assertThat(testSpringArticleCommentEntity.getCreatedBy()).isEqualTo("IxAZlqHBKgoiLFZDHSqDAzzFOQNqFpTvwJOw");
		assertThat(testSpringArticleCommentEntity.getCreatedIp()).isEqualTo("SMYdGFEaAAPyiZkHkRXkxBetPVvAczlcaIDp");
		assertThat(testSpringArticleCommentEntity.getCreatedUserId()).isEqualTo("CIBxsVepbxqHyEmOFWYrepvUwJaBWAeRtqYV");
		assertThat(testSpringArticleCommentEntity.getArticleId()).isEqualTo("CwjUetoENVbLBiJDqxylSxOKdkKXNxvToYQf");
		assertThat(testSpringArticleCommentEntity.getUpdatedBy()).isEqualTo("rNMXZUWwNHNMPpmnJgSdKiaAelZauRTXAxkq");
		assertThat(testSpringArticleCommentEntity.getUpdatedIp()).isEqualTo("vDcBYAnJRNsTAYDusWVgiZVvOpZZZwncmUrn");
		assertThat(testSpringArticleCommentEntity.getUpdatedUserId()).isEqualTo("IEJhrsGBwxrXuDWwHIdlQPKLatWzdBKbLZmH");
		;
	}

	@Test
	void testUpdate() throws Exception {
		SpringArticleComment entity = new SpringArticleComment();
		entity.setContent("XjEXsdWqAUQTyoxnkuaPrOuZBGRKWTaWPRot");
		entity.setArticleId("KiVIiApnxFTmWMKebYIomMFOfPgIzJEjkQOC");
		entity.setCreatedUserId("XodnIzoioUsckskNXAFdeNoacGjstGZZgfdR");
		entity.setCreatedBy("UEwXuQycSZsrcWxBzWaTkZQgUHYKveLnXJzA");
		entity.setCreatedIp("cpGTkGCaZpgtNxOawbxIsBbhyUvGzpslLhUW");
		entity.setUpdatedUserId("eZeokgxdrGLCEbLBoXiESVufFVgScKFzomXt");
		entity.setUpdatedBy("fAXSbOHmtyItJkqoNCmKYuszEMvulyuKmmSI");
		entity.setUpdatedIp("yyYPiFdxmPSiCFVZguoGQYyMKVMpSODheYSd");
		dao.saveAndFlush(entity);
		int databaseSizeBeforeUpdate = dao.findAll().size();
		SpringArticleComment updatedEntity = dao.findById(entity.getId()).get();
		updatedEntity.setContent("koPPyCVlfzFedESoNowDLtkqwevlwyiZExvm");
		updatedEntity.setArticleId("WiJvIWROCkJFEapTWirdwixMNQoXhdkRVjAd");
		updatedEntity.setCreatedUserId("iRvIsVQuKPEoRFtCAeTZJItHVZzyXojdeFEs");
		updatedEntity.setCreatedBy("kQxEMAzNiaOAOsjYYEjBUwUOpkTqRKndcTMB");
		updatedEntity.setCreatedIp("YLwqCaMLpQhXWjhUczNaOJFjZULgtrqCWbPI");
		updatedEntity.setUpdatedUserId("PbEcabkYGiJBSVMjOgFzjznNddIwbtBVuoxb");
		updatedEntity.setUpdatedBy("jLclUFsbkEpCyoKwbDIgcNabkqqXlrbpRhTq");
		updatedEntity.setUpdatedIp("TmnKPfHpMSHQMOawDpnIwKmRqCnqrJtuqPce");
		this.mvc.perform(post("/SpringArticleComment/Edit").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(updatedEntity)))
				.andExpect(status().isOk());
		List<SpringArticleComment> SpringArticleCommentEntityList = dao.findAll();
		assertThat(SpringArticleCommentEntityList).hasSize(databaseSizeBeforeUpdate);
		SpringArticleComment testSpringArticleCommentEntity = SpringArticleCommentEntityList.get(SpringArticleCommentEntityList.size() - 1);
		assertThat(testSpringArticleCommentEntity.getContent()).isEqualTo("koPPyCVlfzFedESoNowDLtkqwevlwyiZExvm");
		assertThat(testSpringArticleCommentEntity.getCreatedBy()).isEqualTo("kQxEMAzNiaOAOsjYYEjBUwUOpkTqRKndcTMB");
		assertThat(testSpringArticleCommentEntity.getCreatedIp()).isEqualTo("YLwqCaMLpQhXWjhUczNaOJFjZULgtrqCWbPI");
		assertThat(testSpringArticleCommentEntity.getCreatedUserId()).isEqualTo("iRvIsVQuKPEoRFtCAeTZJItHVZzyXojdeFEs");
		assertThat(testSpringArticleCommentEntity.getArticleId()).isEqualTo("WiJvIWROCkJFEapTWirdwixMNQoXhdkRVjAd");
		assertThat(testSpringArticleCommentEntity.getUpdatedBy()).isEqualTo("jLclUFsbkEpCyoKwbDIgcNabkqqXlrbpRhTq");
		assertThat(testSpringArticleCommentEntity.getUpdatedIp()).isEqualTo("TmnKPfHpMSHQMOawDpnIwKmRqCnqrJtuqPce");
		assertThat(testSpringArticleCommentEntity.getUpdatedUserId()).isEqualTo("PbEcabkYGiJBSVMjOgFzjznNddIwbtBVuoxb");
		;
	}

	@Test
	void testSetDeleted() throws Exception {
		SpringArticleComment entity = new SpringArticleComment();
		entity.setContent("ZmpAKSSBRYrXwhxotbpyNjEgkDpHnZsQcgPH");
		entity.setArticleId("vZGJUFbMgYdhkAhCjXUqrqUMNTBPIEiEjjBw");
		entity.setCreatedUserId("EpSNSxYFVxufqRMYEvOUMyaaPpABVWBTlZUJ");
		entity.setCreatedBy("ydjrmSqQyJOVzTPevHhyAETjXUoNHTEUYGsf");
		entity.setCreatedIp("QvxTnJDfSciIWTkTMPQxusDuPIZvUIygHgwk");
		entity.setUpdatedUserId("EGhwrrYBeVgTAVSfjNriFjSZqbKXPAzpyPDV");
		entity.setUpdatedBy("uvvLytgeSSRQBQKDKZoyWjsZUONUOfKHvMnQ");
		entity.setUpdatedIp("IhlSwqQeNAjsVqCnyDhBYdjlhZJzoVXOQeKv");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringArticleComment/SetDeleted").param("ids", entity.getId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk());
	}

}
