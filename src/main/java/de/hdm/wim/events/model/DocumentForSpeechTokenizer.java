package de.hdm.wim.events.model;

/**
 * Representation of a Document as we send it to the SpeechTokenizer
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
public class DocumentForSpeechTokenizer {
	
	private String userId;
	private String hangoutsId;
	private String documentName;
	private String drivePath;
	

	public DocumentForSpeechTokenizer() {
		
	}
	
	public DocumentForSpeechTokenizer( String userId, String hangoutsId, Document document) {
		this.userId = userId;
		this.hangoutsId = hangoutsId;
		this.documentName = document.getDocumentName();
		String cleansedGoogleDrive = document.getGoogleDrivePath().replaceFirst("^https://drive.google.com/open[?]id[=]", "");
		this.drivePath = cleansedGoogleDrive;
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
	}

	@Override
	public String toString() {
		return "DocumentForSpeechTokenizer [userId=" + userId + ", hangoutsId=" + hangoutsId + ", documentName=" + documentName + ", drivePath=" + drivePath + "]";
	}
	
}