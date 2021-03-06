package de.hdm.wim.events.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import de.hdm.wim.events.model.event.User;

/**
 * Representation of a Document as we send it to the SpeechTokenizer
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */

@Entity
public class DocumentForSpeechTokenizer {
	private String userId;
	private String hangoutsId;
	private String documentName;
	@Id
	private String drivePath;

	public DocumentForSpeechTokenizer() {

	}

	public DocumentForSpeechTokenizer(User user, Document document) {
		this.userId = user.getGoogle_id();
		this.hangoutsId = user.getHangouts_id();
		this.documentName = document.getDocumentName();
		if( document.getDrivePath().contains("=")) {
			this.drivePath = document.getDrivePath().split("=")[1];
		} else {
			this.drivePath = document.getDrivePath();
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getHangoutsId() {
		return hangoutsId;
	}

	public void setHangoutsId(String hangoutsId) {
		this.hangoutsId = hangoutsId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDrivePath() {
		return drivePath;
	}

	public void setDrivePath(String drivePath) {
		this.drivePath = drivePath;
		if( drivePath.contains("=")) {
			this.drivePath = drivePath.split("=")[1];
		} else {
			this.drivePath = drivePath;
		}
	}

	@Override
	public String toString() {
		return "DocumentForSpeechTokenizer [userId=" + userId + ", hangoutsId=" + hangoutsId + ", documentName=" + documentName + ", drivePath=" + drivePath + "]";
	}
}