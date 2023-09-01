package GitHubRepoInfo.exception;

public class GitHubErrorResponse {
	private String message;
	private String documentationUrl;

	public GitHubErrorResponse(String message, String documentationUrl) {
		super();
		this.message = message;
		this.documentationUrl = documentationUrl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDocumentationUrl() {
		return documentationUrl;
	}

	public void setDocumentationUrl(String documentationUrl) {
		this.documentationUrl = documentationUrl;
	}

}
