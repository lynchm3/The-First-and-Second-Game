package com.marklynch.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.swing.JFileChooser;

import org.apache.commons.io.IOUtils;

public class FileUtils {

	public static String openFile() {
		String fileContents = null;
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				FileInputStream fileInputStream;
				fileInputStream = new FileInputStream(file);
				StringWriter writer = new StringWriter();
				IOUtils.copy(fileInputStream, writer, "UTF-8");
				fileContents = writer.toString();
			} else {
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContents;
	}

	public static boolean saveFile(String fileContents) {
		boolean success = false;
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				StringReader reader = new StringReader(fileContents);
				IOUtils.copy(reader, fileOutputStream, "UTF-8");
				success = true;
			} else {
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return success;
	}
}
