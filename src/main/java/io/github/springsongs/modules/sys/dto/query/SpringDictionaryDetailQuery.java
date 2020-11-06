package io.github.springsongs.modules.sys.dto.query;

public class SpringDictionaryDetailQuery {
	private String detailCode;
	private String detailName;
	private String dictionaryCode;

	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}

	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	public String getDictionaryCode() {
		return dictionaryCode;
	}

	public void setDictionaryCode(String dictionaryCode) {
		this.dictionaryCode = dictionaryCode;
	}

}
