package pep.per.mint.msa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.database.service.co.CommonService;

@Controller
public class MSAController {
	@Autowired
	CommonService commonService;
	
	
	@RequestMapping("/")
	public String home(Model model) throws Exception {
		 
		return "/view/main/index.jsp";
	}
	
	@RequestMapping("/interfaces")
	public String getInterfaces(Model model) throws Exception {
		
		List<Interface> list = commonService.getInterfaceCdList();
		model.addAttribute("interfaces", list);
		return "interface-list";
	}
}
