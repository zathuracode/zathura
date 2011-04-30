package com.vortexbird.amazilia.fw;

import java.beans.PropertyChangeListener;

import net.sourceforge.squirrel_sql.fw.id.IIdentifier;
import net.sourceforge.squirrel_sql.fw.persist.ValidationException;
import net.sourceforge.squirrel_sql.fw.sql.ISQLAlias;
import net.sourceforge.squirrel_sql.fw.sql.SQLDriverPropertyCollection;

// TODO: Auto-generated Javadoc
/**
 * The Class AmaziliaSQLAlias.
 */
public class AmaziliaSQLAlias implements ISQLAlias {

	/** The url. */
	private String url;
	
	/** The name. */
	private String name;
	
	/** The password. */
	private String password;
	
	/** The user name. */
	private String userName;
	
	/** The auto logon. */
	private boolean autoLogon;
	
	/** The valid. */
	private boolean valid;

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object rhs) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#getDriverIdentifier()
	 */
	@Override
	public IIdentifier getDriverIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#getDriverPropertiesClone()
	 */
	@Override
	public SQLDriverPropertyCollection getDriverPropertiesClone() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#getName()
	 */
	@Override
	public String getName() {

		return name;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#getPassword()
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#getUrl()
	 */
	@Override
	public String getUrl() {

		return url;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#getUseDriverProperties()
	 */
	@Override
	public boolean getUseDriverProperties() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#getUserName()
	 */
	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return userName;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#isAutoLogon()
	 */
	@Override
	public boolean isAutoLogon() {
		// TODO Auto-generated method stub
		return autoLogon;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#isConnectAtStartup()
	 */
	@Override
	public boolean isConnectAtStartup() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#setAutoLogon(boolean)
	 */
	@Override
	public void setAutoLogon(boolean value) {
		this.autoLogon = value;

	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#setConnectAtStartup(boolean)
	 */
	@Override
	public void setConnectAtStartup(boolean value) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#setDriverIdentifier(net.sourceforge.squirrel_sql.fw.id.IIdentifier)
	 */
	@Override
	public void setDriverIdentifier(IIdentifier data) throws ValidationException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#setDriverProperties(net.sourceforge.squirrel_sql.fw.sql.SQLDriverPropertyCollection)
	 */
	@Override
	public void setDriverProperties(SQLDriverPropertyCollection value) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) throws ValidationException {
		this.name = name;

	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#setPassword(java.lang.String)
	 */
	@Override
	public void setPassword(String password) throws ValidationException {
		this.password = password;

	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#setUrl(java.lang.String)
	 */
	@Override
	public void setUrl(String url) throws ValidationException {
		this.url = url;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#setUseDriverProperties(boolean)
	 */
	@Override
	public void setUseDriverProperties(boolean value) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.sql.ISQLAlias#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) throws ValidationException {
		this.userName = userName;

	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.id.IHasIdentifier#getIdentifier()
	 */
	@Override
	public IIdentifier getIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.squirrel_sql.fw.persist.IValidatable#isValid()
	 */
	@Override
	public boolean isValid() {
		return valid;
	}

}
