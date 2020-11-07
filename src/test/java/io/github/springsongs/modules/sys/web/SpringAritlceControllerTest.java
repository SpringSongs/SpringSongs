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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;

import io.github.springsongs.modules.sys.domain.SpringAritlce;
import io.github.springsongs.modules.sys.repo.SpringAritlceRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "administrator", roles = "Administrator")
class SpringAritlceControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private SpringAritlceRepo dao;

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	public void setUp() throws Exception {
		mvc = MockMvcBuilders
		        .webAppContextSetup(context)
		        .apply(springSecurity())
		        .build();
		//mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	void testGetPage() throws Exception {
		SpringAritlce entity = new SpringAritlce();
		entity.setCategoryId("iXBbqWaTKRDnumXxdMcxSWqXuZlczzwWpExS");
		entity.setCategoryTitle("spKdXzYVBvbDylIuInILhRegKVQhOcRdSVCH");
		entity.setColor("NixcfIdRDuHKZdiMFrsXfSDhXIVvLafaZFDA");
		entity.setTag("CJxcBARxWuIhaVRpjPAEdSSezocyLeeVlfng");
		entity.setKeyword("XqsiRBnfkHWFqmnikSyIGgnvZDWaLepPImfR");
		entity.setTitle("ifESpdVgpktrwjrHSuhKSsidaujRymwIKbSl");
		entity.setSummary("BPPCtXEPPCNebRYOYUGQTZFSwgASYrQNJmWU");
		entity.setContents("PqyGRVumXGcOPnaKvkZQpdeUcuuJwrAGusif");
		entity.setAuthorId("HFtOFmXYibBaREIoujHVQTJxTjOxSQwLyRbz");
		entity.setAuthor("vQgXLuEHgYiGJQxZHbwtldPHOoemsyxRTPvp");
		entity.setAuthorUrl("sdisIOgJbFinCzJHoQTrHAzjaoZULtgSNhJP");
		entity.setLink("bMoAwFFJVIOBxHlreBuzhcnwEKMCaYtajjlv");
		entity.setComeFrom("dqCdFFLAIbWkbHlYvXimerasYinPYGXWZAvP");
		entity.setComeFromLink("XmMHdJCyEhwMinYtLaNXklYRAYNhVnbfrPVi");
		entity.setCreatedUserId("zNSuKUELBtUNFxjPTvOUInPYFZTJYVqzqVxO");
		entity.setCreatedBy("DLkDNCzvKpVOzWWjmRNGyuAcqTRqBlvCexGQ");
		entity.setCreatedIp("xJZWEfDZbkajljUOzOSDljBRUBxNBueMqscC");
		entity.setUpdatedUserId("AXXKqFeroCbNtgrjXMlhJXJQrRQXPMBlOMlS");
		entity.setUpdatedBy("vxqhsQvZsRdMdRHVVlhaltaYvMQeebuIKzoB");
		entity.setUpdatedIp("HBKFEnKrmgLtBoNooeqFEamPWXpLVdCKVzkO");
		this.mvc.perform(post("/SpringAritlce/ListByPage").param("page", "1").param("size", "20").with(csrf().useInvalidToken())
				.with(user("Administrator").password("qweasd").roles("USER", "Administrators"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(entity))).andExpect(status().isOk())
		.andExpect(jsonPath("$.data.[*].author").value(hasItem("vQgXLuEHgYiGJQxZHbwtldPHOoemsyxRTPvp")))
		.andExpect(jsonPath("$.data.[*].authorId").value(hasItem("HFtOFmXYibBaREIoujHVQTJxTjOxSQwLyRbz")))
		.andExpect(jsonPath("$.data.[*].authorUrl").value(hasItem("sdisIOgJbFinCzJHoQTrHAzjaoZULtgSNhJP")))
		.andExpect(jsonPath("$.data.[*].categoryId").value(hasItem("iXBbqWaTKRDnumXxdMcxSWqXuZlczzwWpExS")))
		.andExpect(jsonPath("$.data.[*].categoryTitle").value(hasItem("spKdXzYVBvbDylIuInILhRegKVQhOcRdSVCH")))
		.andExpect(jsonPath("$.data.[*].color").value(hasItem("NixcfIdRDuHKZdiMFrsXfSDhXIVvLafaZFDA")))
		.andExpect(jsonPath("$.data.[*].comeFrom").value(hasItem("dqCdFFLAIbWkbHlYvXimerasYinPYGXWZAvP")))
		.andExpect(jsonPath("$.data.[*].comeFromLink").value(hasItem("XmMHdJCyEhwMinYtLaNXklYRAYNhVnbfrPVi")))
		.andExpect(jsonPath("$.data.[*].contents").value(hasItem("PqyGRVumXGcOPnaKvkZQpdeUcuuJwrAGusif")))
		.andExpect(jsonPath("$.data.[*].createdBy").value(hasItem("DLkDNCzvKpVOzWWjmRNGyuAcqTRqBlvCexGQ")))
		.andExpect(jsonPath("$.data.[*].createdIp").value(hasItem("xJZWEfDZbkajljUOzOSDljBRUBxNBueMqscC")))
		.andExpect(jsonPath("$.data.[*].createdUserId").value(hasItem("zNSuKUELBtUNFxjPTvOUInPYFZTJYVqzqVxO")))
		.andExpect(jsonPath("$.data.[*].keyword").value(hasItem("XqsiRBnfkHWFqmnikSyIGgnvZDWaLepPImfR")))
		.andExpect(jsonPath("$.data.[*].link").value(hasItem("bMoAwFFJVIOBxHlreBuzhcnwEKMCaYtajjlv")))
		.andExpect(jsonPath("$.data.[*].summary").value(hasItem("BPPCtXEPPCNebRYOYUGQTZFSwgASYrQNJmWU")))
		.andExpect(jsonPath("$.data.[*].tag").value(hasItem("CJxcBARxWuIhaVRpjPAEdSSezocyLeeVlfng")))
		.andExpect(jsonPath("$.data.[*].title").value(hasItem("ifESpdVgpktrwjrHSuhKSsidaujRymwIKbSl")))
		.andExpect(jsonPath("$.data.[*].updatedBy").value(hasItem("vxqhsQvZsRdMdRHVVlhaltaYvMQeebuIKzoB")))
		.andExpect(jsonPath("$.data.[*].updatedIp").value(hasItem("HBKFEnKrmgLtBoNooeqFEamPWXpLVdCKVzkO")))
		.andExpect(jsonPath("$.data.[*].updatedUserId").value(hasItem("AXXKqFeroCbNtgrjXMlhJXJQrRQXPMBlOMlS")));
	}

	@Test
	void testGet() throws Exception {
		SpringAritlce entity = new SpringAritlce();
		entity.setCategoryId("gRrNZsLEeviVXSudAhUReHANgCNjWvfCeJRy");
		entity.setCategoryTitle("IbeCKXxHQYKRjdUhuKWkchsdTgOYTFYgJQJv");
		entity.setColor("kMpwKCDjuyTIhhkrtXXLKqeySNAPIvIFxFFk");
		entity.setTag("qofquuBDdUuMpWArsGJUzFFwHZtbCYHCEyIk");
		entity.setKeyword("FGFzCaQRmymIYTaXmbKRpFabjsypjbBBNfEP");
		entity.setTitle("ApPQsALwEfspynWffKICyrJoQlwQycfeIBKC");
		entity.setSummary("mPuMBNIdFvNNjnRFQtxSIlNAGLySuoecLnhG");
		entity.setContents("rxVsMLGZrFJBEdnkczMfEhDGsJDMstkRuSil");
		entity.setAuthorId("bgrUfOCoZpqhNEawsbpkFurkPphjIgvwhTrG");
		entity.setAuthor("bMybWHWNsHyHLlXeGRFGvEakmyrMmVgTGPdq");
		entity.setAuthorUrl("GzPOYjThrwQNhJaufhlCIJYvBWEWFLLglVhh");
		entity.setLink("YAyqAklKRAGAfodgaEbYZoHBSXpGwXLullzF");
		entity.setComeFrom("IuyJQKAPybqWbJejdoKwiqijHchZGhttQnSX");
		entity.setComeFromLink("sIHtXsyABweohOdWaTgBjfGjRGInLUBttCiT");
		entity.setCreatedUserId("TlsBUHKBnbnEKtvVfnrOsMaXwTYwEdRlvTwh");
		entity.setCreatedBy("dQWrsUtIOqXEZNcjsMLdvPWMNEJfHiTnEBlT");
		entity.setCreatedIp("zBBkgwsfsvyYhOoeZkrUkFvWVwrsxURIZeYh");
		entity.setUpdatedUserId("nAmdHDkYIGEoolabZnPNpyeQAWECrxaktkfh");
		entity.setUpdatedBy("jmPxRdopOWUcVoHdGkNKCPSLTAfZngtQQVwh");
		entity.setUpdatedIp("LZDGnSkcEkEttpEswMNQlAjAfRxkQzDsxfse");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringAritlce/Detail").param("id", entity.getId()).with(csrf().useInvalidToken())
				.with(user("Administrator").password("qweasd").roles("USER", "Administrators"))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$..author").value(hasItem("bMybWHWNsHyHLlXeGRFGvEakmyrMmVgTGPdq")))
				.andExpect(jsonPath("$..authorId").value(hasItem("bgrUfOCoZpqhNEawsbpkFurkPphjIgvwhTrG")))
				.andExpect(jsonPath("$..authorUrl").value(hasItem("GzPOYjThrwQNhJaufhlCIJYvBWEWFLLglVhh")))
				.andExpect(jsonPath("$..categoryId").value(hasItem("gRrNZsLEeviVXSudAhUReHANgCNjWvfCeJRy")))
				.andExpect(jsonPath("$..categoryTitle").value(hasItem("IbeCKXxHQYKRjdUhuKWkchsdTgOYTFYgJQJv")))
				.andExpect(jsonPath("$..color").value(hasItem("kMpwKCDjuyTIhhkrtXXLKqeySNAPIvIFxFFk")))
				.andExpect(jsonPath("$..comeFrom").value(hasItem("IuyJQKAPybqWbJejdoKwiqijHchZGhttQnSX")))
				.andExpect(jsonPath("$..comeFromLink").value(hasItem("sIHtXsyABweohOdWaTgBjfGjRGInLUBttCiT")))
				.andExpect(jsonPath("$..contents").value(hasItem("rxVsMLGZrFJBEdnkczMfEhDGsJDMstkRuSil")))
				.andExpect(jsonPath("$..createdBy").value(hasItem("dQWrsUtIOqXEZNcjsMLdvPWMNEJfHiTnEBlT")))
				.andExpect(jsonPath("$..createdIp").value(hasItem("zBBkgwsfsvyYhOoeZkrUkFvWVwrsxURIZeYh")))
				.andExpect(jsonPath("$..createdUserId").value(hasItem("TlsBUHKBnbnEKtvVfnrOsMaXwTYwEdRlvTwh")))
				.andExpect(jsonPath("$..keyword").value(hasItem("FGFzCaQRmymIYTaXmbKRpFabjsypjbBBNfEP")))
				.andExpect(jsonPath("$..link").value(hasItem("YAyqAklKRAGAfodgaEbYZoHBSXpGwXLullzF")))
				.andExpect(jsonPath("$..summary").value(hasItem("mPuMBNIdFvNNjnRFQtxSIlNAGLySuoecLnhG")))
				.andExpect(jsonPath("$..tag").value(hasItem("qofquuBDdUuMpWArsGJUzFFwHZtbCYHCEyIk")))
				.andExpect(jsonPath("$..title").value(hasItem("ApPQsALwEfspynWffKICyrJoQlwQycfeIBKC")))
				.andExpect(jsonPath("$..updatedBy").value(hasItem("jmPxRdopOWUcVoHdGkNKCPSLTAfZngtQQVwh")))
				.andExpect(jsonPath("$..updatedIp").value(hasItem("LZDGnSkcEkEttpEswMNQlAjAfRxkQzDsxfse")))
				.andExpect(jsonPath("$..updatedUserId").value(hasItem("nAmdHDkYIGEoolabZnPNpyeQAWECrxaktkfh")));
	}

	@Test
	//@WithMockUser(roles="Administrators")
	void testSave() throws Exception {
		int databaseSizeBeforeCreate = dao.findAll().size();
		SpringAritlce entity = new SpringAritlce();
		entity.setCategoryId("syNoCDQIUjpAZnnSSonQaqGSSyIzxUvLwFvd");
		entity.setCategoryTitle("iszWdmXEODlqJdTwgygHFYSdBjKVxmzbaPah");
		entity.setColor("nIbAkRBDQdFoSKUXYTehsXwkEJQefHcWtjkj");
		entity.setTag("CmnyJywdSEBVYyADWHsAAOgLetqpmolGHhKU");
		entity.setKeyword("JandUcpvVFSViLyxjPsltnExCIWHzmHsOzPA");
		entity.setTitle("NwxhqgTdKXEXhCinuofQuzygxEifJWYVNiuc");
		entity.setSummary("GAoMHJyOWJnuLgqVObyhmqFDoNCrfEnbQLxY");
		entity.setContents("woZKpsNBzIidrGSlopbwQJCAZmpcEicsSNBa");
		entity.setAuthorId("APnJfRRMnahVXBGhuiPRrxLTgUjlWzYbQWQe");
		entity.setAuthor("QGdoSfHTUCwzJGRMgLOWrLhvpYTaKwrrwxRz");
		entity.setAuthorUrl("EhrTrvKfepehXUSPNdJorMabtsTGIYsBqvim");
		entity.setLink("aiVunZusOaZdmLDCWszSBnpSLLwRRHPYFECK");
		entity.setComeFrom("bDbTnJINuyHXfECCbeLsbYHzLTwsaetlLyJH");
		entity.setComeFromLink("HZtJkKduAaVnbAksxxNbotZrSxmHYiCpyPPl");
		entity.setTopStatus(true);
		entity.setHotStatus(true);
		entity.setFeatured(true);
		entity.setCollectCount(1);
		entity.setLikeCount(1);
		entity.setShareCount(1);
		entity.setReadCount(1);
		entity.setCreatedUserId("iuJHRqlBOjcKgXgjGaDABfhTVKApgQHGPntp");
		entity.setCreatedBy("NBdJFwTHvREjhIKKwDGgPBYNRWvkbrAlPEVp");
		entity.setCreatedIp("pENhwbtMFtuFrtTHsGXDxBLCzUrmFUWHuqht");
		entity.setUpdatedUserId("vZLFpITMrKyQqEsIpLagzHaliqtVeHcjCFJN");
		entity.setUpdatedBy("SBeLxWyJINiMqVTwOPGZDgWOUooEbgTAPCZP");
		entity.setUpdatedIp("DfbmJPplhjeSNhFwiafjpLDPaVZPfmWZthUT");
		this.mvc.perform(post("/SpringAritlce/Create").with(csrf().useInvalidToken())
				.with(user("Administrator").password("qweasd").roles("USER", "Administrators")).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(entity))).andExpect(status().isOk());
		List<SpringAritlce> SpringAritlceEntityList = dao.findAll();
		assertThat(SpringAritlceEntityList).hasSize(databaseSizeBeforeCreate + 1);
		SpringAritlce testBaseSpringAritlceEntity = SpringAritlceEntityList.get(SpringAritlceEntityList.size() - 1);
		assertThat(testBaseSpringAritlceEntity.getAuthor()).isEqualTo("QGdoSfHTUCwzJGRMgLOWrLhvpYTaKwrrwxRz");
		assertThat(testBaseSpringAritlceEntity.getAuthorId()).isEqualTo("APnJfRRMnahVXBGhuiPRrxLTgUjlWzYbQWQe");
		assertThat(testBaseSpringAritlceEntity.getAuthorUrl()).isEqualTo("EhrTrvKfepehXUSPNdJorMabtsTGIYsBqvim");
		assertThat(testBaseSpringAritlceEntity.getCategoryId()).isEqualTo("syNoCDQIUjpAZnnSSonQaqGSSyIzxUvLwFvd");
		assertThat(testBaseSpringAritlceEntity.getCategoryTitle()).isEqualTo("iszWdmXEODlqJdTwgygHFYSdBjKVxmzbaPah");
		assertThat(testBaseSpringAritlceEntity.getColor()).isEqualTo("nIbAkRBDQdFoSKUXYTehsXwkEJQefHcWtjkj");
		assertThat(testBaseSpringAritlceEntity.getComeFrom()).isEqualTo("bDbTnJINuyHXfECCbeLsbYHzLTwsaetlLyJH");
		assertThat(testBaseSpringAritlceEntity.getComeFromLink()).isEqualTo("HZtJkKduAaVnbAksxxNbotZrSxmHYiCpyPPl");
		assertThat(testBaseSpringAritlceEntity.getContents()).isEqualTo("woZKpsNBzIidrGSlopbwQJCAZmpcEicsSNBa");
		assertThat(testBaseSpringAritlceEntity.getCreatedBy()).isEqualTo("NBdJFwTHvREjhIKKwDGgPBYNRWvkbrAlPEVp");
		assertThat(testBaseSpringAritlceEntity.getCreatedIp()).isEqualTo("pENhwbtMFtuFrtTHsGXDxBLCzUrmFUWHuqht");
		assertThat(testBaseSpringAritlceEntity.getCreatedUserId()).isEqualTo("iuJHRqlBOjcKgXgjGaDABfhTVKApgQHGPntp");
		assertThat(testBaseSpringAritlceEntity.getKeyword()).isEqualTo("JandUcpvVFSViLyxjPsltnExCIWHzmHsOzPA");
		assertThat(testBaseSpringAritlceEntity.getLink()).isEqualTo("aiVunZusOaZdmLDCWszSBnpSLLwRRHPYFECK");
		assertThat(testBaseSpringAritlceEntity.getSummary()).isEqualTo("GAoMHJyOWJnuLgqVObyhmqFDoNCrfEnbQLxY");
		assertThat(testBaseSpringAritlceEntity.getTag()).isEqualTo("CmnyJywdSEBVYyADWHsAAOgLetqpmolGHhKU");
		assertThat(testBaseSpringAritlceEntity.getTitle()).isEqualTo("NwxhqgTdKXEXhCinuofQuzygxEifJWYVNiuc");
		assertThat(testBaseSpringAritlceEntity.getUpdatedBy()).isEqualTo("SBeLxWyJINiMqVTwOPGZDgWOUooEbgTAPCZP");
		assertThat(testBaseSpringAritlceEntity.getUpdatedIp()).isEqualTo("DfbmJPplhjeSNhFwiafjpLDPaVZPfmWZthUT");
		assertThat(testBaseSpringAritlceEntity.getUpdatedUserId()).isEqualTo("vZLFpITMrKyQqEsIpLagzHaliqtVeHcjCFJN");
	}

	@Test
	void testUpdate() throws Exception {
		SpringAritlce entity = new SpringAritlce();
		entity.setCategoryId("qitUbvJsqouhzVoyabOjHGEbLtTQTXnbPssY");
		entity.setCategoryTitle("BRGQPRSsOYLXqIgHsPbgodDYOLBKiFaiAjMj");
		entity.setColor("AoFSfvyqvRcSRfpTwjqEqHFdfPBduEsKczSu");
		entity.setTag("lumwWzikvaTmRPjOLVRtmqBSrkbmDLvNVOPB");
		entity.setKeyword("JmsLIqdsKrjRwJgSqFbItAaLBxLnWTUQyoMF");
		entity.setTitle("tHqcIFHBpiiHbhKicRaJmGoxWjYsaSFxVats");
		entity.setSummary("VMFKZIUGSwRIJSKfhRKjjPqXumRuFJowthYG");
		entity.setContents("UVPWohRhmuzMJuLiOoPrhPYboxhiUOOJGLPT");
		entity.setAuthorId("nIBlFVqPxMrRALlOdxPSpicwSiXNYZGxBUQT");
		entity.setAuthor("poHyQFGAGFdtQViVUhJDDVhNVaDuZaNwMBgT");
		entity.setAuthorUrl("qEQWwYRBBzfXeAyTRTceZrUkJkjuMaMYkRaK");
		entity.setLink("IirewSIWaqmlpXKFmIunIZhJnWKAywucbPuZ");
		entity.setComeFrom("JzSXJAWHDUQBxUfpYarJIEFmNMHEQpnfAqPf");
		entity.setComeFromLink("URModroPbyhZclENZUCTtOHfDyZAnnPdeXdn");
		entity.setCreatedUserId("CsmfOfCxqoKArEbCIeLNuRKWIgBLjnZlqHPS");
		entity.setCreatedBy("HLqmfogvrrKcJRKyvWSWroMSaJOprKRRixrH");
		entity.setCreatedIp("XRiYPlnfGWspBEVAhwKOkpOuJeZplnUcBzIe");
		entity.setUpdatedUserId("cJtUUsSistWmeXECkYBcXPoOmJjeCLyChjxz");
		entity.setUpdatedBy("ATTYloUAVZVwiOhfpKYupkyHsNIkRUAuVSvO");
		entity.setUpdatedIp("CbjXvQHjfBPPPmudxfQHvIvgOnAxLYBPgIvq");
		dao.saveAndFlush(entity);
		int databaseSizeBeforeUpdate = dao.findAll().size();
		SpringAritlce updatedEntity = dao.findById(entity.getId()).get();
		updatedEntity.setCategoryId("efOXgbizkhzParuketjdbjqwwWdVeUXJUIzP");
		updatedEntity.setCategoryTitle("CVYiFhDCOtqDnDZAVdlRJPUTfJwAlHCyQmia");
		updatedEntity.setColor("iifZqILtpWhNvtvSVWtzbSuujYwFGahPFzyD");
		updatedEntity.setTag("pCjEVXxyOLQpThpIZKxhqCyotlpdwFFLnVkH");
		updatedEntity.setKeyword("IpqXpIqgUTcerFsBxXKEMRFsjHBaGsphlbpi");
		updatedEntity.setTitle("NfnTbrqmOFhMJGfycTtruOUuooeiWMAQAmiL");
		updatedEntity.setSummary("AIpwoyYJmMbwYQFfxsBxqNvAPyXWPFsyacpD");
		updatedEntity.setContents("aHexhjFWOUtBjATHEfZQIaMlFimvtYLePXXG");
		updatedEntity.setAuthorId("BdutrlPVPUYXcgjQFkYnoXDvbiAPHUdcgbtJ");
		updatedEntity.setAuthor("JcBhgMiNrSGQEgNETRWutNJuyyAuPeKSIWyC");
		updatedEntity.setAuthorUrl("FxpDIVDAqVMlgIWJCcUKkKSXWNDHDbiNmbJo");
		updatedEntity.setLink("DnykPMBPVoXmApbqrgNZUUwWxrDUJrhLXyKB");
		updatedEntity.setComeFrom("jMpRvmolEsHDLPCNTjppYqOqXZUtblzqgeUZ");
		updatedEntity.setComeFromLink("OKBpFSaXtSKiQkZQKhBgpnexWdLgvBjCpFvQ");
		updatedEntity.setCreatedUserId("JKYgSxBtopwKpWkCRoiEreeisLxdLXcfHyMx");
		updatedEntity.setCreatedBy("kexhCjryBSTVXIdQafQxsUbhAIdOLpRYzLoL");
		updatedEntity.setCreatedIp("NyqPbryuObjRDuEwVrOjZBhvWOXNkVGYyHTs");
		updatedEntity.setUpdatedUserId("YkebfPHoeqmfrskFtfAahngvKCbfyRnxpptX");
		updatedEntity.setUpdatedBy("vLhOPqQDAeAWZQYNGOaGicyTHiuNNeGTDpbX");
		updatedEntity.setUpdatedIp("WXSsfyEMJfTZjryoVTfJaVNOXdGogAOiyDlE");
		this.mvc.perform(post("/SpringAritlce/Edit").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(JSON.toJSONString(updatedEntity)))
				.andExpect(status().isOk());
		List<SpringAritlce> SpringAritlceEntityList = dao.findAll();
		assertThat(SpringAritlceEntityList).hasSize(databaseSizeBeforeUpdate);
		SpringAritlce testBaseSpringAritlceEntity = SpringAritlceEntityList.get(SpringAritlceEntityList.size() - 1);
		assertThat(testBaseSpringAritlceEntity.getAuthor()).isEqualTo("JcBhgMiNrSGQEgNETRWutNJuyyAuPeKSIWyC");
		assertThat(testBaseSpringAritlceEntity.getAuthorId()).isEqualTo("BdutrlPVPUYXcgjQFkYnoXDvbiAPHUdcgbtJ");
		assertThat(testBaseSpringAritlceEntity.getAuthorUrl()).isEqualTo("FxpDIVDAqVMlgIWJCcUKkKSXWNDHDbiNmbJo");
		assertThat(testBaseSpringAritlceEntity.getCategoryId()).isEqualTo("efOXgbizkhzParuketjdbjqwwWdVeUXJUIzP");
		assertThat(testBaseSpringAritlceEntity.getCategoryTitle()).isEqualTo("CVYiFhDCOtqDnDZAVdlRJPUTfJwAlHCyQmia");
		assertThat(testBaseSpringAritlceEntity.getColor()).isEqualTo("iifZqILtpWhNvtvSVWtzbSuujYwFGahPFzyD");
		assertThat(testBaseSpringAritlceEntity.getComeFrom()).isEqualTo("jMpRvmolEsHDLPCNTjppYqOqXZUtblzqgeUZ");
		assertThat(testBaseSpringAritlceEntity.getComeFromLink()).isEqualTo("OKBpFSaXtSKiQkZQKhBgpnexWdLgvBjCpFvQ");
		assertThat(testBaseSpringAritlceEntity.getContents()).isEqualTo("aHexhjFWOUtBjATHEfZQIaMlFimvtYLePXXG");
		assertThat(testBaseSpringAritlceEntity.getCreatedBy()).isEqualTo("kexhCjryBSTVXIdQafQxsUbhAIdOLpRYzLoL");
		assertThat(testBaseSpringAritlceEntity.getCreatedIp()).isEqualTo("NyqPbryuObjRDuEwVrOjZBhvWOXNkVGYyHTs");
		assertThat(testBaseSpringAritlceEntity.getCreatedUserId()).isEqualTo("JKYgSxBtopwKpWkCRoiEreeisLxdLXcfHyMx");
		assertThat(testBaseSpringAritlceEntity.getKeyword()).isEqualTo("IpqXpIqgUTcerFsBxXKEMRFsjHBaGsphlbpi");
		assertThat(testBaseSpringAritlceEntity.getLink()).isEqualTo("DnykPMBPVoXmApbqrgNZUUwWxrDUJrhLXyKB");
		assertThat(testBaseSpringAritlceEntity.getSummary()).isEqualTo("AIpwoyYJmMbwYQFfxsBxqNvAPyXWPFsyacpD");
		assertThat(testBaseSpringAritlceEntity.getTag()).isEqualTo("pCjEVXxyOLQpThpIZKxhqCyotlpdwFFLnVkH");
		assertThat(testBaseSpringAritlceEntity.getTitle()).isEqualTo("NfnTbrqmOFhMJGfycTtruOUuooeiWMAQAmiL");
		assertThat(testBaseSpringAritlceEntity.getUpdatedBy()).isEqualTo("vLhOPqQDAeAWZQYNGOaGicyTHiuNNeGTDpbX");
		assertThat(testBaseSpringAritlceEntity.getUpdatedIp()).isEqualTo("WXSsfyEMJfTZjryoVTfJaVNOXdGogAOiyDlE");
		assertThat(testBaseSpringAritlceEntity.getUpdatedUserId()).isEqualTo("YkebfPHoeqmfrskFtfAahngvKCbfyRnxpptX");
	}

	@Test
	void testSetDeleted() throws Exception {
		SpringAritlce entity = new SpringAritlce();
		entity.setCategoryId("pstTNiwEOcORdZqkgVwqopcvIdPquQHpQcHv");
		entity.setCategoryTitle("LSzPFUKFXvekYlAmDMBZnHqwaoEMrHnkLgfB");
		entity.setColor("ZOiYTzGcDGROGIulwkLSqiCawjUJsWNToZSQ");
		entity.setTag("LBkobrkiSFGhrtcTzGapUbAhoAzhFdtsDRIG");
		entity.setKeyword("qVyvkdSuzgUEczEhhkvlOPGpqFORuGyZrAwB");
		entity.setTitle("uYuvctHESthYWZHQuRRRHkWrlxZuCcVRArFS");
		entity.setSummary("MRlRaVGBxgqQHopTDAXZMEReHTPuWjWTnFZx");
		entity.setContents("fsAdfIrkFLmlGENUYOgChPMsiXtQNDGojajd");
		entity.setAuthorId("zKNBVPCbXONWGaVNfEhUoFYBTCVNDePVYBXT");
		entity.setAuthor("VrTgvRryGGuBSyflVwQQizyaugcfoINKJFsK");
		entity.setAuthorUrl("gegpXEzrupgdQhPtLgkNeekblCrJjXEoSntW");
		entity.setLink("wWzkPBuDKzGemDqKFDzbTGWLpPzCMExlRVhN");
		entity.setComeFrom("nsanSzOhOVPtpIiyEkiLYSCjAjkSGxbPDhkV");
		entity.setComeFromLink("nQJOSWkJhMlCAJZNDuFRBQpngUuguSeopazg");
		entity.setCreatedUserId("RIcImzxNYWhyOXqrPMOICGgkPcuvjvowbCeh");
		entity.setCreatedBy("XKhtnhFtyJcOYbjOCQeiJqCRUgwqeygeNDGA");
		entity.setCreatedIp("JrYarvCnmmDYcTBFudZOucRhsvARCoBKgshX");
		entity.setUpdatedUserId("RsSezHonhMhdjqOyePWMFhDEgsgvozUYobOC");
		entity.setUpdatedBy("karpGYiBaYsSHmKQfEJxnscVbgVBWWqOhqiy");
		entity.setUpdatedIp("ldKWvGJPTLpmcafkLGsiDfRoqVUQbYCStUyF");
		dao.saveAndFlush(entity);
		this.mvc.perform(post("/SpringAritlce/SetDeleted").param("ids", entity.getId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk());
	}

}
