package io.github.springsongs.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/Admin")
public class AdminController {
	@GetMapping("")
	public String index(ModelMap map) {
		return "admin/index";
	}

	@GetMapping("Dashboard")
	public String dashboard(ModelMap map) {
		return "admin/Dashboard/Index";
	}

	@GetMapping("System/SpringUser/Index")
	public String springUser(ModelMap map) {
		return "admin/System/SpringUser/Index";
	}

	@GetMapping("System/SpringArticle/Index")
	public String springArticle(ModelMap map) {
		return "admin/System/SpringArticle/Index";
	}

	@GetMapping("System/SpringContact/Index")
	public String springContact(ModelMap map) {
		return "admin/System/SpringContact/Index";
	}

	@GetMapping("System/SpringArticleComment/Index")
	public String springArticleComment(ModelMap map) {
		return "admin/System/SpringArticleComment/Index";
	}

	@GetMapping("System/SpringDictionary/Index")
	public String springDictionary(ModelMap map) {
		return "admin/System/SpringDictionary/Index";
	}

	@GetMapping("System/SpringAttachment/Index")
	public String springAttachment(ModelMap map) {
		return "admin/System/SpringAttachment/Index";
	}

	@GetMapping("System/SpringResource/Index")
	public String springResource(ModelMap map) {
		return "admin/System/SpringResource/Index";
	}

	@GetMapping("System/SpringParameter/Index")
	public String springParameter(ModelMap map) {
		return "admin/System/SpringParameter/Index";
	}

	@GetMapping("System/SpringRole/Index")
	public String springRole(ModelMap map) {
		return "admin/System/SpringRole/Index";
	}

	@GetMapping("System/SpringSystem/Index")
	public String springSystem(ModelMap map) {
		return "admin/System/SpringSystem/Index";
	}

	@GetMapping("System/SpringOrganization/Index")
	public String springOrganization(ModelMap map) {
		return "admin/System/SpringOrganization/Index";
	}

	@GetMapping("System/SpringDictionaryDetail/Index")
	public String springDictionaryDetail(ModelMap map) {
		return "admin/System/SpringDictionaryDetail/Index";
	}

	@GetMapping("System/SpringArticleCategory/Index")
	public String springArticleCategory(ModelMap map) {
		return "admin/System/SpringArticleCategory/Index";
	}

	@GetMapping("System/SpringDistrict/Index")
	public String springDistrict(ModelMap map) {
		return "admin/System/SpringDistrict/Index";
	}

	@GetMapping("Job/SpringJobGroup/Index")
	public String springJobGroup(ModelMap map) {
		return "admin/Job/SpringJobGroup/Index";
	}

	@GetMapping("Job/SpringJob/Index")
	public String springJob(ModelMap map) {
		return "admin/Job/SpringJob/Index";
	}

	@GetMapping("Job/SpringJobHistory/Index")
	public String springJobHistory(ModelMap map) {
		return "admin/Job/SpringJobHistory/Index";
	}

	@GetMapping("Activiti/SpringActCategory/Index")
	public String springActCategory(ModelMap map) {
		return "admin/Activiti/SpringActCategory/Index";
	}

	@GetMapping("Activiti/SpringActModel/Index")
	public String springActModel(ModelMap map) {
		return "admin/Activiti/SpringActModel/Index";
	}

	@GetMapping("Activiti/SpringProcess/Index")
	public String springProcess(ModelMap map) {
		return "admin/Activiti/SpringProcess/Index";
	}

	@GetMapping("Activiti/SpringMyProcess/Index")
	public String springMyProcess(ModelMap map) {
		return "admin/Activiti/SpringMyProcess/Index";
	}

	@GetMapping("Activiti/SpringNewProcess/Index")
	public String springNewProcess(ModelMap map) {
		return "admin/Activiti/SpringNewProcess/Index";
	}

	@GetMapping("Activiti/SpringToDoProcess/Index")
	public String springToDoProcess(ModelMap map) {
		return "admin/Activiti/SpringToDoProcess/Index";
	}

	@GetMapping("Activiti/SpringFinishProcess/Index")
	public String springFinishProcess(ModelMap map) {
		return "admin/Activiti/SpringFinishProcess/Index";
	}

	@GetMapping("Activiti/SpringProcessDefinition/Index")
	public String springProcessDefinition(ModelMap map) {
		return "admin/Activiti/springProcessDefinition/Index";
	}

	@GetMapping("Activiti/commonvaction/Index")
	public String commonvaction(ModelMap map) {
		return "admin/Activiti/commonvaction/Index";
	}

	@GetMapping("Activiti/commonvactionInfo/Index")
	public String commonvactionInfo(ModelMap map) {
		return "admin/Activiti/commonvactionInfo/Index";
	}

	@GetMapping("OperationAndMaintenanceManagement/SpringLoginLog/Index")
	public String springLoginLog(ModelMap map) {
		return "admin/OperationAndMaintenanceManagement/springLoginLog/Index";
	}

	@GetMapping("Other/SpringSiteInfo/Index")
	public String springSiteInfo(ModelMap map) {
		return "admin/Other/SpringSiteInfo/Index";
	}

	@GetMapping("Other/SpringSiteMessage/Index")
	public String springSiteMessage(ModelMap map) {
		return "admin/Other/SpringSiteMessage/Index";
	}

	@GetMapping("Other/SpringSiteNotice/Index")
	public String springSiteNotice(ModelMap map) {
		return "admin/Other/SpringSiteNotice/Index";
	}
}
