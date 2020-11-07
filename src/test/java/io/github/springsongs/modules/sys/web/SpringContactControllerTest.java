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

import io.github.springsongs.modules.sys.domain.SpringContact;
import io.github.springsongs.modules.sys.repo.SpringContactRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithUserDetails("Administrator")
class SpringContactControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private SpringContactRepo dao;

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void testGetPage() throws Exception {
		SpringContact entity = new SpringContact();
		entity.setCompany("maWLwMVhQWQVLyAKpZlmJmZmCKFElXziWgHz");
		entity.setTitle("yeQhUKGEHbSgZfuhdrppdGSGgwAqHIFGSWCq");
		entity.setUsername("haTkaeJqeKzWkBINZoJVGLAeIjBuMyutOFIa");
		entity.setEmail("GIJVhzxOExRzFVkKhkFngYNpbaeoyaEtwMAW");
		entity.setWeb("FeWTJXzKFjtFmbyRRnHOUbsgPioqNVWqcIUz");
		entity.setFax("cjAbATVjXPOvlBQpPCGuernxsvSqtLAuSOrR");
		entity.setQq("pXitoozTfXtgXXHePvCZhfKvhjGeaeKshCbN");
		entity.setWebchat("YwxSQEWVddjsHESXYDFgRbKAjgRrzfUdZoQu");
		entity.setMobile("SnMOTcepkvnHFdKxgsrVcKCqmMPYQViJxqVf");
		entity.setTel("DqYbDgCqoYbXIFwOYFIxKKbSiLBPFiilnvWT");
		entity.setCreatedUserId("plFdChxnhbfUAzlpqkEIOHJVKkctTpaiJmto");
		entity.setCreatedBy("CqrHpHvUxYZXDXYDgqiEIujLpCCvMgNazrfH");
		entity.setCreatedIp("GrsiHfNOLHqShFzuJgCETROLAldknsEEtXrS");
		entity.setUpdatedUserId("efVseujfQLyrVCsypSTXoTRuLvqNaxWqADwt");
		entity.setUpdatedBy("purOsrdeMmUPbkuxIseivLtzrmSdsEojOPEn");
		entity.setUpdatedIp("EaYLRjMKVkdoPLDJVkCUjkCnxGPGuAXNnwpz");
		this.mvc.perform(post("/SpringContact/ListByPage").param("page", "1").param("limit", "20")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(entity))).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.[*].company").value(hasItem("maWLwMVhQWQVLyAKpZlmJmZmCKFElXziWgHz")))
				.andExpect(jsonPath("$.data.[*].createdBy").value(hasItem("CqrHpHvUxYZXDXYDgqiEIujLpCCvMgNazrfH")))
				.andExpect(jsonPath("$.data.[*].createdIp").value(hasItem("GrsiHfNOLHqShFzuJgCETROLAldknsEEtXrS")))
				.andExpect(jsonPath("$.data.[*].createdUserId").value(hasItem("plFdChxnhbfUAzlpqkEIOHJVKkctTpaiJmto")))
				.andExpect(jsonPath("$.data.[*].email").value(hasItem("GIJVhzxOExRzFVkKhkFngYNpbaeoyaEtwMAW")))
				.andExpect(jsonPath("$.data.[*].fax").value(hasItem("cjAbATVjXPOvlBQpPCGuernxsvSqtLAuSOrR")))
				.andExpect(jsonPath("$.data.[*].mobile").value(hasItem("SnMOTcepkvnHFdKxgsrVcKCqmMPYQViJxqVf")))
				.andExpect(jsonPath("$.data.[*].qq").value(hasItem("pXitoozTfXtgXXHePvCZhfKvhjGeaeKshCbN")))
				.andExpect(jsonPath("$.data.[*].tel").value(hasItem("DqYbDgCqoYbXIFwOYFIxKKbSiLBPFiilnvWT")))
				.andExpect(jsonPath("$.data.[*].title").value(hasItem("yeQhUKGEHbSgZfuhdrppdGSGgwAqHIFGSWCq")))
				.andExpect(jsonPath("$.data.[*].updatedBy").value(hasItem("purOsrdeMmUPbkuxIseivLtzrmSdsEojOPEn")))
				.andExpect(jsonPath("$.data.[*].updatedIp").value(hasItem("EaYLRjMKVkdoPLDJVkCUjkCnxGPGuAXNnwpz")))
				.andExpect(jsonPath("$.data.[*].updatedUserId").value(hasItem("efVseujfQLyrVCsypSTXoTRuLvqNaxWqADwt")))
				.andExpect(jsonPath("$.data.[*].username").value(hasItem("haTkaeJqeKzWkBINZoJVGLAeIjBuMyutOFIa")))
				.andExpect(jsonPath("$.data.[*].web").value(hasItem("FeWTJXzKFjtFmbyRRnHOUbsgPioqNVWqcIUz")))
				.andExpect(jsonPath("$.data.[*].webchat").value(hasItem("YwxSQEWVddjsHESXYDFgRbKAjgRrzfUdZoQu")));
	}

	@Test
	void testGet() throws Exception {
		SpringContact entity = new SpringContact();
		entity.setCompany("jvyeyhLcaJprLvphjQwKAemuDqIEHwVGeLDh");
		entity.setTitle("hLvYcAIExxodgvHRUufVezPFpYhCFUSFVQba");
		entity.setUsername("zGCxcmOSVUbUlTKyLYBoDannfENUgcDMveQl");
		entity.setEmail("OSZSsOgByQZTwiSemqWGKKkZLYzddmFLNvQR");
		entity.setWeb("tLSAWSPwGNuInoXGCoSIcHaSNKhcpjNHBSbS");
		entity.setFax("CDCPhHxrbZOGsVqpgURBiSJzEopchzNFEzdz");
		entity.setQq("tJnFtdQgAkVnfqLhJEYMdxcDJRuudXInrnEi");
		entity.setWebchat("ulTdeWKsTXIfOGopqlpcBuZeHNUXCnjzwbYA");
		entity.setMobile("LbLLbPozfzCBwOmiHTLSIlHYgpOGXgfYkOEB");
		entity.setTel("KPFXNQbkdUqGvOLpnTtTUNyKoKjkNTPPjyWK");
		entity.setCreatedUserId("jOAHwYgwWhRCWPHiASxGrfWoqpPHzLIYaqGv");
		entity.setCreatedBy("wJbckkMESiqgoRTlOZeAopfxSPOgLApNDCuk");
		entity.setCreatedIp("IZdgFDQhrlKCwyMXpfrkfwUnVwBFprEHEVmB");
		entity.setUpdatedUserId("FAYltkMlUVUgZuyMHyyeCBGwfSETUGyDnAsz");
		entity.setUpdatedBy("yTgVYhNcnGOtqNjpdiPgtroxulbWNbMjXSNB");
		entity.setUpdatedIp("RhiLIzkXeqUKCWYFcUTznNXiDHDaskbTzRRU");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringContact/Detail").param("id", entity.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$..company").value(hasItem("jvyeyhLcaJprLvphjQwKAemuDqIEHwVGeLDh")))
				.andExpect(jsonPath("$..createdBy").value(hasItem("wJbckkMESiqgoRTlOZeAopfxSPOgLApNDCuk")))
				.andExpect(jsonPath("$..createdIp").value(hasItem("IZdgFDQhrlKCwyMXpfrkfwUnVwBFprEHEVmB")))
				.andExpect(jsonPath("$..createdUserId").value(hasItem("jOAHwYgwWhRCWPHiASxGrfWoqpPHzLIYaqGv")))
				.andExpect(jsonPath("$..email").value(hasItem("OSZSsOgByQZTwiSemqWGKKkZLYzddmFLNvQR")))
				.andExpect(jsonPath("$..fax").value(hasItem("CDCPhHxrbZOGsVqpgURBiSJzEopchzNFEzdz")))
				.andExpect(jsonPath("$..mobile").value(hasItem("LbLLbPozfzCBwOmiHTLSIlHYgpOGXgfYkOEB")))
				.andExpect(jsonPath("$..qq").value(hasItem("tJnFtdQgAkVnfqLhJEYMdxcDJRuudXInrnEi")))
				.andExpect(jsonPath("$..tel").value(hasItem("KPFXNQbkdUqGvOLpnTtTUNyKoKjkNTPPjyWK")))
				.andExpect(jsonPath("$..title").value(hasItem("hLvYcAIExxodgvHRUufVezPFpYhCFUSFVQba")))
				.andExpect(jsonPath("$..updatedBy").value(hasItem("yTgVYhNcnGOtqNjpdiPgtroxulbWNbMjXSNB")))
				.andExpect(jsonPath("$..updatedIp").value(hasItem("RhiLIzkXeqUKCWYFcUTznNXiDHDaskbTzRRU")))
				.andExpect(jsonPath("$..updatedUserId").value(hasItem("FAYltkMlUVUgZuyMHyyeCBGwfSETUGyDnAsz")))
				.andExpect(jsonPath("$..username").value(hasItem("zGCxcmOSVUbUlTKyLYBoDannfENUgcDMveQl")))
				.andExpect(jsonPath("$..web").value(hasItem("tLSAWSPwGNuInoXGCoSIcHaSNKhcpjNHBSbS")))
				.andExpect(jsonPath("$..webchat").value(hasItem("ulTdeWKsTXIfOGopqlpcBuZeHNUXCnjzwbYA")));
	}

	@Test
	void testSave() throws Exception {
		int databaseSizeBeforeCreate = dao.findAll().size();
		SpringContact entity = new SpringContact();
		entity.setCompany("ZpEySVaBNrUWfqIxQuPeopIKOXYHSumBsmxl");
		entity.setTitle("dLpbcuwiINGvmnWMfEQjebDQhPoUKqAuDEhi");
		entity.setUsername("sXVbjgxSSaHmpuDDFZAlGOKfFFvyNkrwPhsF");
		entity.setEmail("PmqiUhcTmCJoNYEuISWAlQhhkIkTTEQyhJLg");
		entity.setWeb("ErGrlogkIUxFGWnEtxjqNuYMrIjIrkvFNMYE");
		entity.setFax("OcjjxXrGWrvwIDUEHfruSfmYTxOdkPQBvOJu");
		entity.setQq("CrIaClUESvZKXMZwQmZpXdclaJjnYMGKgpOn");
		entity.setWebchat("pvISRVsNimedWEgFTYBLNbbXRLuFiSyKIVHu");
		entity.setMobile("MVHzUwAlsGjqSfnNPPQAeOkwiNLoXzQDXses");
		entity.setTel("VsndAOaucfxrkMcGqxiozsLuaNgjuFUlPwkE");
		entity.setCreatedUserId("UNEVjyILePZfpxcOObNIjIpRnxJllZAMvEaz");
		entity.setCreatedBy("gXiaNbQQqzsCRokoRUTBvbLaLjTlJPzdGGZT");
		entity.setCreatedIp("vpIVVkUcROFIrpnKYvRZNwlVJixJYjXzrhie");
		entity.setUpdatedUserId("VIdxAYUerRSrseLBknumtFmgMczTVaYujYOi");
		entity.setUpdatedBy("LgMWILQJtCnTmCWxrNfBtPBFmerXUVSGYhoi");
		entity.setUpdatedIp("tVhuyzSfuQkFxaQMbekeKxlXCceJbwzMiMrr");
		this.mvc.perform(post("/SpringContact/Create").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(entity))).andExpect(status().isOk());
		List<SpringContact> SpringContactEntityList = dao.findAll();
		assertThat(SpringContactEntityList).hasSize(databaseSizeBeforeCreate + 1);
		SpringContact testSpringContactEntity = SpringContactEntityList
				.get(SpringContactEntityList.size() - 1);
		assertThat(testSpringContactEntity.getCompany()).isEqualTo("ZpEySVaBNrUWfqIxQuPeopIKOXYHSumBsmxl");
		assertThat(testSpringContactEntity.getCreatedBy()).isEqualTo("gXiaNbQQqzsCRokoRUTBvbLaLjTlJPzdGGZT");
		assertThat(testSpringContactEntity.getCreatedIp()).isEqualTo("vpIVVkUcROFIrpnKYvRZNwlVJixJYjXzrhie");
		assertThat(testSpringContactEntity.getCreatedUserId()).isEqualTo("UNEVjyILePZfpxcOObNIjIpRnxJllZAMvEaz");
		assertThat(testSpringContactEntity.getEmail()).isEqualTo("PmqiUhcTmCJoNYEuISWAlQhhkIkTTEQyhJLg");
		assertThat(testSpringContactEntity.getFax()).isEqualTo("OcjjxXrGWrvwIDUEHfruSfmYTxOdkPQBvOJu");
		assertThat(testSpringContactEntity.getMobile()).isEqualTo("MVHzUwAlsGjqSfnNPPQAeOkwiNLoXzQDXses");
		assertThat(testSpringContactEntity.getQq()).isEqualTo("CrIaClUESvZKXMZwQmZpXdclaJjnYMGKgpOn");
		assertThat(testSpringContactEntity.getTel()).isEqualTo("VsndAOaucfxrkMcGqxiozsLuaNgjuFUlPwkE");
		assertThat(testSpringContactEntity.getTitle()).isEqualTo("dLpbcuwiINGvmnWMfEQjebDQhPoUKqAuDEhi");
		assertThat(testSpringContactEntity.getUpdatedBy()).isEqualTo("LgMWILQJtCnTmCWxrNfBtPBFmerXUVSGYhoi");
		assertThat(testSpringContactEntity.getUpdatedIp()).isEqualTo("tVhuyzSfuQkFxaQMbekeKxlXCceJbwzMiMrr");
		assertThat(testSpringContactEntity.getUpdatedUserId()).isEqualTo("VIdxAYUerRSrseLBknumtFmgMczTVaYujYOi");
		assertThat(testSpringContactEntity.getUsername()).isEqualTo("sXVbjgxSSaHmpuDDFZAlGOKfFFvyNkrwPhsF");
		assertThat(testSpringContactEntity.getWeb()).isEqualTo("ErGrlogkIUxFGWnEtxjqNuYMrIjIrkvFNMYE");
		assertThat(testSpringContactEntity.getWebchat()).isEqualTo("pvISRVsNimedWEgFTYBLNbbXRLuFiSyKIVHu");
		;
	}

	@Test
	void testUpdate() throws Exception {
		SpringContact entity = new SpringContact();
		entity.setCompany("HnYrvPyWtyuEAyBfTkrsDFqfsbZMluPQOqGY");
		entity.setTitle("cdGCiVoNlGKVWfPVnlPriCMyhatIgKhZzdYZ");
		entity.setUsername("RcqDJMStZwdDNamkFiUmEYjKymBiMlXimkst");
		entity.setEmail("WstXQCzhaFIpSTGOAsJNiwKDPgnhLsMTSEAk");
		entity.setWeb("nitxrFZjrndoEFvMmwXOHLkXcsjktjfHyZQv");
		entity.setFax("CUXDGkpETFMEMQYfALVPXlLLDzAlhUgLeZvu");
		entity.setQq("NnvmrRhzUDOrvHIjqfGOkSCaWvfXDtlzCBQA");
		entity.setWebchat("XCJByMxntNxCsVMOQvaXjyNitKCQKHqhnZYT");
		entity.setMobile("GyIHaqPbqLaRTSFDEFqVAthFoDqraUQmUvQN");
		entity.setTel("dxQTkQVSqLonkioMxZVYePtQABroOWnsukjD");
		entity.setCreatedUserId("MBaMgUsGPgGGvsjhTXgMjzOYEJfgNaIaJDza");
		entity.setCreatedBy("vjzAqMwsNeGEbTUAujxTalZFhbrnZRRPffoz");
		entity.setCreatedIp("aVGLiFMeAkpNYGbCdvVPbhynwtgoxqeSvcNF");
		entity.setUpdatedUserId("sSoFiQQIvOdjZXantCiJkBGaeVqRVwJtFNqJ");
		entity.setUpdatedBy("LoFIwAKBcusMHRBDOsbWekLwkTZfmdjvOyAr");
		entity.setUpdatedIp("xeavkyLguKgthexoJhLqScxkuFQbpjGHCsVa");
		dao.saveAndFlush(entity);
		int databaseSizeBeforeUpdate = dao.findAll().size();
		SpringContact updatedEntity = dao.findById(entity.getId()).get();
		updatedEntity.setCompany("jelJYinWAlTHXlszAFANjNqSqOiNcfpohzlO");
		updatedEntity.setTitle("nYvzYiOUdNCZKQnQISdOpaZBKzIaAwQTnZNX");
		updatedEntity.setUsername("qwUEHwegCyVAYHRmJwyFJnnLBpkUdTJBWKZE");
		updatedEntity.setEmail("JtxBuBmyVTuBfgtHcWIeIepzQGVWVnENasxr");
		updatedEntity.setWeb("YQaYqEFwDGCvlWjtuCwhZhElhdNVCCwnCjHh");
		updatedEntity.setFax("HgjXEinZHsRFsGVtjSLoFcRfUUpoayhGqorK");
		updatedEntity.setQq("zzDHcgiJHNHWeAmSYmwQPNzMqAnBObBLJplh");
		updatedEntity.setWebchat("lKjKnXgSQbddXCtmAmIuSLHmpNTOWxdEUQdt");
		updatedEntity.setMobile("LzUGcubFIapfpSzwLDwWRDazGsFeDLcDMoRd");
		updatedEntity.setTel("DSsIEQzJpKjHpssJlkpzjPlajsBIYTDOHwJk");
		updatedEntity.setCreatedUserId("hTYJiqRNQCyktkyHegNglpbAtnDLSBBfNGTz");
		updatedEntity.setCreatedBy("rXoaqgiELWSvbnybsYIQxdzApVieXAejHmJm");
		updatedEntity.setCreatedIp("DhRAytlaYzLhkuUmaEnUVSoqViUfikgAyDcq");
		updatedEntity.setUpdatedUserId("yaUiziPvhZXXKWeWqpBLQimzwiYRVNXGxEeL");
		updatedEntity.setUpdatedBy("oIFzQABHqRrBDBwMtOIwebMziRtyluSWUUiB");
		updatedEntity.setUpdatedIp("zUTPmqZaCGcPmNcfTpZNLLbBFoEdTpefZkVO");
		this.mvc.perform(post("/SpringContact/Edit").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(updatedEntity)))
				.andExpect(status().isOk());
		List<SpringContact> SpringContactEntityList = dao.findAll();
		assertThat(SpringContactEntityList).hasSize(databaseSizeBeforeUpdate);
		SpringContact testSpringContactEntity = SpringContactEntityList
				.get(SpringContactEntityList.size() - 1);
		assertThat(testSpringContactEntity.getCompany()).isEqualTo("jelJYinWAlTHXlszAFANjNqSqOiNcfpohzlO");
		assertThat(testSpringContactEntity.getCreatedBy()).isEqualTo("rXoaqgiELWSvbnybsYIQxdzApVieXAejHmJm");
		assertThat(testSpringContactEntity.getCreatedIp()).isEqualTo("DhRAytlaYzLhkuUmaEnUVSoqViUfikgAyDcq");
		assertThat(testSpringContactEntity.getCreatedUserId()).isEqualTo("hTYJiqRNQCyktkyHegNglpbAtnDLSBBfNGTz");
		assertThat(testSpringContactEntity.getEmail()).isEqualTo("JtxBuBmyVTuBfgtHcWIeIepzQGVWVnENasxr");
		assertThat(testSpringContactEntity.getFax()).isEqualTo("HgjXEinZHsRFsGVtjSLoFcRfUUpoayhGqorK");
		assertThat(testSpringContactEntity.getMobile()).isEqualTo("LzUGcubFIapfpSzwLDwWRDazGsFeDLcDMoRd");
		assertThat(testSpringContactEntity.getQq()).isEqualTo("zzDHcgiJHNHWeAmSYmwQPNzMqAnBObBLJplh");
		assertThat(testSpringContactEntity.getTel()).isEqualTo("DSsIEQzJpKjHpssJlkpzjPlajsBIYTDOHwJk");
		assertThat(testSpringContactEntity.getTitle()).isEqualTo("nYvzYiOUdNCZKQnQISdOpaZBKzIaAwQTnZNX");
		assertThat(testSpringContactEntity.getUpdatedBy()).isEqualTo("oIFzQABHqRrBDBwMtOIwebMziRtyluSWUUiB");
		assertThat(testSpringContactEntity.getUpdatedIp()).isEqualTo("zUTPmqZaCGcPmNcfTpZNLLbBFoEdTpefZkVO");
		assertThat(testSpringContactEntity.getUpdatedUserId()).isEqualTo("yaUiziPvhZXXKWeWqpBLQimzwiYRVNXGxEeL");
		assertThat(testSpringContactEntity.getUsername()).isEqualTo("qwUEHwegCyVAYHRmJwyFJnnLBpkUdTJBWKZE");
		assertThat(testSpringContactEntity.getWeb()).isEqualTo("YQaYqEFwDGCvlWjtuCwhZhElhdNVCCwnCjHh");
		assertThat(testSpringContactEntity.getWebchat()).isEqualTo("lKjKnXgSQbddXCtmAmIuSLHmpNTOWxdEUQdt");
		;
	}

	@Test
	void testSetDeleted() throws Exception {
		SpringContact entity = new SpringContact();
		entity.setCompany("OrlcdoFVojZBUwiXEJuYCOnSlPvlFfDhWjcE");
		entity.setTitle("QYrJFcKEuAWjsCWczSLOrhvjOnVcPnrPvXHU");
		entity.setUsername("gLGKMTGMzbTcYbyrSMaudcSroJQOIQNHySEn");
		entity.setEmail("SNASaGeKNeRyLCvoYFKDLcnbUJQghrGKbRUr");
		entity.setWeb("MeyOogFYYHJGGpTbfqOmrcEXKEbuXOhpYDYU");
		entity.setFax("LyVnVwywOtGzHzxKdAgZwXWbMXvCjkDijnjn");
		entity.setQq("CUnlIAQFziNQSxXEsmIWfYNujvixBDGGnjmO");
		entity.setWebchat("fTPFWnPgJidUmPhyTDkoayBzLRBPsJjJmQIk");
		entity.setMobile("CxLgXdOgmqbFquAotiHkJLXoVlQLIBuqlJzw");
		entity.setTel("oGqaIelaQcJtWXGxBNPhcWHMdgrGcdAbbyGN");
		entity.setCreatedUserId("jyZmGJcgIYunHEPBrXFDEhTaomdBEFTyAozT");
		entity.setCreatedBy("iLfqdPqsDIeAXDTIHcgHsvnGfZMisltpYTGm");
		entity.setCreatedIp("cGvtXBoeNrScyYcvuQBrySAyKJSYUjmBLubV");
		entity.setUpdatedUserId("pGLEoADwrLrpIxMZdzpBtXNJrYowvyhEXXlT");
		entity.setUpdatedBy("FNdzYHxWItVdZBMPXSKrNmZllCUUwCHJWCns");
		entity.setUpdatedIp("NroXSyNBJJrCqGwSMxnsfKxDamsSrRlNPuya");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringContact/SetDeleted").param("ids", entity.getId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk());
	}

}
