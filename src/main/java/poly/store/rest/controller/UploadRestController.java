package poly.store.rest.controller;

import java.io.File;

import javax.websocket.server.PathParam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import poly.store.service.UploadService;

@CrossOrigin("*")
@RestController

public class UploadRestController {
	@Autowired
	UploadService uploadService;
	
	@PostMapping("/rest/upload/{folder}")
	public JsonNode upload(@PathParam("file") MultipartFile file, @PathVariable("folder") String folder) {
		File saveFile = uploadService.save(file, folder);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("name", saveFile.getName());
		node.put("size", saveFile.length());
		
		return node;
	}

}
