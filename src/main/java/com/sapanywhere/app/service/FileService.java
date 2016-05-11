package com.sapanywhere.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapanywhere.app.entity.Files;
import com.sapanywhere.app.repository.FileRepository;

@Service
public class FileService {
	@Autowired
	private FileRepository fileRepository;

	public void create(Files file){
		this.fileRepository.save(file);
	}
	
	public String findById(Long avatarId){
		Files file = this.fileRepository.findOne(avatarId);
		
		return file.getFileLocation();
	}
}
