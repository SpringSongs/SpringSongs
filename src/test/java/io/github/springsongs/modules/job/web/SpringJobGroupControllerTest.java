package io.github.springsongs.modules.job.web;

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

import org.junit.Before;
import org.junit.Test;
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

import io.github.springsongs.modules.job.domain.SpringJobGroup;
import io.github.springsongs.modules.job.dto.SpringJobGroupDTO;
import io.github.springsongs.modules.job.repo.SpringJobGroupRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("Administrator")
@Transactional
public class SpringJobGroupControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private SpringJobGroupRepo dao;

	@Autowired
	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testGetPage() throws Exception {
		SpringJobGroupDTO entity = new SpringJobGroupDTO();
		entity.setTitle("FxpkieCkwBZPnOjpSnFSnZhehkTbDoTNAyyS");
		entity.setCreatedUserId("xANVCgSSsiMLIxFcyjNACcOIpqOpvYCMEblb");
		entity.setCreatedBy("KCjkhhgLbxhrIEnLeABFBRLpycWvkhIOXLJj");
		entity.setCreatedIp("PQAUKwcYrAZILsNWsdejnMTYpDVXfowHCKyh");
		entity.setUpdatedUserId("LxLbtIDZOUvyYxdXbsBKeHUHHwRyLTuPSZKP");
		entity.setUpdatedBy("WBOsqSDzaMzWNQZpJAXBxshhSdBGLSObCUKW");
		entity.setUpdatedIp("VhuoGEhxsiOMjOhFXPIVpeKCDIAVjBaYxjCW");
		this.mvc.perform(post("/SpringJobGroup/ListByPage").param("page", "0").param("limit", "20")
				.with(csrf().useInvalidToken())
				.with(user("Administrator").password("qweasd").roles("USER", "Administrators"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(entity))).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.[*].createdBy").value(hasItem("KCjkhhgLbxhrIEnLeABFBRLpycWvkhIOXLJj")))
				.andExpect(jsonPath("$.data.[*].createdIp").value(hasItem("PQAUKwcYrAZILsNWsdejnMTYpDVXfowHCKyh")))
				.andExpect(jsonPath("$.data.[*].createdUserId").value(hasItem("xANVCgSSsiMLIxFcyjNACcOIpqOpvYCMEblb")))
				.andExpect(jsonPath("$.data.[*].title").value(hasItem("FxpkieCkwBZPnOjpSnFSnZhehkTbDoTNAyyS")))
				.andExpect(jsonPath("$.data.[*].updatedBy").value(hasItem("WBOsqSDzaMzWNQZpJAXBxshhSdBGLSObCUKW")))
				.andExpect(jsonPath("$.data.[*].updatedIp").value(hasItem("VhuoGEhxsiOMjOhFXPIVpeKCDIAVjBaYxjCW")))
				.andExpect(jsonPath("$.data.[*].updatedUserId").value(hasItem("LxLbtIDZOUvyYxdXbsBKeHUHHwRyLTuPSZKP")));
	}

	@Test
	public void testGet() throws Exception {
		SpringJobGroup entity = new SpringJobGroup();
		entity.setTitle("tJJlBNmWkwzdKEHXpjaaUKJWTSyyeghvhWRp");
		entity.setCreatedUserId("thqsCxGKCSfwhKzmTMyiydfJsXcIiPnLqoze");
		entity.setCreatedBy("bsHVOUcvornPGiHgYIjkNDDEPBHLjBVHMATK");
		entity.setCreatedIp("zKxrngsWErCeyEdohlWicGwTZzLYwuPPmMOw");
		entity.setUpdatedUserId("eUBqdYwvhngNWAirvRUGgpfMLTsVzEaiZkfe");
		entity.setUpdatedBy("XrKDQDSLrPSsBziCwwDiaxtnoDpcdWOtNcdS");
		entity.setUpdatedIp("hHBXuaCWyFNGxdtVQYyobfgRFasdpLrRlkCu");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringJobGroup/Detail").param("id", entity.getId()).with(csrf().useInvalidToken())
				.with(user("Administrator").password("qweasd").roles("USER", "Administrators")))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$..createdBy").value(hasItem("bsHVOUcvornPGiHgYIjkNDDEPBHLjBVHMATK")))
				.andExpect(jsonPath("$..createdIp").value(hasItem("zKxrngsWErCeyEdohlWicGwTZzLYwuPPmMOw")))
				.andExpect(jsonPath("$..createdUserId").value(hasItem("thqsCxGKCSfwhKzmTMyiydfJsXcIiPnLqoze")))
				.andExpect(jsonPath("$..title").value(hasItem("tJJlBNmWkwzdKEHXpjaaUKJWTSyyeghvhWRp")))
				.andExpect(jsonPath("$..updatedBy").value(hasItem("XrKDQDSLrPSsBziCwwDiaxtnoDpcdWOtNcdS")))
				.andExpect(jsonPath("$..updatedIp").value(hasItem("hHBXuaCWyFNGxdtVQYyobfgRFasdpLrRlkCu")))
				.andExpect(jsonPath("$..updatedUserId").value(hasItem("eUBqdYwvhngNWAirvRUGgpfMLTsVzEaiZkfe")));
	}

	@Test
	public void testSave() throws Exception {
		int databaseSizeBeforeCreate = dao.findAll().size();
		SpringJobGroupDTO entity = new SpringJobGroupDTO();
		entity.setTitle("PmjsXfxqFcApoSHfiEeahWYbClaHwswkNIUf");
		entity.setCreatedUserId("ZGxVJrmeylJboaunQzWVSjvmDyFQuJFfIiia");
		entity.setCreatedBy("oubwzguvezsfAdjEFqqpfDcSeJlJDPaFnGDB");
		entity.setCreatedIp("ADwQHwTDXsKmNbnPRfffuNuqNZMMcvgLdPnT");
		entity.setUpdatedUserId("nvdNJXTSNffvFGbcWlzEuenJRjRBbyphjkJf");
		entity.setUpdatedBy("ctRDWqcaKJvsgAAUAgAdokrYoqnhcmWJtTkA");
		entity.setUpdatedIp("LLnZvwHYoCmWGzHkumdrSUZmzvtOKszxJrQy");
		this.mvc.perform(post("/SpringJobGroup/Create").with(csrf().useInvalidToken())
				.with(user("Administrator").password("qweasd").roles("USER", "Administrators"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(entity))).andExpect(status().isOk());
		List<SpringJobGroup> SpringJobGroupDTOList = dao.findAll();
		assertThat(SpringJobGroupDTOList).hasSize(databaseSizeBeforeCreate + 1);
		SpringJobGroup testSpringJobGroupDTO = SpringJobGroupDTOList.get(SpringJobGroupDTOList.size() - 1);
		assertThat(testSpringJobGroupDTO.getCreatedBy()).isEqualTo("oubwzguvezsfAdjEFqqpfDcSeJlJDPaFnGDB");
		assertThat(testSpringJobGroupDTO.getCreatedIp()).isEqualTo("ADwQHwTDXsKmNbnPRfffuNuqNZMMcvgLdPnT");
		assertThat(testSpringJobGroupDTO.getCreatedUserId()).isEqualTo("ZGxVJrmeylJboaunQzWVSjvmDyFQuJFfIiia");
		assertThat(testSpringJobGroupDTO.getTitle()).isEqualTo("PmjsXfxqFcApoSHfiEeahWYbClaHwswkNIUf");
		assertThat(testSpringJobGroupDTO.getUpdatedBy()).isEqualTo("ctRDWqcaKJvsgAAUAgAdokrYoqnhcmWJtTkA");
		assertThat(testSpringJobGroupDTO.getUpdatedIp()).isEqualTo("LLnZvwHYoCmWGzHkumdrSUZmzvtOKszxJrQy");
		assertThat(testSpringJobGroupDTO.getUpdatedUserId()).isEqualTo("nvdNJXTSNffvFGbcWlzEuenJRjRBbyphjkJf");
	}

	@Test
	public void testUpdate() throws Exception {
		SpringJobGroup entity = new SpringJobGroup();
		entity.setTitle("aNgrVNeKZGcePlIDdtEdwwDZGXjvOwSBlFSf");
		entity.setCreatedUserId("QkUGOCvEvIpecItchkXXDnMfhFILOATRdzty");
		entity.setCreatedBy("otxlkLvxaNbGAWhhsepRuQRLiDvhxrHGQdAP");
		entity.setCreatedIp("BgNOYIgdQNmGxlAgHsPmcsfirvWfwqOZQWWF");
		entity.setUpdatedUserId("zynsVSrsJEOSsffObkaiRBLRPBwOCeDJbOKz");
		entity.setUpdatedBy("VDpLMYnFaQBgWawYWLyzLJeMAwcKgLLMIOhf");
		entity.setUpdatedIp("hmLCVKnECpplsDOTRtVqgsdnJiHDDlUgChbN");
		dao.saveAndFlush(entity);
		int databaseSizeBeforeUpdate = dao.findAll().size();
		SpringJobGroup updatedEntity = dao.findById(entity.getId()).get();
		updatedEntity.setTitle("EMDydjJPZzYLuYGgQnpLQVtIkfgbKGnggHAv");
		updatedEntity.setCreatedUserId("aqqZgwmEArBEoTydNCOCcjIfFxQcfSkoadho");
		updatedEntity.setCreatedBy("KNWJIJHTEpOUxFjYFbdezFUhppSLrhdcxEUT");
		updatedEntity.setCreatedIp("eyyjHxkKDyNgfUGCZZTdRVXTUMaglDcwFcDF");
		updatedEntity.setUpdatedUserId("dwfdSxUfTmsoVFtTtyJvceLPSXDbLczbwzFQ");
		updatedEntity.setUpdatedBy("kvvCzPGUqlLUGvGAeLBbqsQGiDdprMaNrZoi");
		updatedEntity.setUpdatedIp("XEGyBCvKpArrhhcUXKbmykPhAYmxQKcFTEmf");
		this.mvc.perform(post("/SpringJobGroup/Edit").with(csrf().useInvalidToken())
				.with(user("Administrator").password("qweasd").roles("USER", "Administrators"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(updatedEntity))).andExpect(status().isOk());
		List<SpringJobGroup> SpringJobGroupDTOList = dao.findAll();
		assertThat(SpringJobGroupDTOList).hasSize(databaseSizeBeforeUpdate);
		SpringJobGroup testSpringJobGroupDTO = SpringJobGroupDTOList.get(SpringJobGroupDTOList.size() - 1);
		assertThat(testSpringJobGroupDTO.getCreatedBy()).isEqualTo("KNWJIJHTEpOUxFjYFbdezFUhppSLrhdcxEUT");
		assertThat(testSpringJobGroupDTO.getCreatedIp()).isEqualTo("eyyjHxkKDyNgfUGCZZTdRVXTUMaglDcwFcDF");
		assertThat(testSpringJobGroupDTO.getCreatedUserId()).isEqualTo("aqqZgwmEArBEoTydNCOCcjIfFxQcfSkoadho");
		assertThat(testSpringJobGroupDTO.getTitle()).isEqualTo("EMDydjJPZzYLuYGgQnpLQVtIkfgbKGnggHAv");
		assertThat(testSpringJobGroupDTO.getUpdatedBy()).isEqualTo("kvvCzPGUqlLUGvGAeLBbqsQGiDdprMaNrZoi");
		assertThat(testSpringJobGroupDTO.getUpdatedIp()).isEqualTo("XEGyBCvKpArrhhcUXKbmykPhAYmxQKcFTEmf");
		assertThat(testSpringJobGroupDTO.getUpdatedUserId()).isEqualTo("dwfdSxUfTmsoVFtTtyJvceLPSXDbLczbwzFQ");
	}

	@Test
	public void testSetDeleted() throws Exception {
		SpringJobGroup entity = new SpringJobGroup();
		entity.setTitle("bsjlzrYDAfMbNuCgZaqqhODvLJQPSnjXSyMx");
		entity.setCreatedUserId("oqMZxnciIYcqBrfhWOMlRlQtvTXREPZKYVSq");
		entity.setCreatedBy("waJpUJJyBodGdrKlHPotXLgBkWTuHXwssiEN");
		entity.setCreatedIp("LGnazvFyGZfCdJOthMwzRMdQMRejIwrOZwLB");
		entity.setUpdatedUserId("IxXypiPlEYsWZazhUJXDPHrpZGbyKCItPREB");
		entity.setUpdatedBy("opUoKXZFtsTLsPsysNxjQQOwucHbwURbBrPu");
		entity.setUpdatedIp("JNYhVlfGGCIQCpnaFaqArWSVftpwMIganqia");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringJobGroup/SetDeleted").param("ids", entity.getId()).with(csrf().useInvalidToken())
				.with(user("Administrator").password("qweasd").roles("USER", "Administrators"))
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk());
	}
}
