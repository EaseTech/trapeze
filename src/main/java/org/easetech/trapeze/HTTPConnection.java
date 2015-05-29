package org.easetech.trapeze;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HTTPConnection {
	
	public void connect() throws Exception {
		SSLContext sslContext = SSLContexts.custom()
		        .useTLS() // Only this turned out to be not enough
		        .build();
		SSLConnectionSocketFactory sf = new SSLConnectionSocketFactory(
		        sslContext,
		        new String[] {"TLSv1", "TLSv1.1", "TLSv1.2"},
		        null,
		        SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient client = HttpClients.custom()
		        .setSSLSocketFactory(sf)
		        .build();
	}

}
