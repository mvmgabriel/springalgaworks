package br.com.mstec.aw.algamoney.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperty {

	private final Security security = new Security();
			
	public Security getSecurity() {
		return security;
	}

	public static class Security{
		private boolean enableHttps;
		private String corsAllowedOrigin = "http://localhost:8080";
		
		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}

		public String getCorsAllowedOrigin() {
			return corsAllowedOrigin;
		}

		public void setCorsAllowedOrigin(String corsAllowedOrigin) {
			this.corsAllowedOrigin = corsAllowedOrigin;
		}
		
	}
	
}
