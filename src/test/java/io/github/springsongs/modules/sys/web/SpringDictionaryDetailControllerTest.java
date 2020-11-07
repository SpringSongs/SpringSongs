package io.github.springsongs.modules.sys.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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

import io.github.springsongs.modules.sys.domain.SpringDictionaryDetail;
import io.github.springsongs.modules.sys.repo.SpringDictionaryDetailRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithUserDetails("Administrator")
class SpringDictionaryDetailControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private SpringDictionaryDetailRepo dao;

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	void testGetPage() throws Exception {
		SpringDictionaryDetail entity = new SpringDictionaryDetail();
		entity.setDictionaryCode("KAfsbjqxteMsRvnJqWPikTshHQmhSEmVuQup");
		entity.setParentId("RFJMiMrWKoZAKkMjnMDrKBhuGGasSYngOEzO");
		entity.setDetailCode("PTlYqUptcCwKHUFNgRfTnfTntMIQKmJGlFzw");
		entity.setDetailName("bChISmpIXNfXVLeHNPHvjbabwgSvYIytRFve");
		entity.setDetailValue("tlseMJckKJPOyqNejZcuXyjmSJFRLHWwHGjp");
		entity.setDescription("AtbHVgTyhKYEDcPRTrQHgyHzlJQfCHSrfhxu");
		entity.setChildIds("CbCKARotGWkoyAvTbapOEjKpJzWeabTEUsbd");
		entity.setCreatedUserId("jmmgENfEZdvBGBVJwKiBTMJRIvJasqzHescT");
		entity.setCreatedBy("OnSthglMWnYGfEeSCsTMSiCbLcgvgkgzYXSS");
		entity.setCreatedIp("hFISSbhujqERRORgxQvRbBXwYGEvfLQmUECy");
		entity.setUpdatedUserId("vXGgQrZpeVUfQLhAZxRIaNxrApFUbguvLraQ");
		entity.setUpdatedBy("jDUmjiRefPWZUnBokYTrUNkYejaiMGniaqpv");
		entity.setUpdatedIp("yRzcfbrnYEnvmlkCcqoSuSoBUojebZncsxxV");
		this.mvc.perform(post("/BaseDictionaryDetail/ListByPage").param("page", "1").param("limit", "20")
				.with(csrf().useInvalidToken()).with(user("Administrator").password("qweasd").roles("Administrators"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(entity))).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.[*].childIds").value(hasItem("CbCKARotGWkoyAvTbapOEjKpJzWeabTEUsbd")))
				.andExpect(jsonPath("$.data.[*].createdBy").value(hasItem("OnSthglMWnYGfEeSCsTMSiCbLcgvgkgzYXSS")))
				.andExpect(jsonPath("$.data.[*].createdIp").value(hasItem("hFISSbhujqERRORgxQvRbBXwYGEvfLQmUECy")))
				.andExpect(jsonPath("$.data.[*].createdUserId").value(hasItem("jmmgENfEZdvBGBVJwKiBTMJRIvJasqzHescT")))
				.andExpect(jsonPath("$.data.[*].dictionaryCode").value(hasItem("KAfsbjqxteMsRvnJqWPikTshHQmhSEmVuQup")))
				.andExpect(jsonPath("$.data.[*].itemCode").value(hasItem("PTlYqUptcCwKHUFNgRfTnfTntMIQKmJGlFzw")))
				.andExpect(jsonPath("$.data.[*].itemDesc").value(hasItem("AtbHVgTyhKYEDcPRTrQHgyHzlJQfCHSrfhxu")))
				.andExpect(jsonPath("$.data.[*].itemName").value(hasItem("bChISmpIXNfXVLeHNPHvjbabwgSvYIytRFve")))
				.andExpect(jsonPath("$.data.[*].itemValue").value(hasItem("tlseMJckKJPOyqNejZcuXyjmSJFRLHWwHGjp")))
				.andExpect(jsonPath("$.data.[*].parentId").value(hasItem("RFJMiMrWKoZAKkMjnMDrKBhuGGasSYngOEzO")))
				.andExpect(jsonPath("$.data.[*].updatedBy").value(hasItem("jDUmjiRefPWZUnBokYTrUNkYejaiMGniaqpv")))
				.andExpect(jsonPath("$.data.[*].updatedIp").value(hasItem("yRzcfbrnYEnvmlkCcqoSuSoBUojebZncsxxV")))
				.andExpect(jsonPath("$.data.[*].updatedUserId").value(hasItem("vXGgQrZpeVUfQLhAZxRIaNxrApFUbguvLraQ")));
	}

	@Test
	void testGet() throws Exception {
		SpringDictionaryDetail entity = new SpringDictionaryDetail();
		entity.setDictionaryCode("avhMSFZfwJjfpQvyfeUYywUAorIkMrDuNgzF");
		entity.setParentId("AjpLOvgEXQcBHsNNrGtqJEjSMGiWvTSnaZMD");
		entity.setDetailCode("SxZcyyfBQrMCZFBOaOQhilRcpGskvCMDlubj");
		entity.setDetailName("meDKUfJdJSWfRwJWzleuRWBubeeLXGqIqeos");
		entity.setDetailValue("XZRTicTLfTloKBMKfOabsNnWdvYcIVhbcGOb");
		entity.setDescription("TQNKYtYZWyHAhFXYRuVJyxHfObVUJmNLlWDp");
		entity.setChildIds("FezrSvKFscCIhSkgoajtYuByPRPvfwXYhFvh");
		entity.setCreatedUserId("OahKTVBTvSLleKRrGfBQOfcwzhXebsnwhCZc");
		entity.setCreatedBy("EWisYFaOUzSSZmMsFkufsykLrRaBJqHRFBfH");
		entity.setCreatedIp("AeDnMqLSAXhtBlrLqpOmZEDsZNeAgPUhNPLQ");
		entity.setUpdatedUserId("wBrBkyaKvGoKqieKACagQYIiDtynZCPWRUPz");
		entity.setUpdatedBy("iNncFMTpOclWEfzhkBKWASEajGsueBiJUlfn");
		entity.setUpdatedIp("djRowBvGqEwQqFstELaOFRcgbDWrTcwlhtFT");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/BaseDictionaryDetail/Detail").param("id", entity.getId()).with(csrf().useInvalidToken())
				.with(user("Administrator").password("qweasd").roles("Administrators"))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$..childIds").value(hasItem("FezrSvKFscCIhSkgoajtYuByPRPvfwXYhFvh")))
				.andExpect(jsonPath("$..createdBy").value(hasItem("EWisYFaOUzSSZmMsFkufsykLrRaBJqHRFBfH")))
				.andExpect(jsonPath("$..createdIp").value(hasItem("AeDnMqLSAXhtBlrLqpOmZEDsZNeAgPUhNPLQ")))
				.andExpect(jsonPath("$..createdUserId").value(hasItem("OahKTVBTvSLleKRrGfBQOfcwzhXebsnwhCZc")))
				.andExpect(jsonPath("$..dictionaryCode").value(hasItem("avhMSFZfwJjfpQvyfeUYywUAorIkMrDuNgzF")))
				.andExpect(jsonPath("$..itemCode").value(hasItem("SxZcyyfBQrMCZFBOaOQhilRcpGskvCMDlubj")))
				.andExpect(jsonPath("$..itemDesc").value(hasItem("TQNKYtYZWyHAhFXYRuVJyxHfObVUJmNLlWDp")))
				.andExpect(jsonPath("$..itemName").value(hasItem("meDKUfJdJSWfRwJWzleuRWBubeeLXGqIqeos")))
				.andExpect(jsonPath("$..itemValue").value(hasItem("XZRTicTLfTloKBMKfOabsNnWdvYcIVhbcGOb")))
				.andExpect(jsonPath("$..parentId").value(hasItem("AjpLOvgEXQcBHsNNrGtqJEjSMGiWvTSnaZMD")))
				.andExpect(jsonPath("$..updatedBy").value(hasItem("iNncFMTpOclWEfzhkBKWASEajGsueBiJUlfn")))
				.andExpect(jsonPath("$..updatedIp").value(hasItem("djRowBvGqEwQqFstELaOFRcgbDWrTcwlhtFT")))
				.andExpect(jsonPath("$..updatedUserId").value(hasItem("wBrBkyaKvGoKqieKACagQYIiDtynZCPWRUPz")));
	}

	@Test
	void testSave() throws Exception {
		int databaseSizeBeforeCreate = dao.findAll().size();
		SpringDictionaryDetail entity = new SpringDictionaryDetail();
		entity.setDictionaryCode("gEfzzKLHoNEavsJZaRpeByyedQoExRThbeFs");
		entity.setParentId("wOKeQbJCMbBUwgJWiquIRhFYDSoiIqqmnZGP");
		entity.setDetailCode("NTMNObEXkAVbCWtZkdWVIStVHzzDUkEqFIse");
		entity.setDetailName("gQqpqEXUWqkPUuWWPvjthHxQzPmaIUmxUyJc");
		entity.setDetailValue("fdSuQiSAirezntskxKZiuxdQYsXkOJLllAkX");
		entity.setDescription("RExAmqWypwPXdbBUBlRdtKysfgDqKELUOcaw");
		entity.setChildIds("VzcfJsFCcQRsxqzRNklMZSwMmLrDEoodyrhC");
		entity.setCreatedUserId("KmeHEsryUOFidrMoQoDUtfKkwEYfAGlDohCe");
		entity.setCreatedBy("HAZPnZNesqyIErvsfTQsVtpxLgWcQnxwtDKn");
		entity.setCreatedIp("HKkzECBlRbhZdSNhPrHKmAkFqWqlLGOohFxN");
		entity.setUpdatedUserId("MZnCqapBvrDAcYQjaUSqIopmSNVrzKfLfkaM");
		entity.setUpdatedBy("uiOwQKTpZFeYYPqHoeBpmQYkPtgpfSCnQgOp");
		entity.setUpdatedIp("ziXVlUMJENAEKxpXLEuDVPkUSGIoDxeUOsWE");
		this.mvc.perform(post("/BaseDictionaryDetail/Create").with(csrf().useInvalidToken())
				.with(user("Administrator").password("qweasd").roles("Administrators"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(entity))).andExpect(status().isOk());
		List<SpringDictionaryDetail> baseDictionaryDetailEntityList = dao.findAll();
		assertThat(baseDictionaryDetailEntityList).hasSize(databaseSizeBeforeCreate + 1);
		SpringDictionaryDetail testBaseDictionaryDetailEntity = baseDictionaryDetailEntityList
				.get(baseDictionaryDetailEntityList.size() - 1);
		assertThat(testBaseDictionaryDetailEntity.getChildIds()).isEqualTo("VzcfJsFCcQRsxqzRNklMZSwMmLrDEoodyrhC");
		assertThat(testBaseDictionaryDetailEntity.getCreatedBy()).isEqualTo("HAZPnZNesqyIErvsfTQsVtpxLgWcQnxwtDKn");
		assertThat(testBaseDictionaryDetailEntity.getCreatedIp()).isEqualTo("HKkzECBlRbhZdSNhPrHKmAkFqWqlLGOohFxN");
		assertThat(testBaseDictionaryDetailEntity.getCreatedUserId()).isEqualTo("KmeHEsryUOFidrMoQoDUtfKkwEYfAGlDohCe");
		assertThat(testBaseDictionaryDetailEntity.getDictionaryCode())
				.isEqualTo("gEfzzKLHoNEavsJZaRpeByyedQoExRThbeFs");
		assertThat(testBaseDictionaryDetailEntity.getDetailCode()).isEqualTo("NTMNObEXkAVbCWtZkdWVIStVHzzDUkEqFIse");
		assertThat(testBaseDictionaryDetailEntity.getDetailName()).isEqualTo("RExAmqWypwPXdbBUBlRdtKysfgDqKELUOcaw");
		assertThat(testBaseDictionaryDetailEntity.getDetailValue()).isEqualTo("gQqpqEXUWqkPUuWWPvjthHxQzPmaIUmxUyJc");
		assertThat(testBaseDictionaryDetailEntity.getDescription()).isEqualTo("fdSuQiSAirezntskxKZiuxdQYsXkOJLllAkX");
		assertThat(testBaseDictionaryDetailEntity.getParentId()).isEqualTo("wOKeQbJCMbBUwgJWiquIRhFYDSoiIqqmnZGP");
		assertThat(testBaseDictionaryDetailEntity.getUpdatedBy()).isEqualTo("uiOwQKTpZFeYYPqHoeBpmQYkPtgpfSCnQgOp");
		assertThat(testBaseDictionaryDetailEntity.getUpdatedIp()).isEqualTo("ziXVlUMJENAEKxpXLEuDVPkUSGIoDxeUOsWE");
		assertThat(testBaseDictionaryDetailEntity.getUpdatedUserId()).isEqualTo("MZnCqapBvrDAcYQjaUSqIopmSNVrzKfLfkaM");
		;
	}

	@Test
	void testUpdate() throws Exception {
		SpringDictionaryDetail entity = new SpringDictionaryDetail();
		entity.setDictionaryCode("jWZOUiSYgjeNbpYuufWUlWBWiHZOITKIKPtL");
		entity.setParentId("QPzbIIfmMUmxVewDuhOfFyeYXrEXoCCgCjgi");
		entity.setDetailCode("UVqoBSVjsuvUWmDCOPCFuEkXPejOAhRtRUlp");
		entity.setDetailName("sKBLeYAStkAjpdcdlCqFJtcjsqvvmmIRjZww");
		entity.setDetailValue("ygtaqVrWupJtjiAMUZsHbdQepPBpzevGSApW");
		entity.setDescription("NFzZmRkauApolpTwyWMhuZnUjiJvgPsHIOjo");
		entity.setChildIds("OilgAdrBeJCDXFYUaVDBgxohoypOnFoEOiyR");
		entity.setCreatedUserId("WYKSQxnfVFJqUElDnnoihtytFfQWnDsVZkXq");
		entity.setCreatedBy("kDUBbYlaXQDWsdSEArdUXjaWLDbJroBkPhPq");
		entity.setCreatedIp("kamuhigagEnbhEqwoGopASpmNZiFVGAXxkfy");
		entity.setUpdatedUserId("OlSBSSYgmORTxNKnEPpnRclYMKCCFXTfaLmV");
		entity.setUpdatedBy("QpWWGTmmclSEBweSvWJGCoDPgoemeskjXePD");
		entity.setUpdatedIp("gPYsBkyRYQcDWVMPrMMERoOzrWSEJDbLjsRt");
		dao.saveAndFlush(entity);
		int databaseSizeBeforeUpdate = dao.findAll().size();
		SpringDictionaryDetail updatedEntity = dao.findById(entity.getId()).get();
		updatedEntity.setDictionaryCode("TPIsULOIZaIiuxPFlnkWhhQiVyWKMshoQSZB");
		updatedEntity.setParentId("dkTnkwyRqKwxPeARhNQaLtHvVwYjxMYqubUl");
		updatedEntity.setDetailCode("xfeiEJtoxmfFGFgfzjrBLNoVlXIXvzalhZnd");
		updatedEntity.setDetailName("MupPVpEFsOgTWMfYCTMwbOSIPxevzMpyjMkR");
		updatedEntity.setDetailValue("LBkAxglAPLoQnWbqMZXKyRteJRjTAhoVeuDy");
		updatedEntity.setDescription("HIjuuYFkMZErBgvbDbaPTOsvSHrbKQcwJyNo");
		updatedEntity.setChildIds("pgyblXcrrSQKSKKtRbJUDpMMlbcpMPWCjkhW");
		updatedEntity.setCreatedUserId("PywQoHtCttCeEZVnWIcdCncrotWFSNRNoqpB");
		updatedEntity.setCreatedBy("vVVzfekChFfVRBhkPgsQPpvlfJqVyDbQqYCj");
		updatedEntity.setCreatedIp("oNaNXfaVsmNFDGvmFeBkGmAQqkjQZcVvywMA");
		updatedEntity.setUpdatedUserId("vzJuqTNBHbcykVtZpEhsIgHlBTyUeEebYIXZ");
		updatedEntity.setUpdatedBy("dmlCApOVBxTPoEuKRTXjhrxyJtObILPhCawW");
		updatedEntity.setUpdatedIp("WYgsJUCJnorRxqfbtrAigrswKKLddXFzisPF");
		this.mvc.perform(post("/BaseDictionaryDetail/Edit").with(csrf().useInvalidToken())
				.with(user("Administrator").password("qweasd").roles("Administrators"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(updatedEntity))).andExpect(status().isOk());
		List<SpringDictionaryDetail> baseDictionaryDetailEntityList = dao.findAll();
		assertThat(baseDictionaryDetailEntityList).hasSize(databaseSizeBeforeUpdate);
		SpringDictionaryDetail testBaseDictionaryDetailEntity = baseDictionaryDetailEntityList
				.get(baseDictionaryDetailEntityList.size() - 1);
		assertThat(testBaseDictionaryDetailEntity.getChildIds()).isEqualTo("pgyblXcrrSQKSKKtRbJUDpMMlbcpMPWCjkhW");
		assertThat(testBaseDictionaryDetailEntity.getCreatedBy()).isEqualTo("vVVzfekChFfVRBhkPgsQPpvlfJqVyDbQqYCj");
		assertThat(testBaseDictionaryDetailEntity.getCreatedIp()).isEqualTo("oNaNXfaVsmNFDGvmFeBkGmAQqkjQZcVvywMA");
		assertThat(testBaseDictionaryDetailEntity.getCreatedUserId()).isEqualTo("PywQoHtCttCeEZVnWIcdCncrotWFSNRNoqpB");
		assertThat(testBaseDictionaryDetailEntity.getDictionaryCode())
				.isEqualTo("TPIsULOIZaIiuxPFlnkWhhQiVyWKMshoQSZB");
		assertThat(testBaseDictionaryDetailEntity.getDetailCode()).isEqualTo("xfeiEJtoxmfFGFgfzjrBLNoVlXIXvzalhZnd");
		assertThat(testBaseDictionaryDetailEntity.getDetailName()).isEqualTo("HIjuuYFkMZErBgvbDbaPTOsvSHrbKQcwJyNo");
		assertThat(testBaseDictionaryDetailEntity.getDetailValue()).isEqualTo("MupPVpEFsOgTWMfYCTMwbOSIPxevzMpyjMkR");
		assertThat(testBaseDictionaryDetailEntity.getDescription()).isEqualTo("LBkAxglAPLoQnWbqMZXKyRteJRjTAhoVeuDy");
		assertThat(testBaseDictionaryDetailEntity.getParentId()).isEqualTo("dkTnkwyRqKwxPeARhNQaLtHvVwYjxMYqubUl");
		assertThat(testBaseDictionaryDetailEntity.getUpdatedBy()).isEqualTo("dmlCApOVBxTPoEuKRTXjhrxyJtObILPhCawW");
		assertThat(testBaseDictionaryDetailEntity.getUpdatedIp()).isEqualTo("WYgsJUCJnorRxqfbtrAigrswKKLddXFzisPF");
		assertThat(testBaseDictionaryDetailEntity.getUpdatedUserId()).isEqualTo("vzJuqTNBHbcykVtZpEhsIgHlBTyUeEebYIXZ");
		;
	}

	@Test
	void testSetDeleted() throws Exception {
		SpringDictionaryDetail entity = new SpringDictionaryDetail();
		entity.setDictionaryCode("nOKYvTbumokpDEkVeKncCUsWHzGpLGiyotDu");
		entity.setParentId("MfElnTfxDsHONOrgaweTuRiRiVDWuasOpHtJ");
		entity.setDetailCode("DcwdVXJITBsSVTLZSkBZXKPiDNSxkzWKRitW");
		entity.setDetailName("aTDBgbiSITXfvGMBFHZbWGzcycKvbYQGiJWO");
		entity.setDetailValue("eLGHpobPhdeIUZSRMEJExJMmOuFchUzlcBru");
		entity.setDescription("HbaAmogxBbnTfYFrPqOvvYsCqFihYUeUSNJu");
		entity.setChildIds("YMBhNOTHOsCmXfYUDaNcGoVauGgKFrGvxVdb");
		entity.setCreatedUserId("GGHjKXpKdruhzyZNQUTHxCImOLjBAMqODBEh");
		entity.setCreatedBy("oxsivKTBPiWewmZZAjzbDvyQAMisPgDsJmyE");
		entity.setCreatedIp("ubOqhOFGzhGuuYjVXYyrdLlAnRkAoqchdjrT");
		entity.setUpdatedUserId("GKNzNHWUoCXNgQrzPFqGWWplZenzMDJcpycm");
		entity.setUpdatedBy("UOplmwkRgRlaqnrfiHtyiWCnleepVGyvDBtj");
		entity.setUpdatedIp("LAEjkWjrydfzLMNvIWlQWAPYhvcExRnLXZrQ");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/BaseDictionaryDetail/SetDeleted").with(csrf().useInvalidToken())
				.with(user("Administrator").password("qweasd").roles("Administrators")).param("ids", entity.getId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk());
	}
}
