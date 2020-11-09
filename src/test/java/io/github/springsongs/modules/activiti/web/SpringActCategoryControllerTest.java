package io.github.springsongs.modules.activiti.web;

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

import io.github.springsongs.modules.activiti.domain.SpringActCategory;
import io.github.springsongs.modules.activiti.repo.SpringActCategoryRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("Administrator")
@Transactional
class SpringActCategoryControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private SpringActCategoryRepo dao;

	@Autowired
	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void testGetPage() throws Exception {
		SpringActCategory entity = new SpringActCategory();
		entity.setCategoryCode("bgFzUnerfQZLambiEbiKasQyNHsMliFCyrys");
		entity.setCategoryTitle("eDhbOUKMvtBlKJeOhcyRTQKkTYskXidnuHnC");
		entity.setCreatedUserId("sokhxEJfhZCMGIcbzlEZAGQykVKflAlPVVne");
		entity.setCreatedBy("XUwlblUwmzmSuGJEfOwOUgcefLmDSyBnyayy");
		entity.setCreatedIp("siPqOuqgysxAogkpZExgyWpMBnZpKdytGdUg");
		entity.setUpdatedUserId("GhoDEbsuFeBsBdAgKZIAMXXTCtLwDFqvwmUF");
		entity.setUpdatedBy("vjLxfZSzeQYgQuszQtJGFmQoVbuxzQiqttCI");
		entity.setUpdatedIp("CoiXmhfxKLXIyUPGeradDdWsPRlWrpUukTdX");
		this.mvc.perform(post("/SpringActCategory/ListByPage").param("page", "1").param("limit", "20")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(entity))).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.[*].categoryCode").value(hasItem("bgFzUnerfQZLambiEbiKasQyNHsMliFCyrys")))
				.andExpect(jsonPath("$.data.[*].categoryTitle").value(hasItem("eDhbOUKMvtBlKJeOhcyRTQKkTYskXidnuHnC")))
				.andExpect(jsonPath("$.data.[*].createdBy").value(hasItem("XUwlblUwmzmSuGJEfOwOUgcefLmDSyBnyayy")))
				.andExpect(jsonPath("$.data.[*].createdIp").value(hasItem("siPqOuqgysxAogkpZExgyWpMBnZpKdytGdUg")))
				.andExpect(jsonPath("$.data.[*].createdUserId").value(hasItem("sokhxEJfhZCMGIcbzlEZAGQykVKflAlPVVne")))
				.andExpect(jsonPath("$.data.[*].updatedBy").value(hasItem("vjLxfZSzeQYgQuszQtJGFmQoVbuxzQiqttCI")))
				.andExpect(jsonPath("$.data.[*].updatedIp").value(hasItem("CoiXmhfxKLXIyUPGeradDdWsPRlWrpUukTdX")))
				.andExpect(jsonPath("$.data.[*].updatedUserId").value(hasItem("GhoDEbsuFeBsBdAgKZIAMXXTCtLwDFqvwmUF")));
	}

	@Test
	void testGet() throws Exception {
		SpringActCategory entity = new SpringActCategory();
		entity.setCategoryCode("oFARdOpQMlCRsUrnppZNOiakubVwMLLWfCwQ");
		entity.setCategoryTitle("anrUYRDMJeuVzdPOySoZLsNUtbBKxvGUEdXr");
		entity.setCreatedUserId("UWSThnUCGvAQiPsApSmeIAMlmWfemHsrlLPt");
		entity.setCreatedBy("mitLiZpkgnJbLsrZZeqJoVEycaffZNjqXxzX");
		entity.setCreatedIp("bVnRiahQbbLHZVdlhoEEYNVwrTZWdMgVwYCS");
		entity.setUpdatedUserId("OqtDdyelKeBLPoROmacMybJTkqkNyujxEeoz");
		entity.setUpdatedBy("ancvQBQshoXHKkJmQPApGjWpTJawhgNYFpzH");
		entity.setUpdatedIp("botCIRqSIeLqzzVNbWxnaMdxbPGeNmKxYWfs");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringActCategory/Detail").param("id", entity.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$..categoryCode").value(hasItem("oFARdOpQMlCRsUrnppZNOiakubVwMLLWfCwQ")))
				.andExpect(jsonPath("$..categoryTitle").value(hasItem("anrUYRDMJeuVzdPOySoZLsNUtbBKxvGUEdXr")))
				.andExpect(jsonPath("$..createdBy").value(hasItem("mitLiZpkgnJbLsrZZeqJoVEycaffZNjqXxzX")))
				.andExpect(jsonPath("$..createdIp").value(hasItem("bVnRiahQbbLHZVdlhoEEYNVwrTZWdMgVwYCS")))
				.andExpect(jsonPath("$..createdUserId").value(hasItem("UWSThnUCGvAQiPsApSmeIAMlmWfemHsrlLPt")))
				.andExpect(jsonPath("$..updatedBy").value(hasItem("ancvQBQshoXHKkJmQPApGjWpTJawhgNYFpzH")))
				.andExpect(jsonPath("$..updatedIp").value(hasItem("botCIRqSIeLqzzVNbWxnaMdxbPGeNmKxYWfs")))
				.andExpect(jsonPath("$..updatedUserId").value(hasItem("OqtDdyelKeBLPoROmacMybJTkqkNyujxEeoz")));
	}

	@Test
	void testSave() throws Exception {
		int databaseSizeBeforeCreate = dao.findAll().size();
		SpringActCategory entity = new SpringActCategory();
		entity.setCategoryCode("LKXIJjuddlaYWdwLeWvVQygAwQzCLUwpQmzC");
		entity.setCategoryTitle("yRgGyxEFXFNrKtlmpssIZFLTUSJBahessDfu");
		entity.setCreatedUserId("zJsqnGAxcQnLbVfwgfXbpGFYMXZceawpeUsE");
		entity.setCreatedBy("aCmQdcTktryEebijzRkKHVYmncReJnOODvzu");
		entity.setCreatedIp("ttyvrrfSLhfMCufzCrXgYuFikrgZqXpghUHZ");
		entity.setUpdatedUserId("RmhECKNSWMGVcJjELGgndmQwdNBwMmkvekVE");
		entity.setUpdatedBy("OqwXgSybItDIPQoiPLWLqiMyGCLFkSAXKYLP");
		entity.setUpdatedIp("sdHSOSDFHjJjhardfQALvcIreLiqNAfaJXBO");
		this.mvc.perform(post("/SpringActCategory/Create").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(entity))).andExpect(status().isOk());
		List<SpringActCategory> SpringActCategoryList = dao.findAll();
		assertThat(SpringActCategoryList).hasSize(databaseSizeBeforeCreate + 1);
		SpringActCategory testSpringActCategory = SpringActCategoryList.get(SpringActCategoryList.size() - 1);
		assertThat(testSpringActCategory.getCategoryCode()).isEqualTo("LKXIJjuddlaYWdwLeWvVQygAwQzCLUwpQmzC");
		assertThat(testSpringActCategory.getCategoryTitle()).isEqualTo("yRgGyxEFXFNrKtlmpssIZFLTUSJBahessDfu");
		assertThat(testSpringActCategory.getCreatedBy()).isEqualTo("aCmQdcTktryEebijzRkKHVYmncReJnOODvzu");
		assertThat(testSpringActCategory.getCreatedIp()).isEqualTo("ttyvrrfSLhfMCufzCrXgYuFikrgZqXpghUHZ");
		assertThat(testSpringActCategory.getCreatedUserId()).isEqualTo("zJsqnGAxcQnLbVfwgfXbpGFYMXZceawpeUsE");
		assertThat(testSpringActCategory.getUpdatedBy()).isEqualTo("OqwXgSybItDIPQoiPLWLqiMyGCLFkSAXKYLP");
		assertThat(testSpringActCategory.getUpdatedIp()).isEqualTo("sdHSOSDFHjJjhardfQALvcIreLiqNAfaJXBO");
		assertThat(testSpringActCategory.getUpdatedUserId()).isEqualTo("RmhECKNSWMGVcJjELGgndmQwdNBwMmkvekVE");
		;
	}

	@Test
	void testUpdate() throws Exception {
		SpringActCategory entity = new SpringActCategory();
		entity.setCategoryCode("SGfbDCMZZWmWGfMlNetjzGpxHjZBLfQomvLx");
		entity.setCategoryTitle("dehytljkWyWqZmcSSJhXELNTcfMEVEbggPZq");
		entity.setCreatedUserId("ZGulYXNNZHlVddjhaKLJAyLpTddIOqiguMdt");
		entity.setCreatedBy("kxYXcTffJpTWRSkFZtiZlZgRkHPVtxbBoCbW");
		entity.setCreatedIp("EcoUcffNGszwQSKiPsGXJmdzGKJFTfjQAcXY");
		entity.setUpdatedUserId("kDbBQtkmxeAAtDZQMYMHSiQXyZjcgZTMdVgy");
		entity.setUpdatedBy("ReIfNMGjpsGmIlNpQAXlsUmPERFHzxPYHDND");
		entity.setUpdatedIp("aaUMrtChdvYSbIGACVQPxBTNUVwAkNrvSeol");
		dao.saveAndFlush(entity);
		int databaseSizeBeforeUpdate = dao.findAll().size();
		SpringActCategory updatedEntity = dao.findById(entity.getId()).get();
		updatedEntity.setCategoryCode("AFoWNVhraYLGXdmjdGQlOfljulwaUMRgJPKl");
		updatedEntity.setCategoryTitle("CtfRdtRrnxLnuBNQyWvbTafxfIXtSZEPXUmk");
		updatedEntity.setCreatedUserId("VwuVoejeAfwnwWkWgaAEKUTKbLzApzWvxZnD");
		updatedEntity.setCreatedBy("EKCzJQLSGdFfNxmbGtgejwuSQQpveHPBIkAj");
		updatedEntity.setCreatedIp("QtvAKHaruRgxVjabLPcbfhVUpNlbdDTLqlHj");
		updatedEntity.setUpdatedUserId("mVXBlsOVCITOaPuiJcwBsbbHWtwakTQgRhSY");
		updatedEntity.setUpdatedBy("ZDPGvWlqWcZxeNKhlfaFSzeLarrLFYByFFWU");
		updatedEntity.setUpdatedIp("QzjRpzSBhxVakNNoBBrEIWOfWPWrjSAlufqf");
		this.mvc.perform(post("/SpringActCategory/Edit").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(updatedEntity)))
				.andExpect(status().isOk());
		List<SpringActCategory> SpringActCategoryList = dao.findAll();
		assertThat(SpringActCategoryList).hasSize(databaseSizeBeforeUpdate);
		SpringActCategory testSpringActCategory = SpringActCategoryList.get(SpringActCategoryList.size() - 1);
		assertThat(testSpringActCategory.getCategoryCode()).isEqualTo("AFoWNVhraYLGXdmjdGQlOfljulwaUMRgJPKl");
		assertThat(testSpringActCategory.getCategoryTitle()).isEqualTo("CtfRdtRrnxLnuBNQyWvbTafxfIXtSZEPXUmk");
		assertThat(testSpringActCategory.getCreatedBy()).isEqualTo("EKCzJQLSGdFfNxmbGtgejwuSQQpveHPBIkAj");
		assertThat(testSpringActCategory.getCreatedIp()).isEqualTo("QtvAKHaruRgxVjabLPcbfhVUpNlbdDTLqlHj");
		assertThat(testSpringActCategory.getCreatedUserId()).isEqualTo("VwuVoejeAfwnwWkWgaAEKUTKbLzApzWvxZnD");
		assertThat(testSpringActCategory.getUpdatedBy()).isEqualTo("ZDPGvWlqWcZxeNKhlfaFSzeLarrLFYByFFWU");
		assertThat(testSpringActCategory.getUpdatedIp()).isEqualTo("QzjRpzSBhxVakNNoBBrEIWOfWPWrjSAlufqf");
		assertThat(testSpringActCategory.getUpdatedUserId()).isEqualTo("mVXBlsOVCITOaPuiJcwBsbbHWtwakTQgRhSY");
		;
	}

	@Test
	void testSetDeleted() throws Exception {
		SpringActCategory entity = new SpringActCategory();
		entity.setCategoryCode("UenzVSbJFaxofSCvMaWNXsaEKiiEeyHIxTFF");
		entity.setCategoryTitle("bxZhsYcvQSXvFweyuLedZFVGXCfOamQAmiRJ");
		entity.setCreatedUserId("KBPvcdVmhaYYxdTWGPTTbjlsPKWOwIzLxwsd");
		entity.setCreatedBy("JqKoyupMVmLFXFlvoPlJudyidHPWNUbNpAYp");
		entity.setCreatedIp("XfFXwovyAfcoINizQmkznHzcSmQRIGtzFVvB");
		entity.setUpdatedUserId("oSNMZjFZriGfnlrhluSPSNKKevciInmNkiBR");
		entity.setUpdatedBy("kgkflomciSWFYSlLXFoqEBYiFFfhbKaFruIu");
		entity.setUpdatedIp("KkGHPMPmsZIfjGRfrfpBmRxoeNvfzVHfrGsU");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringActCategory/SetDeleted").param("ids", entity.getId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk());
	}

}
