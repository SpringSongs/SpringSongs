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

import io.github.springsongs.modules.sys.domain.SpringDictionary;
import io.github.springsongs.modules.sys.repo.SpringDictionaryRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithUserDetails("Administrator")
class SpringDictionaryControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private SpringDictionaryRepo dao;

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void testGetPage() throws Exception {
		SpringDictionary entity = new SpringDictionary();
		entity.setCode("NsrXIPJJjPivAeYphvRiFkqvFRkVPvHzeHsp");
		entity.setTitle("tCrMsRIyCBPMBGwMgdwSmHTOnuRtkxDtbSXT");
		entity.setDescription("cThDAsBIXbJsztnqMQZcObntkaNzMcFksALy");
		entity.setCreatedUserId("zcTcdvTMOiDCHMhpheCKddwtVIygBCxKGOaa");
		entity.setCreatedBy("iHWSwKRrJDDzvUMmSkosUrzeTFUFDBWXRDvq");
		entity.setCreatedIp("vlAQxUBZgMIGzSJwDrUFlZebNXziJlNUigeA");
		entity.setUpdatedUserId("TfEFJqqXcPxKHHeHHaWjsdfOvVGVvxKCleDe");
		entity.setUpdatedBy("EXjEvuhKWbpKDzRWHAOTEFFJclBdxMWmSolo");
		entity.setUpdatedIp("KwrMmiwTyPMKjnDyXQflbynseMEHdCwGZIbf");
		this.mvc.perform(post("/SpringDictionary/ListByPage").param("page", "1").param("limit", "20")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(entity))).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.[*].code").value(hasItem("NsrXIPJJjPivAeYphvRiFkqvFRkVPvHzeHsp")))
				.andExpect(jsonPath("$.data.[*].createdBy").value(hasItem("iHWSwKRrJDDzvUMmSkosUrzeTFUFDBWXRDvq")))
				.andExpect(jsonPath("$.data.[*].createdIp").value(hasItem("vlAQxUBZgMIGzSJwDrUFlZebNXziJlNUigeA")))
				.andExpect(jsonPath("$.data.[*].createdUserId").value(hasItem("zcTcdvTMOiDCHMhpheCKddwtVIygBCxKGOaa")))
				.andExpect(jsonPath("$.data.[*].desc").value(hasItem("cThDAsBIXbJsztnqMQZcObntkaNzMcFksALy")))
				.andExpect(jsonPath("$.data.[*].title").value(hasItem("tCrMsRIyCBPMBGwMgdwSmHTOnuRtkxDtbSXT")))
				.andExpect(jsonPath("$.data.[*].updatedBy").value(hasItem("EXjEvuhKWbpKDzRWHAOTEFFJclBdxMWmSolo")))
				.andExpect(jsonPath("$.data.[*].updatedIp").value(hasItem("KwrMmiwTyPMKjnDyXQflbynseMEHdCwGZIbf")))
				.andExpect(jsonPath("$.data.[*].updatedUserId").value(hasItem("TfEFJqqXcPxKHHeHHaWjsdfOvVGVvxKCleDe")));
	}

	@Test
	void testGet() throws Exception {
		SpringDictionary entity = new SpringDictionary();
		entity.setCode("ziaaCtmYHcUIilUgUSZoSgJAInysCtlOLLRa");
		entity.setTitle("GUikZOcrDqmJzvmwiWFPoMkthGYPbdwOxItc");
		entity.setDescription("vShpqZzeeuvKxvdESPEdPGvaHpXcJPDnNdWD");
		entity.setCreatedUserId("BHrivshCfxLRmuHmBhchYvTQqeawYJIopYIt");
		entity.setCreatedBy("rWdJvHUVhBuFXnyIKKJqpjafmZbIgNiRkfcU");
		entity.setCreatedIp("vFSKUekYtcOoOvLfxPEdVGaBabCMptieAuXd");
		entity.setUpdatedUserId("HerIqCaOJFbvioRUwwcPWJKqWrsgiYuqxBtL");
		entity.setUpdatedBy("czGobiTSvbvLdyewfYFvgvyuLnimbnYXnHZR");
		entity.setUpdatedIp("jtbcYogGSOJwSxHCBdjQNPPRvtLdWOYEgKxL");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringDictionary/Detail").param("id", entity.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$..code").value(hasItem("ziaaCtmYHcUIilUgUSZoSgJAInysCtlOLLRa")))
				.andExpect(jsonPath("$..createdBy").value(hasItem("rWdJvHUVhBuFXnyIKKJqpjafmZbIgNiRkfcU")))
				.andExpect(jsonPath("$..createdIp").value(hasItem("vFSKUekYtcOoOvLfxPEdVGaBabCMptieAuXd")))
				.andExpect(jsonPath("$..createdUserId").value(hasItem("BHrivshCfxLRmuHmBhchYvTQqeawYJIopYIt")))
				.andExpect(jsonPath("$..desc").value(hasItem("vShpqZzeeuvKxvdESPEdPGvaHpXcJPDnNdWD")))
				.andExpect(jsonPath("$..title").value(hasItem("GUikZOcrDqmJzvmwiWFPoMkthGYPbdwOxItc")))
				.andExpect(jsonPath("$..updatedBy").value(hasItem("czGobiTSvbvLdyewfYFvgvyuLnimbnYXnHZR")))
				.andExpect(jsonPath("$..updatedIp").value(hasItem("jtbcYogGSOJwSxHCBdjQNPPRvtLdWOYEgKxL")))
				.andExpect(jsonPath("$..updatedUserId").value(hasItem("HerIqCaOJFbvioRUwwcPWJKqWrsgiYuqxBtL")));
	}

	@Test
	void testSave() throws Exception {
		int databaseSizeBeforeCreate = dao.findAll().size();
		SpringDictionary entity = new SpringDictionary();
		entity.setCode("hCgNkcXQSxdiaiNecLAircTmXDLtlVCORifX");
		entity.setTitle("hjgDzskICbNlreBWQwXfabXBfbVjxekbqlto");
		entity.setDescription("AcWyDNruLiYPWpRXSwptGlZexivxCRCgVOeM");
		entity.setCreatedUserId("HTIzyrKGwxeCJdheZQnyKbZnccbhjrMosMFI");
		entity.setCreatedBy("PnboDovcsiXlWIYzCwVMIjlKybgiyrywEmxv");
		entity.setCreatedIp("KveqTAilzkHtPlZQYvkwXHuVUkJsrIYmmjEo");
		entity.setUpdatedUserId("RPQXKKcRijtiSGBGpaHOHPOeyosjTpTwUnam");
		entity.setUpdatedBy("jcynezrTioGrHuDdNlTiHeSQiXYBSXpXYbYJ");
		entity.setUpdatedIp("bPntVyfiWFVlWwFHSOSfZpJRnZIlfzSvyGIs");
		this.mvc.perform(post("/SpringDictionary/Create").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(entity))).andExpect(status().isOk());
		List<SpringDictionary> SpringDictionaryEntityList = dao.findAll();
		assertThat(SpringDictionaryEntityList).hasSize(databaseSizeBeforeCreate + 1);
		SpringDictionary testSpringDictionaryEntity = SpringDictionaryEntityList
				.get(SpringDictionaryEntityList.size() - 1);
		assertThat(testSpringDictionaryEntity.getCode()).isEqualTo("hCgNkcXQSxdiaiNecLAircTmXDLtlVCORifX");
		assertThat(testSpringDictionaryEntity.getCreatedBy()).isEqualTo("PnboDovcsiXlWIYzCwVMIjlKybgiyrywEmxv");
		assertThat(testSpringDictionaryEntity.getCreatedIp()).isEqualTo("KveqTAilzkHtPlZQYvkwXHuVUkJsrIYmmjEo");
		assertThat(testSpringDictionaryEntity.getCreatedUserId()).isEqualTo("HTIzyrKGwxeCJdheZQnyKbZnccbhjrMosMFI");
		assertThat(testSpringDictionaryEntity.getDescription()).isEqualTo("AcWyDNruLiYPWpRXSwptGlZexivxCRCgVOeM");
		assertThat(testSpringDictionaryEntity.getTitle()).isEqualTo("hjgDzskICbNlreBWQwXfabXBfbVjxekbqlto");
		assertThat(testSpringDictionaryEntity.getUpdatedBy()).isEqualTo("jcynezrTioGrHuDdNlTiHeSQiXYBSXpXYbYJ");
		assertThat(testSpringDictionaryEntity.getUpdatedIp()).isEqualTo("bPntVyfiWFVlWwFHSOSfZpJRnZIlfzSvyGIs");
		assertThat(testSpringDictionaryEntity.getUpdatedUserId()).isEqualTo("RPQXKKcRijtiSGBGpaHOHPOeyosjTpTwUnam");
		;
	}

	@Test
	void testUpdate() throws Exception {
		SpringDictionary entity = new SpringDictionary();
		entity.setCode("UiYmnwoAXzzwVmTOiMiUeRTDxIlZydUjePUg");
		entity.setTitle("zsSyjZVfmELXWzXDpgbjhLrknckqFuwGzdnh");
		entity.setDescription("DESocskbvUXpRhYCpRzydEYQUAOwJcRCMyfs");
		entity.setCreatedUserId("isTcTVPzRBrlcBSsoDiNLPcyJKHtBPQploAu");
		entity.setCreatedBy("mDIsABuCJkgnlFGZErHPqMSjFQFOOZzfHCce");
		entity.setCreatedIp("SGeStuCmVDpPXyVZdobzQAwpNtDPaHmtXpGZ");
		entity.setUpdatedUserId("kQecKrlKQiZINwjZIYaKwdMZHTndvbvMLHFX");
		entity.setUpdatedBy("dRKDYcLkyZDdZKcnZVOcOzUFUgSORQvBfCnP");
		entity.setUpdatedIp("auxIYhKRxApTdBDNHayMEidwnyLvjBfdaNeq");
		dao.saveAndFlush(entity);
		int databaseSizeBeforeUpdate = dao.findAll().size();
		SpringDictionary updatedEntity = dao.findById(entity.getId()).get();
		updatedEntity.setCode("lFEuqkLpgWatvjytznzaggyKocxsnknKpIeg");
		updatedEntity.setTitle("sFQmqHFzmiCaqyURUVRyucYZFTOfZdCzYCQL");
		updatedEntity.setDescription("UWVZTUwMSrzkNHmIgrojePYhjuMSMufjDGeh");
		updatedEntity.setCreatedUserId("HmSfbectdMMCOIWIQrUmXxgtLXmoPcXSmAFy");
		updatedEntity.setCreatedBy("lZwinvRPnHPyDXkBZsdavOUlzcOiLXgitqhe");
		updatedEntity.setCreatedIp("pJXWNptDfwkbMyfFrjGGlmhgjBQSVGSpDuEI");
		updatedEntity.setUpdatedUserId("cpnrGQGIgZPyEIGvtTvTyGGhsZgyvRZllgWJ");
		updatedEntity.setUpdatedBy("KtvoksTJpnWcoNCFOzLMNFHaHPBCupvOukgc");
		updatedEntity.setUpdatedIp("xQbrGvnQzvBUFFjWNhogGlNIvQKMRPVqRCWW");
		this.mvc.perform(post("/SpringDictionary/Edit").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(updatedEntity)))
				.andExpect(status().isOk());
		List<SpringDictionary> SpringDictionaryEntityList = dao.findAll();
		assertThat(SpringDictionaryEntityList).hasSize(databaseSizeBeforeUpdate);
		SpringDictionary testSpringDictionaryEntity = SpringDictionaryEntityList
				.get(SpringDictionaryEntityList.size() - 1);
		assertThat(testSpringDictionaryEntity.getCode()).isEqualTo("lFEuqkLpgWatvjytznzaggyKocxsnknKpIeg");
		assertThat(testSpringDictionaryEntity.getCreatedBy()).isEqualTo("lZwinvRPnHPyDXkBZsdavOUlzcOiLXgitqhe");
		assertThat(testSpringDictionaryEntity.getCreatedIp()).isEqualTo("pJXWNptDfwkbMyfFrjGGlmhgjBQSVGSpDuEI");
		assertThat(testSpringDictionaryEntity.getCreatedUserId()).isEqualTo("HmSfbectdMMCOIWIQrUmXxgtLXmoPcXSmAFy");
		assertThat(testSpringDictionaryEntity.getDescription()).isEqualTo("UWVZTUwMSrzkNHmIgrojePYhjuMSMufjDGeh");
		assertThat(testSpringDictionaryEntity.getTitle()).isEqualTo("sFQmqHFzmiCaqyURUVRyucYZFTOfZdCzYCQL");
		assertThat(testSpringDictionaryEntity.getUpdatedBy()).isEqualTo("KtvoksTJpnWcoNCFOzLMNFHaHPBCupvOukgc");
		assertThat(testSpringDictionaryEntity.getUpdatedIp()).isEqualTo("xQbrGvnQzvBUFFjWNhogGlNIvQKMRPVqRCWW");
		assertThat(testSpringDictionaryEntity.getUpdatedUserId()).isEqualTo("cpnrGQGIgZPyEIGvtTvTyGGhsZgyvRZllgWJ");
		;
	}

	@Test
	void testSetDeleted() throws Exception {
		SpringDictionary entity = new SpringDictionary();
		entity.setCode("BAxuCnDPCmDIEVuTVuzirHURQgmOUNSUNkBK");
		entity.setTitle("NxJAsoThhNOjFBOhDEXOfDsYlEdgoNOzIYor");
		entity.setDescription("gTdztwaycbdKZSkxAyWlycSMjIFQZJBMADaf");
		entity.setCreatedUserId("cCgXSoBKmZZxuhvnchlFYeOyIvkYbfMUmNMX");
		entity.setCreatedBy("kSFvrFaYlDhCCPjvjhEbJBznzoaeffdWaFMZ");
		entity.setCreatedIp("MbRGEbMuThvvCcwdFzsFdsrThEQNzSbTrwep");
		entity.setUpdatedUserId("QEAnYHeHABcelQeZQJBfnIoILqTnOYRTnYIk");
		entity.setUpdatedBy("rDWrmEoRSZOXSGxXOpPJHbcMtnUPUdSRiPXw");
		entity.setUpdatedIp("mVXxoDwsddknVJilVOVTmNKxwkaqhALrJEMx");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringDictionary/SetDeleted").param("ids", entity.getId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk());
	}

}
