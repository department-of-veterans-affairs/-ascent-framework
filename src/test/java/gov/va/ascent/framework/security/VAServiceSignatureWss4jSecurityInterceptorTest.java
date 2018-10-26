package gov.va.ascent.framework.security;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.Properties;

import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;

public class VAServiceSignatureWss4jSecurityInterceptorTest {

	private static final String SOAP_MESSAGE_FILE = "src/test/resources/testFiles/security/soapMessage.xml";

	/** The key time stamp alias. */
	private String keyTimeStampAlias = "ebn_vbms_cert";

	/** The key time stamp password. */
	private String keyTimeStampPassword = "changeit";

	/** The security.crypto.merlin.keystore.alias */
	private String securityCryptoMerlinKeystoreAlias;

	private Properties propsCrypto;

	private VAServiceSignatureWss4jSecurityInterceptor interceptor = Mockito.spy(VAServiceSignatureWss4jSecurityInterceptor.class);

	@Before
	public final void setUp() throws Exception {
		ReflectionTestUtils.setField(interceptor, "securityCryptoProvider", "org.apache.ws.security.components.crypto.Merlin");
		ReflectionTestUtils.setField(interceptor, "cryptoKeystoreTypeOriginator", "SUN");
		ReflectionTestUtils.setField(interceptor, "cryptoKeystoreType", "jks");
		ReflectionTestUtils.setField(interceptor, "cryptoKeystorePassword", "changeit");
		ReflectionTestUtils.setField(interceptor, "cryptoKeystoreAlias", "ebn_vbms_cert");
		ReflectionTestUtils.setField(interceptor, "cryptoKeystoreFile",
				"/encryption/EFolderService/vbmsKeystore.jks");

		propsCrypto = interceptor.retrieveCryptoProps();

		assertNotNull(propsCrypto);

		securityCryptoMerlinKeystoreAlias = (String) propsCrypto.get("org.apache.ws.security.crypto.merlin.keystore.alias");
	}

	@Test
	public void testSecureMessage() throws Exception {

		SoapMessage sm = WSInterceptorTestUtil.createSoapMessage(SOAP_MESSAGE_FILE);
		Crypto crypto = CryptoFactory.getInstance(propsCrypto);
		crypto.setDefaultX509Identifier(securityCryptoMerlinKeystoreAlias);
		interceptor.setCrypto(crypto);
		interceptor.setKeyAlias(keyTimeStampAlias);
		interceptor.setKeyPassword(keyTimeStampPassword);
		interceptor.setValidationActions("Signature");
		interceptor.setValidateRequest(false);
		interceptor.setValidateResponse(false);
		interceptor.setSecurementUsername("selfsigned");
		interceptor.setSecurementPassword("password");
		interceptor.afterPropertiesSet();
		MessageContext messageContextMock = mock(MessageContext.class);
		interceptor.secureMessage(sm, messageContextMock);

		assertNotNull(sm);

	}

	@Test
	public void testSecureMessageNoCrypto() throws Exception {

		SoapMessage sm = WSInterceptorTestUtil.createSoapMessage(SOAP_MESSAGE_FILE);
		interceptor.setKeyAlias(keyTimeStampAlias);
		interceptor.setKeyPassword(keyTimeStampPassword);
		interceptor.setValidationActions("Signature");
		interceptor.setValidateRequest(false);
		interceptor.setValidateResponse(false);
		interceptor.setSecurementUsername("selfsigned");
		interceptor.setSecurementPassword("password");
		interceptor.afterPropertiesSet();
		MessageContext messageContextMock = mock(MessageContext.class);
		interceptor.secureMessage(sm, messageContextMock);

		assertNotNull(sm);

	}
}
