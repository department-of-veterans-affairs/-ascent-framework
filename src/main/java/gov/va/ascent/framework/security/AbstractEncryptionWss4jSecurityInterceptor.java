package gov.va.ascent.framework.security;

import java.util.Properties;

import org.apache.ws.security.components.crypto.Crypto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

import gov.va.ascent.framework.log.AscentLogger;
import gov.va.ascent.framework.log.AscentLoggerFactory;

/**
 * An abstract implementation of spring's Wss4jSecurityInterceptor that adds
 * common encryption properties for encryption related implementations.
 */
public abstract class AbstractEncryptionWss4jSecurityInterceptor extends Wss4jSecurityInterceptor {
	private static final AscentLogger LOGGER = AscentLoggerFactory.getLogger(AbstractEncryptionWss4jSecurityInterceptor.class);

	/** The crypto. */
	private Crypto crypto;

	/** The key alias. */
	private String keyAlias;

	/** The key password. */
	private String keyPassword;

	/** Fully qualified name of the class that will provide cryptography capabilities. */
	@Value("${ascent.security.crypto.provider}")
	private String securityCryptoProvider;

	/**
	 * The type of
	 */
	@Value("${ascent.security.crypto.keystore.type}")
	private String securityCryptoMerlinKeystoreType;

	/**
	 * The security.crypto.merlin.keystore.password.
	 */
	@Value("${ascent.security.crypto.keystore.password}")
	private String securityCryptoMerlinKeystorePassword;

	/**
	 * The security.crypto.merlin.keystore.alias
	 */
	@Value("${ascent.security.crypto.keystore.alias}")
	private String securityCryptoMerlinKeystoreAlias;

	/**
	 * The securityCryptoMerlinKeystoreFile
	 */
	@Value("${ascent.security.crypto.keystore.keystore}")
	private String securityCryptoMerlinKeystoreFile;

	/**
	 * Retrieves properties to set to create a crypto file
	 *
	 * @return
	 */
	protected Properties retrieveCryptoProps() {

		LOGGER.info("Retrieving crypto properties:\n"
				+ "- org.apache.ws.security.crypto.provider {} " + securityCryptoProvider + " ; \n"
				+ "- org.apache.ws.security.crypto.merlin.keystore.type {} " + securityCryptoMerlinKeystoreType + " ; \n"
				+ "- org.apache.ws.security.crypto.merlin.keystore.password {} " + securityCryptoMerlinKeystorePassword + " ; \n"
				+ "- org.apache.ws.security.crypto.merlin.keystore.alias {} " + securityCryptoMerlinKeystoreAlias + " ; \n"
				+ "- org.apache.ws.security.crypto.merlin.keystore.file {} " + securityCryptoMerlinKeystoreFile + " .");

		Properties propertiesMap = new Properties();
		propertiesMap.setProperty("org.apache.ws.security.crypto.provider", securityCryptoProvider);
		propertiesMap.setProperty("org.apache.ws.security.crypto.merlin.keystore.type", securityCryptoMerlinKeystoreType);
		propertiesMap.setProperty("org.apache.ws.security.crypto.merlin.keystore.password", securityCryptoMerlinKeystorePassword);
		propertiesMap.setProperty("org.apache.ws.security.crypto.merlin.keystore.alias", securityCryptoMerlinKeystoreAlias);
		propertiesMap.setProperty("org.apache.ws.security.crypto.merlin.keystore.file", securityCryptoMerlinKeystoreFile);

		return propertiesMap;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.ws.soap.security.wss4j.Wss4jSecurityInterceptor#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() {
		//
	}

	/**
	 * Gets the crypto.
	 *
	 * @return the crypto
	 */
	public final Crypto getCrypto() {
		return crypto;
	}

	/**
	 * Sets the crypto.
	 *
	 * @param crypto the new crypto
	 */
	public final void setCrypto(final Crypto crypto) {
		this.crypto = crypto;
	}

	/**
	 * Gets the key alias.
	 *
	 * @return the key alias
	 */
	public final String getKeyAlias() {
		return keyAlias;
	}

	/**
	 * Sets the key alias.
	 *
	 * @param keyAlias the new key alias
	 */
	public final void setKeyAlias(final String keyAlias) {
		this.keyAlias = keyAlias;
	}

	/**
	 * Gets the key password.
	 *
	 * @return the key password
	 */
	public final String getKeyPassword() {
		return keyPassword;
	}

	/**
	 * Sets the key password.
	 *
	 * @param keyPassword the new key password
	 */
	public final void setKeyPassword(final String keyPassword) {
		this.keyPassword = keyPassword;
	}
}
